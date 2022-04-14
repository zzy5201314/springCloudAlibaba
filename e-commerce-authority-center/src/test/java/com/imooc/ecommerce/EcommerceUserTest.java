package com.imooc.ecommerce;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.ecommerce.entity.TEcommerceUser;
import com.imooc.ecommerce.service.ITEcommerceUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zzy
 * @date 2022/4/12
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class EcommerceUserTest {

    @Autowired
    private ITEcommerceUserService itEcommerceUserService;

    @Test
    public void creatUserRecord(){

//        TEcommerceUser tEcommerceUser = new TEcommerceUser();
//        tEcommerceUser.setUsername("zengzhaoyang");
//        tEcommerceUser.setPassword(MD5.create().digestHex("12345678"));
//        tEcommerceUser.setExtraInfo("{}");
//        tEcommerceUser.setCreateTime(LocalDateTime.now());
//        tEcommerceUser.setUpdateTime(LocalDateTime.now());
//        log.info("save user:[{}]", JSON.toJSON(
//                itEcommerceUserService.save(tEcommerceUser)
//                ));
//        TEcommerceUser tEcommerceUser = new TEcommerceUser();
//        tEcommerceUser.setUserid(Long.parseLong("10"));
//        tEcommerceUser.setUpdateTime(LocalDateTime.now());
//        itEcommerceUserService.removeById(tEcommerceUser.getUserid());
//        QueryWrapper<TEcommerceUser> queryWrapper = new QueryWrapper();
//        queryWrapper.eq("username","zengzhaoyang")
//                .eq("password","25d55ad283aa400af464c76d713c07ad");
//        TEcommerceUser tEcommerceUser = itEcommerceUserService
//                .getOne(queryWrapper);
//        log.info(tEcommerceUser.toString());
        TEcommerceUser tEcommerceUser = new TEcommerceUser();
        tEcommerceUser.setUsername("123");
        // MD5 编码以后
        tEcommerceUser.setPassword(MD5.create().digestHex("123456"));
        tEcommerceUser.setExtraInfo("{}");

        // 注册一个新用户，写一条记录到数据表中
        Boolean register = itEcommerceUserService.save(tEcommerceUser);
        log.info("register user success: [{}],[{}]",tEcommerceUser.getUsername(),tEcommerceUser.getUserid());

    }

}
