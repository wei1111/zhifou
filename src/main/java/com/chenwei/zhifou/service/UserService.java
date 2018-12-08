package com.chenwei.zhifou.service;

import com.chenwei.zhifou.entity.User;

import java.util.Map;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/7 19:38
 * @Description:
 */
public interface UserService {
    public Map<String, Object> register(String username, String password);

    public Map<String, Object> login(String username, String password);

    public void logout(String ticket);

    public User getUser(int id);
}
