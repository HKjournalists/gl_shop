//
//  UIButton+Common.h
//  TCLSales
//
//  Created by me on 14-1-17.
//  Copyright (c) 2014年 Insigma. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIButton (Common)

+ (instancetype)buttonWithTip:(NSString *)tip target:(id)target selector:(SEL)selector;

@end
