package com.chenwei.zhifou.entity;

import org.springframework.stereotype.Component;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/7 23:49
 * @Description: 做一个线程共享的线程安全的身份
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }
}
