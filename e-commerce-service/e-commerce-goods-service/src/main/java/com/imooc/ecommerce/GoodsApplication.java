package com.imooc.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;

/**
 * TODO: 商品微服务启动入口
 * 启动依赖组件/中间件：Redis + Mysql + Nacos + Kafka + Zipkin
 * http://127.0.0.1:8001/ecommerce-goods-service/doc.html
 *
 * @author zzy
 * @date 2022/8/24
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GoodsApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(GoodsApplication.class);
        Environment env = app.run(args).getEnvironment();
        System.out.println(env.getProperty("spring.application.name")+ "\tinit success;port:\t" +env.getProperty("server.port"));
    }
}
