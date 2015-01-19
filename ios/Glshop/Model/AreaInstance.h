//
//  AreaInstance.h
//  Glshop
//
//  Created by River on 15-1-6.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AreaModel.h"

@interface AreaInstance : NSObject

/**
 *@brief 所有地域对象
 */
@property (nonatomic, strong) NSArray *allAreas;

/**
 *@brief 所有省对象
 */
@property (nonatomic, strong) NSArray *provinceAreas;


+ (id)sharedInstance;

- (void)synacData;

- (NSArray *)citysForProvinceId:(AreaModel *)provinceModel;

- (NSArray *)regionAreasForProvince:(AreaModel *)cityModel;

@end
