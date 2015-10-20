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
#import "IBActionSheet.h"

@interface PayListViewController () <UITableViewEventDelegate,IBActionSheetDelegate>

@property (nonatomic, strong)  PayListTableView *payListAllTableView;
@property (nonatomic, strong)  PayListTableView *payListIncomeTableView;
@property (nonatomic, strong)  PayListTableView *payListOutTableView;

@property (nonatomic, strong) IndicateExtionView *indicateView;

/**
 *@brief 为IBActionSheet记录选择的索引，默认为0
 */
@property (nonatomic, assign) NSInteger markIndex;
@property (nonatomic, strong) IBActionSheet *sheet;

@property (nonatomic, strong) UIButton *titleViewBtn;

@end

@implementation PayListViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"收支明细";
    [self requestNet];
}

- (void)setTitle:(NSString *)title {
    [super setTitle:title];
    
    _titleViewBtn = [UIButton buttonWithTip:@"收支明细" target:self selector:@selector(showSheet:)];
    [_titleViewBtn setImage:[UIImage imageNamed:@"supply-and-demand_icon_on"] forState:UIControlStateNormal];
    [_titleViewBtn setTitleColor:[UIColor grayColor] forState:UIControlStateHighlighted];
    [_titleViewBtn setImageEdgeInsets:UIEdgeInsetsMake(0, 90, 0, 0)];
    [_titleViewBtn setTitleEdgeInsets:UIEdgeInsetsMake(0, -30, 0, 0)];
    _titleViewBtn.frame = CGRectMake(0, 0, 120, 44);
    self.navigationItem.titleView = _titleViewBtn;
}

- (void)showSheet:(UIButton *)button {
    if (_sheet.visible) {
        return;
    }
    
    [self indicateArrow];
    [_sheet showInView:self.view];
}
- (void)loadSubViews {
    
    _sheet = [[IBActionSheet alloc] initWithTitle:@"选择分类" delegate:self cancelButtonTitle:globe_cancel_str destructiveButtonTitle:nil otherButtonTitlesArray:@[@"全部",@"收入",@"支出",]];
    _sheet.markIndex = _markIndex;
    
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
    [super requestNet];
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
    
    NSString *typeStr = _listType == pay_margin ? @"0" : @"1";
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:typeStr,@"type",[NSNumber numberWithInteger:[self currentTableView].pageIndex],@"pageIndex",@10,@"pageSize", nil];
    if (_markIndex == 1) {
        [params setObject:@0 forKey:@"direction"];
    }else if (_markIndex == 2) {
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
        [[currentTableView refreshControl] endRefreshing];
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

#pragma mark -
- (void)actionSheet:(IBActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex == 0) {
        self.payListAllTableView.hidden = NO;
        self.payListIncomeTableView.hidden = YES;
        self.payListOutTableView.hidden = YES;
    }else if (buttonIndex == 1) {
        self.payListIncomeTableView.hidden = NO;
        self.payListAllTableView.hidden = YES;
        self.payListOutTableView.hidden = YES;
    }else if (buttonIndex == 2){
        self.payListOutTableView.hidden = NO;
        self.payListIncomeTableView.hidden = YES;
        self.payListAllTableView.hidden = YES;
    }
    if (actionSheet.cancelButtonIndex != buttonIndex) {
        _markIndex = buttonIndex;
        [self refrush:nil];
    }
}

- (void)actionWillDismiss:(IBActionSheet *)actionSheet {
    [self indicateArrow];
}

static int selectFlag = 0;
- (void)indicateArrow {
    float duration = 0.25;
    if (selectFlag) {
        selectFlag = 0;
        [UIView animateWithDuration:duration animations:^{
            [_titleViewBtn.imageView.layer setTransform:CATransform3DIdentity];
        }];
    }else {
        selectFlag = 1;
        [UIView animateWithDuration:duration animations:^{
            [_titleViewBtn.imageView.layer setTransform:CATransform3DMakeRotation(-M_PI/1.0000001, 0, 0, 1)];
        }];
    }
}

@end
