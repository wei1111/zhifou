package com.chenwei.zhifou.interceptor;

import com.chenwei.zhifou.entity.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/8 1:25
 * @Description:
 */
@Component
public class LoginRequiredInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    HostHolder hostHolder;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if (hostHolder.getUser() == null) {
            response.sendRedirect("reglogin?next=" + request.getRequestURI());
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}
