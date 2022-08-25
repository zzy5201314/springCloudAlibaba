package com.imooc.ecommerce.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 用户账户余额表
 * </p>
 *
 * @author ZZY
 * @since 2022-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TEcommerceBalance对象", description="用户账户余额表")
@TableName(value = "t_ecommerce_balance")
public class EcommerceBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键 id")
    @TableId(type = IdType.AUTO)
    private  Long id;

    @ApiModelProperty(value = "用户 id")
    private Long userId;

    @ApiModelProperty(value = "账户余额")
    private Long balance;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
