//
//  ProModel.h
//  Glshop
//
//  Created by River on 14-12-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface ProModel : WXBaseModel

@property (nonatomic, copy) NSString *code;
@property (nonatomic, copy) NSString *id;
@property (nonatomic, copy) NSString *pid;
@property (nonatomic, copy) NSString *name;
@property (nonatomic, copy) NSString *types;
@property (nonatomic, strong) NSNumber *maxv;
@property (nonatomic, strong) NSNumber *minv;
@property (nonatomic, copy) NSString *content;
@property (nonatomic, strong) NSNumber *orderno;
@property (nonatomic, strong) NSDictionary *status;

@end
