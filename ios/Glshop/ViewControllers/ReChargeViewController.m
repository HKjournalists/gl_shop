//
//  ReChargeViewController.m
//  Glshop
//
//  Created by River on 15-1-8.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "ReChargeViewController.h"
#import "HLCheckbox.h"
#import "ChargeViewController.h"
#import "WebViewController.h"
#import "IQKeyboardManager.h"

static NSString *protcalName = @"长江电商交易保证金协议-141009版.html";

@interface ReChargeViewController ()<UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate,UIActionSheetDelegate>

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

@implementation ReChargeViewController

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
    _dataSource = ([userInstance userType] == user_ship && !isAuth) ? 4 : 3;
    _type = unKnowType;
    self.title = @"充值";
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
    UILabel *label1 = [UILabel labelWithTitle:@"交易保证金账号由上海浦东发展银行托管，直接汇入上海浦东发展银行指定账号，账号受上海浦东发展银行进行资金监控"];
    label1.font = [UIFont systemFontOfSize:14.f];
    label1.frame = CGRectMake(10, 40, 280, 20);
    label1.numberOfLines = 0;
    [label1 sizeToFit];
    [tipView addSubview:label1];
    [footerView addSubview:tipView];
    _tableView.tableFooterView = footerView;
    
    _nextBtn = [UIFactory createBtn:BlueButtonImageName bTitle:@"下一步" bframe:CGRectMake(15, _tableView.vbottom-10, SCREEN_WIDTH-30, 40)];
    [_nextBtn addTarget:self action:@selector(pushToCharge) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:_nextBtn];
}

#pragma mark - UITableView DataSource/Delegate

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return _dataSource;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.textLabel.font = [UIFont boldSystemFontOfSize:14.f];
    if (indexPath.row) {
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    UserInstance *userInstance = [UserInstance sharedInstance];
    BOOL isAuth = [userInstance.user.authstatus[DataValueKey] integerValue] == 1;
    if (indexPath.row == 0) {
        cell.textLabel.text = @"用户类型";
        cell.detailTextLabel.textColor = [UIColor grayColor];
        
        if (!isAuth) {
            NSString *text;
            if (_type == user_company) {
                text = @"企业";
            }else if (_type == user_personal) {
                text = @"个人";
            }else if (_type == user_ship) {
                text = @"船舶";
            }else {
                text = @"选择";
            }
            cell.detailTextLabel.text = text;
        }else {
            cell.detailTextLabel.text = userInstance.user.ctype[DataTextKey];
        }
        
        if ([userInstance.user.authstatus[DataValueKey] integerValue] != 1) { // 没有被认证的用户
            cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        }
        
    }else if (indexPath.row == 1) {
        if (!isAuth && _type == user_ship) {
            cell.textLabel.text = @"船载重量";
            UILabel *unitLabel = [UILabel labelWithTitle:@"(单位:吨)"];
            unitLabel.font = [UIFont systemFontOfSize:12.5f];
            unitLabel.textColor = [UIColor grayColor];
            unitLabel.frame = CGRectMake(70, 1.5, 90, 44);
            [cell addSubview:unitLabel];
            [cell addSubview:self.fieldTun];
            
        }else {
            [self cellSubViewFor:cell];
            if ( !isAuth) {
                self.tfmoney.enabled = NO;
                _tfmoney.placeholder = nil;
            }
        }
    }else if (indexPath.row == 2) {
        if (!isAuth && _type == user_ship) {
            cell.textLabel.text = @"需缴纳交易保证金";
            UILabel *unitLabel = [UILabel labelWithTitle:@"(单位:元)"];
            unitLabel.font = [UIFont systemFontOfSize:12.5f];
            unitLabel.textColor = [UIColor grayColor];
            unitLabel.frame = CGRectMake(125, 1.5, 90, 44);
            [cell addSubview:unitLabel];
            cell.detailTextLabel.textColor = [UIColor redColor];
            cell.detailTextLabel.text = [NSString stringWithFormat:@"%ld",(long)_chargeAmount];
        }else {
            [cell addSubview:self.checkView];
        }
    }else if (indexPath.row == 3) {
        [cell addSubview:self.checkView];
    }
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    if (indexPath.row == 0) {
        UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:@"选择用户类型" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:@"企业",@"船舶",@"个人", nil];
        [sheet showInView:self.view];
    }
}

