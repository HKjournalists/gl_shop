//
//  PlaceSelect.h
//  Glshop
//
//  Created by River on 14-12-23.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "AreaModel.h"

@protocol PlaceDidSelect <NSObject>

- (void)placeDidSelect:(NSString *)place theAreaCode:(NSString *)areaCode;

@end

@interface PlaceSelect : NSObject <UIPickerViewDelegate,UIPickerViewDataSource>
{
    UIPickerView *picker;
    
    NSDictionary *areaDic;
    NSArray *province;
    NSArray *city;
    NSArray *district;
    
    AreaModel *selectedProvince;
}

@property (nonatomic, assign) id <PlaceDidSelect> delegate;

/**
 *@brief 弹出全国省市区选择视图
 */
- (void)showPlaceSelectView;

@end
