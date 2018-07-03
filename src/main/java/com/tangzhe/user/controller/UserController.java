package com.tangzhe.user.controller;

import com.tangzhe.user.entity.User;
import com.tangzhe.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户控制层
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 测试通过id查询用户，返回一个页面
     */
    @RequestMapping("/test01")
    public String test01() {
        User user = userService.findById(1L);
        System.out.println(user.getRealName());
        return "test";
    }

    /**
     * 测试插入用户，事务是否正确执行
     */
    @RequestMapping("/test02")
    public void test02() {
        User user1 = new User();
        user1.setMobile("1313453");
        user1.setPassword("1213346");
        user1.setRealName("老唐");
        user1.setUsername("laotang");

        User user2 = new User();
        user2.setId(1L);
        user2.setMobile("1545353");
        user2.setPassword("123123");
        user2.setRealName("老张");
        user2.setUsername("laozhang");

        userService.testCreate(user1, user2);
    }

    /**
     * 进入首页
     */
    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    /**
     * 获取登录用户信息
     */
    @RequestMapping("/userinfo")
    @ResponseBody
    public User getUserinfo(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("userInfo");
        return user;
    }

    /**
     * 登出系统
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

}














