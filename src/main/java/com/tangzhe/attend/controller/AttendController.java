package com.tangzhe.attend.controller;

import com.tangzhe.attend.entity.Attend;
import com.tangzhe.attend.service.AttendService;
import com.tangzhe.attend.vo.QueryCondition;
import com.tangzhe.common.page.PageQueryBean;
import com.tangzhe.user.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 考勤控制层
 */
@Controller
@RequestMapping("attend")
public class AttendController {

    @Autowired
    private AttendService attendService;

    /**
     * 进入考勤页面
     */
    @RequestMapping
    public String toAttend() {
        return "attend";
    }

    /**
     * 模拟打卡机添加打卡记录
     */
    @RequestMapping("/sign")
    @ResponseBody
    public String signAttend(@RequestBody Attend attend) throws Exception {
        attendService.signAttend(attend);
        return "sign_success";
    }

    /**
     * 分页查询考勤数据
     */
    @RequestMapping("/attendList")
    @ResponseBody
    public PageQueryBean listAttend(QueryCondition condition, HttpSession session) {
        User user = (User) session.getAttribute("userInfo");
        String [] rangeDate = condition.getRangeDate().split("/");
        condition.setStartDate(rangeDate[0]);
        condition.setEndDate(rangeDate[1]);
        condition.setUserId(user.getId());
        PageQueryBean pageQueryBean = attendService.listAttend(condition);
        return pageQueryBean;
    }

}


















