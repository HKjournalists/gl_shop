//
//  ButtonWithTitleAndImage.m
//  Glshop
//
//  Created by River on 14-11-18.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "ButtonWithTitleAndImage.h"

@implementation ButtonWithTitleAndImage

- (CGRect)titleRectForContentRect:(CGRect)contentRect {
    return CGRectMake(8+self.left, 0, contentRect.size.width, contentRect.size.height);
}

- (CGRect)imageRectForContentRect:(CGRect)contentRect {
//    return CGRectMake(2*contentRect.size.width/3+5, contentRect.size.height/2-5, 15, 10);
    NSInteger height = _imgHeight ? _imgHeight : 10;
        return CGRectMake(contentRect.size.width-30, contentRect.size.height/2-height/2, 12, height);
}

@end
