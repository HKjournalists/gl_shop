package com.appabc.datas.system;

import com.appabc.bean.pvo.TAuthRecord;

/**
 * Created by zouxifeng on 11/26/14.
 */
public class AccountVerifyTask extends Task {

    private TAuthRecord authRecord;

    public TAuthRecord getAuthRecord() {
        return authRecord;
    }

    public void setAuthRecord(TAuthRecord authRecord) {
        this.authRecord = authRecord;
    }
}
