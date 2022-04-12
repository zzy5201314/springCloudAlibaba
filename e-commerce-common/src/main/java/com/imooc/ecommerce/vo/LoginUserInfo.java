package com.imooc.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * <h1>登录用户信息</h1>
 *
 * @author zzy
 * @date 2022/4/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserInfo {

    /** 用户的 userid */
    private Long userid;

    /** 用户名 */
    private String username;

}
