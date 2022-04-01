package com.imooc.ecommerce.vo;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zzy
 * @date 2022/4/1
 * <h1>通用相应对象定义</h1>
 * {
 *     "code": 0,
 *     "message": "",
 *     "data": {}
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {

    /** 错误码 */
    private Integer code;

    /** 错误消息 */
    private String message;

    /** 泛型相应数据 */
    private T data;

    public CommonResponse(Integer code, String message){

        this.code = code;
        this.message = message;
    }

}
