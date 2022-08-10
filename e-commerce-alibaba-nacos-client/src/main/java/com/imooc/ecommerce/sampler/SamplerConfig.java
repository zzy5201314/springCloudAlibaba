/*
package com.imooc.ecommerce.sampler;

import brave.sampler.RateLimitingSampler;
import brave.sampler.Sampler;
import org.springframework.cloud.sleuth.sampler.ProbabilityBasedSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

*/
/**
 * TODO: 使用配置的方式设置抽样率,使用配置文件的方式，不需要使用代码的方式
 *
 * @author zzy
 * @date 2022/8/10
 *//*

@Configuration
public class SamplerConfig {

    */
/**
     * 限速采集策略
     * @return
     *//*

    @Bean
    public Sampler sampler(){
        return RateLimitingSampler.create(100);
    }
    */
/**
     * 默认的采样策略，默认值为 0。1，概率采集
     * @return
     *//*

    @Bean
    public Sampler defaultSample(){
        return ProbabilityBasedSampler.create(0.5f);
    }
}
*/
