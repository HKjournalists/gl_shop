//
//  ChargePaymentViewController.m
//  Glshop
//
//  Created by River on 15-3-31.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  充值货款

#import "ChargePaymentViewController.h"
#import "HLCheckbox.h"
#import "ChargeViewController.h"
#import "WebViewController.h"

static NSString *protcalName = @"长江电商钱包支付协议-150512版.html";

@interface ChargePaymentViewController () <UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, assign) NSInteger dataSource;

@property (nonatomic, strong) HLCheckbox *box;
@property (nonatomic, strong) UILabel *agreeLabel;
@property (nonatomic, strong) UIButton *nextBtn;
@property (nonatomic, strong) UITextField *fieldTun;
@property (nonatomic, strong) WTReTextField *tfmoney;

@property (nonatomic, strong) UIView *checkView;

@property (nonatomic, assign) UserType type;

/**
 *@brief 充值金额
 */
@property (nonatomic, assign) double chargeAmount;

@end

@implementation ChargePaymentViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.title = @"货款充值";
}

- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStylePlain];
    _tableView.vtop += 20;
    _tableView.vheight = 44*3+170;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    _tableView.backgroundColor = [UIColor clearColor];
    _tableView.scrollEnabled = NO;
    [self.view addSubview:_tableView];
    
    UIView *footerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 120)];
    footerView.backgroundColor = [UIColor clearColor];
    UIView *tipView = [UIFactory createPromptViewframe:CGRectMake(10, 10, self.view.vwidth-20, 100) tipTitle:nil];
    UILabel *label1 = [UILabel labelWithTitle:@"货款将保存在上海浦东发展银行，用途由上海浦东发展银行监管。用户可以随时安心自由操作自己的账户。"];
    label1.font = [UIFont systemFontOfSize:14.f];
    label1.frame = CGRectMake(10, 40, 280, 20);
    label1.numberOfLines = 0;
    [label1 sizeToFit];
    [tipView addSubview:label1];
    [footerView addSubview:tipView];
    _tableView.tableFooterView = footerView;
    
    _nextBtn = [UIFactory createBtn:BlueButtonImageName bTitle:btntitle_next bframe:CGRectMake(15, _tableView.vbottom-10, SCREEN_WIDTH-30, 40)];
    [_nextBtn addTarget:self action:@selector(pushToCharge) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:_nextBtn];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 3;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;

    cell.textLabel.font = FontBoldSystem(15);
    cell.textLabel.textColor = C_BLACK;
    cell.detailTextLabel.font = FontBoldSystem(15);
    cell.detailTextLabel.textColor = C_GRAY;
    
    if (indexPath.row == 0) {
        cell.textLabel.text = @"当前可用金额";
        UILabel *unitlabel = [UIFactory createUnitLabel:@"当前可用金额" withFont:FontBoldSystem(15) unitType:unint_yuan];
        unitlabel.font = FontBoldSystem(15);
        [cell addSubview:unitlabel];
        
        cell.detailTextLabel.textColor = [UIColor redColor];
        cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",_money];
    }else if (indexPath.row == 1) {
            cell.textLabel.text = @"充值货款";
            UILabel *unitLabel = [UILabel labelWithTitle:@"(单位:元)"];
            unitLabel.font = UFONT_16_B;
            unitLabel.textColor = C_GRAY;
            unitLabel.frame = CGRectMake(80, 0, 90, 44);
            [cell addSubview:unitLabel];
            [cell addSubview:self.tfmoney];
    }else if (indexPath.row == 2) {
        [cell addSubview:self.checkView];

    }
        
    
    return cell;
}

- (void)pushToCharge {
    if (_chargeAmount <= 0) {
        [self showTip:@"请输入充值金额"];
        return;
    }
    
    ChargeViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"ChargeViewControllerId"];
    vc.chartAmount = self.chargeAmount;
    vc.chargeType = ChargeTypePayment;
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)showProtocal {
    WebViewController *vc = [[WebViewController alloc] initWithFileName:protcalName];
    [self.navigationController pushViewController:vc animated:YES];
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
            weakSelf.nextBtn.enabled = selected;
            weakSelf.agreeLabel.textColor = selected ? [UIColor blackColor] : ColorWithHex(@"#999999");
        };
        
        self.agreeLabel = [UILabel labelWithTitle:@"同意"];
        _agreeLabel.font = UFONT_14;
        _agreeLabel.frame = CGRectMake(_box.vright+2, _box.vtop, 40, _box.vheight);
        [_checkView addSubview:_agreeLabel];
        
        UIButton *proBtn = [UIButton buttonWithTip:@"长江电商钱包支付协议" target:self selector:@selector(showProtocal)];
        [proBtn setTitleColor:RGB(40, 113, 214, 1) forState:UIControlStateNormal];
        proBtn.titleLabel.font = UFONT_14;
        proBtn.frame = CGRectMake(_agreeLabel.vright-40, _agreeLabel.vtop+1, 215, 20);
        [_checkView addSubview:proBtn];
    }
    return _checkView;
}

- (WTReTextField *)tfmoney {
    if (!_tfmoney) {
        _tfmoney = [[WTReTextField alloc] init];
        _tfmoney.placeholder = @"请输入金额";
        _tfmoney.delegate = self;
        _tfmoney.textColor = [UIColor redColor];
        _tfmoney.keyboardType = UIKeyboardTypeDecimalPad;
        _tfmoney.pattern = @"^[0-9]+(.[0-9]{1,2})?$";
        if (_chargeAmount) {
            _tfmoney.text = [NSString stringWithFormat:@"%ld",(long)_chargeAmount];
        }
        _tfmoney.textAlignment = NSTextAlignmentRight;
        _tfmoney.returnKeyType = UIReturnKeyDone;
        _tfmoney.frame = CGRectMake(SCREEN_WIDTH-110, 0, 95, 44);
    }
    return _tfmoney;
}

- (void)textFieldDidEndEditing:(UITextField *)textField {
    if (textField == _tfmoney) {
        _chargeAmount = [_tfmoney.text doubleValue];
    }
}

@end
