//
//  TranserViewController.m
//  Glshop
//
//  Created by River on 15-1-8.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "TranserViewController.h"
#import "HLCheckbox.h"
#import "WebViewController.h"
#import "GatherModel.h"
#import "MypurseViewController.h"
#import "TipSuccessViewController.h"


@interface TranserViewController ()<UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) HLCheckbox *box;
@property (nonatomic, strong) UILabel *agreeLabel;
@property (nonatomic, strong) UIButton *nextBtn;
@property (nonatomic, strong) UIView *checkView;
@property (nonatomic, strong) UITextField *codeAuthtf;
@property (nonatomic, strong) UITextField *moneytf;
@property (nonatomic, strong) UITextField *pwtf;
@property (nonatomic, strong) UIButton *btnVerifCode;

@end

@implementation TranserViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.title = @"提现";
    
}

- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    _tableView.vheight -= kTopBarHeight+55;
    [self.view addSubview:_tableView];
    
    _nextBtn = [UIFactory createBtn:BlueButtonImageName bTitle:@"立即提交申请" bframe:CGRectZero];
    [_nextBtn addTarget:self action:@selector(postAuth) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:_nextBtn];
    
    [_nextBtn makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view).offset(15);
        make.right.mas_equalTo(self.view).offset(-15);
        make.height.mas_equalTo(40);
        make.bottom.mas_equalTo(self.view).offset(-10);
    }];
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
    return 3;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (section == 0) {
        return 3;
    }else if (section == 1){
        return 2;
    }else {
        return 5;
    }
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    cell.textLabel.font = [UIFont systemFontOfSize:16.f];
    if (indexPath.section == 0) {
        NSArray *titles = @[@"提现至",@"用户名",@"卡号",];
        cell.textLabel.text = titles[indexPath.row];
        if (indexPath.row == 0) {
            cell.detailTextLabel.text = @"工商银行";
        }else if (indexPath.row == 1) {
            cell.detailTextLabel.text = _gahter.carduser;
        }else if (indexPath.row == 2) {
            cell.detailTextLabel.text = _gahter.bankcard;
        }
    }else if (indexPath.section == 1) {
        if (indexPath.row == 0) {
            cell.textLabel.text = @"可用余额";
            MypurseViewController *vc = [self findDesignatedViewController:[MypurseViewController class]];
            cell.detailTextLabel.text = [vc.currentPurse.amount stringValue];
            
        }else {
            cell.textLabel.text = @"提现金额";
            cell.textLabel.textColor = [UIColor blackColor];
            [cell addSubview:self.moneytf];
        }
        UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:cell.textLabel.font unitType:unint_yuan];
        [cell addSubview:unitlabel];
    }else {
        if (indexPath.row == 0) {
            cell.textLabel.textColor = [UIColor grayColor];
            cell.textLabel.text = @"短信验证";
        }else if (indexPath.row == 1) {
            cell.textLabel.text = @"验证码发送至:";
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
            _codeAuthtf = [UITextField textFieldWithPlaceHodler:@"请输入验证码" withDelegate:self];
            _codeAuthtf.textColor = [UIColor grayColor];
            _codeAuthtf.keyboardType = UIKeyboardTypeNumberPad;
            [cell.contentView addSubview:_codeAuthtf];
            [_codeAuthtf makeConstraints:^(MASConstraintMaker *make) {
                make.leading.mas_equalTo(cell).offset(15);
                make.width.mas_equalTo(200);
                make.height.mas_equalTo(44);
                make.top.mas_equalTo(cell.contentView.top);
            }];
            
            _btnVerifCode = [UIFactory createBtn:@"Buy_sell_publish" bTitle:@"获取验证码" bframe:CGRectZero];
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
        }else {
            
            [cell addSubview:self.checkView];
        }

    }
    
    return cell;
}

