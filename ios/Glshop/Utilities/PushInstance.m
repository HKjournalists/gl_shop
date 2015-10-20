//
//  PushInstance.m
//  Glshop
//
//  Created by River on 15-3-21.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  消息推送

#import "PushInstance.h"
#import "ProfileViewController.h"
#import "JSONKit.h"
#import "MainViewController.h"
#import "MypurseViewController.h"
#import "LoginViewController.h"
#import "MyContractViewController.h"
#import "ContractWaitSureViewController.h"
#import "ContractEndedViewController.h"
#import "ContractPorccesingViewController.h"
#import "ContractProcessDetailViewController.h"
#import "ContractDetailViewController.h"
#import "MySupplyViewController.h"
#import "PushAlertView.h"

static NSInteger loginAlertTag = 200;

@implementation PushInstance

#pragma mark - Initalizes
+ (instancetype)sharedInstance {
    static id sharedInstance;
    static dispatch_once_t once;
    dispatch_once(&once, ^{
        sharedInstance = [[[self class] alloc] init];
    });
    return sharedInstance;
}

#pragma mark - Private
- (void)pushViewContrller:(UIViewController *)viewContrelller {
    UIViewController *vc = self.topViewController;
    
    UINavigationController *nav = (UINavigationController *)[UIApplication sharedApplication].keyWindow.rootViewController;

    if (vc.navigationController) {
        if (vc.presentingViewController) {
            [vc dismissViewControllerAnimated:NO completion:nil];
        }
        
        if (vc.navigationController.viewControllers.count >= 2) {
            [vc.navigationController popToRootViewControllerAnimated:NO];
        }
        
        if ([nav isKindOfClass:[UINavigationController class]]) {
            dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.01 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
                [nav pushViewController:viewContrelller animated:YES];
            });
        }else {
            DLog(@"获取导航控制器异常！");
        }
    }else {
        DLog(@"获取导航控制器失败!"); 
    }
}

- (BOOL)isAppcicationActive {
    UIApplicationState state = [UIApplication sharedApplication].applicationState;
    return state == UIApplicationStateActive;
}

- (void)showAlert:(NSDictionary *)pushData {
    PushAlertView *alert = [[PushAlertView alloc] initWithTitle:@"通知提醒" message:[self alertMessage] delegate:self cancelButtonTitle:@"忽略" otherButtonTitles:@"去看看", nil];
    alert.pushMessage = pushData;
    [alert show];
}

#pragma mark - Public
- (void)handlePushMessage {
    PushMessageType type = [[PushInstance sharedInstance] pushType];
    NSDictionary *userInfo = [[NSUserDefaults standardUserDefaults] objectForKey:kRemoteNotificationKey];
    NSString *strDic = userInfo[ServiceDataKey];
    NSDictionary *dic = [strDic objectFromJSONString];
    
    switch (type) {
        case TYPE_USER_LOGIN_OTHER_DEVICE:{
            [self handleUserLoginPush];
        }
            break;
        case TYPE_COMPANY_AUTH:{
            [self handleCompanyAuth:dic];
        }
            break;
        case TYPE_COMPANY_PAYEE_AUTH:{
//            [self handlePurse];
        }
            break;
        case TYPE_CONTRACT_SIGN: { 
            [self handlePushToContractDetail:dic];
        }
            break;
        case TYPE_CONTRACT_ING: { // 
            
        }
            break;
        case TYPE_CONTRACT_CANCEL: { /**合同取消**/
            [self handlePushToContractDetail:dic];
        }
            break;
        case TYPE_CONTRACT_MAKE_MATCH: { /**撮合成功**/
            [self handlePUshDrafDetail:dic];
        }
            break;
        case TYPE_CONTRACT_SINGLE_DAF_CONFIRM: { /**合同单方起草确认**/
            [self handlePUshDrafDetail:dic];
        }
            break;
        case TYPE_CONTRACT_DAF_CANCEL: /**合同起草取消**/
        {
            [self handlePUshDrafDetail:dic];
        }
            break;
        case TYPE_CONTRACT_DAF_TIMEOUT: /**合同起草超时**/
        {
            [self handlePUshDrafDetail:dic];
        }
            break;
        case TYPE_CONTRACT_BUYER_PAYFUNDS: // 买方付款
        {
            [self handlePushToContractDetail:dic];
        }
            break;
        case TYPE_CONTRACT_BUYER_APPLY_FINALESTIMATE: /**买家申请合同货物和货款结算**/
        {
            [self handlePushToContractDetail:dic];
        }
            break;
        case TYPE_CONTRACT_SELLER_AGREE_FINALESTIMATE: /**卖家同意合同货物和货款结算确认**/
        {
            [self handlePushToContractDetail:dic];
        }
            break;
        case TYPE_CONTRACT_APPLY_ARBITRATION: /**申请仲裁**/
        {
            [self handlePushToContractDetail:dic];
        }
            break;
        case TYPE_CONTRACT_ARBITRATED_FINALESTIMATE: /**仲裁结算**/
        {
            [self handlePushToContractDetail:dic];
        }
            break;
        case TYPE_CONTRACT_PAYFUNDS_TIMEOUT: /**买家预期未付款**/
        {
            [self handlePushToContractDetail:dic];
        }
            break;
        case TYPE_CONTRACT_OTHERS: {
            // 暂无业务处理
        }
            break;
        case TYPE_MONEY_CHANG_GUARANTY: {
            [self handlePurseOperate];
        }
            break;
        case TYPE_MONEY_CHANG_DEPOSIT: {
            [self handlePurseOperate];
        }
            break;
        case TYPE_MONEY_CHANG: {
            [self handlePurseOperate];
        }
            break;
            
        default:
            break;
    }
    
    if (![self isAppcicationActive]) { // 应用处于非活跃状态，处理完消息推送后需及时删除
        [[NSUserDefaults standardUserDefaults] removeObjectForKey:kRemoteNotificationKey];
    }
    
}

