package com.imooc.ecommerce.mapper;

import com.imooc.ecommerce.entity.EcommerceBalance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户账户余额表 Mapper 接口
 * </p>
 *
 * @author ZZY
 * @since 2022-08-11
 */
@Mapper
@Repository
public interface EcommerceBalanceMapper extends BaseMapper<EcommerceBalance> {

    /**
     * 根据用户 id 查询 余额记录
     * @return
     */
    EcommerceBalance findBalanceByUserId(Long userId);
}
