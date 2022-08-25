package com.imooc.ecommerce.controller;


import brave.Tags;
import com.imooc.ecommerce.account.AddressInfo;
import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.service.IEcommerceAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户地址表 前端控制器
 * </p>
 *
 * @author ZZY
 * @since 2022-08-11
 */
@RestController
@RequestMapping("/address")
@Api(tags = "用户地址控制器")
@Slf4j
public class EcommerceAddressController {

    @Autowired
    private IEcommerceAddressService iEcommerceAddressService;

    @ApiOperation(value = "创建",notes = "创建用户地址信息",httpMethod = "POST")
    @PostMapping("/creat-address")
    public TableId createAddressInfo(@RequestBody AddressInfo addressInfo){
        return iEcommerceAddressService.createAddressInfo(addressInfo);
    }

    @ApiOperation(value = "当前用户",notes = "获取当前登录的用户地址信息",httpMethod = "GET")
    @GetMapping("/current-address")
    public AddressInfo getCurrentAddressInfo(){
        return iEcommerceAddressService.getCurrentAddressInfo();
    }

    @ApiOperation(value = "获取用户地址信息",
            notes = "通过 id 获取用户地址信息， id 是 EcommerceAddress 表的主键",httpMethod = "GET")
    @GetMapping("/address-info")
    public AddressInfo getAddressInfoById(@RequestParam Long id){
        return iEcommerceAddressService.getAddressInfoById(id);
    }

    @ApiOperation(value = "获取用户地址信息",notes = "通过 TableId 获取用户地址信息",httpMethod = "POST")
    @GetMapping("/address-info-by-table-id")
    public AddressInfo getAddressInfoByTableId(@RequestBody TableId tableId){
        return iEcommerceAddressService.getAddressInfoByTableId(tableId);
    }
}
