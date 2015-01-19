//
//  BusinessViewController.m
//  Glshop
//
//  Created by River on 14-11-18.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "BusinessViewController.h"
#import "BusinessTableViewCell.h"
#import "BusinessTableView.h"
#import "PurchaseTableView.h"
#import "OrderModel.h"
#import "PublicInfoViewController.h"
#import "IndicateExtionView.h"
#import "BrowseViewController.h"
#import "BarSegment.h"

#define kHeaderViewHeight 40

@interface BusinessViewController () <UITableViewEventDelegate,BarDidSelected>

/**
 *@brief 出售列表
 */
@property (nonatomic, strong) BusinessTableView *listTableView;
/**
 *@brief 求购列表
 */
@property (nonatomic, strong) PurchaseTableView *puchaseTableView;
/**
 *@brief 下拉菜单
 */
@property (nonatomic, strong) IndicateExtionView *indicateView;
/**
 *@brief 头部控件
 */
@property (nonatomic, strong) BarSegment *segmentBar;


@end

@implementation BusinessViewController

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
    [self loadTitleMenu];
    
    // 发布信息按钮
    UIButton *filterBtn = [UIButton buttonWithTip:@"发布信息" target:self selector:nil];
    [filterBtn setImage:[UIImage imageNamed:@"Buy_sell_icon_bi"] forState:UIControlStateNormal];
    [filterBtn setTitleColor:[UIColor grayColor] forState:UIControlStateHighlighted];
    filterBtn.titleLabel.font = [UIFont systemFontOfSize:13.f];
    [filterBtn addTarget:self action:@selector(publicInfo:) forControlEvents:UIControlEventTouchUpInside];
    filterBtn.frame = CGRectMake(0, 0, 70, 54);
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithCustomView:filterBtn];
    self.navigationItem.rightBarButtonItem = rightItem;
    
    // 头部视图
    _segmentBar = [[BarSegment alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 40)];
    _segmentBar.delegate = self;
    [self.view addSubview:_segmentBar];
    

    // 求购信息列表
    _listTableView = [[BusinessTableView alloc] initWithFrame:CGRectMake(0, _segmentBar.vbottom, self.view.vwidth, self.view.vheight-_segmentBar.vheight-kTopBarHeight) style:UITableViewStyleGrouped];
    [_listTableView.refreshControl addTarget:self action:@selector(refrush:) forControlEvents:
     UIControlEventValueChanged];
    _listTableView.backgroundColor = self.view.backgroundColor;
    _listTableView.eventDelegate = self;
    [self.view addSubview:_listTableView];
    self.listTableView.contentOffset = CGPointMake(0, -44);
    [_listTableView.refreshControl beginRefreshing];

    
    // 出售信息列表
    _puchaseTableView = [[PurchaseTableView alloc] initWithFrame:CGRectMake(0, _segmentBar.vbottom, self.view.vwidth, self.view.vheight-_segmentBar.vheight-kTopBarHeight) style:UITableViewStyleGrouped];
    [_puchaseTableView.refreshControl addTarget:self action:@selector(refrush:) forControlEvents:UIControlEventValueChanged];
    _puchaseTableView.eventDelegate = self;
    if (_puchaseTableView.dataArray.count < 5) {
        _puchaseTableView.isMore = NO;
    }else {
        _puchaseTableView.isMore = YES;
    }
    [self.view addSubview:_puchaseTableView];
    _puchaseTableView.hidden = YES;
}

/**
 *@brief 下拉菜单
 */
- (void)loadTitleMenu {    
    _indicateView = [[IndicateExtionView alloc] initWithFrame:CGRectMake(self.view.vwidth/2-80, 20, 140, 44) title:@"找买找卖"];
    _indicateView.dir = listDown;
    _indicateView.weakViewController = self.navigationController;
    [self.navigationController.view addSubview:_indicateView];
    _indicateView.dataSource = @[@"全部",@"我的供",@"我的求",];
}

