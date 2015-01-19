//
//  WeekForcastTableViewCell.h
//  Glshop
//
//  Created by River on 14-11-24.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

@class ProductWeekModel;
@interface WeekForcastTableViewCell : UITableViewCell
{
@private
    UILabel *_productNameLabel;
    UILabel *_sizeLabel;
    UILabel *_todayPriceLabel;
    UILabel *_oneWeekLabel;
    UILabel *_twoWeekLabel;
    UIImageView *_indicteOne;
    UIImageView *_indicteTwo;
}

@property (nonatomic, strong) ProductWeekModel *weekModel;

@end
