package com.chenwei.zhifou.interceptor;

import com.chenwei.zhifou.dao.LoginTicketDAO;
import com.chenwei.zhifou.dao.UserDAO;
import com.chenwei.zhifou.entity.HostHolder;
import com.chenwei.zhifou.entity.LoginTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/7 23:46
 * @Description:
 */
@Component
public class PassportInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LoginTicketDAO loginTicketDAO;
    @Autowired
    UserDAO userDAO;

    private static final Logger logger = LoggerFactory.getLogger(PassportInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String ticket = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            //如果下面的成立的化就不放到threadlocal中
            if (loginTicket == null || loginTicket.getStatus() != 0 ||
                    loginTicket.getExpired().before
                            (new Date())) {
                return true;
            } else {
                hostHolder.setUser(userDAO.selectById(loginTicket.getUserId()));
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            //每次页面渲染前都把user放到modelAndView中，这样每个页面都有user的信息
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse, Object o,
                                Exception e) throws Exception {
        logger.info("--------------->消灭user");
        hostHolder.clear();
    }
}
