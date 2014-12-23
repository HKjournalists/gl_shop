//
//  AddressViewController.h
//  Glshop
//
//  Created by River on 14-12-15.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "BaseViewController.h"
#import "PublicInfoModel.h"

#define PROVINCE_COMPONENT  0
#define CITY_COMPONENT      1
#define DISTRICT_COMPONENT  2

@interface AddressViewController : BaseViewController<UIPickerViewDelegate, UIPickerViewDataSource>{
    UIPickerView *picker;
    
    NSDictionary *areaDic;
    NSArray *province;
    NSArray *city;
    NSArray *district;
    
    NSString *selectedProvince;
}


@property (nonatomic, weak) PublicInfoModel *publicModel;
@property (nonatomic, strong, readonly) UITableView *tableView;

@end
