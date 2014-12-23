//
//  BusinessViewController.m
//  Glshop
//
//  Created by River on 14-11-18.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "BusinessViewController.h"
#import "IGLDropDownMenu.h"
#import "IGLDropDownItem.h"
#import "BusinessTableViewCell.h"
#import "BusinessTableView.h"
#import "PurchaseTableView.h"
#import "OrderModel.h"
#import "FilterControl.h"
#import "PublicInfoViewController.h"
#import "IndicateExtionView.h"

#define kHeaderViewHeight 40

@interface BusinessViewController () <IGLDropDownMenuDelegate,UITableViewEventDelegate>

@property (nonatomic, strong) IGLDropDownMenu *dropDownMenu;
@property (nonatomic, strong) UISegmentedControl *segment;
@property (nonatomic, strong) BusinessTableView *listTableView;
@property (nonatomic, strong) IndicateExtionView *indicateView;

Strong FilterControl *filterControl;
Strong PurchaseTableView *puchaseTableView;

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
    
    // 筛选按钮
    UIButton *filterBtn = [UIButton buttonWithTip:@"筛选" target:self selector:nil];
    [filterBtn setImage:[UIImage imageNamed:@"Buy_sell_icon_screen"] forState:UIControlStateNormal];
    [filterBtn setTitleColor:[UIColor grayColor] forState:UIControlStateHighlighted];
    [filterBtn addTarget:self action:@selector(filterProduct:) forControlEvents:UIControlEventTouchUpInside];
    filterBtn.frame = CGRectMake(0, 0, 50, 54);
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithCustomView:filterBtn];
    self.navigationItem.rightBarButtonItem = rightItem;
    
    // 头部视图
    UIView *headerView = [[UIView alloc] initWithFrame:CGRectMake(0, 5, self.view.vwidth, kHeaderViewHeight)];
    headerView.backgroundColor = [UIColor clearColor];
    [self.view addSubview:headerView];
    
    _segment = [[UISegmentedControl alloc] initWithItems:@[buyInfo,sellInfo]];
    _segment.frame = CGRectMake(10, 5, 180, 30);
    [_segment setTintColor:[UIColor orangeColor]];
    [_segment setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:
                                      [UIColor orangeColor], NSForegroundColorAttributeName,
                                      [UIFont systemFontOfSize:17.f], NSFontAttributeName,
                                      nil] forState:UIControlStateNormal];
    [_segment addTarget:self action:@selector(choseBusinessType:) forControlEvents:UIControlEventValueChanged];
    _segment.selectedSegmentIndex = 0;
    [headerView addSubview:_segment];
    
    UIButton *publicMessageBtn = [UIButton buttonWithTip:@"发布信息" target:self selector:@selector(publicInfo:)];
    [publicMessageBtn setImage:[UIImage imageNamed:@"Buy_sell_icon_publish"] forState:UIControlStateNormal];
    publicMessageBtn.frame = CGRectMake(SCREEN_WIDTH-100-15, _segment.vtop, 100, _segment.vheight);
    [publicMessageBtn setBackgroundImage:[UIImage imageNamed:@"Buy_sell_publish"] forState:UIControlStateNormal];
    [publicMessageBtn setTitleColor:ColorWithHex(@"#646464") forState:UIControlStateNormal];
    [headerView addSubview:publicMessageBtn];

    // 求购信息列表
    _listTableView = [[BusinessTableView alloc] initWithFrame:CGRectMake(0, headerView.vbottom, self.view.vwidth, self.view.vheight-headerView.vheight-kTopBarHeight) style:UITableViewStyleGrouped];
    [_listTableView.refreshControl addTarget:self action:@selector(refrush:) forControlEvents:
     UIControlEventValueChanged];
    _listTableView.backgroundColor = self.view.backgroundColor;
    _listTableView.eventDelegate = self;
    [self.view addSubview:_listTableView];
    self.listTableView.contentOffset = CGPointMake(0, -44);
    [_listTableView.refreshControl beginRefreshing];

    
    // 出售信息列表
    _puchaseTableView = [[PurchaseTableView alloc] initWithFrame:CGRectMake(0, headerView.vbottom, self.view.vwidth, self.view.vheight-headerView.vheight-kTopBarHeight) style:UITableViewStyleGrouped];
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
//    NSArray *dataArray = @[@"按最低价格筛选",@"按最低价格筛选",@"按最低价格筛选",@"按最低价格筛选",@"按最低价格筛选",@"按最低价格筛选"];
//    NSMutableArray *dropdownItems = [[NSMutableArray alloc] init];
//    for (int i = 0; i < dataArray.count; i++) {
//        
//        IGLDropDownItem *item = [[IGLDropDownItem alloc] init];
//        [item setText:dataArray[i]];
//        [dropdownItems addObject:item];
//    }
//    
//    self.dropDownMenu = [[IGLDropDownMenu alloc] init];
//    self.dropDownMenu.weakViewController = self.navigationController;
//    self.dropDownMenu.menuText = @"找买找卖";
//    self.dropDownMenu.paddingLeft = -10;
//    self.dropDownMenu.dropDownItems = dropdownItems;
//    self.dropDownMenu.paddingLeft = -10;
//    [self.dropDownMenu setFrame:CGRectMake(SCREEN_WIDTH/2-80, 20, 160, 44.5)];
//    self.dropDownMenu.delegate = self;
//    self.dropDownMenu.gutterY = -0.5;
//    self.dropDownMenu.type = IGLDropDownMenuTypeSlidingInBoth;
//    self.dropDownMenu.itemAnimationDelay = 0.1;
//    [self.navigationController.view addSubview:self.dropDownMenu];
//    
//    [self.dropDownMenu reloadView];
    
    _indicateView = [[IndicateExtionView alloc] initWithFrame:CGRectMake(self.view.vwidth/2-80, 20, 160, 44) title:@"找买找卖"];
    _indicateView.dir = listDown;
    _indicateView.weakViewController = self.navigationController;
    [self.navigationController.view addSubview:_indicateView];
    _indicateView.dataSource = @[@"全部",@"我的供",@"我的求",];
}

