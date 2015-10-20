//
//  MySupplyTableViewCell.m
//  Glshop
//
//  Created by River on 15-1-20.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "MySupplyTableViewCell.h"
#import "TPFloatRatingView.h"

@interface MySupplyTableViewCell ()

@property (strong, nonatomic) IBOutlet UIImageView *logoImageView;
@property (strong, nonatomic) IBOutlet UIImageView *myInfoImgView; // 是我发布的信息就显示相应的logo,否则隐藏
@property (strong, nonatomic) IBOutlet UIImageView *isAuthImgView; // 显示是否认证logo
@property (strong, nonatomic) IBOutlet UIImageView *isPaymentImgView; // 显示是否已缴纳保证金logo

@property (strong, nonatomic) IBOutlet UILabel *totalLabel;
@property (strong, nonatomic) IBOutlet UILabel *priceLabel;
@property (strong, nonatomic) IBOutlet UILabel *areaLabel;
@property (strong, nonatomic) IBOutlet UILabel *startTimeLabel;
@property (strong, nonatomic) IBOutlet UILabel *endTimeLabel;
@property (strong, nonatomic) IBOutlet UILabel *productLabel;
@property (strong, nonatomic) IBOutlet UIImageView *sepetor1;
@property (strong, nonatomic) IBOutlet UIImageView *sepetor2;
@property (strong, nonatomic) IBOutlet TPFloatRatingView *rateingView;

@end

@implementation MySupplyTableViewCell

- (void)awakeFromNib {
//    _sepetor1 = [[UIImageView alloc] initWithFrame:CGRectMake(15, 28, self.vwidth-15, 0.5)];
//    _sepetor1.backgroundColor = [UIColor lightGrayColor];
//    [self.contentView addSubview:_sepetor1];
//    
//    _sepetor2 = [[UIImageView alloc] initWithFrame:CGRectMake(15, 88, self.vwidth-15, 0.5)];
//    _sepetor2.backgroundColor = [UIColor lightGrayColor];
//    [self.contentView addSubview:_sepetor2];
    
    _rateingView.emptySelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star-huise"];
    _rateingView.fullSelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star_huangse"];
}

- (void)setOrderModel:(OrderModel *)orderModel {
    if (_orderModel != orderModel) {
        _orderModel = orderModel;

        float totalStr = [_orderModel.totalnum floatValue];
        NSString *str = [NSString stringWithFormat:@"%.2f吨",totalStr];
        _totalLabel.text = str;
        _startTimeLabel.text = [_orderModel.starttime substringToIndex:10];
        _endTimeLabel.text = [_orderModel.endtime substringToIndex:10];
        _priceLabel.text = [NSString stringWithFormat:@"%.2f元/吨",[_orderModel.price floatValue]];
        
        _areaLabel.text = _orderModel.areaFullName;

        _productLabel.text = [SynacObject combinProducName:_orderModel.ptype proId:_orderModel.pid];
        NSNumber *type = [_orderModel.type objectForKey:@"val"];
        if ([type integerValue] == 1) {
            _logoImageView.image = [UIImage imageNamed:@"Buy_sell_qiugou"];
//            if ([[_orderModel.status objectForKey:@"val"] integerValue] != 0) {
//                _logoImageView.image = [UIImage imageNamed:@"supply-and-demand_qiugou_huise"];
//            }
        }else {
            _logoImageView.image = [UIImage imageNamed:@"Buy_sell_chushou"];
//            if ([[_orderModel.status objectForKey:@"val"] integerValue] != 0) {
//                _logoImageView.image = [UIImage imageNamed:@"supply-and-demand_shushou_huise"];
//            }
        }
        
        UserInstance *u = [UserInstance sharedInstance];
        if ([_orderModel.isApply integerValue] == 1) {
            _myInfoImgView.hidden = NO;
            _myInfoImgView.image = [UIImage imageNamed:@"icon_bq"];
        }else {
//            _myInfoImgView.hidden = YES;
            if ([_orderModel.cid isEqualToString:u.user.cid]) {
                _myInfoImgView.hidden = NO;
                _myInfoImgView.image = [UIImage imageNamed:@"icon_biaoqian"];
            }else {
                _myInfoImgView.hidden = YES;
            }
        }
        
        if ([_orderModel.authstatus[DataValueKey] integerValue] == 1) {
            _isAuthImgView.image = [UIImage imageNamed:@"icon_renzheng"];
        }else {
            _isAuthImgView.image = [UIImage imageNamed:@"icon_rz"];
        }
        
        if ([_orderModel.bailstatus[DataValueKey] integerValue] == 1) {
            _isPaymentImgView.image = [UIImage imageNamed:@"icon_bao"];
        }else {
            _isPaymentImgView.image = [UIImage imageNamed:@"icon_b"];
        }
        
        _rateingView.rating = [_orderModel.credit floatValue];
    }
}
@end
