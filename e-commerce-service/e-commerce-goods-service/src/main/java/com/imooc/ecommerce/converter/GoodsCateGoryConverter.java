package com.imooc.ecommerce.converter;

import com.imooc.ecommerce.constant.BrandCategory;
import com.imooc.ecommerce.constant.GoodsCateGory;

import javax.persistence.AttributeConverter;

/**
 * TODO: Description
 *
 * @author zzy
 * @date 2022/8/25
 */
public class GoodsCateGoryConverter implements AttributeConverter<GoodsCateGory, String> {
    @Override
    public String convertToDatabaseColumn(GoodsCateGory goodsCateGory) {
        return goodsCateGory.getCode();
    }

    @Override
    public GoodsCateGory convertToEntityAttribute(String code) {
        return GoodsCateGory.getGoodsCateGory(code);
    }
}
