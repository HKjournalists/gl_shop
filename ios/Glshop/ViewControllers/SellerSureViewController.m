//
//  SellerSureViewController.m
//  Glshop
//
//  Created by River on 15-2-6.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  卖家进行实收货款的确认

#import "SellerSureViewController.h"
#import "ContractModel.h"
#import "TipSuccessViewController.h"
#import "ContractDetailViewController.h"

@interface SellerSureViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;

@end

@implementation SellerSureViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.title = @"实收货款确认";
    // Do any additional setup after loading the view.
}

- (void)loadSubViews {
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vheight -= kTopBarHeight;
    _tableView.contentInset = UIEdgeInsetsMake(-25, 0, 0, 0);
    _tableView.dataSource = self;
    _tableView.delegate = self;
    _tableView.sectionFooterHeight = 5;
    _tableView.sectionHeaderHeight = 5;
    [self.view addSubview:_tableView];
}

#pragma mark -
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 3;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (section == 0) {
        return 1;
    }else if (section == 1) {
        return 4;
    }else {
        return 8;
    }
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
        NSArray *texts = @[@"合同记载：",@"合同货物单价",@"合同货物总量",@"合同货物总价"];
        cell.textLabel.text = texts[indexPath.row];
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
            
        }
    }else if (indexPath.section == 2) {
        NSArray *texts = @[@"实际货物确认后经双方协商，买家确认实付货款如下:",@"实际货物单价",@"实际货物总量",@"实际货物总价",@"确认以上实收货款总价",@"",@"实际货物与合同记载差别太大，无法交易",@""];
        cell.textLabel.text = texts[indexPath.row];
        cell.textLabel.adjustsFontSizeToFitWidth = YES;
        
        float num = [_contractModel.fundGoodsDisPriceList[@"endnum"] floatValue];
        float price = [_contractModel.fundGoodsDisPriceList[@"endamount"] floatValue];
        if (indexPath.row == 1) {
            UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_per_dun_money];
            [cell addSubview:unitlabel];
            cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",price];
        }else if (indexPath.row == 2) {
            UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_dun];
            [cell addSubview:unitlabel];
            cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",num];
        }else if (indexPath.row == 3) {
            UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_yuan];
            [cell addSubview:unitlabel];
            cell.detailTextLabel.textColor = [UIColor redColor];
            float total = price*num;
            cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",total];
        }else if (indexPath.row == 4) {
            cell.textLabel.textColor = [UIColor redColor];
            
        }else if (indexPath.row == 5) {
            UIButton *btn = [UIFactory createBtn:imgName_btnBg_lightGray bTitle:btntitle_sure_agree bframe:CGRectMake(cell.vwidth/2-105, 7, 210, 30)];
            [btn addTarget:self action:@selector(acualSureAction) forControlEvents:UIControlEventTouchUpInside];
            btn.titleLabel.font = [UIFont systemFontOfSize:15.f];
            [btn setTitleColor:[UIColor orangeColor] forState:UIControlStateNormal];
            [cell addSubview:btn];
        }else if (indexPath.row == 6) {
            cell.textLabel.textColor = [UIColor redColor];
        }else if (indexPath.row == 7) {
            UIButton *btn = [UIFactory createBtn:imgName_btnBg_lightGray bTitle:btntitle_apply_plan bframe:CGRectMake(cell.vwidth/2-105, 7, 210, 30)];
            [btn addTarget:self action:@selector(applyArbitrate) forControlEvents:UIControlEventTouchUpInside];
            btn.titleLabel.font = [UIFont systemFontOfSize:15.f];
            [btn setTitleColor:[UIColor orangeColor] forState:UIControlStateNormal];
            [cell addSubview:btn];
        }
    }
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return indexPath.section == 0 ? 50 : 44;
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
- (void)acualSureAction {
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:nil message:contract_sellerSurePayment delegate:self cancelButtonTitle:nil otherButtonTitles:globe_cancel_str,globe_sure_str, nil];
    alertView.tag = 200;
    [alertView show];
}// 实收货款确认

- (void)applyArbitrate {
    UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:contract_applyArbiteTitle message:contract_sure_applyArbite delegate:self cancelButtonTitle:globe_cancel_str otherButtonTitles:globe_sure_str, nil];
    alertView.tag = 201;
    [alertView show];
}// 申请仲裁

#pragma mark -
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (alertView.tag == 200) { // 实收货款确认
        if (buttonIndex) {
            NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_contractModel.contractId,path_key_contractId,_contractModel.price,@"disPrice",_contractModel.totalnum,@"disNum",@21,@"operateType", nil];
            [self showHUD];
            __block typeof(self) this = self;
            [self requestWithURL:bapplyOrAgreeOrArbitrateFinalEstimate
                          params:params
                      HTTPMethod:kHttpPostMethod
                   completeBlock:^(ASIHTTPRequest *request, id responseData) {
                       kASIResultLog;
                       [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushContractNotification object:nil];
                       TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
                       vc.operationType = tip_seller_sure_payment;
                       vc.contractId = this.contractModel.contractId;
                       [this.navigationController pushViewController:vc animated:YES];
                   } failedBlock:^(ASIHTTPRequest *request) {
                    
                   }];
        }
        
    }else if (alertView.tag == 201) { // 申请仲裁
        if (buttonIndex) {
            NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_contractModel.contractId,path_key_contractId,@22,@"operateType", nil];
            [self showHUD];
            __block typeof(self) this = self;
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
