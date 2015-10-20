//
//  PaymentSureViewController.m
//  Glshop
//
//  Created by River on 15-2-5.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  买家货物与货款的确认

#import "PaymentSureViewController.h"
#import "TipSuccessViewController.h"
#import "ContractDetailViewController.h"

@interface PaymentSureViewController () <UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate,UIAlertViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) UITextField *priceField;
@property (nonatomic, strong) UITextField *totalField;
@property (nonatomic, strong) NSString *priceText;
@property (nonatomic, strong) NSString *totalText;
/**
 *@brief 最终总价
 */
@property (nonatomic, copy) NSString *actualTotalNum;

@end

@implementation PaymentSureViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"货物与货款确认";
}

#pragma mark - Overidde
- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vheight -= kTopBarHeight;
    _tableView.contentInset = UIEdgeInsetsMake(-35, 0, 0, 0);
    _tableView.dataSource = self;
    _tableView.delegate = self;
    _tableView.sectionFooterHeight = 5;
    _tableView.sectionHeaderHeight = 5;
    [self.view addSubview:_tableView];
}

- (void)tipErrorCode:(NSInteger)errorCode {
    if (errorCode == 10005) {
        [self showTip:@"密码错误,请重新输入"];
    }else {
        [super tipErrorCode:errorCode];
    }
}

#pragma mark - Getter
- (UITextField *)priceField {
    if (!_priceField) {
        _priceField = [UITextField textFieldWithPlaceHodler:@"请输入" withDelegate:self];
        _priceField.textAlignment = NSTextAlignmentRight;
        _priceField.keyboardType = UIKeyboardTypeDecimalPad;
        _priceField.frame = CGRectMake(SCREEN_WIDTH-150-15, 0, 150, 44);
    }
    return _priceField;
}

- (UITextField *)totalField {
    if (!_totalField) {
        _totalField = [UITextField textFieldWithPlaceHodler:@"请输入" withDelegate:self];
        _totalField.textAlignment = NSTextAlignmentRight;
        _totalField.keyboardType = UIKeyboardTypeDecimalPad;
        _totalField.frame = CGRectMake(SCREEN_WIDTH-150-15, 0, 150, 44);
    }
    return _totalField;
}


