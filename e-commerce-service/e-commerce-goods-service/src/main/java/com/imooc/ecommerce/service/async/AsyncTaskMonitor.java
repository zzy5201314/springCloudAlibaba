package com.imooc.ecommerce.service.async;

import com.imooc.ecommerce.constant.AsyncTaskStatusEnum;
import com.imooc.ecommerce.vo.asyncTask.AsyncTaskInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * TODO: 异步任务执行监控切面
 *
 * @author zzy
 * @date 2022/8/26
 */
@Slf4j
@Aspect
@Component
public class AsyncTaskMonitor {

    /**
     * TODO: 注入异步任务管理器
     */
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    /**
     * TODO: 异步任务执行的环绕切面
     *
     * @Author : zzy
     * @Date 2022/8/26 0:03
     * @param: proceedingJoinPoint
     * @return: java.lang.Object
     */
    @Around("execution(* com.imooc.ecommerce.service.async.impl.AsyncServiceImpl.*(..)))")
    public Object taskHandle(ProceedingJoinPoint proceedingJoinPoint) {

        /** TODO: 获取 taskId,调用异步任务执行传入的第二个参数 */
        String taskId = proceedingJoinPoint.getArgs()[1].toString();

        /** TODO: 获取任务信息，在提交任务的时候就已经放到容器中了 */
        AsyncTaskInfo taskInfo = asyncTaskManager.getAsyncTaskInfo(taskId);
        log.info("AsyncTaskMonitor is monitoring async task: [{}]", taskId);

        taskInfo.setStatus(AsyncTaskStatusEnum.RUNNING);
        /** TODO: 设置为运行状态，并重新放入容器 */
        asyncTaskManager.setTaskInfo(taskInfo);

        AsyncTaskStatusEnum status;
        Object result;

        try {
            /** TODO: 执行异步任务 */
            result = proceedingJoinPoint.proceed();
            status = AsyncTaskStatusEnum.SUCCESS;

        } catch (Throwable ex) {
            /** TODO: 异步任务出现异常 */
            result = null;
            status = AsyncTaskStatusEnum.FAILED;
            log.error("AsyncTaskMonitor: async task [{}] is failed, Error info : [{}]",
                    taskId, ex.getMessage(), ex
            );
        }

        /** TODO: 设置异步任务其他的信息，并再次重新放入到容器中 */
        taskInfo.setEndTime(new Date());
        taskInfo.setStatus(status);
        taskInfo.setTotalTime(String.valueOf(
                taskInfo.getEndTime().getTime() - taskInfo.getEndTime().getTime()
        ));

        asyncTaskManager.setTaskInfo(taskInfo);

        return result;
    }
}
