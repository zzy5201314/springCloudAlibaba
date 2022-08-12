package com.imooc.ecommerce.service;

import com.imooc.ecommerce.account.AddressInfo;
import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.entity.EcommerceAddress;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户地址表 服务类
 * </p>
 *
 * @author ZZY
 * @since 2022-08-11
 */
public interface IEcommerceAddressService extends IService<EcommerceAddress> {

    /**
     * 创建用户地址信息
     * @param addressInfo
     * @return
     */
    TableId createAddressInfo(AddressInfo addressInfo);

    /**
     * 获取当前登录的用户地址信息
     * @return
     */
    AddressInfo getCurrentAddressInfo();

    /**
     * 通过 id 获取用户地址信息， id 是 EcommerceAddress 表的主键
     * @param id
     * @return
     */
    AddressInfo getAddressInfoById(Long id);

    /**
     * 通过 TableId 获取用户地址信息
     * @param tableId
     * @return
     */
    AddressInfo getAddressInfoByTableId(TableId tableId);
}
