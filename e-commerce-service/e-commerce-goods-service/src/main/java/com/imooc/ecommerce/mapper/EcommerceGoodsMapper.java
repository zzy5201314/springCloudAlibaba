package com.imooc.ecommerce.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.imooc.ecommerce.entity.EcommerceGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author ZZY
 * @since 2022-08-25
 */
@Mapper
@Repository
public interface EcommerceGoodsMapper extends BaseMapper<EcommerceGoods> {

    /**
     * 根据查询条件查询商品表，并限制返回结果
     * @param GoodsCategory
     * @param BrandCategory
     * @param GoodsName
     * @return
     */
    Optional<EcommerceGoods> findFirstPageByGoodsCategoryAndBrandCategoryAndGoodsName(
            @Param("GoodsCategory")String GoodsCategory,
            @Param("BrandCategory")String BrandCategory,
            @Param("GoodsName")String GoodsName
    );

    /**
     * TODO: 查询要求页数的数据
     *
     * @Author : zzy
     * @Date 2022/8/30 14:38
     * @param: page
     * @return: java.util.List<com.imooc.ecommerce.entity.EcommerceGoods>
     */
    List<EcommerceGoods> findEcommerceGoodsByPage(Page<EcommerceGoods> page);
}
