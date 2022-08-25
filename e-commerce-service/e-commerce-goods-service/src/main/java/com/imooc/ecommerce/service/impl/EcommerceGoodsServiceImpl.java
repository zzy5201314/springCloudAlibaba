package com.imooc.ecommerce.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.entity.EcommerceGoods;
import com.imooc.ecommerce.goods.DeductGoodsInventory;
import com.imooc.ecommerce.goods.GoodsInfo;
import com.imooc.ecommerce.goods.SimpleGoodsInfo;
import com.imooc.ecommerce.mapper.EcommerceGoodsMapper;
import com.imooc.ecommerce.service.IEcommerceGoodsService;
import com.imooc.ecommerce.vo.PageSimpleGoodsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author ZZY
 * @since 2022-08-25
 */
@Service
public class EcommerceGoodsServiceImpl extends ServiceImpl<EcommerceGoodsMapper, EcommerceGoods> implements IEcommerceGoodsService {

    @Autowired
    private EcommerceGoodsMapper ecommerceGoodsMapper;

    @Override
    public List<GoodsInfo> getGoodsInfoByTableId(TableId tableId) {
        return null;
    }

    @Override
    public PageSimpleGoodsInfo getSimpleGoodsInfoByPage(int page) {
        return null;
    }

    @Override
    public List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(TableId tableId) {
        return null;
    }

    @Override
    public Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories) {
        return null;
    }

    @Override
    public Optional<EcommerceGoods> findFirstPageByGoodsCategoryAndBrandCategoryAndGoodsName(String GoodsCategory, String BrandCategory, String GoodsName) {
        return ecommerceGoodsMapper.findFirstPageByGoodsCategoryAndBrandCategoryAndGoodsName(
                GoodsCategory,BrandCategory,GoodsName
        );
    }
}
