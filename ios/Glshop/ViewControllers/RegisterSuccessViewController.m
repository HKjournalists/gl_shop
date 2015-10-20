//
//  RegisterSuccessViewController.m
//  Glshop
//
//  Created by River on 14-11-7.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "RegisterSuccessViewController.h"
#import "CompanyAuthViewController.h"
#import "LoginViewController.h"

@interface RegisterSuccessViewController ()

@end

@implementation RegisterSuccessViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)loadSubViews {
    if (_isRegister) {
        [self loadRegisterSuccessView];
    }else {
        [self loadSetPasswordSuccessView];
    }
}

- (void)loadRegisterSuccessView {
    UIImageView *markView = [[UIImageView alloc] initWithFrame:CGRectMake(SCREEN_WIDTH/2-80/2, 45, 80, 80)];
    markView.image = PNGIMAGE(@"success@2x");
    [self.view addSubview:markView];
    
    UILabel *tipLabel = [UILabel labelWithTitle:@"恭喜您，已注册成功！"];
    tipLabel.frame = CGRectMake(0, markView.vbottom+25, SCREEN_WIDTH, 25);
    tipLabel.textAlignment = NSTextAlignmentCenter;
    tipLabel.font = [UIFont boldSystemFontOfSize:18.f];
    [self.view addSubview:tipLabel];
    
    UILabel *detailLabel = [UILabel labelWithTitle:@"接下来请您提交平台认证信息，否则将无法在长江电商平台交易。"];
    detailLabel.frame = CGRectMake(20, tipLabel.vbottom+20, SCREEN_WIDTH-40, 45);
    detailLabel.numberOfLines = 2;
    detailLabel.textColor = [UIColor lightGrayColor];
    detailLabel.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:detailLabel];
    
    UIButton *sureBtn = [UIButton buttonWithTip:@"立即去认证" target:self selector:@selector(skipAuthCopyRight)];
    sureBtn.frame = CGRectMake(detailLabel.vleft, detailLabel.vbottom+30, detailLabel.vwidth, 40);
    sureBtn.backgroundColor = CJBtnColor;
    [self.view addSubview:sureBtn];
    
    UIButton *skipBtn = [UIButton buttonWithTip:@"知道了以后认证，跳过" target:self selector:@selector(turnToMain)];
    skipBtn.frame = CGRectMake(detailLabel.vleft, sureBtn.vbottom+20, detailLabel.vwidth, sureBtn.vheight);
    skipBtn.backgroundColor = RGB(247, 247, 247, 1);
    skipBtn.layer.borderWidth = 0.5;
    [skipBtn setTitleColor:ColorWithHex(@"#2260b5") forState:UIControlStateNormal];
    skipBtn.layer.borderColor = [UIColor lightGrayColor].CGColor;
    skipBtn.layer.cornerRadius = 1.f;
    [self.view addSubview:skipBtn];
}

- (void)loadSetPasswordSuccessView {
    UIImageView *markView = [[UIImageView alloc] initWithFrame:CGRectMake(SCREEN_WIDTH/2-80/2, 80, 80, 80)];
    markView.image = PNGIMAGE(@"success@2x");
    [self.view addSubview:markView];
    
    UILabel *tipLabel = [UILabel labelWithTitle:@"恭喜您密码重置成功！"];
    tipLabel.frame = CGRectMake(0, markView.vbottom+20, SCREEN_WIDTH, 25);
    tipLabel.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:tipLabel];
    
    UILabel *detailLabel = [UILabel labelWithTitle:@"接下来请您提交平台认证信息，否则将无法在长江电商平台交易。"];
    detailLabel.frame = CGRectMake(20, tipLabel.vbottom+20, SCREEN_WIDTH-40, 45);
    detailLabel.numberOfLines = 2;
    detailLabel.textColor = [UIColor lightGrayColor];
    detailLabel.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:detailLabel];
    
    UIButton *sureBtn = [UIFactory createBtn:BlueButtonImageName bTitle:@"登录长江电商" bframe:CGRectZero];
    [sureBtn addTarget:self action:@selector(skipToLogin) forControlEvents:UIControlEventTouchUpInside];
    sureBtn.frame = CGRectMake(10, detailLabel.vbottom+30, SCREEN_WIDTH-20, 40);
    [self.view addSubview:sureBtn];

}

- (void)skipAuthCopyRight {
    LoginViewController *vc = [self loginvc];
    vc.skipToAuth = YES;
    [self.navigationController popToViewController:vc animated:YES];
}

- (void)skipToLogin {
    UIViewController *vc = [self findDesignatedViewController:[LoginViewController class]];
    if (vc) {
        [self.navigationController popToViewController:vc animated:YES];
    }else {
        vc = [[LoginViewController alloc] init];
        [self.navigationController popToRootViewControllerAnimated:NO];
        UIViewController *nav = [[UIApplication sharedApplication] keyWindow].rootViewController;
        if ([nav isKindOfClass:[UINavigationController class]]) {
            [(UINavigationController *)nav pushViewController:vc animated:YES];
        }
    }
}

- (void)turnToMain {
    LoginViewController *vc = [self findDesignatedViewController:[LoginViewController class]];
    if (vc) {
        [self.navigationController popToViewController:vc animated:YES];
    }else {
        [self.navigationController popToRootViewControllerAnimated:YES];
    }
}

#pragma mark - mark Private 
- (LoginViewController *)loginvc {
    for (UIViewController *vc in self.navigationController.viewControllers) {
        if ([vc isKindOfClass:[LoginViewController class]]) {
            return (LoginViewController *)vc;;
        }
    }
    return nil;
}

@end
