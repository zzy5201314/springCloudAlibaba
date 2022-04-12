package com.imooc.ecommerce;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;

/**
 * <h1>授权中心启动入口</h1>
 *
 * @author zzy
 * @date 2022/4/11
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.imooc.ecommerce.mapper")
public class AuthorityCenterApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(AuthorityCenterApplication.class);
        Environment env = app.run(args).getEnvironment();
        System.out.println(env.getProperty("spring.application.name")+ "\tinit success;port:\t" +env.getProperty("server.port"));
    }
}
