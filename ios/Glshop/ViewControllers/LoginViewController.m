//
//  LoginViewController.m
//  Glshop
//
//  Created by River on 14-11-7.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "LoginViewController.h"
#import "ForgetPasswordViewController.h"
#import "RegisterSuccessViewController.h"
#import "RegisterViewController.h"
#import "UserModel.h"
#import "AuthViewController.h"

#import "CompanyAuthViewController.h"

@interface LoginViewController () <UITextFieldDelegate>

@property (nonatomic, strong) UITextField *userNameTextField;
@property (nonatomic, strong) UITextField *passwordTextField;
@property (nonatomic, strong) UISwitch *switc;
@property (nonatomic, strong) UILabel *labelPw;
@property (nonatomic, strong) UIButton *loginBtn;

@end

@implementation LoginViewController

- (void)viewDidLoad {
    [super viewDidLoad];

}

- (void)loadSubViews {
    
    UIImageView *backgroundView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 15, self.view.vwidth, 90)];
    backgroundView.image = [UIImage imageNamed:@"白色列表2条"];
    backgroundView.userInteractionEnabled = YES;
    [self.view addSubview:backgroundView];
    
    _userNameTextField = [UITextField textFieldWithImageName:@"loginPhone" placeHodler:@"请输入手机号码" withDelegate:self];
    _userNameTextField.text = [[NSUserDefaults standardUserDefaults] objectForKey:kUserNamekey];
    _userNameTextField.frame = CGRectMake(0, 13, backgroundView.vwidth, backgroundView.vheight/2);
    _userNameTextField.keyboardType = UIKeyboardTypeNumberPad;
    _userNameTextField.returnKeyType = UIReturnKeyNext;
    [self.view addSubview:_userNameTextField];
    
    _passwordTextField = [UITextField textFieldWithImageName:@"loginPassword" placeHodler:@"请输入密码" withDelegate:self];
    _passwordTextField.frame = CGRectMake(0, _userNameTextField.vbottom, backgroundView.vwidth, backgroundView.vheight/2);
    _passwordTextField.text = [[NSUserDefaults standardUserDefaults] objectForKey:kUserPasswordkey];
    _passwordTextField.secureTextEntry = YES;
    _passwordTextField.keyboardType = UIKeyboardTypeASCIICapable;
    _passwordTextField.returnKeyType = UIReturnKeyDone;
    [self.view addSubview:_passwordTextField];
    
    _loginBtn = [UIButton buttonWithTip:@"登录" target:self selector:@selector(login:)];
    _loginBtn.frame = CGRectMake(10, backgroundView.vbottom+15, SCREEN_WIDTH-20, 40);
    _loginBtn.layer.cornerRadius = 3.f;
    [_loginBtn setTitleColor:[UIColor darkGrayColor] forState:UIControlStateDisabled];
    _loginBtn.backgroundColor = ColorWithHex(@"#FF6700");
    [self.view addSubview:_loginBtn];
    
    _switc = [[UISwitch alloc] initWithFrame:CGRectMake(_loginBtn.vleft, _loginBtn.vbottom+15, 40, 25)];
    [_switc addTarget:self action:@selector(switcOn:) forControlEvents:UIControlEventValueChanged];
    _switc.tintColor = [UIColor grayColor];
    _switc.onTintColor = [UIColor orangeColor];
    _switc.on = _passwordTextField.text.length>0 ? YES : NO;
    [self.view addSubview:_switc];
    
    _labelPw = [UILabel labelWithTitle:@"记住密码"];
    _labelPw.textColor = _switc.on ? ColorWithHex(@"#424242"):ColorWithHex(@"#808080");
    _labelPw.frame = CGRectMake(_switc.vright+3, _switc.vtop, 100, _switc.vheight);
    [self.view addSubview:_labelPw];
    
    UIButton *btnPw = [UIButton buttonWithTip:@"忘记密码?" target:self selector:@selector(forgetPassword:)];
    btnPw.frame = CGRectMake(self.view.vright-100, _switc.vtop, 100, 30);
    [btnPw setTitleColor:[Utilits colorWithHexString:@"#507daf"] forState:UIControlStateNormal];
    [self.view addSubview:btnPw];
    
    NSInteger height =  iPhone5 ? 200 : 160;
    UIButton *registerBtn = [UIButton buttonWithTip:nil target:self selector:@selector(gotoResgister:)];
    registerBtn.frame = CGRectMake(10, _switc.vbottom+25, self.view.vwidth-20, height);
    [registerBtn setBackgroundImage:[UIImage imageNamed:@"登录--注册广告图"] forState:UIControlStateNormal];
    [self.view addSubview:registerBtn];
    
}

