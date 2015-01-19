//
//  NSArray+SafeRetriveal.m
//  Glshop
//
//  Created by River on 15-1-16.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "NSArray+SafeRetriveal.h"

@implementation NSArray (SafeRetriveal)

- (id)safeObjAtIndex:(NSInteger)index {
    if (index < 0 || index >= self.count) {
        DLog(@"数组越界，请检查！");
        return nil;
    }else {
        return [self objectAtIndex:index];
    }
}

@end
