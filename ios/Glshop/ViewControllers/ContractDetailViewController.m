//
//  ContractDetailViewController.m
//  Glshop
//
//  Created by River on 15-1-27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  合同模板

#import "ContractDetailViewController.h"
#import "ContractDetailModel.h"
#import "TipSuccessViewController.h"
#import "ContractAddressViewController.h"
#import "OppsiteProfileViewController.h"
#import "MypurseViewController.h"

static NSInteger sureAlertViewTag = 2000;
static NSInteger cancelAlertViewTag = 2001;
static NSInteger deleteContractAlertViewTag = 2002;
static NSInteger chargeAlertViewTag = 2003;

@interface ContractDetailViewController () <UIAlertViewDelegate>

@property (nonatomic, strong) ContractDetailModel *detailModel;
@property (nonatomic, strong) UIView *headerView;
@property (nonatomic, strong) UIView *bottomView;

@end

@implementation ContractDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.title = @"我的合同";
    [self requestNet];
}

- (void)loadSubViews {
    
    _headerView = UIView.new;
    _headerView.hidden = YES;
    [self.view addSubview:_headerView];
    [_headerView makeConstraints:^(MASConstraintMaker *make) {
        make.top.mas_equalTo(self.view);
        make.width.mas_equalTo(self.view);
        make.right.mas_equalTo(self.view);
        make.height.mas_equalTo(50);
    }];
    UIImage *bgImg = [UIImage imageNamed:@"agreement_anniu"];
    bgImg = [bgImg resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
    
    UIButton *setBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [setBtn setTitle:@"合同交易地址" forState:UIControlStateNormal];
    setBtn.titleEdgeInsets = UIEdgeInsetsMake(0, 5, 0, 0);
    setBtn.titleLabel.font = [UIFont systemFontOfSize:15.5];
    [setBtn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [setBtn setImage:[UIImage imageNamed:@"agreement_dizhi"] forState:UIControlStateNormal];
    [setBtn addTarget:self action:@selector(checkAddress:) forControlEvents:UIControlEventTouchUpInside];
    [setBtn setBackgroundImage:bgImg forState:UIControlStateNormal];
    [_headerView addSubview:setBtn];
    [setBtn makeConstraints:^(MASConstraintMaker *make) {
        make.top.mas_equalTo(_headerView).offset(10);
        make.leading.mas_equalTo(_headerView).offset(15);
        make.right.mas_equalTo(_headerView.centerX).offset(-10);
        make.height.mas_equalTo(30);
    }];
    
    
    UIButton *detailBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [detailBtn setTitle:@"查看对方资料" forState:UIControlStateNormal];
    detailBtn.titleEdgeInsets = UIEdgeInsetsMake(0, 5, 0, 0);
    detailBtn.titleLabel.font = [UIFont systemFontOfSize:15.5];
    [detailBtn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [detailBtn setImage:[UIImage imageNamed:@"agreement_ziliao"] forState:UIControlStateNormal];
    [detailBtn addTarget:self action:@selector(checkOppsiteProvile) forControlEvents:UIControlEventTouchUpInside];
    [detailBtn setBackgroundImage:bgImg forState:UIControlStateNormal];
    [_headerView addSubview:detailBtn];
    [detailBtn makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(_headerView.centerX).offset(10);
        make.height.mas_equalTo(setBtn);
        make.centerY.mas_equalTo(setBtn);
        make.right.mas_equalTo(_headerView).offset(-15);
    }];
    
    _webView = [[UIWebView alloc] initWithFrame:self.view.bounds];
    _webView.delegate = self;
    _webView.vtop += 50;
    _webView.hidden = YES;
    _webView.vheight -= kTopBarHeight+50;
    [self.view addSubview:_webView];
    
}

- (void)requestNet {
    [super requestNet];
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_contractId,@"OID", nil];
    __block typeof(self) this = self;
    [self requestWithURL:bgetContractDetailTemplate params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

- (void)handleNetData:(id)responseData {
    NSString *html = responseData[ServiceDataKey][@"template"];
    _detailModel = [[ContractDetailModel alloc] initWithDataDic:responseData[ServiceDataKey][@"bean"]];
    NSData *data = [NSData dataFromBase64String:html];
    NSString *s = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    [_webView loadHTMLString:s baseURL:nil];
    
    [self updateViews];
}

- (void)tipErrorCode:(NSInteger)errorCode {
    if (errorCode == 100005010) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"保证金不足" message:@"您的交易保证金不足，不能确认合同。" delegate:self cancelButtonTitle:nil otherButtonTitles:globe_cancel_str,@"去充值", nil];
        alert.tag = chargeAlertViewTag;
        [alert show];
    }else {
        [super tipErrorCode:errorCode];
    }
    
}

- (void)updateViews {
    _headerView.hidden = NO;
    _webView.hidden = NO;
    if (![_detailModel contractValide]) { // 合同已失效，显示删除按钮
        UIBarButtonItem *item = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemTrash target:self action:@selector(deleteContract)];
        self.navigationItem.rightBarButtonItem = item;
        
    }else {
        ContractLifeCycle lifeCycle = [_detailModel.lifecycle[DataValueKey] integerValue];
        if ([_detailModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY && lifeCycle == DRAFTING) { // 如果我是买家
            if ([_detailModel.buyerDraftStatus[DataValueKey] integerValue] == NOTHING) { // 用户还没操作，显示取消和确认按钮
                [self showSureBtnAndCancleBtn];
            }else {
                [_webView updateConstraints:^(MASConstraintMaker *make) {
                    make.bottom.mas_equalTo(self.view);
                    make.top.mas_equalTo(50);
                    make.width.mas_equalTo(self.view);
                }];
            }
        }else if ([_detailModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_SELL && lifeCycle == DRAFTING) { // 如果我是卖家
            if ([_detailModel.sellerDraftStatus[DataValueKey] integerValue] == NOTHING) { // 用户还没操作，显示取消和确认按钮
                [self showSureBtnAndCancleBtn];
            }else {
                [_webView updateConstraints:^(MASConstraintMaker *make) {
                    make.bottom.mas_equalTo(self.view);
                    make.top.mas_equalTo(50);
                    make.width.mas_equalTo(self.view);
                }];
            }
        }
    }
}

- (void)showSureBtnAndCancleBtn {
    
    _bottomView = UIView.new;
    [self.view addSubview:_bottomView];
    [_bottomView makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view);
        make.right.mas_equalTo(self.view);
        make.bottom.mas_equalTo(self.view);
        make.height.mas_equalTo(55);
    }];
    
    UIButton *sureBtn = [UIFactory createBtn:BlueButtonImageName bTitle:btntilte_sure_rightnow bframe:CGRectZero];
    [sureBtn addTarget:self action:@selector(sureContract) forControlEvents:UIControlEventTouchUpInside];
    [_bottomView addSubview:sureBtn];
    [sureBtn makeConstraints:^(MASConstraintMaker *make) {
        make.leading.lessThanOrEqualTo(_bottomView).offset(10);
        make.top.mas_equalTo(_bottomView.top).offset(10);
        make.height.mas_equalTo(35);
        make.right.mas_equalTo(_bottomView.centerX).offset(-10);
    }];
    
    UIButton *cancleBtn = [UIFactory createBtn:YelloCommnBtnImgName bTitle:btntitle_cancel_contract bframe:CGRectZero];
    [cancleBtn addTarget:self action:@selector(cancelContract) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:cancleBtn];
    [cancleBtn makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(_bottomView.centerX).offset(10);
        make.top.mas_equalTo(sureBtn);
        make.height.mas_equalTo(sureBtn);
        make.trailing.mas_equalTo(_bottomView).offset(-10);
    }];
    
    [_webView updateConstraints:^(MASConstraintMaker *make) {
        make.bottom.mas_equalTo(_bottomView.top);
        make.top.mas_equalTo(50);
        make.width.mas_equalTo(self.view);
    }];
}

