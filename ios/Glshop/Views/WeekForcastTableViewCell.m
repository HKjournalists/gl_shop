//
//  WeekForcastTableViewCell.m
//  Glshop
//
//  Created by River on 14-11-24.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "WeekForcastTableViewCell.h"
#import "ProductWeekModel.h"

@implementation WeekForcastTableViewCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        
        _productNameLabel = [UILabel labelWithTitle:@"黄砂0"];
        _productNameLabel.frame = CGRectMake(10, 0, (SCREEN_WIDTH-20)/4, self.contentView.vheight);
        _productNameLabel.font = [UIFont systemFontOfSize:13.f];
        [self.contentView addSubview:_productNameLabel];
        
        float gap = -20;
        if (iPhone6) {
            gap = -30;
        }else if (iPhone6plus) {
            gap = -40;
        }
        _todayPriceLabel = [UILabel labelWithTitle:@"33.3元/吨"];
        _todayPriceLabel.frame = CGRectMake(_productNameLabel.vright+gap, 0, _productNameLabel.vwidth, self.contentView.vheight);
        _todayPriceLabel.font = [UIFont systemFontOfSize:13.f];
        _todayPriceLabel.textAlignment = NSTextAlignmentCenter;
        [self.contentView addSubview:_todayPriceLabel];
        
        _oneWeekLabel = [UILabel labelWithTitle:@"36.0元"];
        _oneWeekLabel.frame = CGRectMake(_todayPriceLabel.vright, 0, _productNameLabel.vwidth, self.contentView.vheight);
        _oneWeekLabel.font = [UIFont systemFontOfSize:13.f];
        _oneWeekLabel.textAlignment = NSTextAlignmentCenter;
        [self.contentView addSubview:_oneWeekLabel];
        _indicteOne = [[UIImageView alloc] initWithFrame:CGRectMake(_oneWeekLabel.vright-10, self.contentView.vheight/2-6, 12, 12)];
        _indicteOne.image = [UIImage imageNamed:@"index_icon_arrow_on"];
        [self.contentView addSubview:_indicteOne];
        
        _twoWeekLabel = [UILabel labelWithTitle:@"722元"];
        _twoWeekLabel.font = [UIFont systemFontOfSize:13.f];
        _twoWeekLabel.frame = CGRectMake(_oneWeekLabel.vright, 0, _productNameLabel.vwidth, self.contentView.vheight);
        _twoWeekLabel.textAlignment = NSTextAlignmentCenter;
            _twoWeekLabel.vleft += 15;

        [self.contentView addSubview:_twoWeekLabel];
        _indicteTwo = [[UIImageView alloc] initWithFrame:CGRectMake(_twoWeekLabel.vright-10, self.contentView.vheight/2-6, 12, 12)];
        _indicteTwo.image = [UIImage imageNamed:@"index_icon_arrow_on"];
        [self.contentView addSubview:_indicteTwo];
    }
    return self;
}

- (void)awakeFromNib {
    // Initialization code
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated {
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

- (void)setWeekModel:(ProductWeekModel *)weekModel {
    _weekModel = weekModel;
    
    _productNameLabel.text = _weekModel.pname;
    _todayPriceLabel.text = [_weekModel.todayPrice stringValue];
    _oneWeekLabel.text = [_weekModel.basePrice1 stringValue];
    _twoWeekLabel.text = [_weekModel.basePrice2 stringValue];
    
    NSString *image1Name = [_weekModel.basePrice1 floatValue]/[_weekModel.todayPrice floatValue] > 1 ? @"index_icon_arrow_on" : @"index_icon_arrow_down_hongse";
    _indicteOne.image = [UIImage imageNamed:image1Name];
    
    NSString *image2Name = [_weekModel.basePrice2 floatValue]/[_weekModel.todayPrice floatValue] > 1 ? @"index_icon_arrow_on" : @"index_icon_arrow_down_hongse";
    _indicteTwo.image = [UIImage imageNamed:image2Name];
}

@end
