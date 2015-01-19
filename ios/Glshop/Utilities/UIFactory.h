//
//  UIFactory.h
//  Glshop
//
//  Created by River on 14-11-13.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger, UnitTextType) {
    /**
     *@brief 单位：元
     */
    unint_yuan,
    /**
     *@brief 单位：吨
     */
    unint_dun,
};

@interface UIFactory : NSObject

/**
 *@brief 提示视图
 */
+ (UIView *)createPromptViewframe:(CGRect)frame tipTitle:(NSString *)title;

/**
 *@brief 针对列表的单元格添加单位列表
 *@discussion 针对本项目
 */
+ (UILabel *)createUnitLabel:(NSString *)text withFont:(UIFont *)font unitType:(UnitTextType)type;

/**
 *@brief 创建通用按钮，附带背景图片
 *@param imageName 背景图片名
 *@param title 按钮title
 *@param frame 按钮坐标
 */
+ (UIButton *)createBtn:(NSString *)imageName bTitle:(NSString *)title bframe:(CGRect)frame;

@end
