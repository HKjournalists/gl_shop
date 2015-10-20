//
//  SynacInstance.m
//  Glshop
//
//  Created by River on 14-11-28.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "SynacInstance.h"

@interface SynacInstance ()

@property (nonatomic, strong) id jsonData;

@end

@implementation SynacInstance

#pragma mark - Initalize
+ (id)sharedInstance {
    static id sharedInstance;
    static dispatch_once_t once;
    dispatch_once(&once, ^{
        sharedInstance = [[[self class] alloc] init];
    });
    return sharedInstance;
}

#pragma mark - Private
- (void)synacData {
    NSString *file = [[NSString documentsPath] stringByAppendingPathComponent:kSynacFileName];
    
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
        NSData *data = [NSData dataWithContentsOfFile:file];
        if (data == nil) {
            [[NSFileManager defaultManager] removeItemAtPath:file error:nil];
            data = [NSData dataWithContentsOfFile:[[NSBundle mainBundle] pathForResource:kSynacFileName ofType:nil]];
        }
        id json = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
        
        // 必须在主线程
        dispatch_async(dispatch_get_main_queue(), ^{
            [self synacData:json];
            self.jsonData = json;
            
            [[NSNotificationCenter defaultCenter] postNotificationName:synacDataDidFinishNotification object:nil];
        });
        
    });
}

/**
 *@brief 获取银行列表信息
 */
- (NSArray *)banksData {
    NSDictionary *responseDic = [self.jsonData objectForKey:ServiceDataKey];
    NSArray *bankArray = [[responseDic objectForKey:@"banks"] objectForKey:@"data"];
    NSMutableArray *banks = [NSMutableArray arrayWithCapacity:bankArray.count];
    for (NSDictionary *dic in bankArray) {
        BankModel *model = [[BankModel alloc] initWithDataDic:dic];
        [banks addObject:model];
    }
    
    return [NSArray arrayWithArray:banks];
}

/**
 *@brief 系统参数
 */
- (NSArray *)sysParams {
    NSDictionary *responseDic = [self.jsonData objectForKey:ServiceDataKey];
    NSArray *sysParamArray = [[responseDic objectForKey:@"sysParam"] objectForKey:@"data"];
    return sysParamArray;
}

- (void)synacData:(id)json {
    NSDictionary *responseDic = [json objectForKey:ServiceDataKey];
    // 河段
    NSArray *sectionsArray = [[responseDic objectForKey:@"riverSection"] objectForKey:@"data"];
    NSMutableArray *sectionstemp = [NSMutableArray array];
    for (NSDictionary *dic in sectionsArray) {
        RiverSectionModel *river = [[RiverSectionModel alloc] initWithDataDic:dic];
        [sectionstemp addObject:river];
    }
    
    NSArray *sortedArray = [sectionstemp sortedArrayUsingComparator:^NSComparisonResult(RiverSectionModel *p1, RiverSectionModel *p2){
        return [p1.riverOrderno compare:p2.riverOrderno];
    }];
    
    [[SynacInstance sharedInstance] setRiverSectionsArray:[NSArray arrayWithArray:sortedArray]];
    
    // 商品大类
    NSArray *goosArray = [[responseDic objectForKey:@"goods"] objectForKey:@"data"];
    NSMutableArray *goodsTemp = [NSMutableArray array];
    for (NSDictionary *dicg in goosArray) {
        GoodsModel *goods = [[GoodsModel alloc] initWithDataDic:dicg];
        [goodsTemp addObject:goods];
    }
    [self setGoodsArray:[NSArray arrayWithArray:goodsTemp]];
    
    // 商品小类
    NSArray *goodChildArray = [[responseDic objectForKey:@"goodChild"] objectForKey:@"data"];
    NSMutableArray *goodChildTemp = [NSMutableArray array];
    for (NSDictionary *dic in goodChildArray) {
        GoodChildModel *goods = [[GoodChildModel alloc] initWithDataDic:dic];
        [goodChildTemp addObject:goods];
    }
    [self setGoodsChildArray:[NSArray arrayWithArray:goodChildTemp]];
    
    // 获取石子、黄砂下属商品
    [self stoneSubTypeParse];
    
    [self setSendsDicArray:[self sendsPtypeMapsendsgroundsonArray]];
    
}

