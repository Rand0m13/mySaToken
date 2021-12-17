package com.rangamer.mysatoken.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jesse
 * @date 2021年12月17日 15:44
 */
@Api("b站cookie表")
@Data
@TableName("t_bi_cookie")
public class BiCookie implements Serializable {
    private static final long serialVersionUID = 4246266631078448162L;

    @TableId
    @ApiModelProperty(name = "id", value = "主键", dataType = "Long")
    private Long id;

    @TableField("userId")
    private Long userId;

    @TableField("cookie")
    private String cookie;

    @TableField("needUpLevel")
    private Boolean needUpLevel;


}
