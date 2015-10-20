//
//  TipSuccessViewController.m
//  Glshop
//
//  Created by River on 15-1-16.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "TipSuccessViewController.h"
#import "MypurseViewController.h"
#import "BusinessViewController.h"
#import "MySupplyViewController.h"
#import "MyContractViewController.h"
#import "ContractDetailViewController.h"
#import "ContractWaitSureViewController.h"
#import "ContractPorccesingViewController.h"
#import "ContractEndedViewController.h"
#import "ContractProcessDetailViewController.h"
#import "BusinessViewController.h"
#import "NSString+WPAttributedMarkup.h"
#import "WPAttributedStyleAction.h"
#import "WPHotspotLabel.h"

static NSString *successTipImgName = @"success";

@interface TipSuccessViewController ()

@end

@implementation TipSuccessViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)loadSubViews {
    switch (_operationType) {
        case tip_add_payee_success:
        {
            self.title = @"新增收款人";
            [self loadCommanViewWithImgName:successTipImgName tipTitle:@"提交成功！" tipDetail:@"等待客服审核通过后即可进行提现，敬请留意。" buttonTitle:@"查看我的钱包" btnSelector:@selector(skipToMypurse)];
        }
            break;
        case tip_rollout_success:
        {
            self.title = @"转出";
            [self loadCommanViewWithImgName:successTipImgName tipTitle:@"提现申请成功！" tipDetail:@"转出金额将在48小时内到账，敬请留意" buttonTitle:@"查看我的钱包" btnSelector:@selector(skipToMypurse)];
        }
            break;
        case tip_to_margin_success:
        {
            self.title = @"货款转保证金";
            NSString *tipStr = [NSString stringWithFormat:@"已成功转款%.2f元至我的交易保证金账户。",_margin];
            [self loadCommanViewWithImgName:successTipImgName tipTitle:@"货款转保证金成功！" tipDetail:tipStr buttonTitle:@"查看我的钱包" btnSelector:@selector(skipToMypurse)];
        }
            break;
        case tip_inquiry_success:
        {
            self.title = @"找买找卖";
            [self loadCommanViewWithImgName:successTipImgName tipTitle:@"提交成功！" tipDetail:@"稍后平台客服将帮您联系对方发起三方通话洽谈生意，请保持您的手机通讯正常" buttonTitle:@"看看其他的供求信息" btnSelector:@selector(popToBussinessVC)];
        }
            break;
        case tip_public_success:
        {
            self.title = @"发布成功";
            [self loadCommanViewWithImgName:successTipImgName tipTitle:@"恭喜您，您的信息已发布成功！" tipDetail:@"请保持手机通讯畅通,以便客服及时联系您！" buttonTitle:@"查看我的供求" btnSelector:@selector(chekeMySupply)];
        }
            break;
        case tip_cancel_public_success: {
            self.title = @"我的供求";
            [self loadCommanViewWithImgName:successTipImgName tipTitle:@"取消发布成功！" tipDetail:@"该信息已取消发布成功。" buttonTitle:@"查看我的供求" btnSelector:@selector(chekeMySupply)];
        }
            break;
        case tip_delete_public: {
            self.title = @"我的供求";
            [self loadCommanViewWithImgName:successTipImgName tipTitle:@"删除发布成功！" tipDetail:@"该信息已删除成功。" buttonTitle:@"查看我的供求" btnSelector:@selector(chekeMySupply)];
        }
            break;
        case tip_my_sure_contract_success:
        {
            self.title = vc_title_myContract;
            [self loadCommanViewWithImgName:successTipImgName tipTitle:sure_success tipDetailheader:@"如果对方在" tipDetailbody:@"15" tipDetailEnd:@"分钟内也确认该合同，将即刻生效，敬请耐心等待对方的确认" buttonTitle:@"查看我的合同" btnSelector:@selector(checkMyContract:)];
        }
            break;
        case tip_both_sure_contract_success:
        {
            self.title = vc_title_myContract;
            [self loadCommanViewWithImgName:successTipImgName tipTitle:sure_success tipDetail:contract_both_sure_tip buttonTitle:btntitle_review_mycontract btnSelector:@selector(checkMyContract:)];
        }
            break;
        case tip_cancel_contract_success:
        {
            self.title = vc_title_myContract;
            [self loadCommanViewWithImgName:successTipImgName tipTitle:cancel_success tipDetail:cancelWatiSureContract buttonTitle:btntitle_review_mycontract btnSelector:@selector(checkMyContract:)];
        }
            break;
        case tip_contract_pay_success:
        {
            self.title = @"支付成功";
            [self loadCommanViewWithImgName:successTipImgName tipTitle:pay_success tipDetail:contract_tip_action1 buttonTitle:btntitle_review_mycontract btnSelector:@selector(checkMyContractDetail:)];
        }
            break;
        case tip_moveContract:
        {
            self.title = vc_title_myContract;
            [self loadCommanViewWithImgName:successTipImgName tipTitle:operateSuccess tipDetail:tipMoveToEndSuccessContent buttonTitle:btntitle_review_mycontract btnSelector:@selector(checkMyContract:)];
        }
            break;
        case tip_buyer_sure_success:
        {
            self.title = vc_title_myContract;
            [self loadCommanViewWithImgName:successTipImgName tipTitle:sure_success tipDetail:contract_buyer_surePayDet buttonTitle:btntitle_review_mycontract btnSelector:@selector(checkMyContractDetail:)];
        }
            break;
        case tip_seller_sure_payment:
        {
            self.title = vc_title_myContract;
            [self loadCommanViewWithImgName:successTipImgName tipTitle:sure_success tipDetail:contract_tip_sellerSurePayment buttonTitle:btntitle_review_mycontract btnSelector:@selector(checkMyContractDetail:)];
        }
            break;
        case tip_arbitrate_success:
        {
            self.title = vc_title_myContract;
            [self loadCommanViewWithImgName:successTipImgName tipTitle:operateSuccess tipDetail:contract_freeze_arbitrate_detail buttonTitle:btntitle_review_mycontract btnSelector:@selector(checkMyContractDetail:)];
        }
            break;
        case tip_cancel_contracting_success:
        {
            self.title = vc_title_myContract;
            [self loadCommanViewWithImgName:successTipImgName tipTitle:cancel_success tipDetail:contract_tip_cancel_successgl buttonTitle:btntitle_review_mycontract btnSelector:@selector(checkMyContractDetail:)];
        }
            break;
        case tip_deleteContract: {
            self.title = vc_title_myContract;
            [self loadCommanViewWithImgName:successTipImgName tipTitle:operateSuccess tipDetail:tip_contract_deleteSuccess buttonTitle:btntitle_review_mycontract btnSelector:@selector(checkMyContract:)];
        }
            break;
        case tip_reviewContract: {
            self.title = vc_title_myContract;
            [self loadCommanViewWithImgName:successTipImgName tipTitle:operateSuccess tipDetail:tip_review_contract_success buttonTitle:btntitle_review_mycontract btnSelector:@selector(checkMyContractDetail:)];
        }
            break;
        case tip_chargeMarginSuccess: {
            self.title = @"支付成功";
            NSString *tipStr = [NSString stringWithFormat:@"本次充值交易保证金%.2lf元。\n如果查询未到账，可能是网络问题而导致暂时充值不成功，请联系客服。",_chargeMoney];
            [self loadCommanViewWithImgName:successTipImgName tipTitle:@"已充值成功，请查询确认" tipDetail:tipStr buttonTitle:@"查看我的钱包" btnSelector:@selector(skipToMypurse)];
        }
            break;
        case tip_chargePaymentSuccess: {
            self.title = @"支付成功";
            NSString *tipStr = [NSString stringWithFormat:@"本次充值交易货款%.2lf元。\n如果查询未到账，可能是网络问题而导致暂时充值不成功，请联系客服。",_chargeMoney];
            [self loadCommanViewWithImgName:successTipImgName tipTitle:@"已充值成功，请查询确认" tipDetail:tipStr buttonTitle:@"查看我的钱包" btnSelector:@selector(skipToMypurse)];
        }
            break;
        default:
            break;
    }
}

