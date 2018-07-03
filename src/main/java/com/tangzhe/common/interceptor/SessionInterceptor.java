package com.tangzhe.common.interceptor;

import com.tangzhe.user.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * session超时拦截器
 */
public class SessionInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //请求地址带有login直接放行
        if(request.getRequestURI().contains("login") ||
                request.getRequestURI().contains("sign") ||
                request.getRequestURI().contains("error")) {
            return true;
        }

        //获取session中userInfo
        User user = (User) request.getSession().getAttribute("userInfo");

        //判断user是否为空
        if(user != null) {
            //如果user不为空，放行
            return true;
        }
        //如果user为空，请求转发至登录页面
        request.getRequestDispatcher("/login").forward(request, response);

        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
