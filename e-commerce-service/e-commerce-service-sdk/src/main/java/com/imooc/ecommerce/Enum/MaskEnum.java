package com.imooc.ecommerce.Enum;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * TODO: 脱敏字段枚举
 *
 * @author zzy
 * @date 2022/9/22
 */
@AllArgsConstructor
@NoArgsConstructor
public enum MaskEnum {

    PERSON_NAME(1, "NAME", "个人姓名", "个人姓名转换成客户姓名"),
    PERSON_ID_CARD(1, "ID_CARD", "身份证", "43052119****7336"),
    PERSON_MOBILE_PHONE(1, "MOBILE_PHONE", "手机号码", "157****8759"),
    PERSON_EMAIL(1, "EMAIL", "邮箱", "197****@qq.com"),
    PERSON_BANKCARD(1, "BANKCARD", "银行卡号", "123456****1234"),
    PERSON_ADDRESS(1, "ADDRESS", "居住地址", "个人地址转换成居住地址"),
    PERSON_WORK_ADDRESS(1, "WORK_ADDRESS", "工作地址", "个人工作地址转换成工作地址");


    private int num;
    /***
     * TODO: 字段名称
     *
     * @Author : zzy
     * @Date 2022/9/22 15:04
     */
    private String filed;
    /***
     * TODO: 字段描述
     *
     * @Author : zzy
     * @Date 2022/9/22 15:05
     */
    private String filedMsg;
    /***
     * TODO: 规则定义
     *
     * @Author : zzy
     * @Date 2022/9/22 15:05
     */
    private String maskMsg;

    public String getFiled() {
        return this.filed;
    }

    public String getFiledMsg() {
        return this.filedMsg;
    }

    public String getMaskMsg() {
        return this.maskMsg;
    }

}
