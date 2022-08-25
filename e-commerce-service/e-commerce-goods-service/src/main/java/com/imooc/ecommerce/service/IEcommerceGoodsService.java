package com.imooc.ecommerce.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.entity.EcommerceGoods;
import com.imooc.ecommerce.goods.DeductGoodsInventory;
import com.imooc.ecommerce.goods.GoodsInfo;
import com.imooc.ecommerce.goods.SimpleGoodsInfo;
import com.imooc.ecommerce.vo.PageSimpleGoodsInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author ZZY
 * @since 2022-08-25
 */
public interface IEcommerceGoodsService extends IService<EcommerceGoods> {

    /**
     * TODO: 根据 TableId 查询商品详细信息
     *
     * @Author : zzy
     * @Date 2022/8/25 21:48
     * @param: tableId
     * @return: java.util.List<com.imooc.ecommerce.goods.GoodsInfo>
     */
    List<GoodsInfo> getGoodsInfoByTableId(TableId tableId);

    /**
     * TODO: 获取分页的商品信息
     *
     * @Author : zzy
     * @Date 2022/8/25 21:50
     * @param: page
     * @return: com.imooc.ecommerce.vo.PageSimpleGoodsInfo
     */
    PageSimpleGoodsInfo getSimpleGoodsInfoByPage(int page);

    /**
     * TODO: 根据 TableId 查询简单商品信息
     *
     * @Author : zzy
     * @Date 2022/8/25 21:50
     * @param: tableId
     * @return: java.util.List<com.imooc.ecommerce.goods.SimpleGoodsInfo>
     */
    List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(TableId tableId);

    /**
     * TODO: 扣减商品库存
     *
     * @Author : zzy
     * @Date 2022/8/25 21:52
     * @param: deductGoodsInventories
     * @return: java.lang.Boolean
     */
    Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories);

    /***
     * TODO: Mapper 映射查询语句
     *
     * @Author : zzy
     * @Date 2022/8/25 22:51
     * @param: GoodsCategory
     * @param: BrandCategory
     * @param: GoodsName
     * @return: java.util.Optional<com.imooc.ecommerce.entity.EcommerceGoods>
     */
    Optional<EcommerceGoods> findFirstPageByGoodsCategoryAndBrandCategoryAndGoodsName(
            String GoodsCategory,
            String BrandCategory,
            String GoodsName
    );
}
