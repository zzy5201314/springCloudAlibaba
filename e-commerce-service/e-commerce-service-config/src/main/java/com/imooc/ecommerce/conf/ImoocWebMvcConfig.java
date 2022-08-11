package com.imooc.ecommerce.conf;

import com.imooc.ecommerce.filter.LoginUserInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * TODO: Web Mvc 配置
 *
 * @author zzy
 * @date 2022/8/11
 */
@Configuration
public class ImoocWebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 添加拦截器配置，定义好拦截器后不会自动生效，需要加入拦截器配置
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        // 添加用户身份统一登录拦截的拦截器
        registry.addInterceptor(new LoginUserInfoInterceptor()).addPathPatterns("/**").order(0);
    }
}
