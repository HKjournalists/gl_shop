//
//  ContractWaitSureViewController.m
//  Glshop
//
//  Created by River on 15-1-29.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  待确认合同

#import "ContractWaitSureViewController.h"
#import "ContractTableView.h"
#import "ContractModel.h"
#import "ContractDetailViewController.h"
#import "MypurseViewController.h"
#import "MyContractViewController.h"

#define kWaitSureTableDateKey @"kWaitSureTableDateKey"

static NSString *const ContractWaitSureViewControllerTitle = @"待确认的合同";

@interface ContractWaitSureViewController () <UITableViewEventDelegate,UIAlertViewDelegate>

@property (nonatomic, strong) ContractTableView *tableView;
@property (nonatomic, strong) NSTimer *timer;

@end

@implementation ContractWaitSureViewController

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
    } dateKey:kWaitSureTableDateKey];
    _tableView.backgroundColor = self.view.backgroundColor;
    _tableView.eventDelegate = self;
    [self.view addSubview:_tableView];

}

- (void)requestNet {
    [super requestNet];
    _tableView.pageIndex = 1;
//    [self refresh:YES];
    [self.tableView.header beginRefreshing];
}

- (void)refresh:(BOOL)refreshControl {
    if (refreshControl) {
        _tableView.pageIndex = 1;
    }
    
    NSNumber *statusNum = @0;
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:statusNum,@"status",[NSNumber numberWithInteger:_tableView.pageIndex],@"pageIndex",@10,@"pageSize", nil];
    
    __weak typeof(self) this = self;
    [self requestWithURL:bgetMyContractListWithPaginiation params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        [this.tableView.header endRefreshing];
    }];
}

- (void)tipErrorCode:(NSInteger)errorCode {
    if (errorCode == 100003001) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"保证金不足" message:@"请您缴纳足够的保证金后再来确认合同。" delegate:self cancelButtonTitle:@"取消" otherButtonTitles:@"去缴纳", nil];
        [alert show];
    }else {
        [super tipErrorCode:errorCode];
    }
}

- (void)handleNetData:(id)responseData {
    NSArray *datas = responseData[ServiceDataKey];
    
    [self fireRefrush];
    
    NSMutableArray *temp = [NSMutableArray array];
    for (NSDictionary *dic in datas) {
        ContractModel *model = [[ContractModel alloc] initWithDataDic:dic];
        [temp addObject:model];
    }
    
    BOOL isloadMore = _tableView.pageIndex > 1 ? YES : NO;
    if (!isloadMore && temp.count <= 0) { // 刷新时，没有数据
        [self requestSuccessButNoData];
    }
    
    _tableView.isMore = temp.count < 10 ? NO : YES;
    _tableView.dataArray = isloadMore ? [_tableView.dataArray arrayByAddingObjectsFromArray:temp]:[NSArray arrayWithArray:temp];
    [_tableView reloadData];
    
    [_tableView.header endRefreshing];
    
    
}

- (void)requestSuccessButNoData {
    if (self.shouldShowFailView && !self.failView.superview) {
        [self.view addSubview:[self failViewWithFrame:self.view.bounds expectionImgName:nil expectionTitle:nil expectionSubTitle:alert_tip_no_waitsureContract isNodata:YES]];
    }
}


- (void)pullUp:(BaseTableView *)tableView {
    tableView.pageIndex++;
    [self refresh:NO];
}

- (void)tableView:(BaseTableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    ContractModel *model = _tableView.dataArray[indexPath.section];
    ContractDetailViewController *vc = [[ContractDetailViewController alloc ] init];
    vc.title = ContractWaitSureViewControllerTitle;
    vc.contractId = model.contractId;
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex) {
        MypurseViewController *vc = [[MypurseViewController alloc] init];
        [self.navigationController pushViewController:vc animated:YES];
    }
}

- (void)fireRefrush {
    BOOL shouldRefrush = NO;
    if (_tableView.dataArray.count <= 0) {
        shouldRefrush = NO;
    }else {
        for (ContractModel *model in _tableView.dataArray) {
            if ([model contractValide]) {
                shouldRefrush = YES;
                break;
            }
        }
    }
    
    if (shouldRefrush) {
        _tableView.pageIndex = 1;
        [NSTimer scheduledTimerWithTimeInterval:60 target:self selector:@selector(timerFireMethod:) userInfo:[NSNumber numberWithBool:YES] repeats:NO];
    }

}

- (void)timerFireMethod:(NSTimer *)timer {
    if ([self.topViewController isKindOfClass:[ MyContractViewController class]]) {
        NSNumber *number = timer.userInfo;
        [self refresh:[number boolValue]];
    }
    [timer invalidate];
}

- (void)dealloc {
    [[NSNotificationCenter defaultCenter] removeObserver:self];
    [_timer invalidate];
}


@end
