package com.imooc.ecommerce.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * TODO: 自定义异步任务线程池， 异步任务异常捕获处理器
 *
 * @author zzy
 * @date 2022/8/25
 */
@Slf4j
@EnableAsync
@Configuration
public class AsyncPoolConfig implements AsyncConfigurer {

    /**
     * TODO: 将自定义的线程池注入到 Spring 容器中
     *
     * @Author : zzy
     * @Date 2022/8/25 22:01
     * @return: java.util.concurrent.Executor
     */
    @Override
    @Bean(name = "Goods-Async")
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        /** 核心线程数 */
        executor.setCorePoolSize(10);
        /** 最大线程数 */
        executor.setMaxPoolSize(20);
        /** 队列大小 */
        executor.setQueueCapacity(20);
        /** 线程最大空闲时间 */
        executor.setKeepAliveSeconds(60);
        /** 指定用于新创建的线程名称的前缀 */
        executor.setThreadNamePrefix("Goods-Async-");

        /** 等待所有任务结果后再关闭线程池 */
        executor.setWaitForTasksToCompleteOnShutdown(true);
        /** 设置线程池中的任务等待时间，如果任务的执行时间超过，关闭线程，（防止阻塞），确保任务最后关闭 */
        executor.setAwaitTerminationSeconds(60);
        /** 定义拒绝策略
         ThreadPoolExecutor.AbortPolicy 丢弃任务并抛出RejectedExecutionException异常(默认)。
         ThreadPoolExecutor.DiscardPolic 丢弃任务，但是不抛出异常。
         ThreadPoolExecutor.DiscardOldestPolicy 丢弃队列最前面的任务，然后重新尝试执行任务
         ThreadPoolExecutor.CallerRunsPolic 由调用线程处理该任务
         */
        executor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        /** 初始化线程池 */
        executor.initialize();

        return executor;
    }

    /***
     * TODO: 指定系统中异步任务在出现异常时使用到的处理器
     *
     * @Author : zzy
     * @Date 2022/8/25 22:13
     * @return: org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

    /**
     * TODO: 异步任务异常捕获处理器
     *
     * @Author : zzy
     * @Date 2022/8/25 22:14
     */
    class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

        @Override
        public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {

            throwable.printStackTrace();
            log.error("Async Error: [{}]，MethodL: [{}], Param: [{}]",
                    throwable.getMessage(), method.getName(),
                    JSON.toJSON(objects));

            /** TODO 发送邮件或者短信，做进一步的报警处理 */

        }
    }
}
