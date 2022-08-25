package com.imooc.ecommerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * TODO: 商品类别
 * 电器 -> 手机、电脑
 *
 * @author zzy
 * @date 2022/8/25
 */
@Getter
@AllArgsConstructor
public enum GoodsCateGory {

    GOODS_A("10001", "电器"),
    GOODS_B("10002", "家具"),
    GOODS_C("10003", "服饰"),
    GOODS_D("10004", "母婴"),
    ;

    /**
     * 商品分类编码
     */
    private final String code;

    /**
     * 商品分类描述信息
     */
    private final String description;

    /**
     * 根据 code 获取到 GoodsCateGory
     *
     * @param code
     * @return
     */
    public static GoodsCateGory getGoodsCateGory(String code) {

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(e -> e.code.equals(code))
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException(code + "not exits")
                );
    }
}
