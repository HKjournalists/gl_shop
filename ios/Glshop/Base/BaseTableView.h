//
//  BaseTableView.h
//  PromoterClient
//
//  Created by ios on 14-7-21.
//  Copyright (c) 2014年 TCL. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "EGORefreshTableHeaderView.h"
#import "ODRefreshControl.h"

@class BaseTableView;
@protocol UITableViewEventDelegate <NSObject>

@optional
// 上拉
- (void)pullUp:(BaseTableView *)tableView;
// 选中一个Cell
- (void)tableView:(BaseTableView *)tableView
didSelectRowAtIndexPath:(NSIndexPath *)indexPath;

@end

@interface BaseTableView : UITableView <UITableViewDataSource,UITableViewDelegate,EGORefreshTableHeaderDelegate>
{
    EGORefreshTableHeaderView *_freshHeaderView;
    BOOL                       _reloading;
    UIButton                   *_moreButton;
}

@property (nonatomic, strong) NSArray *dataArray;
@property (nonatomic, readonly) ODRefreshControl *refreshControl;
@property (nonatomic, assign) BOOL refreshHeader;   // 是否需要下拉
@property (nonatomic, assign) id <UITableViewEventDelegate> eventDelegate;
@property (nonatomic,assign)BOOL isMore;            // 是否还有更多下一页
@property (nonatomic, assign) NSInteger pageIndex;  

@property (nonatomic, assign) NSInteger maxLoadsCount;

- (void)doneLoadingTableViewData;

- (void)refreshData;

@end
