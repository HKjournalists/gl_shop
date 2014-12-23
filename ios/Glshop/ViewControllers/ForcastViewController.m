//
//  ForcastViewController.m
//  Glshop
//
//  Created by River on 14-11-21.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "ForcastViewController.h"
#import "ButtonWithTitleAndImage.h"
#import "ForcastTableView.h"
#import "SecondTableView.h"
#import "ProductWeekModel.h"

@interface ForcastViewController () <UIActionSheetDelegate,UITableViewEventDelegate>

@property (nonatomic, strong) UISegmentedControl *segment; // 选择产品类型
@property (nonatomic, strong) ButtonWithTitleAndImage *choseBtn; // 地域选择
@property (nonatomic, strong) ForcastTableView *listTableView;
@property (nonatomic, strong) SecondTableView *puchaseTableView;

Strong NSArray *sandArray;
Strong NSArray *stoneArray;

@end

@implementation ForcastViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self requestNet];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    [MKNetworkEngine cancelOperationsContainingURLString:bProductTomrrowInfo];
}

#pragma mark - UI
- (void)loadSubViews {
    UIView *headerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 50)];
    headerView.backgroundColor = self.view.backgroundColor;
    headerView.layer.shadowOffset = CGSizeMake(headerView.vwidth, 2);
    headerView.layer.shadowColor = [UIColor lightGrayColor].CGColor;
    [self.view addSubview:headerView];
    
    _segment = [[UISegmentedControl alloc] initWithItems:@[@"黄沙",@"石子"]];
    _segment.frame = CGRectMake(10, 10, 180, 35);
    _segment.tintColor = [UIColor orangeColor];
    [_segment setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:
                                      [UIColor grayColor], NSForegroundColorAttributeName,
                                      [UIFont systemFontOfSize:17.f], NSFontAttributeName,
                                      nil] forState:UIControlStateNormal];
    _segment.selectedSegmentIndex = 0;
    [_segment addTarget:self action:@selector(choseBusinessType:) forControlEvents:UIControlEventValueChanged];
    [headerView addSubview:_segment];
    
    RiverSectionModel *model;
    if ([[SynacInstance sharedInstance]riverSectionsArray].count > 0) {
        model = [[SynacInstance sharedInstance]riverSectionsArray][0];
    }
    _choseBtn = [ButtonWithTitleAndImage buttonWithType:UIButtonTypeCustom];
    _choseBtn.frame = CGRectMake(_segment.vright+SCREEN_WIDTH-310, _segment.vtop, 110, _segment.vheight);
    [_choseBtn setImage:[UIImage imageNamed:@"index_icon_arrow_down"] forState:UIControlStateNormal];
    [_choseBtn addTarget:self action:@selector(choseRegion:) forControlEvents:UIControlEventTouchUpInside];
    [_choseBtn setTitle:model.riverSectionName forState:UIControlStateNormal];
    [_choseBtn setTitleColor:ColorWithHex(@"#646464") forState:UIControlStateNormal];
    _choseBtn.layer.borderColor = [UIColor orangeColor].CGColor;
    _choseBtn.layer.cornerRadius = 3.f;
    _choseBtn.layer.borderWidth = 1;
    [headerView addSubview:_choseBtn];
    
    [self.view addSubview:[self filedView:CGRectMake(0, headerView.vbottom, headerView.vwidth, 50)]];
    
    
    // 黄砂
    _listTableView = [[ForcastTableView alloc] initWithFrame:CGRectMake(0, headerView.vbottom+50, self.view.vwidth, self.view.vheight-headerView.vheight-kTopBarHeight-10) style:UITableViewStylePlain];
    self.listTableView.contentOffset = CGPointMake(0, -44);
    _listTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [_listTableView.refreshControl beginRefreshing];
    [_listTableView.refreshControl addTarget:self action:@selector(refrush:) forControlEvents:UIControlEventValueChanged];
    _listTableView.eventDelegate = self;
    _listTableView.isMore = NO;
    [self.view addSubview:_listTableView];
    
    // 石子
    _puchaseTableView = [[SecondTableView alloc] initWithFrame:CGRectMake(0, headerView.vbottom+50, self.view.vwidth, self.view.vheight-headerView.vheight-kTopBarHeight-10) style:UITableViewStylePlain];
    [_puchaseTableView.refreshControl addTarget:self action:@selector(refrush:) forControlEvents:UIControlEventValueChanged];
    _puchaseTableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    _puchaseTableView.eventDelegate = self;
    _puchaseTableView.maxLoadsCount = 20;
    _puchaseTableView.isMore = NO;
    [self.view addSubview:_puchaseTableView];
    _puchaseTableView.hidden = YES;
    
}

