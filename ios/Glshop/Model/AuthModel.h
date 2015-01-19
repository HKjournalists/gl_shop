//
//  AuthModel.h
//  Glshop
//
//  Created by River on 15-1-7.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface AuthModel : WXBaseModel

/**
 *@brief 认证类型
 */
@property (nonatomic, copy) NSString *ctypeValue;

/**
 *@brief 认证状态
 */
@property (nonatomic, strong) NSDictionary *authstatus;

/**
 *@brief 认证图片图片ID
 */
@property (nonatomic, copy) NSString *imgid;
/**
 *@brief 企业简介
 */
@property (nonatomic, copy) NSString *mark;
/**
 *@brief 企业照片ID，多ID用逗号间隔，例：ID1,ID2,ID3
 */
@property (nonatomic, copy) NSString *companyImgIds;
/**
 *@brief 默认卸货地址ID
 */
@property (nonatomic, copy) NSString *addressid;

/**
 *@brief 样例图片名称：企业执照/身份证/船舶
 */
@property (nonatomic, copy) NSString *exampleImgName;

@property (nonatomic, copy) NSString *addrAreaFullName;
@property (nonatomic, copy) NSString *address;

@property (nonatomic, copy) NSString *contact;
@property (nonatomic, copy) NSString *phone;
@property (nonatomic, copy) NSString *tel;

@end
