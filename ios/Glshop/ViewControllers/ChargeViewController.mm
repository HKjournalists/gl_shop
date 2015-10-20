//
//  ChargeViewController.m
//  Glshop
//
//  Created by River on 15-1-8.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "ChargeViewController.h"
#import "PayByBankViewController.h"
#import "PayByOfflineViewController.h"
#import "UPPayPlugin.h"
#import "TipSuccessViewController.h"

#define kMode_Development             @"01"
#define kMode_Distribution             @"00"

#define kPaySuccess @"success"
#define kPayCancel  @"cancel"

@interface ChargeViewController ()<UITableViewDataSource,UITableViewDelegate,UPPayPluginDelegate>

@property (nonatomic, strong) UITableView *tableView;
/**
 *@brief 订单号
 */
@property (nonatomic, copy) NSString *oid;

@end

@implementation ChargeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vheight = 200;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    _tableView.backgroundColor = [UIColor clearColor];
    _tableView.scrollEnabled = NO;
    [self.view addSubview:_tableView];
    
    UIView *tipView = [UIFactory createPromptViewframe:CGRectMake(10, _tableView.vbottom+10, self.view.vwidth-20, 100) tipTitle:nil];
    NSString *str;
    if (_chargeType == ChargeTypeMargin) {
        str = @"交易保证金账号由上海浦东发展银行托管，直接汇入上海浦东发展银行指定账号，账号受上海浦东发展银行进行资金监控";
    }else if (_chargeType == ChargeTypePayment) {
        str = @"货款账号由上海浦东发展银行托管，直接汇入上海浦东发展银行指定账号，账号受上海浦东发展银行进行资金监控";
    }
    UILabel *label1 = [UILabel labelWithTitle:str];
    label1.font = [UIFont systemFontOfSize:FONT_13];
    label1.frame = CGRectMake(10, 40, 280, 20);
    label1.numberOfLines = 0;
    [label1 sizeToFit];
    [tipView addSubview:label1];
    [self.view addSubview:tipView];
}

#pragma mark - UITableView DataSource/Delegate

#define sectionHigh 5
- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    return sectionHigh;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return sectionHigh;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, sectionHigh)];
    return view;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, sectionHigh)];
    return view;
    
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return section == 0 ? 1 : 3;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.textLabel.font = UFONT_16_B;
    cell.textLabel.textColor = C_BLACK;
    cell.detailTextLabel.font = UFONT_16_B;
    if (indexPath.section == 0) {
        cell.textLabel.text = @"充值金额";
        
        NSString *lex = [Utilits formatMoney:_chartAmount];
        cell.detailTextLabel.text = [NSString stringWithFormat:@"%@元",lex];
        cell.detailTextLabel.textColor = [UIColor redColor];
    }else {
//        NSArray *titles = @[@"在线充值",@"银行转账",@"线下支付"];
        NSArray *imgNames = @[@"wallet_icon_online",@"wallet_icon_transfer",@"wallet_icon_offline"];
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
//        cell.textLabel.text = titles[indexPath.row];
        cell.imageView.image = [UIImage imageNamed:imgNames[indexPath.row]];
    }
    
    return cell;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    if (section == 1) {
        return @"支付方式";
    }
    return nil;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    if (indexPath.section == 1) {
        if (indexPath.row == 0) {
            NSString *type;
            if (_chargeType == ChargeTypeMargin) {
                type = @"0";
            }else if (_chargeType == ChargeTypePayment) {
                type = @"1";
            }
            NSString *moneyStr = [NSString stringWithFormat:@"%f",_chartAmount];
            NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:type,@"type",moneyStr,@"balance", nil];
            [self showHUD];
            [self requestWithURL:bGetBankTNPath params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
                [self startPayWithData:responseData];
            } failedBlock:^(ASIHTTPRequest *request) {
                
            }];
        }
        if(indexPath.row == 1){
           PayByBankViewController *vc = [[PayByBankViewController alloc] init];
            vc.chargeMoney = _chartAmount;
            [self.navigationController pushViewController:vc animated:YES];
        }else if(indexPath.row == 2){
            PayByOfflineViewController *vc = [[PayByOfflineViewController alloc]init];
            [self.navigationController pushViewController:vc animated:YES];
        }

    }
}

#pragma mark - Private
/**
 *@brief 调用银联支付
 */
- (void)startPayWithData:(NSDictionary *)responseData {
    NSString *tn = responseData[ServiceDataKey][@"tn"];
    _oid = responseData[ServiceDataKey][@"oid"];
    DLog(FomartObj,tn);
    if (tn.length) {
        [UPPayPlugin startPay:tn mode:kMode_Distribution viewController:self delegate:self];
    }
}

#pragma mark UPPayPluginResult
- (void)UPPayPluginResult:(NSString *)result
{
    NSString* msg = [NSString stringWithFormat:@"支付结果：%@", result];
    DLog(@"%@",msg);
    if ([result isEqualToString:kPaySuccess]) {
        NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObject:_oid forKey:@"oid"];
        [self requestWithURL:bpursereportToUnionPayTradeResult params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
            kASIResultLog;
        } failedBlock:^(ASIHTTPRequest *request) {
            
        }];
        
        [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushMyPurseNotification object:nil];
        TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
        vc.operationType = _chargeType == ChargeTypeMargin ? tip_chargeMarginSuccess : tip_chargePaymentSuccess;
        vc.chargeMoney = _chartAmount;
        [self.navigationController pushViewController:vc animated:NO];
        
    }else if ([result isEqualToString:kPayCancel]) {
        
    }else {
        
    }
}


@end