#pragma mark - UIAction
- (void)switcOn:(UISwitch *)swich {
    if (swich.on) {
        _labelPw.textColor = ColorWithHex(@"#424242");
    }else {
        _labelPw.textColor = ColorWithHex(@"#808080");
    }
}

- (void)login:(UIButton *)btn {
    if (!_userNameTextField.text.length) {
        HUD(@"请输入用户名！");
        return;
    }
    
    if (!_passwordTextField.text.length) {
        HUD(@"请输入密码！");
        return;
    }
    [_userNameTextField resignFirstResponder];
    [_passwordTextField resignFirstResponder];
    
    NSString *pushToken = [Utilits ToGetdeviceToken];
    
    NSString *append = [NSString stringWithFormat:@"%@%@",_userNameTextField.text,_passwordTextField.text];
    NSString *securityStr = [append md5];
    NSMutableDictionary *param = [NSMutableDictionary dictionaryWithObjectsAndKeys:_userNameTextField.text,@"username",securityStr,@"password",pushToken,@"clientid",IntToNSNumber(1),@"clienttype", nil];
    [self showHUD];
    __block typeof(self) this = self;
    [self requestWithURL:bUserLoginPath params:param HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        NSArray *dicArray = [responseData objectForKey:ServiceDataKey];
        [this _loginSuccess:dicArray];
    } failedBlock:^(ASIHTTPRequest *request) {
       
    }];
    
//    [self.navigationController pushViewController:[[CompanyAuthViewController alloc] init] animated:YES];

} // 点击登录

- (void)gotoResgister:(UIButton *)btn {
    
    [self.navigationController pushViewController:PublicPushVC(@"RegisterViewControllerId") animated:YES];
} // 点击进行注册

- (void)forgetPassword:(UIButton *)btn {
    [self.navigationController pushViewController:PublicPushVC(@"ForgetPasswordViewControllerId") animated:YES];
} // 点击重新设置密码

#pragma mark - Private
/**
 *@brief 登入成功
 *@param datas 服务器返回的数组，包含用户信息
 */
- (void)_loginSuccess:(NSArray *)datas {
    HUD(@"登录成功");
    // 保存用户信息
    NSDictionary *dic = [NSDictionary dictionary];
    if (datas.count > 0)
        dic = datas.firstObject;
    UserModel *model = [[UserModel alloc] initWithDataDic:dic];
    UserInstance *userInstance = [UserInstance sharedInstance];
    userInstance.user = model;
    
    // 发送通知，用户已登入
    [[NSNotificationCenter defaultCenter] postNotificationName:kUserDidLoginNotification object:nil];
    
    // 保存登入账号和密码
    [[NSUserDefaults standardUserDefaults] setObject:_userNameTextField.text forKey:kUserNamekey];
    if (_switc.on) {
        [[NSUserDefaults standardUserDefaults] setObject:_passwordTextField.text forKey:kUserPasswordkey];
        [[NSUserDefaults standardUserDefaults] synchronize];
    }else {
        [[NSUserDefaults standardUserDefaults] removeObjectForKey:kUserPasswordkey];
    }
    
    // 登录成功，返回主页面
    if (!_skipToAuth) {
        [self.navigationController popToRootViewControllerAnimated:YES];
    }else {
        AuthViewController *vc = [[AuthViewController alloc] init];
        [self.navigationController pushViewController:vc animated:YES];
    }
    
    // 如果有相应的推送消息就进行相应的跳转
    if ([[NSUserDefaults standardUserDefaults] objectForKey:kRemoteNotificationKey]) {
        DLog(@"%@",[[NSUserDefaults standardUserDefaults] objectForKey:kRemoteNotificationKey]);
        PushMessageType type = [[PushInstance sharedInstance] pushType];
        if (type != TYPE_USER_LOGIN_OTHER_DEVICE) {
            [[PushInstance sharedInstance] handlePushMessage];
        }
    }
}

@end
