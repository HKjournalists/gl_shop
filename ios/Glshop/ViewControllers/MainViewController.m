//
//  MainViewController.m
//  Glshop
//
//  Created by River on 14-11-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "MainViewController.h"
#import "NetEngine.h"
#import "LoginViewController.h"
#import "OpenUDID.h"
#import "IQKeyboardManager.h"
#import "ImagePlayerView.h"
#import "ButtonWithTitleAndImage.h"
#import "ItemListView.h"
#import "MainItemView.h"
#import "BusinessViewController.h"
#import "MySupplyViewController.h"
#import "MyContractViewController.h"
#import "MypurseViewController.h"
#import "ProfileViewController.h"
#import "ProductTodayModel.h"
#import "ForcastViewController.h"
#import "CompanyAuthViewController.h"
#import "BrowseViewController.h"

@interface MainViewController () <ImagePlayerViewDelegate,UIActionSheetDelegate>

@property (nonatomic, strong) ImagePlayerView *banerView;
@property (nonatomic, strong) UIView *middleView;
@property (nonatomic, strong) UIView *bottomView;
@property (nonatomic, strong) UISegmentedControl *segment; // 选择产品类型
@property (nonatomic, strong) ItemListView *itemListView;   // 产品价格走势列表
@property (nonatomic, strong) ButtonWithTitleAndImage *choseBtn; // 地域选择

Strong UIView *failedView;

@end

@implementation MainViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(userLoginNotification:) name:kUserDidLoginNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(userLogoutNotification:) name:kUserDidLogoutNotification object:nil];
    
//    [[IQKeyboardManager sharedManager] setShouldResignOnTouchOutside:YES];
    
//    [self syncData];
    
    [self autoLogin];
    
    [self requestNet];
    
//    DLog(@"%@",[NSString documentsPath]);
    
}

#pragma mark - UI
- (void)loadSubViews {
    [self _loadHeaderView];
    [self _loadMiddleView];
    [self _loadBottomView];
}

- (void)_loadHeaderView {
    float height = iPhone4 ? 90 : 115;

    _banerView = [[ImagePlayerView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, height)];
    [self.view addSubview:_banerView];
    self.banerView.imagePlayerViewDelegate = self;
    self.banerView.scrollInterval = 5.0f;
    self.banerView.pageControlPosition = ICPageControlPosition_BottomRight;
    self.banerView.hidePageControl = NO;
    [self.banerView reloadData];
}

- (void)_loadMiddleView {
    float middleHeight = 180;
    if (iPhone4) {
        middleHeight = 165;
    }
    _middleView = [[UIView alloc] initWithFrame:CGRectMake(_banerView.vleft, _banerView.vbottom, self.view.vwidth, middleHeight)];
    [self.view addSubview:_middleView];
    
    UILabel *todayPriceLabel = [UILabel labelWithTitle:@"今日价格"];
    todayPriceLabel.textColor = ColorWithHex(@"#646464");
    todayPriceLabel.frame = CGRectMake(10, 5, 70, 20);
    [_middleView addSubview:todayPriceLabel];
    
    UILabel *unitLabel = [UILabel labelWithTitle:@"(单位：元/吨)"];
    unitLabel.font = [UIFont systemFontOfSize:13.f];
    unitLabel.textColor = ColorWithHex(@"#999999");
    unitLabel.frame = CGRectMake(todayPriceLabel.vright+2, todayPriceLabel.vtop, 120, 20);
    [_middleView addSubview:unitLabel];
    
    ButtonWithTitleAndImage *marketBtn = [ButtonWithTitleAndImage buttonWithType:UIButtonTypeCustom];
    marketBtn.frame = CGRectMake(self.view.vright-100, unitLabel.vtop-6, 110, 35);
    [marketBtn setImage:[UIImage imageNamed:@"index_icon_arrow_zuo"] forState:UIControlStateNormal];
    [marketBtn setTitle:@"市场行情" forState:UIControlStateNormal];
    [marketBtn addTarget:self action:@selector(skipToForcastVC:) forControlEvents:UIControlEventTouchUpInside];
    [marketBtn setTitleColor:ColorWithHex(@"#ff6600") forState:UIControlStateNormal];
    [_middleView addSubview:marketBtn];
    
    float gap = 8;
    if (iPhone5 || iPhone6) {
        gap = 15;
    }else if (iPhone6plus) {
        gap = 25;
    }
    _segment = [[UISegmentedControl alloc] initWithItems:@[@"黄砂",@"石子"]];
    _segment.frame = CGRectMake(10, todayPriceLabel.vbottom+gap, 180, 35);
//    _segment.tintColor = [UIColor orangeColor];
    [_segment setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:
                                     [UIColor grayColor], NSForegroundColorAttributeName,
                                     [UIFont systemFontOfSize:17.f], NSFontAttributeName,
                                      nil] forState:UIControlStateNormal];
    _segment.selectedSegmentIndex = 0;
    [_segment addTarget:self action:@selector(choseProduct:) forControlEvents:UIControlEventValueChanged];
    [_middleView addSubview:_segment];
    
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
    _choseBtn.layer.borderColor = [UIColor lightGrayColor].CGColor;
    _choseBtn.layer.cornerRadius = 3.f;
    _choseBtn.layer.borderWidth = 1;
    [_middleView addSubview:_choseBtn];
    
    _itemListView = [[ItemListView alloc] initWithFrame:CGRectMake(0, _segment.vbottom+gap, _middleView.vwidth, 90)];
    [_middleView addSubview:_itemListView];
    
    
}

