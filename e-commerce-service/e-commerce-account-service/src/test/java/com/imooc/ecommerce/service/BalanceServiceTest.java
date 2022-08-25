package com.imooc.ecommerce.service;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.account.BalanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO: Description
 *
 * @author zzy
 * @date 2022/8/12
 */
@Slf4j
public class BalanceServiceTest extends BaseTest {

    @Autowired
    private IEcommerceBalanceService iEcommerceBalanceService;

    /**
     * 测试获取当前用户的余额信息
     */
    @Test
    public void testGetCurrentBalanceInfo() {

        log.info("test get current user balance info: [{}]",
                JSON.toJSONString(iEcommerceBalanceService.getCurrentBalanceInfo()));
    }

    /**
     * 测试增加用户余额
     */
    @Test
    public void testAddBalanceInfo() {

        BalanceInfo balanceInfo = new BalanceInfo();
        balanceInfo.setUserId(loginUserInfo.getUserId());
        balanceInfo.setBalance(1000L);
        log.info("test add balance : [{}]",
                JSON.toJSONString(iEcommerceBalanceService.addBalance(balanceInfo)));
    }

    /**
     * 测试扣减用户余额
     */
    @Test
    public void testDeductBalance() {

        BalanceInfo balanceInfo = new BalanceInfo();
        balanceInfo.setUserId(loginUserInfo.getUserId());
        balanceInfo.setBalance(1000L);
        log.info("test deduct balance : [{}]",
                JSON.toJSONString(iEcommerceBalanceService.deductBalance(balanceInfo)));
    }


}
