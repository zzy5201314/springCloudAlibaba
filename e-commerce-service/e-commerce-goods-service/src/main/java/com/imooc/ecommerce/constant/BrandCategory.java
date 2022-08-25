package com.imooc.ecommerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * TODO: 品牌分类
 *
 * @author zzy
 * @date 2022/8/25
 */
@Getter
@AllArgsConstructor
public enum BrandCategory {

    BRAND_A("20001", "品牌 A"),
    BRAND_B("20001", "品牌 B"),
    BRAND_C("20001", "品牌 C"),
    BRAND_D("20001", "品牌 D"),
    ;

    /**
     * 品牌分类编码
     */
    private final String code;

    /**
     * 品牌分类描述信息
     */
    private final String description;

    /**
     * TODO: 根据 code 获取到 BrandCategory
     *
     * @return {@link null}
     * @date 2022/8/25 1:06
     * @author zzy
     * @params * @param null
     */
    public static BrandCategory getBrandCategory(String code) {

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(e -> e.code.equals(code))
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException(code + "not exits")
                );
    }
}
