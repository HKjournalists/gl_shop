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
    if ([listModel.direction[DataValueKey] integerValue] == 1) { // 流出
        amountStr = [NSString stringWithFormat:@"-%@",listModel.amount];
        _amountLabel.textColor = RGB(255, 102, 0, 1);
    }else {
        amountStr = [NSString stringWithFormat:@"+%@",listModel.amount];
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
    _balnceLabel.text = [listModel.balance stringValue];
}

@end
