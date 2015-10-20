//
//  ReChargeViewController.m
//  Glshop
//
//  Created by River on 15-1-8.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  充值保证金第一步

#import "ReChargeViewController.h"
#import "HLCheckbox.h"
#import "ChargeViewController.h"
#import "WebViewController.h"

static NSString *protcalName = @"长江电商交易保证金协议-150512版.html";

@interface ReChargeViewController ()<UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate,UIActionSheetDelegate>

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

@implementation ReChargeViewController

- (void)viewDidLoad { 
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"充值";
}

- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStylePlain];
    _tableView.vtop += 10;
    _tableView.vheight = 44*3+170;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    _tableView.backgroundColor = [UIColor clearColor];
    _tableView.scrollEnabled = NO;
    [self.view addSubview:_tableView];
    
    UIView *footerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 120)];
    footerView.backgroundColor = [UIColor clearColor];
    UIView *tipView = [UIFactory createPromptViewframe:CGRectMake(10, 10, self.view.vwidth-20, 100) tipTitle:nil];
    UILabel *label1 = [UILabel labelWithTitle:@"保证金是交易的前提，被用于保障您的合同与交易的顺利进行，受银行托管并监管，不做其他任何用途，您可以随时取回。"];
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

#pragma mark - 同步数据里获取相应的缴纳费用
- (double)valueForEnterprise {
    for (NSDictionary *dic in [SynacObject sysParams]) {
        if ([dic[@"pname"] isEqualToString:@"BOND_ENTERPRISE"]) {
            NSNumber *num = dic[@"pvalue"];
            return [num doubleValue];
        }
    }
    return 0;
}

- (double)valueForPersonal {
    for (NSDictionary *dic in [SynacObject sysParams]) {
        if ([dic[@"pname"] isEqualToString:@"BOND_PERSONAL"]) {
            NSNumber *num = dic[@"pvalue"];
            return [num doubleValue];
        }
    }
    return 0;
}

- (NSInteger)valueForBOND_SHIP_0_1000 {
    for (NSDictionary *dic in [SynacObject sysParams]) {
        if ([dic[@"pname"] isEqualToString:@"BOND_SHIP_0_1000"]) {
            NSNumber *num = dic[@"pvalue"];
            return [num integerValue];
        }
    }
    return 0;
}

- (NSInteger)valueForBOND_SHIP_1001_5000 {
    for (NSDictionary *dic in [SynacObject sysParams]) {
        if ([dic[@"pname"] isEqualToString:@"BOND_SHIP_1001_5000"]) {
            NSNumber *num = dic[@"pvalue"];
            return [num integerValue];
        }
    }
    return 0;
}

- (NSInteger)valueForBOND_SHIP_5001_10000 {
    for (NSDictionary *dic in [SynacObject sysParams]) {
        if ([dic[@"pname"] isEqualToString:@"BOND_SHIP_5001_10000"]) {
            NSNumber *num = dic[@"pvalue"];
            return [num integerValue];
        }
    }
    return 0;
}

- (NSInteger)valueForBOND_SHIP_10001_15000 {
    for (NSDictionary *dic in [SynacObject sysParams]) {
        if ([dic[@"pname"] isEqualToString:@"BOND_SHIP_10001_15000"]) {
            NSNumber *num = dic[@"pvalue"];
            return [num integerValue];
        }
    }
    return 0;
}

#pragma mark - UITableView DataSource/Delegate

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
        cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",_margin];
        
    }else if (indexPath.row == 1) {
//        cell.textLabel.text = @"充值金额";
        [self cellSubViewFor:cell];
        [cell addSubview:self.tfmoney];
        
    }else if (indexPath.row == 2) {
        [cell addSubview:self.checkView];
    }

    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 20;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    NSString *str = @"     交易保证金的缴纳基准为3000元";
    NSDictionary *attDic = [NSDictionary dictionaryWithObjectsAndKeys:[UIColor redColor],NSForegroundColorAttributeName,FontBoldSystem(12), NSFontAttributeName,nil];
    NSAttributedString *attStr = [Utilits attString:str attTargetStr:@"3000" attrubites:attDic];
    UILabel *label = [UILabel label];
    label.font = FontBoldSystem(12);
    label.frame = CGRectMake(0, 0, tableView.vwidth, 20);
    label.attributedText = attStr;
    return label;
}

