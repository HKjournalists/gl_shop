//
//  UITextField+Common.h
//  TCLSales
//
//  Created by 陈耀武 on 14-1-17.
//  Copyright (c) 2014年 Insigma. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UITextField (Common)

+ (instancetype)textFieldWithPlaceHodler:(NSString *)placeHodler withDelegate:(id)delegate ;
+ (instancetype)textFieldWithPlaceHodler:(NSString *)placeHodler leftView:(NSString *)tip withDelegate:(id)delegate;
+ (instancetype)textFieldWithImageName:(NSString *)image placeHodler:(NSString *)placeHodler withDelegate:(id)delegate;


@end
