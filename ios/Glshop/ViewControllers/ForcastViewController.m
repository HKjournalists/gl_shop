//
//  ForcastViewController.m
//  Glshop
//
//  Created by River on 14-11-21.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  市场行情预测

#import "ForcastViewController.h"
#import "ButtonWithTitleAndImage.h"
#import "ForcastTableView.h"
#import "SecondTableView.h"
#import "ProductWeekModel.h"
#import "IBActionSheet.h"

#define kSendTableDateKey @"kSendTableDateKey"
#define kStoneTableDateKey @"kStoneTableDateKey"

static NSInteger filedViewTag = 201;

@interface ForcastViewController () <IBActionSheetDelegate,UITableViewEventDelegate>

@property (nonatomic, strong) UIView *headerView;
@property (nonatomic, strong) UISegmentedControl *segment; // 选择产品类型
@property (nonatomic, strong) ButtonWithTitleAndImage *choseBtn; // 地域选择
@property (nonatomic, strong) ForcastTableView *listTableView;
@property (nonatomic, strong) SecondTableView *puchaseTableView;

/**
 *@brief 为IBActionSheet记录选择的索引，默认为0
 */
@property (nonatomic, assign) NSInteger markIndex;

@end

@implementation ForcastViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    [self requestNet];
}

- (void)initDatas {
    self.title = @"价格预测";
    self.isRefrushTable = YES;
}

#pragma mark - UI
- (void)loadSubViews {
    _headerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 50)];
    _headerView.backgroundColor = ColorWithHex(@"#F6F6F6");
    _headerView.layer.shadowOffset = CGSizeMake(_headerView.vwidth, 2);
    _headerView.layer.shadowColor = [UIColor lightGrayColor].CGColor;
    [self.view addSubview:_headerView];
    
    _segment = [[UISegmentedControl alloc] initWithItems:@[@"黄砂",@"石子"]];
    _segment.frame = CGRectMake(10, 10, 180, 30);
    [_segment setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:
                                      [UIColor grayColor], NSForegroundColorAttributeName,
                                      [UIFont systemFontOfSize:17.f], NSFontAttributeName,
                                      nil] forState:UIControlStateNormal];
    _segment.selectedSegmentIndex = _isSend ? 0 : 1;
    [_segment addTarget:self action:@selector(choseBusinessType:) forControlEvents:UIControlEventValueChanged];
    [_headerView addSubview:_segment];
    
    RiverSectionModel *model;
    if ([[SynacInstance sharedInstance]riverSectionsArray].count > 0) {
        model = [[SynacInstance sharedInstance]riverSectionsArray][0];
    }
    _choseBtn = [ButtonWithTitleAndImage buttonWithType:UIButtonTypeCustom];
    _choseBtn.frame = CGRectMake(_segment.vright+SCREEN_WIDTH-310, _segment.vtop, 100, _segment.vheight);
    [_choseBtn setImage:[UIImage imageNamed:@"index_icon_arrow_down"] forState:UIControlStateNormal];
    UIImage *bImage = [UIImage imageNamed:@"index_spec_background"];
    bImage = [bImage resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
    [_choseBtn setBackgroundImage:bImage forState:UIControlStateNormal];
    [_choseBtn addTarget:self action:@selector(choseRegion:) forControlEvents:UIControlEventTouchUpInside];
    [_choseBtn setTitle:model.riverSectionName forState:UIControlStateNormal];
    [_choseBtn setTitleColor:ColorWithHex(@"#646464") forState:UIControlStateNormal];
    [_headerView addSubview:_choseBtn];
    
    [self.view addSubview:[self filedView:CGRectMake(0, _headerView.vbottom, _headerView.vwidth, 50)]];
    
    // 黄砂
    _listTableView = [[ForcastTableView alloc] initWithFrame:CGRectMake(0, _headerView.vbottom+50, self.view.vwidth, self.view.vheight-_headerView.vheight-kTopBarHeight-10) style:UITableViewStylePlain];
    __weak typeof(self) this = self;
    [_listTableView addLegendHeaderWithRefreshingBlock:^{
        [this refrush:YES targetTable:this.listTableView];
    } dateKey:kSendTableDateKey];
    _listTableView.eventDelegate = self;
    _listTableView.isMore = NO;
//    _listTableView.tableHeaderView = headerView;
    [self.view addSubview:_listTableView];
    
    // 石子
    _puchaseTableView = [[SecondTableView alloc] initWithFrame:CGRectMake(0, _headerView.vbottom+50, self.view.vwidth, self.view.vheight-_headerView.vheight-kTopBarHeight-10) style:UITableViewStylePlain];
    [_puchaseTableView addLegendHeaderWithRefreshingBlock:^{
        [this refrush:YES targetTable:this.puchaseTableView];
    } dateKey:kStoneTableDateKey];
    _puchaseTableView.eventDelegate = self;
    _puchaseTableView.maxLoadsCount = 20;
    _puchaseTableView.isMore = NO;
//    _puchaseTableView.tableHeaderView = headerView;
    [self.view addSubview:_puchaseTableView];
    _puchaseTableView.hidden = YES;
    
}

