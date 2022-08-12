package com.imooc.ecommerce.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TODO: 主键 ids
 *
 * @author zzy
 * @date 2022/8/12
 */
@ApiModel(description = "通用 id 对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableId {

    @ApiModelProperty(value = "数据表记录主键")
    private List<ID> ids;

    @ApiModel(description = "数据表记录主键对象")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ID{

        @ApiModelProperty(value = "数据表记录主键")
        private Long id;
    }
}
