//
//  PayListModel.h
//  Glshop
//
//  Created by River on 15-1-9.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface PayListModel : WXBaseModel

@property (nonatomic, strong) NSNumber *amount;
@property (nonatomic, strong) NSNumber *balance;
@property (nonatomic, copy) NSString *creator;
@property (nonatomic, copy) NSString *createdate;
@property (nonatomic, copy) NSString *paytime;
@property (nonatomic, strong) NSDictionary *devices;
@property (nonatomic, strong) NSDictionary *direction;
@property (nonatomic, copy) NSString *oid;
@property (nonatomic, copy) NSString *name;
@property (nonatomic, strong) NSNumber *needamount;
@property (nonatomic, copy) NSString *payId;
@property (nonatomic, strong) NSDictionary *otype;
@property (nonatomic, copy) NSString *passid;
@property (nonatomic, copy) NSString *payno;
@property (nonatomic, strong) NSDictionary *paytype;
@property (nonatomic, copy) NSString *status;

@end
