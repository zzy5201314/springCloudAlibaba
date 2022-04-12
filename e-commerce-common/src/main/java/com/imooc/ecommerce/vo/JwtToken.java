package com.imooc.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * <h1>授权中心鉴权之后给客户端的 Token</h1>
 *
 * @author zzy
 * @date 2022/4/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {

    /** JWT 方便将来扩展 */
    private String token;

}
