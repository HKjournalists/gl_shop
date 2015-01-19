//
//  MypurseViewController.m
//  Glshop
//
//  Created by River on 14-11-18.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "MypurseViewController.h"
#import "TipViewController.h"
#import "ReChargeViewController.h"
#import "TranserViewController.h"
#import "ProfileViewController.h"
#import "PayListViewController.h"
#import "RolloutViewController.h"

@interface MypurseViewController () <UITableViewDataSource,UITableViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSArray *dataSource;
@property (nonatomic, strong) UISegmentedControl *segment;
@property (nonatomic, strong) MyPurseInfoModel *currentPurse;

/**
 *@brief 保证金账户信息
 */
@property (nonatomic, strong) MyPurseInfoModel *guarantInfo;
/**
 *@brief 货款账户信息
 */
@property (nonatomic, strong) MyPurseInfoModel *paymentInfo;

@end

@implementation MypurseViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self requestNet];
}

- (void)initDatas {
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(requestNet) name:kRefrushMyPurseNotification object:nil];
    
    _dataSource = @[@{@"wallet_icon_yue":@"余额"},
                    @{@"wallet_icon_zhuanchu":@"转出"},
                    @{@"wallet_icon_chongzhi":@"充值"},
                    @{@"wallet_icon_xiangqing":@"详情"},];
    self.title = @"我的钱包";
    self.shouldShowFailView = YES;
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(requestNet) name:kRefrushCompanyInfoNotification object:nil];
}

- (void)loadSubViews {
    [self loadTableView];
    _tableView.hidden = YES;
}

- (void)requestNet {
    
    __weak typeof(self) this = self;
    [self requestWithURL:bGetPurseAccountInfo params:nil HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

- (void)handleNetData:(id)responseData {
    NSArray *dicArray = responseData[ServiceDataKey][@"result"];
    if (!dicArray.count) {
        return;
    }
    
    for (NSDictionary *dataDic in dicArray) {
        NSDictionary *aDic = dataDic[@"passtype"];
        if ([aDic[DataValueKey] integerValue] == 0) {
            _guarantInfo = [[MyPurseInfoModel alloc] initWithDataDic:dataDic];
            _currentPurse = _guarantInfo;
        }else {
            _paymentInfo = [[MyPurseInfoModel alloc] initWithDataDic:dataDic];
        }
    }
    _tableView.hidden = NO;
    [_tableView reloadData];
}

- (UIView *)loadHeaderView {
    UIView *headerBG = UIView.new;
    headerBG.frame = CGRectMake(0, 0, SCREEN_WIDTH, 250);
    headerBG.backgroundColor = self.view.backgroundColor;
    
    UIImageView *header = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 400/2)];
    header.userInteractionEnabled = YES;
    header.image = [UIImage imageNamed:@"information_beijing"];
    [headerBG addSubview:header];
    
    UIImageView *logoImage = [[UIImageView alloc] initWithFrame:CGRectMake(SCREEN_WIDTH/2-33, 70, 66, 66)];
    logoImage.image = [UIImage imageNamed:@"information_touxiang"];
    [header addSubview:logoImage];
    
    UILabel *titleLabel = [UILabel labelWithTitle:@"腾讯QQ"];
    titleLabel.textColor = [UIColor whiteColor];
    titleLabel.frame = CGRectMake(SCREEN_WIDTH/2-60, logoImage.vbottom, 120, 25);
    titleLabel.textAlignment = NSTextAlignmentCenter;
    [header addSubview:titleLabel];
    
    UIImageView *bottomBar = [[UIImageView alloc] initWithFrame:CGRectMake(0, header.vbottom-40, SCREEN_WIDTH, 40)];
    bottomBar.userInteractionEnabled = YES;
    UIImage *bImage = [UIImage imageNamed:@"information_xia"];
    bImage = [bImage resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
    bottomBar.image = bImage;
    [header addSubview:bottomBar];
    [bottomBar addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(pushToProfileVC)]];
    UserInstance *user = [UserInstance sharedInstance];
    NSString *userName = user.user.username;
    UILabel *userLabel = [UILabel labelWithTitle:[NSString stringWithFormat:@"登录账号:%@",userName]];
    userLabel.frame = CGRectMake(5, 0, 140, bottomBar.vheight);
    userLabel.font = [UIFont systemFontOfSize:13.f];
    userLabel.textColor = [UIColor whiteColor];
    [bottomBar addSubview:userLabel];
    
    UIButton *setBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    setBtn.frame = CGRectMake(bottomBar.vright-80, 0, 70, 40);
    setBtn.enabled = NO;
    [setBtn setImageEdgeInsets:UIEdgeInsetsMake(0, 50, 0, 0)];
    [setBtn setImage:[UIImage imageNamed:@"Buy_sell_icon_arrowhead"] forState:UIControlStateNormal];
//    [setBtn addTarget:self action:@selector(pushToProfileVC) forControlEvents:UIControlEventTouchUpInside];
    [bottomBar addSubview:setBtn];
    
    _segment = [[UISegmentedControl alloc] initWithItems:@[@"交易保证金账户",@"货款账户"]];
    _segment.frame = CGRectMake(10, bottomBar.vbottom+10, SCREEN_WIDTH-20, 30);
    _segment.tintColor = [UIColor blueColor];
    _segment.selectedSegmentIndex = 0;
    _segment.layer.cornerRadius = 0;
    [_segment addTarget:self action:@selector(chosePurseType:) forControlEvents:UIControlEventValueChanged];
    [headerBG addSubview:_segment];
    
    return headerBG;
}

