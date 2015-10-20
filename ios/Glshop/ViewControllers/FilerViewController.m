//
//  FilerViewController.m
//  Glshop
//
//  Created by River on 15-3-16.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "FilerViewController.h"
#import "BusinessViewController.h"
#import "FilerView.h"

@interface FilerViewController ()

@end

@implementation FilerViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"筛选";
}

- (void)loadSubViews {
    _filerView = [[FilerView alloc] initWithFrame:self.view.bounds];
    [self.view addSubview:_filerView];
    
    UIBarButtonItem *item1 = [[UIBarButtonItem alloc] initWithTitle:@"重置" style:UIBarButtonItemStylePlain target:self action:@selector(reSetFilerData)];
    
    UIBarButtonItem *item2 = [[UIBarButtonItem alloc] initWithTitle:@"确定" style:UIBarButtonItemStylePlain target:self action:@selector(filerAction)];
    self.navigationItem.rightBarButtonItems = @[item2,item1];
}

/**
 *@brief 重置筛选数据
 */
- (void)reSetFilerData {
    [_filerView resetFilerData];
}

/**
 *@brief 进行筛选
 */
- (void)filerAction {
    // 产品
    NSString *produtStr = [_filerView gatherFilerProductData];
    BusinessViewController *vc = [self findDesignatedViewController:[BusinessViewController class]];
    if (produtStr.length) {
        [vc.requestParams addString:produtStr forKey:@"pids"];
    }else {
        [vc.requestParams removeObjectForKey:@"pids"];
    }
    
    // 省、区
    NSString *provinceStr = [_filerView gatherFilerProvinceData];
    if (provinceStr.length) {
        [vc.requestParams addString:provinceStr forKey:@"areaCodeProvince"];
    }else {
        [vc.requestParams removeObjectForKey:@"areaCodeProvince"];
    }
    
    NSString *regionsStr = [_filerView gatherFilerRegionData];
    if (regionsStr.length) {
        [vc.requestParams addString:regionsStr forKey:@"areaCodeArea"];
    }else {
        [vc.requestParams removeObjectForKey:@"areaCodeArea"];
    }
    
    // 时间
    NSString *startTime = [[NSUserDefaults standardUserDefaults] objectForKey:kUserDefalutsKeyStartTime];
    NSString *endTime = [[NSUserDefaults standardUserDefaults] objectForKey:kUserDefalutsKeyEndTime];
    if (_filerView.timeView.allTimeBox.selected) {
        [vc.requestParams removeObjectForKey:@"startTime"];
        [vc.requestParams removeObjectForKey:@"endTime"];
    }else if (startTime.length && endTime.length) {
        NSTimeInterval t = [[Utilits dateFromFomate:startTime formate:kTimeFormart] timeIntervalSinceDate:[Utilits dateFromFomate:endTime formate:kTimeFormart]];
        if (t > 0) {
            [self showTip:@"开始时间不能大于结束时间"];
            return;
        }
        [vc.requestParams addString:startTime forKey:@"startTime"];
        [vc.requestParams addString:endTime forKey:@"endTime"];
    }else {
        [self showTip:@"请选择筛选时间"];
        return;
    }
    
    [vc requestNet];
    [self.navigationController popViewControllerAnimated:YES];
}

@end
