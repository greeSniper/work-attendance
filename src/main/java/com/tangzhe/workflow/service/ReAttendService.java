package com.tangzhe.workflow.service;

import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

public interface ReAttendService {

    void startReAttendFlow(Map varibles);

    List<Task> listTasks(Map varibles);

    void approve(String taskId);

}
