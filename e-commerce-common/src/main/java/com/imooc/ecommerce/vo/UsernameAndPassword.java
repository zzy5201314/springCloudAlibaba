package com.imooc.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * <h1>用户名和密码</h1>
 *
 * @author zzy
 * @date 2022/4/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsernameAndPassword {

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

}
