package com.imooc.ecommerce.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.constant.GoodsConstant;
import com.imooc.ecommerce.entity.EcommerceGoods;
import com.imooc.ecommerce.goods.DeductGoodsInventory;
import com.imooc.ecommerce.goods.GoodsInfo;
import com.imooc.ecommerce.goods.SimpleGoodsInfo;
import com.imooc.ecommerce.mapper.EcommerceGoodsMapper;
import com.imooc.ecommerce.service.IEcommerceGoodsService;
import com.imooc.ecommerce.vo.PageSimpleGoodsInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author ZZY
 * @since 2022-08-25
 */
@Slf4j
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EcommerceGoodsServiceImpl extends ServiceImpl<EcommerceGoodsMapper, EcommerceGoods> implements IEcommerceGoodsService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private EcommerceGoodsMapper ecommerceGoodsMapper;
    @Autowired
    private IEcommerceGoodsService iEcommerceGoodsService;

    private static final Integer pageSize = 10;

    @Override
    public List<GoodsInfo> getGoodsInfoByTableId(TableId tableId) {

        /** TODO: 详细的商品信息不能从 Redis Cache 中拿 */
        List<Long> ids = tableId.getIds().stream()
                .map(TableId.ID::getId)
                .collect(Collectors.toList());
        log.info("get goods info by ids: [{}]", JSON.toJSONString(ids));

        List<EcommerceGoods> ecommerceGoods = ecommerceGoodsMapper.selectBatchIds(ids);

        return ecommerceGoods.stream().map(EcommerceGoods::toGoodsInfo).collect(Collectors.toList());
    }

    @Override
    public PageSimpleGoodsInfo getSimpleGoodsInfoByPage(int pageCount) {

        /** TODO: 分页不能从 redis cache 中去拿 */
        /** TODO: 这里是分页的规则，1页10条数据，按照 id 倒序排列 */
        Page<EcommerceGoods> page = new Page<>(pageCount <= 0 ? 1 : pageCount, pageSize);
        List<EcommerceGoods> ecommerceGoods = ecommerceGoodsMapper.findEcommerceGoodsByPage(page).stream()
                .sorted(Comparator.comparing(EcommerceGoods::getId).reversed())
                .collect(Collectors.toList());

        /** TODO: 是否有更多页数：总页数是否大于当前给定的页 */
        boolean hasMore = page.getTotal() > pageCount;

        return new PageSimpleGoodsInfo(
                ecommerceGoods.stream()
                        .map(EcommerceGoods::toSimpleGoodsInfo)
                        .collect(Collectors.toList())
                , hasMore
        );
    }

    @Override
    public List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(TableId tableId) {

        /** TODO: 获取商品的简单信息，可以从 redis cache 中去拿，如果不能拿到则从 DB 中获取，并保存到 redis 中 */
        /** TODO: Redis 中的 KV 都是字符串类型，需要进行反序列化操作 */
        List<Object> goodIds = tableId.getIds().stream()
                .map(i -> i.getId().toString()).collect(Collectors.toList());
        List<Object> cacheSimpleGoodsInfos = redisTemplate.opsForHash()
                .multiGet(GoodsConstant.ECOMMERCE_GOODS_DICT_KEY, goodIds);

        /** TODO: 如果从 Redis 中查到了商品信息，分两种情况去操作 */
        if (CollectionUtils.isNotEmpty(cacheSimpleGoodsInfos)) {
            /** TODO: 1.如果从缓存中查询出所有需要的 SimpleGoodsInfo */
            if (cacheSimpleGoodsInfos.size() == goodIds.size()) {
                log.info("get simple goods info by ids(from Cache): [{}]", JSON.toJSONString(goodIds));
                return parseCacheGoodsInfo(cacheSimpleGoodsInfos);
            } else {
                /** TODO: 2.一半从数据表中获取(right)，一半从 redis cache 中获取(left) */
                List<SimpleGoodsInfo> left = parseCacheGoodsInfo(cacheSimpleGoodsInfos);
                /** TODO: 取差集： 传递进来的参数 - 缓存中查询到的 = 缓存中没有的 */
                Collection<Long> subtractIds = CollectionUtils.subtract(
                        goodIds.stream().map(e -> Long.valueOf(e.toString())).collect(Collectors.toList()),
                        left.stream().map(SimpleGoodsInfo::getId).collect(Collectors.toList())
                );
                /** TODO: 缓存中没有的，查询数据表并缓存 */
                List<SimpleGoodsInfo> right = queryGoodsFromDBAndCacheToRedis(
                        new TableId(subtractIds.stream().map(TableId.ID::new).collect(Collectors.toList()))
                );
                /** TODO: 合并 left 和 right */
                log.info("get simple goods info by ids(from DB and Cache): [{}]", JSON.toJSONString(subtractIds));
                return new ArrayList<>(CollectionUtils.union(left, right));
            }
        } else {
            /** TODO: Redis 中没有任何数据 */
            return queryGoodsFromDBAndCacheToRedis(tableId);
        }
    }

    @Override
    public Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories) {

        /** TODO: 检验参数是否合法 */
        deductGoodsInventories.forEach(e -> {
            if (e.getCount() <= 0) {
                throw new RuntimeException("purchase goods count need > 0");
            }
        });

        List<EcommerceGoods> ecommerceGoods = iEcommerceGoodsService.getBaseMapper().selectBatchIds(
                deductGoodsInventories.stream()
                        .map(DeductGoodsInventory::getGoodsId)
                        .collect(Collectors.toList())
        );
        /** TODO: 根据传递的 goodsIds 查询不到商品对象，抛出异常 */
        if (CollectionUtils.isEmpty(ecommerceGoods)) {
            throw new RuntimeException("can not find any goods by request");
        }
        /** TODO: 如果查询出来的商品数量与传递的不一致，抛出异常 */
        if (ecommerceGoods.size() != deductGoodsInventories.size()) {
            throw new RuntimeException("request is not valid");
        }
        /** TODO: goodsId -> DeductGoodsInventory */
        Map<Long, DeductGoodsInventory> goodsId2Inventory = deductGoodsInventories.stream()
                .collect(Collectors.toMap(DeductGoodsInventory::getGoodsId, Function.identity()));

        /** TODO: 检查是否可以扣减库存 */
        ecommerceGoods.forEach(e -> {

            Long currentInventory = e.getInventory();
            Integer needDeductInventory = goodsId2Inventory.get(e.getId()).getCount();
            if (currentInventory < needDeductInventory) {
                log.error("goods inventory is not enough: [{}], [{}]",
                        currentInventory, needDeductInventory);
                throw new RuntimeException("goods inventory is not enough: [{}]" + e.getId());
            }
            /** TODO: 扣减库存 */
            e.setInventory(currentInventory - needDeductInventory);
            log.info("deduct goods inventory: [{}], [{}], [{}]", e.getId(), currentInventory, needDeductInventory);
        });

        /** TODO: 如果没有执行成功会抛出异常，执行成功都会返回 true */
        iEcommerceGoodsService.updateBatchById(ecommerceGoods);
        log.info("deduct goods inventory done");
        return true;
    }

    @Override
    public Optional<EcommerceGoods> findFirstPageByGoodsCategoryAndBrandCategoryAndGoodsName(String GoodsCategory, String BrandCategory, String GoodsName) {
        return ecommerceGoodsMapper.findFirstPageByGoodsCategoryAndBrandCategoryAndGoodsName(
                GoodsCategory, BrandCategory, GoodsName
        );
    }

    /**
     * TODO: 将缓存中的对象反序列化为 Java Pojo 对象
     *
     * @Author : zzy
     * @Date 2022/8/30 14:57
     * @param: cacheSimpleGoodsInfo
     * @return: java.util.List<com.imooc.ecommerce.goods.SimpleGoodsInfo>
     */
    private List<SimpleGoodsInfo> parseCacheGoodsInfo(List<Object> cacheSimpleGoodsInfo) {

        return cacheSimpleGoodsInfo.stream()
                .map(s -> JSON.parseObject(s.toString(), SimpleGoodsInfo.class))
                .collect(Collectors.toList());
    }

    /**
     * TODO: 从数据表中查询数据，并缓存到 Redis 中
     *
     * @Author : zzy
     * @Date 2022/8/30 16:27
     * @param: tableId
     * @return: java.util.List<com.imooc.ecommerce.goods.SimpleGoodsInfo>
     */
    private List<SimpleGoodsInfo> queryGoodsFromDBAndCacheToRedis(TableId tableId) {

        /** TODO: 从数据表中查询数据并做转换 */
        List<Long> ids = tableId.getIds().stream()
                .map(TableId.ID::getId)
                .collect(Collectors.toList());
        log.info("get simple goods info by ids(from DB): [{}]", JSON.toJSONString(ids));

        List<EcommerceGoods> ecommerceGoods = ecommerceGoodsMapper.selectBatchIds(ids);
        List<SimpleGoodsInfo> simpleGoodsInfos = ecommerceGoods.stream()
                .map(EcommerceGoods::toSimpleGoodsInfo).collect(Collectors.toList());

        /** TODO: 将结果缓存，下一次可以直接从 redis cache 中查询 */
        log.info("cache goods info: [{}]", JSON.toJSONString(ids));

        Map<String, String> id2JsonObject = new HashMap<>(simpleGoodsInfos.size());
        simpleGoodsInfos.stream().forEach(
                e -> id2JsonObject.put(e.getId().toString(), JSON.toJSONString(e))
        );
        /** TODO: 保存到 Redis 中 */
        redisTemplate.opsForHash().putAll(GoodsConstant.ECOMMERCE_GOODS_DICT_KEY, id2JsonObject);

        return simpleGoodsInfos;
    }


}
