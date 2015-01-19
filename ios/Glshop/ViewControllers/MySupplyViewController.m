//
//  MySupplyViewController.m
//  Glshop
//
//  Created by River on 14-11-18.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "MySupplyViewController.h"
#import "SupplyTableView.h"
#import "OrderModel.h"
#import "IndicateExtionView.h"
#import "PublicInfoViewController.h"
#import "PublicInfoModel.h"
#import "CompanyAuthViewController.h"
#import "BrowseViewController.h"

#define kHeaderViewHeight 40

@interface MySupplyViewController () <UITableViewEventDelegate>

@property (nonatomic, strong) IndicateExtionView *indicateView;

@property (nonatomic, strong)  SupplyTableView *supplyAllTableView;
@property (nonatomic, strong)  SupplyTableView *supplyBuyTableView;
@property (nonatomic, strong)  SupplyTableView *supplySellTableView;

@end

@implementation MySupplyViewController

- (void)viewDidLoad {
    [super viewDidLoad];
   
    self.shouldShowFailView = YES;
    [self requestNet];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    _indicateView.hidden = NO;
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    _indicateView.hidden = YES;

}

#pragma mark - UI
- (void)loadSubViews {
    _indicateView = [[IndicateExtionView alloc] initWithFrame:CGRectMake(self.view.vwidth/2-80, 20, 140, 44) title:@"我的供求"];
    _indicateView.dir = listDown;
    __block typeof(self) weakSelf = self;
    _indicateView.selectBlock = ^(NSInteger index) {
        if (index == 0) {
            weakSelf.supplyAllTableView.hidden = NO;
            weakSelf.supplyBuyTableView.hidden = YES;
            weakSelf.supplySellTableView.hidden = YES;
        }else if (index == 1) {
            weakSelf.supplyBuyTableView.hidden = NO;
            weakSelf.supplySellTableView.hidden = YES;
            weakSelf.supplyAllTableView.hidden = YES;
        }else {
            weakSelf.supplySellTableView.hidden = NO;
            weakSelf.supplyBuyTableView.hidden = YES;
            weakSelf.supplyAllTableView.hidden = YES;
        }
        [weakSelf requestNet];
    };
    _indicateView.weakViewController = self.navigationController;
    [self.navigationController.view addSubview:_indicateView];
    _indicateView.dataSource = @[@"全部",@"我的供",@"我的求",];
    
    // 发布信息按钮
    UIButton *filterBtn = [UIButton buttonWithTip:@"发布信息" target:self selector:nil];
    [filterBtn setImage:[UIImage imageNamed:@"Buy_sell_icon_bi"] forState:UIControlStateNormal];
    [filterBtn setTitleColor:[UIColor grayColor] forState:UIControlStateHighlighted];
    filterBtn.titleLabel.font = [UIFont systemFontOfSize:13.f];
    [filterBtn addTarget:self action:@selector(publicInfo:) forControlEvents:UIControlEventTouchUpInside];
    filterBtn.frame = CGRectMake(0, 0, 70, 54);
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithCustomView:filterBtn];
    self.navigationItem.rightBarButtonItem = rightItem;
    
    // 我的供求全部列表
    _supplyAllTableView = [[SupplyTableView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, self.view.vheight-kTopBarHeight) style:UITableViewStyleGrouped];
    [_supplyAllTableView.refreshControl addTarget:self action:@selector(refrush:) forControlEvents:
     UIControlEventValueChanged];
    _supplyAllTableView.backgroundColor = self.view.backgroundColor;
    _supplyAllTableView.eventDelegate = self;
    [self.view addSubview:_supplyAllTableView];
    self.supplyAllTableView.contentOffset = CGPointMake(0, -44);
    [_supplyAllTableView.refreshControl beginRefreshing];
    
    // 我的求列表
    _supplyBuyTableView = [[SupplyTableView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, self.view.vheight-kTopBarHeight) style:UITableViewStyleGrouped];
    [_supplyBuyTableView.refreshControl addTarget:self action:@selector(refrush:) forControlEvents:
     UIControlEventValueChanged];
    _supplyBuyTableView.backgroundColor = self.view.backgroundColor;
    _supplyBuyTableView.eventDelegate = self;
    _supplyBuyTableView.hidden = YES;
    [self.view addSubview:_supplyBuyTableView];

    // 我的供列表
    _supplySellTableView = [[SupplyTableView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, self.view.vheight-kTopBarHeight) style:UITableViewStyleGrouped];
    [_supplySellTableView.refreshControl addTarget:self action:@selector(refrush:) forControlEvents:
     UIControlEventValueChanged];
    _supplySellTableView.hidden = YES;
    _supplySellTableView.backgroundColor = self.view.backgroundColor;
    _supplySellTableView.eventDelegate = self;
    [self.view addSubview:_supplySellTableView];

}

