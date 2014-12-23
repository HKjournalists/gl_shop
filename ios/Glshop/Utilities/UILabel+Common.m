//
//  UILabel+UILabel_Common.m
//  TCLSales
//
//  Created by ken liu on 14-1-18.
//  Copyright (c) 2014年 Insigma. All rights reserved.
//

#import "UILabel+Common.h"

@implementation UILabel (Common)

+ (instancetype)label
{
    UILabel *label = [[UILabel alloc] init];
    label.textAlignment = NSTextAlignmentLeft;
    label.backgroundColor = [UIColor clearColor];
    label.textColor = [UIColor blackColor];
    return label;
}

+ (instancetype)labelWithTitle:(NSString *)title
{
    UILabel *label = [UILabel label];
    
    label.text = title;
    return label;
}

@end
