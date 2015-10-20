//
//  ContractTableViewCell.m
//  Glshop
//
//  Created by River on 15-1-26.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  合同待确认列表cell

#import "ContractTableViewCell.h"
#import "ContractModel.h"

@interface ContractTableViewCell ()

@property (strong, nonatomic) IBOutlet UILabel *timeLabel;
@property (strong, nonatomic) IBOutlet UIImageView *logoImgView;
@property (strong, nonatomic) IBOutlet UILabel *productNameLabel;
@property (strong, nonatomic) IBOutlet UILabel *totalLabel;
@property (strong, nonatomic) IBOutlet UILabel *priceLabel;
@property (strong, nonatomic) IBOutlet UILabel *stasusLabel;
@property (strong, nonatomic) IBOutlet UILabel *detailLabel;
@property (strong, nonatomic) IBOutlet UIButton *button;
@property (strong, nonatomic) IBOutlet UILabel *titleTipLabel;
@property (strong, nonatomic) IBOutlet UILabel *timeTitleLabel;



@end

@implementation ContractTableViewCell

- (void)awakeFromNib {
    // Initialization code
    _button.titleLabel.font = [UIFont systemFontOfSize:13.f];
    _button.imageEdgeInsets = UIEdgeInsetsMake(0, 20, 0, 0);
    
    [_stasusLabel setAdjustsFontSizeToFitWidth:YES];
}

