//
//  NSMutableArray+SafeAddObject.m
//  Glshop
//
//  Created by River on 15-1-15.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "NSMutableArray+SafeAddObject.h"

@implementation NSMutableArray (SafeAddObject)

- (void)addSafeObject:(id)obj {
    if (obj == nil) {
        DLog(@"数组添加了nil对象,请检查");
        [self addObject:[[NSObject alloc] init]];
    }else {
        [self addObject:obj];
    }
}

@end
