//
//  NSMutableArray+SafeAddObject.h
//  Glshop
//
//  Created by River on 15-1-15.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSMutableArray (SafeAddObject)

- (void)addSafeObject:(id)obj;

@end