#pragma mark - 推送消息解析
- (PushMessageType)pushType {
    NSDictionary *userInfo = [[NSUserDefaults standardUserDefaults] objectForKey:kRemoteNotificationKey];
    NSString *strDic = userInfo[ServiceDataKey];
    NSDictionary *dic = [strDic objectFromJSONString];
    PushMessageType type = [dic[@"bcode"] integerValue];
    return type;
}

- (NSString *)alertMessage {
    NSDictionary *userInfo = [[NSUserDefaults standardUserDefaults] objectForKey:kRemoteNotificationKey];
    NSString *str = userInfo[@"aps"][@"alert"];
    return str;
}// 获取推送标题

#pragma mark - 业务逻辑处理
- (void)handleUserLoginPush {
    // 注销用户信息
    UserInstance *userInstance = [UserInstance sharedInstance];
    [userInstance destory];
    [[NSNotificationCenter defaultCenter] postNotificationName:kUserDidLogoutNotification object:nil];
    
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"通知提醒" message:@"用户已在其它设备登录，如果继续使用，请重新登录！" delegate:self cancelButtonTitle:globe_sure_str otherButtonTitles:nil, nil];
    alert.tag = loginAlertTag;
    [alert show];
}

/**
 *@brief 收到保证金变化或企业认证推送消息后,需要及时同步用户数据
 */
- (void)updateInfo {
    UINavigationController *nav = (UINavigationController *)[UIApplication sharedApplication].keyWindow.rootViewController;
    MainViewController *vc;
    if (nav.viewControllers.count) {
        vc = nav.viewControllers[0];
    }
    if ([vc respondsToSelector:@selector(updateUserInfo)]) {
        [vc updateUserInfo];
    }
}

- (void)handleCompanyAuth:(NSDictionary *)pushData {
    [self updateInfo];
    
    if ([self isAppcicationActive]) {
        // 如果当前在我的资料页面，刷新页面
        if ([self.topViewController isKindOfClass:[ProfileViewController class]]) {
            [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushCompanyInfoNotification object:nil];
        }else { // 如果不在我的资料页面，弹出去看看
            [self showAlert:pushData];
        }
    }else { // 应用挂起或者关闭
        [self handleCompanyAuthOperate];
    }
    
}// 企业认证推送

- (void)handleCompanyAuthOperate {
    ProfileViewController *vc = [[ProfileViewController alloc] init];
    [self pushViewContrller:vc];
}

- (void)handlePurse {
 
}// 钱包相关推送

- (void)handlePurseOperate {
    PushMessageType type = [self pushType];
    if (type == TYPE_MONEY_CHANG_GUARANTY || type == TYPE_MONEY_CHANG_DEPOSIT ) { // 如果保证金变动要刷新个人资料，已同步重要数据
        [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushMyPurseNotification object:nil];
    }
    
    if (type == TYPE_MONEY_CHANG_GUARANTY) {
        [[NSNotificationCenter defaultCenter] postNotificationName:kUpdateUserInfoNitification object:nil];
    }
    
    if (![self isAppcicationActive]) {
        MypurseViewController *vc = [[MypurseViewController alloc] init];
        [self pushViewContrller:vc];
    }
}

