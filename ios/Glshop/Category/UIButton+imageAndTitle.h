//
//  UIButton+imageAndTitle.h
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/29.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIButton (imageAndTitle)

+ (UIButton *)buttonWith:(CGRect)frame title:(NSString *)title normalimage:(NSString *)imageName selectedImage:(NSString *)selectedImage userInteractionEnabled:(BOOL)enable edgeInset:(UIEdgeInsets)edgeInsets titleFont:(UIFont *)font;

//+ (UIButton *)buttonWith:(CGRect)frame title:(NSString *)title normalimage:(NSString *)imageName selectedImage:(NSString *)selectedImage backGroundImage:(NSString *)backGroundImage userInteractionEnabled:(BOOL)enable edgeInset:(UIEdgeInsets)edgeInsets titleFont:(UIFont *)font;
@end
