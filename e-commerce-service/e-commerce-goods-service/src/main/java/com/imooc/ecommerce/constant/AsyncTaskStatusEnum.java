package com.imooc.ecommerce.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TODO: 异步任务状态枚举
 *
 * @author zzy
 * @date 2022/8/25
 */
@Getter
@AllArgsConstructor
public enum AsyncTaskStatusEnum {

    STARTED(0, "已经启动"),
    RUNNING(1, "正在运行"),
    SUCCESS(2, "执行成功"),
    FAILED(3, "执行失败"),
    ;
    /**
     * TODO: 执行状态编码
     */
    private final int state;

    /**
     * TODO: 执行状态描述
     */
    private final String stateInfo;
}
