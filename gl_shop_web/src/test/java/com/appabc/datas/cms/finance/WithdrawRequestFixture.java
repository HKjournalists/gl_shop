package com.appabc.datas.cms.finance;

import com.appabc.bean.enums.PurseInfo;
import com.appabc.pay.bean.TPassbookDraw;
import com.appabc.pay.service.IPassPayService;
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
 * Created by zouxifeng on 5/7/15.
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
public class WithdrawRequestFixture {

    static {
        System.setProperty("local.config", "classpath:/local-config.properties");
    }

    @Autowired
    private IPassPayService passPayService;

    @Test
    public void withdrawRequestFixture() {
        for (int i = 0; i < 15; i++) {
            TPassbookDraw res = passPayService.extractCashRequest("241220140000030",
                    PurseInfo.PurseType.GUARANTY, Double.valueOf(100.0 + i),
                    "201412030000015101813");
            System.out.println(res.getStatus().getText());
        }

        for (int i = 0; i < 10; i++) {
            TPassbookDraw res = passPayService.extractCashRequest("241220140000030",
                    PurseInfo.PurseType.DEPOSIT, Double.valueOf(100.0 + i),
                    "201412030000015101813");
            System.out.println(res.getStatus().getText());
        }
    }
}
