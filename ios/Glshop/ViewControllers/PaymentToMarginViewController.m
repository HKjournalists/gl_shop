//
//  PaymentToMarginViewController.m
//  Glshop
//
//  Created by River on 15-1-19.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  货款转保证金

#import "PaymentToMarginViewController.h"
#import "MypurseViewController.h"
#import "TipSuccessViewController.h"

@interface PaymentToMarginViewController ()<UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) UIButton *nextBtn;
@property (nonatomic, strong) UIView *checkView;
@property (nonatomic, strong) UITextField *codeAuthtf;
@property (nonatomic, strong) WTReTextField *moneytf;
@property (nonatomic, strong) UITextField *pwtf;
@property (nonatomic, strong) UIButton *btnVerifCode;

@end

@implementation PaymentToMarginViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.title = @"货款转保证金";
    
}

- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    _tableView.sectionFooterHeight = 5;
    _tableView.sectionHeaderHeight = 5;
    _tableView.vtop -= 20;
    _tableView.vheight -= kTopBarHeight+55;
    
    
    UIView *footerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 120)];
    footerView.backgroundColor = [UIColor clearColor];
    UIView *tipView = [UIFactory createPromptViewframe:CGRectMake(10, 10, self.view.vwidth-20, 100) tipTitle:nil];
    UILabel *label1 = [UILabel labelWithTitle:@"交易保证金账号由上海浦东发展银行托管，直接汇入上海浦东发展银行指定账号，账号受上海浦东发展银行进行资金监控。"];
    label1.font = [UIFont systemFontOfSize:14.f];
    label1.frame = CGRectMake(10, 40, 280, 20);
    label1.numberOfLines = 0;
    [label1 sizeToFit];
    [tipView addSubview:label1];
    [footerView addSubview:tipView];
    _tableView.tableFooterView = footerView;
    
    [self.view addSubview:_tableView];
    
    _nextBtn = [UIFactory createBtn:BlueButtonImageName bTitle:@"确认转款至交易保证金账户" bframe:CGRectZero];
    [_nextBtn addTarget:self action:@selector(postAuth) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:_nextBtn];
    
    [_nextBtn makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view).offset(15);
        make.right.mas_equalTo(self.view).offset(-15);
        make.height.mas_equalTo(40);
        make.bottom.mas_equalTo(self.view).offset(-10);
    }];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return section == 0 ? 2 : 4;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    cell.textLabel.font = UFONT_16_B;
    cell.textLabel.textColor = C_BLACK;
    cell.detailTextLabel.font = UFONT_16_B;
    cell.detailTextLabel.textColor = C_GRAY;
    
    if (indexPath.section == 0) {
        if (indexPath.row == 0) {
            cell.textLabel.text = @"可用余额";
            MypurseViewController *vc = [self findDesignatedViewController:[MypurseViewController class]];
            NSString *lex = [Utilits formatMoney:[vc.currentPurse.amount  doubleValue]];
            cell.detailTextLabel.text = lex;
            cell.detailTextLabel.textColor = [UIColor redColor];
            
            
        }else {
            cell.textLabel.text = @"转出金额";
            UITextField *pass = self.moneytf;
            pass.font = UFONT_16_B;
            pass.textColor = C_GRAY;
            [cell addSubview:pass];
        }
        UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:cell.textLabel.font unitType:unint_yuan];
        unitlabel.font = UFONT_16_B;
        unitlabel.textColor = C_GRAY;
        [cell addSubview:unitlabel];

    }else {
        if (indexPath.row == 0) {
            cell.textLabel.textColor = [UIColor grayColor];
            cell.textLabel.text = @"短信验证:";
            cell.textLabel.font = UFONT_14;
        }else if (indexPath.row == 1) {
            cell.textLabel.text = @"验证码发送至:";
            UserInstance *uins = [UserInstance sharedInstance];
            UILabel *phoneLab = [UILabel labelWithTitle:uins.user.username];
            phoneLab.textColor = RGB(40, 113, 213, 1);
            phoneLab.font = UFONT_16_B;
            [cell addSubview:phoneLab];
            [phoneLab makeConstraints:^(MASConstraintMaker *make) {
                make.leading.mas_equalTo(cell.textLabel.right);
                make.width.mas_equalTo(200);
                make.top.mas_equalTo(cell.contentView);
                make.height.mas_equalTo(44);
            }];
            
        }else if (indexPath.row == 2) {
            _codeAuthtf = [UITextField textFieldWithPlaceHodler:sms_input withDelegate:self];
            _codeAuthtf.textColor = C_GRAY;
            _codeAuthtf.keyboardType = UIKeyboardTypeNumberPad;
            _codeAuthtf.font = UFONT_16_B;
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
            //pwtf.textColor = C_GRAY;
            UITextField *pass = self.pwtf;
            pass.font = UFONT_16_B;
            pass.textColor = C_GRAY;
            [cell addSubview:pass];
        }
    }
 
    return cell;
}

#pragma mark - UIAcion
- (void)postAuth {
    if (_moneytf.text.length == 0) {
        HUD(placehold_money);
        return;
    }
    
    if (_codeAuthtf.text.length == 0) {
        HUD(sms_input);
        return;
    }
    
    if (_pwtf.text.length == 0) {
        HUD(placehold_input_login_pw);
        return;
    }
    [self showHUD];
    UserInstance *uins = [UserInstance sharedInstance];
    NSString *append = [NSString stringWithFormat:@"%@%@",uins.user.username,_pwtf.text];
    NSString *securityStr = [append md5];
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_moneytf.text,@"balance",_codeAuthtf.text,@"smsCode",securityStr,@"password", nil];
    __block typeof(self) this = self;
    [self requestWithURL:bdepositToGuaranty params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

- (void)tipErrorCode:(NSInteger)errorCode {
    if (errorCode == 10005) {
        [self showTip:@"密码错误,请重新输入"];
    }else {
        [super tipErrorCode:errorCode];
    }
}

#pragma mark - Private
- (void)handleNetData:(id)responseData {
    [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushMyPurseNotification object:nil];
    TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
    vc.margin = [_moneytf.text floatValue];
    vc.operationType = tip_to_margin_success;
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)getCode:(UIButton *)btn {
    [self _requesSmsCode];
    btn.enabled = NO;
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
                [_btnVerifCode setTitle:@"重发验证码" forState:UIControlStateNormal];
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
- (WTReTextField *)moneytf {
    if (!_moneytf) {
        _moneytf = [[WTReTextField alloc] init];
        _moneytf.placeholder = placehold_money;
        _moneytf.delegate = self;
        _moneytf.textAlignment = NSTextAlignmentRight;
        _moneytf.keyboardType = UIKeyboardTypeDecimalPad;
        _moneytf.pattern = @"^[0-9]+(.[0-9]{1,2})?$";
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

@end
