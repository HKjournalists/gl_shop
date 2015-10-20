//
//  UIViewController+RetrivalWD.h
//  Glshop
//
//  Created by River on 15-1-16.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIViewController (RetrivalWD)

/**
 *@brief 查找导航控制器堆栈中，指定的视图控制器
 */
- (id)findDesignatedViewController:(Class)aClass;

@end
