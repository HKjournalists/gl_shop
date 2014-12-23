//
//  BusinessTableViewCell.m
//  Glshop
//
//  Created by River on 14-11-20.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "BusinessTableViewCell.h"

@interface BusinessTableViewCell ()

@property (strong, nonatomic) IBOutlet UIImageView *logoImageView;
@property (strong, nonatomic) IBOutlet UILabel *totalLabel;
@property (strong, nonatomic) IBOutlet UILabel *priceLabel;
@property (strong, nonatomic) IBOutlet UILabel *areaLabel;
@property (strong, nonatomic) IBOutlet UILabel *startTimeLabel;
@property (strong, nonatomic) IBOutlet UILabel *endTimeLabel;
@property (strong, nonatomic) IBOutlet UILabel *productLabel;
@property (strong, nonatomic) IBOutlet UIImageView *sepetor;


@end

@implementation BusinessTableViewCell

- (void)awakeFromNib {
    _sepetor = [[UIImageView alloc] initWithFrame:CGRectMake(15, 25, self.vwidth-15, 0.5)];
    _sepetor.backgroundColor = [UIColor lightGrayColor];
    [self.contentView addSubview:_sepetor];
}

- (void)setOrderModel:(OrderModel *)orderModel {
    _orderModel = orderModel;
    
    _totalLabel.text = [_orderModel.totalnum stringValue];
    _startTimeLabel.text = [_orderModel.starttime substringToIndex:10];
    _endTimeLabel.text = [_orderModel.limitime substringToIndex:10];
    _priceLabel.text = [NSString stringWithFormat:@"%.2f",[_orderModel.price floatValue]];
    
    SynacInstance *synac = [SynacInstance sharedInstance];
    NSString *areaName = [synac codeMappingName:YES targetStr:_orderModel.area];
    _areaLabel.text = areaName;
    _productLabel.text = [NSString stringWithFormat:@"求购%@",_orderModel.pname];
    NSNumber *type = [_orderModel.type objectForKey:@"val"];
    if ([type integerValue] == 1) {
        _logoImageView.image = [UIImage imageNamed:@"Buy_sell_icon_buy"];
    }else {
        _logoImageView.image = [UIImage imageNamed:@"Buy_sell_icon_sell"];
    }
}

- (void)layoutSubviews {
    [super layoutSubviews];
    
}

@end
