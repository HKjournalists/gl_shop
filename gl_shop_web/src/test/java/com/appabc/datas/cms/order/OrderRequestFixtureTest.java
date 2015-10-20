package com.appabc.datas.cms.order;

import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TOrderFind;
import com.appabc.datas.service.order.IOrderFindService;
import com.appabc.datas.cms.service.TaskService;
import com.appabc.datas.cms.vo.task.Task;
import com.appabc.datas.cms.vo.task.TaskType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by zouxifeng on 12/12/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-redis.xml",
        "classpath*:applicationContext-tools.xml",
        "classpath*:applicationContext-datas.xml",
        "classpath*:applicationContext-pay.xml",
        "classpath*:test-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager",defaultRollback=false)
@ActiveProfiles("develop")
@Transactional
public class OrderRequestFixtureTest {

    static {
        System.setProperty("local.config", "classpath:/local-config.properties");
    }

    private final static Logger logger = LoggerFactory.getLogger(OrderRequestFixtureTest.class);

    @Autowired
    private IOrderFindService orderFindService;

    @Autowired
    private TaskService taskService;

    @Test
    public void orderRequestFixture() {
        List<TOrderFind> result = orderFindService.queryNewListForTask();
        for (TOrderFind orderRequest : result) {
            logger.info("Current order request company id is [{}]", orderRequest.getCid());
            Task t = new Task();
            t.setCreateTime(new Date());
            TCompanyInfo c = new TCompanyInfo();
            c.setId(orderRequest.getCid());
            t.setCompany(c);
            t.setObjectId(orderRequest.getId());
            t.setTaskObject(orderRequest);
            t.setType(TaskType.MatchOrderRequest);
            taskService.createTask(t);
        }
    }
}
