package com.imooc.ecommerce.controller;


import com.imooc.ecommerce.account.AddressInfo;
import com.imooc.ecommerce.account.BalanceInfo;
import com.imooc.ecommerce.service.IEcommerceBalanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户账户余额表 前端控制器
 * </p>
 *
 * @author ZZY
 * @since 2022-08-11
 */
@RestController
@RequestMapping("/balance")
@Api(tags = "用户账户余额控制器")
@Slf4j
public class EcommerceBalanceController {

    @Autowired
    private IEcommerceBalanceService iEcommerceBalanceService;

    @ApiOperation(value = "获取当前用户余额信息",
            notes = "获取当前用户余额信息",httpMethod = "GET")
    @GetMapping("/balance-info")
    public BalanceInfo getCurrentBalanceInfo(){
        return iEcommerceBalanceService.getCurrentBalanceInfo();
    }

    @ApiOperation(value = "增加",
            notes = "增加当前用户余额",httpMethod = "POST")
    @GetMapping("/add-balance")
    public BalanceInfo addBalance(@RequestBody BalanceInfo balanceInfo){
        return iEcommerceBalanceService.addBalance(balanceInfo);
    }

    @ApiOperation(value = "扣减",
            notes = "扣减当前用户余额",httpMethod = "POST")
    @GetMapping("/deduct-balance")
    public BalanceInfo deductBalance(@RequestBody BalanceInfo balanceInfo){
        return iEcommerceBalanceService.deductBalance(balanceInfo);
    }

    @ApiOperation(value = "增加",
            notes = "增加其他用户余额",httpMethod = "POST")
    @GetMapping("/add-other-balance")
    public BalanceInfo addOtherUserBalance(@RequestBody BalanceInfo balanceInfo){
        return iEcommerceBalanceService.addOtherUserBalance(balanceInfo);
    }

    @ApiOperation(value = "扣减",
            notes = "扣减其他用户余额",httpMethod = "POST")
    @GetMapping("/deduct-other-balance")
    public BalanceInfo deductOtherUserBalance(@RequestBody BalanceInfo balanceInfo){
        return iEcommerceBalanceService.deductOtherUserBalance(balanceInfo);
    }

}
