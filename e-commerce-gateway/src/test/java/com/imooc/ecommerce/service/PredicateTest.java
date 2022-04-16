package com.imooc.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * java8 Predicate 使用方法与思想
 *
 * @author zzy
 * @date 2022/4/16
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PredicateTest {

    public static List<String> MICRO_SERVICE = Arrays.asList(
            "nacos", "authority", "gateway", "ribbon", "feign", "hystrix", "e-commerce"
    );

    /**
     * test 方法主要用于参数符不符合规则，返回 Boolean 值
     */
    @Test
    public void testPredicateTest() {

        // java 8
        Predicate<String> letterLengthLimit = s -> s.length() > 5;
        MICRO_SERVICE.stream().filter(letterLengthLimit).forEach(System.out::println);
    }

    /**
     * And 方法
     */
    @Test
    public void testPredicateAnd() {

        // java 8
        Predicate<String> letterLengthLimit = s -> s.length() > 5;
        Predicate<String> letterStartWith = s -> s.startsWith("gate");

        MICRO_SERVICE.stream().filter(
                letterLengthLimit.and(letterStartWith)
        ).forEach(System.out::println);
    }

    /**
     * Or方法
     */
    @Test
    public void testPredicateOr() {

        // java 8
        Predicate<String> letterLengthLimit = s -> s.length() > 5;
        Predicate<String> letterStartWith = s -> s.startsWith("gate");

        MICRO_SERVICE.stream().filter(
                letterLengthLimit.or(letterStartWith)
        ).forEach(System.out::println);
    }

    /**
     * Negate 等同于逻辑非
     */
    @Test
    public void testPredicateNegate() {

        // java 8
        Predicate<String> letterLengthLimit = s -> s.length() > 5;
        Predicate<String> letterStartWith = s -> s.startsWith("gate");

        MICRO_SERVICE.stream().filter(
                letterStartWith.negate()
        ).forEach(System.out::println);
    }

    /**
     * IsEqual 类似于 equals
     * 区别在于：先判断对象是否为 NULL ，不为 NULL 再使用
     */
    @Test
    public void testPredicateIsEqual() {

        // java 8
        Predicate<String> equalGateway = s -> Predicate.isEqual("gateway").test(s);

        MICRO_SERVICE.stream().filter(equalGateway).forEach(System.out::println);
    }

}
