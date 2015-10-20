//
//  MessageViewController.m
//  Glshop
//
//  Created by River on 15-2-27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "MessageViewController.h"
#import "MessageTableView.h"
#import "MessageModel.h"
#import "MessageDetailViewController.h"
#import "MainViewController.h"

#define kMessageListDateKey @"messageListDateKey"

@interface MessageViewController () <UITableViewEventDelegate>

@property (strong, nonatomic) IBOutlet MessageTableView *tableView;
/**
 *@brief 未读消息数
 */
@property (nonatomic, assign) NSInteger unReadCount;

@end

@implementation MessageViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.title = @"消息盒子";
    self.isRefrushTable = YES;
    __block typeof(self) this = self;
    [_tableView addLegendHeaderWithRefreshingBlock:^{
        [this refrush:YES];
    } dateKey:kMessageListDateKey];
    _tableView.isMore = YES;
    _tableView.eventDelegate = self;
    
    [self requestNet];
}

- (void)requestNet {
    [super requestNet];
    
//    [self refrush:YES];
    [self.tableView.header beginRefreshing];
}

- (void)refrush:(BOOL)refreshControl {
    UserInstance *userInstance = [UserInstance sharedInstance];
    NSString *cid = userInstance.user.cid;
    if (refreshControl) {
        _tableView.pageIndex = 1;
    }
    
    if (refreshControl && self.tableView.dataArray.count > 0) {
        self.shouldShowFailView = NO;
    }else {
        self.shouldShowFailView = YES;
    }
    
    BOOL isLoadMore = _tableView.pageIndex == 1 ? NO : YES;
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:cid,path_key_companyId,@10,@"pageSize",[NSNumber numberWithInteger:_tableView.pageIndex],@"pageIndex", nil];
    
    __block typeof(self) this = self;
    [self requestWithURL:bmessageList params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        if (isLoadMore) {
            [_tableView doneLoadingTableViewData];
        }
        [_tableView.header endRefreshing];
    }];
}

- (void)handleNetData:(id)responseData {
    NSArray *datas = responseData[ServiceDataKey][@"result"];
    NSNumber *unRead = responseData[ServiceDataKey][@"resultParam"][@"unreadTotalSize"];
    _unReadCount = [unRead integerValue];
    NSMutableArray *temp = [NSMutableArray array];
    for (NSDictionary *dic in datas) {
        MessageModel *model = [[MessageModel alloc] initWithDataDic:dic];
        [temp addObject:model];
    }
    
    if (_tableView.refreshControl.refreshing) {
        [_tableView.refreshControl endRefreshing];
    }
    
    BOOL isloadMore = _tableView.pageIndex > 1 ? YES : NO;
    if (!isloadMore && temp.count <= 0) { // 刷新时，没有数据
        [self requestSuccessButNoData];
    }
    
    _tableView.pageIndex = !isloadMore ? 1 : _tableView.pageIndex;
    
    if (!isloadMore && _tableView.dataArray.count > 0) {
        self.shouldShowFailView = NO;
    }else {
        self.shouldShowFailView = YES;
    }
    
    // 每次加载10条数据
    NSInteger loadCount = 10;
    if (temp.count < loadCount) {
        _tableView.isMore = NO;
    }else {
        _tableView.isMore = YES;
    }
    
    if (isloadMore) {
        _tableView.dataArray = [_tableView.dataArray arrayByAddingObjectsFromArray:temp];
    }else {
        
        _tableView.dataArray = [NSArray arrayWithArray:temp];
    }
    
    [_tableView reloadData];
    [_tableView.header endRefreshing];
}

#pragma mark - UITableViewEventDelegate
- (void)pullUp:(BaseTableView *)tableView {
    tableView.pageIndex++;
    [self refrush:NO];
    self.shouldShowFailView = NO;
}

- (void)tableView:(BaseTableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    MessageModel *model = _tableView.dataArray[indexPath.row];
    
    MessageDetailViewController *vc = [[MessageDetailViewController alloc] init];
    vc.messageModel = model;
    
    NSInteger read = [model.status[DataValueKey] integerValue];
    __block typeof(self) this = self;
    if (read==0) {
        NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:model.messageId,@"msgids", nil];
        [self requestWithURL:bmessageMarkRead params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
            kASIResultLog;
            [this refrushBox];
        } failedBlock:^(ASIHTTPRequest *request) {
            
        }];
        [[_tableView.dataArray[indexPath.row] status] setValue:@1 forKey:DataValueKey];
        
        NSIndexPath *indexPathx = [NSIndexPath indexPathForRow:indexPath.row inSection:0];
        [_tableView reloadRowsAtIndexPaths:[NSArray arrayWithObjects:indexPathx,nil]withRowAnimation:UITableViewRowAnimationNone];
        
        
    }
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)refrushBox {
    MainViewController *vc = [self findDesignatedViewController:[MainViewController class]];
    _unReadCount -= 1;
    if (vc) {
        [vc refrushMessageBox:_unReadCount];
    }

}

@end
