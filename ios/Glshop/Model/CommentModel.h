//
//  CommentModel.h
//  Glshop
//
//  Created by River on 15-2-14.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

//"cid": "201501150000017",
//"oid": "1120150202234",
//"satisfaction": 5,
//"credit": 5,
//"evaluation": "由于您7天内未评价对方，系统已默认设置为好评.",
//"cratedate": "2015-02-11 17:30:00",
//"creater": "111120140000012",
//"cname": "国立测试公司1421313279529"

#import "WXBaseModel.h"

@interface CommentModel : WXBaseModel

@property (nonatomic, strong) NSNumber *satisfaction;
@property (nonatomic, strong) NSNumber *credit;
@property (nonatomic, copy) NSString *evaluation;
@property (nonatomic, copy) NSString *cname;
@property (nonatomic, copy) NSString *cratedate;
@property (nonatomic, copy) NSString *creater;
@property (nonatomic, copy) NSString *cid;
@property (nonatomic, copy) NSString *oid;

@end