- (GoodsModel *)stoneModel {
    NSArray *topProducts = [SynacObject productTopModels];
    GoodsModel *model;
    for (GoodsModel *aModel in topProducts) {
        if ([aModel.goodsVal isEqualToString:glProduct_top_stone_code]) {
            model = aModel;
        }
    }
    return model;
}

- (GoodsModel *)sendsModel {
    NSArray *topProducts = [SynacObject productTopModels];
    GoodsModel *model;
    for (GoodsModel *aModel in topProducts) {
        if ([aModel.goodsVal isEqualToString:glProduct_top_send_code]) {
            model = aModel;
        }
    }
    return model;
}

/**
 *@brief 获取石子、黄砂下属商品
 *@discussion 石子下属商品：瓜子片、石粉、碎石...
 黄砂下属商品：湖砂-》（特细砂，中砂...)、河砂-》（特细砂、中砂...) 、、、、
 */
- (void)stoneSubTypeParse {
    NSArray *topProduct = [self productTopModels];
    NSMutableArray *stoneTemp = [NSMutableArray array];
    NSMutableArray *sendTemp = [NSMutableArray array];
    for (GoodsModel *model in topProduct) {
        if ([model.goodsVal isEqualToString:glProduct_top_stone_code]) {
            for (GoodChildModel *childModel in self.goodsChildArray) {
                if ([childModel.goodChildPcode isEqualToString:model.goodsVal]) {
                    [stoneTemp addObject:childModel];
                }
            }
        }
        
        if ([model.goodsVal isEqualToString:glProduct_top_send_code]) {
            for (GoodsModel *childModel in self.goodsArray) {
                if ([childModel.goodsPcode isEqualToString:model.goodsVal] && ![childModel.goodsVal isEqualToString:model.goodsVal]) {
                    [sendTemp addObject:childModel];
                }
            }
        }
        
    }
    
     // 排序
    NSArray *sortedStoneArray = [stoneTemp sortedArrayUsingComparator:^NSComparisonResult(GoodChildModel *p1, GoodChildModel *p2){
        return [p1.goodOrderno compare:p2.goodOrderno];
    }];
    NSArray *sortedSendArray = [sendTemp sortedArrayUsingComparator:^NSComparisonResult(GoodsModel *p1, GoodsModel *p2){
        return [p1.orderNo compare:p2.orderNo];
    }];
    
    // 石子子类
    self.stoneSubType = sortedStoneArray;
    // 黄砂2级子类
    self.sendSubType = sortedSendArray;
    
    // 石子子类名称
    NSMutableArray *stonesSubTypeName = [NSMutableArray array];
    for (GoodChildModel *model in _stoneSubType) {
        [stonesSubTypeName addObject:model.goodChildPname];
    }
    self.stoneSubTypeName = [NSArray arrayWithArray:stonesSubTypeName];
    
    // 黄砂子类2级名称
    NSMutableArray *sendsSubTypeName = [NSMutableArray array];
    for (GoodsModel *model in _sendSubType) {
        [sendsSubTypeName addObject:model.goodsName];
    }
    self.sendSubTypeName = [NSArray arrayWithArray:sendsSubTypeName];
}

/**
 *@brief 根据石子子类名称找到子类模型
 */
- (GoodChildModel *)stoneSubTypeNameMapstoneSubTypeModel:(NSString *)stoneName {
    for (GoodChildModel *model in self.stoneSubType) {
        if ([model.goodChildPname isEqualToString:stoneName]) {
            return model;
            break;
        }
    }
    return nil;
}

#pragma mark - Public
/**
 *@brief 返回江段名数组
 */
- (NSArray *)riverSectionsNames {
    NSMutableArray *temp = [NSMutableArray array];
    for (RiverSectionModel *model in self.riverSectionsArray) {
        [temp addObject:model.riverSectionName];
    }
    
    if (temp.count <= 0) {
        DebugLog(@"同步数据异常，请检查！");
        return @[@"晋江段",@"江阴段"];
    }else {
        return [NSArray arrayWithArray:temp];
    }
    
}

