package com.appabc.datas.cms.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Created by zouxifeng on 5/11/15.
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
public class ServiceLogDaoTest {
    static {
        System.setProperty("local.config", "classpath:/local-config.properties");
    }

    @Autowired
    private UserDao userDao;

    @Autowired
    private ServiceLogDao serviceLogDao;

    @Test
    public void testQuery() {
        /*User root = userDao.findByUserName("root");
        String objectId = "TEST0001";
        for (int i = 0; i < 19; i++) {
            ServiceLog log = new ServiceLog();
            log.setObjectId(objectId);
            log.setOperator(root);
            log.setContent("TEST" + String.valueOf(i));
            log.setType(ServiceLogType.Withdraw);
            log.setCreateTime(new Date());
            serviceLogDao.create(log);
        }
        List<ServiceLog> logs = serviceLogDao.query(objectId, ServiceLogType.Withdraw);
        Assert.assertNotNull(logs);
        Assert.assertTrue(logs.size() == 19);*/
//        User root = userDao.findByUserName("root");
//        String objectId = "TEST0001";
//        for (int i = 0; i < 19; i++) {
//            ServiceLog log = new ServiceLog();
//            log.setObjectId(objectId);
//            log.setOperator(root);
//            log.setContent("TEST" + String.valueOf(i));
//            log.setType(ServiceLogType.Withdraw);
//            log.setCreateTime(new Date());
//            serviceLogDao.create(log);
//        }
//        List<ServiceLog> logs = serviceLogDao.query(objectId, ServiceLogType.Withdraw);
//        Assert.assertNotNull(logs);
//        Assert.assertTrue(logs.size() == 19);
    }
}
