package com.imooc.ecommerce.service;

import com.imooc.ecommerce.account.BalanceInfo;
import com.imooc.ecommerce.entity.EcommerceBalance;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户账户余额表 服务类
 * </p>
 *
 * @author ZZY
 * @since 2022-08-11
 */
public interface IEcommerceBalanceService extends IService<EcommerceBalance> {

    /**
     * 获取当前用户余额信息
     * @return
     */
    BalanceInfo getCurrentBalanceInfo();

    /**
     * 扣减用户余额
     * @param balanceInfo 代表想要扣减的余额
     * @return
     */
    BalanceInfo deductBalance(BalanceInfo balanceInfo);

}