#pragma mark - Getter
- (UIView *)checkView {
    if (!_checkView) {
        _checkView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 44)];
        
        _box = [[HLCheckbox alloc] initWithBoxImage:[UIImage imageNamed:@"check_unselected"] selectImage:[UIImage imageNamed:@"check_selected"]];
        _box.frame = CGRectMake(15, 7, 20, 20);
        _box.selected = YES;
        [_checkView addSubview:_box];
        
        __block typeof(self) weakSelf = self;
        _box.tapBlock = ^(BOOL selected) {
            weakSelf.nextBtn.enabled = !selected;
            weakSelf.agreeLabel.textColor = !selected ? [UIColor blackColor] : ColorWithHex(@"#999999");
        };
        
        self.agreeLabel = [UILabel labelWithTitle:@"同意"];
        _agreeLabel.frame = CGRectMake(_box.vright+2, _box.vtop, 40, _box.vheight);
        [_checkView addSubview:_agreeLabel];
        
        UIButton *proBtn = [UIButton buttonWithTip:@"长江电商提现协议" target:self selector:@selector(showProtocal)];
        [proBtn setTitleColor:ColorWithHex(@"#507daf") forState:UIControlStateNormal];
        proBtn.titleLabel.font = [UIFont systemFontOfSize:13.5f];
        proBtn.frame = CGRectMake(_agreeLabel.vright-10, _agreeLabel.vtop+1, 130, 20);
        [_checkView addSubview:proBtn];
    }
    return _checkView;
}

- (UITextField *)moneytf {
    if (!_moneytf) {
        _moneytf = [UITextField textFieldWithPlaceHodler:@"请输入金额" withDelegate:self];
        _moneytf.textAlignment = NSTextAlignmentRight;
        _moneytf.keyboardType = UIKeyboardTypeNumberPad;
        _moneytf.frame = CGRectMake(SCREEN_WIDTH-150-15, 0, 150, 44);
    }
    return _moneytf;
}

- (UITextField *)pwtf {
    if (!_pwtf) {
        _pwtf = [UITextField textFieldWithPlaceHodler:@"请输入登录密码" withDelegate:self];
        _pwtf.secureTextEntry = YES;
        _pwtf.frame = CGRectMake(15, 0, 200, 44);
    }
    return _pwtf;
}

#pragma mark - UIActions 
- (void)postAuth {
    
    if (!_moneytf.text.length) {
        HUD(@"请输入提现金额");
        return;
    }
    
    if (!_codeAuthtf.text.length) {
        HUD(@"请输入验证码");
        return;
    }
    
    if (!_pwtf.text.length) {
        HUD(@"请输入登录密码");
        return;
    }
    
    MypurseViewController *vc = [self findDesignatedViewController:[MypurseViewController class]];
    NSNumber *type = vc.currentPurse.passtype[DataValueKey];
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:self.gahter.gatherId,@"acceptId",self.pwtf.text,@"password",self.codeAuthtf.text,@"validateCode",self.moneytf.text,@"balance",type,@"type", nil];
    __block typeof(self) this = self;
    [self showHUD:@"正在提交..." isDim:NO Yoffset:0];
    [self requestWithURL:bextractCashRequest params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushMyPurseNotification object:nil];
        TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
        [this.navigationController pushViewController:vc animated:YES];
    } failedBlock:^(ASIHTTPRequest *request) {
        HUD(kNetError);
    }];
}// 提交提现申请

- (void)showProtocal {
//    WebViewController *vc = [[WebViewController alloc] initWithFileName:];
//    vc
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
               HUD(kNetError);
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
            NSString *strTime = [NSString stringWithFormat:@"%ds后重发",timeout];
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

- (void)textFieldDidEndEditing:(UITextField *)textField {
    MypurseViewController *vc = [self findDesignatedViewController:[MypurseViewController class]];
    if (textField == _moneytf) {
        if (!_moneytf.text) {
            return;
        }
        if ([_moneytf.text integerValue] <= 0 || [_moneytf.text integerValue] > [vc.currentPurse.amount integerValue]) {
            HUD(@"您输入的金额有误,请重新输入");
            _moneytf.text = nil;
        }
    }
}

@end