- (void)handlePUshDrafDetail:(NSDictionary *)pushData {
    PushMessageType type = [pushData[@"bcode"] integerValue];
    if (type != TYPE_CONTRACT_SINGLE_DAF_CONFIRM) {
        [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushMySupplyNotification object:nil];
    }
    
    if ([self isAppcicationActive]) {
        if ([self.topViewController isKindOfClass:[MyContractViewController class]] || [self.topViewController isKindOfClass:[ContractDetailViewController class]]) {
            [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushContractNotification object:nil];
        }else { // 如果不在我的合同列表或者我的合同详情，弹出去看看
            [self showAlert:pushData];
        }
    }else { // 应用挂起或者关闭
        [self handlePUshDrafDetailOperate:pushData];
    }
}// 跳转到起草合同详情

- (void)handlePUshDrafDetailOperate:(NSDictionary *)pushData {
    // 获取合同id
    NSString *conId = pushData[@"businessId"];
    ContractDetailViewController *vc = [[ContractDetailViewController alloc] init];
    vc.contractId = conId;
    [self pushViewContrller:vc];
}

- (void)handlePushToContractDetail:(NSDictionary *)pushData {
    
    if ([self isAppcicationActive]) {
        // 如果当前在我的合同列表或者我的合同详情，刷新页面
        if ([self.topViewController isKindOfClass:[MyContractViewController class]] || [self.topViewController isKindOfClass:[ContractProcessDetailViewController class]]) {
            [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushContractNotification object:nil];
        }else { // 如果不在我的合同列表或者我的合同详情，弹出去看看
            [self showAlert:pushData];
        }
    }else { // 应用挂起或者关闭
        [self handlePushToContractDetailOperate:pushData];
    }
    
}// 合同推送，跳转到详请页面或刷新详情页面

- (void)handlePushToContractDetailOperate:(NSDictionary *)pushData {
    // 获取合同id
    NSString *conId = pushData[@"businessId"];
    // 进入合同明细
    ContractProcessDetailViewController *vc = [[ContractProcessDetailViewController alloc] init];
    vc.contractId = conId;
    [self pushViewContrller:vc];
}// 合同推送，跳转到详请页面或刷新详情页面具体实现

#pragma mark - UIAlertView Delegate
- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex {
    if (alertView.tag == loginAlertTag) {
        LoginViewController *vc = [[LoginViewController alloc] init];
        vc.title = @"登录";
        [self pushViewContrller:vc];
    }else {
        PushAlertView *alert = (PushAlertView *)alertView;
        PushMessageType type = TYPE_DEFAULT;
        if ([alertView isKindOfClass:[PushAlertView class]]) {
            type = [alert.pushMessage[@"bcode"] integerValue];
        }
        
        if (type == TYPE_CONTRACT_SIGN || type == TYPE_CONTRACT_CANCEL || type == TYPE_CONTRACT_BUYER_PAYFUNDS || type == TYPE_CONTRACT_BUYER_APPLY_FINALESTIMATE || type == TYPE_CONTRACT_SELLER_AGREE_FINALESTIMATE || type == TYPE_CONTRACT_APPLY_ARBITRATION || type == TYPE_CONTRACT_ARBITRATED_FINALESTIMATE || type == TYPE_CONTRACT_PAYFUNDS_TIMEOUT) {
            
            if (buttonIndex) {
                [self handlePushToContractDetailOperate:alert.pushMessage];
            }
        }else if (type == TYPE_COMPANY_AUTH) {
            if (buttonIndex) {
                [self handleCompanyAuthOperate];
            }
        }else if (type == TYPE_CONTRACT_SINGLE_DAF_CONFIRM || type == TYPE_CONTRACT_MAKE_MATCH || type == TYPE_COMPANY_AUTH ) {
            if (buttonIndex) {
                [self handlePUshDrafDetailOperate:alert.pushMessage];
            }
        }
    }
    
    // 应用处于活跃状态，处理完消息推送后需及时删除
    [[NSUserDefaults standardUserDefaults] removeObjectForKey:kRemoteNotificationKey];
}

@end
