//
//  CompanyAuthModel.h
//  Glshop
//
//  Created by River on 15-1-5.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface CompanyAuthModel : WXBaseModel

@property (nonatomic, copy) NSString *address;
@property (nonatomic, copy) NSString *cname;
@property (nonatomic, copy) NSString *cpId;
@property (nonatomic, copy) NSString *authid;
@property (nonatomic, copy) NSString *lperson;
@property (nonatomic, copy) NSString *orgid;
@property (nonatomic, copy) NSString *rdate;

@end
