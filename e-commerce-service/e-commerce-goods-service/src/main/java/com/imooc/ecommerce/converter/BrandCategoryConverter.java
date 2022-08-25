package com.imooc.ecommerce.converter;

import com.imooc.ecommerce.constant.BrandCategory;

import javax.persistence.AttributeConverter;

/**
 * TODO: Description
 *
 * @author zzy
 * @date 2022/8/25
 */
public class BrandCategoryConverter implements AttributeConverter<BrandCategory, String> {
    @Override
    public String convertToDatabaseColumn(BrandCategory brandCategory) {
        return brandCategory.getCode();
    }

    @Override
    public BrandCategory convertToEntityAttribute(String code) {
        return BrandCategory.getBrandCategory(code);
    }
}
