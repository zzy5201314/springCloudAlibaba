package com.imooc.ecommerce.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * TODO: Description
 *
 * @author zzy
 * @date 2022/8/11
 */
@ApiModel(description = "用户地址信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressInfo {

    @ApiModelProperty(value = "用户 id")
    private Long userId;

    @ApiModelProperty(value = "地址详细信息")
    private List<AddressItem> addressItems;

    @ApiModel(description = "用户的单个地址信息")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddressItem{

        @ApiModelProperty(value = "地址表主键 id")
        private Long id;

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

        public AddressItem(Long id){
            this.id = id;
        }

        /**
         * 将 AddressItem 转换成 UserAddress
         * @return
         */
        public UserAddress toUserAddress(){
            UserAddress userAddress = new UserAddress();

            userAddress.setUsername(this.username);
            userAddress.setPhone(this.phone);
            userAddress.setProvince(this.province);
            userAddress.setCity(this.city);
            userAddress.setAddressDetail(this.addressDetail);

            return userAddress;
        }
    }

}
