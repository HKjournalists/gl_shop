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
        
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        
        _productNameLabel = [UILabel labelWithTitle:@"黄砂0"];
        _productNameLabel.frame = CGRectMake(8, 3, (SCREEN_WIDTH-20)/4, 22-5/2);
        _productNameLabel.font = [UIFont systemFontOfSize:13.f];
        [self.contentView addSubview:_productNameLabel];
        
        _sizeLabel = [UILabel labelWithTitle:@"(1.1-1.8)"];
        _sizeLabel.frame = CGRectMake(2, _productNameLabel.vbottom-3, _productNameLabel.vwidth, 20);
        _sizeLabel.font = _productNameLabel.font;
        [self.contentView addSubview:_sizeLabel];
        
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
    _todayPriceLabel.text = [NSString stringWithFormat:@"%.2f",[_weekModel.todayPrice floatValue]];
    _oneWeekLabel.text = [NSString stringWithFormat:@"%.2f",[_weekModel.basePrice1 floatValue]];
    _twoWeekLabel.text = [NSString stringWithFormat:@"%.2f",[_weekModel.basePrice2 floatValue]];
    
    float oneFloat = [_weekModel.basePrice1 floatValue]/[_weekModel.todayPrice floatValue];
    float twoFloat = [_weekModel.basePrice2 floatValue]/[_weekModel.todayPrice floatValue];
    
    NSString *image1Name = oneFloat > 1 ? @"index_icon_arrow_on" : @"index_icon_arrow_down_hongse";
    _indicteOne.image = [UIImage imageNamed:image1Name];
    
    NSString *image2Name = twoFloat > 1 ? @"index_icon_arrow_on" : @"index_icon_arrow_down_hongse";
    _indicteTwo.image = [UIImage imageNamed:image2Name];

    if (oneFloat == 1 || isnan(oneFloat)) {
        _indicteOne.image = nil;
    }
    
    if (twoFloat == 1 || isnan(twoFloat)) {
        _indicteTwo.image = nil;
    }
    
    if ([_weekModel.basePrice1 floatValue]/[_weekModel.todayPrice floatValue] > 1) {
        _oneWeekLabel.textColor = ColorWithHex(@"36a830");
    }else if ([_weekModel.basePrice1 floatValue]/[_weekModel.todayPrice floatValue] < 1){
        _oneWeekLabel.textColor = ColorWithHex(@"f10000");
    }else {
        _oneWeekLabel.textColor = [UIColor blackColor];
    }
    
    if ([_weekModel.basePrice2 floatValue]/[_weekModel.todayPrice floatValue] < 1 ) {
        _twoWeekLabel.textColor = ColorWithHex(@"f10000");
    }else if ([_weekModel.basePrice2 floatValue]/[_weekModel.todayPrice floatValue] > 1){
        _twoWeekLabel.textColor = ColorWithHex(@"36a830");
    }else {
        _twoWeekLabel.textColor = [UIColor blackColor];
    }
    
    NSString *subProNameStr;
    if (_weekModel.ptype.length > 0) { // 黄砂
        
        GoodChildModel *model = [[SynacInstance sharedInstance] goodsChildModlelFor:_weekModel.ptype deepId:_weekModel.pid];
        subProNameStr = [NSString stringWithFormat:@"(%@-%@)",model.sizeModel.minv,model.sizeModel.maxv];
    }else { // 石子
        GoodChildModel *model = [[SynacInstance sharedInstance] goodsChildStone:_weekModel.pid];
        subProNameStr = [NSString stringWithFormat:@"(%@-%@)",model.sizeModel.minv,model.sizeModel.maxv];
    }
    
    _sizeLabel.text = subProNameStr;
}

@end
