package com.imooc.ecommerce.service;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.account.AddressInfo;
import com.imooc.ecommerce.common.TableId;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * TODO: 用户地址相关服务功能测试
 *
 * @author zzy
 * @date 2022/8/12
 */
@Slf4j
public class AddressServiceTest extends BaseTest{

    @Autowired
    private IEcommerceAddressService iEcommerceAddressService;

    /**
     * 测试创建用户地址信息
     */
    @Test
    public void testCreateAddressInfo(){

        AddressInfo.AddressItem addressItem = new AddressInfo.AddressItem();
        addressItem.setUsername("zzy");
        addressItem.setPhone("999999999");
        addressItem.setProvince("上海市");
        addressItem.setCity("上海市");
        addressItem.setAddressDetail("陆家嘴");
        addressItem.setCreateTime(LocalDateTime.now());
        addressItem.setUpdateTime(LocalDateTime.now());

        log.info("test create address info: [{}]", JSON.toJSONString(
                iEcommerceAddressService.createAddressInfo(
                        new AddressInfo(loginUserInfo.getUserId(), Collections.singletonList(addressItem))
                )
        ));
    }

    /**
     * 测试获取用户地址信息
     */
    @Test
    public void testGetCurrentAddressInfo(){
        log.info("test get current user info: [{}]",
                JSON.toJSONString(iEcommerceAddressService.getCurrentAddressInfo()));
    }

    /**
     * 测试通过 id 获取用户地址信息
     */
    @Test
    public void testGetAddressInfoById(){
        log.info("test get address info by id: [{}]",
                JSON.toJSONString(iEcommerceAddressService.getAddressInfoById(1558000472269352981L)));
    }

    /**
     * 测试通过 tableId 获取用户地址信息
     */
    @Test
    public void testGetAddressInfoByTableId(){
        log.info("test get address info by table id: [{}]",
                JSON.toJSONString(iEcommerceAddressService.getAddressInfoByTableId(
                        new TableId(Collections.singletonList(new TableId.ID(1558000472269352981L)))
                )));
    }

}
