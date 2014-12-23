//
//  UIFactory.h
//  Glshop
//
//  Created by River on 14-11-13.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface UIFactory : NSObject

/**
 *@brief 提示视图
 */
+ (UIView *)createPromptViewframe:(CGRect)frame tipTitle:(NSString *)title;

@end
