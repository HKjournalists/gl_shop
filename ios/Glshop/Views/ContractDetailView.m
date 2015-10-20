//
//  ContractDetailView.m
//  Glshop
//
//  Created by River on 15-2-3.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "ContractDetailView.h"
#import "ContractProductView.h"
#import "ContractModel.h"
#import "PayTradeViewController.h"
#import "PaymentSureViewController.h"
#import "SellerSureViewController.h"
#import "ContractStatusView.h"

static float ConDeltailcellHeight = 30;

typedef NS_ENUM(NSInteger, BottmViewType) {
    
    BottmViewType_unknow,
    /**
     *@brief 显示取消合同按钮和支付货款按钮
     */
    cancelBtn_payBtn,
    /**
     *@brief 显示取消合同按钮和货物货款实际确认按钮
     */
    cancelBtn_sureBtn,
    /**
     *@brief 显示取消合同按钮
     */
    cancelBtn,
    /**
     *@brief 显示合同评价按钮和移至已结束按钮
     */
    reviewBtn,
    /**
     *@brief 显示合同评价按钮
     */
    reviewBtnOnly,
    /**
     *@brief 显示移至已结束合同按钮
     */
    moveToendcontractBtn,
    /**
     *@brief 显示实际货款确认按钮和取消合同按钮
     */
    actual_reciveBtn_cancelBtn,
};

@interface ContractDetailView ()

@property (nonatomic, strong) UIView *bottomView;

@property (nonatomic, strong) UIView *leftView;

@property (nonatomic, strong) ContractStatusView *statusView;

/**
 *@brief 不同的状态显示不同的底部视图
 */
@property (nonatomic, assign) BottmViewType bottomViewType;


@property (nonatomic, copy) NSString *tipStr1;
@property (nonatomic, copy) NSString *tipStr2;
@property (nonatomic, copy) NSString *tipStr3;

@end

@implementation ContractDetailView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        [self loadSubViews];
    }
    return self;
}

- (void)loadSubViews {
    _headerView = UIView.new;
    _headerView.frame = CGRectMake(0, 0, self.vwidth, 100);
    [self addSubview:_headerView];
    _productView = [[ContractProductView alloc] initWithFrame:CGRectMake(0, 0, self.vwidth, 100)];
    [_headerView addSubview:_productView];
    
    _controlScrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, _headerView.vbottom, self.vwidth, 400)];
    _controlScrollView.pagingEnabled = YES;
    _controlScrollView.delegate = self;
    _controlScrollView.contentSize = CGSizeMake(self.vwidth*2, 0);
    _controlScrollView.showsHorizontalScrollIndicator = NO;
    [self addSubview:_controlScrollView];
    
    _leftView = [[UIView alloc] initWithFrame:_controlScrollView.bounds];
    [_controlScrollView addSubview:_leftView];
    
    _contractTableView = [[UITableView alloc] initWithFrame:_controlScrollView.bounds style:UITableViewStyleGrouped];
    _contractTableView.dataSource = self;
    _contractTableView.delegate = self;
    _contractTableView.sectionFooterHeight = 5;
    _contractTableView.sectionHeaderHeight = 5;
    _contractTableView.rowHeight = ConDeltailcellHeight;
    _contractTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [_leftView addSubview:_contractTableView];
    
    _productView.weakScrollView = _controlScrollView;
    
    _statusView = [[ContractStatusView alloc] initWithFrame:CGRectMake(_contractTableView.vright,
                                                          _contractTableView.vtop+10,
                                                          _contractTableView.vwidth,
                                                          _contractTableView.vheight)];
    [_controlScrollView addSubview:_statusView];
    
    
    self.backgroundColor = _contractTableView.backgroundColor;
    
}

#pragma mark - Setter
- (void)setContractModel:(ContractModel *)contractModel {
    if (_contractModel != contractModel) {
        _contractModel = contractModel;
        _statusView.contractModel = _contractModel;
        
        [self loadContractDetailViewDatas];
        
        float height = iPhone4 ? (_bottomView.vheight+kTopBarHeight+20) : _bottomView.vheight;
        _contractTableView.frame = CGRectMake(0, 0, self.vwidth, _leftView.vheight-height);
        
        
        _productView.contractId = _contractModel.contractId;
        NSString *saleStr = [_contractModel.saleType[DataValueKey] integerValue] == 1 ? @"求购 " : @"出售 ";
        NSString *productStr = [SynacObject combinProducName:_contractModel.productType proId:_contractModel.productId];
        _productView.productLabel.text = [NSString stringWithFormat:@"%@%@",saleStr,productStr];
        _productView.totalLabel.text = [NSString stringWithFormat:@"%.2f%@",[contractModel.totalnum floatValue],unit_tun];
        _productView.priceLabel.text = [NSString stringWithFormat:@"%.2f%@",[contractModel.price floatValue],unit_per_price_tun];
    }
}

