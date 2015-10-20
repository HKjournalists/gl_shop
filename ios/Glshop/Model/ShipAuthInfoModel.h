//
//  ShipAuthInfoModel.h
//  Glshop
//
//  Created by River on 15-1-15.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "WXBaseModel.h"

@interface ShipAuthInfoModel : WXBaseModel

/**
 *@brief
 */
@property (nonatomic, copy) NSString *authid;
/**
 *@brief 船舶名称
 */
@property (nonatomic, copy) NSString *sname;
/**
 *@brief 船集港
 */
@property (nonatomic, copy) NSString * pregistry;
/**
 *@brief 船舶登记号
 */
@property (nonatomic, copy) NSString *sno;
/**
 *@brief 船舶检验机构
 */
@property (nonatomic, copy) NSString *sorg;
/**
 *@brief 船舶所有人
 */
@property (nonatomic, copy) NSString *sowner;
/**
 *@brief 船舶经营人
 */
@property (nonatomic, copy) NSString *sbusinesser;
/**
 *@brief 船舶种类
 */
@property (nonatomic, copy) NSString *stype;
/**
 *@brief 船舶创建日期
 */
@property (nonatomic, copy) NSString *screatetime;
/**
 *@brief 总吨位
 */
@property (nonatomic, strong) NSNumber *stotal;
/**
 *@brief 载重
 */
@property (nonatomic, strong) NSNumber *sload;
/**
 *@brief 船长
 */
@property (nonatomic, strong) NSNumber *slength;
/**
 *@brief 船宽
 */
@property (nonatomic, strong) NSNumber *swidth;

/**
 *@brief 型深
 */
@property (nonatomic, strong) NSNumber *sdeep;

/**
 *@brief 满载吃水
 */
@property (nonatomic, strong) NSNumber *sover;

/**
 *@brief 船体材料
 */
@property (nonatomic, copy) NSString *smateriall;
/**
 *@brief 更新时间
 */
@property (nonatomic, copy) NSString *updatedate;

@end
