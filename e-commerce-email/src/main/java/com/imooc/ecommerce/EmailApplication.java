package com.imooc.ecommerce;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zzy
 * @date 2022/4/4
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class EmailApplication {

    public static void main(String[] args) {

        SpringApplication.run(EmailApplication.class, args);
    }
}
