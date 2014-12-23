//
//  UIView+Additions.m
//  GLIOS
//
//  Created by Mctu on 13-8-7.
//  Copyright (c) 2013年 Mctu. All rights reserved.
//

#import "UIView+Additions.h"

@implementation UIView (Additions)

- (UIViewController *)viewController
{
    UIResponder *next = [self nextResponder];
    do {
        if ([next isKindOfClass:[UIViewController class]]) {
            return (UIViewController *)next;
        }
        next = [next nextResponder];
    } while (next != nil);
    return nil;
}

- (UITableView *)tableView
{
    UIResponder *next = [self nextResponder];
    do {
        if ([next isKindOfClass:[UITableView class]]) {
            return (UITableView *)next;
        }
        next = [next nextResponder];
    } while (next != nil);
    return nil;
}

- (UITableViewCell *)tableViewcell
{
    UIResponder *next = [self nextResponder];
    do {
        if ([next isKindOfClass:[UITableViewCell class]]) {
            return (UITableViewCell *)next;
        }
        next = [next nextResponder];
    } while (next != nil);
    return nil;
}

@end