- (void)loadTableView {
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    _tableView.backgroundColor = [UIColor clearColor];
    [self.view addSubview:_tableView];
    
//    _tableView.tableHeaderView = [self loadHeaderView];
    
    UIView *footerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 120)];
    footerView.backgroundColor = [UIColor clearColor];
    UIView *tipView = [UIFactory createPromptViewframe:CGRectMake(10, 10, self.view.vwidth-20, 100) tipTitle:nil];
    UILabel *label1 = [UILabel labelWithTitle:@"号为必填项号为必填项号为必填项号为必填项号为必填项号为必填项号为必填项号为必填项号为必填项号为必填项号为必填项号为必填项"];
    label1.font = [UIFont systemFontOfSize:14.f];
    label1.frame = CGRectMake(10, 40, 280, 20);
    label1.numberOfLines = 0;
    [label1 sizeToFit];
    [tipView addSubview:label1];
    [footerView addSubview:tipView];
    
//    _tableView.tableFooterView = footerView;
}

#pragma mark - UITableView DataSource/Delegate

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return section == 0 ? 1 : _dataSource.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.textLabel.font = [UIFont boldSystemFontOfSize:16.f];
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    if (indexPath.section == 0) {
        cell.imageView.image = [UIImage imageNamed:@"wallet_icon_qianbao"];
        UserInstance *user = [UserInstance sharedInstance];
        UILabel *labelcname = [UILabel labelWithTitle:user.user.cname];
        labelcname.font = [UIFont boldSystemFontOfSize:16.f];
        labelcname.frame = CGRectMake(75, 13, 250, 20);
        [cell.contentView addSubview:labelcname];
        
        UILabel *labelUname = [[UILabel alloc] initWithFrame:CGRectMake(labelcname.vleft, labelcname.vbottom+10, 300, 20)];
        labelUname.textColor = RGB(100, 100, 100, 1);
        labelUname.font = [UIFont boldSystemFontOfSize:14.f];
        labelUname.text = [NSString stringWithFormat:@"登录账号：%@",user.user.username];
        [cell.contentView addSubview:labelUname];
        
        return cell;
    }else {
        NSDictionary *dic = _dataSource[indexPath.row];
        cell.imageView.image = [UIImage imageNamed:dic.allKeys.firstObject];
        cell.textLabel.text = dic.allValues.firstObject;
        if (indexPath.row == 0) {
            cell.detailTextLabel.textColor = [UIColor redColor];
        }
        
        if (_dataSource.count == 4 && indexPath.row == 0) {
            cell.detailTextLabel.text = [NSString stringWithFormat:@"￥%.2f",[_guarantInfo.amount floatValue]];
        }else if (_dataSource.count == 5 && indexPath.row == 0) {
            cell.detailTextLabel.text = [NSString stringWithFormat:@"￥%.2f",[_paymentInfo.amount floatValue]];
        }
        
        return cell;
    }
    
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    if (section == 1) {
        return 40;
    }
    return 5;
}

- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section {
    if (section == 1) {
        return 120;
    }
    return 5;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    if (section == 1) {
        UIView *bgView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 40)];
        [bgView addSubview:self.segment];
        return bgView;
    }
    return nil;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section {
    if (section == 1) {
        UIView *footerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 120)];
        footerView.backgroundColor = [UIColor clearColor];
        UIView *tipView = [UIFactory createPromptViewframe:CGRectMake(10, 10, self.view.vwidth-20, 100) tipTitle:nil];
        UILabel *label1 = [UILabel labelWithTitle:@"号为必填项号为必填项号为必填项号为必填项号为必填项号为必填项号为必填项号为必填项号为必填项号为必填项号为必填项号为必填项"];
        label1.font = [UIFont systemFontOfSize:14.f];
        label1.frame = CGRectMake(10, 40, 280, 20);
        label1.numberOfLines = 0;
        [label1 sizeToFit];
        [tipView addSubview:label1];
        [footerView addSubview:tipView];
        
        return footerView;
    }
    return nil;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.row == 0 && indexPath.section == 0) {
        return 72;
    }
    return 44;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    if (indexPath.section == 0) {
        ProfileViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"ProfileViewControllerId"];
        [self.navigationController pushViewController:vc animated:YES];
    }else {
        if (indexPath.row == 0) {
            TipViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"TipViewControllerId"];
            if ((_segment.selectedSegmentIndex==0 && [_guarantInfo.guarantyEnough integerValue] == 1)|| (_segment.selectedSegmentIndex==1 && [_paymentInfo.guarantyEnough integerValue] == 1)) {
                vc.guarantyEnough = YES;
            }
            vc.amountMoney = _segment.selectedSegmentIndex == 0 ? _guarantInfo.amount : _paymentInfo.amount;
            [self.navigationController pushViewController:vc animated:YES];
        }else if (indexPath.row == 1) {
            RolloutViewController *vc = [[RolloutViewController alloc] init];
            vc.title = @"提现";
            [self.navigationController pushViewController:vc animated:YES];
        }else if (indexPath.row == 2) {
            ReChargeViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"ReChargeViewControllerId"];
            [self.navigationController pushViewController:vc animated:YES];
        }else if (indexPath.row == 3) {
            if (_segment.selectedSegmentIndex == 0) {
                PayListViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"PayListViewControllerId"];
                vc.listType = pay_margin;
                [self.navigationController pushViewController:vc animated:YES];
            }
            
        }else if (indexPath.row == 4) {
            PayListViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"PayListViewControllerId"];
            vc.listType = pay_payment;
            [self.navigationController pushViewController:vc animated:YES];
        }

    }
}

#pragma mark - UIActions
- (void)chosePurseType:(UISegmentedControl *)seg {
    if (seg.selectedSegmentIndex == 0) {
            _dataSource = @[@{@"wallet_icon_yue":@"余额"},@{@"wallet_icon_zhuanchu":@"转出"},@{@"wallet_icon_chongzhi":@"充值"},@{@"wallet_icon_xiangqing":@"详情"},];
        _currentPurse = _guarantInfo;
    }else {
            _dataSource = @[@{@"wallet_icon_yue":@"余额"},@{@"wallet_icon_zhuanchu":@"转出"},@{@"wallet_icon_chongzhi":@"充值"},@{@"wallet_icon_zhuanchuzhi":@"转出至保证金"},@{@"wallet_icon_xiangqing":@"详情"},];
        _currentPurse = _paymentInfo;
    }
    [_tableView reloadData];
}

- (void)pushToProfileVC {
    ProfileViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"ProfileViewControllerId"];
    [self.navigationController pushViewController:vc animated:YES];
}

#pragma mark - Getter

- (UISegmentedControl *)segment {
    if (!_segment) {
        _segment = [[UISegmentedControl alloc] initWithItems:@[@"交易保证金账户",@"货款账户"]];
        _segment.frame = CGRectMake(10, 0, SCREEN_WIDTH-20, 35);
        _segment.tintColor = RGB(40, 113, 213, 1);
        _segment.selectedSegmentIndex = 0;
        [_segment setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:
                                          [UIFont boldSystemFontOfSize:14.f], NSFontAttributeName,
                                          nil] forState:UIControlStateNormal];
        [_segment addTarget:self action:@selector(chosePurseType:) forControlEvents:UIControlEventValueChanged];
    }
    return _segment;
}

@end
