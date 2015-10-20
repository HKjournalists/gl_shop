//
//  CommentViewController.m
//  Glshop
//
//  Created by River on 15-1-21.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  评论

#import "CommentViewController.h"
#import "CommentTableView.h"
#import "CommentModel.h"

@interface CommentViewController ()

@property (nonatomic, strong) CommentTableView *tableView;

@end

@implementation CommentViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"交易评价";
    [self requestNet];
}

-(void)viewDidLayoutSubviews
{
    if ([self.tableView respondsToSelector:@selector(setSeparatorInset:)]) {
        [self.tableView setSeparatorInset:UIEdgeInsetsMake(0,15,0,0)];
    }
    
    if ([self.tableView respondsToSelector:@selector(setLayoutMargins:)]) {
        [self.tableView setLayoutMargins:UIEdgeInsetsMake(0,15,0,0)];
    }
}

- (void)loadSubViews {
    _tableView = [[CommentTableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vheight -= kTopBarHeight;
    _tableView.sectionFooterHeight = 5;
    _tableView.sectionHeaderHeight = 5;
    _tableView.contentInset = UIEdgeInsetsMake(-20, 0, 0, 0);
    [_tableView.refreshControl addTarget:self action:@selector(refrush:) forControlEvents:UIControlEventValueChanged];
    [self.view addSubview:_tableView];
}

- (void)requestNet {
    [super requestNet];
    
    [self refrush:nil];
}

- (void)refrush:(ODRefreshControl *)refrush {
    NSString *checkId = _checkStyle == contractCheck ? _contractId : _cid;
    NSString *style = _checkStyle == contractCheck ? @"1":@"0";
    __block typeof(self) this = self;
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:checkId,@"ID",style,@"type", nil];
    [self requestWithURL:bnoAuthUrlgetEvaluationContractList params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        if (this.tableView.refreshControl.refreshing) {
            [this.tableView.refreshControl endRefreshing];
        }
    }];
}

- (void)handleNetData:(id)responseData {
    NSArray *dataDics = responseData[ServiceDataKey];
    NSMutableArray *temp = [NSMutableArray arrayWithCapacity:dataDics.count];
    for (NSDictionary *dic in dataDics) {
        CommentModel *model = [[CommentModel alloc] initWithDataDic:dic];
        [temp addObject:model];
    }
    _tableView.dataArray = [NSArray arrayWithArray:temp];
    [_tableView reloadData];
    
    BOOL isloadMore = _tableView.pageIndex > 1 ? YES : NO;
    if (!isloadMore && temp.count <= 0) { // 载入时，没有数据
        [self requestSuccessButNoData];
    }
    
    if (self.tableView.refreshControl.refreshing) {
        [self.tableView.refreshControl endRefreshing];
    }
}

@end
