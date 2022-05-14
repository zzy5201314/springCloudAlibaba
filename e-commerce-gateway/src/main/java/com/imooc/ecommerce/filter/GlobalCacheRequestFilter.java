package com.imooc.ecommerce.filter;

import com.imooc.ecommerce.constant.GatewayConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 *
 * 缓存请求 body 的全局过滤器
 * Spring WebFlux
 *
 * @author zzy
 * @date 2022/4/23
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class GlobalCacheRequestFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        boolean isLoginOrRegister =
                exchange.getRequest().getURI().getPath().contains(GatewayConstant.LOGIN_URL)
                || exchange.getRequest().getURI().getPath().contains(GatewayConstant.REGISTER_URL);

        if (null == exchange.getRequest().getHeaders().getContentType() || !isLoginOrRegister){
            return chain.filter(exchange);
        }

        // DataBufferUtils.join 拿到请求中的数据 --> DataBuffer
        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {

            // 确保数据缓冲去不被释放，必须要 DataBufferUtils.retain()
            DataBufferUtils.retain(dataBuffer);
            // defer just 都是去创建数据源，得到当前数据的副本
            Flux<DataBuffer> cacheFlux = Flux.defer(()->
                    // 从第 0 个开始 读取到最后
                    Flux.just(dataBuffer.slice(0,dataBuffer.readableByteCount())));
            // 重新包装 ServerHttpRequest,重写 getBody 方法，能够返回请求数据
            // import org.springframework.http.server.reactive.ServerHttpRequest;
            ServerHttpRequest mutateRequest =new ServerHttpRequestDecorator(exchange.getRequest()){
                @Override
                public Flux<DataBuffer> getBody() {
                    return cacheFlux;
                }
            };
            // 将包装之后的 ServerHttpRequest 继续向下传递
            return chain.filter(exchange.mutate().request(mutateRequest).build());
        });
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 1;
    }
}
