package com.appabc.datas.system;

import com.appabc.datas.system.dao.RemittanceDao;
import com.appabc.pay.service.IPassPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zouxifeng on 12/5/14.
 */
@Service
public class FinanceService {

    @Autowired
    private IPassPayService passPayService;

    @Autowired
    private RemittanceDao remittanceDao;

    public Remittance getRemittance(int id) {
        return remittanceDao.get(id);
    }

    public void inputRemittance(Remittance remittance) {
        remittanceDao.create(remittance);
    }

    public Context<Remittance> queryForRemittance(Context<Remittance> ctx) {
        ctx.setTotalRows(remittanceDao.countBy(ctx.getParameters()));
        ctx.setResult(remittanceDao.queryForList(ctx.getParameters()));
        return ctx;
    }

    public void finishProcess(Remittance remittance, User processor) {
        remittance.setStatus(Remittance.Status.AUDITING);
        remittance.setProcessor(processor);
        remittanceDao.update(remittance);
    }

    public void finishAudit(Remittance remittance, User auditor) {
        passPayService.depositAccountOnline(remittance.getTargetCompanyId(), remittance.getTargetWalletType(),
                remittance.getAmount(), remittance.getBankSerialNumber());
        auditAction(remittance, auditor, Remittance.Status.FINISH);
    }

    public void auditFail(Remittance remittance, User auditor) {
        auditAction(remittance, auditor, Remittance.Status.AUDIT_FAIL);
    }

    private void auditAction(Remittance remittance, User auditor, Remittance.Status status) {
        remittance.setStatus(status);
        remittance.setAuditor(auditor);
        remittanceDao.update(remittance);
    }

    public void unableSolveRemittance(Remittance remittance) {
        remittance.setStatus(Remittance.Status.UNSOLVABLE);
        remittanceDao.update(remittance);
    }
}
