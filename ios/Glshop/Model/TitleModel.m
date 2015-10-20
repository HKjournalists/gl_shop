//
//  TitleModel.m
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/23.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "TitleModel.h"

@implementation TitleModel
+ (instancetype)initWithTitleString:(NSString *)title
{

    return [[self alloc] initTitleString:title];

}
- (id)initTitleString:(NSString *)string
{
    self = [super init];
    if (self) {
        self.title = string;
    }
    return self;
}
@end