- (void)setContractModel:(ContractModel *)contractModel {
    if (_contractModel != contractModel) {
        _contractModel = contractModel;
        
        // 总量
        _totalLabel.text = [NSString stringWithFormat:@"%.2f%@",[contractModel.totalnum floatValue],unit_tun];
        // 单价
        _priceLabel.text = [NSString stringWithFormat:@"%.2f%@",[contractModel.price floatValue],unit_per_price_tun];
        // 产品名称
        _productNameLabel.text = [SynacObject combinProducName:contractModel.productType proId:contractModel.productId];
        [self setDifferentBtnStyle:NO];
        BOOL isBuyer = [contractModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
        _logoImgView.image = isBuyer ? [UIImage imageNamed:@"supply-and-demand_icon_qiugou"] : [UIImage imageNamed:@"supply-and-demand_icon_shushou"];
        
        ContractStatus myContractType = [contractModel.myContractType[DataValueKey] integerValue];
        if (myContractType == DRAFT) {
            [self waitSureCellSetup:contractModel];
        }else if (myContractType == DOING) {
            _timeLabel.text = [Utilits timeStrFomate:contractModel.limittime];
            [self processingCellSetup:contractModel];
        }else if (myContractType == FINISHED) {
            [self grayCell:isBuyer];
            [self endContractCellSetup:contractModel];
        }
    }
    
}

/**
 *@brief 无效合同显示为灰色
 */
- (void)grayCell:(BOOL)isBuyer {
//    _logoImgView.image = isBuyer ? [UIImage imageNamed:@"Buy_sell_qiugou"] : [UIImage imageNamed:@"Buy_sell_chushou"];
    _logoImgView.image = isBuyer ? [UIImage imageNamed:@"Buy_sell_qiugou_gray"] : [UIImage imageNamed:@"Buy_sell_chushou_gray"];
    _timeLabel.textColor = [UIColor grayColor];
//    _stasusLabel.textColor = [UIColor blackColor];
//    _detailLabel.textColor = [UIColor blackColor];
//    _priceLabel.textColor = [UIColor blackColor];
//    _totalLabel.textColor = [UIColor blackColor];
    
    _productNameLabel.textColor = [UIColor grayColor];
    _stasusLabel.textColor = [UIColor grayColor];
    _detailLabel.textColor = [UIColor grayColor];
    _priceLabel.textColor = [UIColor grayColor];
    _totalLabel.textColor = [UIColor grayColor];
}

- (void)renderCell:(BOOL)isBuyer {
    _logoImgView.image = isBuyer ? [UIImage imageNamed:@"Buy_sell_qiugou"] : [UIImage imageNamed:@"Buy_sell_chushou"];
    _timeLabel.textColor = [UIColor redColor];
    _stasusLabel.textColor = [UIColor redColor];
    _detailLabel.textColor = [UIColor blackColor];
    _priceLabel.textColor = [UIColor redColor];
    _totalLabel.textColor = [UIColor redColor];
}

/**
 *@brief 配置待确认合同列表显示内容
 */
- (void)waitSureCellSetup:(ContractModel *)contractModel {
    _timeTitleLabel.text = @"有效期限:";
    ContractDraftStageBuyerSellerDoType drftTypeBuyer = [contractModel.buyerDraftStatus[DataValueKey] integerValue];
    ContractDraftStageBuyerSellerDoType drftTypeSeller = [contractModel.sellerDraftStatus[DataValueKey] integerValue];
    
    _detailLabel.text = contract_tip_three_sure;
    
    BOOL isBuyer = [contractModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
    if (![contractModel contractValide] || [[Utilits timeGap:contractModel.draftLimitTime] isEqualToString:@"0小时0分"]) { // 合同已失效
        DLog(@"时间 ＝＝ %d",[[Utilits timeGap:contractModel.draftLimitTime] isEqualToString:@"0小时0分"]);
        DLog(@"状态 == %d",[contractModel contractValide]);
        
        _timeLabel.text = contract_invalid_timeout;
        [self grayCell:isBuyer];
        [self setDifferentBtnStyle:NO];
        if ([contractModel.buyerDraftStatus[DataValueKey] integerValue] == NOTHING && [contractModel.sellerDraftStatus[DataValueKey] integerValue] == NOTHING) {  // 如果双方都未确认
            _stasusLabel.text = contract_timout_for_both;
        }else if (isBuyer) {
            if (drftTypeBuyer == NOTHING) {
                if (drftTypeSeller == CANCEL) {
                    _stasusLabel.text = contract_invalid_cancle_for_opposite;
                }else {
                    _stasusLabel.text = contract_timeout_for_me;
                }
            }else if (drftTypeBuyer == CANCEL) {
                _stasusLabel.text = contract_invalid_cancle_for_me;
            }else if (drftTypeBuyer == CONFIRM) {
                if (drftTypeSeller == NOTHING) {
                    _stasusLabel.text = contract_timeout_for_oppsite;
                }else if (drftTypeSeller == CANCEL) {
                    _stasusLabel.text = contract_invalid_cancle_for_opposite;
                }
            }
        }else if (!isBuyer) {
            if (drftTypeSeller == NOTHING) {
                if (drftTypeBuyer == CANCEL) {
                    _stasusLabel.text = contract_invalid_cancle_for_opposite;
                }else {
                    _stasusLabel.text = contract_timeout_for_me;
                }
            }else if (drftTypeSeller == CANCEL) {
                _stasusLabel.text = contract_invalid_cancle_for_me;
            }else if (drftTypeSeller == CONFIRM) {
                if (drftTypeBuyer == NOTHING) {
                    _stasusLabel.text = contract_timeout_for_oppsite;
                }else if (drftTypeBuyer == CANCEL) {
                    _stasusLabel.text = contract_invalid_cancle_for_opposite;
                }
            }
        }
    }else { // 合同有效
        [self renderCell:isBuyer];
        _timeLabel.text = [Utilits timeGap:contractModel.draftLimitTime];
        if ([contractModel.buyerDraftStatus[DataValueKey] integerValue] == NOTHING && [contractModel.sellerDraftStatus[DataValueKey] integerValue] == NOTHING) {  // 如果双方都未确认
            [self setDifferentBtnStyle:NO];
            _stasusLabel.text = contract_valid_waitBoth_sure;
        }else if (isBuyer) { // 我是买家
            if (drftTypeBuyer == NOTHING) { // 我还没确认
                [self setDifferentBtnStyle:NO];
                _stasusLabel.text = contract_valid_waitMe_sure;
            }
            if (drftTypeBuyer == CONFIRM) { // 我已确认
                _stasusLabel.text = contract_valid_waitOpposite_sure;
            }
        }else if (!isBuyer) { // 我是卖家
            if (drftTypeSeller == NOTHING) { // 我还没确认
                [self setDifferentBtnStyle:NO];
                _stasusLabel.text = contract_valid_waitMe_sure;
            }
            if (drftTypeSeller == CONFIRM) { // 我已确认
                _stasusLabel.text = contract_valid_waitOpposite_sure;
            }
        }
    }
}

/**
 *@brief 配置进行中合同列表显示内容
 */

- (void)processingCellSetup:(ContractModel *)contractModel {
    _stasusLabel.textColor = [UIColor blackColor];
    _detailLabel.textColor = [UIColor redColor];
    _detailLabel.font = [UIFont systemFontOfSize:12.f];
    _detailLabel.numberOfLines = 2;
    
    BOOL isBuyer = [contractModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
    ContractOperateType buyOperateType = [contractModel.buyerOperatorType[DataValueKey] integerValue];
    ContractLifeCycle lifeCycle = [contractModel.lifecycle[DataValueKey] integerValue];
    
    ContractLifeCycle sellerOperatorStatus = [_contractModel.sellerOperatorStatus[DataValueKey] integerValue];
    ContractLifeCycle buyerOperatorStatus = [_contractModel.buyerOperatorStatus[DataValueKey] integerValue];
    
    if ([contractModel contractValide]) { //-------------- 合同有效  ------------------------------
        [self renderCell:isBuyer];
        _timeTitleLabel.text = @"交货时间:";
        if (isBuyer) { // 如果我是买家
            // 检查我的操作类型
            switch (buyOperateType) {
                case CONFRIM_CONTRACT:
                {
                    _stasusLabel.text = contract_valid_wait_pay;
                    _detailLabel.text = contract_pay_tip;
                }
                    break;
                case PAYED_Buyer_FUNDS:
                {
                    _stasusLabel.text = contract_valid_payed;
                    _detailLabel.text = contract_wait_seller_post;
                }
                    break;
                case FUNDS_GOODS_CONFIRM:
                {
                    if (sellerOperatorStatus == NORMAL_FINISHED) {
                        _stasusLabel.text = contract_seller_sure;
                        _detailLabel.text = contract_tip_comman;
                    }else if (sellerOperatorStatus == ARBITRATING) { // 卖家申请了仲裁
                        _stasusLabel.text = contract_seller_post_arbitrate;
                        _detailLabel.text = btntitle_contract_frzee;
                    }else if (buyerOperatorStatus == ARBITRATING) {//买家申请了仲裁
                     //   _stasusLabel.text = contract_self_post_arbitrate;
                        _stasusLabel.text = contract_buyer_post_arbitrate;
                        _detailLabel.text = btntitle_contract_frzee;
                    }else {
                        _stasusLabel.text = contract_post_sure;
                        _detailLabel.text = contract_wait_seller_sure;
                    }
                    
                }
                    break;
                case APPLY_ARBITRATION:
                {
                    _stasusLabel.text = contract_self_post_arbitrate;
                    _detailLabel.text = btntitle_contract_frzee;
                }
                    break;
                    
                default:
                    break;
            }
            
        }else { // 如果我是卖家
            
            // 检查我的操作类型
            switch (buyOperateType) { 
                case CONFRIM_CONTRACT:
                {
                    _stasusLabel.text = contract_valid_wait_pay_seller;
                    _detailLabel.text = contract_send_timely;
                }
                    break;
                case PAYED_Buyer_FUNDS:
                {
                    _stasusLabel.text = contract_valid_arrive_money;
                    _detailLabel.text = contract_seller_post;
                }
                    break;
                    case FUNDS_GOODS_CONFIRM:
                {
                    if (lifeCycle == CONFIRMING_GOODS_FUNDS) { // 卖家确认中
                        _stasusLabel.text = contract_valid_buyer_posted;
                        _detailLabel.text = contract_sure_recive_money;
                    }else if (sellerOperatorStatus == ARBITRATING) {
                       // _stasusLabel.text = contract_self_post_arbitrate;
                        _stasusLabel.text = contract_seller_post_arbitrate;
                        _detailLabel.text = btntitle_contract_frzee;
                    }else if (buyerOperatorStatus == ARBITRATING) {
                        _stasusLabel.text = contract_buyer_post_arbitrate;
                        _detailLabel.text = btntitle_contract_frzee;
                    }else if (lifeCycle == ARBITRATED) {
                        _stasusLabel.text = contract_freeze_end;
                        _detailLabel.text = contract_buisness_end;
                    }else if (lifeCycle == NORMAL_FINISHED) {
                        _stasusLabel.text = contract_did_sure_recive;
                        _detailLabel.text = contract_tip_comman;
                    }
                }
                    break;
                case APPLY_ARBITRATION:
                {
                    _stasusLabel.text = contract_buyer_post_arbitrate;
                    _detailLabel.text = btntitle_contract_frzee;
                }
                    break;
                    
                default:
                    break;
            }
        }
        
    }else { // -------------- 合同无效  ------------------------------
        [self grayCell:isBuyer];
        NSString *remainTime = [Utilits timeDHMGap:_contractModel.limittime];
        NSString *remainTimeStr = [NSString stringWithFormat:@"还有%@移至已结束合同",remainTime];
        _timeTitleLabel.text = remainTimeStr;
        _timeLabel.text = nil;
        
       
        
        if (isBuyer) {
            if (lifeCycle == BUYER_UNPAY_FINISH) { // 逾期未付款
                _stasusLabel.text = contract_timeout_pay;
                _detailLabel.text = contract_breach_paytoSeller;
            }else if (lifeCycle == SINGLECANCEL_FINISHED) {
                if (sellerOperatorStatus == SINGLECANCEL_FINISHED) {
                    _stasusLabel.text = contract_invalid_seller_cancel;
                    _detailLabel.text = contract_breach_money;
                }
            }else if (lifeCycle == ARBITRATED) {
                _stasusLabel.text = contract_freeze_end;
                _detailLabel.text = contract_buisness_end;
            }else if (lifeCycle == NORMAL_FINISHED) {
                _stasusLabel.text = contract_invalid_seller_posted;
                _detailLabel.text = _contractModel.isMeEvalution ? contract_buisness_normal_end : contract_tip_comman;
            }
        }else {
            if (lifeCycle == BUYER_UNPAY_FINISH) {
                _stasusLabel.text = contract_buyer_pay_timeout;
                _detailLabel.text = contract_breach_payforyou;
            }else if (lifeCycle == SINGLECANCEL_FINISHED) { // 单方取消
                if (buyOperateType == SINGLE_CANCEL) { // 买方取消
                    _stasusLabel.text = contract_invalid_buyer_cancel;
                    _detailLabel.text = contract_breach_money;
                }
            }else if (lifeCycle == ARBITRATED) {
                _stasusLabel.text = contract_freeze_end;
                _detailLabel.text = contract_buisness_end;
            }else if (lifeCycle == NORMAL_FINISHED) {
                _stasusLabel.text = contract_invalid_self_posted;
                _detailLabel.text = _contractModel.isMeEvalution ? contract_buisness_normal_end : contract_tip_comman;
            }
        }
        
        
    }
}

- (void)endContractCellSetup:(ContractModel *)contractModel {
    BOOL isBuyer = [contractModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;

    ContractLifeCycle lifeCycle = [contractModel.lifecycle[DataValueKey] integerValue];
    ContractLifeCycle sellerOperatorStatus = [contractModel.sellerOperatorStatus[DataValueKey] integerValue];
    ContractLifeCycle buyerOperatorStatus = [contractModel.buyerOperatorStatus[DataValueKey] integerValue];
    ContractStatus status = [_contractModel.myContractType[DataValueKey] integerValue];
    if (status  == FINISHED) {
        _timeLabel.text = [Utilits timeStrFomate:_contractModel.updatetime];
        _timeTitleLabel.text = @"结束时间:";
    }
    
    
        if (isBuyer) {
            if (lifeCycle == BUYER_UNPAY_FINISH) {
                _stasusLabel.text = contract_buyer_self_timeout_pay;
                _detailLabel.text = contract_breach_money_toOppsite;
            }else if (buyerOperatorStatus == SINGLECANCEL_FINISHED) {
                _stasusLabel.text = contract_invalid_self_cancel;
                _detailLabel.text = contract_breach_money_toOppsite;
            }else if (sellerOperatorStatus == SINGLECANCEL_FINISHED) {
                _stasusLabel.text = contract_invalid_seller_cancel;
                _detailLabel.text = contract_breach_money;
            }else if (buyerOperatorStatus == ARBITRATING || sellerOperatorStatus == ARBITRATING) {
                _stasusLabel.text = contract_freeze_end;
                _detailLabel.text = contract_buisness_end;
            }
            else if (lifeCycle == NORMAL_FINISHED) {
                _stasusLabel.text = contract_invalid_seller_posted;
                _detailLabel.text = _contractModel.isMeEvalution ? contract_buisness_normal_end : contract_tip_comman;
            }
        }else {
            if (lifeCycle == BUYER_UNPAY_FINISH) {
                _stasusLabel.text = contract_buyer_pay_timeout;
                _detailLabel.text = contract_breach_paytoSeller;
            }else if (sellerOperatorStatus == SINGLECANCEL_FINISHED) {
                _stasusLabel.text = contract_invalid_self_cancel;
                _detailLabel.text = contract_breach_money_toOppsite;
            }else if (buyerOperatorStatus == SINGLECANCEL_FINISHED) {
                _stasusLabel.text = contract_invalid_buyer_cancel;
                _detailLabel.text = contract_breach_money;
            }else if (buyerOperatorStatus == ARBITRATING || sellerOperatorStatus == ARBITRATING) {
                _stasusLabel.text = contract_freeze_end;
                _detailLabel.text = contract_buisness_end;
            }else if (lifeCycle == NORMAL_FINISHED) {
                _stasusLabel.text = contract_invalid_self_posted;
                _detailLabel.text = _contractModel.isMeEvalution ? contract_buisness_normal_end : contract_tip_comman;
            }
        }
    
    if (lifeCycle == ARBITRATED) {
        _stasusLabel.text = contract_freeze_end;
        _detailLabel.text = contract_buisness_end;
    }
}

/**
 *@brief 如果需要用户确认的合同，显示立即确认按钮，否则显示箭头
 */
- (void)setDifferentBtnStyle:(BOOL)isSure {
    if (isSure) {
        [_button setImage:nil forState:UIControlStateNormal];
        [_button setTitle:@"立即确认" forState:UIControlStateNormal];
        [_button setBackgroundImage:[UIImage imageNamed:@"Buy_sell_details"] forState:UIControlStateNormal];
    }else {
        [_button setTitle:nil forState:UIControlStateNormal];
        [_button setBackgroundImage:nil forState:UIControlStateNormal];
        [_button setImage:[UIImage imageNamed:@"jiantou"] forState:UIControlStateNormal];
    }
}

@end
