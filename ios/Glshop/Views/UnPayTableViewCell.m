//
//  UnPayTableViewCell.m
//  Glshop
//
//  Created by River on 15-3-2.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "UnPayTableViewCell.h"
#import "ContractModel.h"

@interface UnPayTableViewCell ()

@property (strong, nonatomic) IBOutlet UILabel *timeLabel;
@property (strong, nonatomic) IBOutlet UIImageView *logoImgView;
@property (strong, nonatomic) IBOutlet UILabel *productNameLabel;
@property (strong, nonatomic) IBOutlet UILabel *totalLabel;
@property (strong, nonatomic) IBOutlet UILabel *priceLabel;
@property (strong, nonatomic) IBOutlet UILabel *payLabel;

@end

@implementation UnPayTableViewCell

- (void)awakeFromNib {
    // Initialization code
}

- (void)setContractModel:(ContractModel *)contractModel {
    if (_contractModel != contractModel) {
        _contractModel = contractModel;
        
        // 总量
        _totalLabel.text = [NSString stringWithFormat:@"%@%@",contractModel.totalnum ,unit_tun];
        // 单价
        _priceLabel.text = [NSString stringWithFormat:@"%@%@",contractModel.price ,unit_per_price_tun];
        // 产品名称
        _productNameLabel.text = [SynacObject combinProducName:contractModel.productType proId:contractModel.productId];
        _timeLabel.text = [Utilits timeDHMGap:contractModel.payGoodsLimitTime];
        
        float pay = [contractModel.totalamount floatValue];
        _payLabel.text = [NSString stringWithFormat:@"%.2f元",pay];
        
        BOOL isBuyer = [contractModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
        _logoImgView.image = isBuyer ? [UIImage imageNamed:@"Buy_sell_qiugou"] : [UIImage imageNamed:@"Buy_sell_chushou"];
        
    }
    
}


@end
