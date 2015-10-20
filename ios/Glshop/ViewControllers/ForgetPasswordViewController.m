//
//  ForgetPasswordViewController.m
//  Glshop
//
//  Created by River on 14-11-7.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "ForgetPasswordViewController.h"
#import "SettingPasswordViewController.h"
#import "NetEngine.h"
#import "IQKeyboardManager.h"
#import "WPHotspotLabel.h"
#import "WPAttributedStyleAction.h"
#import "NSString+WPAttributedMarkup.h"

@interface ForgetPasswordViewController () <UIAlertViewDelegate>

@property (nonatomic, strong) WPHotspotLabel *tipActionLabel;
@property (nonatomic, strong) UIWebView *webView;

@end

@implementation ForgetPasswordViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
//    self.shouldShowFailView = NO;
    [self refreshCode];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    
    [[IQKeyboardManager sharedManager] setEnableAutoToolbar:YES];
    [[IQKeyboardManager sharedManager] setShouldResignOnTouchOutside:NO];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    [[IQKeyboardManager sharedManager] setShouldResignOnTouchOutside:YES];
    [[IQKeyboardManager sharedManager] setEnableAutoToolbar:NO];
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    
    [_userNameField becomeFirstResponder];

}

- (void)loadSubViews {
    UIImageView *backgroundView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 15, self.view.vwidth, 90)];
    backgroundView.image = [UIImage imageNamed:@"白色列表2条"];
    backgroundView.userInteractionEnabled = YES;
    [self.view addSubview:backgroundView];
    
    _userNameField = [UITextField textFieldWithPlaceHodler:@"请输入手机号" withDelegate:self];;
    _userNameField.frame = CGRectMake(10, 7.5, SCREEN_WIDTH-10, 30);
    _userNameField.keyboardType = UIKeyboardTypeNumberPad;
    [backgroundView addSubview:_userNameField];
    
    _codeField = [UITextField textFieldWithPlaceHodler:sms_input withDelegate:self];
    _codeField.frame = CGRectMake(_userNameField.vleft, _userNameField.vbottom+15, _userNameField.vwidth-150, _userNameField.vheight);
    _codeField.keyboardType = UIKeyboardTypeNumberPad;
    [backgroundView addSubview:_codeField];
    
    _codeImageView = [[UIImageView alloc] initWithFrame:CGRectMake(_codeField.vright+30, _codeField.vtop+14, 80, _codeField.vheight)];
    _codeImageView.image = [UIImage imageNamed:@"password_security-code"];
    [self.view addSubview:_codeImageView];
    
    _refrushBtn = [UIButton buttonWithTip:@"刷新" target:self selector:@selector(refreshCode)];
    _refrushBtn.frame = CGRectMake(_codeImageView.vright+5, _codeImageView.vtop, 33, _codeImageView.vheight);
    [_refrushBtn setImage:[UIImage imageNamed:@"password_refresh"] forState:UIControlStateNormal];
    [self.view addSubview:_refrushBtn];
    
    _nextBtn = [UIFactory createBtn:BlueButtonImageName bTitle:btntitle_next bframe:CGRectZero];
    [_nextBtn addTarget:self action:@selector(gotoSetVC) forControlEvents:UIControlEventTouchUpInside];
    _nextBtn.frame = CGRectMake(10, backgroundView.vbottom+20, SCREEN_WIDTH-20, 40);
    _nextBtn.layer.cornerRadius = 2.5f;
    [self.view addSubview:_nextBtn];
    
    UIView *view = [UIFactory createPromptViewframe:CGRectMake(_nextBtn.vleft, _nextBtn.vbottom+20, _nextBtn.vwidth, 100) tipTitle:nil];
    _tipActionLabel = [[WPHotspotLabel alloc] initWithFrame:CGRectMake(20, 38, view.vwidth-20, 50)];
    _tipActionLabel.numberOfLines = 3;
    NSDictionary* style3 = @{@"body":@[[UIFont fontWithName:@"HelveticaNeue" size:14.0],ColorWithHex(@"#ba9057")],
                             @"help":[WPAttributedStyleAction styledActionWithAction:^{
                                 if (_webView == nil) {
                                     _webView = [[UIWebView alloc] init];
                                 }
                                 NSURL *url = [NSURL URLWithString:kAppTel];
                                 NSURLRequest *request = [NSURLRequest requestWithURL:url];
                                 [_webView loadRequest:request];
                             }],
                             @"link": @[[UIFont boldSystemFontOfSize:16.f],ColorWithHex(@"#FF0000"),],
                             };
    
    self.tipActionLabel.attributedText = [@"若您无法通过此方式找回密码,请致电 <help>400-961-6816</help> 联系我司客服为您解决。" attributedStringWithStyleBook:style3];
    [view addSubview:_tipActionLabel];
    [self.view addSubview:view];
}

#pragma mark - UIActions
/**
 *@brief 刷新图片验证码
 */
- (void)refreshCode {
    NSString *token = [Utilits ToGetdeviceToken];
    if (!token.length) {
        return;
    }
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObject:[Utilits ToGetdeviceToken] forKey:@"deviceId"];
    MKNetworkEngine *engine = [[MKNetworkEngine alloc] initWithHostName:kBaseUrl];
    
    MKNetworkOperation *op = [engine operationWithPath:bImageCodePath params:[Utilits packSevrverRequestParams:params] httpMethod:kHttpGetMethod];
    
    [op addCompletionHandler:^(MKNetworkOperation *completedOperation) {
        NSLog(@"--%@--%@--%@",completedOperation.responseData,completedOperation.responseJSON,completedOperation.responseString);

        UIImage *image = [UIImage imageWithData:completedOperation.responseData];
        _codeImageView.image = image;
        
    } errorHandler:^(MKNetworkOperation *completedOperation, NSError *error) {
        
        HUD(kNetError);
    }];
    
    [engine enqueueOperation:op];
    op.shouldNotCacheResponse = YES;
}

/**
 *@brief 验证图片验证码是否正确，如果正确就跳转的密码设置页面
 */
- (void)gotoSetVC {
    
    if (![Utilits isMobilePhone:_userNameField.text]) {
        HUD(@"请输入正确的手机号！");
        return;
    }
    
    if (!_codeField.text.length) {
        HUD(sms_input);
        return;
    }
    
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObject:[Utilits ToGetdeviceToken] forKey:@"deviceId"];
    NSString *token = [Utilits ToGetdeviceToken];
    if (!token.length) {
        return;
    }
    [params setObject:_codeField.text forKey:@"code"];
    [params setObject:_userNameField.text forKey:@"userName"];
    [self showHUD];
    [self requestWithURL:bImageCodeCheckPath params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        SettingPasswordViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"SettingPasswordViewControllerId"];
        vc.phone = _userNameField.text;
        vc.operation = ForgetSetPassword;
        [self.navigationController pushViewController:vc animated:YES];
    } failedBlock:^(ASIHTTPRequest *req){
        
    }];
    
}

#pragma mark - UIAlertView Delegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex) {
        
    }
}

@end