#define Item_size_width 53
#define Item_size_height 45
- (void)_loadBottomView {
    _bottomView = [[UIView alloc] initWithFrame:CGRectMake(0, _middleView.vbottom+5, self.view.vwidth, SCREEN_HEIGHT-kTopBarHeight-_banerView.vheight-_middleView.vheight)];
    _bottomView.backgroundColor = [UIColor whiteColor];
    [self.view addSubview:_bottomView];
    
    UIImageView *logoView = [[UIImageView alloc] initWithFrame:CGRectMake(_bottomView.vwidth/2-35, _bottomView.vheight/2-10, 70, 70)];
    logoView.image = [UIImage imageNamed:@"index_icon_yonghu"];
    [_bottomView addSubview:logoView];
    
    NSArray *itemTitles = @[@"找买找卖",@"我的供求",@"我的合同",@"我的钱包",@"我的资料",];
    NSArray *itemImages = @[@"index_icon_fangdajing",
                            @"index_icon_menu",
                            @"index_icon_hetong_touch",
                            @"index_icon_qiandai",
                            @"index_icon_rentou",];
    
    NSArray *itemHightlightImages = @[@"index_icon_fangdajing_touch",
                                      @"index_icon_menu_touch",
                                      @"index_icon_hetong",
                                      @"index_icon_qiandai_touch",
                                      @"index_icon_rentou_touch",];
    
    // 找买找卖
    MainItemView *findBussineItem = [self createCommentItemView:itemTitles[0] imageName:itemImages[0] hightLiaghImageName:itemHightlightImages[0] itemTag:200];
    findBussineItem.frame = CGRectMake(logoView.vleft-Item_size_width-60, logoView.vtop+35, Item_size_width, Item_size_height);
    [_bottomView addSubview:findBussineItem];
    
    // 我的供求
    MainItemView *mySupplyItem = [self createCommentItemView:itemTitles[1] imageName:itemImages[1] hightLiaghImageName:itemHightlightImages[1] itemTag:201];
    mySupplyItem.frame = CGRectMake(logoView.vleft-Item_size_width-10, logoView.vtop-Item_size_height+25, Item_size_width, Item_size_height);
    [_bottomView addSubview:mySupplyItem];
    
    // 我的合同
    NSInteger topGap = iPhone4 ? 20 : 30;
    MainItemView *myContractItem = [self createCommentItemView:itemTitles[2] imageName:itemImages[2] hightLiaghImageName:itemHightlightImages[2] itemTag:202];
    myContractItem.frame = CGRectMake(logoView.center.x-Item_size_width/2, logoView.center.y-35-Item_size_height-topGap, Item_size_width, Item_size_height);
    [_bottomView addSubview:myContractItem];
    
    // 我的钱包
    MainItemView *myPurseItem = [self createCommentItemView:itemTitles[3] imageName:itemImages[3] hightLiaghImageName:itemHightlightImages[3] itemTag:203];
    myPurseItem.frame = CGRectMake(logoView.vright+10, mySupplyItem.vtop, Item_size_width, Item_size_height);
    [_bottomView addSubview:myPurseItem];
    
    // 我的资料
    MainItemView *profileItem = [self createCommentItemView:itemTitles[4] imageName:itemImages[4] hightLiaghImageName:itemHightlightImages[4] itemTag:204];
    profileItem.frame = CGRectMake(logoView.vright+60, findBussineItem.vtop, Item_size_width, Item_size_height);
    [_bottomView addSubview:profileItem];
}

