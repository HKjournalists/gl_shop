//
//  SettingPasswordViewController.m
//  Glshop
//
//  Created by River on 14-11-7.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "SettingPasswordViewController.h"
#import "RegisterSuccessViewController.h"
#import "IQKeyboardManager.h"

@interface SettingPasswordViewController () <UITextFieldDelegate>


@end

@implementation SettingPasswordViewController

#pragma mark - VC Life
- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.automaticallyAdjustsScrollViewInsets=YES;

}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    
    [_phoneTextField becomeFirstResponder];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    [[IQKeyboardManager sharedManager] setShouldToolbarUsesTextFieldTintColor:NO];
    [[IQKeyboardManager sharedManager] setShouldResignOnTouchOutside:YES];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    
    [MKNetworkEngine cancelOperationsContainingURLString:bMessageSendPath];
    [MKNetworkEngine cancelOperationsContainingURLString:bUserResgisterPath];
    [MKNetworkEngine cancelOperationsContainingURLString:bUserFindPWPath];
}

#pragma mark - UI
- (void)loadSubViews {
    
    UILabel *smsLabel = [UILabel labelWithTitle:@"短信验证"];
    smsLabel.textColor = ColorWithHex(@"#646464");
    smsLabel.frame = CGRectMake(10, 15, 100, 20);
    [self.view addSubview:smsLabel];
    
    UIImageView *backgroundView = [[UIImageView alloc] initWithFrame:CGRectMake(0, smsLabel.vbottom+5, self.view.vwidth, 90)];
    backgroundView.image = [UIImage imageNamed:@"白色列表2条"];
    backgroundView.userInteractionEnabled = YES;
    [self.view addSubview:backgroundView];
    
    _phoneTextField = [UITextField textFieldWithPlaceHodler:nil leftView:@"发送验证码到：" withDelegate:nil];
    _phoneTextField.text = _phone;
    _phoneTextField.textColor = [UIColor grayColor];
    _phoneTextField.enabled = NO;
    _phoneTextField.keyboardType = UIKeyboardTypeNumberPad;
    _phoneTextField.frame = CGRectMake(10, smsLabel.vbottom+5, backgroundView.vwidth, backgroundView.vheight/2);
    _phoneTextField.returnKeyType = UIReturnKeyNext;
    [self.view addSubview:_phoneTextField];
    
    _codeTextfield = [UITextField textFieldWithPlaceHodler:@"请输入验证码" withDelegate:self];
    _codeTextfield.frame = CGRectMake(_phoneTextField.vleft, _phoneTextField.vbottom, backgroundView.vwidth-130, backgroundView.vheight/2);
    _codeTextfield.keyboardType = UIKeyboardTypeNumberPad;
    _codeTextfield.returnKeyType = UIReturnKeyDone;
    [self.view addSubview:_codeTextfield];
    
    
    // 获取验证码按钮
//    btnVerifCode = [UIButton buttonWithTip:@"获取验证码" target:self selector:@selector(getCode:)];
    btnVerifCode = [UIFactory createBtn:@"Buy_sell_publish" bTitle:@"获取验证码" bframe:CGRectZero];
    [btnVerifCode addTarget:self action:@selector(getCode:) forControlEvents:UIControlEventTouchUpInside];
    btnVerifCode.frame = CGRectMake(_codeTextfield.vright+15, _codeTextfield.vtop+6, 100, 30);
    [btnVerifCode setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [btnVerifCode setTitleColor:ColorWithHex(@"#3b70d4") forState:UIControlStateDisabled];
    [self.view addSubview:btnVerifCode];
    
    UILabel *oldLabel = [UILabel labelWithTitle:@"请输入旧密码"];
    oldLabel.textColor = smsLabel.textColor;
    oldLabel.frame = CGRectMake(smsLabel.vleft, backgroundView.vbottom+15, 200, smsLabel.vheight);
    UIImageView *textFieldBG = [[UIImageView alloc] initWithFrame:CGRectMake(0, oldLabel.vbottom, self.view.vwidth, 44)];
    textFieldBG.image = [UIImage imageNamed:@"白色列表一条"];
    textFieldBG.userInteractionEnabled = YES;
    
    if (_operation == ModifySetPassword) {
        [self.view addSubview:oldLabel];
        [self.view addSubview:textFieldBG];
        
        _oldPWTextField = [UITextField textFieldWithPlaceHodler:@"请输入手机号码" withDelegate:self];
        _oldPWTextField.frame = CGRectMake(_codeTextfield.vleft, textFieldBG.vtop, textFieldBG.vwidth, textFieldBG.vheight);
        _oldPWTextField.keyboardType = UIKeyboardTypeNumberPad;
        _oldPWTextField.returnKeyType = UIReturnKeyDone;
        [self.view addSubview:_oldPWTextField];
    }
    
    UILabel *tip = [UILabel labelWithTitle:@"请设置登入密码"];
    tip.textColor = smsLabel.textColor;
    float y = 0;
    if (_operation == ModifySetPassword) {
        y = textFieldBG.vbottom+15;
    }else {
        y = backgroundView.vbottom+15;
    }
    tip.frame = CGRectMake(smsLabel.vleft, y, 200, smsLabel.vheight);
    [self.view addSubview:tip];
    
    UIImageView *backgroundView1 = [[UIImageView alloc] initWithFrame:CGRectMake(0, tip.vbottom+5, self.view.vwidth, 90)];
    backgroundView1.image = [UIImage imageNamed:@"白色列表2条"];
    backgroundView1.userInteractionEnabled = YES;
    [self.view addSubview:backgroundView1];
    
    // 密码输入框
    _pwTextField = [UITextField textFieldWithPlaceHodler:@"请输入密码" withDelegate:self];
    _pwTextField.frame = CGRectMake(tip.vleft, tip.vbottom+10, SCREEN_WIDTH-10, 30);
    _pwTextField.secureTextEntry = YES;
    [self.view addSubview:_pwTextField];
    
    // 密码确认框
    _rePwTextField = [UITextField textFieldWithPlaceHodler:@"请再次输入密码" withDelegate:self];
    _rePwTextField.frame = CGRectMake(tip.vleft, _pwTextField.vbottom+10, _pwTextField.vwidth, _pwTextField.vheight);
    _rePwTextField.secureTextEntry = YES;
    [self.view addSubview:_rePwTextField];
    
    UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(smsLabel.vleft, backgroundView1.vbottom+18, 5, 5)];
    imageView.image = nil;
    imageView.backgroundColor = [UIColor redColor];
    [self.view addSubview:imageView];
    UILabel *fomartLabel = [UILabel labelWithTitle:@"密码为6~12位数字或字符"];
    fomartLabel.frame = CGRectMake(imageView.vright, backgroundView1.vbottom+10, 200, 20);
    [self.view addSubview:fomartLabel];
    
    UIButton *doneBtn = [UIButton buttonWithTip:@"完成" target:self selector:@selector(resgisteSuccess)];
    float gap = iPhone4 ? 5 : 25;
    doneBtn.frame = CGRectMake(10, fomartLabel.vbottom+gap, self.view.vwidth-20, 40);
    doneBtn.layer.cornerRadius = 2.5f;
    doneBtn.backgroundColor = CJBtnColor;
    [self.view addSubview:doneBtn];

}

