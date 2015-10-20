//
//  MySupplyViewController.m
//  Glshop
//
//  Created by River on 14-11-18.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  我的供求

#import "MySupplyViewController.h"
#import "SupplyTableView.h"
#import "OrderModel.h"
#import "IndicateExtionView.h"
#import "PublicInfoViewController.h"
#import "PublicInfoModel.h"
#import "CompanyAuthViewController.h"
#import "BrowseViewController.h"
#import "IBActionSheet.h"

#define kHeaderViewHeight 40

@interface MySupplyViewController () <UITableViewEventDelegate,IBActionSheetDelegate>

@property (nonatomic, strong) IndicateExtionView *indicateView;

@property (nonatomic, strong)  SupplyTableView *supplyAllTableView;
@property (nonatomic, strong)  SupplyTableView *supplyBuyTableView;
@property (nonatomic, strong)  SupplyTableView *supplySellTableView;

/**
 *@brief 为IBActionSheet记录选择的索引，默认为0
 */
@property (nonatomic, assign) NSInteger markIndex;
@property (nonatomic, strong) IBActionSheet *sheet;

@property (nonatomic, strong) UIButton *titleViewBtn;

@end

@implementation MySupplyViewController

- (void)viewDidLoad {
    [super viewDidLoad];
   
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(requestNet) name:kRefrushMySupplyNotification object:nil];
    [self requestNet];
}

- (void)initDatas {
    self.title = @"我的供求";
    self.isRefrushTable = YES;
    _markIndex = 0;
}


- (void)backRootVC {
    [self.navigationController popToRootViewControllerAnimated:YES];
}

- (void)setTitle:(NSString *)title {
    [super setTitle:title];
    
    _titleViewBtn = [UIButton buttonWithTip:@"我的供求" target:self selector:@selector(showSheet:)];
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

#pragma mark - UI
- (void)loadSubViews {
    
    _sheet = [[IBActionSheet alloc] initWithTitle:@"选择分类" delegate:self cancelButtonTitle:globe_cancel_str destructiveButtonTitle:nil otherButtonTitlesArray:@[@"全部",@"我的供",@"我的求",]];
    _sheet.markIndex = _markIndex;
    
    // 发布信息按钮
    UIButton *filterBtn = [UIButton buttonWithTip:@"发布信息" target:self selector:nil];
    [filterBtn setImage:[UIImage imageNamed:@"Buy_sell_icon_bi"] forState:UIControlStateNormal];
    [filterBtn setTitleColor:[UIColor grayColor] forState:UIControlStateHighlighted];
    filterBtn.titleLabel.font = [UIFont systemFontOfSize:15.f];
    [filterBtn addTarget:self action:@selector(publicInfo:) forControlEvents:UIControlEventTouchUpInside];
    filterBtn.frame = CGRectMake(0, 0, 70, 54);
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithCustomView:filterBtn];
    self.navigationItem.rightBarButtonItem = rightItem;
    
    // 我的供求全部列表
    _supplyAllTableView = [[SupplyTableView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, self.view.vheight-kTopBarHeight) style:UITableViewStyleGrouped];
    __block typeof(self) this = self;
    [_supplyAllTableView addLegendHeaderWithRefreshingBlock:^{
        [this refrush:YES];
    }];
    _supplyAllTableView.backgroundColor = self.view.backgroundColor;
    _supplyAllTableView.eventDelegate = self;
    [self.view addSubview:_supplyAllTableView];
    
    // 我的求列表
    _supplyBuyTableView = [[SupplyTableView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, self.view.vheight-kTopBarHeight) style:UITableViewStyleGrouped];
    [_supplyBuyTableView.refreshControl addTarget:self action:@selector(refrush:) forControlEvents:
     UIControlEventValueChanged];
    _supplyBuyTableView.backgroundColor = self.view.backgroundColor;
    _supplyBuyTableView.eventDelegate = self;
    _supplyBuyTableView.hidden = YES;
    [_supplyBuyTableView addLegendHeaderWithRefreshingBlock:^{
        [this refrush:YES];
    }];
    [self.view addSubview:_supplyBuyTableView];

    // 我的供列表
    _supplySellTableView = [[SupplyTableView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, self.view.vheight-kTopBarHeight) style:UITableViewStyleGrouped];
    [_supplySellTableView.refreshControl addTarget:self action:@selector(refrush:) forControlEvents:
     UIControlEventValueChanged];
    _supplySellTableView.hidden = YES;
    _supplySellTableView.backgroundColor = self.view.backgroundColor;
    _supplySellTableView.eventDelegate = self;
    [_supplySellTableView addLegendHeaderWithRefreshingBlock:^{
        [this refrush:YES];
    }];
    [self.view addSubview:_supplySellTableView];

}

#pragma mark - Net
- (void)requestNet {
    [super requestNet];
    [self currentTableView].pageIndex = 1;
    [[self currentTableView].legendHeader beginRefreshing];
}

/**
 *@brief 下拉刷新
 */
