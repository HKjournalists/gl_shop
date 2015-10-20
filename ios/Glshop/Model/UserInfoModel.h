//
//  UserInfoModel.h
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/23.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface UserInfoModel : NSObject

@property (nonatomic,copy)NSString *iconName;
@property (nonatomic,copy)NSString *placeHolderStr;

+(instancetype)createWithIconImage:(NSString *)imageName PlaceHolderString:(NSString *)placeHolderStr;
@end
