//
//  ItmeCell.m
//  Glshop
//
//  Created by River on 14-11-6.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "ItmeCell.h"

@implementation ItmeCell

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = [UIColor whiteColor];
        
        _priceLabel = [UILabel labelWithTitle:@"89.9"];
        _priceLabel.textColor = [UIColor orangeColor];
        _priceLabel.font = [UIFont boldSystemFontOfSize:18.f];
        _priceLabel.textAlignment = NSTextAlignmentCenter;
        _priceLabel.frame = CGRectMake(0, 0, self.vwidth, 30);
        [self.contentView addSubview:_priceLabel];
        
        _productNameLabel = [UILabel labelWithTitle:@"粗沙"];
        _productNameLabel.font = [UIFont boldSystemFontOfSize:15.f];
        _productNameLabel.textAlignment = NSTextAlignmentCenter;
        _productNameLabel.frame = CGRectMake(0, _priceLabel.vbottom+3, self.vwidth, 20);
        [self.contentView addSubview:_productNameLabel];
        

        _subProductNameLabel = [UILabel labelWithTitle:@"error"];
        _subProductNameLabel.font = [UIFont systemFontOfSize:11.f];
        _subProductNameLabel.textAlignment = NSTextAlignmentCenter;
        _subProductNameLabel.textColor = [UIColor grayColor];
        _subProductNameLabel.frame = CGRectMake(0, _productNameLabel.vbottom+3, self.vwidth, 20);
        [self.contentView addSubview:_subProductNameLabel];
        
        UIImageView *sView = [[UIImageView alloc] initWithFrame:CGRectZero];
//        UIImage *image = [[UIImage imageNamed:@"tapAffect"] resizableImageWithCapInsets:UIEdgeInsetsMake(5, 5, 5, 5) resizingMode:UIImageResizingModeTile];
//        UIColor *color = [UIColor colorWithPatternImage:image];
        sView.backgroundColor = RGB(255, 244, 236, 1);
        self.selectedBackgroundView = sView;
        
    }
    return self;
}

- (void)setTodayModel:(ProductTodayModel *)todayModel {
    _todayModel = todayModel;
    
    _priceLabel.text = [NSString stringWithFormat:@"%.2f",[todayModel.todayPrice floatValue]];
    
    NSString *subProNameStr;
    if (_todayModel.ptype.length > 0) { // 黄砂
        
        GoodsModel *gmodel = [[SynacInstance sharedInstance] goodsModelForPtype:_todayModel.ptype];
        _productNameLabel.text = gmodel.goodsName;
        
        GoodChildModel *model = [[SynacInstance sharedInstance] goodsChildModlelFor:_todayModel.ptype deepId:_todayModel.pid];
        subProNameStr = [model productUnit];
    }else { // 石子
        GoodChildModel *model = [[SynacInstance sharedInstance] goodsChildStone:_todayModel.pid];
        subProNameStr = model.productUnit;
        _productNameLabel.text = todayModel.pname;
    }
    _subProductNameLabel.text = subProNameStr;
    
    

}

@end
