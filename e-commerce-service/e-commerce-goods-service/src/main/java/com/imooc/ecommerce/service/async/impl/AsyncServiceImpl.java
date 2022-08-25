package com.imooc.ecommerce.service.async.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.imooc.ecommerce.constant.GoodsConstant;
import com.imooc.ecommerce.entity.EcommerceGoods;
import com.imooc.ecommerce.goods.GoodsInfo;
import com.imooc.ecommerce.goods.SimpleGoodsInfo;
import com.imooc.ecommerce.mapper.EcommerceGoodsMapper;
import com.imooc.ecommerce.service.IEcommerceGoodsService;
import com.imooc.ecommerce.service.async.IAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * TODO: Description
 *
 * @author zzy
 * @date 2022/8/25
 */
@Slf4j
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AsyncServiceImpl implements IAsyncService {

    @Autowired
    private IEcommerceGoodsService iEcommerceGoodsService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * TODO: 异步任务需要加入注解，并指定使用的线程池
     * 异步任务处理两件事情：
     * 1.将商品信息保存到数据表
     * 2.更新商品缓存
     *
     * @Author : zzy
     * @Date 2022/8/25 22:21
     * @param: goodsInfos
     * @param: taskId
     */
    @Async("Goods-Async")
    @Override
    public void asyncImportGoods(List<GoodsInfo> goodsInfos, String taskId) {

        log.info("async task running taskId: [{}]", taskId);

        Stopwatch watch = Stopwatch.createStarted();

        /** TODO: 1.如果是 goodsInfos 存在重复的商品，不保存；直接返回，记录错误日志  */
        /** TODO: 请求数据是否合法的标记 */
        boolean isIllegal = false;

        /** TODO: 将商品信息字段 joint 在一起，用来判断是否存在重复 */
        Set<String> goodsJointInfos = new HashSet<>(goodsInfos.size());
        /** TODO: 过滤出来的可以入库的商品信息(规则按照自己的业务需求自定义即可) */
        List<GoodsInfo> filteredGoodsInfo = new ArrayList<>(goodsInfos.size());

        /** TODO: 走一遍循环，过滤非法参数与判定当前请求是否合法 */
        for (GoodsInfo goods : goodsInfos) {

            /** TODO: 基本条件不满足的，直接过滤 */
            if (goods.getPrice() <= 0 || goods.getSupply() <= 0) {
                log.info("goods info is invalid: [{}]", JSON.toJSONString(goods));
                continue;
            }

            /** TODO: 组合商品信息 */
            String joinInfo = String.format(
                    "%s,%s,%s",
                    goods.getGoodsCategory(),
                    goods.getBrandCategory(),
                    goods.getGoodsName()
            );
            if (goodsJointInfos.contains(joinInfo)) {
                isIllegal = true;
            }

            // 加入到两个容器中
            goodsJointInfos.add(joinInfo);
            filteredGoodsInfo.add(goods);
        }

        /** TODO: 如果存在重复商品或者时没有需要入库的商品，直接打印日志返回 */
        if (isIllegal || CollectionUtils.isEmpty(filteredGoodsInfo)) {
            watch.stop();
            log.warn("import nothing: [{}]", JSON.toJSONString(filteredGoodsInfo));
            log.info("check and import goods done: [{}ms]",
                    watch.elapsed(TimeUnit.MILLISECONDS));
            return;
        }

        List<EcommerceGoods> ecommerceGoods = filteredGoodsInfo.stream()
                .map(EcommerceGoods::toEcommerceGoods)
                .collect(Collectors.toList());

        List<EcommerceGoods> targetGoods = new ArrayList<>(ecommerceGoods.size());

        /** TODO: 保存 goodsInfo 之前先判断下是否存在重复商品 */
        ecommerceGoods.forEach(e -> {
            if (null != iEcommerceGoodsService.findFirstPageByGoodsCategoryAndBrandCategoryAndGoodsName(
                    e.getGoodsCategory(), e.getBrandCategory(), e.getGoodsName()
            ).orElse(null)) {
                return;
            }
            targetGoods.add(e);
        });

        /** TODO: 商品信息入库 */
        boolean isSaveGoods = iEcommerceGoodsService.saveBatch(targetGoods);

        /** TODO: 将入库商品信息同步到 redis 中 */
        if (isSaveGoods) {
            saveNewGoodsInfoToRedis(targetGoods);
            log.info("save goods info to db and redis: [{}]", targetGoods.size());
            watch.stop();
            log.info("check and import goods success: [{}ms]",
                    watch.elapsed(TimeUnit.MILLISECONDS));
        }
    }

    /***
     * TODO: 将保存到数据表中的数据缓存到 Redis 中
     * dict : key -> <id, SimpleGoodsInfo(json)>
     *
     * @Author : zzy
     * @Date 2022/8/25 22:58
     * @param: savedGoods
     */
    private void saveNewGoodsInfoToRedis(List<EcommerceGoods> targetGoods) {

        /** TODO: 由于 Redis 是内存存储，只存储简单商品信息 */
        List<SimpleGoodsInfo> simpleGoodsInfos = targetGoods.stream()
                .map(EcommerceGoods::toSimpleGoodsInfo)
                .collect(Collectors.toList());

        Map<String, String> id2JsonObject = new HashMap<>(simpleGoodsInfos.size());
        simpleGoodsInfos.forEach(
                e -> id2JsonObject.put(e.getId().toString(),JSON.toJSONString(e))
        );

        /** TODO: 保存到 Redis 中 */
        redisTemplate.opsForHash().putAll(
                GoodsConstant.ECOMMERCE_GOODS_DICT_KEY,
                id2JsonObject
        );
    }

}
