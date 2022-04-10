package com.imooc.ecommerce.service.impl;

import com.imooc.ecommerce.service.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author zzy
 * @date 2022/4/5
 */
@Slf4j
@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    // 注入常量（从哪里发送） -- A_Sword_TenStpes@163.com
//    @Value("${spring.mail.username}")
    private final String from = "A_Sword_TenStpes@163.com";

    /**
     * 发送文本邮件
     * @param toAddr    发送地址
     * @param title     标题
     * @param content   内容
     */
    @Override
    public void sendTextEmail(String toAddr, String title, String content) {
        // 纯文本邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(toAddr);
        message.setSubject(title);
        message.setText(content);

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("发送[Text]邮件时发生异常：[{}]", e);
        }

    }

    /**
     * 发送html邮件
     * @param toAddr    发送地址
     * @param title     标题
     * @param content   内容
     */
    @Override
    public void sendHtmlEmail(String toAddr, String title, String content) {
        // html 邮件对象
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toAddr);
            helper.setSubject(title);
            helper.setText(content, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("发送[Html]邮件时发生异常：[{}]", e);
        }
    }


    /**
     * 发送带附件的邮件
     * @param toAddr    发送地址
     * @param title     标题
     * @param content   内容
     * @param filePath  文件路径
     */
    @Override
    public void sendAttachmentsEmail(String toAddr, String title, String content, String filePath){
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toAddr);
            helper.setSubject(title);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("发送[附件]邮件时发生异常：[{}]", e);
        }
    }


    /**
     * 发送正文中有静态资源（图片）的邮件
     * @param toAddr    发送地址
     * @param title     标题
     * @param content   内容
     * @param rscPath
     * @param rscId
     */
    @Override
    public void sendInlineResourceEmail(String toAddr, String title, String content, String rscPath, String rscId){
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(toAddr);
            helper.setSubject(title);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("发送[静态资源]邮件时发生异常：[{}]", e);
        }
    }
}
