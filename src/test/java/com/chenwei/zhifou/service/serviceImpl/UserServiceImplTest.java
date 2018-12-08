package com.chenwei.zhifou.service.serviceImpl;

import com.chenwei.zhifou.ZhifouApplication;
import com.chenwei.zhifou.entity.User;
import com.chenwei.zhifou.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZhifouApplication.class)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void getUser() {
        User user = userService.getUser(2);
        Assert.assertNotNull(user);
    }

}