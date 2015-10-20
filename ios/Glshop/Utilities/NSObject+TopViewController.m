//
//  NSObject+TopViewController.m
//  Glshop
//
//  Created by River on 15-3-21.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "NSObject+TopViewController.h"

@implementation NSObject (TopViewController)

- (UIViewController *)deepestPresentedViewControllerOf:(UIViewController *)viewController
{
    if (viewController.presentedViewController) {
        return [self deepestPresentedViewControllerOf:viewController.presentedViewController];
    } else {
        return viewController;
    }
}

- (UIViewController *)topViewController
{
    UIViewController *rootViewController = [[[UIApplication sharedApplication] keyWindow] rootViewController];
    UIViewController *deepestPresentedViewController = [self deepestPresentedViewControllerOf:rootViewController];
    if ([deepestPresentedViewController isKindOfClass:[UINavigationController class]]) {
        return ((UINavigationController *)deepestPresentedViewController).topViewController;
    } else {
        return deepestPresentedViewController;
    }
}

@end
