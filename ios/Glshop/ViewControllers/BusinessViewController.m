//
//  BusinessViewController.m
//  Glshop
//
//  Created by River on 14-11-18.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "BusinessViewController.h"
#import "BusinessTableViewCell.h"
#import "BusinessTableView.h"
#import "PurchaseTableView.h"
#import "OrderModel.h"
#import "PublicInfoViewController.h"
#import "IndicateExtionView.h"
#import "BrowseViewController.h"
#import "BarSegment.h"
#import "FilerViewController.h"
#import "IBActionSheet.h"

#define kHeaderViewHeight 40
#define kLoadCount 10
#define kSellTableDateKey @"sellInfoListKey"
#define kBuyTableDateKey @"buyInfoListKey"

@interface BusinessViewController () <UITableViewEventDelegate,BarDidSelected,IBActionSheetDelegate>

/**
 *@brief 出售列表
 */
@property (nonatomic, strong) BusinessTableView *listTableView;
/**
 *@brief 求购列表
 */
@property (nonatomic, strong) PurchaseTableView *puchaseTableView;

/**
 *@brief 头部控件
 */
@property (nonatomic, strong) BarSegment *segmentBar;

@property (nonatomic, strong) IBActionSheet *sheet;
@property (nonatomic, strong) UIButton *titleViewBtn;

/**
 *@brief 为IBActionSheet记录选择的索引，默认为0
 */
@property (nonatomic, assign) NSInteger markIndex;


@end

@implementation BusinessViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.title = @"找买找卖";
    [self requestNet];
}

- (void)initDatas {
    self.requestParams = [NSMutableDictionary dictionary];
    _markIndex = 0;
    self.isRefrushTable = YES;
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(requestNet) name:kRefrushBuySellNotification object:nil];
}

- (void)setTitle:(NSString *)title {
    [super setTitle:title];
    
    _titleViewBtn = [UIButton buttonWithTip:@"找买找卖" target:self selector:@selector(showSheet:)];
    [_titleViewBtn setImage:[UIImage imageNamed:@"supply-and-demand_icon_on"] forState:UIControlStateNormal];
    [_titleViewBtn setTitleColor:[UIColor grayColor] forState:UIControlStateHighlighted];
    [_titleViewBtn setImageEdgeInsets:UIEdgeInsetsMake(0, 90, 0, 0)];
    [_titleViewBtn setTitleEdgeInsets:UIEdgeInsetsMake(0, -30, 0, 0)];
    _titleViewBtn.frame = CGRectMake(0, 0, 120, 44);
    self.navigationItem.titleView = _titleViewBtn;
}

#pragma mark - UI
- (void)loadSubViews {
    
    _sheet = [[IBActionSheet alloc] initWithTitle:@"选择查看排序" delegate:self cancelButtonTitle:globe_cancel_str destructiveButtonTitle:nil otherButtonTitlesArray:@[@"发布时间排序",@"价格从低到高",@"价格从高到低",@"诚信度从高到低"]];
    _sheet.markIndex = _markIndex;
    
    // 发布信息按钮
    UIButton *filterBtn = [UIButton buttonWithTip:@"发布信息" target:self selector:nil];
    [filterBtn setImage:[UIImage imageNamed:@"Buy_sell_icon_bi"] forState:UIControlStateNormal];
    [filterBtn setTitleColor:[UIColor grayColor] forState:UIControlStateHighlighted];
    filterBtn.titleLabel.font = [UIFont systemFontOfSize:13.f];
    [filterBtn addTarget:self action:@selector(publicInfo:) forControlEvents:UIControlEventTouchUpInside];
    filterBtn.frame = CGRectMake(0, 0, 70, 54);
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithCustomView:filterBtn];
    self.navigationItem.rightBarButtonItem = rightItem;
    
    // 头部视图
    _segmentBar = [[BarSegment alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 40)];
    _segmentBar.delegate = self;
    [self.view addSubview:_segmentBar];
    

    // 求购信息列表
    _listTableView = [[BusinessTableView alloc] initWithFrame:CGRectMake(0, _segmentBar.vbottom, self.view.vwidth, self.view.vheight-_segmentBar.vheight-kTopBarHeight) style:UITableViewStyleGrouped];
//    _listTableView.backgroundColor = self.view.backgroundColor;
    _listTableView.eventDelegate = self;
    __weak typeof(self) this = self;
    [_listTableView addLegendHeaderWithRefreshingBlock:^{
        [this refrush:YES targetTable:this.listTableView];
    } dateKey:kBuyTableDateKey];
    [self.view addSubview:_listTableView];

    
    // 出售信息列表
    _puchaseTableView = [[PurchaseTableView alloc] initWithFrame:CGRectMake(0, _segmentBar.vbottom, self.view.vwidth, self.view.vheight-_segmentBar.vheight-kTopBarHeight) style:UITableViewStyleGrouped];
    _puchaseTableView.eventDelegate = self;
    if (_puchaseTableView.dataArray.count < kLoadCount) {
        _puchaseTableView.isMore = NO;
    }else {
        _puchaseTableView.isMore = YES;
    }
    [_puchaseTableView addLegendHeaderWithRefreshingBlock:^{
        [this refrush:YES targetTable:this.puchaseTableView];
    } dateKey:kSellTableDateKey];
    [self.view addSubview:_puchaseTableView];
    _puchaseTableView.hidden = YES;
}

#pragma mark - Net
- (void)requestNet {
//    [self currentTableView].hidden = YES;
    [super requestNet];
    [[self currentTableView].legendHeader beginRefreshing];
    
}