#pragma mark - UIAcions
- (void)getCode:(UIButton *)btn {
    [self _requesSmsCode];
    
    [_rePwTextField resignFirstResponder];
}

/**
 *@brief 点击完成注册或密码重置
 */
- (void)resgisteSuccess {
    if (![self vertifyParams]) return;

    if (_operation == RegisterSetpassWord) { // 用户注册操作
        NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_phone,@"username",_pwTextField.text,@"password",_phone,@"phone",_codeTextfield.text,@"code", nil];
        [self requestWithPath:bUserResgisterPath params:params httpMehtod:kHttpPostMethod HUDString:@"" success:^(MKNetworkOperation *operation) {
            
            [self _skip:YES];
        } error:^(MKNetworkOperation *operation, NSError *error) {
            
        }];
    }else if (_operation == ForgetSetPassword){   // 用户找回密码操作
        NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_phone,@"userName",_pwTextField.text,@"newPassword",_codeTextfield.text,@"code", nil];
        [self requestWithPath:bUserFindPWPath params:params httpMehtod:kHttpPostMethod HUDString:@"" success:^(MKNetworkOperation *operation) {
            kHDebug;
            [self _skip:NO];
        } error:^(MKNetworkOperation *operation, NSError *error) {
            
        }];

    }else {
        // 修改密码
        NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_oldPWTextField.text,@"oldPassword", _phone,@"userName",_pwTextField.text,@"newPassword",_codeTextfield.text,@"code", nil];
        [self requestWithPath:bUserModifyPWPath params:params httpMehtod:kHttpPostMethod HUDString:@"" success:^(MKNetworkOperation *operation) {
            kHDebug;
            [self _skip:NO];
        } error:^(MKNetworkOperation *operation, NSError *error) {
            
        }];
    }
    
}

