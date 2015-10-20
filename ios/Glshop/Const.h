//
//  Const.h
//  Glshop
//
//  Created by River on 14-11-6.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#ifndef Glshop_Const_h
#define Glshop_Const_h

#define kNetError @"网络不给力!"
#define kServerNotReachable @"不能连接到服务器"
#define kRequestTimeOut @"请求超时"
#define kRefrushing @"正在刷新..."
#define kLoading @"正在加载..."

#pragma mark - 服务器常用字段
#define ServiceDataKey @"DATA"
#define DataValueKey @"val"
#define DataTextKey @"text"

#pragma mark - 文件名
#define kSynacFileName @"synacFile"
#define kAreaFileName @"area"
#define KBankFileName @"bank"

#pragma mark - 默认加载数
#define kDefaultLoadDataCount 10

#pragma mark - Notifications
#define kUserDidLoginNotification @"userLoginNotification"
#define kUserDidLogoutNotification @"userLogoutNotification"
#define kRefrushAddressListNotification @"refrushAddressList" // 刷新收货地址列表
#define kRefrushCompanyInfoNotification @"refrushCompanyInfo" // 刷新个人资料信息
#define kRefrushGatherListNotification @"refrushGathers" // 刷新收款人列表
#define kRefrushMyPurseNotification @"refrushMyPurse"   // 刷新我的钱包信息
#define kRefrushBuySellNotification @"refrushBuySellNotification" // 刷新找买找卖列表
#define kRefrushMySupplyNotification @"refrushMySupply" // 刷新我的供求列表
#define kRefrushContractNotification @"refrushContractList" // 刷新合同列表
#define kClearBageNotification @"clearBageNotification"
#define kUpdateUserInfoNitification @"updateUserInfoNotification"

#pragma mark - Remote Notifications
#define kRemoteNotificationKey @"userInfoKey"
#define kDeviceTokenKey @"deviceToken"
#define kUpdateTokenKey @"serviceTokenKey"

#pragma mark - 用户相关
#define kUserPasswordkey @"passwordkey"
#define kUserNamekey     @"usernamekey"

#pragma mark - 时间格式
#define kTimeFormart @"yyyy-MM-dd"
#define kTimeDetailFormat @"yyyy/MM/dd HH:mm:ss"
#define kTimeDetail_Format @"yyyy-MM-dd HH:mm:ss"
#define kOneDaySeconds 24*60*60

#pragma mark - 常用图片名字
#define RedStartImageName @"icon_redStart"
#define EmptyDataImage @"loading_kongbaiye"
#define RequestNetErrorImage @"loading_jiazaishibai"
#define BlueButtonImageName @"wallet_anniu"
#define YelloCommnBtnImgName @"attestation_icon"
#define PlaceHodelImageName @"attestation_icon_photo"
#define WhiltWithLineImgName @"wallet_beijing"

#pragma mark - 常用字符串
#define ShowDataEmptyStr @"暂无数据，点击刷新"
#define ShowNetErrorStr @"加载失败，点击重新加载"

#define sellInfo @"出售信息"
#define buyInfo @"求购信息"

#define kAppTel @"tel://4009616816"

#endif