#pragma mark - Private
/**
 *@brief 自动登录
 */
- (void)autoLogin {
    
    NSString *udid = [OpenUDID value];
    NSString *password = [[NSUserDefaults standardUserDefaults] objectForKey:kUserPasswordkey];
    NSString *userName = [[NSUserDefaults standardUserDefaults] objectForKey:kUserNamekey];
    if (userName.length>0 && password.length>0) {
        
        NSMutableDictionary *param = [NSMutableDictionary dictionaryWithObjectsAndKeys:userName,@"username",password,@"password",udid,@"clientid",IntToNSNumber(1),@"clienttype", nil];
        [self requestWithURL:bUserLoginPath params:param
                  HTTPMethod:kHttpPostMethod
                 shouldCache:NO
                  needHeader:NO
               completeBlock:^(ASIHTTPRequest *request, id responseData) {
                   
            NSArray *dicArray = [responseData objectForKey:ServiceDataKey];
            [self _loginSuccess:dicArray];
        } dataConflictBlock:^(ASIHTTPRequest *request, id responseData) {
            [self _setupRightNavationItem];
        } failedBlock:^(ASIHTTPRequest *req){
            [self _setupRightNavationItem];
        }];
    }else {
        [self _setupRightNavationItem];
    }
    


}

/**
 *@brief 自动登入成功
 */
- (void)_loginSuccess:(NSArray *)datas {
    // 发送通知，用户已登入
    [[NSNotificationCenter defaultCenter] postNotificationName:kUserDidLoginNotification object:nil];
    
    // 保存用户信息
    NSDictionary *dic = [NSDictionary dictionary];
    if (datas.count > 0)
        dic = datas.firstObject;
    UserModel *model = [[UserModel alloc] initWithDataDic:dic];
    UserInstance *userInstance = [UserInstance sharedInstance];
    userInstance.user = model;
}

/**
 *@brief 如果是登录了，显示“帮助”按钮；如果未登录，显示“登录”按钮
 */
- (void)_setupRightNavationItem {
    NSString *itemTitle;
    UserInstance *user = [UserInstance sharedInstance];
    SEL selector;
    if ([user login]) {
        itemTitle = @"帮助";
        selector =  @selector(skipHelpVC);
        
        UIButton *messageBtn = [UIButton buttonWithType:UIButtonTypeCustom];
        [messageBtn setImage:[UIImage imageNamed:@"index_icon_envelope"] forState:UIControlStateNormal];
        messageBtn.frame = CGRectMake(0, 0, 20, 44);
        UIBarButtonItem *leftItem = [[UIBarButtonItem alloc] initWithCustomView:messageBtn];
        self.navigationItem.leftBarButtonItem = leftItem;
    }else {
        itemTitle = @"登录";
        selector = @selector(login:);
    }

    UIButton *loginBtn = [UIButton buttonWithTip:itemTitle target:self selector:selector];
    [loginBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    loginBtn.frame = CGRectMake(0, 0, 40, 44);
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithCustomView:loginBtn];
    self.navigationItem.rightBarButtonItem = rightItem;

}

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

/**
 *@brief 创建菜单按钮
 */
