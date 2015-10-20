//
//  ContractDetailView.h
//  Glshop
//
//  Created by River on 15-2-3.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

@class ContractProductView,ContractModel;
@interface ContractDetailView : UIView <UITableViewDataSource,UITableViewDelegate,UIScrollViewDelegate>
{
@private
    UIView          *_headerView;
    UIButton        *_statusBtn;
    UILabel         *_productNameLabel;
    UIScrollView    *_controlScrollView;
    UITableView     *_contractTableView;
    ContractProductView *_productView;
}

@property (nonatomic, strong) ContractModel *contractModel;

@end