#pragma mark -
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 3;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return section == 0 ? 1 : 6;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    UIFont *font = [UIFont systemFontOfSize:14.f];
    cell.textLabel.font = font;
    if (indexPath.section == 0) {
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        UIImageView *logo = [[UIImageView alloc] initWithFrame:CGRectMake(8, 8, 15, 19)];
        logo.image = [UIImage imageNamed:@"agreement_icon_hetong"];
        [cell.contentView addSubview:logo];
        
        NSString *saleStr = [_contractModel.saleType[DataValueKey] integerValue] == 1 ? @"求购 " : @"出售 ";
        NSString *productStr = [SynacObject combinProducName:_contractModel.productType proId:_contractModel.productId];
        UILabel *nameLab = [UILabel labelWithTitle:[NSString stringWithFormat:@"%@%@",saleStr,productStr]];
        nameLab.frame = CGRectMake(30, 6, 260, 20);
        nameLab.font = font;
        [cell.contentView addSubview:nameLab];
        
        UILabel *priceLab = [UILabel labelWithTitle:[NSString stringWithFormat:@"%.2f%@",[_contractModel.totalnum floatValue],unit_tun]];
        priceLab.frame = CGRectMake(nameLab.vleft, nameLab.vbottom+5, 95, 20);
        priceLab.font = font;
        [cell.contentView addSubview:priceLab];
        
        UILabel *totalLab = [UILabel labelWithTitle:[NSString stringWithFormat:@"%.2f%@",[_contractModel.price floatValue],unit_per_price_tun]];
        totalLab.frame = CGRectMake(priceLab.vright+10, nameLab.vbottom+5, 95, 20);
        totalLab.font = font;
        [cell.contentView addSubview:totalLab];
        
    }else if (indexPath.section == 1) {
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        NSArray *texts = @[@"货物与货款的实际确认：",@"实际货物单价",@"实际货物总量",@"实际货物总价",@"实际货物确认后经双方协商，最终确认结果",];
        if (indexPath.row < 5) {
            cell.textLabel.text = texts[indexPath.row];
        }
        if (indexPath.row == 0) {
            
        }else if (indexPath.row == 1) {
            UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_per_dun_money];
            [cell addSubview:unitlabel];
            [cell addSubview:self.priceField];
            self.priceField.text = _priceText.length ? _priceText : [NSString stringWithFormat:@"%.2f",[_contractModel.price floatValue]];
        }else if (indexPath.row == 2) {
            UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_dun];
            [cell addSubview:unitlabel];
            [cell addSubview:self.totalField];
            self.totalField.text = _totalText.length ? _totalText : [NSString stringWithFormat:@"%.2f",[_contractModel.totalnum floatValue]];
        }else if (indexPath.row == 3) {
            UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_yuan];
            [cell addSubview:unitlabel];
            if (_actualTotalNum.length) {
                cell.detailTextLabel.textColor = [UIColor redColor];
                cell.detailTextLabel.text = _actualTotalNum;
            }else {
                if (_priceField.text.length && _totalField.text.length) {
                    float totalMoney = [_contractModel.price floatValue] * [_contractModel.totalnum floatValue];
                    cell.detailTextLabel.textColor = [UIColor redColor];
                    cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",totalMoney];
                }else {
                    cell.detailTextLabel.text = @"0";
                }
            }
            
        }else if (indexPath.row == 4) {
            cell.textLabel.textColor = [UIColor redColor];
            
        }else if (indexPath.row == 5) {
            UIButton *btn = [UIFactory createBtn:imgName_btnBg_lightGray bTitle:btntitle_post_toSellerSure bframe:CGRectMake(cell.vwidth/2-105, 7, 210, 30)];
            [btn addTarget:self action:@selector(paySure) forControlEvents:UIControlEventTouchUpInside];
            btn.titleLabel.font = [UIFont systemFontOfSize:15.f];
            [btn setTitleColor:[UIColor orangeColor] forState:UIControlStateNormal];
            [cell addSubview:btn];
        }
    }else if (indexPath.section == 2) {
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        NSArray *texts = @[@"合同记载：",@"合同货物单价",@"合同货物总量",@"合同货物总价",@"实际货物与合同记载差别太大，无法交易"];
        if (indexPath.row < 5) {
            cell.textLabel.text = texts[indexPath.row];
        }
        if (indexPath.row == 0) {
            
        }else if (indexPath.row == 1) {
            UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_per_dun_money];
            [cell addSubview:unitlabel];
            cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",[_contractModel.price floatValue]];
        }else if (indexPath.row == 2) {
            UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_dun];
            [cell addSubview:unitlabel];
            cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",[_contractModel.totalnum floatValue]];
            
        }else if (indexPath.row == 3) {
            UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_yuan];
            [cell addSubview:unitlabel];
            cell.detailTextLabel.textColor = [UIColor redColor];
            cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",[_contractModel.totalamount floatValue]];
            
        }else if (indexPath.row == 4) {
            cell.textLabel.textColor = [UIColor redColor];
            
        }else if (indexPath.row == 5) {
            UIButton *btn = [UIFactory createBtn:imgName_btnBg_lightGray bTitle:btntitle_apply_plan bframe:CGRectMake(cell.vwidth/2-105, 7, 210, 30)];
            btn.titleLabel.font = [UIFont systemFontOfSize:15.f];
            [btn addTarget:self action:@selector(applyArbitrate) forControlEvents:UIControlEventTouchUpInside];
            [btn setTitleColor:[UIColor orangeColor] forState:UIControlStateNormal];
            [cell addSubview:btn];
        }
    }
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0) {
        return 50;
    }
    return 44;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    if (indexPath.section == 0) {
        ContractDetailViewController *vc = [[ContractDetailViewController alloc] init];
        vc.contractId = _contractModel.contractId;
        [self.navigationController pushViewController:vc animated:YES];
    }
}

