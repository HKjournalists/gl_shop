//
//  MainItemView.m
//  Glshop
//
//  Created by River on 14-11-18.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "MainItemView.h"

@implementation MainItemView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.titleLabel.textAlignment = NSTextAlignmentCenter;
        self.titleLabel.font = [UIFont systemFontOfSize:19];
        self.titleLabel.numberOfLines = 0;
        
        self.imageView.contentMode = UIViewContentModeScaleAspectFit;
        self.adjustsImageWhenHighlighted = NO;
    }
    return self;
}

//重写title的rect
- (CGRect)titleRectForContentRect:(CGRect)contentRect
{
    CGFloat titleW;
    if (self.imageView.image == nil) {
        titleW = contentRect.size.width;
    } else {
        titleW = contentRect.size.width * (1 - 0.1);
    }
    return CGRectMake(0, contentRect.size.height/2, contentRect.size.width, contentRect.size.height/2);
}

//重写image的rect
- (CGRect)imageRectForContentRect:(CGRect)contentRect
{
    return CGRectMake(0, 0, contentRect.size.width, contentRect.size.height/2);
}

@end
