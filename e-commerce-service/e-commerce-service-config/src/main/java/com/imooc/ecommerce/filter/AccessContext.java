package com.imooc.ecommerce.filter;

import com.imooc.ecommerce.vo.LoginUserInfo;

/**
 * TODO: 使用 ThreadLocal 去单独存储每一个线程存储的 LoginUserInfo 信息
 * 线程安全的，需要及时清理保存到 ThreadLocal 的用户信息；
 * 1.保证没有资源泄露
 * 2.保证线程在重用时，不会出现数据混乱
 *
 * @author zzy
 * @date 2022/8/11
 */
public class AccessContext {

    private static final ThreadLocal<LoginUserInfo> loginUserInfo = new ThreadLocal<>();

    public static LoginUserInfo getLoginUserInfo(){
        return loginUserInfo.get();
    }

    public static void setLoginUserInfo(LoginUserInfo loginUserInfo_){
        loginUserInfo.set(loginUserInfo_);
    }

    public static void clearLoginUserInfo(){
        loginUserInfo.remove();
    }
}