/**
 *@brief 商品大类名称
 */
- (NSArray *)productTopNames {
    NSMutableArray *temp = [NSMutableArray array];
    for (GoodsModel *model in [self productTopModels]) {
        [temp addObject:model.goodsName];
    }
    
    if (temp.count <= 0) {
        DebugLog(@"同步数据异常，请检查！");
        return @[@"黄沙",@"石子"];
    }else {
        return [NSArray arrayWithArray:temp];
    }

}

/**
 *@brief 商品大类对象
 *@discussion 暂为石子和黄砂
 */
- (NSArray *)productTopModels {
    NSMutableArray *temp = [NSMutableArray array];
    for (GoodsModel *model in self.goodsArray) {
        if ([model.goodsPcode isEqualToString:@"0"]) { // pcode = 0的为大类商品
            [temp addObject:model];
        }
    }
    
    NSArray *sortedArray = [temp sortedArrayUsingComparator:^NSComparisonResult(GoodsModel *p1, GoodsModel *p2){
        return [p1.orderNo compare:p2.orderNo];
    }];
    
    if (temp.count <= 0) {
        return nil;
    }else {
#warning unknow problem
        GoodsModel *model = temp[0];
        if (model.orderNo == nil) {
            sortedArray = [NSArray arrayWithObjects:temp[1],temp[0], nil];
            return sortedArray;
        }
        return sortedArray;
    }
}

/**
 *@brief 根据pcode找到相应的产品顶层model
 */
- (GoodsModel *)goodsModelForPcode:(NSString *)pcode {
    for (GoodsModel *model in self.productTopModels) {
        if ([model.goodsVal isEqualToString:pcode]) {
            return model;
        }
    }
    return nil;
}

/**
 *@brief 根据产品名找到产品模型
 */
- (GoodsModel *)productNameMapGoodsModel:(NSString *)productName {
    GoodsModel *model;
    
    for (GoodsModel *model in _goodsArray) {
        if ([model.goodsName isEqualToString:productName]) {
            return model;
        }
    }
    
    return model;
}

/**
 *@brief 根据ptype找到黄砂的第三层子商品
 */
- (NSArray *)sendsGroundSonProductType:(NSString *)ptype {

    NSMutableArray *temp = [NSMutableArray array];
    for (GoodChildModel *model in _goodsChildArray) {
        if ([model.goodChildPtype isEqualToString:ptype]) {
            [temp addObject:model];
        }
    }
    NSArray *sortedArray = [temp sortedArrayUsingComparator:^NSComparisonResult(GoodChildModel *p1, GoodChildModel *p2){
        return [p1.goodOrderno compare:p2.goodOrderno];
    }];
    return sortedArray;
}

/**
 *@brief 根据ptype、pid找到指定商品，针对黄砂
 *@param ptype 黄砂type
 *@param pid 黄砂下属产品id
 */
- (GoodChildModel *)goodsChildModlelFor:(NSString*)ptype deepId:(NSString *)pid {
    NSArray *childs = [self sendsGroundSonProductType:ptype];
    for (GoodChildModel *child in childs) {
        if ([child.goodChildId isEqualToString:pid]) {
            return child;
        }
    }
    return nil;
}

/**
 *@brief 根据pid找到商品，针对石子
 */
- (GoodChildModel *)goodsChildStone:(NSString *)pid {
    for (GoodChildModel *model in self.stoneSubType) {
        if ([model.goodChildId isEqualToString:pid]) {
            return model;
        }
    }
    return nil;
}

/**
 *@brief 根据ptype找到黄砂二级对象：湖砂、河砂...
 */
- (GoodsModel *)goodsModelForPtype:(NSString *)ptype {
    for (GoodsModel *model in self.sendSubType) {
        if ([model.goodsVal isEqualToString:ptype]) {
            return model;
        }
    }
    return nil;
}

