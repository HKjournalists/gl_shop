//
//  ContactModel.h
//  Glshop
//
//  Created by River on 15-1-5.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  企业联系人

#import "WXBaseModel.h"

@interface ContactModel : WXBaseModel

@property (nonatomic, copy) NSString *compId;
@property (nonatomic, copy) NSString *cname;
@property (nonatomic, copy) NSString *cphone;
@property (nonatomic, copy) NSString *tel;
@property (nonatomic, copy) NSString *status;

@end
