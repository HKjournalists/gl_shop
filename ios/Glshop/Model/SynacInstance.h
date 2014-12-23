//
//  SynacInstance.h
//  Glshop
//
//  Created by River on 14-11-28.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  同步数据类

#import <Foundation/Foundation.h>
#import "NSString+DTPaths.h"
#import "RiverSectionModel.h"
#import "GoodsModel.h"
#import "GoodChildModel.h"

/**
 *@brief 商品信息、系统信息、银行信息、地域信息都从此处获取.
 *@discussion 所有数据都要排序
 */
@interface SynacInstance : NSObject

@property (nonatomic, strong) NSArray *riverSectionsArray; // 河段
@property (nonatomic, strong) NSArray *goodsArray;         // 商品大类
@property (nonatomic, strong) NSArray *goodsChildArray;    // 商品子类
@property (nonatomic, strong) NSArray *stoneSubType;       // 石子下属小类
@property (nonatomic, strong) NSArray *stoneSubTypeName;    // 石子下属小类名称
@property (nonatomic, strong) NSArray *sendSubType;       // 黄砂下属小类
@property (nonatomic, strong) NSArray *sendSubTypeName;    // 黄砂下属小类名称
@property (nonatomic, strong) NSArray *sendsDicArray;      // key:ptype value:@[goodchildModel1,goodchildModel2]

+ (id)sharedInstance;

/**
 *@brief 同步数据
 */
- (void)synacData;

/**
 *@brief 返回江段名数组
 */
- (NSArray *)riverSectionsNames;

/**
 *@brief 商品大类名称、对象
 */
- (NSArray *)productTopNames;
- (NSArray *)productTopModels;

/**
 *@brief 根据pcode找到相应的产品顶层model
 */
- (GoodsModel *)goodsModelForPcode:(NSString *)pcode;

/**
 *@brief 根据ptype找到黄砂二级对象：湖砂、河砂...
 */
- (GoodsModel *)goodsModelForPtype:(NSString *)ptype;

/**
 *@brief 根据服务器返回的江段code，需要自己映射名字，以便做展示
 *@param codeToName YES 返回名称 NO 返回code
 *@param code 服务器返回的实体code
 */
- (NSString *)codeMappingName:(BOOL)codeToName targetStr:(NSString *)code;

/**
 *@brief 根据ptype找到黄砂的第三层子商品
 */
- (NSArray *)sendsGroundSonProductType:(NSString *)ptype;

/**
 *@brief 根据pid找到指定商品，针对黄砂
 */
- (GoodChildModel *)goodsChildModlelFor:(NSString*)ptype deepId:(NSString *)pid;

/**
 *@brief 根据pid找到商品，针对石子
 */
- (GoodChildModel *)goodsChildStone:(NSString *)pid;

/**
 *@brief 根据产品名找到产品模型 GoodsModel
 */
- (GoodsModel *)productNameMapGoodsModel:(NSString *)productName;

/**
 *@brief 黄砂第三层商品名。例如：中砂、特细砂
 */
- (NSArray *)sendsGroundSonUnMontageProductTypeName:(NSString *)ptype;

/**
 *@brief 拼接第三层商品。例如：（特细砂（1.0-5.6）
 */
- (NSArray *)sendsGroundSonProductTypeName:(NSString *)ptype;

/**
 *@brief 根据石子子类名称找到子类模型
 */
- (GoodChildModel *)stoneSubTypeNameMapstoneSubTypeModel:(NSString *)stoneName;

@end
