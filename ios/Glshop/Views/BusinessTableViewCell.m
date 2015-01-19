//
//  BusinessTableViewCell.m
//  Glshop
//
//  Created by River on 14-11-20.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "BusinessTableViewCell.h"
#import <CoreText/CoreText.h>

@interface BusinessTableViewCell ()

@property (strong, nonatomic) IBOutlet UIImageView *logoImageView;
@property (strong, nonatomic) IBOutlet UILabel *totalLabel;
@property (strong, nonatomic) IBOutlet UILabel *priceLabel;
@property (strong, nonatomic) IBOutlet UILabel *areaLabel;
@property (strong, nonatomic) IBOutlet UILabel *startTimeLabel;
@property (strong, nonatomic) IBOutlet UILabel *endTimeLabel;
@property (strong, nonatomic) IBOutlet UILabel *productLabel;
@property (strong, nonatomic) IBOutlet UIImageView *sepetor1;
@property (strong, nonatomic) IBOutlet UIImageView *sepetor2;
@property (strong, nonatomic) IBOutlet UILabel *statusLabel;



@end

@implementation BusinessTableViewCell

- (void)awakeFromNib {
    _sepetor1 = [[UIImageView alloc] initWithFrame:CGRectMake(15, 28, self.vwidth-15, 0.5)];
    _sepetor1.backgroundColor = [UIColor lightGrayColor];
    [self.contentView addSubview:_sepetor1];
    
    _sepetor2 = [[UIImageView alloc] initWithFrame:CGRectMake(15, 88, self.vwidth-15, 0.5)];
    _sepetor2.backgroundColor = [UIColor lightGrayColor];
    [self.contentView addSubview:_sepetor2];
}

- (void)setOrderModel:(OrderModel *)orderModel {
    _orderModel = orderModel;
    
    NSString *totalStr = [_orderModel.totalnum stringValue];
    NSString *str = [NSString stringWithFormat:@"%@吨",totalStr];
    _totalLabel.text = str;
    _startTimeLabel.text = [_orderModel.starttime substringToIndex:10];
    _endTimeLabel.text = [_orderModel.endtime substringToIndex:10];
    _priceLabel.text = [NSString stringWithFormat:@"%.2f元/吨",[_orderModel.price floatValue]];
    
    _areaLabel.text = _orderModel.areaFullName;
    NSString *proTitle;
    if (_orderModel.ptype.length) {
        GoodsModel *model = [SynacObject goodsModelForPtype:_orderModel.ptype];
        GoodChildModel *child = [SynacObject goodsChildModlelFor:_orderModel.ptype deepId:_orderModel.pid];
        NSString *size = [NSString stringWithFormat:@"%@(%@-%@)",child.sizeModel.name,child.sizeModel.minv,child.sizeModel.maxv];
        proTitle = [NSString stringWithFormat:@"黄砂.%@.%@mm",model.goodsName,size];
    }else {
        GoodChildModel *child = [SynacObject goodsChildStone:_orderModel.pid];
        NSString *size = [NSString stringWithFormat:@"%@(%@-%@)",child.sizeModel.name,child.sizeModel.minv,child.sizeModel.maxv];
        proTitle = [NSString stringWithFormat:@"石子.%@mm",size];
    }
    _productLabel.text = proTitle;
    NSNumber *type = [_orderModel.type objectForKey:@"val"];
    if ([type integerValue] == 1) {
        _logoImageView.image = [UIImage imageNamed:@"supply-and-demand_icon_qiugou"];
        if ([[_orderModel.status objectForKey:@"val"] integerValue] != 0) {
        _logoImageView.image = [UIImage imageNamed:@"supply-and-demand_qiugou_huise"];
        }
    }else {
        _logoImageView.image = [UIImage imageNamed:@"supply-and-demand_icon_shushou"];
        if ([[_orderModel.status objectForKey:@"val"] integerValue] != 0) {
            _logoImageView.image = [UIImage imageNamed:@"supply-and-demand_shushou_huise"];
        }
    }
    
    if ([[_orderModel.status objectForKey:@"val"] integerValue] == 0) {
        _statusLabel.text = @"有效";
    }else {
        _statusLabel.text = @"无效";
    }
}

- (void)layoutSubviews {
    [super layoutSubviews];
    
}

@end
