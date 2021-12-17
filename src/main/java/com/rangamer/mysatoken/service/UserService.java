package com.rangamer.mysatoken.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rangamer.mysatoken.model.User;

public interface UserService extends IService<User> {
    /**
     * 注册用户
     * @param user
     */
    void registerUser(User user);
}
