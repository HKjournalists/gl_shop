//
//  UIImageView+Creation.m
//  PromoterClient
//
//  Created by ios on 14-7-15.
//  Copyright (c) 2014年 TCL. All rights reserved.
//

#import "UIImageView+Creation.h"

@implementation UIImageView (Creation)

+ (UIImageView *)createImageViewWithImg:(NSString *)imgName
{
    UIImageView *imgView = [[UIImageView alloc] init];
    imgView.image = [UIImage imageNamed:imgName];
    return imgView;
}

@end
