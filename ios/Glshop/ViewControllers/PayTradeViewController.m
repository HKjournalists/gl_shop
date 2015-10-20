//
//  PayTradeViewController.m
//  Glshop
//
//  Created by River on 15-1-29.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  支付交易货款

#import "PayTradeViewController.h"
#import "TipSuccessViewController.h"
#import "MypurseViewController.h"
#import "MyPurseInfoModel.h"

static NSInteger chargeAlertViewTag = 2003;

@interface PayTradeViewController () <UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate,UIAlertViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) UIButton *nextBtn;
@property (nonatomic, strong) UITextField *codeAuthtf;
@property (nonatomic, strong) UITextField *moneytf;
@property (nonatomic, strong) UITextField *pwtf;
@property (nonatomic, strong) UIButton *btnVerifCode;

/**
 *@brief 钱包余额信息
 */
Strong MyPurseInfoModel *moneyModel;

@end

@implementation PayTradeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"支付交易货款";
    
    [self requestNet];
}

- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    _tableView.sectionFooterHeight = 5;
    _tableView.sectionHeaderHeight = 5;
    _tableView.vtop -= 20;
    _tableView.vheight -= kTopBarHeight+55;
    [self.view addSubview:_tableView];
    _tableView.hidden = YES;
    
    _nextBtn = [UIFactory createBtn:BlueButtonImageName bTitle:btnTitle_sure_pay bframe:CGRectZero];
    [_nextBtn addTarget:self action:@selector(payMoney) forControlEvents:UIControlEventTouchUpInside];
    _nextBtn.hidden = YES;
    [self.view addSubview:_nextBtn];
    
    [_nextBtn makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view).offset(15);
        make.right.mas_equalTo(self.view).offset(-15);
        make.height.mas_equalTo(40);
        make.bottom.mas_equalTo(self.view).offset(-10);
    }];
}

- (void)requestNet {
    [super requestNet];
    __weak typeof(self) this = self;
    [self requestWithURL:bGetPurseAccountInfo
                  params:nil
              HTTPMethod:kHttpPostMethod
           completeBlock:^(ASIHTTPRequest *request, id responseData) {
               kASIResultLog;
               [this handleNetData:responseData];
           } failedBlock:^(ASIHTTPRequest *request) {
               
           }];
}

- (void)handleNetData:(id)responseData {
    NSArray *dicArray = responseData[ServiceDataKey][@"result"];
    
    if (!dicArray.count) {
        return;
    }
    
    for (NSDictionary *dataDic in dicArray) {
        NSDictionary *aDic = dataDic[@"passtype"];
        if ([aDic[DataValueKey] integerValue] != 0) {
            _moneyModel = [[MyPurseInfoModel alloc] initWithDataDic:dataDic];
        }
    }
    
    _tableView.hidden = NO;
    _nextBtn.hidden = NO;
    [_tableView reloadData];
}

- (void)tipErrorCode:(NSInteger)errorCode {
    if (errorCode == 10005) {
        [self showTip:@"密码错误,请重新输入"];
    }else if (errorCode == 100005014) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"货款金额不足" message:@"您的货款余额不足，不能完成支付。" delegate:self cancelButtonTitle:nil otherButtonTitles:globe_cancel_str,@"去充值", nil];
        alert.tag = chargeAlertViewTag;
        [alert show];
    }else {
        [super tipErrorCode:errorCode];
    }
}

#pragma mark - UITableView Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return section == 0 ? 2 : 4;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    cell.textLabel.font = [UIFont systemFontOfSize:16.f];
    
    if (indexPath.section == 0) {
        if (indexPath.row == 0) {
            cell.textLabel.text = pay_needPay_money;
            cell.detailTextLabel.textColor = [UIColor redColor];
            cell.detailTextLabel.text = [_payAmount stringValue];
            
        }else {
            cell.textLabel.text = pay_pgoods_balance;
            cell.textLabel.textColor = [UIColor blackColor];
            cell.detailTextLabel.text = [Utilits formatMoney:[_moneyModel.amount doubleValue] isUnit:false];
        }
        UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:cell.textLabel.font unitType:unint_yuan];
        [cell addSubview:unitlabel];
        
    }else {
        if (indexPath.row == 0) {
            cell.textLabel.textColor = [UIColor grayColor];
            cell.textLabel.text = sms_valide;
        }else if (indexPath.row == 1) {
            cell.textLabel.text = sms_post_to;
            UserInstance *uins = [UserInstance sharedInstance];
            UILabel *phoneLab = [UILabel labelWithTitle:uins.user.username];
            phoneLab.textColor = RGB(40, 113, 213, 1);
            [cell addSubview:phoneLab];
            [phoneLab makeConstraints:^(MASConstraintMaker *make) {
                make.leading.mas_equalTo(cell.textLabel.right);
                make.width.mas_equalTo(200);
                make.top.mas_equalTo(cell.contentView);
                make.height.mas_equalTo(44);
            }];
            
        }else if (indexPath.row == 2) {
            _codeAuthtf = [UITextField textFieldWithPlaceHodler:sms_input withDelegate:self];
            _codeAuthtf.textColor = [UIColor grayColor];
            _codeAuthtf.keyboardType = UIKeyboardTypeNumberPad;
            [cell.contentView addSubview:_codeAuthtf];
            [_codeAuthtf makeConstraints:^(MASConstraintMaker *make) {
                make.leading.mas_equalTo(cell).offset(15);
                make.width.mas_equalTo(200);
                make.height.mas_equalTo(44);
                make.top.mas_equalTo(cell.contentView.top);
            }];
            
            _btnVerifCode = [UIFactory createBtn:@"Buy_sell_publish" bTitle:sms_get bframe:CGRectZero];
            [_btnVerifCode addTarget:self action:@selector(getCode:) forControlEvents:UIControlEventTouchUpInside];
            [_btnVerifCode setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
            _btnVerifCode.titleLabel.font = [UIFont boldSystemFontOfSize:15.f];
            [cell.contentView addSubview:_btnVerifCode];
            [_btnVerifCode makeConstraints:^(MASConstraintMaker *make) {
                make.width.mas_equalTo(100);
                make.right.mas_equalTo(cell.right).offset(-8);
                make.height.mas_equalTo(30);
                make.top.mas_equalTo(cell).offset(7);
                
            }];
        }else if (indexPath.row == 3){
            [cell addSubview:self.pwtf];
        }
    }
    
    return cell;
}

