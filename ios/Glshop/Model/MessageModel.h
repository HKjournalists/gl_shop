//
//  MessageModel.h
//  Glshop
//
//  Created by River on 15-2-27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface MessageModel : WXBaseModel

@property (nonatomic, copy) NSString *messageId;
@property (nonatomic, copy) NSString *qyid;
@property (nonatomic, copy) NSString *content;
@property (nonatomic, strong) NSDictionary *type;
@property (nonatomic, copy) NSString *businessid;
@property (nonatomic, strong) NSDictionary *businesstype;
@property (nonatomic, strong) NSDictionary *status;
@property (nonatomic, copy) NSString *createtime;

@end
