//
//  TipViewController.m
//  Glshop
//
//  Created by River on 15-1-8.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "TipViewController.h"
#import "ReChargeViewController.h"
#import "ChargePaymentViewController.h"

@interface TipViewController ()

@end

@implementation TipViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"我的钱包";
}

- (void)loadSubViews {
    
    NSString *title = _fromPayment ? @"货款账户不足！" : @"保证金不足！";
//    NSString *tipStrmoney = _fromPayment ? @"您的货款账户余额为%@元，可能在下次交易中由于交易保证金不足造成不能交易，建议您立即缴纳交易保证金" : @"您的交易保证金余额为%@元，可能在下次交易中由于交易保证金不足造成不能交易，建议您立即缴纳交易保证金";
  
    UILabel *tilteLabel = [UILabel labelWithTitle:title];
    tilteLabel.font = [UIFont boldSystemFontOfSize:18.f];
    tilteLabel.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:tilteLabel];
    [tilteLabel makeConstraints:^(MASConstraintMaker *make) {
        make.height.mas_equalTo(25);
        make.top.mas_equalTo(40);
        make.width.mas_equalTo(200);
        make.centerX.mas_equalTo(self.view.centerX);
    }];
    
    NSString *tipStr;
    if ([_amountMoney floatValue] > 0) {
        tipStr = [NSString stringWithFormat:@"您的交易保证金余额为%@元，可能在下次交易中由于交易保证金不足造成不能交易，建议您立即缴纳交易保证金",_amountMoney];
    }else {
        tipStr = [NSString stringWithFormat:@"您还没有交易保证金，赶紧去缴纳交易保证金吧，否则将不能在平台交易。"];
    }
    
    if ([_amountMoney floatValue] <= 0 && _fromPayment) {
        tipStr = @"您的货款账户余额为0，赶紧去充值吧，否则将无法进行交易哦！";
    }
    
    UILabel *tipLabel = [UILabel labelWithTitle:tipStr];
    tipLabel.font = [UIFont boldSystemFontOfSize:16.f];
    tipLabel.lineBreakMode = NSLineBreakByClipping;
    tipLabel.textColor = [UIColor grayColor];
    tipLabel.numberOfLines = 4;
    tipLabel.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:tipLabel];
    
    [tipLabel makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(15);
        make.centerX.mas_equalTo(self.view.centerX);
        make.right.mas_equalTo(self.view.right).offset(-15);
        make.top.mas_equalTo(tilteLabel.bottom).offset(10);
    }];
    
    NSString *btnTitle = _fromPayment ? @"货款充值" : @"缴纳保证金";
    UIButton *button = [UIFactory createBtn:BlueButtonImageName bTitle:btnTitle bframe:CGRectZero];
    [button addTarget:self action:@selector(charegeAction:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button];
    
    [button makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(tipLabel.leading);
        make.right.mas_equalTo(tipLabel.right);
        make.top.mas_equalTo(tipLabel.bottom).offset(20);
        make.height.mas_equalTo(35);
    }];

}

- (void)charegeAction:(UIButton *)button {
    UIViewController *vc;
    if (_fromPayment) {
        vc = [[ChargePaymentViewController alloc] init];
    }else {
        vc = [[ReChargeViewController alloc] init];
    }
    [self.navigationController pushViewController:vc animated:YES];
}

@end
