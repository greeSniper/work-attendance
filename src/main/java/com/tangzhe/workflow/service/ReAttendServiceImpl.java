package com.tangzhe.workflow.service;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 补签工作流业务
 */
@Service("reAttendService")
public class ReAttendServiceImpl implements ReAttendService {

    private static final String RE_ATTEND_FLOW_ID = "re_attend";

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    /**
     * 组员开始补签工作流
     */
    public void startReAttendFlow(Map varibles) {
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(RE_ATTEND_FLOW_ID, varibles);
        System.out.println(instance.getId() + "-----" + instance.getActivityId());

        Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
        System.out.println((task.getId() + "-----" + task.getName()));

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("attend_morning", "09:00");
        map.put("attend_evening", "18:30");
        taskService.complete(task.getId(), map);
    }

    /**
     * 组长获取补签工作流
     */
    public List<Task> listTasks(Map varibles) {
        List<Task> taskList = taskService.createTaskQuery().taskDefinitionKey("re_attend_approve").active().list();
        Map<String, Object> param = taskService.getVariables(taskList.get(0).getId());
        return taskList;
    }

    /**
     * 提交补签工作流
     */
    public void approve(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        taskService.complete(taskId);
    }

}
