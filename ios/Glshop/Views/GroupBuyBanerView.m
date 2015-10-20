//
//  GroupBuyBanerView.m
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/28.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "GroupBuyBanerView.h"
#import "UILabel+adjustText.h"

@interface GroupBuyBanerView ()
@property (nonatomic,strong)UILabel *textLabel1;
@property (nonatomic,strong)UILabel *textLabel2;
@end
@implementation GroupBuyBanerView

- (instancetype)initWithFrame:(CGRect)frame image:(NSString *)imageName title:(NSString *)title
{
    self = [super initWithFrame:frame image:imageName title:title];
    if (self) {
        self.titleLabel.frame = CGRectMake(10, 0, frame.size.width-10, 34.5);
    
    }
    return self;
}

- (void)loadSubViews
{
    _imageView = [[ImagePlayerView alloc] initWithFrame:CGRectMake(10, 34.5, 143, 171)];
    _imageView.scrollInterval = 4.0f;
    _imageView.pageControlPosition = ICPageControlPosition_BottomCenter;
    _imageView.hidePageControl = NO;
    [self addSubview:_imageView];
    
    
    CGSize text1Size = CGSizeMake(CGRectGetWidth(self.frame)-CGRectGetWidth(_imageView.frame)-10-20,2000);
    CGPoint label1Point = CGPointMake(CGRectGetMaxX(_imageView.frame)+10, CGRectGetMinY(_imageView.frame));
    _textLabel1 = [UILabel createLabelWith:text1Size text:description_sand1 textColor:GROUPBUYWHITECOLOR textFont:GROUPBUYTEXTFONT point:label1Point];
    _textLabel1.numberOfLines = 0;
    [self addSubview:_textLabel1];
    
    CGSize text2Size = CGSizeMake(CGRectGetWidth(_textLabel1.frame), 2000);
    CGPoint label2Point = CGPointMake(CGRectGetMinX(_textLabel1.frame), CGRectGetMaxY(_textLabel1.frame));
    _textLabel2 = [UILabel createLabelWith:text2Size text:description_sand2 textColor:_textLabel1.textColor textFont:_textLabel1.font point:label2Point];
    _textLabel2.numberOfLines = 0;
    [self addSubview:_textLabel2];
    
}

    



@end
