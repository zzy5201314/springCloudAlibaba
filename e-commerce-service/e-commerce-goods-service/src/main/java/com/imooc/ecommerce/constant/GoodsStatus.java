package com.imooc.ecommerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Bean;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * TODO: 商品状态枚举类
 *
 * @author zzy
 * @date 2022/8/24
 */
@Getter
@AllArgsConstructor
public enum GoodsStatus {

    ONLINE(101, "上线"),
    OFFLINE(102, "下线"),
    STOCK_OUT(103, "缺货状态"),
    ;

    /**
     * 状态码
     */
    private final Integer status;
    /**
     * 状态描述
     */
    private final String description;

    /**
     * 根据 code 获取到 GoodsStatus
     *
     * @param status
     * @return
     */
    public static GoodsStatus getGoodsStatus(Integer status) {

        Objects.requireNonNull(status);

        return Stream.of(values())
                .filter(e -> e.status.equals(status))
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException(status + "not exits")
                );
    }
}