- (MainItemView *)createCommentItemView:(NSString *)title imageName:(NSString *)imgName hightLiaghImageName:(NSString *)hlImgName itemTag:(NSInteger)tag{
    MainItemView *item = [[MainItemView alloc] initWithFrame:CGRectZero];
    [item setTitle:title forState:UIControlStateNormal];
    [item setTitleColor:CJBtnColor forState:UIControlStateHighlighted];
    item.tag = tag;
    [item setTitleColor:ColorWithHex(@"#666666") forState:UIControlStateNormal];
    item.titleLabel.font = [UIFont boldSystemFontOfSize:13.f];
    [item addTarget:self action:@selector(skip:) forControlEvents:UIControlEventTouchUpInside];
    [item setImage:[UIImage imageNamed:imgName] forState:UIControlStateNormal];
    [item setImage:[UIImage imageNamed:hlImgName] forState:UIControlStateHighlighted];
    
    return item;
}

#pragma mark - Net
- (void)requestNet {
    if (_failedView) {
        [_failedView removeFromSuperview];
        _failedView = nil;
    }
    
    NSMutableDictionary *params = [self productTodayInfoRequestParams];
    [self.itemListView showWithTip:kRefrushing];
    
    [self requestWithURL:bProductTodayInfo params:params HTTPMethod:kHttpGetMethod shouldCache:YES completeBlock:^(ASIHTTPRequest *request, id responseData) {
        [_itemListView hideLoading];
//        kASIResultLog;
        NSArray *datas = [responseData objectForKey:ServiceDataKey];
        NSMutableArray *temp = [NSMutableArray array];
        for (NSDictionary *dic in datas)
        {
            ProductTodayModel *model = [[ProductTodayModel alloc] initWithDataDic:dic];
            [temp addObject:model];
        }
        _itemListView.items = [NSArray arrayWithArray:temp];
        [_itemListView.collectionView setContentOffset:CGPointZero animated:YES];
        [_itemListView.collectionView reloadData];
        if (temp.count == 0) {
            [self handleRequestFaileds:ShowDataEmptyStr];
        }
    } failedBlock:^ (ASIHTTPRequest *req){
        [self.itemListView hideLoading];
        [self handleRequestFaileds:ShowNetErrorStr];
    }];
}

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
 *@brief 获取今日价格列表失败
 */
- (void)handleRequestFaileds:(NSString *)tip {
    if (!_failedView) {
        _failedView = [[UIView alloc] initWithFrame:self.itemListView.bounds];
        UILabel *label = [UILabel labelWithTitle:tip];
        label.frame = CGRectMake(0, 0, _failedView.vwidth, _failedView.vheight);
        label.textAlignment = NSTextAlignmentCenter;
        [_failedView addSubview:label];
        
        UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(requestNet)];
        [_failedView addGestureRecognizer:tap];
    }
    
    if (!_failedView.superview) {
        
        [self.itemListView addSubview:_failedView];
    }
}

#pragma mark - UIAction
- (void)login:(UIButton *)btn {
    LoginViewController *loginVC = [mainStoryBoard instantiateViewControllerWithIdentifier:@"LoginViewControllerId"];
    [self.navigationController pushViewController:loginVC animated:YES];
} // 点击登录

- (void)choseProduct:(UISegmentedControl *)segment {
    self.itemListView.items = nil;
    [self.itemListView.collectionView reloadData];
    [self requestNet];
}

- (void)skipHelpVC {
    
} // 跳转到帮助页面

- (void)skipToForcastVC:(UIButton *)btn {
    ForcastViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"ForcastViewControllerId"];
    [self.navigationController pushViewController:vc animated:YES];
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

- (void)skip:(UIButton *)btn {
    NSInteger itemTag = btn.tag;
    
//    if (![[UserInstance sharedInstance] login] && itemTag != 200) {
//        HUD(@"您还没有登录");
//        return;
//    }
    
    switch (itemTag) {
        case 200:
        {
            BusinessViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"BusinessViewControllerId"];
            self.navigationItem.backBarButtonItem = [[UIBarButtonItem alloc]
                                                     initWithTitle:@"返回"
                                                     style:UIBarButtonItemStylePlain
                                                     target:self
                                                     action:nil];
            [self.navigationController pushViewController:vc animated:YES];
        }
            break;
            
        case 201:
        {
            MySupplyViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"MySupplyViewControllerId"];
            [self.navigationController pushViewController:vc animated:YES];
            self.navigationItem.backBarButtonItem = [[UIBarButtonItem alloc]
                                                     initWithTitle:@"返回"
                                                     style:UIBarButtonItemStylePlain
                                                     target:self
                                                     action:nil];
        }
            break;
            
        case 202:
        {
            MyContractViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"MyContractViewControllerId"];
            [self.navigationController pushViewController:vc animated:YES];
        }
            break;
            
        case 203:
        {
            MypurseViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"MypurseViewControllerId"];
            [self.navigationController pushViewController:vc animated:YES];
