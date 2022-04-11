package com.imooc.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <h1>授权中心启动入口</h1>
 *
 * @author zzy
 * @date 2022/4/11
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AuthorityCenterApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthorityCenterApplication.class, args);
    }
}