- (void)refrush:(BOOL)refrush targetTable:(BaseTableView *)tableView {
    BOOL isRefresh = refrush ? YES : NO;
    tableView.pageIndex = isRefresh ? 1 : tableView.pageIndex;
    NSString *businessType = _segmentBar.selctedIndex == 0 ? @"1" : @"2";

    [self.requestParams setObject:[NSNumber numberWithInteger:[self currentTableView].pageIndex] forKey:@"pageIndex"];
    [self.requestParams setObject:businessType forKey:@"type"];
    NSNumber *loadNum = [NSNumber numberWithInteger:kLoadCount];
    [self.requestParams setObject:loadNum forKey:@"pageSize"];

    __block typeof(self) this = self;
    [self requestWithURL:bFoundOrderList
                  params:self.requestParams
              HTTPMethod:kHttpGetMethod
             shouldCache:NO
           completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
           [tableView.header endRefreshing];
        [this handleNetData:responseData];
        
        } failedBlock:^(ASIHTTPRequest *req){
        [tableView.header endRefreshing];
            
    }];
}

- (void)handleNetData:(id)responseData {
    BaseTableView *tableView = [self currentTableView];
    tableView.hidden = NO;
    BOOL isLoadMore = tableView.pageIndex == 1 ? NO : YES;
    NSArray *datas = [[responseData objectForKey:ServiceDataKey] objectForKey:@"result"];
    NSMutableArray *temp = [NSMutableArray array];
    for (NSDictionary *dic in datas) {
        OrderModel *model = [[OrderModel alloc] initWithDataDic:dic];
        [temp addObject:model];
    }
    
    if (!isLoadMore && temp.count <= 0) { // 刷新时，没有数据
        [self requestSuccessButNoData];
    }
    
    // 每次加载5条数据
    NSInteger loadCount = kLoadCount;
    if (temp.count < loadCount) {
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

}

- (void)handleRequestFailed:(ASIHTTPRequest *)req {
    [super commandHandle:req];
    if (self.shouldShowFailView && self.isNotActionRequest && !self.failView.superview) {
        [self.view addSubview:[self failViewWithFrame:CGRectMake(0, 50, self.view.vwidth, self.view.vheight-50) empty:NO]];
    }else {
        [self showTip:kNetError];
    }
}

- (void)requestSuccessButNoData {
    if (self.shouldShowFailView && !self.failView.superview) {
        [self.view addSubview:[self failViewWithFrame:CGRectMake(0, _segmentBar.vheight, self.view.vwidth, self.view.vheight-_segmentBar.vheight) expectionImgName:nil expectionTitle:nil expectionSubTitle:@"该条件下暂无信息，请赶紧发布信息!" isNodata:YES]];
    }
}

#pragma mark - UITableViewEventDelegate
- (void)pullUp:(BaseTableView *)tableView {
    tableView.pageIndex++;
    [self refrush:NO targetTable:tableView];
}

- (void)tableView:(BaseTableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    OrderModel *model = [self currentTableView].dataArray[indexPath.section];
    NSInteger orderStatus = [model.status[DataValueKey] integerValue];
    NSInteger orderType = [model.type[DataValueKey] integerValue];
    BrowseViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"BrowseViewControllerId"];
    if (orderType == 1) {
        vc.title = @"求购信息";
    }else {
        vc.title = @"出售信息";
    }
    vc.orderStatus = orderStatus;
    vc.orderId = model.id;
    [self.navigationController pushViewController:vc animated:YES];
}


#pragma mark - barDidSelectedAt Delegate
- (void)barDidSelectedAtIndex:(NSInteger)index {
    if (index == 1) {
        _puchaseTableView.hidden = NO;
        _listTableView.hidden = YES;
        [self requestNet];
    }else if (index == 0) {
        _puchaseTableView.hidden = YES;
        _listTableView.hidden = NO;
        [self requestNet];
    }else {
        FilerViewController *vc = [[FilerViewController alloc] init];
        [self.navigationController pushViewController:vc animated:YES];
    }
}

#pragma mark - UIActionSheet Delegate
- (void)actionSheet:(IBActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex != 4) {
        self.requestParams = [NSMutableDictionary dictionary];
    }
    BaseTableView *table = [self currentTableView];
    if (buttonIndex == 0) {
        [self.requestParams setObject:@1 forKey:@"orderEffTime"];
//        [self refrush:YES targetTable:table];
    }else if (buttonIndex == 1) {
        [self.requestParams setObject:@0 forKey:@"orderPrice"];
//        [self refrush:YES targetTable:table];
    }else if (buttonIndex == 2) {
        [self.requestParams setObject:@1 forKey:@"orderPrice"];
//        [self refrush:YES targetTable:table];
    }else if (buttonIndex == 3) {
        [self.requestParams setObject:@1 forKey:@"orderCredit"];
//        [self refrush:YES targetTable:table];
    }
    if (buttonIndex != 4) {
        [table.header beginRefreshing];
    }
}

//- (void)actionSheet:(IBActionSheet *)actionSheet willDismissWithButtonIndex:(NSInteger)buttonIndex {
//       [self indicateArrow];
//}

- (void)actionWillDismiss:(IBActionSheet *)actionSheet {
    [self indicateArrow];
}

#pragma mark - UIActions
- (void)publicInfo:(UIButton *)btn {
    if (![[UserInstance sharedInstance] login]) {
        HUD(@"您还没有登录");
        return;
    }
    
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

- (void)showSheet:(UIButton *)button {
    if (_sheet.visible) {
        return;
    }
    
    [self indicateArrow];
    [_sheet showInView:self.view];
}

#pragma mark - Private
- (BaseTableView *)currentTableView {
    if (_segmentBar.selctedIndex == 0) {
        return _listTableView;
    }else if (_segmentBar.selctedIndex == 1) {
        return _puchaseTableView;
    }
    return nil;
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
