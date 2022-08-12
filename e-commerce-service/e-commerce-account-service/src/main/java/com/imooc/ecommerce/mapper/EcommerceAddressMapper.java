package com.imooc.ecommerce.mapper;

import com.imooc.ecommerce.entity.EcommerceAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * <p>
 * 用户地址表 Mapper 接口
 * </p>
 *
 * @author ZZY
 * @since 2022-08-11
 */
@Mapper
@Repository
public interface EcommerceAddressMapper extends BaseMapper<EcommerceAddress> {

    /**
     * 根据用户 id 查询地址信息
     * @param userId
     * @return
     */
    List<EcommerceAddress> findAllByUserId(Long userId);
}
