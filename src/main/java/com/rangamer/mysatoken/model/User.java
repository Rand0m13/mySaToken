package com.rangamer.mysatoken.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jesse
 * @date 2021年12月16日 14:07
 * 实体类上的注解 主键交给框架生成，字段名相同@TableField可不配置value
 */
@Api("用户表")
@Data
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = -94859660802450285L;

    @TableId
    @ApiModelProperty(name = "id", value = "主键", dataType = "Long")
    private Long id;

    @TableField
    private String account;

    @TableField
    private String avatar;

    @TableField
    private String phone;

    @TableField
    private String email;

    @TableField
    private String biliCookie;

    @TableField("name")
    private String name;

    @TableField
    private String password;

    @TableField
    private String keySign;

    @TableField
    private Integer age;

    @TableField
    private Date birthday;

    @TableField
    private Date registrationTime;

}
