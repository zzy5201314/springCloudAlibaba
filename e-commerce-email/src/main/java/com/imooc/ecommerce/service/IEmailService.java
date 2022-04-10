package com.imooc.ecommerce.service;



/**
 * @author zzy
 * @date 2022/4/5
 */
public interface IEmailService {

    /**
     * 发送文本邮件
     *
     * @param toAddr  发送地址
     * @param title   标题
     * @param content 内容
     */
    void sendTextEmail(String toAddr, String title, String content);

    /**
     * 发送html邮件
     *
     * @param toAddr  发送地址
     * @param title   标题
     * @param content 内容
     */
    void sendHtmlEmail(String toAddr, String title, String content);


    /**
     * 发送带附件的邮件
     *
     * @param toAddr   发送地址
     * @param title    标题
     * @param content  内容
     * @param filePath 文件路径
     */
    void sendAttachmentsEmail(String toAddr, String title, String content, String filePath);


    /**
     * 发送正文中有静态资源（图片）的邮件
     *
     * @param toAddr  发送地址
     * @param title   标题
     * @param content 内容
     * @param rscPath
     * @param rscId
     */
    void sendInlineResourceEmail(String toAddr, String title, String content, String rscPath, String rscId);
}