-(void)viewDidLayoutSubviews
{
    if ([self.listTableView respondsToSelector:@selector(setSeparatorInset:)]) {
        [self.listTableView setSeparatorInset:UIEdgeInsetsMake(0,0,0,0)];
    }
    
    if ([self.listTableView respondsToSelector:@selector(setLayoutMargins:)]) {
        [self.listTableView setLayoutMargins:UIEdgeInsetsMake(0,0,0,0)];
    }
    
    if ([self.puchaseTableView respondsToSelector:@selector(setSeparatorInset:)]) {
        [self.puchaseTableView setSeparatorInset:UIEdgeInsetsMake(0,0,0,0)];
    }
    
    if ([self.puchaseTableView respondsToSelector:@selector(setLayoutMargins:)]) {
        [self.puchaseTableView setLayoutMargins:UIEdgeInsetsMake(0,0,0,0)];
    }
}

- (UIView *)filedView:(CGRect)rect {
    UIImageView *backgroundView = [[UIImageView alloc] initWithFrame:rect];
    backgroundView.tag = filedViewTag;
    UIImage *bjImg = [UIImage imageNamed:WhiltWithLineImgName];
    bjImg = [bjImg resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
    backgroundView.image = bjImg;
    UIFont *bottomFont = [UIFont systemFontOfSize:10.f];
    
    UIColor *firstColor = [UIColor orangeColor];
    UILabel *label1 = [UILabel labelWithTitle:@"商品名称"];
    label1.frame = CGRectMake(5, 13, 70, 13);
    label1.font = [UIFont boldSystemFontOfSize:FONT_13];
    [backgroundView addSubview:label1];
    
    UILabel *label2 = [UILabel labelWithTitle:@"(单位:mm)"];
    label2.frame = CGRectMake(label1.vleft, label1.vbottom, label1.vwidth, label1.vheight);
    label2.font = bottomFont;
    label2.textColor = firstColor;
    [backgroundView addSubview:label2];
    
    UILabel *label3 = [UILabel labelWithTitle:@"今日参考价格"];
    label3.frame = CGRectMake(label1.vright-13, label1.vtop, 80, label1.vheight);
    label3.textAlignment = NSTextAlignmentCenter;
    label3.font = [UIFont boldSystemFontOfSize:FONT_13];
    [backgroundView addSubview:label3];
    
    UILabel *label4 = [UILabel labelWithTitle:@"(单位:元/吨)"];
    label4.frame = CGRectMake(label3.vleft, label3.vbottom, label3.vwidth, label3.vheight);
    label4.textAlignment = NSTextAlignmentCenter;
    label4.font = bottomFont;
    label4.textColor = firstColor;
    [backgroundView addSubview:label4];
    
    UILabel *label5 = [UILabel labelWithTitle:@"一周价格预测"];
    label5.frame = CGRectMake(label3.vright, label3.vtop, label3.vwidth+10, label3.vheight);
    label5.textAlignment = NSTextAlignmentCenter;
    label5.font = [UIFont boldSystemFontOfSize:FONT_13];
    [backgroundView addSubview:label5];
    
    UILabel *label6 = [UILabel labelWithTitle:@"(单位:元/吨)"];
    label6.frame = CGRectMake(label5.vleft, label5.vbottom, label3.vwidth, label3.vheight);
    label6.textAlignment = NSTextAlignmentCenter;
    label6.textColor = firstColor;
    label6.font = bottomFont;
    [backgroundView addSubview:label6];
    
    UILabel *label7 = [UILabel labelWithTitle:@"二周价格预测"];
    label7.frame = CGRectMake(label5.vright, label3.vtop, label5.vwidth, label3.vheight);
    label7.textAlignment = NSTextAlignmentCenter;
    label7.font = [UIFont boldSystemFontOfSize:FONT_13];
    [backgroundView addSubview:label7];
    
    UILabel *label8 = [UILabel labelWithTitle:@"(单位:元/吨)"];
    label8.frame = CGRectMake(label7.vleft, label7.vbottom, label3.vwidth, label3.vheight);
    label8.textAlignment = NSTextAlignmentCenter;
    label8.textColor = firstColor;
    label8.font = bottomFont;
    [backgroundView addSubview:label8];
    
    return backgroundView;
}

#pragma mark - UIActions
- (void)choseBusinessType:(UISegmentedControl *)segment {
    if (segment.selectedSegmentIndex == 1) {
        _puchaseTableView.hidden = NO;
        _listTableView.hidden = YES;
    }else {
        _puchaseTableView.hidden = YES;
        _listTableView.hidden = NO;
    }
    [self requestNet];
}

- (void)choseRegion:(UIButton *)btn {
    [self indicateArrow];
    
    NSArray *sections = [[SynacInstance sharedInstance] riverSectionsNames];
    IBActionSheet *sheet = [[IBActionSheet alloc] initWithTitle:@"选择地段" delegate:self cancelButtonTitle:globe_cancel_str destructiveButtonTitle:nil otherButtonTitles:nil, nil];
    for (NSString *name in sections) {
        [sheet addButtonWithTitle:name];
    }
    sheet.markIndex = _markIndex;
    [sheet showInView:self.view];
}

#pragma mark - Net
/**
 *@brief 获取今日价格列表的请求参数
 */
- (NSMutableDictionary *)productTodayInfoRequestParams {
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    NSString *pcode = _segment.selectedSegmentIndex ? glProduct_top_stone_code : glProduct_top_send_code;
    RiverSectionModel *cruentModel = nil;
    for (RiverSectionModel *model in [[SynacInstance sharedInstance] riverSectionsArray]) {
        if ([model.riverSectionName isEqualToString:_choseBtn.titleLabel.text]) {
            cruentModel = model;
        }
    }
    NSString *area = cruentModel.riverSectionVal;
    [params setObject:pcode forKey:@"pcode"];
    [params setObject:area forKey:@"area"];
    
    return params;
}

/**
 *@brief 获取商品预测价格列表数据
 */
- (void)requestNet {
//    [self currentTableView].hidden = YES;
    [super requestNet];
    [[self currentTableView].header beginRefreshing];
}

- (void)refrush:(BOOL)refreshControl targetTable:(BaseTableView *)tableView{
    NSMutableDictionary *params = [self productTodayInfoRequestParams];
    BOOL useCache = refreshControl ? NO : YES;
    BOOL isloadMore = tableView.pageIndex > 1 ? YES : NO;
    if (!isloadMore && tableView.dataArray.count <= 0) {
        useCache = NO;
    }
    __block typeof(self) this = self;
    [self requestWithURL:bProductTomrrowInfo params:params HTTPMethod:kHttpGetMethod shouldCache:useCache completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [tableView.header endRefreshing];
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *req){
        [tableView.header endRefreshing];
    }];
}

