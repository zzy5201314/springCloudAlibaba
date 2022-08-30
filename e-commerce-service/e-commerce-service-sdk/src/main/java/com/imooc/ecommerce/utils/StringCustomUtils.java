package com.imooc.ecommerce.utils;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;


/**
 * TODO: 字符串操作类
 *
 * @author zzy
 * @date 2022/8/30
 */
public class StringCustomUtils extends org.apache.commons.lang3.StringUtils {

    private static Pattern pattern = Pattern.compile("[0-9]*");

    /**
     * TODO: 比较值
     *
     * @Author : zzy
     * @Date 2022/8/30 11:48
     * @param: str1
     * @param: str2
     * @return: int
     */
    public static int compare(String str1, String str2) {
        if (str1 == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        int c = str1.compareTo(str2);
        return c == 0 ? 0 : (c < 0 ? -1 : 1);
    }

    /**
     * TODO: 判断是否为数字或者小数
     *
     * @Author : zzy
     * @Date 2022/8/30 11:44
     * @param: str
     * @return: boolean
     */
    public static boolean isNumeric(String str){
        if (str.indexOf(".") > 0) {
            if (str.indexOf(".") == str.lastIndexOf(".") && str.split("\\.").length == 2) {
                return pattern.matcher(str.replace(".", "")).matches();
            } else {
                return false;
            }
        } else {
            return pattern.matcher(str).matches();
        }
    }
}