- (void)loadCommanViewWithImgName:(NSString *)imgName
                         tipTitle:(NSString *)title
                        tipDetail:(NSString *)detatil
                      buttonTitle:(NSString *)btnTitle
                      btnSelector:(SEL)selector {
    
    UIImageView *markView = [[UIImageView alloc] initWithFrame:CGRectMake(SCREEN_WIDTH/2-60/2, 50, 60, 60)];
    markView.image = [UIImage imageNamed:imgName];
    [self.view addSubview:markView];
    
    UILabel *tipLabel = [UILabel labelWithTitle:title];
    tipLabel.frame = CGRectMake(0, markView.vbottom+20, SCREEN_WIDTH, 25);
    tipLabel.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:tipLabel];
    
    UILabel *detailLabel = [UILabel labelWithTitle:detatil];
    detailLabel.numberOfLines = 0;
    detailLabel.textColor = [UIColor grayColor];
    detailLabel.frame = CGRectMake(20, tipLabel.vbottom+20, SCREEN_WIDTH-40, 0);
    [detailLabel sizeToFit];
    detailLabel.frame = CGRectMake(20, tipLabel.vbottom+20, SCREEN_WIDTH-40, detailLabel.vheight);
    detailLabel.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:detailLabel];
    
    UIButton *sureBtn = [UIFactory createBtn:BlueButtonImageName bTitle:btnTitle bframe:CGRectMake(15, detailLabel.vbottom+30, SCREEN_WIDTH-30, 40)];
    [sureBtn addTarget:self action:selector forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:sureBtn];
}