#pragma mark - UIActions 
- (void)checkAddress:(UIButton *)btn {
    ContractAddressViewController *vc = [[ContractAddressViewController alloc] init];
    vc.fid = _detailModel.fid;
    [self.navigationController pushViewController:vc animated:YES];
}// 查看合同交易地址

- (void)checkOppsiteProvile {
    BOOL isBuyer = [_detailModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
    OppsiteProfileViewController *vc = [[OppsiteProfileViewController alloc] init];
    vc.cid = isBuyer ? _detailModel.sellerid : _detailModel.buyerid;
    [self.navigationController pushViewController:vc animated:YES];
    
}// 查看对方资料

- (void)sureContract {
    UIAlertView *sureAlert = [[UIAlertView alloc] initWithTitle:@"合同确认" message:contract_tap_sure_tip delegate:self cancelButtonTitle:nil otherButtonTitles:globe_cancel_str,globe_sure_str, nil];
    sureAlert.tag = sureAlertViewTag;
    [sureAlert show];
    
}// 确认合同

- (void)cancelContract {
    UIAlertView *cancelAlert = [[UIAlertView alloc] initWithTitle:@"合同取消" message:contract_weait_sure_tap_cancel_tip delegate:self cancelButtonTitle:@"暂不取消" otherButtonTitles:@"确认取消", nil];
    cancelAlert.tag = cancelAlertViewTag;
    [cancelAlert show];

}// 取消合同

- (void)deleteContract {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:deleteContract message:alert_tip_delete_contract delegate:self cancelButtonTitle:nil otherButtonTitles:globe_cancel_str,globe_sure_str, nil];
    alert.tag = deleteContractAlertViewTag;
    [alert show];
}// 删除失效合同

