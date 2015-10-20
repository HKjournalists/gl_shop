//
//  TitleModel.h
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/23.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TitleModel : NSObject
@property(nonatomic,copy)NSString *title;
+ (instancetype)initWithTitleString:(NSString *)title;
@end
