//
//  AddressPublicModel.h
//  Glshop
//
//  Created by River on 14-12-16.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "WXBaseModel.h"
#import "AddressImgModel.h"

@interface AddressPublicModel : WXBaseModel

@property (nonatomic, copy) NSString *address;
@property (nonatomic, copy) NSString *cid;
@property (nonatomic, copy) NSString *id;
@property (nonatomic, strong) NSNumber *realdeep;
@property (nonatomic, strong) NSNumber *deep;
@property (nonatomic, strong) NSDictionary *status;
@property (nonatomic, strong) NSArray *addressImgModels;

@end
