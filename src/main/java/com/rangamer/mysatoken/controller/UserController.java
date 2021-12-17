package com.rangamer.mysatoken.controller;

import cn.hutool.core.exceptions.ValidateException;
import com.rangamer.mysatoken.base.SingleResult;
import com.rangamer.mysatoken.mapper.UserMapper;
import com.rangamer.mysatoken.model.User;
import com.rangamer.mysatoken.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jesse
 * @date 2021年12月16日 14:18
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "用户管理")
public class UserController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @ApiOperation(value = "注册用户")
    @PostMapping("/register")
    public SingleResult<String> register(@RequestBody User user) {
        SingleResult<String> result = new SingleResult<>();
        String msg;
        try {
            this.userService.registerUser(user);
            msg = "用户注册成功！";
            result.setSuccess(true);
            log.info("用户注册成功");
        } catch (ValidateException e) {
            msg = e.getMessage();
            result.setSuccess(false);
            log.info("用户注册失败！->{}", e.getMessage());
        } catch (Exception e) {
            msg = "用户注册失败！";
            result.setSuccess(false);
            log.info("用户注册失败！->{}", e.getMessage());
        }
        result.setMessage(msg);
        return result;
    }
}
