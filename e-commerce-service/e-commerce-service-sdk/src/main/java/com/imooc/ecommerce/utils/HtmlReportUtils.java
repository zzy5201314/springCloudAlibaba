package com.imooc.ecommerce.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * TODO: Html 解析工具所需方法
 *
 * @author zzy
 * @date 2022/9/1
 */
public class HtmlReportUtils {

    private static ThreadLocal<Meta> currentInfo = new ThreadLocal<Meta>();

    private final static String DATE = "yyyy-MM-dd";

    private static Meta meta;

    private static class Meta {
        /**
         * TODO: 左边界
         */
        int left;
        /**
         * TODO: 右边界
         */
        int right;
        /**
         * TODO: 转换后的 html 字符串
         */
        String html;

        public Meta(String html) {
            this.html = html;
            this.left = 0;
            this.right = html.length();
        }
    }

    /**
     * TODO: 滑动窗口，找字符串只能在[left , right]间
     *
     * @Author : zzy
     * @Date 2022/9/1 9:34
     * @param: slideEndStr  查找的字符串
     * @param: closeStr   闭合的字符串（可空）
     * @return: boolean     是否能够找到结果
     */
    public static boolean slideWindow(String slideEndStr, String closeStr) {
        Meta meta = currentInfo.get();
        /** TODO: html 不能为空，查找的字符串不能为空 */
        if (StringUtils.isEmpty(meta.html) || StringUtils.isEmpty(slideEndStr)) {
            return false;
        }
        /** TODO: String.indexOf 从 meta.left 开始找，返回第一次找到的索引 */
        int temp = meta.html.indexOf(slideEndStr, meta.left);
        if (temp < BigDecimal.ZERO.intValue()) {
            return false;
        }
        if (StringUtils.isNotEmpty(closeStr)) {
            int limitIndex = meta.html.indexOf(closeStr, temp);
            if (limitIndex > BigDecimal.ZERO.intValue() && temp < limitIndex) {
                meta.left = temp + slideEndStr.length();
                meta.right = limitIndex;
            } else {
                return false;
            }
        } else {
            if (temp < meta.right) {
                meta.left = temp + slideEndStr.length();
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * TODO: 左滑动（left 能滑则滑否则返回 false），也可以判断窗口内是否还存在某字符串
     *
     * @Author : zzy
     * @Date 2022/9/1 10:22
     * @param: slideStr
     * @return: boolean
     */
    public static boolean leftSlide(String... slideStr) {
        if (slideStr != null) {
            for (String s : slideStr) {
                if (!slideWindow(s, null)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * TODO: 查找两个字符串之间的字符，自带 offset 滑动效果
     *
     * @Author : zzy
     * @Date 2022/9/1 10:35
     * @param: startStr 开始
     * @param: endStr   结束
     * @return: java.lang.String
     */
    public static String getString(String startStr, String endStr) {
        Meta meta = currentInfo.get();
        int tempLeft = meta.left;
        int startIndex = startStr == null ? tempLeft : meta.html.indexOf(startStr, tempLeft);
        int endIndex = meta.html.indexOf(endStr, tempLeft);
        if (startIndex >= BigDecimal.ZERO.intValue() && startIndex < endIndex && endIndex < meta.right) {
            meta.left = endIndex + endStr.length();
            String result = meta.html.substring(startIndex + (startStr == null ? BigDecimal.ZERO.intValue() : startStr.length()), endIndex);
            return result;
        }
        return null;
    }

    /**
     * TODO: 将 html 中的 yyyy.MM.dd 转换成 yyyy-MM-dd
     *
     * @Author : zzy
     * @Date 2022/9/1 11:21
     * @param: timeStr
     * @return: java.lang.String
     */
    public static String coverDotToTime(String timeStr){
        return timeStr.replaceAll("\\.","-");
    }


    public static void main(String[] args) throws IOException {

        /** TODO: eg */
        String html = FileUtils.readFileToString(new File(""),"UTF-8");

    }
}
