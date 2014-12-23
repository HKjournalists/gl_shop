//
//  UIView+HHFirstResponer.m
//  Glshop
//
//  Created by River on 14-11-10.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "UIView+HHFirstResponer.h"

@implementation UIView (HHFirstResponer)

-(UIView*) findFirstResponder {
    
    if (self.isFirstResponder) return self;
    for (UIView *subView in self.subviews) {
        UIView *firstResponder = [subView findFirstResponder];
        if (firstResponder != nil) return firstResponder;
    }
    return nil;
    
}

@end
