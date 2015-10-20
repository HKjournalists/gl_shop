//
//  PayListTableViewCell.m
//  Glshop
//
//  Created by River on 15-1-9.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "PayListTableViewCell.h"
#import "PayListModel.h"

@interface PayListTableViewCell ()

@property (strong, nonatomic) IBOutlet UILabel *otypeLabel;

@property (strong, nonatomic) IBOutlet UILabel *createTimeLabel;
@property (strong, nonatomic) IBOutlet UILabel *balnceLabel;

@property (strong, nonatomic) IBOutlet UILabel *amountLabel;

@property (strong, nonatomic) IBOutlet UILabel *balncexLabel;
@end

@implementation PayListTableViewCell

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

- (void)setListModel:(PayListModel *)listModel {
    _listModel = listModel;
    
    _otypeLabel.text = listModel.otype[DataTextKey];
    NSString *amountStr;
    double aoumx = [listModel.amount doubleValue];
    NSString *money = [Utilits formatMoney:aoumx isUnit:false];
    
    if ([listModel.direction[DataValueKey] integerValue] == 1) { // 流出
        amountStr = [NSString stringWithFormat:@"-%@",money];
        _amountLabel.textColor = RGB(255, 102, 0, 1);
    }else {
        amountStr = [NSString stringWithFormat:@"+%@",money];
        _amountLabel.textColor = RGB(68, 153, 0, 1);
    }
    _amountLabel.text = amountStr;
    
    NSString *time;
    if (listModel.paytime.length >10) {
        time = [listModel.paytime substringToIndex:10];
    }else {
        time = listModel.paytime;
    }
    _createTimeLabel.text = listModel.paytime;
    
    double balancex = [listModel.balance doubleValue];
    NSString *balancexxx = [Utilits formatMoney:balancex isUnit:false];
    _balnceLabel.text = balancexxx;
    
    _otypeLabel.font = [UIFont boldSystemFontOfSize:FONT_16];
    _otypeLabel.textColor = RGB(51, 51, 51, 1);
    
    _amountLabel.font = [UIFont boldSystemFontOfSize:FONT_16];
    
    _createTimeLabel.font = [UIFont systemFontOfSize:FONT_12];
    _createTimeLabel.textColor = RGB(153, 153, 153, 1);
    
    _balnceLabel.font = [UIFont systemFontOfSize:FONT_12];
    _balncexLabel.font = [UIFont systemFontOfSize:FONT_12];
    _balnceLabel.textColor = RGB(51, 51, 51, 1);
    _balncexLabel.textColor = RGB(51, 51, 51, 1);
}

@end
