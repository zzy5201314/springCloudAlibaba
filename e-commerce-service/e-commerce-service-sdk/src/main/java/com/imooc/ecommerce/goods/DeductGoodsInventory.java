package com.imooc.ecommerce.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO: 扣减商品库存对象
 *
 * @author zzy
 * @date 2022/8/25
 */
@ApiModel(description = "扣减商品库存对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeductGoodsInventory {

    @ApiModelProperty(value = "商品主键 id")
    private Long goodsId;

    @ApiModelProperty(value = "扣减个数")
    private Integer count;
}
