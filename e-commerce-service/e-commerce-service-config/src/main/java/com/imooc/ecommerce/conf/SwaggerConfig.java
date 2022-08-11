package com.imooc.ecommerce.conf;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * TODO: Swagger 配置类
 * 原生： /swagger-ui.html
 * 美化： /doc.html
 *
 * @author zzy
 * @date 2022/8/11
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Swagger 实例 bean 是 Docket 所以通过配置 Docket 实例来配置 Swagger
     * @return
     */
    public Docket docket(){

        return new Docket(DocumentationType.SWAGGER_2)
                // 展示在 Swagger 页面上的自定义工程描述信息
                .apiInfo(apiInfo())
                // 选择展示哪些接口
                .select()
                // 只有 com.imooc.ecommerce 包内的才去展示
                .apis(RequestHandlerSelectors.basePackage("com.imooc.ecommerce"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Swagger 的描述信息
     * @return
     */
    public ApiInfo apiInfo(){

        return new ApiInfoBuilder()
                .title("imooc-micro-service")
                .description("e-commerce-springCloud-service")
                .contact(new Contact("Zzy","1971166454@qq.com","1971166454@qq.com"))
                .version("1.0.0")
                .build();
    }
}
