package com.appabc.datas.system;


import com.appabc.datas.dao.company.IAuthRecordDao;
import com.appabc.datas.system.dao.AbstractTaskDao;
import com.appabc.datas.system.dao.VerifyInfoTaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zouxifeng on 11/26/14.
 */
@Service
public class TaskService {
    @Autowired
    private IAuthRecordDao authRecordDao;

    @Autowired
    private VerifyInfoTaskDao verifyInfoTaskDao;

    public boolean createTask(Task task) {
        return verifyInfoTaskDao.create(task);
    }

    public void completeTask(Task task) {
        task.setFinished(true);
        task.setFinishTime(new Date());
        verifyInfoTaskDao.update(task);
    }

    public Context<Task> queryForUnfinished(Context<Task> ctx, TaskType type) {
        switch (type) {
            case VerifyInfo:
                Map<String, Object> params = new HashMap<>();
                params.put(AbstractTaskDao.FIELD_FINISHED, false);
                ctx.setParameters(params);
                return queryForVerifyInfo(ctx);
        }
        return ctx;
    }

    private Context<Task> queryForVerifyInfo(final Context<Task> ctx) {
        List<Task> result = verifyInfoTaskDao.queryForList(ctx.getParameters(), ctx.getStart(), ctx.getPageSize(), null);
        ctx.setTotalRows(verifyInfoTaskDao.countBy(ctx.getParameters()));
        ctx.setResult(result);
        return ctx;
    }

    public Task getVerifyInfoTask(int taskId) {
        Task t = verifyInfoTaskDao.get(taskId);
        return t;
    }
}
