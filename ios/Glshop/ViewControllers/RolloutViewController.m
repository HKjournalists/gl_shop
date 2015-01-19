//
//  RolloutViewController.m
//  Glshop
//
//  Created by River on 15-1-13.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "RolloutViewController.h"
#import "TranserViewController.h"
#import "FirstGatherViewController.h"
#import "GatherModel.h"
#import "MangerGathersViewController.h"

@interface RolloutViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *listView;
@property (nonatomic, strong) NSArray *listDatas;
@property (nonatomic, assign) NSInteger lastSeleced;

@end

@implementation RolloutViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(requestNet) name:kRefrushGatherListNotification object:nil];
    self.shouldShowFailView = YES;
    _lastSeleced = NSNotFound;
    [self requestNet];
}

#pragma mark - UI
- (void)loadSubViews {
    _listView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _listView.dataSource = self;
    _listView.delegate = self;
    _listView.sectionFooterHeight = 5;
    _listView.sectionHeaderHeight = 5;
    _listView.vheight -= kTopBarHeight+60;
    _listView.contentInset = UIEdgeInsetsMake(-20, 0, 0, 0);
    [self.view addSubview:_listView];
    _listView.hidden = YES;
    
//    self.view.backgroundColor = [UIColor purpleColor];
    
    UIView *view = [UIView new];
    view.backgroundColor = [UIColor clearColor];
    [_listView setTableFooterView:view];
    
    UIBarButtonItem *barItem = [[UIBarButtonItem alloc] initWithTitle:@"管理" style:UIBarButtonItemStylePlain target:self action:@selector(mangerGathers)];
    self.navigationItem.rightBarButtonItem = barItem;
    
    UIButton *nextBtn = [UIFactory createBtn:BlueButtonImageName bTitle:@"下一步" bframe:CGRectZero];
    [nextBtn addTarget:self action:@selector(nextAction) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:nextBtn];
    [nextBtn makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view).offset(15);
        make.right.mas_equalTo(self.view).offset(-15);
        make.width.mas_equalTo(35);
        make.bottom.mas_equalTo(self.view).offset(-10);
    }];
}

- (void)loadNoGatherUI {
    self.navigationItem.rightBarButtonItem = nil;
    
    UILabel *tilteLabel = [UILabel labelWithTitle:@"暂时还没有有效的提现收款人，赶紧来添加吧。"];
    tilteLabel.font = [UIFont boldSystemFontOfSize:15.f];
    tilteLabel.textAlignment = NSTextAlignmentCenter;
    tilteLabel.numberOfLines = 2;
    [self.view addSubview:tilteLabel];
    [tilteLabel makeConstraints:^(MASConstraintMaker *make) {
        make.height.mas_equalTo(50);
        make.top.mas_equalTo(40);
        make.width.mas_equalTo(300);
        make.centerX.mas_equalTo(self.view.centerX);
    }];
    
    UIButton *button = [UIFactory createBtn:BlueButtonImageName bTitle:@"添加收款人" bframe:CGRectZero];
    [button addTarget:self action:@selector(addGather) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:button];
    
    [button makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(tilteLabel.leading);
        make.right.mas_equalTo(tilteLabel.right);
        make.top.mas_equalTo(tilteLabel.bottom).offset(20);
        make.height.mas_equalTo(35);
    }];
}

#pragma mark - Net Handle
- (void)requestNet {
    [super requestNet];
    UserInstance *userInstance = [UserInstance sharedInstance];
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:userInstance.user.cid,@"cid",@1,@"authStatus", nil];
    __block typeof(self) this = self;
    [self requestWithURL:bcopnacceptgetList params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

- (void)handleNetData:(id)responseData {
    NSArray *datas = responseData[ServiceDataKey];
    if (datas.count <= 0) {
        [self loadNoGatherUI];
    }else {
        NSMutableArray *temp = [NSMutableArray array];
        for (NSDictionary *dic in datas) {
            GatherModel *model = [[GatherModel alloc] initWithDataDic:dic];
            [temp addObject:model];
        }
        self.listDatas = [NSArray arrayWithArray:temp];
        _listView.hidden = NO;
        [_listView reloadData];
    }
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
        cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        
        return cell;
        
    }else {
        static NSString *reuseId = @"gatherListId";
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:reuseId];
        if (!cell) {
            cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:reuseId];
        }
        GatherModel *model = [_listDatas safeObjAtIndex:indexPath.row];
        cell.textLabel.text = model.carduser;
        cell.detailTextLabel.text = model.bankcard;
        
        UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 25, 15)];
        imageView.image = [UIImage imageNamed:@"address_icon_select"];
        if ([[model.status objectForKey:@"val"] integerValue] == 1 && _lastSeleced == NSNotFound) {
            cell.detailTextLabel.text = [NSString stringWithFormat:@"[默认]%@",model.bankcard];
            _lastSeleced = [_listDatas indexOfObject:model];
        }else {
            cell.detailTextLabel.text = [NSString stringWithFormat:@"%@",model.bankcard];
        }
        
        if (indexPath.row == _lastSeleced) {
            cell.accessoryView = imageView;
        }
        
        return cell;
    }
    
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    if (indexPath.section == 0) {
        [self addGather];
    }else {
        UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
        UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 25, 15)];
        imageView.image = [UIImage imageNamed:@"address_icon_select"];
        cell.accessoryView = imageView;
        if (indexPath.row != _lastSeleced) {
            UITableViewCell *prvCell = [tableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:_lastSeleced inSection:1]];
            prvCell.accessoryView = nil;
        }
        _lastSeleced = indexPath.row;
    }
}

#pragma mark - UIActions
- (void)addGather {
    FirstGatherViewController *vc = [[FirstGatherViewController alloc] init];
    [self.navigationController pushViewController:vc animated:YES];
}// 添加收款人

- (void)mangerGathers {
    MangerGathersViewController *vc = [[MangerGathersViewController alloc] init];
    [self.navigationController pushViewController:vc animated:YES];
}// 进入管理收款人列表

- (void)nextAction {
    TranserViewController *vc = [[TranserViewController alloc] init];
    vc.gahter = [_listDatas safeObjAtIndex:_lastSeleced];
    [self.navigationController pushViewController:vc animated:YES];
}// 提现操作

@end
