package com.imooc.ecommerce.service;

import brave.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO: 使用代码更直观的看到 Sleuth 生成的相关跟踪信息
 * 无含义，只是为了直观展示
 *
 * @author zzy
 * @date 2022/8/10
 */
@Slf4j
@Service
public class SleuthTraceInfoService {

    // 注意是在 brave.Tracer 跟踪对象 包下
    @Autowired
    private Tracer tracer;

    /**
     * 打印当前的跟踪信息到日志中
     */
    public void logCurrentTraceInfo() {

        log.info("Sleuth trace id :[{}]", String.format("%08x", tracer.currentSpan().context().traceId()));
        log.info("Sleuth span id :[{}]", String.format("%08x", tracer.currentSpan().context().spanId()));
    }


}
