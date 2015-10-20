//
//  MangerGathersViewController.m
//  Glshop
//
//  Created by River on 15-1-14.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "MangerGathersViewController.h"
#import "GatherModel.h"
#import "FirstGatherViewController.h"
#import "GatherDetailViewController.h"

@interface MangerGathersViewController ()<UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *listView;
@property (nonatomic, strong) NSArray *listDatas;

@end

@implementation MangerGathersViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(requestNet) name:kRefrushGatherListNotification object:nil];
    self.title = @"收款人管理";
    [self requestNet];
}

- (void)loadSubViews {
    _listView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _listView.dataSource = self;
    _listView.delegate = self;
    _listView.sectionFooterHeight = 5;
    _listView.sectionHeaderHeight = 5;
    _listView.vheight -= kTopBarHeight;
    _listView.contentInset = UIEdgeInsetsMake(-20, 0, 0, 0);
    [self.view addSubview:_listView];
    _listView.hidden = YES;
    
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [_listView setTableFooterView:view];
}

- (void)requestNet {
    [super requestNet];
    UserInstance *userInstance = [UserInstance sharedInstance];
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:userInstance.user.cid,@"cid", nil];
    __block typeof(self) this = self;
    [self requestWithURL:bcopnacceptgetList params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

- (void)handleNetData:(id)responseData {
    NSArray *datas = responseData[ServiceDataKey];

        NSMutableArray *temp = [NSMutableArray array];
        for (NSDictionary *dic in datas) {
            GatherModel *model = [[GatherModel alloc] initWithDataDic:dic];
            [temp addObject:model];
        }
        self.listDatas = [NSArray arrayWithArray:temp];
        _listView.hidden = NO;
        [_listView reloadData];
    
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return section == 0 ? 1 : _listDatas.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    if (indexPath.section == 0) {
        UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:nil];
        cell.imageView.image = [UIImage imageNamed:@"information_icon_add"];
        cell.textLabel.text = @"新增收款人";
        cell.textLabel.font = UFONT_16_B;
        cell.textLabel.textColor = C_BLACK;
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        
        return cell;
        
    }else {
        static NSString *reuseId = @"gathermListId";
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:reuseId];
        if (!cell) {
            cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:reuseId];
            UILabel *label = [UILabel label];
            label.frame = CGRectMake(cell.vright-160, 0, 150, cell.vheight);
            label.tag = 100+indexPath.row;
            label.font = [UIFont boldSystemFontOfSize:FONT_12];
            label.textAlignment = NSTextAlignmentRight;
            [cell.contentView addSubview:label];
        }
        GatherModel *model = _listDatas[indexPath.row];
        cell.textLabel.text = model.carduser;
        cell.textLabel.textColor = C_BLACK;
        cell.textLabel.font = [UIFont boldSystemFontOfSize:FONT_13];
        cell.detailTextLabel.text = model.bankcard;
        cell.detailTextLabel.textColor = RGB(100, 100, 100, 1);
        cell.detailTextLabel.font = [UIFont systemFontOfSize:FONT_12];

        
        UILabel *label = (UILabel *)[cell viewWithTag:100+indexPath.row];
        if ([model.authstatus[DataValueKey] integerValue] == 2) {
            label.textColor = [UIColor redColor];
            label.text = model.authstatus[DataTextKey];
        }else if ([model.authstatus[DataValueKey] integerValue] == 0){
            label.textColor = [UIColor grayColor];
            label.text = model.authstatus[DataTextKey];
        }
        
        return cell;
    }
    
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    if (indexPath.section == 0) {
        FirstGatherViewController *vc = [[FirstGatherViewController alloc] init];
        [self.navigationController pushViewController:vc animated:YES];
    }else {
        GatherModel *model = _listDatas[indexPath.row];
        GatherDetailViewController *vc = [[GatherDetailViewController alloc] init];
        vc.gather = model;
        [self.navigationController pushViewController:vc animated:YES];
    }
}

- (void)dealloc {
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

@end
