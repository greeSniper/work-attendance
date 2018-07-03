package com.tangzhe.workflow.controller;

import com.tangzhe.workflow.service.ReAttendService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 补签工作流控制器
 */
@Controller
@RequestMapping("reAttend")
public class ReAttendController {

    @Autowired
    private ReAttendService reAttendService;

    /**
     * 组员开始补签工作流
     */
    @RequestMapping("/start")
    public void startReAttendFlow(Map varibles) {
        reAttendService.startReAttendFlow(varibles);
    }

    /**
     * 组长获取补签工作流
     */
    @RequestMapping("/list")
    public List<Task> listReAttendFlow(Map varibles) {
        List<Task> tasks = reAttendService.listTasks(varibles);
        return tasks;
    }

    /**
     * 提交补签工作流
     */
    @RequestMapping("/approve/{taskId}")
    public void approveReAttendFlow(@PathVariable String taskId) {
        reAttendService.approve(taskId);
    }

}
