//
//  CinemaViewController.m
//  GLIOS
//
//  Created by Mctu on 13-7-20.
//  Copyright (c) 2013年 www.sinawb.com WXHL. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface WXBaseModel : NSObject <NSCoding>{

}
-(id)initWithDataDic:(NSDictionary*)data;

- (NSDictionary*)attributeMapDictionary;

- (void)setAttributes:(NSDictionary*)dataDic;

- (NSString *)customDescription;

- (NSString *)description;

- (NSData*)getArchivedData;

- (NSString *)cleanString:(NSString *)str;    //清除\n和\r的字符串

@end
