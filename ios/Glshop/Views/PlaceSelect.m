//
//  PlaceSelect.m
//  Glshop
//
//  Created by River on 14-12-23.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "PlaceSelect.h"
#import "HJActionSheet.h"
#import "AreaInstance.h"

#define PROVINCE_COMPONENT  0
#define CITY_COMPONENT      1
#define DISTRICT_COMPONENT  2

@interface PlaceSelect ()

@property (strong, nonatomic) HJActionSheet *hjSheet;

@end

@implementation PlaceSelect

- (instancetype)init
{
    self = [super init];
    if (self) {
        NSBundle *bundle = [NSBundle mainBundle];
        NSString *plistPath = [bundle pathForResource:@"area" ofType:@"plist"];
        areaDic = [[NSDictionary alloc] initWithContentsOfFile:plistPath];
        
        NSArray *components = [areaDic allKeys];
        NSArray *sortedArray = [components sortedArrayUsingComparator: ^(id obj1, id obj2) {
            
            if ([obj1 integerValue] > [obj2 integerValue]) {
                return (NSComparisonResult)NSOrderedDescending;
            }
            
            if ([obj1 integerValue] < [obj2 integerValue]) {
                return (NSComparisonResult)NSOrderedAscending;
            }
            return (NSComparisonResult)NSOrderedSame;
        }];
        
        NSMutableArray *provinceTmp = [[NSMutableArray alloc] init];
        for (int i=0; i<[sortedArray count]; i++) {
            NSString *index = [sortedArray objectAtIndex:i];
            NSArray *tmp = [[areaDic objectForKey: index] allKeys];
            [provinceTmp addObject: [tmp objectAtIndex:0]];
        }
        
        province = [[NSArray alloc] initWithArray: provinceTmp];
        
        NSString *index = [sortedArray safeObjAtIndex:0];
        NSString *selected = [province safeObjAtIndex:0];
        NSDictionary *dic = [NSDictionary dictionaryWithDictionary: [[areaDic objectForKey:index]objectForKey:selected]];
        
        NSArray *cityArray = [dic allKeys];
        NSDictionary *cityDic = [NSDictionary dictionaryWithDictionary: [dic objectForKey: [cityArray objectAtIndex:0]]];
        city = [[NSArray alloc] initWithArray: [cityDic allKeys]];
        
        
        NSString *selectedCity = [city safeObjAtIndex: 0];
        district = [[NSArray alloc] initWithArray: [cityDic objectForKey: selectedCity]];
        selectedProvince = [province safeObjAtIndex: 0];
        
        province = [[AreaInstance sharedInstance] provinceAreas];
        city = [[AreaInstance sharedInstance] citysForProvinceId:[province safeObjAtIndex:0]];
        district = [[AreaInstance sharedInstance] regionAreasForProvince:[city safeObjAtIndex:0]];
    }
    return self;
}

#pragma mark - Public Methods
- (void)showPlaceSelectView {
    [self.hjSheet showSheet];
}

#pragma mark - Getter
- (HJActionSheet *)hjSheet {
    if (!_hjSheet) {
        _hjSheet = [[HJActionSheet alloc] initWithTitle:nil contentView:[self contryPlace]];
    }
    return _hjSheet;
}

#pragma mark - Private
/**
 *@brief 省市区
 */
- (UIView *)contryPlace {
    UIView * contentView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 260)];
    contentView.backgroundColor = ColorWithHex(@"#EDEDED");
    
    UIView *headerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, contentView.vwidth, 40)];
    headerView.backgroundColor = ColorWithHex(@"#838383");
    [contentView addSubview:headerView];
    
    UIButton *sure = [UIFactory createBtn:@"登录-未触及状态" bTitle:@"确认" bframe:CGRectMake(contentView.vwidth-80-10, 5, 80, 30)];
    [sure addTarget:self action:@selector(surePlace) forControlEvents:UIControlEventTouchUpInside];
    sure.backgroundColor = [UIColor orangeColor];
    [sure setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    sure.layer.cornerRadius = 3;
    [headerView addSubview:sure];
    
    UIButton *cancel = [UIButton buttonWithTip:globe_cancel_str target:self selector:@selector(cancelSelectPlace)];
    cancel.frame = CGRectMake(0, sure.vtop, 80, 30);
    [cancel setTitleColor:[UIColor orangeColor] forState:UIControlStateNormal];
    [headerView addSubview:cancel];

    picker = [[UIPickerView alloc] initWithFrame: CGRectMake(0, 40, 320, 240)];
    picker.dataSource = self;
    picker.delegate = self;
    picker.showsSelectionIndicator = YES;
    [picker selectRow: 0 inComponent: 0 animated: YES];
    
    
    [contentView addSubview:picker];
    
    return contentView;
}

