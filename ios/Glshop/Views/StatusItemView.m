//
//  StatusItemView.m
//  Glshop
//
//  Created by River on 15-2-10.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "StatusItemView.h"

static float arrowWidth = 8;
static float labelHeightGap = 5;

@implementation StatusItemView

- (instancetype)initWithArrowDirction:(ItemArrowDirction)driction
{
    self = [super init];
    if (self) {
        self.backgroundColor = [UIColor clearColor];
        
        _dirction = driction;
        
        _backgroundView = [[UIImageView alloc] init];
        [self addSubview:_backgroundView];
        
        _textFont = [UIFont systemFontOfSize:13.f];
        
        _textLabel = [[UILabel alloc] init];
        _textLabel.font = _textFont;
        _textLabel.textColor = [UIColor darkGrayColor];
        _textLabel.textAlignment = NSTextAlignmentCenter;
        [self addSubview:_textLabel];
    }
    return self;
}

- (void)setItemStyle:(StatusItmeViewStyle)itemStyle {
    NSString *imgName;
    
    switch (itemStyle) {
        case statusProcessingStyle:
        {
            if (_isMe) {
//                imgName = _dirction == arrowRight ? @"agreementGreen":@"agreement_kuangkuang_zuo";
                imgName = _dirction == arrowRight ? @"orangeArrowLeft":@"orangeArrowRight";
            }else {
                imgName = _dirction == arrowRight ? @"agreementGreen":@"agreement_kuangkuang_zuo";
            }
        }
            break;
        case statusProcessOverStyle:
        {
            imgName = _dirction == arrowRight ? @"agreement_kuangkuang":@"agreement_baisekuangkuang_zuo";
        }
            break;
        case statusWaitProcessStyle:
        {
            imgName = _dirction == arrowRight ? @"agreement_xuxian_xiaoyou":@"agreement_xuxian_xiaozuo";
            if (self.textArray.count > 1) {
                imgName = _dirction == arrowRight ? @"agreement_xuxian_dayou":@"agreement_xuxian_dazuo";
            }
        }
            break;
        default:
            break;
    }
    
    UIImage *image = [UIImage imageNamed:imgName];
    if (itemStyle != statusWaitProcessStyle) {
        image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(2, 10, 4, 20) resizingMode:UIImageResizingModeStretch];
    }
    _backgroundView.image = image;
    
}

- (void)setTextArray:(NSArray *)textArray {
    if (_textArray != textArray) {
        _textArray = textArray;
        
        _textLabel.text = textArray.firstObject;
        if (_textArray.count == 2) {
            _assistLabel = [[UILabel alloc] init];
            _assistLabel.font = _textFont;
            _assistLabel.textColor = [UIColor darkGrayColor];
            _assistLabel.textAlignment = NSTextAlignmentCenter;
            _assistLabel.text = _textArray.lastObject;
            [self addSubview:_assistLabel];
        }
    }
}

- (void)layoutSubviews {
    [super layoutSubviews];
    
    _backgroundView.frame = self.bounds;
    
    float orgintX = _dirction == arrowRight ? 0 : arrowWidth;
    float orginY = _textArray.count > 1 ? labelHeightGap :CGRectGetHeight(self.bounds)/2-15.0/2;
    _textLabel.frame = CGRectMake(orgintX, orginY, self.vwidth-arrowWidth, 15);
    
    if (_assistLabel) {
        _assistLabel.frame = CGRectMake(orgintX, _textLabel.vbottom, _textLabel.vwidth, _textLabel.vheight);
    }
}

@end
