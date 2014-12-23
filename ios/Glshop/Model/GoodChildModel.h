//
//  GoodChildModel.h
//  Glshop
//
//  Created by River on 14-12-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "WXBaseModel.h"
#import "PsizeModel.h"
#import "ProModel.h"

//@"goodChildId":@"id",
//@"goodChildPname":@"pname",
//@"goodChildPcode":@"pcode",
//@"goodChildPcolor":@"pcolor",
//@"goodChildPaddress":@"paddress",
//@"goodChildUnit":@"unit",
//@"goodChildRemark":@"remark",

@interface GoodChildModel : WXBaseModel

@property (nonatomic, copy) NSString *goodChildId;
@property (nonatomic, copy) NSString *goodChildPname;
@property (nonatomic, copy) NSString *goodChildPcode;
@property (nonatomic, copy) NSString *goodChildPtype; // 商品沙子有此字段，石子没有
@property (nonatomic, copy) NSString *goodChildPcolor;
@property (nonatomic, copy) NSString *goodChildPaddress;
@property (nonatomic, copy) NSString *goodChildUnit;
@property (nonatomic, copy) NSString *goodChildRemark;
@property (nonatomic, strong) PsizeModel *sizeModel;
@property (nonatomic, strong) NSArray *propretyArray;
@property (nonatomic, strong) NSArray *propreDicArray;
@property (nonatomic, strong) NSNumber *goodOrderno;

@end
