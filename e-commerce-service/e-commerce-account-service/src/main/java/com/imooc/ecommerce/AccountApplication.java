package com.imooc.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;

/**
 * TODO: 用户账户微服务启动入口
 * 127.0.0.1:8003/ecommerce-account-center/doc.html
 *
 * @author zzy
 * @date 2022/8/11
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AccountApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(AccountApplication.class);
        Environment env = app.run(args).getEnvironment();
        System.out.println(env.getProperty("spring.application.name")+ "\tinit success;port:\t" +env.getProperty("server.port"));
    }
}
