package com.imooc.ecommerce.controller;

import com.imooc.ecommerce.service.SleuthTraceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: 打印跟踪信息
 *
 * @author zzy
 * @date 2022/8/10
 */
@Slf4j
@RestController
@RequestMapping("/sleuth")
public class SleuthTraceInfoController {

    @Autowired
    private SleuthTraceInfoService sleuthTraceInfoService;

    @GetMapping("/trace-info")
    public void logCurrentTraceInfo(){
        sleuthTraceInfoService.logCurrentTraceInfo();
    }
}
