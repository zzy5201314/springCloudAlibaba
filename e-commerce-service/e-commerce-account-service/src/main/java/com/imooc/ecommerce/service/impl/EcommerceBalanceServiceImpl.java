package com.imooc.ecommerce.service.impl;

import com.imooc.ecommerce.Enum.BalanceEnum;
import com.imooc.ecommerce.account.BalanceInfo;
import com.imooc.ecommerce.entity.EcommerceBalance;
import com.imooc.ecommerce.filter.AccessContext;
import com.imooc.ecommerce.mapper.EcommerceBalanceMapper;
import com.imooc.ecommerce.service.IEcommerceBalanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EcommerceBalanceServiceImpl extends ServiceImpl<EcommerceBalanceMapper, EcommerceBalance> implements IEcommerceBalanceService {

    @Autowired
    private IEcommerceBalanceService iEcommerceBalanceService;
    @Autowired
    private EcommerceBalanceMapper ecommerceBalanceMapper;

    @Override
    @Transactional
    public BalanceInfo getCurrentBalanceInfo() {

        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        BalanceInfo balanceInfo = new BalanceInfo(
                loginUserInfo.getUserId(), 0L
        );

        EcommerceBalance ecommerceBalance = ecommerceBalanceMapper.findBalanceByUserId(loginUserInfo.getUserId());
        if (!ObjectUtils.isEmpty(ecommerceBalance)) {
            balanceInfo.setBalance(ecommerceBalance.getBalance());
        } else {
            // 如果还没有用户余额记录，这里创建出来，余额设定为0即可
            EcommerceBalance newBalance = new EcommerceBalance();
            newBalance.setUserId(loginUserInfo.getUserId());
            newBalance.setBalance(0L);
            newBalance.setCreateTime(LocalDateTime.now());
            newBalance.setUpdateTime(LocalDateTime.now());
            iEcommerceBalanceService.save(newBalance);
            log.info("init user balance record: [{}]", newBalance.getId());
        }
        return balanceInfo;
    }

    @Override
    @Transactional
    public BalanceInfo addBalance(BalanceInfo balanceInfo) {

        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        EcommerceBalance ecommerceBalance =
                addOrDeductBalance(loginUserInfo.getUserId(), balanceInfo, BalanceEnum.ADD.getCode());

        return new BalanceInfo(
                ecommerceBalance.getUserId(), ecommerceBalance.getBalance()
        );
    }

    @Override
    @Transactional
    public BalanceInfo deductBalance(BalanceInfo balanceInfo) {

        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        EcommerceBalance ecommerceBalance =
                addOrDeductBalance(loginUserInfo.getUserId(), balanceInfo, BalanceEnum.DEDUCT.getCode());

        return new BalanceInfo(
                ecommerceBalance.getUserId(), ecommerceBalance.getBalance()
        );
    }

    @Override
    @Transactional
    public BalanceInfo addOtherUserBalance(BalanceInfo balanceInfo) {

        EcommerceBalance ecommerceBalance =
                addOrDeductBalance(balanceInfo.getUserId(), balanceInfo, BalanceEnum.ADD.getCode());
        return new BalanceInfo(
                ecommerceBalance.getUserId(), ecommerceBalance.getBalance()
        );
    }

    @Override
    @Transactional
    public BalanceInfo deductOtherUserBalance(BalanceInfo balanceInfo) {
        // 扣减用户余额的一个基本原则: 扣减额 <= 当前用户余额
        EcommerceBalance ecommerceBalance =
                addOrDeductBalance(balanceInfo.getUserId(), balanceInfo, BalanceEnum.DEDUCT.getCode());

        return new BalanceInfo(
                ecommerceBalance.getUserId(), ecommerceBalance.getBalance()
        );
    }

    private EcommerceBalance addOrDeductBalance(Long userId, BalanceInfo balanceInfo, Integer type) {

        EcommerceBalance ecommerceBalance = ecommerceBalanceMapper.findBalanceByUserId(userId);

        if (type == BalanceEnum.DEDUCT.getCode()){
            if (ObjectUtils.isEmpty(ecommerceBalance) || ecommerceBalance.getBalance() - balanceInfo.getBalance() < 0)
            {
                throw new RuntimeException("user balance is not enough!");
            }
        }

        Long sourceBalance = ecommerceBalance.getBalance();
        if (type == BalanceEnum.ADD.getCode()) {
            ecommerceBalance.setBalance(ecommerceBalance.getBalance() + balanceInfo.getBalance());
        } else if (type == BalanceEnum.DEDUCT.getCode()) {
            ecommerceBalance.setBalance(ecommerceBalance.getBalance() - balanceInfo.getBalance());
        }
        ecommerceBalance.setUpdateTime(LocalDateTime.now());
        iEcommerceBalanceService.saveOrUpdate(ecommerceBalance);
        log.info("[{}] balance: [{}],source: [{}], doHandle: [{}]", BalanceEnum.getMsgByCode(type)
                , ecommerceBalance.getId(), sourceBalance, balanceInfo.getBalance());

        return ecommerceBalance;
    }

}