- (UIView *)filedView:(CGRect)rect {
    UIView *backgroundView = [[UIView alloc] initWithFrame:rect];
    backgroundView.backgroundColor = RGB(247, 247, 247, 1);
    
    UIColor *firstColor = RGB(118, 118, 118, 1);
    UILabel *label1 = [UILabel labelWithTitle:@"商品"];
    label1.frame = CGRectMake(10, 13, 50, 13);
    label1.textColor = firstColor;
    label1.font = [UIFont boldSystemFontOfSize:13.f];
    [backgroundView addSubview:label1];
    
    UILabel *label2 = [UILabel labelWithTitle:@"名称"];
    label2.frame = CGRectMake(label1.vleft, label1.vbottom, label1.vwidth, label1.vheight);
    label2.font = [UIFont boldSystemFontOfSize:13.f];
    label2.textColor = firstColor;
    [backgroundView addSubview:label2];
    
    UILabel *label3 = [UILabel labelWithTitle:@"今日价格"];
    label3.frame = CGRectMake(label1.vright, label1.vtop, 80, label1.vheight);
    label3.textAlignment = NSTextAlignmentCenter;
    label3.textColor = firstColor;
    label3.font = [UIFont boldSystemFontOfSize:13.f];
    [backgroundView addSubview:label3];
    
    UILabel *label4 = [UILabel labelWithTitle:@"(元/吨)"];
    label4.frame = CGRectMake(label3.vleft, label3.vbottom, label3.vwidth, label3.vheight);
    label4.textAlignment = NSTextAlignmentCenter;
    label4.font = [UIFont boldSystemFontOfSize:13.f];
    [backgroundView addSubview:label4];
    
    UILabel *label5 = [UILabel labelWithTitle:@"1周价格预测"];
    label5.frame = CGRectMake(label3.vright, label3.vtop, label3.vwidth+10, label3.vheight);
    label5.textAlignment = NSTextAlignmentCenter;
    label5.textColor = firstColor;
    label5.font = [UIFont boldSystemFontOfSize:13.f];
    [backgroundView addSubview:label5];
    
    UILabel *label6 = [UILabel labelWithTitle:@"(元/吨)"];
    label6.frame = CGRectMake(label5.vleft, label5.vbottom, label3.vwidth, label3.vheight);
    label6.textAlignment = NSTextAlignmentCenter;
    label6.font = [UIFont boldSystemFontOfSize:13.f];
    [backgroundView addSubview:label6];
    
    UILabel *label7 = [UILabel labelWithTitle:@"2周价格预测"];
    label7.frame = CGRectMake(label5.vright, label3.vtop, label5.vwidth, label3.vheight);
    label7.textAlignment = NSTextAlignmentCenter;
    label7.textColor = firstColor;
    label7.font = [UIFont boldSystemFontOfSize:13.f];
    [backgroundView addSubview:label7];
    
    UILabel *label8 = [UILabel labelWithTitle:@"(元/吨)"];
    label8.frame = CGRectMake(label7.vleft, label7.vbottom, label3.vwidth, label3.vheight);
    label8.textAlignment = NSTextAlignmentCenter;
    label8.font = [UIFont boldSystemFontOfSize:13.f];
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
    
    UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:@"选择港口" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:nil, nil];
    for (NSString *name in sections) {
        [sheet addButtonWithTitle:name];
    }
    [sheet showInView:self.view];
}

#pragma mark - Net
/**
 *@brief 获取今日价格列表的请求参数
 */
- (NSMutableDictionary *)productTodayInfoRequestParams {
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    NSString *pcode = _segment.selectedSegmentIndex ? TopProductStonePcode : TopProductSendPcode;
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
    [self refrush:nil];
}

- (void)refrush:(ODRefreshControl *)refreshControl {
    NSMutableDictionary *params = [self productTodayInfoRequestParams];
    BOOL useCache = refreshControl ? NO : YES;
    [self requestWithURL:bProductTomrrowInfo params:params HTTPMethod:kHttpGetMethod shouldCache:useCache completeBlock:^(ASIHTTPRequest *request, id responseData) {
        id  datas = [responseData objectForKey:@"DATA"];
        NSMutableArray *temp = [NSMutableArray array];
        for (NSDictionary *dic in datas) {
            ProductWeekModel *model = [[ProductWeekModel alloc] initWithDataDic:dic];
            [temp addObject:model];
        }
        
        [[self currentTableView].refreshControl endRefreshing];
        [[self currentTableView] setDataArray:temp];
        [[self currentTableView] reloadData];
    } failedBlock:^{
        
    }];
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

#pragma mark - UIActionSheet Delegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    [self indicateArrow];
    
    if (buttonIndex != 0) {
        [_choseBtn setTitle:[actionSheet buttonTitleAtIndex:buttonIndex] forState:UIControlStateNormal];
        [self requestNet];
    }
}

@end
