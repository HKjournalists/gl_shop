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
 *@brief 是否是编辑发布信息。YES:修改发布信息 NO:填写发布信息
 */
//@property (nonatomic, assign) BOOL isEditor;

/**
 *@brief 是否是重新发布。
 *@discussion 重新发布不带id 
 */
//@property (nonatomic, assign) BOOL isRePublic;

/**
 *@brief 发布信息所有数据都在publicModel
 */
@property (nonatomic, strong) PublicInfoModel *publicModel; 

@end