- (void)surePlace {
    NSInteger provinceIndex = [picker selectedRowInComponent: PROVINCE_COMPONENT];
    NSInteger cityIndex = [picker selectedRowInComponent: CITY_COMPONENT];
    NSInteger districtIndex = [picker selectedRowInComponent: DISTRICT_COMPONENT];
    
    AreaModel *provinces = [province objectAtIndex: provinceIndex];
    
    AreaModel *citys;
    if (city.count > cityIndex) {
        citys = [city objectAtIndex: cityIndex];
    }
    AreaModel *districts;
    if (district.count > districtIndex) {
        districts = [district objectAtIndex: districtIndex];
    }
    
    NSString *cityStr;
    if (!citys || [citys.areaName isEqualToString:@"市辖区"] || [citys.areaName isEqualToString:@"县"]) {
        cityStr = @"";
    }else {
        cityStr = citys.areaName;
    }
    
    NSString *districtStr;
    if (!districts) {
        districtStr = @"";
    }else {
        districtStr = districts.areaName;
    }
    
    NSString *areaCode;
    if (districts) {
        areaCode = districts.areaVal;
    }else if (citys) {
        areaCode = citys.areaVal;
    }else {
        areaCode = provinces.areaVal;
    }
    
    NSString *showMsg = [NSString stringWithFormat: @"%@%@%@", provinces.areaName, cityStr, districtStr];
    
    if ([self.delegate respondsToSelector:@selector(placeDidSelect:theAreaCode:)]) {
        [self.delegate placeDidSelect:showMsg theAreaCode:areaCode];
    }
    
    [_hjSheet hideSheet];
}

- (void)cancelSelectPlace {
    [_hjSheet hideSheet];
}


#pragma mark - UIPickerView DataSource
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    return 3;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    if (component == PROVINCE_COMPONENT) {
        return [province count];
    }
    else if (component == CITY_COMPONENT) {
        return [city count];
    }
    else {
        return [district count];
    }
}


#pragma mark- Picker Delegate Methods

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component
{
    if (component == PROVINCE_COMPONENT) {
        return [province objectAtIndex: row];
    }
    else if (component == CITY_COMPONENT) {
        return [city objectAtIndex: row];
    }
    else {
        return [district objectAtIndex: row];
    }
}


- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    if (component == PROVINCE_COMPONENT) {
        selectedProvince = [province objectAtIndex: row];
        
        city = [[AreaInstance sharedInstance] citysForProvinceId:selectedProvince];
        if (city.count > 0) {
            
            district = [[AreaInstance sharedInstance] regionAreasForProvince:city[0]];
        }else {
            district = nil;
        }
        [picker reloadComponent: CITY_COMPONENT];
        [picker reloadComponent: DISTRICT_COMPONENT];
        [picker selectRow: 0 inComponent: CITY_COMPONENT animated: YES];
        [picker selectRow: 0 inComponent: DISTRICT_COMPONENT animated: YES];
        
    }
    else if (component == CITY_COMPONENT) {
        
        AreaModel *seletedModel;
        if (city.count > row) {
            seletedModel = city[row];
            district = [[AreaInstance sharedInstance] regionAreasForProvince:seletedModel];
        }else {
            district = nil;
        }
        [picker selectRow: 0 inComponent: DISTRICT_COMPONENT animated: YES];
        [picker reloadComponent: DISTRICT_COMPONENT];
    }
    
}


- (CGFloat)pickerView:(UIPickerView *)pickerView widthForComponent:(NSInteger)component
{
    if (component == PROVINCE_COMPONENT) {
        return 80;
    }
    else if (component == CITY_COMPONENT) {
        return 100;
    }
    else {
        return 115;
    }
}

- (UIView *)pickerView:(UIPickerView *)pickerView viewForRow:(NSInteger)row forComponent:(NSInteger)component reusingView:(UIView *)view
{
    UILabel *myView = nil;
    UIFont *font = [UIFont boldSystemFontOfSize:15.f];
    
    if (component == PROVINCE_COMPONENT) {
        myView = [[UILabel alloc] initWithFrame:CGRectMake(0.0, 0.0, 78, 30)];
        myView.textAlignment = NSTextAlignmentCenter;
        AreaModel *model = province[row];
        myView.text = model.areaName;
        myView.font = font;
        myView.backgroundColor = [UIColor clearColor];
    }
    else if (component == CITY_COMPONENT) {
        myView = [[UILabel alloc] initWithFrame:CGRectMake(0.0, 0.0, 95, 30)];
        myView.textAlignment = NSTextAlignmentCenter;
        AreaModel *model = city[row];
        myView.text = model.areaName;
        myView.font = font;
        myView.backgroundColor = [UIColor clearColor];
    }
    else {
        myView = [[UILabel alloc] initWithFrame:CGRectMake(0.0, 0.0, 110, 30)];
        myView.textAlignment = NSTextAlignmentCenter;
        AreaModel *model = district[row];
        myView.text = model.areaName;
        myView.font = font;
        myView.backgroundColor = [UIColor clearColor];
    }
    
    return myView;
}


@end
