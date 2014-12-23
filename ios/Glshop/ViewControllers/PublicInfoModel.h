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

typedef NS_ENUM(NSInteger, BussinessType) {
    BussinessTypeBuy = 1, // 求购信息
    BussinessTypeSell = 2, // 出售信息
};

@interface PublicInfoModel : WXBaseModel

@property (nonatomic, copy) NSString *product;   // 产品类型
@property (nonatomic, copy) NSString *productCateory; // 产品分类
@property (nonatomic, copy) NSString *productStandra; // 产品规格
@property (nonatomic, copy) NSString *detailStandard; // 产品详细规格
@property (nonatomic, strong) NSArray *detailstandards; // 货物详细规格
@property (nonatomic, copy) NSString *productColor;  // 产品颜色
@property (nonatomic, copy) NSString *productPlace; // 产品产地
@property (nonatomic, copy) NSString *productRemark; // 货物备注
@property (nonatomic, strong) PublicOtherInfoModel *publicOtherInfoModel; // 交易信息model
@property (nonatomic, strong) AddressPublicModel *addressModel;

@property (nonatomic, strong) NSMutableArray *entryImages; // 实物照片
@property (nonatomic, strong) NSArray *photoModels;   // 照片对象
@property (nonatomic, strong) UIImage *image1;
@property (nonatomic, strong) UIImage *image2;
@property (nonatomic, strong) UIImage *image3;

@property (nonatomic, copy) NSString *cid;
@property (nonatomic, copy) NSString *id; // 信息id
@property (nonatomic, copy) NSString *limitime; // 交易结束时间
@property (nonatomic, copy) NSString *starttime; // 交易开始时间
@property (nonatomic, strong) NSDictionary *addressType; // 卸货地址指定方式
@property (nonatomic, copy) NSString *cname; // 公司名称
@property (nonatomic, copy) NSString *creatime;
@property (nonatomic, strong) NSNumber *num;
@property (nonatomic, strong) NSNumber *isApply;
@property (nonatomic, copy) NSString *opiid;
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
@property (nonatomic, copy) NSString *unit;   // 单位：立方还是吨
@property (nonatomic, copy) NSString *updatetime; // 更新时间
@property (nonatomic, copy) NSString *productPropertys; // jsonStr
@property (nonatomic, copy) NSString *remark; // 发布信息备注
@property (nonatomic, copy) NSString *premark; // 产品备注


@end