#pragma mark - UIAlertView Delegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (alertView.tag == sureAlertViewTag) {
        if (buttonIndex) { // 确认合同
            [self showHUD];
            NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_detailModel.contractId,path_key_contractId, nil];
            __block typeof(self) this = self;
            [self requestWithURL:btoConfirmContract params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
                kASIResultLog;
                TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
                NSArray *data = responseData[ServiceDataKey];
                if (data.count > 0) { // 说明对方已经确认合同
                    vc.operationType = tip_both_sure_contract_success;
                }else { // 对方未确认合同
                    vc.operationType = tip_my_sure_contract_success;
                }
                [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushContractNotification object:nil];
                [this.navigationController pushViewController:vc animated:YES];
            } failedBlock:^(ASIHTTPRequest *request) {
                
            }];
        }
    }else if (alertView.tag == cancelAlertViewTag) {
        if (buttonIndex) { // 取消合同
            [self showHUD];
            __block typeof(self) this = self;
            NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_detailModel.contractId,path_key_contractId,@18,@"operateType", nil];
            [self requestWithURL:bcancelDraftContract params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
                kASIResultLog;
                TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
                vc.operationType = tip_cancel_contract_success;
                [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushContractNotification object:nil];
                [this.navigationController pushViewController:vc animated:YES];
            } failedBlock:^(ASIHTTPRequest *request) {
                
            }];
        }
    }else if (alertView.tag == deleteContractAlertViewTag) {
        if (buttonIndex) {
            [self showHUD];
            NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_detailModel.contractId,@"OID",@23,@"operateType", nil];
            __block typeof(self) this = self;
            [self requestWithURL:bcancelDraftContract params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
                kASIResultLog;
                HUD(hudDeleteSuccess);
                [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushContractNotification object:nil];
                [this.navigationController popViewControllerAnimated:YES];
            } failedBlock:^(ASIHTTPRequest *request) {
                
            }];
        }
    }else if (alertView.tag == chargeAlertViewTag) {
        if (buttonIndex) {
            MypurseViewController *vc = [[MypurseViewController alloc] init];
            [self.navigationController pushViewController:vc animated:YES];
        }
    }
}

@end
