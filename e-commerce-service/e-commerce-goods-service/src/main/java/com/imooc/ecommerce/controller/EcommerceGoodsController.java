package com.imooc.ecommerce.controller;


import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.goods.DeductGoodsInventory;
import com.imooc.ecommerce.goods.GoodsInfo;
import com.imooc.ecommerce.goods.SimpleGoodsInfo;
import com.imooc.ecommerce.service.IEcommerceGoodsService;
import com.imooc.ecommerce.vo.PageSimpleGoodsInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author ZZY
 * @since 2022-08-25
 */
@Api(tags = "商品微服务功能接口")
@RestController
@RequestMapping("/goods")
public class EcommerceGoodsController {

    @Autowired
    private IEcommerceGoodsService iEcommerceGoodsService;

    @ApiOperation(value = "详细商品信息", notes = "根据 TableId 查询详细的商品信息", httpMethod = "POST")
    @PostMapping("/goods-info")
    public List<GoodsInfo> getGoodsInfoByTableId(@RequestBody TableId tableId) {
        return iEcommerceGoodsService.getGoodsInfoByTableId(tableId);
    }

    @ApiOperation(value = "简单商品信息", notes = "获取简单的商品信息", httpMethod = "GET")
    @GetMapping("/page-simple-goods-info")
    public PageSimpleGoodsInfo getSimpleGoodsInfoByPage(@RequestParam(required = false, defaultValue = "1") int page) {
        return iEcommerceGoodsService.getSimpleGoodsInfoByPage(page);
    }

    @ApiOperation(value = "简单商品描述信息",notes = "根据 TableId 去查询简单商品信息",httpMethod = "POST")
    @PostMapping("/simple-goods-info")
    public List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(@RequestBody TableId tableId){
        return iEcommerceGoodsService.getSimpleGoodsInfoByTableId(tableId);
    }

    @ApiOperation(value = "扣减商品库存",notes = "扣减商品库存",httpMethod = "PUT")
    @PutMapping("/deduct-goods-inventory")
    public Boolean deductGoodsInventory(@RequestBody List<DeductGoodsInventory> deductGoodsInventories){
        return iEcommerceGoodsService.deductGoodsInventory(deductGoodsInventories);
    }

}