#pragma mark - Net
- (void)requestNet {
    [self currentTableView].hidden = YES;
    [super requestNet];
    [self refrush:nil];
    
}

- (void)refrush:(ODRefreshControl *)refrush {
    BOOL isRefresh = refrush ? YES : NO;
    BaseTableView *tableView = [self currentTableView];
    tableView.pageIndex = isRefresh ? 1 : tableView.pageIndex;
    BOOL isLoadMore = tableView.pageIndex == 1 ? NO : YES;
    NSString *businessType = _segmentBar.selctedIndex == 0 ? @"1" : @"2";
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:[NSNumber numberWithInteger:[self currentTableView].pageIndex],@"pageIndex",businessType,@"type",@5,@"pageSize", nil];
    
    if (!isLoadMore && tableView.dataArray.count <= 0) {
        isRefresh = YES;
    }
    
    if (isRefresh && tableView.dataArray.count > 0) {
        self.shouldShowFailView = NO;
    }else {
        self.shouldShowFailView = YES;
    }
    __block typeof(self) this = self;
    [self requestWithURL:bFoundOrderList
                  params:params
              HTTPMethod:kHttpGetMethod
             shouldCache:!isRefresh
           completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
        
        } failedBlock:^(ASIHTTPRequest *req){
            if (isLoadMore && this.isFirstResponder) {
                HUD(kNetError);
            }
        [[tableView refreshControl] endRefreshing];
        this.shouldShowFailView = YES;
            
    }];
}

- (void)handleNetData:(id)responseData {
    BaseTableView *tableView = [self currentTableView];
    tableView.hidden = NO;
    BOOL isLoadMore = tableView.pageIndex == 1 ? NO : YES;
    NSArray *datas = [[responseData objectForKey:ServiceDataKey] objectForKey:@"result"];
    NSMutableArray *temp = [NSMutableArray array];
    for (NSDictionary *dic in datas) {
        OrderModel *model = [[OrderModel alloc] initWithDataDic:dic];
        [temp addObject:model];
    }
    
    if (!isLoadMore && temp.count <= 0) { // 刷新时，没有数据
        [self requestSuccessButNoData];
    }
    
    if (temp.count < 5) {
        tableView.isMore = NO;
    }else {
        tableView.isMore = YES;
    }
    if (isLoadMore) {
        tableView.dataArray = [tableView.dataArray arrayByAddingObjectsFromArray:temp];
    }else {
        
        tableView.dataArray = [NSArray arrayWithArray:temp];
    }
    [tableView reloadData];
    
    [[tableView refreshControl] endRefreshing];
}

- (void)handleRequestFailed:(ASIHTTPRequest *)req {
    [self.view hideLoading];
    [self hideHUD];
    [self hideViewsWhenNoData];
    if ([self.requestArray containsObject:req]) {
        [self.requestArray removeObject:req];
    }
    if (self.shouldShowFailView && !self.failView.superview) {
        [self.view addSubview:[self failViewWithFrame:CGRectMake(0, 50, self.view.vwidth, self.view.vheight-50) empty:NO]];
    }
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


#pragma mark - barDidSelectedAt Delegate
- (void)barDidSelectedAtIndex:(NSInteger)index {
    if (index == 1) {
        _puchaseTableView.hidden = NO;
        _listTableView.hidden = YES;
        [self requestNet];
    }else if (index == 0) {
        _puchaseTableView.hidden = YES;
        _listTableView.hidden = NO;
        [self requestNet];
    }
}

#pragma mark - UIActions
- (void)publicInfo:(UIButton *)btn {
    if (![[UserInstance sharedInstance] login]) {
        HUD(@"您还没有登录");
        return;
    }
    PublicInfoViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"PublicInfoViewControllerId"];
    [self.navigationController pushViewController:vc animated:YES];
}

#pragma mark - Private
- (BaseTableView *)currentTableView {
    if (_segmentBar.selctedIndex == 0) {
        return _listTableView;
    }else if (_segmentBar.selctedIndex == 1) {
        return _puchaseTableView;
    }
    return nil;
}

@end
