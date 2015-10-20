//
//  PayByBankViewController.m
//  Glshop
//
//  Created by Appabc on 15/3/12.
//  Copyright (c) 2015年 appabc. All rights reserved.
//


#import "PayByOfflineViewController.h"
#import "HLCheckbox.h"
#import "ChargeViewController.h"
#import "WebViewController.h"
#import "IQKeyboardManager.h"

@interface PayByOfflineViewController ()<UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate,UIActionSheetDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, assign) NSInteger dataSource;

@property (nonatomic, strong) HLCheckbox *box;
@property (nonatomic, strong) UILabel *agreeLabel;
@property (nonatomic, strong) UIButton *nextBtn;
@property (nonatomic, strong) UITextField *fieldTun;
@property (nonatomic, strong) UITextField *tfmoney;

@property (nonatomic, strong) UIView *checkView;

@property (nonatomic, assign) UserType type;

/**
 *@brief 充值金额
 */
@property (nonatomic, assign) NSInteger chargeAmount;

@end

@implementation PayByOfflineViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    UserInstance *userInstance = [UserInstance sharedInstance];
    BOOL isAuth;
    if ([userInstance.user.authstatus[DataValueKey] integerValue] == 1) {
        isAuth = YES;
    }else {
        isAuth = NO;
    }
    _dataSource = 4;
    _type = unKnowType;
    self.title = @"线下支付";
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    //    [[IQKeyboardManager sharedManager] setEnableAutoToolbar:NO];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    
    //    [[IQKeyboardManager sharedManager] setEnableAutoToolbar:YES];
}

- (void)loadSubViews {
    UILabel *labelttile = [UILabel labelWithTitle:pay_offline_title];
    labelttile.font = [UIFont systemFontOfSize:FONT_14];
    labelttile.frame = CGRectMake(10, 10, SCREEN_WIDTH-20, 30);
    labelttile.numberOfLines = 0;
    [labelttile sizeToFit];
    [self.view addSubview:labelttile];
    
    UILabel *labeltel = [UILabel labelWithTitle:pay_offline_tel];
    labeltel.font = [UIFont systemFontOfSize:FONT_14];
    labeltel.frame = CGRectMake(10, 55, SCREEN_WIDTH-20, 15);
    labeltel.numberOfLines = 0;
    [labeltel sizeToFit];
    [self.view addSubview:labeltel];
    
    UILabel *labeltelv = [UILabel labelWithTitle:pay_offline_tel_v];
    labeltelv.font = [UIFont systemFontOfSize:FONT_14];
    labeltelv.frame = CGRectMake(10, 72, SCREEN_WIDTH-20, 15);
    labeltelv.numberOfLines = 0;
    labeltelv.textColor = [UIColor greenColor];
    [labeltelv sizeToFit];
    [self.view addSubview:labeltelv];
    
    
    UILabel *labelad = [UILabel labelWithTitle:pay_offline_address_k];
    labelad.font = [UIFont systemFontOfSize:FONT_14];
    labelad.frame = CGRectMake(10, 100, SCREEN_WIDTH-20, 15);
    labelad.numberOfLines = 0;
    [labelad sizeToFit];
    [self.view addSubview:labelad];
    
    UILabel *labeladv = [UILabel labelWithTitle:pay_offline_address_v];
    labeladv.font = [UIFont systemFontOfSize:FONT_14];
    labeladv.frame = CGRectMake(10, 117, SCREEN_WIDTH-20, 30);
    labeladv.numberOfLines = 0;
    labeladv.textColor = [UIColor blueColor];
    [labeladv sizeToFit];
    [self.view addSubview:labeladv];

    
}
@end