- (void)setBottomViewType:(BottmViewType)bottomViewType {
    if (_bottomViewType != bottomViewType) {
        _bottomViewType = bottomViewType;
        switch (_bottomViewType) {
            case cancelBtn:
            {
                [self loadBottomViewWithOneBtn:btntitle_cancel_contract btnAction:@selector(cancelContractAction)];
            }
                break;
            case cancelBtn_payBtn:
            {
                [self loadBottomViewWithTwoBtn:btntitle_pay_plan btnAction:@selector(payAction) twoBtnTitle:btntitle_cancel_contract twobtnAction:@selector(cancelContractAction)];
            }
                break;
            case cancelBtn_sureBtn:
            {
                [self loadBottomViewWithTwoBtn:btntitle_sure_pay btnAction:@selector(surePaymentAction) twoBtnTitle:btntitle_cancel_contract twobtnAction:@selector(cancelContractAction)];
            }
                break;
            case reviewBtn:
            {
                NSString *btnTitle = _contractModel.isMeEvalution ? btntitle_view_comman : btntitle_contract_comman;
                [self loadBottomViewWithTwoBtn:btnTitle btnAction:@selector(ratingAction) twoBtnTitle:btntitle_move_end twobtnAction:@selector(moveToendAction)];
            }
                break;
            case reviewBtnOnly:
            {
                NSString *btnTitle = _contractModel.isMeEvalution ? btntitle_view_comman : btntitle_contract_comman;
                [self loadBottomViewWithOneBtn:btnTitle btnAction:@selector(ratingAction)];
            }
                break;
            case moveToendcontractBtn:
            {
                [self loadBottomViewWithOneBtn:btntitle_move_end btnAction:@selector(moveToendAction)];
            }
                break;
            case actual_reciveBtn_cancelBtn:
            {
                [self loadBottomViewWithTwoBtn:btntitle_pay_actual_sure btnAction:@selector(acualSureAction) twoBtnTitle:btntitle_cancel_contract twobtnAction:@selector(cancelContractAction)];
            }
                break;
            default:
                break;
        }
    }
}


#pragma mark - Private
- (void)loadBottomViewWithOneBtn:(NSString *)btnTitle btnAction:(SEL)action {
    _bottomView = nil;
    
    NSInteger oneBtnBottomViewHeight = 55;
    
    _bottomView = UIView.new;
    [_leftView addSubview:_bottomView];
    float height = iPhone4 ? (oneBtnBottomViewHeight+kTopBarHeight+20) : oneBtnBottomViewHeight;
    _bottomView.frame = CGRectMake(0, _leftView.vbottom-height, _leftView.vwidth, oneBtnBottomViewHeight);
    
    UIButton *btn = [UIFactory createBtn:BlueButtonImageName bTitle:btnTitle bframe:CGRectZero];
    [btn addTarget:self.firstViewController action:action forControlEvents:UIControlEventTouchUpInside];
    [_bottomView addSubview:btn];
    btn.frame = CGRectMake(10, 10, _bottomView.vwidth-20, 35);
    
}// 底部视图只带一个按钮

- (void)loadBottomViewWithTwoBtn:(NSString *)btnTitle btnAction:(SEL)action twoBtnTitle:(NSString *)twoBtnTitleStr twobtnAction:(SEL)tAction {
    _bottomView = nil;
    NSInteger twoBtnBottomViewHeight = 100;
    _bottomView = UIView.new;
    [_leftView addSubview:_bottomView];
    float height = iPhone4 ? (twoBtnBottomViewHeight+kTopBarHeight+20) : twoBtnBottomViewHeight;
    _bottomView.frame = CGRectMake(0, _leftView.vbottom-height, _leftView.vwidth, twoBtnBottomViewHeight);
    
    UIButton *btn = [UIFactory createBtn:BlueButtonImageName bTitle:btnTitle bframe:CGRectZero];
    [btn addTarget:self.firstViewController action:action forControlEvents:UIControlEventTouchUpInside];
    [_bottomView addSubview:btn];
    btn.frame = CGRectMake(10, 10, _leftView.vwidth-20, 35);
    
    UIButton *btn1 = [UIFactory createBtn:YelloCommnBtnImgName bTitle:twoBtnTitleStr bframe:CGRectZero];
    [btn1 addTarget:self.firstViewController action:tAction forControlEvents:UIControlEventTouchUpInside];
    [_bottomView addSubview:btn1];
    btn1.frame = CGRectMake(btn.vleft, btn.vbottom+10, btn.vwidth, btn.vheight);
    
}// 底部视图显示两个操作按钮

