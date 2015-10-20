//
//  ContractEndedViewController.m
//  Glshop
//
//  Created by River on 14-12-9.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  已结束的合同

#import "ContractEndedViewController.h"
#import "ContractTableView.h"
#import "ContractModel.h"
#import "ContractProcessDetailViewController.h"

#define kEndTableDateKey @"kEndTableDateKey"
static NSString *const ContractEndedViewControllerTitle = @"已结束的合同";

@interface ContractEndedViewController () <UITableViewEventDelegate>

@property (nonatomic, strong) ContractTableView *tableView;

@end

@implementation ContractEndedViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(requestNet) name:kRefrushContractNotification object:nil];
    [self requestNet];
}

- (void)initDatas {
    self.isRefrushTable = YES;
}

- (void)loadSubViews {
    _tableView = [[ContractTableView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, self.view.vheight-kTopBarHeight) style:UITableViewStyleGrouped];
    __weak typeof(self) this = self;
    [_tableView addLegendHeaderWithRefreshingBlock:^{
        [this refresh:YES];
    } dateKey:kEndTableDateKey];
    _tableView.backgroundColor = self.view.backgroundColor;
    _tableView.eventDelegate = self;
    _tableView.isMore = YES;
    [self.view addSubview:_tableView];
}

- (void)requestNet {
    [super requestNet];
    _tableView.pageIndex = 1;
    [self.tableView.header beginRefreshing];
}

- (void)refresh:(BOOL)refreshControl {
    if (refreshControl) {
        _tableView.pageIndex = 1;
    }
    
    NSNumber *statusNum = @3;
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:statusNum,@"status",[NSNumber numberWithInteger:_tableView.pageIndex],@"pageIndex",@10,@"pageSize", nil];
    
    __block typeof(self) this = self;
    [self requestWithURL:bgetMyContractListWithPaginiation
                  params:params
              HTTPMethod:kHttpGetMethod
           completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        [this.tableView.header endRefreshing];
    }];
}

- (void)requestSuccessButNoData {
    if (self.shouldShowFailView && !self.failView.superview) {
        [self.view addSubview:[self failViewWithFrame:self.view.bounds expectionImgName:nil expectionTitle:@"暂无已结束合同" expectionSubTitle:nil isNodata:YES]];
    }
}

- (void)handleNetData:(id)responseData {
    NSArray *datas = responseData[ServiceDataKey];
    
    NSMutableArray *temp = [NSMutableArray array];
    for (NSDictionary *dic in datas) {
        ContractModel *model = [[ContractModel alloc] initWithDataDic:dic];
        [temp addObject:model];
    }
    
    BOOL isloadMore = _tableView.pageIndex > 1 ? YES : NO;
    if (!isloadMore && temp.count <= 0) { // 载入时，没有数据
        [self requestSuccessButNoData];
    }
    
    _tableView.isMore = temp.count < 10 ? NO : YES;
    
    _tableView.dataArray = isloadMore ? [_tableView.dataArray arrayByAddingObjectsFromArray:temp]:[NSArray arrayWithArray:temp];
    [_tableView reloadData];
    [_tableView.header endRefreshing];
}

#pragma mark - UITableViewEventDelegate
- (void)pullUp:(BaseTableView *)tableView {
    tableView.pageIndex++;
    [self refresh:NO];
}

- (void)tableView:(BaseTableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    ContractModel *model = tableView.dataArray[indexPath.section];
    ContractProcessDetailViewController *vc = [[ContractProcessDetailViewController alloc] init];
    vc.contractId = model.contractId;
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)dealloc {
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

@end
