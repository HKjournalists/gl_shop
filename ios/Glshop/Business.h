//
//  Business.h
//  Glshop
//
//  Created by River on 14-11-6.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#ifndef Glshop_Business_h
#define Glshop_Business_h

//#define kBaseUrl @"www.916816.com/gl_shop_http" // 外网

#define kBaseUrl @"192.168.1.223:8080/gl_shop_http" // 内网

#define kHttpGetMethod @"GET"
#define kHttpPostMethod @"POST"

#define bMessageSendPath @"smscode/send"  // 获取短信验证码
#define bValdateMessagepath  @"vldcode/check" // 验证短信码是否正确或是否过期

#pragma mark - 用户
#define bUserResgisterPath @"user/register"   // 用户注册接口 // post
#define bUserFindPWPath    @"user/findpwd"      // 找回密码接口
#define bUserModifyPWPath @"user/mdypwd"       // 用户修改登入密码接口 // get/post
#define bUserLoginPath      @"auth/login"       // 用户登入接口 // get/post
#define bUserLogoutPath     @"user/logout"      // 用户退出登入接口 // get/post
#define bImageCodePath      @"imgcode/getCode"                 // 获取图片验证码
#define bImageCodeCheckPath      @"imgcode/check"                 // 验证图片验证码

#pragma mark - 企业
#define bUserBaseInfoPath   @"copn/getCompanyInfo" // 查询用户注册基本信息 // get
#define bCompanyAuthPath    @"copn/authApply"       // 企业认证申请接口 //get/post
#define bCompanyAuthInfoPath @"copn/getMyCompanyInfo" // 企业认证资料查看接口 // get
#define bUpdateCompanyInfoPath @"copn/updateIntroduction" // 更新企业介绍接口 // post
#define bCompanyContactListPath @"copn/contact/getList"     // 获取企业联系人列表 // get
#define bViewSingleCompanyContactPath @"copn/contact/getinfo" // 查看单个企业联系人详情 // get/post
//#define bAddCompanyContactPath @"copn/contact/add"            // 添加企业联系人 // get/post  
#define bSaveContactPath @"copn/contact/save"               // 保存企业联系人  //get/post
#define bDeleteContactPath @"copn/contact/mdy"              // 删除企业联系人  // get/post
#define bGetUnloadAddressList @"copn/address/getList"       // 3.1.18.	获取企业卸货地址列表接口
#define bGetUnloadAddressInfo @"copn/address/getInfo"       // 3.1.19.	查看单个企业卸货地址详情接口
#define bAddUnloadAddress @"copn/address/add"       // 3.1.20.	添加企业卸货地址接口
#define bModifyUnloadAddress @"copn/address/mdy"       // 3.1.21.	修改企业卸货地址接口
#define bSetDefaultUnloadAddress @"copn/address/setDefault"       // 3.1.22.	设置默认卸货地址接口
#define bDeleUnloadAddress @"copn/address/del"       // 3.1.23.	删除企业卸货地址接口

#pragma mark - 商品
#define bProductTodayInfo @"product/price/getToday"         // 获取商品今日价格列表 //get/post
#define bProductTomrrowInfo @"product/price/getHope"        // 获取商品预测价格列表 //get/post
#define bOrderInfo @"order/open/getInfo"                         // 获取供应/求购信息 //get
#define bPublishOrderInfo @"order/publish"                  // 发布供应/求购信息    // post
#define bGetSideInfo @"copn/getsideinfo"                    // 买/卖家企业详情 //get/post
#define bDealApply @"order/item/dealApply"                  // 交易申请接口   //get/post
#define bFoundOrderList @"order/open/getOrderList"          // 找买找卖     // get
#define bGetMyList      @"order/getMyList"                  // 获取我的供应/求购信息 // get
#define bModifyMyProduct @"order/mdy"                       // 修改我的供应/求购信息  //post
#define bCancelOrder    @"order/cancel"                     // 取消供应/求购信息 //get/post

#pragma mark- 合同



#pragma mark- 钱包
#define bGetPurseAccountInfo               @"purse/getPurseAccountInfo"        // 获取我的账户信息 // get/post
#define bInitPurseAccount               @"purse/initializePurseAccount"         // 初始化钱包信息 // get/post
#define bGetPurseAccountAndCompanyInfo               @"purse/getPurseAccountAndCompanyInfo"     // 获取账户信息以及企业信息 // post
#define bgetPayRecordList               @"purse/getPayRecordList"              // 查询保证金交易流水列表信息 //get/post
#define bgetPayRecordDetail               @"purse/getPayRecordDetail"           // 查询保证金交易流水详细信息 //get/post
#define bgetContractDetailInfo               @"contract/getContractDetailInfo"  // 查询保证金流水相关订单详细信息 //get/post
#define bgetPayRecordList               @"purse/getPayRecordList"               // 查询货款交易流水列表信息 //get/post
#define bgetPayRecordDetail               @"purse/getPayRecordDetail"           // 查询货款交易流水详细信息 //get/post
#define bgetContractDetailInfo               @"contract/getContractDetailInfo"  // 查询货款流水相关订单详细信息。//get/post
#define bdepositToGuaranty                      @"purse/depositToGuaranty"

#define bAccetpAuthApply                @"copn/accept/authApply"  // 添加收款人
#define bcopnacceptgetList              @"copn/accept/getList"  // 获取企业收款人列表信息
#define bcopnacceptgetInfo              @"copn/accept/getInfo"  // 获取企业收款人详细信息
#define bcopnacceptdel                  @"copn/accept/del"      // 删除企业收款人信息
#define bcopnacceptsetDefault         @"copn/accept/setDefault"  // 将企业收款人设置为默认信息
#define bcopnacceptmdy                @"copn/accept/mdy"      // 修改企业收款人后将会重新认证

#define bextractCashRequest                      @"purse/extractCashRequest"        // 用户线上提款申请，客户核实并打款给用户。//post
#define baddAcceptBank                      @"purse/addAcceptBank"                  // 新增收款人信息。//post
#define bgetAcceptBankList                      @"purse/getAcceptBankList"          // 查询收款人信息。//post/get
#define bextractCashValidation                      @"purse/extractCashValidation"  // 单条提取明细数据查询。//post
#define bdepositRecordList                      @"purse/depositRecordList"          // 用户充值的明细纪录列表。
#define bgetContractDetailInfo                      @"contract/getContractDetailInfo"   // 单条充值明细数据查询。//get
#define bdepositAccountOnline                      @"purse/depositAccountOnline"        // 在线充值。//post
#define bdepositAccountOffline                      @"purse/depositAccountOffline"      // 线下充值 // post
#define bdepositThirdPartInfo                      @"purse/depositThirdPartInfo"       // 保证金线下充值。//post
#define bpaymentAccountOffline                      @"purse/paymentAccountOffline"      // 线下付款。//post


#pragma mark - 全局
#define bSyncGetInfo                                 @"sync/getInfo"        // 获取同步数据，无需用户登录
#define bfileupload                                 @"file/upload"  // 文件上传接口

#endif
