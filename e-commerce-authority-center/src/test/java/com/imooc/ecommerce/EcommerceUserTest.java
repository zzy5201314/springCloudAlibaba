package com.imooc.ecommerce;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
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
        TEcommerceUser tEcommerceUser = new TEcommerceUser();
        tEcommerceUser.setUserid(Long.parseLong("10"));
        itEcommerceUserService.removeById(tEcommerceUser.getUserid());

    }

}
