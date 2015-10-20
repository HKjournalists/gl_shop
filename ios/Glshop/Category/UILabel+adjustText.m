//
//  UILabel+adjustText.m
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/29.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "UILabel+adjustText.h"

@implementation UILabel (adjustText)

+(UILabel *)createLabelWith:(CGSize)textSize text:(NSString *)text textColor:(UIColor *)textColor textFont:(UIFont *)textFont point:(CGPoint)textPoint
{
    UILabel *label = [[self alloc] init];
    label.text = text;
    label.textColor = textColor;
    label.font = textFont;
    label.frame = CGRectMake(textPoint.x, textPoint.y,[text boundingRectWithSize:textSize options:NSStringDrawingUsesLineFragmentOrigin attributes:[NSDictionary dictionaryWithObjectsAndKeys:label.font,NSFontAttributeName,label.textColor,NSForegroundColorAttributeName, nil] context:nil].size.width,[text boundingRectWithSize:textSize options:NSStringDrawingUsesLineFragmentOrigin attributes:[NSDictionary dictionaryWithObjectsAndKeys:label.font,NSFontAttributeName,label.textColor,NSForegroundColorAttributeName, nil] context:nil].size.height);
    
   
    return label;
    
}

+(UILabel *)createLabelWith:(CGSize)textSize text:(NSString *)text textColor:(UIColor *)textColor backGroundColor:(UIColor *)color textFont:(UIFont *)textFont point:(CGPoint)textPoint
{
    UILabel *label = [self createLabelWith:textSize text:text textColor:textColor textFont:textFont point:textPoint];
    
    label.backgroundColor = color;
    
    return label;
}

@end
