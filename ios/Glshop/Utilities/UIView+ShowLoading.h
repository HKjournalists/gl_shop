//
//  UIView+ShowLoading.h
//  jfsdl
//
//  Created by River on 14-11-21.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "LoadingView.h"

@interface UIView (ShowLoading)

@property (nonatomic, strong) LoadingView *loadingView;

- (void)show;
- (void)showWithTip:(NSString *)tipTitle;
- (void)hideLoading;
- (void)showWithTip:(NSString *)tipTitle Yoffset:(CGFloat)yoffset;
- (BOOL)isShowLoading;

@end
