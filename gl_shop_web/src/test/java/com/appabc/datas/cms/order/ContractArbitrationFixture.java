package com.appabc.datas.cms.order;

import com.appabc.datas.exception.ServiceException;
import com.appabc.datas.service.contract.IContractArbitrationService;
import com.appabc.datas.service.contract.IContractInfoService;
import org.apache.commons.lang3.StringUtils;
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
 * Created by zouxifeng on 4/1/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-redis.xml",
        "classpath*:applicationContext-tools.xml",
        "classpath*:applicationContext-datas.xml",
        "classpath*:applicationContext-pay.xml",
        "classpath*:test-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager",defaultRollback=true)
@ActiveProfiles("develop")
@Transactional
public class ContractArbitrationFixture {


    static {
        System.setProperty("local.config", "classpath:/local-config.properties");
    }

    @Autowired
    IContractArbitrationService contractArbitrationService;

    @Autowired
    IContractInfoService contractInfoService;

    @Test
    public void makeContractArbitration() throws ServiceException {
        String contractId = "1120150413218";
        String seller = "241220140000030";
        String buyer = "241120140000019";
        String buyerName = "上海美赢通";
        contractInfoService.toConfirmContractRetOperator(contractId, seller, "江苏国立");
        contractInfoService.toConfirmContractRetOperator(contractId, buyer, buyerName);
        contractInfoService.payContractFunds(contractId, buyer, buyerName);
        contractArbitrationService.toContractArbitration(contractId, buyer, buyerName, StringUtils.EMPTY);
    }


}