#pragma mark - Private
- (void)cellSubViewFor:(UITableViewCell *)cell {
    cell.textLabel.text = @"充值金额";
    UILabel *unitLabel = [UILabel labelWithTitle:@"(单位:元)"];
    unitLabel.font = FontBoldSystem(14);
    unitLabel.textColor = C_GRAY;
    unitLabel.frame = CGRectMake(80, 0, 90, 44);
    [cell addSubview:unitLabel];
    [cell.contentView addSubview:self.tfmoney];
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
        
        UIButton *proBtn = [UIButton buttonWithTip:@"长江电商交易保证金协议" target:self selector:@selector(showProtocal)];
        [proBtn setTitleColor:RGB(40, 113, 214, 1) forState:UIControlStateNormal];
        proBtn.titleLabel.font = UFONT_14;
        proBtn.frame = CGRectMake(_agreeLabel.vright-40, _agreeLabel.vtop+1, 215, 20);
        [_checkView addSubview:proBtn];
    }
    return _checkView;
}

- (UITextField *)fieldTun {
    if (!_fieldTun) {
        _fieldTun = [UITextField textFieldWithPlaceHodler:@"请输入吨位" withDelegate:self];
        _fieldTun.textAlignment = NSTextAlignmentRight;
        _fieldTun.keyboardType = UIKeyboardTypeNumberPad;
        _fieldTun.font = UFONT_16_B;
        _fieldTun.textColor = C_GRAY;
        _fieldTun.frame = CGRectMake(SCREEN_WIDTH-150-15, 0, 150, 44);
    }
    return _fieldTun;
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
        _tfmoney.font = FontBoldSystem(15);
        _tfmoney.textAlignment = NSTextAlignmentRight;
        _tfmoney.returnKeyType = UIReturnKeyDone;
        _tfmoney.frame = CGRectMake(SCREEN_WIDTH-160, 0, 140, 44);
    }
    return _tfmoney;
}

- (void)textFieldDidEndEditing:(UITextField *)textField {
    if (textField == _tfmoney) {
        _chargeAmount = [_tfmoney.text doubleValue];
    }else {
        NSInteger ton = [textField.text integerValue];
//        _chargeAmount = ton == 0 ? 4000 : ton*4000;
        if (ton <= 1000) {
            _chargeAmount = [self valueForBOND_SHIP_0_1000];
        }else if (ton <= 5000 && ton > 1000) {
            _chargeAmount = [self valueForBOND_SHIP_1001_5000];
        }else if (ton <= 10000 && ton > 5000) {
            _chargeAmount = [self valueForBOND_SHIP_5001_10000];
        }else if (ton > 10000) {
            _chargeAmount = [self valueForBOND_SHIP_10001_15000];
        }
        [_tableView reloadData];
    }
}

#pragma mark - UIActions
- (void)showProtocal {
    WebViewController *vc = [[WebViewController alloc] initWithFileName:protcalName];
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)pushToCharge {
    if (_chargeAmount <= 0) {
        [self showTip:@"请输入充值金额"];
        return;
    }
    
    ChargeViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"ChargeViewControllerId"];
    vc.chartAmount = self.chargeAmount;
    vc.chargeType = ChargeTypeMargin;
    [self.navigationController pushViewController:vc animated:YES];
    
}

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex != 3) {
        _type = buttonIndex;
    }
    
    if (buttonIndex == 0 || buttonIndex == 2) {
        _dataSource = 3;
//        _tfmoney.text = @"50000.0";
        _tfmoney.text = [NSString stringWithFormat:@"%.1f",[self valueForEnterprise]];
        _chargeAmount = [self valueForEnterprise];
        [_tableView reloadData];
    }else if (buttonIndex == 1) {
        _dataSource = 4;
        self.tfmoney.text = nil;
        _fieldTun.text = nil;
        _chargeAmount = 0;
        [_tableView reloadData];
    }
}

@end