- (void)loadContractDetailViewDatas {
    BOOL isBuyer = [_contractModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
    ContractOperateType buyOperateType = [_contractModel.buyerStatuobj.type[DataValueKey] integerValue];
    ContractLifeCycle buyerOldStatus = [_contractModel.buyerOperatorOldStatus [DataValueKey] integerValue];
    ContractLifeCycle buyerStatus = [_contractModel.buyerOperatorStatus[DataValueKey] integerValue];
    ContractOperateType sellerOperateType = [_contractModel.sellerStatuobj.type[DataValueKey] integerValue];
    ContractLifeCycle lifeCycle = [_contractModel.lifecycle[DataValueKey] integerValue];
    ContractStatus status = [_contractModel.myContractType[DataValueKey] integerValue];
    
    if (lifeCycle == SINGED) { // 合同已签订
        self.bottomViewType = isBuyer ? cancelBtn_payBtn : cancelBtn;
        [_productView.btn1 setTitle:btntitle_waite_handle forState:UIControlStateNormal];
        
        _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                          @{@1:@[contract_status_pay]},
                                          @{@0:@[contract_status_goodsSure,contract_status_pay_sure]},
                                          @{@0:@[contract_status_nopro,contract_status_platform_pay]}];
        _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                            @{@1:@[contract_status_post]},
                                            @{@0:@[contract_status_recive_sure]},
                                            @{@0:@[contract_status_nopro,contract_status_platform_statement]}];
        
        NSString *remainTime = [Utilits timeDHMGap:_contractModel.payGoodsLimitTime];
        _tipStr3 = [NSString stringWithFormat:@"%@%@",contract_tip_time_header,remainTime];
        if (isBuyer) {
            _tipStr1 = contract_tipToPay;
            _tipStr2 = contract_pay_tip;
        }else {
            _tipStr1 = contract_tipToSellerRepay;
            _tipStr2 = contract_send_timely;
        }
    }
    
    else if (lifeCycle == BUYER_UNPAY_FINISH) {
        self.bottomViewType = status == FINISHED ?  BottmViewType_unknow : moveToendcontractBtn;
        [_productView.btn1 setTitle:btntitle_contract_ended forState:UIControlStateNormal];
        
        if (isBuyer) {
            _tipStr1 = contract_pay_outTime;
            _tipStr2 = contract_buisness_end;
        }else {
            _tipStr1 = contract_pay_outTime_seller;
            _tipStr2 = contract_buisness_end;
        }
        
        _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                          @{@2:@[contract_status_pay]},
                                          @{@2:@[contract_status_outtime,contract_status_end]}];
        _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                            @{@2:@[contract_status_post]},
                                            @{@2:@[contract_status_beEnded]}];
    }
    
    else if (lifeCycle == PAYED_FUNDS) {
        self.bottomViewType = isBuyer ? cancelBtn_sureBtn : cancelBtn;
        [_productView.btn1 setTitle:btntitle_waite_handle forState:UIControlStateNormal];
        
        _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                          @{@2:@[contract_status_pay]},
                                          @{@1:@[contract_status_goodsSure,contract_status_pay_sure]},
                                          @{@0:@[contract_status_nopro,contract_status_platform_pay]}];
        _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                            @{@2:@[contract_status_post]},
                                            @{@1:@[contract_status_recive_sure]},
                                            @{@0:@[contract_status_nopro,contract_status_platform_statement]}];
        
        if (isBuyer) {
            _tipStr1 = contract_payed_already;
            _tipStr2 = contract_tip_action1;
        }else {
            _tipStr1 = contract_payed_already_buyer;
            _tipStr2 = contract_payer_tip_action3;
        }
    }
    
    else if (lifeCycle == CONFIRMING_GOODS_FUNDS) {
        self.bottomViewType = isBuyer ? cancelBtn : actual_reciveBtn_cancelBtn;
        
        _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                          @{@2:@[contract_status_pay]},
                                          @{@1:@[contract_status_goodsSure,contract_status_pay_sure]},
                                          @{@0:@[contract_status_nopro,contract_status_platform_pay]}];
        _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                            @{@2:@[contract_status_post]},
                                            @{@1:@[contract_status_recive_sure]},
                                            @{@0:@[contract_status_nopro,contract_status_platform_statement]}];
        
        if (isBuyer) {
            [_productView.btn1 setTitle:btntitle_contract_waite_suer forState:UIControlStateNormal];
            _tipStr1 = contract_payed_toSeller;
            _tipStr2 = contract_tip_action2;
        }else {
            [_productView.btn1 setTitle:btntitle_waite_handle forState:UIControlStateNormal];
            _tipStr1 = contract_payer_tip_action0;
            _tipStr2 = contract_payer_tip_action;
        }
    }
    
    else if (lifeCycle == NORMAL_FINISHED) {
        self.bottomViewType = status == DOING ? reviewBtn : reviewBtnOnly;
        [_productView.btn1 setTitle:btntitle_contract_ended forState:UIControlStateNormal];
        _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                          @{@2:@[contract_status_pay]},
                                          @{@2:@[contract_status_goodsSure,contract_status_pay_sure]},
                                          @{@2:@[contract_status_nopro,contract_status_platform_pay]}];
        _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                            @{@2:@[contract_status_post]},
                                            @{@2:@[contract_status_recive_sure]},
                                            @{@2:@[contract_status_nopro,contract_status_platform_statement]}];
        
        if (isBuyer) {
            _tipStr1 = contract_buisness_end;
            _tipStr2 = contract_tip_planRepay;
        }else {
            _tipStr1 = contract_buisness_end;
            _tipStr2 = contract_tip_planRepay_seller;
        }
    }
    
    else if (lifeCycle == ARBITRATING) {
        [_productView.btn1 setTitle:btntitle_contract_frzee forState:UIControlStateNormal];
        
        if ([_contractModel isMeApplyArbitrate]) {
            _tipStr1 = contract_self_arbitrate;
            _tipStr2 = contract_freeze_arbitrate_detail;
        }else {
            if (isBuyer) {
                _tipStr1 = contract_seller_post_arbi;
                _tipStr2 = contract_freeze_arbitrate_seller_detail;
            }else {
                _tipStr1 = contract_buyer_post_arbi;
                _tipStr2 = contract_freeze_arbitrate_buyer_detail;
            }
        }
        
        if ((isBuyer && [_contractModel isMeApplyArbitrate]) || (!isBuyer && ![_contractModel isMeApplyArbitrate])) {
            if (buyerOldStatus == SINGED) {
                _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                                  @{@1:@[contract_status_havepro,contract_status_paltarbtti]}];
                _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                                    @{@1:@[contract_status_freez]}];
            }else if (buyerOldStatus == PAYED_FUNDS) {
                _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                                  @{@2:@[contract_status_pay]},
                                                  @{@1:@[contract_status_havepro,contract_status_paltarbtti]}];
                _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                                    @{@2:@[contract_status_post]},
                                                    @{@1:@[contract_status_freez]}];
            }else if (buyerOldStatus == CONFIRMING_GOODS_FUNDS) {
                _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                                  @{@2:@[contract_status_pay]},
                                                  @{@2:@[contract_status_goodsSure,contract_status_pay_sure]},
                                                  @{@1:@[contract_status_havepro,contract_status_paltarbtti]}];
                _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                                    @{@2:@[contract_status_post]},
                                                    @{@2:@[contract_status_recive_sure]},
                                                    @{@1:@[contract_status_freez]}];
            }
        }else {
            if (buyerStatus == SINGED) {
                _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                                  @{@1:@[contract_status_freez]}];
                _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                                    @{@1:@[contract_status_havepro,contract_status_paltarbtti]}];
            }else if (buyerStatus == PAYED_FUNDS) {
                _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                                  @{@2:@[contract_status_pay]},
                                                  @{@1:@[contract_status_freez]}];
                _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                                    @{@2:@[contract_status_post]},
                                                    @{@1:@[contract_status_havepro,contract_status_paltarbtti]}];
            }else if (buyerStatus == CONFIRMING_GOODS_FUNDS) {
                _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                                  @{@2:@[contract_status_pay]},
                                                  @{@2:@[contract_status_goodsSure,contract_status_pay_sure]},
                                                  @{@1:@[contract_status_freez]}];
                _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                                    @{@2:@[contract_status_post]},
                                                    @{@2:@[contract_status_recive_sure]},
                                                    @{@1:@[contract_status_havepro,contract_status_paltarbtti]}];
            }
        }
    }
    
    else if (lifeCycle == ARBITRATED) {
        self.bottomViewType = status == DOING ? moveToendcontractBtn : BottmViewType_unknow;
        [_productView.btn1 setTitle:btntitle_contract_ended forState:UIControlStateNormal];
        _tipStr1 = contract_arbitrate_ended;
        _tipStr2 = contract_buisness_end;
        
        ContractLifeCycle buyerStatusOrOldStatus;
        if ((isBuyer && [_contractModel isMeApplyArbitrate]) || (!isBuyer && ![_contractModel isMeApplyArbitrate])) {
            buyerStatusOrOldStatus = buyerOldStatus;
        }else {
            buyerStatusOrOldStatus = buyerStatus;
        }
        
        if (buyerStatusOrOldStatus == SINGED) {
            _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                              @{@2:@[contract_status_plating,contract_status_endHandling]}];
            _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                                @{@2:@[contract_status_plating,contract_status_endHandling]}];
        }else if (buyerStatusOrOldStatus == PAYED_FUNDS) {
            _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                              @{@2:@[contract_status_pay]},
                                              @{@2:@[contract_status_plating,contract_status_endHandling]}];
            _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                                @{@2:@[contract_status_post]},
                                                @{@2:@[contract_status_plating,contract_status_endHandling]}];
        }else if (buyerStatusOrOldStatus == CONFIRMING_GOODS_FUNDS) {
            _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                              @{@2:@[contract_status_pay]},
                                              @{@2:@[contract_status_goodsSure,contract_status_pay_sure]},
                                              @{@2:@[contract_status_plating,contract_status_endHandling]}];
            _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                                @{@2:@[contract_status_post]},
                                                @{@2:@[contract_status_recive_sure]},
                                                @{@2:@[contract_status_plating,contract_status_endHandling]}];
        }
    }
    
    
    else if (lifeCycle == SINGLECANCEL_FINISHED) {
        [_productView.btn1 setTitle:btntitle_contract_ended forState:UIControlStateNormal];
        if ([_contractModel isMeCancelContract]) {
            _tipStr1 = contract_seller_canceled_self;
            if (isBuyer) {
                
                 _tipStr2 = contract_pay_tip_repay;
                
            }else{
               
                _tipStr2 = contract_pay_tip_repay_toBuyer;
            }
            
        }else {
            self.bottomViewType = status == DOING ? moveToendcontractBtn : BottmViewType_unknow;;
            
            if (isBuyer) {
                _tipStr1 = contract_seller_canceled;
                _tipStr2 = contract_tip_payToBuyer;
            }else {
                _tipStr1 = contract_buyer_canceled;
                _tipStr2 = contract_pay_tip_repay_toSeller;
            }
        }

        if (buyOperateType == SINGLE_CANCEL) {
            if (buyerOldStatus == SINGED) {
                _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                                  @{@2:@[btntitle_cancel_contract,contract_status_end]}];
                _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                                    @{@2:@[contract_status_beEnded]}];
            }else if (buyerOldStatus == PAYED_FUNDS) {
                _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                                  @{@2:@[contract_status_pay]},
                                                  @{@2:@[btntitle_cancel_contract,contract_status_end]}];
                _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                                    @{@2:@[contract_status_post]},
                                                    @{@2:@[contract_status_beEnded]}];
            }else if (buyerOldStatus == CONFIRMING_GOODS_FUNDS) {
                _statusView.leftItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                                  @{@2:@[contract_status_pay]},
                                                  @{@2:@[contract_status_goodsSure,contract_status_pay_sure]},
                                                  @{@2:@[btntitle_cancel_contract,contract_status_end]}];
                _statusView.rightItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                                    @{@2:@[contract_status_post]},
                                                    @{@2:@[contract_status_recive_sure]},
                                                    @{@2:@[contract_status_beEnded]}];
            }
        }else if (sellerOperateType == SINGLE_CANCEL) {
            _statusView.rightItemAttributes =@[@{@2:@[contract_satus_freez_money]},
                                              @{@2:@[contract_status_post]},
                                              @{@2:@[contract_status_recive_sure]},
                                              @{@2:@[btntitle_cancel_contract,contract_status_end]}];
            _statusView.leftItemAttributes = @[@{@2:@[contract_satus_freez_money]},
                                                @{@2:@[contract_status_pay]},
                                                @{@2:@[contract_status_goodsSure,contract_status_pay_sure]},
                                                @{@2:@[contract_status_beEnded]}];
        }
    }
    
    
    
    if (lifeCycle == ARBITRATED) {
        float h = 0;
        UIView *containerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.vwidth, 0)];
        UIImageView *imgView = [[UIImageView alloc] initWithFrame:CGRectMake(10, 0, self.vwidth-20, 0)];
        UIImage *image = [UIImage imageNamed:@"attestation_prompt_background"];
        image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeTile];
        imgView.image = image;
        [containerView addSubview:imgView];
        
        UILabel *titleLabel = [UILabel labelWithTitle:@"平台仲裁备注:"];
        titleLabel.frame = CGRectMake(5, 5, 100, 20);
        titleLabel.font = FontSystem(13.f);
        [imgView addSubview:titleLabel];
        
        UILabel *contentLabel = [UILabel labelWithTitle:_contractModel.arbitrationProcessInfo[@"dealresult"]];
        contentLabel.frame = CGRectMake(titleLabel.vleft, titleLabel.vbottom, imgView.vwidth-10, 0);
        contentLabel.numberOfLines = 0;
        contentLabel.font = FontSystem(12.f);
        [contentLabel sizeToFit];
        [imgView addSubview:contentLabel];
        
        h = titleLabel.vheight+contentLabel.vheight;
        imgView.vheight = h+20;
        containerView.vheight = h+20;
        _contractTableView.tableFooterView = containerView;
        
    }else {
        if (_tipStr3.length) {
            _contractTableView.tableHeaderView = [self loadTableHeaderViewText:_tipStr1 andText:_tipStr2 andText0:_tipStr3];
        }else {
            _contractTableView.tableHeaderView = [self loadTableHeaderViewText:_tipStr1 andText:_tipStr2 andText0:nil];
        }
    }
    

    [_contractTableView reloadData];
}

