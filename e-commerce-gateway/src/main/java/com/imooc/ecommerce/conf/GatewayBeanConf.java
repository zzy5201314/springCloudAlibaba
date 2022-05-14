package com.imooc.ecommerce.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * TODO: 网关需要注册到容器中的 bean
 *
 * @author zzy
 * @date 2022/5/13
 */
@Configuration
public class GatewayBeanConf {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
