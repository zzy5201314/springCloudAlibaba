package com.imooc.ecommerce.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zzy
 * @date 2022/4/1
 *
 * <h1>忽略统一响应注解定义</h1>
 * @Target({ElementType.TYPE,ElementType.METHOD})   注解可以标识在哪些地方（类，方法）
 * @Retention(RetentionPolicy.RUNTIME)  标识当前的注解要保存到什么时候（运行时）
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {
}