/**
 *@brief 黄砂第三层商品名。例如：中砂、特细砂
 */
- (NSArray *)sendsGroundSonUnMontageProductTypeName:(NSString *)ptype {
    NSArray *array = [self sendsGroundSonProductType:ptype];
    NSMutableArray *temp = [NSMutableArray array];
    for (GoodChildModel *model in array) {
        [temp addObject:model.goodChildPname];
    }
    return [NSArray arrayWithArray:temp];
}

/**
 *@brief 拼接第三层商品。例如：（特细砂（1.0-5.6）
 */
- (NSArray *)sendsGroundSonProductTypeName:(NSString *)ptype {
    NSMutableArray *temp = [NSMutableArray array];
    for (GoodChildModel *model in _goodsChildArray) {
        if ([model.goodChildPtype isEqualToString:ptype]) {
            [temp addObject:model];
        }
    }
    NSArray *sortedArray = [temp sortedArrayUsingComparator:^NSComparisonResult(GoodChildModel *p1, GoodChildModel *p2){
        return [p1.goodOrderno compare:p2.goodOrderno];
    }];
    
    NSMutableArray *nameTemp = [NSMutableArray array];
    for (GoodChildModel *model in sortedArray) {
        float max = [model.sizeModel.maxv floatValue];
        float min = [model.sizeModel.minv floatValue];
        NSString *str = [NSString stringWithFormat:@"%@(%.1f-%.1f)mm",model.goodChildPname,min,max];
        [nameTemp addObject:str];
    }
    
    return [NSArray arrayWithArray:nameTemp];
}

/**
 *@brief 拼接第三层商品。例如：（特细砂（1.0-5.6）
 */
- (NSString *)sendsThreeCombine:(GoodChildModel *)model {
    NSString *max = [model.sizeModel.maxv stringValue];
    NSString *min = [model.sizeModel.minv stringValue];
    NSString *str = [NSString stringWithFormat:@"%@(%@-%@)mm",model.goodChildPname,min,max];
    return str;
}

- (NSArray *)sendsPtypeMapsendsgroundsonArray {
    
    NSMutableArray *tempDicArray = [NSMutableArray array];
    for (GoodsModel *model in _sendSubType) {
        NSMutableArray *temp = [NSMutableArray array];
        for (GoodChildModel *childModel in _goodsChildArray) {
            if ([childModel.goodChildPtype isEqualToString:model.goodsVal]) {
                [temp addObject:childModel];
            }
        }
        NSDictionary *dic = [NSDictionary dictionaryWithObjectsAndKeys:temp,model.goodsVal, nil];
        [tempDicArray addObject:dic];
    }
    return [NSArray arrayWithArray:tempDicArray];
}

/**
 *@brief 根据服务器返回的code，需要自己映射名字，以便做展示
 *@param codeToName YES 返回名称 NO 返回code
 *@param code 服务器返回的实体code
 */
- (NSString *)codeMappingName:(BOOL)codeToName targetStr:(NSString *)code; {
    for (RiverSectionModel *model in self.riverSectionsArray) {
        if (codeToName) {
            if ([model.riverSectionVal isEqualToString:code]) {
                return model.riverSectionName;
            }
 
        }else {
            if ([model.riverSectionName isEqualToString:code]) {
                return model.riverSectionPcode;
            }
        }
    }
    return nil;
}

- (NSString *)combinProducName:(NSString *)ptype proId:(NSString *)proId {
    NSString *proTitle;
    if (ptype.length) {
        GoodsModel *model = [SynacObject goodsModelForPtype:ptype];
        GoodChildModel *child = [SynacObject goodsChildModlelFor:ptype deepId:proId];
        proTitle = [NSString stringWithFormat:@"%@.%@.%@",glProduct_top_send_name,model.goodsName,[child combineNameWithUnit]];
    }else {
        GoodChildModel *child = [SynacObject goodsChildStone:proId];
        proTitle = [NSString stringWithFormat:@"%@.%@",glProduct_top_stone_name,[child combineNameWithUnit]];
    }
    return proTitle;
}

@end