- (UIView *)loadTableHeaderViewText:(NSString *)text1 andText:(NSString *)text2 andText0:(NSString *)text0 {
    
    UIFont *font = [UIFont systemFontOfSize:13.f];
    
    UIView *contanierView = [[UIView alloc] init];
    contanierView.backgroundColor = _contractTableView.backgroundColor;
    contanierView.frame = CGRectMake(0, 0, SCREEN_WIDTH, 0);
    
    UILabel *label0;
    if (text0.length) {
        label0 = [UILabel labelWithTitle:text0];
        label0.font = font;
        label0.frame = CGRectMake(5, 5, 300, 20);
        NSMutableAttributedString *str = [[NSMutableAttributedString alloc] initWithString:text0];
        NSRange range = NSMakeRange(contract_tip_time_header.length, str.length-contract_tip_time_header.length);
        [str setAttributes:@{NSForegroundColorAttributeName:[UIColor redColor]} range:range];
        label0.attributedText = str;
        [contanierView addSubview:label0];
        contanierView.vheight += label0.vheight;
    }
    
    UILabel *label1 = [UILabel labelWithTitle:text1];
    label1.font = font;
    label1.numberOfLines = 0;
    float y = label0 ? label0.vbottom+3 : 5;
    label1.frame = CGRectMake(16, y, 300, 0);
    [label1 sizeToFit];
    [contanierView addSubview:label1];
    contanierView.vheight += label1.vheight;
    
    UIImageView *imgView = [[UIImageView alloc] initWithFrame:CGRectMake(5, label1.vbottom+5, 11, 11)];
    imgView.image = [UIImage imageNamed:@"agreement_icon_xingxing"];
    [contanierView addSubview:imgView];
    
    UILabel *label2 = [UILabel labelWithTitle:text2];
    label2.font = font;
    label2.numberOfLines = 0;
    label2.textColor = [UIColor redColor];
    label2.frame = CGRectMake(imgView.vright+3, imgView.vtop, 290, 0);
    [label2 sizeToFit];
    [contanierView addSubview:label2];
    contanierView.vheight += label2.vheight+20;
    
    return contanierView;
}// tableView的头部视图

