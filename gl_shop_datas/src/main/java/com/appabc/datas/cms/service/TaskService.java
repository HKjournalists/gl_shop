package com.appabc.datas.cms.service;


import com.appabc.bean.enums.PurseInfo.PurseType;
import com.appabc.datas.cms.dao.TaskDao;
import com.appabc.datas.cms.dao.tasks.OrderRequestMeta;
import com.appabc.datas.cms.dao.tasks.UrgeDepositMeta;
import com.appabc.datas.cms.dao.tasks.UrgeVerifyMeta;
import com.appabc.datas.cms.dao.tasks.VerifyInfoMeta;
import com.appabc.datas.cms.vo.Context;
import com.appabc.datas.cms.vo.User;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.cms.vo.task.TaskType;
import com.appabc.datas.dao.company.IAuthRecordDao;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zouxifeng on 11/26/14.
 */
@Service(value="taskService")
public class TaskService {
    @Autowired
    private IAuthRecordDao authRecordDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private VerifyInfoMeta verifyInfoTaskDao;

    public boolean createTask(Task task) {
        return taskDao.create(task);
    }

    public Task getVerifyInfoTask(int taskId) {
        Task t = taskDao.get(taskId, VerifyInfoMeta.INSTANCE);
        return t;
    }

    public Task getOrderRequestTask(String taskId) {
        return taskDao.get(taskId, OrderRequestMeta.INSTANCE);
    }

    public Task getUrgeVerifyTask(String taskId) {
        return taskDao.get(taskId, UrgeVerifyMeta.INSTANCE);
    }
    
    public Task getUrgeDepositTask(String taskId) {
        return taskDao.get(taskId, UrgeDepositMeta.INSTANCE);
    }
    
    public Task completeTask(String taskId, TaskType type) {
        Task task = taskDao.get(taskId, type.getDaoMeta());
        task.setFinished(true);
        task.setFinishTime(new Date());
        taskDao.update(task);
        return task;
    }
    
    public Task releaseTask(String taskId,TaskType type){
    	Task<?> task = taskDao.get(taskId,type.getDaoMeta());
    	task.setOwner(null);
    	task.setFinished(false);
    	task.setFinishTime(null);
    	task.setClaimTime(null);
    	taskDao.update(task);
    	return task;
    }

    public Task claimTask(String taskId, User claimer, TaskType type) {
        Task task = taskDao.get(taskId, type.getDaoMeta());
        task.setOwner(claimer);
        taskDao.update(task);
        return task;
    }

    public List<Task> queryForList(Map<String, Object> params,TaskType type){
    	String orderBy = " ORDER BY CREATE_TIME ASC ";
    	return taskDao.queryForList(params, type.getDaoMeta(), -1, -1, orderBy);
    }
    
    public Context<Task> queryForUnfinished(Context<Task> ctx, TaskType type) {
    	String orderBy = " ORDER BY OWNER DESC, CREATE_TIME ASC ";
        return queryForTask(ctx, type, false, orderBy);
    }

    public Context<Task> queryForFinished(Context<Task> ctx, TaskType type) {
    	String orderBy = " ORDER BY FINISH_TIME DESC ";
        return queryForTask(ctx, type, true, orderBy.toString());
    }
    
    public Context<Task> queryTaskListForFinished(Context<Task> ctx,TaskType type){
    	StringBuilder orderBy = new StringBuilder();
    	String createTime = ctx.getParameters().get("createTime") != null ? (String)ctx.getParameters().get("createTime") : StringUtils.EMPTY;
    	 if(StringUtils.isNotEmpty(createTime)){
     		if(StringUtils.equalsIgnoreCase("ALL", createTime)){
     			orderBy.append(" ORDER BY FIND.CREATIME DESC ");
     		}else{
     			orderBy.append(" ORDER BY FIND.CREATIME "+createTime);
     		}
     	}else{
     		orderBy.append(" ORDER BY TASK.CREATE_TIME DESC ");
     	}
    	return queryTaskForList(ctx, type, true, orderBy.toString());
    } 
    
    public Context<Task> queryTaskListForUnFinished(Context<Task> ctx,TaskType type){
    	StringBuilder orderBy = new StringBuilder();
    	String applyDate = ctx.getParameters().get("applyDate") != null ? (String)ctx.getParameters().get("applyDate") : StringUtils.EMPTY;
    	String creatime = ctx.getParameters().get("creatime") != null ? (String)ctx.getParameters().get("creatime") : StringUtils.EMPTY;
    	if(StringUtils.isNotEmpty(applyDate)){
    		if(StringUtils.equalsIgnoreCase("ALL", applyDate)){
    			orderBy.append(" ORDER BY B.CREATETIME DESC ");
    		}else{
    			orderBy.append(" ORDER BY B.CREATETIME "+applyDate);
    		}
    	}else if(StringUtils.isNotEmpty(creatime)){
    		if(StringUtils.equalsIgnoreCase("ALL", creatime)){
    			orderBy.append(" ORDER BY FIND.CREATIME DESC ");
    		}else{
    			orderBy.append(" ORDER BY FIND.CREATIME "+creatime);
    		}
    	}else{
    		orderBy.append(" ORDER BY TASK.CREATE_TIME DESC ");
    	}
    	return queryTaskForList(ctx, type, false, orderBy.toString());
    }
    
    public Context<Task> queryTaskForList(Context<Task> ctx, TaskType type, boolean isFinished, String orderBy){
    	if (ctx.getParameters() == null) {
            ctx.setParameters(new HashMap<String, Object>());
        }
    	ctx.getParameters().put("taskType", type.getValue());
    	ctx.getParameters().put("taskFinished", isFinished == true ? 1 : 0);
    	ctx.getParameters().put("passType", PurseType.GUARANTY.getVal());
    	
    	List<Task> result = taskDao.queryTaskForList(ctx.getParameters(), ctx.getStart(), ctx.getPageSize(), orderBy);
    	ctx.setTotalRows(taskDao.countTaskForList(ctx.getParameters(), orderBy));
    	ctx.setResult(result);
    	return ctx;
    }

    public Context<Task> queryForTask(Context<Task> ctx, TaskType type, boolean isFinished, String orderBy) {
        if (ctx.getParameters() == null) {
            ctx.setParameters(new HashMap<String, Object>());
        }
        Map<String, Object> params = ctx.getParameters();
        params.put("task.finished", isFinished);
        params.put("task.type", type.getValue());
        
        List<Task> result = taskDao.queryForList(ctx.getParameters(), type.getDaoMeta(),
                ctx.getStart(), ctx.getPageSize(), orderBy);
        ctx.setTotalRows(taskDao.countBy(ctx.getParameters(), type));
        ctx.setResult(result);
        return ctx;
    }

    /**
     * 根据类型和业务ID删除任务
     * @param type
     * @param objectId
     */
    public void delByTypeAndObjectId(TaskType type, String objectId){
    	this.taskDao.delByTypeAndObjectId(type, objectId);
    }

}
