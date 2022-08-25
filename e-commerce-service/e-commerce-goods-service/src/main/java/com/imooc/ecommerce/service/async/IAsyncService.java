package com.imooc.ecommerce.service.async;

import com.imooc.ecommerce.goods.GoodsInfo;

import java.util.List;

/**
 * TODO: 异步服务接口定义
 *
 * @author zzy
 * @date 2022/8/25
 */
public interface IAsyncService {

    /**
     * TODO: 异步将商品信息保存下来
     *
     * @Author : zzy
     * @Date 2022/8/25 21:54
     * @param: goodsInfos
     * @param: taskId
     */
    void asyncImportGoods(List<GoodsInfo> goodsInfos, String taskId);
}
