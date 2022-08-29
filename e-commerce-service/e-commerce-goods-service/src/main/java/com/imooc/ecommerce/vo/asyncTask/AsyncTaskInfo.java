package com.imooc.ecommerce.vo.asyncTask;

import com.imooc.ecommerce.constant.AsyncTaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * TODO: 标记异步任务执行信息
 *
 * @author zzy
 * @date 2022/8/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsyncTaskInfo {

    /** TODO: 异步任务 id */
    private String taskId;

    /** TODO: 异步任务执行状态 */
    private AsyncTaskStatusEnum status;

    /** TODO: 异步任务开始时间 */
    private Date startTime;

    /** TODO: 异步任务结束时间 */
    private Date endTime;

    /** TODO: 异步任务总耗时 */
    private String totalTime;


}