#pragma mark - Net
- (void)requestNet {
    [self refrush:nil];
    
}

- (void)refrush:(ODRefreshControl *)refrush {
    BOOL isRefresh = refrush ? YES : NO;
    [self currentTableView].pageIndex = isRefresh ? 1 : [self currentTableView].pageIndex;
    BOOL isLoadMore = isRefresh ? NO : YES;
    NSString *businessType = _segment.selectedSegmentIndex == 0 ? @"1" : @"2";
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:[NSNumber numberWithInteger:[self currentTableView].pageIndex],@"pageIndex",businessType,@"type",@5,@"pageSize", nil];
    BaseTableView *tableView = [self currentTableView];
    [self requestWithURL:bFoundOrderList params:params HTTPMethod:kHttpGetMethod shouldCache:!isRefresh completeBlock:^(ASIHTTPRequest *request, id responseData) {
        NSArray *datas = [[responseData objectForKey:@"DATA"] objectForKey:@"result"];
        NSMutableArray *temp = [NSMutableArray array];
        for (NSDictionary *dic in datas) {
            OrderModel *model = [[OrderModel alloc] initWithDataDic:dic];
            [temp addObject:model];
        }
        if (temp.count < 5) {
            [self currentTableView].isMore = NO;
        }else {
            [self currentTableView].isMore = YES;
        }
        if (isLoadMore) {
        tableView.dataArray = [tableView.dataArray arrayByAddingObjectsFromArray:temp];
        }else {
            
            tableView.dataArray = [NSArray arrayWithArray:temp];
        }
        [tableView reloadData];
        
        [[[self currentTableView] refreshControl] endRefreshing];
        
    } failedBlock:^{
        [[[self currentTableView] refreshControl] endRefreshing];
    }];
}

#pragma mark - UITableViewEventDelegate
- (void)pullUp:(BaseTableView *)tableView {
    tableView.pageIndex++;
    [self refrush:nil];
}

- (void)tableView:(BaseTableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    NSLog(@"%ld",(long)indexPath.section);
}


#pragma mark - IGLDropDownMenuDelegate
- (void)selectedItemAtIndex:(NSInteger)index {
    
}

#pragma mark - UIActions
- (void)choseBusinessType:(UISegmentedControl *)segment {
    if (segment.selectedSegmentIndex == 1) {
        _puchaseTableView.hidden = NO;
        _listTableView.hidden = YES;
    }else {
        _puchaseTableView.hidden = YES;
        _listTableView.hidden = NO;
    }
    [self requestNet];
}

- (void)filterProduct:(UIButton *)btn {
    _filterControl = [FilterControl new];
    _filterControl.weakViewConterller = self.navigationController;
    [_filterControl showFilter];
}

- (void)publicInfo:(UIButton *)btn {
    PublicInfoViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"PublicInfoViewControllerId"];
    [self.navigationController pushViewController:vc animated:YES];
    self.navigationItem.backBarButtonItem = [[UIBarButtonItem alloc]
                                             initWithTitle:@"返回"
                                             style:UIBarButtonItemStylePlain
                                             target:self
                                             action:nil];
}

#pragma mark - Private
- (BaseTableView *)currentTableView {
    return _segment.selectedSegmentIndex == 0 ? _listTableView : _puchaseTableView;
}

#pragma mark - Override
- (void)handleRequestFailed {
    if (self.shouldShowFailView && !self.failView.superview) {
        [self.view addSubview:[self failViewWithFrame:CGRectMake(0, kHeaderViewHeight, self.view.vwidth, self.view.vheight-kHeaderViewHeight-kTopBarHeight) empty:NO]];
    }

}

@end