#pragma mark - Net
- (void)requestNet {
    [super requestNet];
    [self refrush:nil];
}

/**
 *@brief 下拉刷新
 */
- (void)refrush:(ODRefreshControl *)refreshControl {

    BaseTableView *currentTableView = [self currentTableView];
    BOOL useCache = refreshControl ? NO : YES;
    if (refreshControl) {
        currentTableView.pageIndex = 1;
    }
    
    BOOL isloadMore = currentTableView.pageIndex > 1 ? YES : NO;
    if (!isloadMore && currentTableView.dataArray.count <= 0) {
        useCache = NO;
    }
    
    if (refreshControl && currentTableView.dataArray.count > 0 ) {
        self.shouldShowFailView = NO;
    }else {
        self.shouldShowFailView = YES;
    }
    
    UserInstance *userInstance = [UserInstance sharedInstance];
    NSString *cid = userInstance.user.cid;
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:[NSNumber numberWithInteger:currentTableView.pageIndex],@"pageIndex",@5,@"pageSize",cid,@"cid",nil];
    if ([self currentTableView] == _supplyBuyTableView) {
        [params addInteger:1 forKey:@"type"];
    }else if ([self currentTableView] == _supplySellTableView) {
        [params addInteger:2 forKey:@"type"];
    }
    [self requestWithURL:bGetMyList params:params HTTPMethod:kHttpGetMethod shouldCache:useCache needHeader:YES completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [currentTableView.refreshControl endRefreshing];
        NSArray *datas = [[responseData objectForKey:ServiceDataKey] objectForKey:@"result"];
        NSMutableArray *temp = [NSMutableArray array];
        for (NSDictionary *dic in datas) {
            OrderModel *model = [[OrderModel alloc] initWithDataDic:dic];
            [temp addObject:model];
        }
        
        if (!isloadMore && temp.count <= 0) {
            [self requestSuccessButNoData];
        }
        
        if (temp.count < 5) {
            currentTableView.isMore = NO;
        }else {
            currentTableView.isMore = YES;
        }
        if (isloadMore) {
            currentTableView.dataArray = [currentTableView.dataArray arrayByAddingObjectsFromArray:temp];
        }else {
            
            currentTableView.dataArray = [NSArray arrayWithArray:temp];
        }
        [currentTableView reloadData];
        
    } failedBlock:^ (ASIHTTPRequest *req){
        [currentTableView.refreshControl endRefreshing];
        if (isloadMore || refreshControl) {
            HUD(kNetError);
        }
        self.shouldShowFailView = YES;
    }];
}

#pragma mark - Private
/**
 *@brief 获取当前表视图
 */
- (BaseTableView *)currentTableView {
    if (_supplyAllTableView.hidden == NO) {
        return _supplyAllTableView;
    }else if (_supplyBuyTableView.hidden == NO) {
        return _supplyBuyTableView;
    }else {
        return _supplySellTableView;
    }
}

#pragma mark - UIActions
- (void)publicInfo:(UIButton *)button {
    PublicInfoViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"PublicInfoViewControllerId"];
    [self.navigationController pushViewController:vc animated:YES];
}

#pragma mark - UITableViewEventDelegate 
- (void)pullUp:(BaseTableView *)tableView {
    tableView.pageIndex++;
    [self refrush:nil];
    self.shouldShowFailView = NO;
}

- (void)tableView:(BaseTableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    OrderModel *model = [self currentTableView].dataArray[indexPath.section];
    NSInteger orderStatus = [model.status[DataValueKey] integerValue];
    BrowseViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"BrowseViewControllerId"];
    vc.title = @"供求详细";
    vc.orderStatus = orderStatus;
    vc.orderId = model.id;
    [self.navigationController pushViewController:vc animated:YES];
}

@end
