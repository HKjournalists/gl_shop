//
//  PayListViewController.m
//  Glshop
//
//  Created by River on 15-1-9.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "PayListViewController.h"
#import "PayListTableView.h"
#import "IndicateExtionView.h"
#import "PayListModel.h"
#import "PayListDetailViewController.h"

@interface PayListViewController () <UITableViewEventDelegate>

@property (nonatomic, strong)  PayListTableView *payListAllTableView;
@property (nonatomic, strong)  PayListTableView *payListIncomeTableView;
@property (nonatomic, strong)  PayListTableView *payListOutTableView;

@property (nonatomic, strong) IndicateExtionView *indicateView;

@end

@implementation PayListViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
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

- (void)loadSubViews {
    _indicateView = [[IndicateExtionView alloc] initWithFrame:CGRectMake(self.view.vwidth/2-80, 20, 140, 44) title:@"收支明细"];
    _indicateView.dir = listDown;
    _indicateView.weakViewController = self.navigationController;
    [self.navigationController.view addSubview:_indicateView];
    
    _indicateView.dir = listDown;
    __block typeof(self) weakSelf = self;
    _indicateView.selectBlock = ^(NSInteger index) {
        if (index == 0) {
            weakSelf.payListAllTableView.hidden = NO;
            weakSelf.payListIncomeTableView.hidden = YES;
            weakSelf.payListOutTableView.hidden = YES;
        }else if (index == 1) {
            weakSelf.payListIncomeTableView.hidden = NO;
            weakSelf.payListAllTableView.hidden = YES;
            weakSelf.payListOutTableView.hidden = YES;
        }else {
            weakSelf.payListOutTableView.hidden = NO;
            weakSelf.payListAllTableView.hidden = YES;
            weakSelf.payListIncomeTableView.hidden = YES;
        }
        [weakSelf refrush:nil];
    };
    _indicateView.dataSource = @[@"全部",@"收入",@"支出",];
    
    // 我的供求全部列表
    _payListAllTableView = [[PayListTableView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, self.view.vheight-kTopBarHeight) style:UITableViewStylePlain];
    [_payListAllTableView.refreshControl addTarget:self action:@selector(refrush:) forControlEvents:
     UIControlEventValueChanged];
    _payListAllTableView.backgroundColor = self.view.backgroundColor;
    _payListAllTableView.eventDelegate = self;
    [self.view addSubview:_payListAllTableView];
    self.payListAllTableView.contentOffset = CGPointMake(0, -44);
    [_payListAllTableView.refreshControl beginRefreshing];
    
    // 我的求列表
    _payListIncomeTableView = [[PayListTableView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, self.view.vheight-kTopBarHeight) style:UITableViewStylePlain];
    [_payListIncomeTableView.refreshControl addTarget:self action:@selector(refrush:) forControlEvents:
     UIControlEventValueChanged];
    _payListIncomeTableView.backgroundColor = self.view.backgroundColor;
    _payListIncomeTableView.eventDelegate = self;
    _payListIncomeTableView.hidden = YES;
    [self.view addSubview:_payListIncomeTableView];
    
    // 我的供列表
    _payListOutTableView = [[PayListTableView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, self.view.vheight-kTopBarHeight) style:UITableViewStylePlain];
    [_payListOutTableView.refreshControl addTarget:self action:@selector(refrush:) forControlEvents:
     UIControlEventValueChanged];
    _payListOutTableView.hidden = YES;
    _payListOutTableView.backgroundColor = self.view.backgroundColor;
    _payListOutTableView.eventDelegate = self;
    [self.view addSubview:_payListOutTableView];
}

- (void)requestNet {
    [self refrush:nil];
}

- (void)refrush:(ODRefreshControl *)refresh {
    
    BaseTableView *currentTableView = [self currentTableView];
    BOOL isRefresh = refresh ? YES : NO;
    currentTableView.pageIndex = refresh ? 1 : currentTableView.pageIndex;
    BOOL isLoadMore = currentTableView.pageIndex == 1 ? NO : YES;
    
    if (!isLoadMore && currentTableView.dataArray.count <= 0) {
        isRefresh = YES;
    }
    
    if (isRefresh && currentTableView.dataArray.count > 0) {
        self.shouldShowFailView = NO;
    }else {
        self.shouldShowFailView = YES;
    }
    
    NSString *typeStr = _listType == pay_margin ? @"0" : @"1";
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:typeStr,@"type",[NSNumber numberWithInteger:[self currentTableView].pageIndex],@"pageIndex",@10,@"pageSize", nil];
    if (_indicateView.selectRow == 1) {
        [params setObject:@0 forKey:@"direction"];
    }else if (_indicateView.selectRow == 2) {
        [params setObject:@1 forKey:@"direction"];
    }
    __block typeof(self) this = self;
    [self requestWithURL:bgetPayRecordList
                  params:params
              HTTPMethod:kHttpGetMethod
           completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        if (isLoadMore && this.isFirstResponder) {
            HUD(kNetError);
        }
        [[currentTableView refreshControl] endRefreshing];
        this.shouldShowFailView = YES;
    }];
}

- (void)handleNetData:(id)responseData {

    BaseTableView *tableView = [self currentTableView];
    tableView.hidden = NO;
    BOOL isLoadMore = tableView.pageIndex == 1 ? NO : YES;
    NSArray *datas = [responseData objectForKey:ServiceDataKey];
    NSMutableArray *temp = [NSMutableArray array];
    for (NSDictionary *dic in datas) {
        PayListModel *model = [[PayListModel alloc] initWithDataDic:dic];
        [temp addObject:model];
    }
    
    if (!isLoadMore && temp.count <= 0) { // 刷新时，没有数据
        [self requestSuccessButNoData];
    }
    
    if (temp.count < 10) {
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

#pragma mark - Private
/**
 *@brief 获取当前表视图
 */
- (BaseTableView *)currentTableView {
    if (_payListAllTableView.hidden == NO) {
        return _payListAllTableView;
    }else if (_payListIncomeTableView.hidden == NO) {
        return _payListIncomeTableView;
    }else {
        return _payListOutTableView;
    }
}

#pragma mark - UITableViewEventDelegate
- (void)pullUp:(BaseTableView *)tableView {
    tableView.pageIndex++;
    [self refrush:nil];
    self.shouldShowFailView = NO;
}

- (void)tableView:(BaseTableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    PayListModel *model = [self currentTableView].dataArray[indexPath.row];
    PayListDetailViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"PayListDetailViewControllerId"];
    vc.payId = model.payId;
    [self.navigationController pushViewController:vc animated:YES];
}

@end