#pragma mark - UIAction
- (void)paySure {
    if (_priceField.text.length == 0) {
        [self showTip:@"请输入实际货物单价"];
        return;
    }
    
    if (_totalField.text.length == 0) {
        [self showTip:@"请输入实际货物总价"];
        return;
    }
    
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:nil message:contract_surePayMoney delegate:self cancelButtonTitle:nil otherButtonTitles:globe_cancel_str,globe_sure_str, nil];
    alertView.tag = 200;
    [alertView show];
}// 货物与货款实际确认

- (void)applyArbitrate {
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:contract_applyArbiteTitle message:contract_sure_applyArbite delegate:self cancelButtonTitle:globe_cancel_str otherButtonTitles:globe_sure_str, nil];
    alertView.tag = 201;
    [alertView show];
}// 申请仲裁

#pragma mark - UITextField Delegate
- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string {
    NSString *finalStr = textField.text.length ? [NSString stringWithFormat:@"%@%@",textField.text,string] : string;
    if (textField == _priceField) {
        if ([finalStr floatValue] > [_contractModel.price floatValue]) {
            [Utilits alertWithString:alert_contract_price alertTitle:nil];
            return NO;
        }
    }
    if (textField == _totalField) {
        if ([finalStr floatValue] > [_contractModel.totalnum floatValue]) {
            [Utilits alertWithString:alert_contract_total alertTitle:nil];
            return NO;
        }
    }
    return YES;
}

- (void)textFieldDidEndEditing:(UITextField *)textField {
    NSString *regex = @"^[0-9]+([.]{0}|[.]{1}[0-9]+)$";
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", regex];
    if ([predicate evaluateWithObject:textField.text] == NO) {
        HUD(@"请输入整数或小数");
        textField.text = nil;
        return;
    }
    
    if (_priceField.text.length && _totalField.text.length) {
        _totalText = _totalField.text;
        _priceText = _priceField.text;
        float total = [_priceField.text floatValue] * [_totalField.text floatValue];
        _actualTotalNum = [NSString stringWithFormat:@"%.2f",total];
    }else {
        _actualTotalNum = @"";
    }
    [_tableView reloadData];
}

#pragma mark -
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (alertView.tag == 200) {
        if (buttonIndex) { // 货物与货款实际确认
            NSNumber *price, *total;
            price = _priceField.text.length ? [NSNumber numberWithFloat:[_priceField.text floatValue]] : _contractModel.price;
            total = _totalField.text.length ? [NSNumber numberWithFloat:[_totalField.text floatValue]] : _contractModel.totalnum;
            
            if ([_priceField.text floatValue] == 0) {
                [self showTip:@"价格不能为0"];
                return;
            }
            
            if ([_totalField.text floatValue] == 0) {
                [self showTip:@"货物总量不能为0"];
                return;
            }
            
            NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_contractModel.contractId,path_key_contractId,price,@"disPrice",total,@"disNum",@20,@"operateType", nil];
            __block typeof(self) this = self;
            [self showHUD];
            [self requestWithURL:bapplyOrAgreeOrArbitrateFinalEstimate
                          params:params
                      HTTPMethod:kHttpPostMethod
                   completeBlock:^(ASIHTTPRequest *request, id responseData) {
                       kASIResultLog;
                       [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushContractNotification object:nil];
                       TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
                       vc.operationType = tip_buyer_sure_success;
                       vc.contractId = this.contractModel.contractId;
                       [this.navigationController pushViewController:vc animated:YES];
                   } failedBlock:^(ASIHTTPRequest *request) {
                       
                   }];
        }

    }else if (alertView.tag == 201) {
        if (buttonIndex) { // 申请仲裁
            NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_contractModel.contractId,path_key_contractId,@22,@"operateType", nil];
            __block typeof(self) this = self;
            [self showHUD];
            [self requestWithURL:bapplyOrAgreeOrArbitrateFinalEstimate
                          params:params
                      HTTPMethod:kHttpPostMethod
                   completeBlock:^(ASIHTTPRequest *request, id responseData) {
                       kASIResultLog;
                       [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushContractNotification object:nil];
                       TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
                       vc.operationType = tip_arbitrate_success;
                       vc.contractId = this.contractModel.contractId;
                       [this.navigationController pushViewController:vc animated:YES];
                   } failedBlock:^(ASIHTTPRequest *request) {
                       
                   }];
        }
    }
}

@end
