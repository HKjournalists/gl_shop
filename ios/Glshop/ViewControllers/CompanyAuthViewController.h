//
//  CompanyAuthViewController.h
//  Glshop
//
//  Created by River on 14-11-12.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  发布（求购/出售）信息

#import "BaseViewController.h"
#import "PublicInfoModel.h"

#define UnKnow @"unknow"

typedef NS_ENUM(NSInteger, PublicInfoType) {
    /**
     *@brief 发布新信息
     */
    public_New,
    /**
     *@brief 修改发布信息
     */
    public_Modify,
    /**
     *@brief 重新发布信息
     */
    public_Reset,
};

@interface CompanyAuthViewController : BaseViewController

@property (nonatomic, strong) NSArray *sectionsArray;

@property (nonatomic, strong,readonly) UITableView *tableView;
@property (nonatomic, assign) PublicInfoType publicInfoType;

/**
 *@brief 发布信息所有数据都在publicModel
 */
@property (nonatomic, strong) PublicInfoModel *publicModel; 

@end
