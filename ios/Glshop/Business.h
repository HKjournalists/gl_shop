//
//  Business.h
//  Glshop
//
//  Created by River on 14-11-6.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#ifndef Glshop_Business_h
#define Glshop_Business_h

//#define kBaseUrl @"www.916816.com/gl_shop_http_new/" // 外网



//#define kBaseUrl @"192.168.1.223:8080/gl_shop_http" // 内网

//#define kBaseUrl @"192.168.1.223/http" // test
//#define kBaseUrl @"192.168.1.224:8080/gl_shop_http" //团购test
#define kBaseUrl @"www.916816.com/gl_shop_http_test/"

#define kHttpGetMethod @"GET"
#define kHttpPostMethod @"POST"

#define kAppStoreVersion 1

#define bMessageSendPath @"smscode/send"  // 获取短信验证码
#define bValdateMessagepath  @"vldcode/check" // 验证短信码是否正确或是否过期


#pragma mark - 团购
#define bUserJoinGroupBuyPath @"noAuthUrl/system/activity/join"//参加团购报名接口//post

#pragma mark - 用户
#define bUserResgisterPath @"user/register"   // 用户注册接口 // post
#define bUserFindPWPath    @"user/findpwd"      // 找回密码接口
#define bUserModifyPWPath @"user/mdypwd"       // 用户修改登入密码接口 // get/post
#define bUserLoginPath      @"auth/login"       // 用户登入接口 // get/post
#define bUserLogoutPath     @"auth/logout"      // 用户退出登入接口 // get/post
#define bImageCodePath      @"imgcode/getCode"                 // 获取图片验证码
#define bImageCodeCheckPath      @"imgcode/check"                 // 验证图片验证码
#define bUpdateTokenPath @"auth/updateUserToken"
#define bSeqlinBagePath @"client/rmBadge"           // 消除角标数字


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
#define bordergetAddress    @"order/address/getAddress" // 合同查看卸货地时调用，但传的是合同关联的询单ID // get

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
#define bDeleteOrder    @"order/del"                        // 删除无效的订单

#pragma mark- 合同
#define bgetMyContractListWithPaginiation     @"contract/getMyContractListWithPaginiation"   // 用户根据合同状态查询自己的合同列表
#define bgetContractDetailInfoEx     @"contract/getContractDetailInfoEx"  // 用户根据单个合同ID查看合同信息
#define btoConfirmContract @"contract/toConfirmContract"   // 买卖双方首次三方通话后同意交易，后台客服生成待签订合同，双方登录手机客户端进行合同签订确认
#define bpayContractFundsOnline               @"contract/payContractFundsOnline"   // 买卖双方确认合同后，买家付款操作
#define bpayContractFundsOffline               @"contract/payContractFundsOffline"   // 3.3.7.	合同支付款项接口(线下)
#define bunPayFundsContractList               @"contract/unPayFundsContractList"   // 买家获取未支付货款的合同列表
#define bgetContractDetailTemplate               @"contract/getContractDetailTemplate"   // 获取合同的模板信息
#define bsingleCancelContract  @"contract/singleCancelContract"   // 交易中的双方都可以进行单方取消合同，单方取消合同属于违约取消，需要扣除违约方当前合同的全部保证金赔付给对方
#define bgetCancelContractListForPage               @"contract/getCancelContractListForPage"   // 获取取消合同记录列表信息
#define bcancelDraftContract               @"contract/cancelDraftContract"   // 买家和卖家在撮合合同完成后，可以取消这个起草的合同
#define bapplyOrAgreeOrArbitrateFinalEstimate               @"contract/applyOrAgreeOrArbitrateFinalEstimate"   // 买家合同付款完成后,支持进行确认结算操作
#define bgetContractChangeHistory               @"contract/getContractChangeHistory"   // 用户查看合同的变更历史记录信息
#define bgetContractArbitrationHistroy               @"contract/getContractArbitrationHistroy"   // 查询合同仲裁列表信息
#define btoEvaluateContract  @"contract/toEvaluateContract"   // 卖家确认卸货后和买家确认收货后可以进行合同评价。企业服务满意度0~5分，企业诚信度0~5分
#define bnoAuthUrlgetEvaluationContractList  @"noAuthUrl/getEvaluationContractList"   // 买家或卖登录手机客户端可以查看合同的评价内容


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
#define bdepositToGuaranty                      @"purse/depositToGuaranty"      // 余额充值保证金

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

#define bGetBankTNPath @"purse/getUnionPayTnOrderId"  // 银联在线支付的获取TN编号 post
#define bpursereportToUnionPayTradeResult @"purse/reportToUnionPayTradeResult" // 在线支付的上报交易结果 post

#pragma mark - 消息
#define bmessageList                      @"msg/getList"      // 获取用户所有消息列表。
#define bmessgaeDetail                    @"msg/getInfo"      // 查看消息的内容
#define bmessageTotal                   @"msg/newTotal"      // 查看新消息的数量。
#define bmessageMarkRead                    @"msg/read"      // 将新消息设置为已读状态。

#pragma mark - 全局
#define bSyncGetInfo                                 @"sync/getInfo"        // 获取同步数据，无需用户登录
#define bfileupload                                 @"file/upload"  // 文件上传接口
#define bcheckVersion                   @"noAuthUrl/checkVersion" // 版本检测

#endif
