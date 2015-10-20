//
//  UserInfoModel.m
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/23.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "UserInfoModel.h"

@implementation UserInfoModel

+(instancetype)createWithIconImage:(NSString *)imageName PlaceHolderString:(NSString *)placeHolderStr
{

    return [[self alloc] initWithIconImage:imageName PlaceHolderString:placeHolderStr];
}

- (instancetype)initWithIconImage:(NSString *)imageName PlaceHolderString:(NSString *)placeHolderStr{

    self = [super init];
    if (self) {
        self.iconName = imageName;
        self.placeHolderStr = placeHolderStr;
    }
    return self;
}

@end
