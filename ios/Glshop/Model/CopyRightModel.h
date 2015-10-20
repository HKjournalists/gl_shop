//
//  CopyRightModel.h
//  Glshop
//
//  Created by River on 15-1-5.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"
#import "CompanyAuthModel.h"
#import "CompanyAuthInfoModel.h"
#import "PersonalAuthInfoModel.h"
#import "ShipAuthInfoModel.h"
#import "EvaluationModel.h"

@interface CopyRightModel : WXBaseModel

/**
 *@brief 认证状态
 */
@property (nonatomic, strong) NSDictionary *authstatus;

/**
 *@brief 企业认证资料
 */
@property (nonatomic, strong) CompanyAuthInfoModel *authCompanyModel;

/**
 *@brief 船舶认证资料
 */
@property (nonatomic, strong) ShipAuthInfoModel *authShipModel;

/**
 *@brief 个人认证资料
 */
@property (nonatomic, strong) PersonalAuthInfoModel *authPsModel;

/**
 *@brief 保证金缴纳状态
 */
@property (nonatomic, strong) NSDictionary *bailstatus;
@property (nonatomic, copy) NSString *cname;
/**
 *@brief 联系人手机号码
 */
@property (nonatomic, copy) NSString *cphone;
@property (nonatomic, copy) NSString *rdate;
@property (nonatomic, strong) NSDictionary *evaluationInfo;

/**
 *@brief 评价信息
 */
@property (nonatomic, strong) EvaluationModel *evaluatModel;
/**
 *@brief 企业id
 */
@property (nonatomic, copy) NSString *companyId;

/**
 *@brief 用户类型（个人，企业，船舶）
 */
@property (nonatomic, strong) NSDictionary *ctype;
/**
 *@brief 企业简介
 */
@property (nonatomic, copy) NSString *mark;

/**
 *@brief 省市区全称（中文）
 */
@property (nonatomic, copy) NSString *addrAreaFullName;
/**
 *@brief 详细地址
 */
@property (nonatomic, copy) NSString *address;
/**
 *@brief 弃用！！！
 */
@property (nonatomic, strong) NSNumber *realdeep;
/**
 *@brief 水深度
 */
@property (nonatomic, strong) NSNumber *deep;
/**
 *@brief 可停泊吨位
 */
@property (nonatomic, strong) NSNumber *shippington;
/**
 *@brief 联系电话（座机）
 */
@property (nonatomic, copy) NSString *tel;
/**
 *@brief 联系人
 */
@property (nonatomic, copy) NSString *contact;
/**
 *@brief 企业照片models
 */
@property (nonatomic, strong) NSArray *companyImgList;
/**
 *@brief 企业图片id
 *@discussion 有多张图片id拼接而成。e.g 23,89,4098
 */
@property (nonatomic, copy) NSString *companyImgIds;

@property (nonatomic, strong) NSArray *authImgList;
@property (nonatomic, strong) NSArray *addressImgList;
//@property (nonatomic, strong) CompanyAuthModel *authModel;

/**
 *@brief 用户是否已认证通过
 */
- (BOOL)isUserAuthed;

/**
 *@brief 是否已缴纳保证金
 */
- (BOOL)isPaymentMargin;


@end
