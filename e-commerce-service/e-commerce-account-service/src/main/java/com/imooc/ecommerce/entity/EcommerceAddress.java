package com.imooc.ecommerce.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.imooc.ecommerce.account.AddressInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 用户地址表
 * </p>
 *
 * @author ZZY
 * @since 2022-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TEcommerceAddress对象", description="用户地址表")
@TableName(value = "t_ecommerce_address")
public class EcommerceAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户 id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "详细地址")
    private String addressDetail;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 根据 userId + AddressItem 得到 EcommerceAddress
     * @param userId
     * @param addressItem
     * @return
     */
    public static EcommerceAddress toEcommerceAddress(Long userId, AddressInfo.AddressItem addressItem){

        EcommerceAddress ecommerceAddresses = new EcommerceAddress();

        ecommerceAddresses.setUserId(userId);
        ecommerceAddresses.setUsername(addressItem.getUsername());
        ecommerceAddresses.setPhone(addressItem.getPhone());
        ecommerceAddresses.setProvince(addressItem.getProvince());
        ecommerceAddresses.setCity(addressItem.getCity());
        ecommerceAddresses.setAddressDetail(addressItem.getAddressDetail());
        ecommerceAddresses.setCreateTime(LocalDateTime.now());
        ecommerceAddresses.setUpdateTime(LocalDateTime.now());

        return ecommerceAddresses;
    }

    /**
     * 将 EcommerceAddress 对象转成 AddressInfo
     * @return
     */
    public AddressInfo.AddressItem toAddressItem(){

        AddressInfo.AddressItem addressItem = new AddressInfo.AddressItem();

        addressItem.setId(this.id);
        addressItem.setUsername(this.username);
        addressItem.setPhone(this.phone);
        addressItem.setProvince(this.province);
        addressItem.setCity(this.city);
        addressItem.setAddressDetail(this.addressDetail);
        addressItem.setCreateTime(this.createTime);
        addressItem.setUpdateTime(this.updateTime);

        return addressItem;
    }
}
