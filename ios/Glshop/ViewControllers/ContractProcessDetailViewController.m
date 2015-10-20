//
//  ContractProcessDetailViewController.m
//  Glshop
//
//  Created by River on 15-1-29.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  进行中合同的详细页面

#import "ContractProcessDetailViewController.h"
#import "MyContractViewController.h"
#import "PayTradeViewController.h"
#import "ContractDetailView.h"
#import "PaymentSureViewController.h"
#import "SellerSureViewController.h"
#import "TipSuccessViewController.h"
#import "RatingViewController.h"
#import "MySupplyViewController.h"

static NSInteger moveToEndAlertViewTag = 200;
static NSInteger cancelContractAlertViewTag = 201;
static NSInteger deleteContractAlertViewTag = 202;

@interface ContractProcessDetailViewController () <UIAlertViewDelegate>

@property (nonatomic, strong, readwrite) ContractModel *contractModel;
@property (nonatomic, strong) ContractDetailView *detailView;
@property (nonatomic, strong) NSTimer *timer;

@end

@implementation ContractProcessDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = vc_title_myContract;
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(requestNet) name:kRefrushContractNotification object:nil];
    
    [self requestNet];
}

- (void)loadSubViews {
    _detailView = [[ContractDetailView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, self.view.vheight)];
    _detailView.hidden = YES;
    [self.view addSubview:_detailView];
}

- (void)requestNet {
    [super requestNet];
    
    __block typeof(self) this = self;
    [self requestWithURL:bgetContractDetailInfoEx
                  params:[NSMutableDictionary dictionaryWithObjectsAndKeys:_contractId,path_key_contractId, nil]HTTPMethod:kHttpPostMethod
           completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

- (void)timerFireMethod:(NSTimer *)timer {
    if ([self.topViewController isKindOfClass:[self class]]) {
        [self requestNet];
    }
    [timer invalidate];
    
}

- (void)handleNetData:(id)responseData {
    _detailView.hidden = NO;
    self.contractModel = [[ContractModel alloc] initWithDataDic:responseData[ServiceDataKey]];
    _detailView.contractModel = _contractModel;
    
    if ([_contractModel.lifecycle[DataValueKey] integerValue] == SINGED) {
        [NSTimer scheduledTimerWithTimeInterval:60 target:self selector:@selector(timerFireMethod:) userInfo:[NSNumber numberWithBool:YES] repeats:NO];
    }
    
    ContractStatus status = [_contractModel.myContractType[DataValueKey] integerValue];
    if (status == FINISHED) {
        UIBarButtonItem *barItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemTrash target:self action:@selector(deteleAction)];
        self.navigationItem.rightBarButtonItem = barItem;
    }
}

- (void)backRootVC {
    MySupplyViewController *vc = [self findDesignatedViewController:[MySupplyViewController class]];
    if (vc) {
        [self.navigationController popToViewController:vc animated:YES];
    }else if ([self findDesignatedViewController:[MyContractViewController class]]) {
        vc = [self findDesignatedViewController:[MyContractViewController class]];
        [self.navigationController popToViewController:vc animated:YES];
    }else {
        [self.navigationController popViewControllerAnimated:YES];
    }
}

#pragma mark - UIActions
- (void)payAction {
    PayTradeViewController *vc = [[PayTradeViewController alloc] init];
    vc.contractId = _contractModel.contractId;
    vc.payAmount = _contractModel.totalamount;
    [self.navigationController pushViewController:vc animated:YES];
}// 向平台支付货款

- (void)cancelContractAction {
    UIAlertView *sureAlert = [[UIAlertView alloc] initWithTitle:btntitle_cancel_contract message:contract_single_cancel_tip delegate:self cancelButtonTitle:globe_cancel_str otherButtonTitles:globe_sure_str, nil];
    sureAlert.tag = cancelContractAlertViewTag;
    [sureAlert show];
    
}// 取消合同

- (void)ratingAction {
    RatingViewController *vc = [[RatingViewController alloc] init];
    [self.navigationController pushViewController:vc animated:YES];
}// 合同评价

- (void)surePaymentAction {
    PaymentSureViewController *vc = [[PaymentSureViewController alloc] init];
    vc.contractModel = _contractModel;
    [self.navigationController pushViewController:vc animated:YES];
    
}// 货物与货款的实际确认

- (void)moveToendAction {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:btntitle_move_end message:contract_move_alert_toEnd delegate:self cancelButtonTitle:globe_cancel_str otherButtonTitles:globe_sure_str, nil];
    alert.tag = moveToEndAlertViewTag;
    [alert show];
}// 移到已结束合同

- (void)acualSureAction {
    SellerSureViewController *vc = [[SellerSureViewController alloc] init];
    vc.contractModel = _contractModel;
    [self.navigationController pushViewController:vc animated:YES];
    
}// 实际货款确认

- (void)deteleAction {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:deleteContract message:alert_tip_delete_contract delegate:self cancelButtonTitle:globe_cancel_str otherButtonTitles:globe_sure_str, nil];
    alert.tag = deleteContractAlertViewTag;
    [alert show];
}

#pragma mark - UIAlertView Delegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    
    __block typeof(self) this = self;
    if (alertView.tag == moveToEndAlertViewTag) {
        if (buttonIndex) {
            NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_contractModel.contractId,path_key_contractId,@19,path_key_contract_operType, nil];
            [self showHUD];
            [self requestWithURL:bcancelDraftContract params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
                kASIResultLog;
                TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
                vc.operationType = tip_moveContract;
                [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushContractNotification object:nil];
                [this.navigationController pushViewController:vc animated:YES];
            } failedBlock:^(ASIHTTPRequest *request) {
                
            }];
        }
    }
    
    else if (alertView.tag == cancelContractAlertViewTag) {
        if (buttonIndex) {
            NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_contractModel.contractId,path_key_contractId, nil];
            [self showHUD];
            [self requestWithURL:bsingleCancelContract params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
                kASIResultLog;
                TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
                vc.operationType = tip_cancel_contracting_success;
                vc.contractId = this.contractModel.contractId;
                [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushContractNotification object:nil];
                [this.navigationController pushViewController:vc animated:YES];
                
            } failedBlock:^(ASIHTTPRequest *request) {
        
            }];
        }

    }
    
    else if (alertView.tag == deleteContractAlertViewTag) {
        if (buttonIndex) {
            [self showHUD];
            NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_contractModel.contractId,@"OID",@23,@"operateType", nil];
            __block typeof(self) this = self;
            [self requestWithURL:bcancelDraftContract params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
                kASIResultLog;
                [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushContractNotification object:nil];
                TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
                vc.operationType = tip_deleteContract;
                [this.navigationController pushViewController:vc animated:YES];
            } failedBlock:^(ASIHTTPRequest *request) {
                
            }];
        }
    }
}

- (void)dealloc {
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}


@end
