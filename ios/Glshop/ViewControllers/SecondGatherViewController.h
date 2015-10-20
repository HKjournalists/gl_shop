//
//  SecondGatherViewController.h
//  Glshop
//
//  Created by River on 15-1-13.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "BaseViewController.h"

@interface SecondGatherViewController : BaseViewController

/**
 *@brief 所选银行
 */
@property (nonatomic, strong) BankModel *selectBank;

@property (nonatomic, strong, readonly) UITableView *tableView;

/**
 *@brief 开户人姓名
 */
@property (nonatomic, copy) NSString *name;
/**
 *@brief 认证图片id
 */
@property (nonatomic, copy) NSString *imgId;

@end
