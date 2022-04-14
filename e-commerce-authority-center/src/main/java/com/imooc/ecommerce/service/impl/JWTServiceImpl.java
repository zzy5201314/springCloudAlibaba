package com.imooc.ecommerce.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.ecommerce.constant.AuthorityConstant;
import com.imooc.ecommerce.constant.CommonConstant;
import com.imooc.ecommerce.entity.TEcommerceUser;
import com.imooc.ecommerce.mapper.TEcommerceUserMapper;
import com.imooc.ecommerce.service.IJWTService;
import com.imooc.ecommerce.service.ITEcommerceUserService;
import com.imooc.ecommerce.vo.LoginUserInfo;
import com.imooc.ecommerce.vo.UsernameAndPassword;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import sun.misc.BASE64Decoder;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.sql.Wrapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


/**
 * JWT 相关服务接口实现
 *
 * @author zzy
 * @date 2022/4/13
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class JWTServiceImpl implements IJWTService {

    @Autowired
    private ITEcommerceUserService itEcommerceUserService;

    @Override
    public String generateToken(String username, String password)
            throws Exception {

        return generateToken(username, password, 0);
    }

    @Override
    public String generateToken(String username, String password, int expire)
            throws Exception {

        // 1.需要验证用户是否能够通过授权校验，即输入的用户名和密码能否匹配数据表记录
        QueryWrapper<TEcommerceUser> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username)
                .eq("password", password);
        TEcommerceUser tEcommerceUser = itEcommerceUserService
                .getOne(queryWrapper);
        if (ObjectUtils.isEmpty(tEcommerceUser)) {
            log.error("can not find user: [{}],[{}]", username, password);
            return null;
        }

        // Token 中塞入对象，即 JWT 中存储的信息，后端拿到这些信息就可以知道是哪个用户在操作
        LoginUserInfo loginUserInfo = new LoginUserInfo(
                tEcommerceUser.getUserid(), tEcommerceUser.getUsername()
        );
        if (expire <= 0) {
            expire = AuthorityConstant.DEFAULT_EXPIRE_DAY;
        }

        // 计算超时时间
        ZonedDateTime zdt = LocalDate.now().plus(expire, ChronoUnit.DAYS)
                .atStartOfDay(ZoneId.systemDefault());
        Date expireDate = Date.from(zdt.toInstant());

        return Jwts.builder()
                // jwt payload --> KV
                .claim(CommonConstant.JWT_USER_INFO_KEY, JSON.toJSONString(loginUserInfo))
                // jwt id
                .setId(UUID.randomUUID().toString())
                // jwt 过期时间
                .setExpiration(expireDate)
                // jwt 签名 --> 加密
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    @Override
    public String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword)
            throws Exception {

        // 先去校验用户名是否存在，如果存在，不能重复注册 （username 是一个唯一键）
        QueryWrapper<TEcommerceUser> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", usernameAndPassword.getUsername());
        TEcommerceUser oldUser = itEcommerceUserService
                .getOne(queryWrapper);
        if (!ObjectUtils.isEmpty(oldUser)) {
            log.error("username is registered: [{}]", oldUser.getUsername());
            return null;
        }

        TEcommerceUser tEcommerceUser = new TEcommerceUser();
        tEcommerceUser.setUsername(usernameAndPassword.getUsername());
        // MD5 编码以后
        tEcommerceUser.setPassword(usernameAndPassword.getPassword());
        tEcommerceUser.setExtraInfo("{}");
        // 设置创建时间为当前时间
        tEcommerceUser.setCreateTime(LocalDateTime.now());
        // 设置更新时间为当前时间
        tEcommerceUser.setUpdateTime(LocalDateTime.now());

        // 注册一个新用户，写一条记录到数据表中
        Boolean register = itEcommerceUserService.save(tEcommerceUser);
        if (register){
            log.info("register user success: [{}],[{}]", tEcommerceUser.getUsername(), tEcommerceUser.getUserid());
        }

        // 生成 Token 并返回
        return generateToken(tEcommerceUser.getUsername(),tEcommerceUser.getPassword());
    }

    /**
     * 根据本地存储的私钥获取到 PrivateKey 对象
     *
     * @return
     * @throws Exception
     */
    private PrivateKey getPrivateKey() throws Exception {

        PKCS8EncodedKeySpec priFKCS8 = new PKCS8EncodedKeySpec(
                new BASE64Decoder().decodeBuffer(AuthorityConstant.PRIVATE_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePrivate(priFKCS8);
    }

}
