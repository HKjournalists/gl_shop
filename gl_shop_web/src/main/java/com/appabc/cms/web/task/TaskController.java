package com.appabc.cms.web.task;

import com.appabc.datas.cms.service.TaskService;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.cms.vo.task.TaskType;
import com.appabc.cms.web.AbstractListBaseController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zouxifeng on 11/24/14.
 */
public class TaskController extends AbstractListBaseController {

    @Autowired
    protected TaskService taskService;

    protected Task claimTask(String taskId, TaskType type) {
        return taskService.claimTask(taskId, getCurrentUser(), type);
    }

    protected Task completeTask(String taskId, TaskType type) {
        return taskService.completeTask(taskId, type);
    }

}
