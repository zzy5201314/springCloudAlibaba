package com.imooc.ecommerce.service.impl;

import com.imooc.ecommerce.account.BalanceInfo;
import com.imooc.ecommerce.entity.EcommerceBalance;
import com.imooc.ecommerce.mapper.EcommerceBalanceMapper;
import com.imooc.ecommerce.service.IEcommerceBalanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户账户余额表 服务实现类
 * </p>
 *
 * @author ZZY
 * @since 2022-08-11
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class EcommerceBalanceServiceImpl extends ServiceImpl<EcommerceBalanceMapper, EcommerceBalance> implements IEcommerceBalanceService {

    @Override
    public BalanceInfo getCurrentBalanceInfo() {
        return null;
    }

    @Override
    public BalanceInfo deductBalance(BalanceInfo balanceInfo) {
        return null;
    }
}
