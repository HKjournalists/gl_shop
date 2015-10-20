//
//  UnPayViewController.m
//  Glshop
//
//  Created by River on 15-3-2.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  待付货款合同列表

#import "UnPayViewController.h"
#import "UnPayTableView.h"
#import "ContractModel.h"
#import "ContractProcessDetailViewController.h"

@interface UnPayViewController () <UITableViewEventDelegate>

@property (strong, nonatomic) IBOutlet UnPayTableView *tableView;

@end

@implementation UnPayViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.title = @"待付货款合同列表";
    
    _tableView.contentInset = UIEdgeInsetsMake(-20, 0, 0, 0);
    _tableView.eventDelegate = self;
    [_tableView.refreshControl addTarget:self action:@selector(refrush:) forControlEvents:UIControlEventValueChanged];
    [self requestNet];
}

- (void)requestNet {
    [super requestNet];
    
    [self refrush:nil];
}

- (void)handleNetData:(id)responseData {
    NSArray *dataDics = responseData[ServiceDataKey];
    NSMutableArray *temp = [NSMutableArray arrayWithCapacity:dataDics.count];
    for (NSDictionary *dic in dataDics) {
        ContractModel *model = [[ContractModel alloc] initWithDataDic:dic];
        [temp addObject:model];
    }
    _tableView.dataArray = [NSArray arrayWithArray:temp];
    [_tableView.refreshControl endRefreshing];
    [_tableView reloadData];
}

- (void)refrush:(ODRefreshControl *)control {
    __block typeof(self) this = self;
    self.shouldShowFailView = self.tableView.dataArray.count;
    [self requestWithURL:bunPayFundsContractList params:nil HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        if (this.tableView.refreshControl.refreshing) {
            [this.tableView.refreshControl endRefreshing];
        }
    }];
}

- (void)tableView:(BaseTableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    ContractModel *model = tableView.dataArray[indexPath.section];
    ContractProcessDetailViewController *vc = [[ContractProcessDetailViewController alloc] init];
    vc.contractId = model.contractId;
    [self.navigationController pushViewController:vc animated:YES];
}

@end