//            CompanyAuthViewController *vc = [[CompanyAuthViewController alloc] init];
//            [self.navigationController pushViewController:vc animated:YES];

        }
            break;
            
        case 204:
        {
            ProfileViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"ProfileViewControllerId"];
            [self.navigationController pushViewController:vc animated:YES];
        }
            break;
            
        default:
            break;
    }
} // 模块页面跳转

#pragma mark - NSNotification
- (void)userLoginNotification:(NSNotification *)notification {
    
    [self performSelectorOnMainThread:@selector(_setupRightNavationItem) withObject:nil waitUntilDone:NO];
}

- (void)userLogoutNotification:(NSNotification *)notification {
    [self performSelectorOnMainThread:@selector(_setupRightNavationItem) withObject:nil waitUntilDone:NO];
}

#pragma mark - ImagePlayerView Delegate
- (NSInteger)numberOfItems
{
    return 4;
}

- (void)imagePlayerView:(ImagePlayerView *)imagePlayerView loadImageForImageView:(UIImageView *)imageView index:(NSInteger)index
{
    
    dispatch_async(dispatch_get_main_queue(), ^{
        imageView.image = [UIImage imageNamed:[NSString stringWithFormat:@"index_banner%d",index+1]];
    });
    
}

- (void)imagePlayerView:(ImagePlayerView *)imagePlayerView didTapAtIndex:(NSInteger)index
{
    NSLog(@"did tap index = %d", (int)index);
}

#pragma mark - UIActionSheet Delegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    [self indicateArrow];

    if (buttonIndex != 0) {
        [_choseBtn setTitle:[actionSheet buttonTitleAtIndex:buttonIndex] forState:UIControlStateNormal];
        [self requestNet];
    }
}

#pragma mark - 数据同步
/**
 *@brief 每次启动需要同步一次数据
 */
- (void)syncData {
    NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithObjectsAndKeys:@"",@"typeInfo", nil];
    
    [self requestWithURL:bSyncGetInfo
                  params:dic HTTPMethod:kHttpGetMethod
             shouldCache:YES
           completeBlock:^(ASIHTTPRequest *request, id responseData) {
               
        kASIResultLog;
//        NSDictionary *responseDic = [responseData objectForKey:@"DATA"];
//        NSArray *areas = [[responseDic objectForKey:@"area"] objectForKey:@"data"];
//        NSMutableArray *areatemp = [NSMutableArray array];
//        for (NSDictionary *dic in areas) {
//            AreaModel *area = [[AreaModel alloc] initWithDataDic:dic];
//            [areatemp addObject:area];
//        }
//        
//        NSArray *sectionsArray = [[responseDic objectForKey:@"riverSection"] objectForKey:@"data"];
//        NSMutableArray *sectionstemp = [NSMutableArray array];
//        for (NSDictionary *dic in sectionsArray) {
//            RiverSectionModel *river = [[RiverSectionModel alloc] initWithDataDic:dic];
//            [sectionstemp addObject:river];
//        }
//        
//        SynacInstance *synac = [SynacInstance sharedInstance];
//        synac.riverSectionsArray = [NSArray arrayWithArray:sectionstemp];
//        
//        NSString *pathFile = [[NSString documentsPath] stringByAppendingPathComponent:kSynacFileName];
//        
//        [request.responseData writeToFile:pathFile options:NSDataWritingAtomic error:nil];
        
    } failedBlock:^ (ASIHTTPRequest *req){
        
    }];
}

@end