- (void)loadCommanViewWithImgName:(NSString *)imgName
                         tipTitle:(NSString *)title
                  tipDetailheader:(NSString *)detatil
                    tipDetailbody:(NSString *)detBody
                     tipDetailEnd:(NSString *)detEnd
                      buttonTitle:(NSString *)btnTitle
                      btnSelector:(SEL)selector {
    UIImageView *markView = [[UIImageView alloc] initWithFrame:CGRectMake(SCREEN_WIDTH/2-60/2, 50, 60, 60)];
    markView.image = [UIImage imageNamed:imgName];
    [self.view addSubview:markView];
    
    UILabel *tipLabel = [UILabel labelWithTitle:title];
    tipLabel.frame = CGRectMake(0, markView.vbottom+20, SCREEN_WIDTH, 25);
    tipLabel.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:tipLabel];
    
    UILabel *detailLabel = [UILabel labelWithTitle:detatil];
    detailLabel.frame = CGRectMake(20, tipLabel.vbottom+20, SCREEN_WIDTH-40, 45);
    detailLabel.numberOfLines = 2;
    detailLabel.textColor = [UIColor grayColor];
    detailLabel.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:detailLabel];
    
    UIButton *sureBtn = [UIFactory createBtn:BlueButtonImageName bTitle:btnTitle bframe:CGRectMake(detailLabel.vleft, detailLabel.vbottom+30, detailLabel.vwidth, 40)];
    [sureBtn addTarget:self action:selector forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:sureBtn];
    
    NSDictionary *styleDic = @{@"glbody":[UIColor redColor]};
    NSString *targetStr = [NSString stringWithFormat:@"%@<glbody>%@</glbody>%@",detatil,detBody,detEnd];
    detailLabel.attributedText = [targetStr attributedStringWithStyleBook:styleDic];
}

#pragma mark - UIActions
- (void)skipToMypurse {
    MypurseViewController *vc = [self findDesignatedViewController:[MypurseViewController class]];
    if (vc) {
        [self.navigationController popToViewController:vc animated:YES];
    }else {
        [self.navigationController popToRootViewControllerAnimated:YES];
    }
}

- (void)popToBussinessVC {
    BusinessViewController *vc = [self findDesignatedViewController:[BusinessViewController class]];
    [self.navigationController popToViewController:vc animated:YES];
}

