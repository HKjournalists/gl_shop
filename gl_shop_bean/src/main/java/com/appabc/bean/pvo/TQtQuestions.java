package com.appabc.bean.pvo;

import com.appabc.common.base.bean.BaseBean;
import java.util.Date;

public class TQtQuestions extends BaseBean {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5984487037921240663L;

    /**
     * 问题内容
     */
    private String quetsion;

    /**
     * 问题答案
     */
    private String answers;

    /**
     * 问题分类
     */
    private String type;

    /**
     * 修改人
     */
    private String updater;

    /**
     * 修改时间
     */
    private Date updatetime;

    public String getQuetsion() {
        return quetsion;
    }

    public void setQuetsion(String quetsion) {
        this.quetsion = quetsion == null ? null : quetsion.trim();
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers == null ? null : answers.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}