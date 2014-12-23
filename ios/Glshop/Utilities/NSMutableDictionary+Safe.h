//
//  NSMutableDictionary+Safe.h
//  TCLSales
//
//  Created by River on 14-12-1.
//  Copyright (c) 2014年 Insigma. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSMutableDictionary (Safe)

- (void)addString:(NSString *)aValue forKey:(id <NSCopying>)aKey;

- (void)addInteger:(NSInteger)aValue forKey:(id <NSCopying>)aKey;

- (void)addCGFloat:(CGFloat)aValue forKey:(id <NSCopying>)aKey;

- (void)addBOOL:(BOOL)aValue forKey:(id <NSCopying>)aKey;

- (void)addBOOLStr:(BOOL)aValue forKey:(id <NSCopying>)aKey;

- (void)addNumber:(NSNumber *)aValue forKey:(id <NSCopying>)aKey;

- (void)addArray:(NSArray *)aValue forKey:(id <NSCopying>)aKey;

- (NSString *)convertToJSONString;

@end


@interface NSDictionary (Json)

- (NSMutableDictionary *)dictionaryForKey:(id<NSCopying>)key;
- (NSString *)stringForKey:(id<NSCopying>)key;
- (NSInteger)integerForKey:(id<NSCopying>)key;
- (BOOL)boolForKey:(id<NSCopying>)key;
- (CGFloat)floatForKey:(id<NSCopying>)key;
- (NSString *)floatValue:(id<NSCopying>)key;
- (double)doubleForKey:(id<NSCopying>)key;
- (NSMutableArray *)arrayForKey:(id<NSCopying>)key;

@end