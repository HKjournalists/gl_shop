package com.appabc.datas.cms.finance;

import com.appabc.bean.enums.AcceptBankInfo;
import com.appabc.bean.pvo.TUser;
import com.appabc.datas.service.company.IAcceptBankService;
import com.appabc.datas.service.user.IUserService;
import com.appabc.pay.bean.TAcceptBank;
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
 * Created by zouxifeng on 5/12/15.
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
public class BankAccountFixture {

    static {
        System.setProperty("local.config", "classpath:/local-config.properties");
    }

    @Autowired
    private IAcceptBankService acceptBankService;

    @Autowired
    private IUserService userService;

    @Test
    public void bankAccountFixture() {
        TUser u = new TUser();
        u.setPhone("13813804762");
        u = userService.query(u);

        TAcceptBank ab = new TAcceptBank();
        ab.setCid(u.getCid());
        ab.setCarduser("邹晰锋");
        ab.setBankcard("6278999999999999");
        ab.setBanktype("BANK003");
        ab.setBankname("南京分行");
        ab.setCarduserid(u.getId());
        ab.setStatus(AcceptBankInfo.AcceptBankStatus.ACCEPT_BANK_STATUS_OTHER);
        ab.setImgid("null");
        acceptBankService.authApply(ab);
    }

}