- (UIView *)addSegmentLineToCellWithStr:(NSString *)str {
    UIView *contanierView = UIView.new;
    contanierView.frame = CGRectMake(0, 0, _contractTableView.vwidth, ConDeltailcellHeight);
    UILabel *label = [UILabel labelWithTitle:str];
    label.font = [UIFont systemFontOfSize:13.f];
    label.textColor = [UIColor grayColor];
    label.frame = CGRectMake(28+12, 0, 80, ConDeltailcellHeight);
    [contanierView addSubview:label];
    
    UIImageView *imgView = [[UIImageView alloc] initWithFrame:CGRectMake(15, 14, 28, 1)];
    imgView.image = [UIImage imageNamed:@"agreement_xian"];
    [contanierView addSubview:imgView];
    
    UIImageView *imgView1 = [[UIImageView alloc] initWithFrame:CGRectMake(125, 14, 200, 1)];
    imgView1.image = [UIImage imageNamed:@"agreement_xianlong"];
    [contanierView addSubview:imgView1];
    
    return contanierView;
}

#pragma mark - UIActions
- (void)payAction {
}// 向平台支付货款
- (void)cancelContractAction {
}// 取消合同
- (void)ratingAction {
}// 合同评价
- (void)surePaymentAction {
}// 货物与货款的实际确认
- (void)moveToendAction {
}// 移到已结束合同
- (void)acualSureAction {
}// 实际货款确认

