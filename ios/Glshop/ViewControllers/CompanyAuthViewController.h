//
//  CompanyAuthViewController.h
//  Glshop
//
//  Created by River on 14-11-12.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  发布（求购/出售）信息

#import "BaseViewController.h"
#import "PublicInfoModel.h"

static NSString *section1_key = @"货物信息";
static NSString *section2_key = @"分类";
static NSString *section3_key = @"规格";
static NSString *section4_key = @"货物详细规格";
static NSString *section_Value = @"选择";

static NSString *section20_key = @"货物颜色";
static NSString *section21_key = @"货物产地";

#define UnKnow @"unknow"

@interface CompanyAuthViewController : BaseViewController

@property (nonatomic, strong) NSArray *sectionsArray;
@property (nonatomic, strong,readonly) UITableView *tableView;

@property (nonatomic, strong) PublicInfoModel *publicModel;  // 发布信息模型

@end
