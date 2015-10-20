//
//  UIButton+imageAndTitle.m
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/29.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "UIButton+imageAndTitle.h"

@implementation UIButton (imageAndTitle)

+ (UIButton *)buttonWith:(CGRect)frame title:(NSString *)title normalimage:(NSString *)imageName selectedImage:(NSString *)selectedImage userInteractionEnabled:(BOOL)enable edgeInset:(UIEdgeInsets)edgeInsets titleFont:(UIFont *)font
{
    UIButton *button = [self buttonWithType:UIButtonTypeCustom];
    
    button.frame = frame;
    button.userInteractionEnabled = enable;
    [button setTitle:title forState:UIControlStateNormal];
    [button setImage:[UIImage imageNamed:imageName] forState:UIControlStateNormal];
    [button setImage:[UIImage imageNamed:selectedImage] forState:UIControlStateSelected];
    button.titleLabel.font = font;
    [button setTitleEdgeInsets:edgeInsets];

    return button;
}
//+ (UIButton *)buttonWith:(CGRect)frame title:(NSString *)title normalimage:(NSString *)imageName selectedImage:(NSString *)selectedImage backGroundImage:(NSString *)backGroundImage userInteractionEnabled:(BOOL)enable edgeInset:(UIEdgeInsets)edgeInsets titleFont:(UIFont *)font
//{
//    UIButton *button = [self buttonWith:frame title:title normalimage:imageName selectedImage:selectedImage userInteractionEnabled:enable edgeInset:edgeInsets titleFont:font];
//    
//    [button setBackgroundImage:[UIImage imageNamed:backGroundImage] forState:UIControlStateHighlighted];
//    return button;
//}
@end
