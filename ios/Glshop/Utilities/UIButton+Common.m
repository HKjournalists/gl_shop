//
//  UIButton+Common.m
//  TCLSales
//
//  Created by me on 14-1-17.
//  Copyright (c) 2014年 Insigma. All rights reserved.
//

#import "UIButton+Common.h"

@implementation UIButton (Common)

+ (instancetype)buttonWithTip:(NSString *)tip target:(id)target selector:(SEL)selector
{
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    [button setTitle:tip forState:UIControlStateNormal];
    [button addTarget:target action:selector forControlEvents:UIControlEventTouchUpInside];
    return button;
}

@end