- (void)chekeMySupply {
    MySupplyViewController *vc = [self findDesignatedViewController:[MySupplyViewController class]];
    if (!vc) {
        vc = [[MySupplyViewController alloc] init];
        [self.navigationController pushViewController:vc animated:YES];
    }else {
        [self.navigationController popToViewController:vc animated:YES];
    }
}// 查看我的供求

- (void)checkMyContract:(UIButton *)btn {
    MyContractViewController *vc = [self findDesignatedViewController:[MyContractViewController class]];
    if (!vc) {
        ContractWaitSureViewController *vc1 = [[ContractWaitSureViewController alloc] init];
        ContractPorccesingViewController *vc2 = [[ContractPorccesingViewController alloc] init];
        ContractEndedViewController *vc3 = [[ContractEndedViewController alloc] init];
        
        MyContractViewController *vc = [[MyContractViewController alloc] initSlidingViewControllerWithTitle:@"待确认合同" viewController:vc1];
        [vc addControllerWithTitle:@"进行中合同" viewController:vc2];
        [vc addControllerWithTitle:@"已结束合同" viewController:vc3];
        vc.selectedLabelColor = [UIColor orangeColor];
        vc.unselectedLabelColor = RGB(100, 100, 100, 1);
        [self.navigationController pushViewController:vc animated:YES];
    }else {
        [self.navigationController popToViewController:vc animated:YES];
    }
}

- (void)checkMyContractDetail:(UIButton *)btn {
    ContractProcessDetailViewController *vc = [[ContractProcessDetailViewController alloc] init];
    vc.contractId = _contractId;
    [self.navigationController pushViewController:vc animated:YES];
}

#pragma mark - Overerid
- (void)backRootVC {
    if (_operationType == tip_public_success || _operationType == tip_cancel_public_success || _operationType == tip_delete_public) {
        UIViewController *vc = [self findDesignatedViewController:[BusinessViewController class]];
        if (!vc) {
            vc = [self findDesignatedViewController:[MySupplyViewController class]];
        }
        if (!vc) {
            [self.navigationController popToRootViewControllerAnimated:YES];
        }else {
            [self.navigationController popToViewController:vc animated:YES];
        }
    }else if (_operationType == tip_my_sure_contract_success
              || _operationType == tip_both_sure_contract_success
              || _operationType == tip_buyer_sure_success
              || _operationType == tip_cancel_contract_success
              || _operationType == tip_contract_pay_success
              || _operationType == tip_seller_sure_payment
              || _operationType == tip_arbitrate_success
              || _operationType == tip_moveContract
              || _operationType == tip_cancel_contracting_success
              || _operationType == tip_deleteContract
              || _operationType == tip_reviewContract) {
        
        UIViewController *vc = [self findDesignatedViewController:[MyContractViewController class]];
        if (!vc) {
            vc = [self findDesignatedViewController:[MySupplyViewController class]];
        }
        if (!vc) {
            vc = [self findDesignatedViewController:[MypurseViewController class]];
        }
        if (!vc) {
            vc = [self findDesignatedViewController:[BusinessViewController class]];
        }
        
        if (!vc) {
            [self.navigationController popToRootViewControllerAnimated:YES];
        }else {
            [self.navigationController popToViewController:vc animated:YES];
        }
    }else if (_operationType == tip_rollout_success || _operationType == tip_to_margin_success || _operationType == tip_chargeMarginSuccess || _operationType == tip_chargePaymentSuccess) {
        MypurseViewController *vc = [self findDesignatedViewController:[MypurseViewController class]];
        if (!vc) {
            [self.navigationController popToRootViewControllerAnimated:YES];
        }else {
            [self.navigationController popToViewController:vc animated:YES];
        }
    }else if (_operationType == tip_inquiry_success) {
        BusinessViewController *vc = [self findDesignatedViewController:[BusinessViewController class]];
        if (!vc) {
            [self.navigationController popToRootViewControllerAnimated:YES];
        }else {
            [self.navigationController popToViewController:vc animated:YES];
        }
    }
    else {
        [self.navigationController popToRootViewControllerAnimated:YES];
    }
}

@end