- (void)handleNetData:(id)responseData {
    id  datas = [responseData objectForKey:ServiceDataKey];
    [self currentTableView].hidden = NO;
    NSMutableArray *temp = [NSMutableArray array];
    for (NSDictionary *dic in datas) {
        ProductWeekModel *model = [[ProductWeekModel alloc] initWithDataDic:dic];
        [temp addObject:model];
    }
    
    // 黄砂数据处理
    SynacInstance *synac = [SynacInstance sharedInstance];
    NSString *pcode = _segment.selectedSegmentIndex ? glProduct_top_stone_code : glProduct_top_send_code;
    if ([pcode isEqualToString:glProduct_top_send_code]) {
        NSMutableArray *arrayTemp = [NSMutableArray array];
        for (GoodsModel *goodModel in synac.sendSubType) {
            NSString *key = goodModel.goodsName;
            NSMutableArray *weekModelTemp = [NSMutableArray array];
            for (ProductWeekModel *weekModel in temp) {
                if ([weekModel.ptype isEqualToString:goodModel.goodsVal]) {
                    [weekModelTemp addObject:weekModel];
                }
            }
            [arrayTemp addObject:@{key:weekModelTemp}];
        }
        [[self currentTableView] setDataArray:arrayTemp];
    }else {
        
        [[self currentTableView] setDataArray:temp];
    }
    
    [[self currentTableView] reloadData];
}

- (void)handleRequestFailed:(ASIHTTPRequest *)req {
    [super commandHandle:req];
    if (self.shouldShowFailView && !self.failView.superview) {
        [self.view addSubview:[self failViewWithFrame:CGRectMake(0, 50, self.view.vwidth, self.view.vheight-50) empty:NO]];
    }else {
        [self showTip:kNetError];
    }
}

#pragma mark - Private
static int selectFlag = 0;
- (void)indicateArrow {
    float duration = 0.25;
    
    if (selectFlag) {
        selectFlag = 0;
        [UIView animateWithDuration:duration animations:^{
            [_choseBtn.imageView.layer setTransform:CATransform3DIdentity];
        }];
    }else {
        selectFlag = 1;
        [UIView animateWithDuration:duration animations:^{
            [_choseBtn.imageView.layer setTransform:CATransform3DMakeRotation(-M_PI/1.0000001, 0, 0, 1)];
        }];
    }
}

- (BaseTableView *)currentTableView {
    return _segment.selectedSegmentIndex == 0 ? _listTableView : _puchaseTableView;
}

#pragma mark - IBActionSheet Delegate
- (void)actionSheet:(IBActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex != actionSheet.cancelButtonIndex) {
        _markIndex = buttonIndex;
        
        [_choseBtn setTitle:[actionSheet buttonTitleAtIndex:buttonIndex] forState:UIControlStateNormal];
        [self requestNet];
    }
}

- (void)actionWillDismiss:(IBActionSheet *)actionSheet {
    [self indicateArrow];
}

@end
