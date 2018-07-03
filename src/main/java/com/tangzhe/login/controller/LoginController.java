package com.tangzhe.login.controller;

import com.tangzhe.common.utils.MD5Utils;
import com.tangzhe.user.entity.User;
import com.tangzhe.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * 用户登录控制层
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 进入登录页面
     */
    @RequestMapping("login")
    public String login() {
        return "login";
    }

    /**
     * 校验登录
     */
    @RequestMapping("/login/check")
    @ResponseBody
    public String checkLogin(String username, String password, HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //调用业务通过用户登录名查询用户
        User user = userService.findByUsername(username);

        //如果用户不存在，则返回login_fail
        if(user == null) {
            return "login_fail";
        }

        //如果页面传入的密码与用户密码不限同，则返回login_fail
        if(!MD5Utils.checkPassword(password, user.getPassword())) {
            return "login_fail";
        }

        //校验通过，将用户放入session中
        request.getSession().setAttribute("userInfo", user);

        //返回登录成功login_success
        return "login_success";
    }

    /**
     * 注册用户，准备数据用，通过postman发送请求
     */
    @RequestMapping("/login/regist")
    @ResponseBody
    public String regist(@RequestBody User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        userService.create(user);
        return "registOk";
    }

}
