//
//  PublicInfoModel.h
//  Glshop
//
//  Created by River on 14-12-12.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "WXBaseModel.h"
#import "PublicOtherInfoModel.h"
#import "AddressPublicModel.h"
#import "PhotoUploadView.h"

typedef NS_ENUM(NSInteger, BussinessType) {
    BussinessTypeBuy = 1, // 求购信息
    BussinessTypeSell = 2, // 出售信息
};

@interface PublicInfoModel : WXBaseModel


@property (nonatomic, strong) AddressPublicModel *addressModel;
/**
 *@brief 区域全称（中文）
 */
@property (nonatomic, copy) NSString *areaFullName;

/**
 *@brief 区域编码（最后一级）
 */
@property (nonatomic, copy) NSString *areaCode;

/**
 *@brief 详细地址
 */
@property (nonatomic, copy) NSString *address;
/**
 *@brief 水深度
 */
@property (nonatomic, strong) NSNumber *deep;
/**
 *@brief 可停泊船吨位
 */
@property (nonatomic, strong) NSNumber *shippington;


@property (nonatomic, strong) NSNumber *realdeep;
@property (nonatomic, strong) NSArray *addressImgModels;
@property (nonatomic, strong) NSArray *productImgList; 


@property (nonatomic, strong) PhotoUploadView *photoUploadView;

@property (nonatomic, copy) NSString *cid; // 公司id
@property (nonatomic, copy) NSString *id; // 订单id
//@property (nonatomic, copy) NSString *limitime; // 交易结束时间
@property (nonatomic, copy) NSString *endtime;
@property (nonatomic, copy) NSString *starttime; // 交易开始时间
@property (nonatomic, strong) NSDictionary *addresstype; // 卸货地址指定方式
@property (nonatomic, copy) NSString *cname; // 公司名称
@property (nonatomic, copy) NSString *creatime;  // 订单生成时间
@property (nonatomic, strong) NSNumber *num;
@property (nonatomic, strong) NSNumber *isApply;
@property (nonatomic, copy) NSString *opiid;
@property (nonatomic, assign) BOOL isOwen; // 是否是己方指定卸货地址 
@property (nonatomic, strong) NSArray *proList; // proModel
@property (nonatomic, copy) NSString *paddress; // 产地
@property (nonatomic, copy) NSString *pcode; // 产品code
@property (nonatomic, copy) NSString *pcolor; // 产品颜色
@property (nonatomic, copy) NSString *pid;  // 产品id
@property (nonatomic, copy) NSString *pname; // 产品名称
@property (nonatomic, strong) NSNumber *price; // 产品单价
@property (nonatomic, strong) PsizeModel *sizeMode; // 产品规格
@property (nonatomic, copy) NSString *ptype;  // 黄砂ptype
@property (nonatomic, strong) NSNumber *status; // 状态
@property (nonatomic, strong) NSNumber *totalnum; // 发布货物总量
@property (nonatomic, strong) NSNumber *type;  // 出售还是求购
@property (nonatomic, strong) NSDictionary *unit;   // 单位：立方还是吨
@property (nonatomic, copy) NSString *updatetime; // 更新时间
@property (nonatomic, strong) NSMutableArray *productDicArray;
@property (nonatomic, copy) NSString *productPropertys; // jsonStr
@property (nonatomic, copy) NSString *remark; // 发布信息备注
@property (nonatomic, copy) NSString *premark; // 产品备注

@property (nonatomic, assign) BOOL isSetDetailStand; // 是否填写了详细规格


@end
