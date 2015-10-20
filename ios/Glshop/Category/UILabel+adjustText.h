//
//  UILabel+adjustText.h
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/29.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UILabel (adjustText)

+(UILabel *)createLabelWith:(CGSize)textSize text:(NSString *)text textColor:(UIColor *)textColor textFont:(UIFont *)textFont point:(CGPoint)textPoint;
+(UILabel *)createLabelWith:(CGSize)textSize text:(NSString *)text textColor:(UIColor *)textColor backGroundColor:(UIColor *)color textFont:(UIFont *)textFont point:(CGPoint)textPoint;
@end