#pragma mark - UIActions
- (void)getCode:(UIButton *)btn {
    [self _requesSmsCode];
    btn.enabled = NO;
}

- (void)payMoney {
    if (_codeAuthtf.text.length == 0) {
        HUD(placehold_input_authcode);
        return;
    }
    
    if (_pwtf.text.length == 0) {
        HUD(placehold_input_login_pw);
        return;
    }
    
    UserInstance *userInstance = [UserInstance sharedInstance];
    NSString *append = [NSString stringWithFormat:@"%@%@",userInstance.user.username,_pwtf.text];
    NSString *securityStr = [append md5];
    
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_contractId,path_key_contractId,_codeAuthtf.text,path_key_authcode,securityStr,path_key_password, nil];
    __block typeof(self) this = self;
    [self showHUD];
    [self requestWithURL:bpayContractFundsOnline params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        MypurseViewController *purseVC = [self findDesignatedViewController:[MypurseViewController class]];
        if (purseVC) {
            [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushMyPurseNotification object:nil];
        }
        
        TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
        vc.operationType = tip_contract_pay_success;
        vc.contractId = this.contractId;
        [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushContractNotification object:nil];
        [this.navigationController pushViewController:vc animated:YES];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

- (void)_requesSmsCode {
    UserInstance *uins = [UserInstance sharedInstance];
    NSMutableDictionary *param = [NSMutableDictionary dictionaryWithObjectsAndKeys:uins.user.username,@"phone", nil];
    __block typeof(self) this = self;
    [self requestWithURL:bMessageSendPath
                  params:param
              HTTPMethod:kHttpGetMethod
           completeBlock:^(ASIHTTPRequest *request, id responseData) {
               [this _setTime];
           } failedBlock:^(ASIHTTPRequest *request) {
               this.btnVerifCode.enabled = YES;
           }];
}

/**
 *@brief 倒计时
 */
-(void)_setTime
{
    _btnVerifCode.enabled=NO;
    __block int timeout=60; //倒计时时间
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_source_t _timer = dispatch_source_create(DISPATCH_SOURCE_TYPE_TIMER, 0, 0,queue);
    dispatch_source_set_timer(_timer,dispatch_walltime(NULL, 0),1.0*NSEC_PER_SEC, 0); //每秒执行
    dispatch_source_set_event_handler(_timer, ^{
        if(timeout<=0){ //倒计时结束，关闭
            dispatch_source_cancel(_timer);
            dispatch_async(dispatch_get_main_queue(), ^{
                _btnVerifCode.userInteractionEnabled=YES;
                [_btnVerifCode setTitle:btntitle_repost_code forState:UIControlStateNormal];
                _btnVerifCode.enabled=YES;
            });
        }else{
            NSString *strTime = [NSString stringWithFormat:@"%d秒后重发",timeout];
            dispatch_async(dispatch_get_main_queue(), ^{
                _btnVerifCode.enabled=YES;
                [_btnVerifCode setTitle:strTime forState:UIControlStateNormal];
                _btnVerifCode.enabled=NO;
            });
            timeout--;
        }
    });
    dispatch_resume(_timer);
}

#pragma mark - Getter
- (UITextField *)moneytf {
    if (!_moneytf) {
        _moneytf = [UITextField textFieldWithPlaceHodler:pay_input_money withDelegate:self];
        _moneytf.textAlignment = NSTextAlignmentRight;
        _moneytf.keyboardType = UIKeyboardTypeNumberPad;
        _moneytf.frame = CGRectMake(SCREEN_WIDTH-150-15, 0, 150, 44);
    }
    return _moneytf;
}

- (UITextField *)pwtf {
    if (!_pwtf) {
        _pwtf = [UITextField textFieldWithPlaceHodler:placehold_input_login_pw withDelegate:self];
        _pwtf.secureTextEntry = YES;
        _pwtf.frame = CGRectMake(15, 0, 200, 44);
    }
    return _pwtf;
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex) {
        MypurseViewController *vc = [[MypurseViewController alloc] init];
        vc.segment.selectedSegmentIndex = 1;
        [self.navigationController pushViewController:vc animated:YES];
    }
}

@end
