//
//  UIView+ShowLoading.m
//  jfsdl
//
//  Created by River on 14-11-21.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "UIView+ShowLoading.h"
#import <objc/runtime.h>

static NSString * const kXHloadingViewKey = @"kXHloadingViewKey";

@implementation UIView (ShowLoading)

- (void)setLoadingView:(LoadingView *)loadingView {
    objc_setAssociatedObject(self, &kXHloadingViewKey, loadingView, OBJC_ASSOCIATION_RETAIN);
}

- (LoadingView *)loadingView {
    return objc_getAssociatedObject(self, &kXHloadingViewKey);
}

- (void)show {
    [self showWithTip:nil];
}

- (void)showWithTip:(NSString *)tipTitle {
    [self showWithTip:tipTitle Yoffset:0];
}

- (void)showWithTip:(NSString *)tipTitle Yoffset:(CGFloat)yoffset {
    if (!self.loadingView) {
        self.loadingView = [[LoadingView alloc] initWithFrame:CGRectMake(CGRectGetWidth(self.frame)/2-60, CGRectGetHeight(self.frame)/2-10-yoffset, 120, 20)];
    }
    self.loadingView.tipTitle = tipTitle;
    [self addSubview:self.loadingView];
}

- (void)hideLoading {
    [self.loadingView removeFromSuperview];
    self.loadingView = nil;
}

@end
