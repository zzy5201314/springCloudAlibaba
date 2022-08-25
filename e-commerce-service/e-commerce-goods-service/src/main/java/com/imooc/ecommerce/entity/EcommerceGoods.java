package com.imooc.ecommerce.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.imooc.ecommerce.constant.BrandCategory;
import com.imooc.ecommerce.constant.GoodsCateGory;
import com.imooc.ecommerce.constant.GoodsStatus;
import com.imooc.ecommerce.goods.GoodsInfo;
import com.imooc.ecommerce.goods.SimpleGoodsInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName(value = "t_ecommerce_goods")
public class EcommerceGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Long id;

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

    /**
     * TODO: 将 GoodsInfo 转换成实体对象
     *
     * @Author : zzy
     * @Date 2022/8/25 21:33
     * @param: goodsInfo
     * @return: com.imooc.ecommerce.entity.EcommerceGoods
     */
    public static EcommerceGoods toEcommerceGoods(GoodsInfo goodsInfo){

        EcommerceGoods ecommerceGoods = new EcommerceGoods();

        ecommerceGoods.setGoodsCategory(GoodsCateGory.getGoodsCateGory(goodsInfo.getGoodsCategory()).getCode());
        ecommerceGoods.setBrandCategory(BrandCategory.getBrandCategory(goodsInfo.getBrandCategory()).getCode());
        ecommerceGoods.setGoodsName(goodsInfo.getGoodsName());
        ecommerceGoods.setGoodsPic(goodsInfo.getGoodsPic());
        ecommerceGoods.setGoodsDescription(goodsInfo.getGoodsDescription());
        // 可以增加一个审核的过程
        ecommerceGoods.setGoodsStatus(GoodsStatus.ONLINE.getStatus());
        ecommerceGoods.setPrice(goodsInfo.getPrice());
        ecommerceGoods.setSupply(goodsInfo.getSupply());
        ecommerceGoods.setGoodsProperty(
                JSON.toJSONString(goodsInfo.getGoodsProperty())
        );

        return ecommerceGoods;
    }

    /**
     * TODO: 将实体对象转换成 GoodsInfo
     *
     * @Author : zzy
     * @Date 2022/8/25 21:32
     * @return: com.imooc.ecommerce.goods.GoodsInfo
     */
    public GoodsInfo toGoodsInfo(){

        GoodsInfo goodsInfo = new GoodsInfo();

        goodsInfo.setId(this.id);
        goodsInfo.setGoodsCategory(GoodsCateGory.getGoodsCateGory(this.goodsCategory).getDescription());
        goodsInfo.setBrandCategory(BrandCategory.getBrandCategory(this.brandCategory).getDescription());
        goodsInfo.setGoodsName(this.goodsName);
        goodsInfo.setGoodsPic(this.goodsPic);
        goodsInfo.setGoodsDescription(this.goodsDescription);
        goodsInfo.setPrice(this.price);
        goodsInfo.setGoodsProperty(
                JSON.parseObject(this.goodsProperty, GoodsInfo.GoodsProperty.class)
        );
        goodsInfo.setSupply(this.supply);
        goodsInfo.setInventory(this.inventory);
        goodsInfo.setCreateTime(this.createTime);
        goodsInfo.setUpdateTime(this.updateTime);

        return goodsInfo;
    }

    /**
     * TODO: 将实体对象转换成 SimpleGoodsInfo
     *
     * @Author : zzy
     * @Date 2022/8/25 21:42
     * @return: com.imooc.ecommerce.goods.SimpleGoodsInfo
     */
    public SimpleGoodsInfo toSimpleGoodsInfo(){

        SimpleGoodsInfo simpleGoodsInfo = new SimpleGoodsInfo();

        simpleGoodsInfo.setId(this.id);
        simpleGoodsInfo.setGoodsName(this.goodsName);
        simpleGoodsInfo.setGoodsPic(this.goodsPic);
        simpleGoodsInfo.setPrice(this.price);

        return simpleGoodsInfo;
    }
}
