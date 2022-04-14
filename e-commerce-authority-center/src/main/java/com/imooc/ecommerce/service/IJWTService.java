package com.imooc.ecommerce.service;

import com.imooc.ecommerce.vo.UsernameAndPassword;

/**
 * <h1> JWT 相关服务接口定义</h1>
 *
 * @author zzy
 * @date 2022/4/13
 */
public interface IJWTService {

    /**
     *
     * 生成 JWT Token，使用默认的超时时间
     *
     * @param username
     * @param password
     * @return
     */
    String generateToken(String username, String password) throws Exception;

    /**
     *
     * 生成指定超时时间的 Token，单位是天
     *
     * @param username
     * @param password
     * @param expire
     * @return
     * @throws Exception
     */
    String generateToken(String username, String password,int expire) throws Exception;

    /**
     *
     * 注册用户并生成 Token 返回
     *
     * @param usernameAndPassword
     * @return
     * @throws Exception
     */
    String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception;
}