#pragma mark - UITableView
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    ContractLifeCycle lifeCycle = [_contractModel.lifecycle[DataValueKey] integerValue];
    if (lifeCycle == NORMAL_FINISHED || lifeCycle == ARBITRATED) {
        return 3;
    }
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if ([_contractModel contractValide]) { // 合同有效，没有结算账单
        return 0;
    }else {
        if (section == 0) {
            return 7;
        }
        BOOL isBuyer = [_contractModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
        ContractLifeCycle sellerOperatorStatus = [_contractModel.sellerOperatorStatus[DataValueKey] integerValue];
        ContractLifeCycle buyerOperatorStatus = [_contractModel.buyerOperatorStatus[DataValueKey] integerValue];
        ContractLifeCycle lifeCycle = [_contractModel.lifecycle[DataValueKey] integerValue];
        
        if (lifeCycle == ARBITRATED) { // 仲裁结束
            if (section == 1) {
                return 4;
            }else if (section == 2) {
                return 3;
            }
        }else if (lifeCycle == NORMAL_FINISHED) { // 正常结束
            if (section == 1) {
                return 5;
            }else if (section == 2) {
                return isBuyer ? 3 : 4;
            }
        }else if (lifeCycle == BUYER_UNPAY_FINISH) {
            return 3;
        }
        else {
            if (buyerOperatorStatus == BUYER_UNPAY_FINISH) {
                return 3;
            }else if (buyerOperatorStatus == SINGLECANCEL_FINISHED) {
                return isBuyer ? 4 : 3;
            }else if (sellerOperatorStatus == SINGLECANCEL_FINISHED) {
                return isBuyer ? 4 : 3;
            }
        }
        
    }
    return 0;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    UIFont *font = [UIFont systemFontOfSize:13.f];
    cell.textLabel.font = font;
    cell.detailTextLabel.font = font;
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    
    BOOL isBuyer = [_contractModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
    ContractLifeCycle sellerOperatorStatus = [_contractModel.sellerOperatorStatus[DataValueKey] integerValue];
    ContractLifeCycle buyerOperatorStatus = [_contractModel.buyerOperatorStatus[DataValueKey] integerValue];
    ContractLifeCycle lifeCycle = [_contractModel.lifecycle[DataValueKey] integerValue];
    
    if (indexPath.row == 0) {
        UIView *line = [[UIView alloc] initWithFrame:CGRectMake(kCellLeftEdgeInsets, ConDeltailcellHeight-0.5, cell.vwidth-kCellLeftEdgeInsets, 0.5)];
        line.backgroundColor = [UIColor lightGrayColor];
        [cell.contentView addSubview:line];
    }
    
    if (indexPath.section == 0) {
        NSArray *texts = @[@"合同记载：",@"合同货物单价",@"合同货物总量",@"",@"合同货物总价",@"",@"合同货物总价"];
        cell.textLabel.text = texts[indexPath.row];
        if (indexPath.row == 0) {
            
        }else if (indexPath.row == 1) {
            UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_per_dun_money cellHeight:ConDeltailcellHeight];
            [cell addSubview:unitlabel];
            cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",[_contractModel.price floatValue]];
        }else if (indexPath.row == 2) {
            UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_dun cellHeight:ConDeltailcellHeight];
            [cell addSubview:unitlabel];
            cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",[_contractModel.totalnum floatValue]];
        }else if (indexPath.row == 3) {
            [cell addSubview:[self addSegmentLineToCellWithStr:@"【合同金额】"]];
        }else if (indexPath.row == 4) {
            UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_yuan cellHeight:ConDeltailcellHeight];
            [cell addSubview:unitlabel];
            cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",[_contractModel.totalamount floatValue]];
        }else if (indexPath.row == 5) {
            [cell addSubview:[self addSegmentLineToCellWithStr:@"【买方付款】"]];
        }else if (indexPath.row == 6) {
            UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_yuan cellHeight:ConDeltailcellHeight];
            [cell addSubview:unitlabel];
            
            FinalEstimateListModel *model = [_contractModel findleTraeModel:PAYMENT_FOR_GOODS];
            NSNumber *money = model.amount;
            if (!money) {
                money = [NSNumber numberWithFloat:0.0];
            }
            cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",[_contractModel.payFundsAmount floatValue]];
        }
    }else if (indexPath.section == 1) {
        if (lifeCycle == ARBITRATED) { // 仲裁结束----------------------------------------------------
            NSArray *texts = @[@"仲裁最终处理结果：",@"仲裁合同单价",@"仲裁合同总量",@"仲裁合同货物总价",];
            cell.textLabel.text = texts[indexPath.row];
            NSDictionary *atbitra = _contractModel.arbitrationDisPriceList.firstObject;
            float price = [atbitra[@"endamount"] floatValue];
            float total = [atbitra[@"endnum"] floatValue];
            if (indexPath.row == 0) {
                NSString *dealtime = [_contractModel.arbitrationProcessInfo objectForKey:@"dealtime"];
                cell.textLabel.text = [NSString stringWithFormat:@"%@%@",dealtime,texts.firstObject];
            }
            if (indexPath.row == 1) {
                UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_per_dun_money cellHeight:ConDeltailcellHeight];
                [cell addSubview:unitlabel];
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",price];
            }else if (indexPath.row == 2) {
                UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_dun cellHeight:ConDeltailcellHeight];
                [cell addSubview:unitlabel];
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",total];
            }else if (indexPath.row == 3) {
                UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_yuan cellHeight:ConDeltailcellHeight];
                [cell addSubview:unitlabel];
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",price*total];
            }

        }else if (lifeCycle == NORMAL_FINISHED) { // 正常结束----------------------------------------------------
            NSArray *texts = @[@"买卖双方最终确认：",@"合同货物单价",@"合同货物总量",@"",@"合同货物总价",];
            NSDictionary *atbitra = _contractModel.fundGoodsDisPriceList;
            float price = [atbitra[@"endamount"] floatValue];
            float total = [atbitra[@"endnum"] floatValue];
            cell.textLabel.text = texts[indexPath.row];
            if (indexPath.row == 1) {
                UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_per_dun_money cellHeight:ConDeltailcellHeight];
                [cell addSubview:unitlabel];
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",price];
            }else if (indexPath.row == 2) {
                UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_dun cellHeight:ConDeltailcellHeight];
                [cell addSubview:unitlabel];
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",total];
            }else if (indexPath.row == 3) {
                [cell addSubview:[self addSegmentLineToCellWithStr:@"【最终货款】"]];
            }else if (indexPath.row == 4) {
                UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_yuan cellHeight:ConDeltailcellHeight];
                [cell addSubview:unitlabel];
                cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",price*total];
            }
        }// -------------------------合同异常结束-------------------------------------------
        else if (isBuyer) {
            if (indexPath.row == 0) {
                cell.textLabel.text = [self payTimeString];
            }
            if (buyerOperatorStatus == BUYER_UNPAY_FINISH) {
                if (indexPath.row == 1) {
                    cell.textLabel.text = cell_margin_unfreez;
                }else if (indexPath.row == 2) {
                    cell.textLabel.text = cell_margin_del;
                }
            }else if (buyerOperatorStatus == SINGLECANCEL_FINISHED) {
                if (indexPath.row == 1) {
                    cell.textLabel.text = cell_margin_unfreez;
                }else if (indexPath.row == 2) {
                    cell.textLabel.text = cell_margin_del;
                }else if (indexPath.row == 3) {
                    cell.textLabel.text = cell_payment_reback;
                }
            }else if (sellerOperatorStatus == SINGLECANCEL_FINISHED) {
                if (indexPath.row == 1) {
                    cell.textLabel.text = cell_margin_unfreez;
                }else if (indexPath.row == 2) {
                    cell.textLabel.text = cell_payment_reback;
                }else if (indexPath.row == 3) {
                    cell.textLabel.text = cell_breach_money;
                }
            }else if (lifeCycle == BUYER_UNPAY_FINISH) {
                if (indexPath.row == 1) {
                    cell.textLabel.text = cell_margin_unfreez;
                }else if (indexPath.row == 2) {
                    cell.textLabel.text = cell_margin_del;
                }
            }
            if (indexPath.row) {
                UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_yuan cellHeight:ConDeltailcellHeight];
                [cell addSubview:unitlabel];
                
                [self writeDetailValue:cell];
            }
        }else if (!isBuyer) {
            if (indexPath.row == 0) {
                cell.textLabel.text = [self payTimeString];
            }
            if (buyerOperatorStatus == BUYER_UNPAY_FINISH || buyerOperatorStatus == SINGLECANCEL_FINISHED) {
                if (indexPath.row == 1) {
                    cell.textLabel.text = cell_margin_unfreez;
                }else if (indexPath.row == 2) {
                    cell.textLabel.text = cell_breach_money;
                }
            }else if (sellerOperatorStatus == SINGLECANCEL_FINISHED) {
                if (indexPath.row == 1) {
                    cell.textLabel.text = cell_margin_unfreez;
                }else if (indexPath.row == 2) {
                    cell.textLabel.text = cell_margin_del;
                }
            }else if (lifeCycle == BUYER_UNPAY_FINISH) {
                if (indexPath.row == 1) {
                    cell.textLabel.text = cell_margin_unfreez;
                }else if (indexPath.row == 2) {
                    cell.textLabel.text = cell_breach_money;
                }
            }

            if (indexPath.row) {
                UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_yuan cellHeight:ConDeltailcellHeight];
                [cell addSubview:unitlabel];
            }
            if (indexPath.row) {
                [self writeDetailValue:cell];
            }
        }
    }else if (indexPath.section == 2) {
        if (lifeCycle == ARBITRATED) {
            if (isBuyer) {
                if (indexPath.row == 0) {
                    cell.textLabel.text = [self payTimeString];
                }else if (indexPath.row == 1) {
                    cell.textLabel.text = cell_margin_unfreez;
                }else if (indexPath.row == 2) {
                    cell.textLabel.text = cell_payment_reback;
                }
            }else {
                if (indexPath.row == 0) {
                    cell.textLabel.text = [self payTimeString];
                }else if (indexPath.row == 1) {
                    cell.textLabel.text = cell_margin_unfreez;
                }else if (indexPath.row == 2) {
                    cell.textLabel.text = cell_actual_recive;
                }
            }
        }else{
            if (isBuyer) {
                if (indexPath.row == 0) {
                    cell.textLabel.text = [self payTimeString];
                }else if (indexPath.row == 1) {
                    cell.textLabel.text = cell_margin_unfreez;
                }else if (indexPath.row == 2) {
                    cell.textLabel.text = cell_payment_reback;
                }
        
            }else if (!isBuyer) {
                if (indexPath.row == 0) {
                    cell.textLabel.text = [self payTimeString];
                }else if (indexPath.row == 1) {
                    cell.textLabel.text = cell_margin_unfreez;
                }else if (indexPath.row == 2) {
                    cell.textLabel.text = cell_actual_recive;
                }else if (indexPath.row == 3) {
                    cell.textLabel.text = cell_platform_poundage;
                }
                    if (indexPath.row) {
                    [self writeDetailValue:cell];
                }
            }
        }
        if (indexPath.row) {
            UILabel *unitlabel = [UIFactory createUnitLabel:cell.textLabel.text withFont:font unitType:unint_yuan cellHeight:ConDeltailcellHeight];
            [cell addSubview:unitlabel];
        }
    }
    
    if ((lifeCycle == ARBITRATED || lifeCycle == NORMAL_FINISHED) && indexPath.section == 2 && indexPath.row) {
        [self writeDetailValue:cell];
    }
    
    return cell;
}

