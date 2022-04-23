package com.imooc.ecommerce.filter.factory;

import com.imooc.ecommerce.filter.HeardTokenGatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * @author zzy
 * @date 2022/4/23
 */
@Component
public class HeardTokenGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    @Override
    public GatewayFilter apply(Object config) {
        return new HeardTokenGatewayFilter();
    }
}
