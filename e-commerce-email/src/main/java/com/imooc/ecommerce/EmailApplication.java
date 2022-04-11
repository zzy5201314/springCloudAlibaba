package com.imooc.ecommerce;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;

/**
 * @author zzy
 * @date 2022/4/4
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class EmailApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(EmailApplication.class);
        Environment env = app.run(args).getEnvironment();
        System.out.println(env.getProperty("spring.application.name")+ "\tinit success;port:\t" +env.getProperty("server.port"));
    }
}
