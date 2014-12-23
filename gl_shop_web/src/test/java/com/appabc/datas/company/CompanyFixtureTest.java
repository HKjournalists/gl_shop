/*package com.appabc.datas.company;

import com.appabc.bean.enums.AuthRecordInfo;
import com.appabc.bean.enums.UserInfo;
import com.appabc.bean.pvo.TAuthRecord;
import com.appabc.bean.pvo.TCompanyInfo;
import com.appabc.bean.pvo.TUser;
import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.company.ICompanyInfoService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.datas.system.Task;
import com.appabc.datas.system.TaskService;
import com.appabc.datas.system.TaskType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

*//**
 * Created by zouxifeng on 11/27/14.
 *//*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-redis.xml",
        "classpath*:applicationContext-tools.xml",
        "classpath*:applicationContext-datas.xml",
        "classpath*:applicationContext-pay.xml",
        "classpath*:test-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager",defaultRollback=false)
@ActiveProfiles("develop")
@Transactional
public class CompanyFixtureTest {

    static {
        System.setProperty("local.config", "classpath:/local-config.properties");
    }

    @Autowired
    IUserService userService;
    @Autowired
    ICompanyInfoService companyInfoService;
    @Autowired
    TaskService taskService;

    @Test
    public void compFixture(){
    	
    }
    
    public void companyFixture() {
        int size = 149;
        long ts = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            long id = Math.round(Math.random() * ts);
            TUser u = new TUser();
            u.setNick("江苏国立user" + id);
            u.setUsername("user" + id);
            u.setPassword("12345678");
            u.setPhone("1390000" + i);
            u.setCname("江苏国立user" + i);
            u.setCreatedate(new Date());
            u.setClienttype(UserInfo.ClientTypeEnum.CLIENT_TYPE_ANDROID);
            u.setClientid("android123");
            userService.register(u);
            TCompanyInfo c = companyInfoService.query(u.getCid());
            TAuthRecord a = new TAuthRecord();
            a.setCid(c.getId());
            a.setAuthstatus(AuthRecordInfo.AuthRecordStatus.AUTH_STATUS_CHECK_ING);
            try {
                companyInfoService.authApply(c, a, "123456", u.getId());
            } catch (ServiceException e) {
                e.printStackTrace();
            }

            Task t = new Task();
            t.setObjectId(a.getId());
            t.setCompany(c);
            t.setType(TaskType.VerifyInfo);
            t.setClaimTime(new Date());
            t.setFinished(false);
            t.setCreateTime(new Date());
            taskService.createTask(t);
        }
    }


}
*/