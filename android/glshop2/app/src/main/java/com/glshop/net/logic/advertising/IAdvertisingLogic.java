package com.glshop.net.logic.advertising;

import com.glshop.platform.base.logic.ILogic;

/**
 * Created by guoweilong on 2015/8/7.
 * 广告活动图业务中心
 */
public interface IAdvertisingLogic extends ILogic {

    /**
     * 提交信息
     * @param username 用户姓名
     * @param userPhone 用户电话
     * @param number 数量
     * @param remark 备注
     */
    void submitInfo(String username,String userPhone,int number,String remark);


    /**
     * 获取首页广告图
     * @param clientType
     */
    void getIndexBanner(String clientType);
}
