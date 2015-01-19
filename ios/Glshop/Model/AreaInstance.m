//
//  AreaInstance.m
//  Glshop
//
//  Created by River on 15-1-6.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "AreaInstance.h"

@implementation AreaInstance

#pragma mark - Initalize
+ (id)sharedInstance {
    static id sharedInstance;
    static dispatch_once_t once;
    dispatch_once(&once, ^{
        sharedInstance = [[[self class] alloc] init];
    });;
    return sharedInstance;
}

- (void)synacData {
    NSString *file = [[NSString documentsPath] stringByAppendingPathComponent:kAreaFileName];
    NSData *data = [NSData dataWithContentsOfFile:file];
    if (data == nil) {
        data = [NSData dataWithContentsOfFile:[[NSBundle mainBundle] pathForResource:kAreaFileName ofType:nil]];
    }
    id json = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableContainers error:nil];
    NSArray *datas = json[ServiceDataKey][@"area"][@"data"];
    
    NSMutableArray *tempModels = [NSMutableArray array];
    for (NSDictionary *dic in datas) {
        AreaModel *areaModel = [[AreaModel alloc] initWithDataDic:dic];
        [tempModels addObject:areaModel];
    }
    _allAreas = [NSArray arrayWithArray:tempModels];
    
    // 获得省
    NSMutableArray *provinceTemp = [NSMutableArray array];
    for (AreaModel *model in _allAreas) {
        if ([model.areaPcode isEqualToString:@"0"]) {
            [provinceTemp addObject:model];
        }
    }
    _provinceAreas = [NSArray arrayWithArray:provinceTemp];
    
}

/**
 *@brief 根据省，找到相应的市
 *@param provinceModel 省数据对象
 */
- (NSArray *)citysForProvinceId:(AreaModel *)provinceModel {
    NSMutableArray *temp = [NSMutableArray array];
    for (AreaModel *model in _allAreas) {
        if ([model.areaPcode isEqualToString:provinceModel.areaVal]) {
            [temp addObject:model];
        }
    }
    return [NSArray arrayWithArray:temp];
}

/**
 *@brief 根据市，找到相应的区
 *@param provinceModel 市数据对象
 */
- (NSArray *)regionAreasForProvince:(AreaModel *)cityModel {
    NSMutableArray *temp = [NSMutableArray array];
    for (AreaModel *model in _allAreas) {
        if ([model.areaPcode isEqualToString:cityModel.areaVal]) {
            [temp addObject:model];
        }
    }
    return [NSArray arrayWithArray:temp];
}

@end
