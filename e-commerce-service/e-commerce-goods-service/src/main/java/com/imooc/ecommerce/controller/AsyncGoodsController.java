package com.imooc.ecommerce.controller;

import com.imooc.ecommerce.goods.GoodsInfo;
import com.imooc.ecommerce.service.async.AsyncTaskManager;
import com.imooc.ecommerce.vo.asyncTask.AsyncTaskInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO: 异步任务服务对外提供 API
 *
 * @author zzy
 * @date 2022/8/31
 */
@Api(tags = "商品异步入库服务")
@Slf4j
@RestController
@RequestMapping("/async-goods")
public class AsyncGoodsController {

    @Autowired
    private AsyncTaskManager asyncTaskManager;

    @ApiOperation(value = "导入商品", notes = "导入商品进入到商品表", httpMethod = "POST")
    @PostMapping("/import-goods")
    public AsyncTaskInfo importGoods(@RequestBody List<GoodsInfo> goodsInfos) {
        return asyncTaskManager.submit(goodsInfos);
    }

    @ApiOperation(value = "查询状态", notes = "查询异步商品的执行状态", httpMethod = "GET")
    @GetMapping("/task-info")
    public AsyncTaskInfo getTaskInfo(@RequestParam String taskId) {
        return asyncTaskManager.getAsyncTaskInfo(taskId);
    }

}