/**
 *@brief 返回字符串 xx时间平台对我的结算
 */
- (NSString *)payTimeString {
    FinalEstimateListModel *model = [_contractModel findleTraeModel:UNGELATION_GUARANTY];
    if (model.paytime.length) {
        return [NSString stringWithFormat:@"%@ %@",model.paytime,cell_paln_final_statement];
    }else {
        return cell_paln_final_statement;
    }
}

/**
 *@brief 不同的交易类型，显示不同的支出、收入数据
 */
- (void)writeDetailValue:(UITableViewCell *)cell {
    NSString *text = cell.textLabel.text;
    
    PurseTradeType type;
    if ([text isEqualToString:cell_margin_unfreez]) {
        type = UNGELATION_GUARANTY;
    }else if ([text isEqualToString:cell_payment_reback]) {
        type = PLATFORM_RETURN;
    }else if ([text isEqualToString:cell_breach_money]) {
        type = VIOLATION_REPARATION;
    }else if ([text isEqualToString:cell_margin_del]) {
        type = VIOLATION_DEDUCTION;
    }else if ([text isEqualToString:cell_plan_handlingCharge]) {
        type = SERVICE_CHARGE;
    }else if ([text isEqualToString:cell_actual_recive]) {
        type = PAYMENT_FOR_GOODS;
    }

    FinalEstimateListModel *model = [_contractModel findleTraeModel:type];
    NSNumber *money = model.amount;
    if (!money) {
        money = [NSNumber numberWithFloat:0.0];
    }
    if ([model.direction[DataValueKey] integerValue] == purseINPUT) {
        cell.detailTextLabel.textColor = RGB(68, 153, 0, 1);
        cell.detailTextLabel.text = [NSString stringWithFormat:@"+%.2f",[money floatValue]];
        if ([model.amount floatValue] == 0) {
            cell.detailTextLabel.text = [NSString stringWithFormat:@"%.2f",[money floatValue]];
        }
    }else {
        cell.detailTextLabel.textColor = RGB(255, 102, 0, 1);
        cell.detailTextLabel.text = [NSString stringWithFormat:@"-%.2f",[money floatValue]];
    }

}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    if (section == 0) {
        return 30;
    }
    return 0;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    UIView *headerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.vwidth, 30)];
    headerView.backgroundColor = [UIColor clearColor];
    UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
    [btn setImage:[UIImage imageNamed:@"agreement_icon_hetong"] forState:UIControlStateNormal];
    [btn setTitle:btntitle_contract_order forState:UIControlStateNormal];
    btn.titleLabel.font = [UIFont systemFontOfSize:14.f];
    [btn setTitleColor:RGB(122, 183, 104, 1) forState:UIControlStateNormal];
    btn.frame = CGRectMake(5, 0, 110, headerView.vheight);
    [headerView addSubview:btn];

    ContractLifeCycle lifeCycle = [_contractModel.lifecycle[DataValueKey] integerValue];
    if (section == 0 && lifeCycle == ARBITRATED) {
        UILabel *label = [UILabel labelWithTitle:@"    平台结果通知"];
        label.font = FontSystem(13.f);
        label.frame = CGRectMake(0, 0, self.vwidth, 20);
        return label;
    }
    if (section == 0 && ![_contractModel contractValide]) {
        return headerView;
    }
    return nil;
}

#pragma mark - UIScrollView
- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView {
    if (scrollView == _controlScrollView) {
        if (scrollView.contentOffset.x/scrollView.vwidth == 1) {
            [_productView commAction:NO];
        }else {
            [_productView commAction:YES];
        }
    }
}

@end
