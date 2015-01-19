//
//  UIViewController+RetrivalWD.m
//  Glshop
//
//  Created by River on 15-1-16.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "UIViewController+RetrivalWD.h"

@implementation UIViewController (RetrivalWD)

- (id)findDesignatedViewController:(Class)aClass {
    NSAssert(self.navigationController, @"Not In Nav");
    for (UIViewController *vc in self.navigationController.viewControllers) {
        if ([vc isKindOfClass:aClass]) {
            return vc;
        }
    }
    return nil;
}

@end
