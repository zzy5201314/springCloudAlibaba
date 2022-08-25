package com.imooc.ecommerce.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author ZZY
 * @since 2022-08-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TEcommerceGoods对象", description="商品表")
public class TEcommerceGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品类别")
    private String goodsCategory;

    @ApiModelProperty(value = "品牌分类")
    private String brandCategory;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品图片")
    private String goodsPic;

    @ApiModelProperty(value = "商品描述信息")
    private String goodsDescription;

    @ApiModelProperty(value = "商品状态")
    private Integer goodsStatus;

    @ApiModelProperty(value = "商品价格")
    private Integer price;

    @ApiModelProperty(value = "总供应量")
    private Long supply;

    @ApiModelProperty(value = "库存")
    private Long inventory;

    @ApiModelProperty(value = "商品属性")
    private String goodsProperty;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
