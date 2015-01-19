//
//  PersonalAuthInfoModel.h
//  Glshop
//
//  Created by River on 15-1-15.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface PersonalAuthInfoModel : WXBaseModel

/**
 *@brief 认证记录id
 */
@property (nonatomic, copy) NSString *authid;

/**
 *@brief 姓名
 */
@property (nonatomic, copy) NSString *cpname;
/**
 *@brief 性别
 */
@property (nonatomic, strong) NSDictionary *sex;
/**
 *@brief 身份证
 */
@property (nonatomic, copy) NSString *identification;
/**
 *@brief 籍贯
 */
@property (nonatomic, copy) NSString *origo;
/**
 *@brief 创建时间
 */
@property (nonatomic, copy) NSString *createdate;
/**
 *@brief 更新时间
 */
@property (nonatomic, copy) NSString *updatedate;

@property (nonatomic, copy) NSString *remark;
/**
 *@brief 出生日期
 */
@property (nonatomic, copy) NSString *birthday;
/**
 *@brief 签发机关
 */
@property (nonatomic, copy) NSString *issuingauth;
/**
 *@brief 有效期开始
 */
@property (nonatomic, copy) NSString *effstarttime;
/**
 *@brief 有效期结束
 */
@property (nonatomic, copy) NSString *effendtime;

@end