#pragma mark - Private
- (void)cellSubViewFor:(UITableViewCell *)cell {
    cell.textLabel.text = @"需缴纳交易保证金";
    UILabel *unitLabel = [UILabel labelWithTitle:@"(单位:元)"];
    unitLabel.font = [UIFont boldSystemFontOfSize:12.5f];
    unitLabel.textColor = [UIColor grayColor];
    unitLabel.frame = CGRectMake(125, 1.5, 90, 44);
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
            weakSelf.nextBtn.enabled = !selected;
            weakSelf.agreeLabel.textColor = !selected ? [UIColor blackColor] : ColorWithHex(@"#999999");
        };
        
        self.agreeLabel = [UILabel labelWithTitle:@"同意"];
        _agreeLabel.frame = CGRectMake(_box.vright+2, _box.vtop, 40, _box.vheight);
        [_checkView addSubview:_agreeLabel];
        
        UIButton *proBtn = [UIButton buttonWithTip:@"长江电商平台交易保证金支付协议" target:self selector:@selector(showProtocal)];
        [proBtn setTitleColor:RGB(40, 113, 214, 1) forState:UIControlStateNormal];
        proBtn.titleLabel.font = [UIFont boldSystemFontOfSize:13.5f];
        proBtn.frame = CGRectMake(_agreeLabel.vright-10, _agreeLabel.vtop+1, 215, 20);
        [_checkView addSubview:proBtn];
    }
    return _checkView;
}

- (UITextField *)fieldTun {
    if (!_fieldTun) {
        _fieldTun = [UITextField textFieldWithPlaceHodler:@"请输入吨位" withDelegate:self];
        _fieldTun.textAlignment = NSTextAlignmentRight;
        _fieldTun.keyboardType = UIKeyboardTypeNumberPad;
        _fieldTun.frame = CGRectMake(SCREEN_WIDTH-150-15, 0, 150, 44);
    }
    return _fieldTun;
}

- (UITextField *)tfmoney {
    if (!_tfmoney) {
        _tfmoney = [UITextField textFieldWithPlaceHodler:@"请输入金额" withDelegate:self];
        _tfmoney.textColor = [UIColor redColor];
        _tfmoney.keyboardType = UIKeyboardTypeNumberPad;
        if (_chargeAmount) {
            _tfmoney.text = [NSString stringWithFormat:@"%ld",(long)_chargeAmount];
        }
        _tfmoney.textAlignment = NSTextAlignmentRight;
        _tfmoney.returnKeyType = UIReturnKeyDone;
        _tfmoney.frame = CGRectMake(SCREEN_WIDTH-145, 0, 130, 44);
    }
    return _tfmoney;
}

- (void)textFieldDidEndEditing:(UITextField *)textField {
    if (textField == _tfmoney) {
        _chargeAmount = [_tfmoney.text integerValue];
    }else {
        NSInteger ton = [textField.text integerValue]/1000;
        _chargeAmount = ton == 0 ? 4000 : ton*4000;
        [_tableView reloadData];
    }
}

#pragma mark - UIActions
- (void)showProtocal {
    WebViewController *vc = [[WebViewController alloc] initWithFileName:protcalName];
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)pushToCharge {
    UserInstance *userInstance = [UserInstance sharedInstance];
    BOOL isAuth = [userInstance.user.authstatus[DataValueKey] integerValue] == 1;
    if (!isAuth && _type == unKnowType) {
        HUD(@"请选择认证类型");
        return;
    }
    
    if (!isAuth && _type == user_ship && !_fieldTun.text) {
        HUD(@"请输入船舶载重量");
        return;
    }
    
    ChargeViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"ChargeViewControllerId"];
    vc.chartAmount = self.chargeAmount;
    [self.navigationController pushViewController:vc animated:YES];
    
}

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex != 3) {
        _type = buttonIndex;
    }
    
    if (buttonIndex == 0 || buttonIndex == 2) {
        _dataSource = 3;
        _tfmoney.text = @"5000.0";
        _chargeAmount = 5000;
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