- (void)refrush:(BOOL)isRefresh {

    BaseTableView *currentTableView = [self currentTableView];
    BOOL useCache = isRefresh ? NO : YES;
    if (isRefresh) {
        currentTableView.pageIndex = 1;
    }
    
    BOOL isloadMore = currentTableView.pageIndex > 1 ? YES : NO;
    if (!isloadMore && currentTableView.dataArray.count <= 0) {
        useCache = NO;
    }
    
    UserInstance *userInstance = [UserInstance sharedInstance];
    NSString *cid = userInstance.user.cid;
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:[NSNumber numberWithInteger:currentTableView.pageIndex],@"pageIndex",@5,@"pageSize",cid,@"cid",nil];
    if ([self currentTableView] == _supplyBuyTableView) {
        [params addInteger:2 forKey:@"type"];
    }else if ([self currentTableView] == _supplySellTableView) {
        [params addInteger:1 forKey:@"type"];
    }
    [self requestWithURL:bGetMyList params:params HTTPMethod:kHttpGetMethod shouldCache:NO needHeader:YES completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [currentTableView.refreshControl endRefreshing];
        NSArray *datas = [[responseData objectForKey:ServiceDataKey] objectForKey:@"result"];
        NSMutableArray *temp = [NSMutableArray array];
        for (NSDictionary *dic in datas) {
            OrderModel *model = [[OrderModel alloc] initWithDataDic:dic];
            [temp addObject:model];
        }
        
        if (!isloadMore && temp.count <= 0) {
            [self requestSuccessButNoData];
        }
        
        if (temp.count < 5) {
            currentTableView.isMore = NO;
        }else {
            currentTableView.isMore = YES;
        }
        if (isloadMore) {
            currentTableView.dataArray = [currentTableView.dataArray arrayByAddingObjectsFromArray:temp];
        }else {
            
            currentTableView.dataArray = [NSArray arrayWithArray:temp];
        }
        [currentTableView reloadData];
        [currentTableView.header endRefreshing];
        
    } failedBlock:^ (ASIHTTPRequest *req){
        [currentTableView.header endRefreshing];
    }];
}

- (void)requestSuccessButNoData {
    if (self.shouldShowFailView && !self.failView.superview) {
        [self.view addSubview:[self failViewWithFrame:self.view.bounds expectionImgName:nil expectionTitle:nil expectionSubTitle:alert_tip_no_gongqiu isNodata:YES]];
    }
}

#pragma mark - Private
/**
 *@brief 获取当前表视图
 */
- (BaseTableView *)currentTableView {
    if (_supplyAllTableView.hidden == NO) {
        return _supplyAllTableView;
    }else if (_supplyBuyTableView.hidden == NO) {
        return _supplyBuyTableView;
    }else {
        return _supplySellTableView;
    }
}

#pragma mark - UIActions
- (void)publicInfo:(UIButton *)button {
//    UserInstance *userObj = [UserInstance sharedInstance];
//    if (!userObj.isBeAuthed) {
//        [Utilits alertWithString:@"请完成长江电商平台认证后再来发布信息！" alertTitle:nil];
//        return;
//    }
//    
//    if (!userObj.isPaymentMargin) {
//        [Utilits alertWithString:@"请先缴纳保证金后再来发布信息！" alertTitle:nil];
//        return;
//    }
    
    PublicInfoViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"PublicInfoViewControllerId"];
    [self.navigationController pushViewController:vc animated:YES];
}

#pragma mark - UITableViewEventDelegate 
- (void)pullUp:(BaseTableView *)tableView {
    tableView.pageIndex++;
    [self refrush:NO];
}

- (void)tableView:(BaseTableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    OrderModel *model = [self currentTableView].dataArray[indexPath.section];
    NSInteger orderStatus = [model.status[DataValueKey] integerValue];
    BrowseViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"BrowseViewControllerId"];
    vc.title = @"供求详细";
    vc.fromMySupply = 1;
    vc.orderStatus = orderStatus;
    vc.orderId = model.id;
    
    NSInteger orderType = [model.type[DataValueKey] integerValue];
    if (orderType == 1) {
        vc.title = @"求购信息";
    }else {
        vc.title = @"出售信息";
    }
    
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)dealloc {
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

#pragma mark - 
- (void)actionSheet:(IBActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex == 0) {
        self.supplyAllTableView.hidden = NO;
        self.supplyBuyTableView.hidden = YES;
        self.supplySellTableView.hidden = YES;
    }else if (buttonIndex == 1) {
        self.supplyBuyTableView.hidden = NO;
        self.supplySellTableView.hidden = YES;
        self.supplyAllTableView.hidden = YES;
    }else if (buttonIndex == 2){
        self.supplySellTableView.hidden = NO;
        self.supplyBuyTableView.hidden = YES;
        self.supplyAllTableView.hidden = YES;
    }
    if (actionSheet.cancelButtonIndex != buttonIndex) {
        
        [self requestNet];
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
