package com.imooc.ecommerce.service.async;

import com.imooc.ecommerce.constant.AsyncTaskStatusEnum;
import com.imooc.ecommerce.goods.GoodsInfo;
import com.imooc.ecommerce.vo.asyncTask.AsyncTaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * TODO: 异步任务执行管理器
 * 对异步任务进行包装管理，记录并塞入异步任务执行信息
 *
 * @author zzy
 * @date 2022/8/25
 */
@Slf4j
@Component
public class AsyncTaskManager {

    /**
     * TODO: 异步任务执行信息容器
     */
    private final Map<String, AsyncTaskInfo> taskContainer = new HashMap<>(16);

    @Autowired
    private IAsyncService asyncService;

    /**
     * TODO: 初始化异步任务
     *
     * @Author : zzy
     * @Date 2022/8/25 23:24
     * @return: com.imooc.ecommerce.vo.asyncTask.AsyncTaskInfo
     */
    public AsyncTaskInfo initTask() {

        AsyncTaskInfo taskInfo = new AsyncTaskInfo();
        /** TODO: 设置一个唯一的异步任务 id，只要唯一即可 */
        taskInfo.setTaskId(UUID.randomUUID().toString());
        taskInfo.setStatus(AsyncTaskStatusEnum.STARTED);
        taskInfo.setStartTime(new Date());

        /** TODO: 初始化的时候就要把异步任务执行信息放入到存储容器中 */
        taskContainer.put(taskInfo.getTaskId(), taskInfo);
        return taskInfo;
    }

    /**
     * TODO: 提交异步任务
     *
     * @Author : zzy
     * @Date 2022/8/25 23:38
     * @param: goodsInfos
     * @return: com.imooc.ecommerce.vo.asyncTask.AsyncTaskInfo
     */
    public AsyncTaskInfo submit(List<GoodsInfo> goodsInfos) {

        /** TODO: 初始化一个异步任务的监控信息 */
        AsyncTaskInfo taskInfo = initTask();
        asyncService.asyncImportGoods(goodsInfos, taskInfo.getTaskId());
        return taskInfo;
    }

    /**
     * TODO: 设置异步任务执行状态信息
     *
     * @Author : zzy
     * @Date 2022/8/25 23:45
     * @param: taskInfo
     */
    public void setTaskInfo(AsyncTaskInfo taskInfo) {
        taskContainer.put(taskInfo.getTaskId(), taskInfo);
    }

    /**
     * TODO: 获取异步任务执行状态信息
     *
     * @Author : zzy
     * @Date 2022/8/25 23:46
     * @param: taskId
     * @return: com.imooc.ecommerce.vo.asyncTask.AsyncTaskInfo
     */
    public AsyncTaskInfo getAsyncTaskInfo(String taskId) {
        return taskContainer.get(taskId);
    }
}
