package com.imooc.ecommerce.Enum;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: 当前用户扣减还是增加余额枚举
 *
 * @author zzy
 * @date 2022/8/12
 */
public enum  BalanceEnum {

    ADD(1,"增加"),
    DEDUCT(2,"减少");

    private int code;
    private String msg;

    private BalanceEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    private static Map<Integer, BalanceEnum> map = new HashMap<>();
    static {
        for (BalanceEnum e : BalanceEnum.values()) {
            map.put(e.code, e);
        }
    }

    public static String getMsgByCode(Integer code) {
        BalanceEnum be = map.get(code);
        if (be != null) {
            return be.getMsg();
        }
        return null;
        //throw new ServiceException(ErrCodeEnum.UNKNOW_MODULE_DESC.getCode(), moduleDesc);
    }
}
