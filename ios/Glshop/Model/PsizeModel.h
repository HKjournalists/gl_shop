//
//  PsizeModel.h
//  Glshop
//
//  Created by River on 14-12-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

//"id": "182",
//"pid": "201411200000001",
//"name": "碎石1-2",
//"types": "float",
//"maxv": 25,
//"minv": 5,
//"content": "19.6",
//"status": {
//    "val": "1",
//    "text": "直接属性"
//},
//"orderno": 0

@interface PsizeModel : WXBaseModel

@property (nonatomic, copy) NSString *code;
@property (nonatomic, copy) NSString *id;
@property (nonatomic, copy) NSString *pid;
@property (nonatomic, copy) NSString *name;
@property (nonatomic, copy) NSString *types;
@property (nonatomic, strong) NSNumber *maxv;
@property (nonatomic, strong) NSNumber *minv;
@property (nonatomic, copy) NSString *content;
@property (nonatomic, strong) NSNumber *order;
@property (nonatomic, strong) NSDictionary *status;

@end
