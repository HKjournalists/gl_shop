//
//  UpdateModel.h
//  Glshop
//
//  Created by River on 15/4/17.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface UpdateModel : WXBaseModel

@property (nonatomic, copy) NSString *lastname;
@property (nonatomic, copy) NSNumber *lastno;
@property (nonatomic, copy) NSString *mark;
@property (nonatomic, copy) NSString *downurl;
@property (nonatomic, copy) NSNumber *isforce; // 是否强制更新
@property (nonatomic, copy) NSNumber *fequency;

@end
