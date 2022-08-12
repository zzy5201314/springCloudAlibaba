package com.imooc.ecommerce.service.impl;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.account.AddressInfo;
import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.entity.EcommerceAddress;
import com.imooc.ecommerce.filter.AccessContext;
import com.imooc.ecommerce.mapper.EcommerceAddressMapper;
import com.imooc.ecommerce.service.IEcommerceAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户地址表 服务实现类
 * </p>
 *
 * @author ZZY
 * @since 2022-08-11
 */
@Slf4j
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class EcommerceAddressServiceImpl extends ServiceImpl<EcommerceAddressMapper, EcommerceAddress> implements IEcommerceAddressService {

    @Autowired
    private IEcommerceAddressService iEcommerceAddressService;
    @Autowired
    private EcommerceAddressMapper ecommerceAddressMapper;

    /**
     * 存储多个地址信息
     *
     * @param addressInfo
     * @return
     */
    @Override
    @Transactional
    public TableId createAddressInfo(AddressInfo addressInfo) {

        // 不能从参数中获取用户的 id 信息
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        // 将传递的参数转换成实体对象
        List<EcommerceAddress> ecommerceAddresses = addressInfo.getAddressItems().stream()
                .map(e -> EcommerceAddress.toEcommerceAddress(loginUserInfo.getUserid(), e))
                .collect(Collectors.toList());

        // 保存到数据表并把返回记录的 id 给调用方
        List<Long> ids = new ArrayList<>();
        if (saveEcommerceAddress(ecommerceAddresses)) {
            ids = findIds(ecommerceAddresses);
            log.info("create address info: [{}],[{}]", loginUserInfo.getUserid(), JSON.toJSONString(ids));
        }

        return new TableId(
                ids.stream().map(TableId.ID::new).collect(Collectors.toList())
        );
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public boolean saveEcommerceAddress(List<EcommerceAddress> ecommerceAddresses){
        return iEcommerceAddressService.saveBatch(ecommerceAddresses);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Long> findIds(List<EcommerceAddress> ecommerceAddresses){
        return iEcommerceAddressService.getBaseMapper().selectBatchIds(
                ecommerceAddresses.stream().map(EcommerceAddress::getId).collect(Collectors.toList())
        )
                .stream()
                .map(EcommerceAddress::getId)
                .collect(Collectors.toList());
    }


    @Override
    public AddressInfo getCurrentAddressInfo() {

        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        // 根据 userId 查询到用户的地址信息，再实现转换
        List<EcommerceAddress> ecommerceAddresses = ecommerceAddressMapper.findAllByUserId(loginUserInfo.getUserid());
        List<AddressInfo.AddressItem> addressItems = ecommerceAddresses.stream()
                .map(EcommerceAddress::toAddressItem)
                .collect(Collectors.toList());


        return new AddressInfo(loginUserInfo.getUserid(), addressItems);
    }

    @Override
    public AddressInfo getAddressInfoById(Long id) {

        EcommerceAddress ecommerceAddresses = iEcommerceAddressService.getById(id);
        if (ObjectUtils.isEmpty(ecommerceAddresses)){
            throw new RuntimeException("address is not exist");
        }

        return new AddressInfo(
                ecommerceAddresses.getUserId(),
                Collections.singletonList(ecommerceAddresses.toAddressItem())
        );
    }

    @Override
    public AddressInfo getAddressInfoByTableId(TableId tableId) {

        List<Long> ids = tableId.getIds().stream()
                .map(TableId.ID::getId)
                .collect(Collectors.toList());

        log.info("get address info by table id: [{}]", JSON.toJSONString(ids));

        List<EcommerceAddress> ecommerceAddresses = iEcommerceAddressService.getBaseMapper().selectBatchIds(ids);
        if (CollectionUtils.isEmpty(ecommerceAddresses)){
            return new AddressInfo(-1L,Collections.emptyList());
        }
        List<AddressInfo.AddressItem>  addressItems = ecommerceAddresses.stream()
                .map(EcommerceAddress::toAddressItem)
                .collect(Collectors.toList());


        return new AddressInfo(
                ecommerceAddresses.get(0).getUserId()
                ,addressItems);
    }
}
