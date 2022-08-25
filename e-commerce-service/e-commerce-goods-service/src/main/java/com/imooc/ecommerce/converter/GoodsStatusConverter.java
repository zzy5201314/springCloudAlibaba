package com.imooc.ecommerce.converter;

import com.imooc.ecommerce.constant.GoodsStatus;

import javax.persistence.AttributeConverter;
/**
 * TODO: 商品状态枚举属性转换器
 *
 * @author zzy
 * @date 2022/8/24
 */
public class GoodsStatusConverter  implements AttributeConverter<GoodsStatus,Integer> {

    /**
     * TODO: 转换成可以存入数据表的基本类型
     *
     * @param goodsStatus
     * @return
     */
    @Override
    public Integer convertToDatabaseColumn(GoodsStatus goodsStatus) {
        return goodsStatus.getStatus();
    }

    /**
     * TODO: 还原数据表中的字段值到 Java 数据类型
     *
     * @date 2022/8/24 23:11
     * @author zzy
     * @params [status]
     * @return com.imooc.ecommerce.constant.GoodsStatus
     */
    @Override
    public GoodsStatus convertToEntityAttribute(Integer status) {
        return GoodsStatus.getGoodsStatus(status);
    }
}