- (BOOL)vertifyParams {
    if (_codeTextfield.text.length == 0) {
        HUD(@"请输入验证码！");
        return NO;
    }
    
    if (_pwTextField.text.length == 0) {
        HUD(@"请输入密码！");
        return NO;
    }
    if (_rePwTextField.text.length == 0) {
        HUD(@"请再次输入密码！");
        return NO;
    }
    if (![_pwTextField.text isEqualToString:_rePwTextField.text]) {
        HUD(@"两次输入的密码不一致！");
        return NO;
    }
    
    if (_pwTextField.text.length<6 || _pwTextField.text.length>12) {
        HUD(@"密码在6到12位之间！");
        return NO;
    }

    return YES;
}

#pragma mark - Private
/**
 *@brief 注册成功或密码设置成功，跳转到相应的页面
 *@param isregister YES是注册 NO密码设置
 */
- (void)_skip:(BOOL)isregister {
    RegisterSuccessViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"RegisterSuccessViewControllerId"];
    vc.isRegister = isregister;
    [self.navigationController pushViewController:vc animated:YES];
}

/**
 *@brief 倒计时
 */
-(void)_setTime
{
    btnVerifCode.enabled=NO;
    __block int timeout=60; //倒计时时间
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_source_t _timer = dispatch_source_create(DISPATCH_SOURCE_TYPE_TIMER, 0, 0,queue);
    dispatch_source_set_timer(_timer,dispatch_walltime(NULL, 0),1.0*NSEC_PER_SEC, 0); //每秒执行
    dispatch_source_set_event_handler(_timer, ^{
        if(timeout<=0){ //倒计时结束，关闭
            dispatch_source_cancel(_timer);
            dispatch_async(dispatch_get_main_queue(), ^{
                btnVerifCode.userInteractionEnabled=YES;
                [btnVerifCode setTitle:@"重发验证码" forState:UIControlStateNormal];
                btnVerifCode.enabled=YES;
            });
        }else{
            NSString *strTime = [NSString stringWithFormat:@"%ds后重发",timeout];
            dispatch_async(dispatch_get_main_queue(), ^{
                btnVerifCode.enabled=YES;
                [btnVerifCode setTitle:strTime forState:UIControlStateNormal];
                btnVerifCode.enabled=NO;
            });
            timeout--;
        }
    });
    dispatch_resume(_timer);
}

/**
 *@brief 获取注册短信验证码
 */
- (void)_requesSmsCode {
    NSMutableDictionary *param = [NSMutableDictionary dictionaryWithObjectsAndKeys:_phone,@"phone", nil];
    
   MKNetworkOperation *op = [self requestWithPath:bMessageSendPath params:param httpMehtod:kHttpGetMethod HUDString:@"正在发送..." success:^(MKNetworkOperation *operation) {
        [self _setTime];
    } error:^(MKNetworkOperation *operation, NSError *error) {
        
    }];
    
    op.shouldNotCacheResponse = YES;
}

@end
