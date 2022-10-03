package com.spider_net.config.Intercept;

import com.spider_net.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


public class LoginIntercept implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            User user = (User)request.getSession().getAttribute("Login::User");
            if (Objects.isNull(user)){
                response.sendRedirect("/manage/login");
                return false;
            }
            return true;
        }catch (Exception e){
            response.sendRedirect("/manage/login");
        }
        return false;
    }
}
