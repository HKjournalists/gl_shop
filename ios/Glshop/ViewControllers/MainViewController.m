//
//  MainViewController.m
//  Glshop
//
//  Created by River on 14-11-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "MainViewController.h"
#import "LoginViewController.h"
#import "EGOCache.h"
#import "IQKeyboardManager.h"
#import "ImagePlayerView.h"
#import "ButtonWithTitleAndImage.h"
#import "ItemListView.h"
#import "TDBadgedCell.h"
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
#import "ContractWaitSureViewController.h"
#import "ContractEndedViewController.h"
#import "ContractPorccesingViewController.h"
#import "AssistViewController.h"
#import "MessageViewController.h"
#import "JSONKit.h"
#import "UpdateModel.h"
#import "IBActionSheet.h"
#import "AuthViewController.h"
#import "GroupBuyViewController.h"
#import "MediaViewController.h"




static NSInteger authAlertTag = 3000;
static NSInteger updateAlertTag = 3001;

@interface MainViewController () <ImagePlayerViewDelegate,IBActionSheetDelegate,UIAlertViewDelegate>

Strong ImagePlayerView *banerView;
Strong UIView *middleView;
Strong UIView *bottomView;
Strong UISegmentedControl *segment; // 选择产品类型
Strong ItemListView *itemListView;   // 产品价格走势列表
Strong ButtonWithTitleAndImage *choseBtn; // 地域选择
Strong UIActivityIndicatorView * activityIndicatorView;
Strong UIView *failedView;
Strong TDBadgeView *bage;
@property (nonatomic, strong) UpdateModel *updateModel;

/**
 *@brief 为IBActionSheet记录选择的索引，默认为0
 */
@property (nonatomic, assign) NSInteger markIndex;

@end

@implementation MainViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    if (!kAppStoreVersion) {
        [self checkUpdate];
    }
    
    [self syncData];
    
    [self autoLogin];
    
    [self requestNet];
    
    if ([UIApplication sharedApplication].applicationIconBadgeNumber > 0) {
        [self seqlinBage];
    }
    
}

- (void)initDatas {
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(userLoginNotification:) name:kUserDidLoginNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(userLogoutNotification:) name:kUserDidLogoutNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updateToken) name:UIApplicationWillEnterForegroundNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(saveTimeForBackground) name:UIApplicationDidEnterBackgroundNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(seqlinBage) name:kClearBageNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updateUI) name:synacDataDidFinishNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(updateUserInfo) name:kUpdateUserInfoNitification object:nil];
    
    self.markIndex = 0;
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
    UserInstance *uIns = [UserInstance sharedInstance];
    if (uIns.login) {
        [self inquireUnreadMessage];
    }
}

#pragma mark - Getter
- (UIActivityIndicatorView *)activityIndicatorView {
    if (!_activityIndicatorView) {
        _activityIndicatorView = [[UIActivityIndicatorView alloc] initWithFrame:CGRectMake(0, 0, 40, 40)];
        _activityIndicatorView.activityIndicatorViewStyle = UIActivityIndicatorViewStyleWhite;
    }
    return _activityIndicatorView;
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

- (void)viewDidLayoutSubviews
{
     

}
- (void)_loadMiddleView {
    float middleHeight = 180;
    if (iPhone4) {
        middleHeight = 165;
    }
    _middleView = [[UIView alloc] initWithFrame:CGRectMake(_banerView.vleft, _banerView.vbottom, self.view.vwidth, middleHeight)];
    
    [self.view addSubview:_middleView];
    
    UILabel *todayPriceLabel = [UILabel labelWithTitle:@"今日参考价格"];
    todayPriceLabel.textColor = ColorWithHex(@"#646464");
    todayPriceLabel.frame = CGRectMake(10, 5, 100, 20);
    todayPriceLabel.font = UFONT_16;
    [_middleView addSubview:todayPriceLabel];
    
    UILabel *unitLabel = [UILabel labelWithTitle:@"(单位:元/吨)"];
    unitLabel.font = [UIFont systemFontOfSize:FONT_13];
    unitLabel.textColor = ColorWithHex(@"#999999");
    unitLabel.frame = CGRectMake(todayPriceLabel.vright-2, todayPriceLabel.vtop, 120, 20);
    [_middleView addSubview:unitLabel];
    
    ButtonWithTitleAndImage *marketBtn = [ButtonWithTitleAndImage buttonWithType:UIButtonTypeCustom];
    marketBtn.titleLabel.font = UFONT_16;
    marketBtn.frame = CGRectMake(self.view.vright-100, unitLabel.vtop-6, 110, 35);
    [marketBtn setImage:[UIImage imageNamed:@"index_icon_arrow_zuo"] forState:UIControlStateNormal];
    [marketBtn setTitle:@"价格预测" forState:UIControlStateNormal];
    [marketBtn addTarget:self action:@selector(skipToForcastVC:) forControlEvents:UIControlEventTouchUpInside];
    [marketBtn setTitleColor:ColorWithHex(@"#ff6600") forState:UIControlStateNormal];
    [_middleView addSubview:marketBtn];
    
    float gap = 8;
    if (iPhone5 || iPhone6) {
        gap = 15;
    }else if (iPhone6plus) {
        gap = 25;
    }
    _segment = [[UISegmentedControl alloc] initWithItems:@[glProduct_top_send_name,glProduct_top_stone_name]];
    _segment.frame = CGRectMake(10, todayPriceLabel.vbottom+gap, 180, 35);
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
    NSString *title = model.riverSectionName.length ? model.riverSectionName : @"靖江段";
    _choseBtn = [ButtonWithTitleAndImage buttonWithType:UIButtonTypeCustom];
    _choseBtn.frame = CGRectMake(_segment.vright+SCREEN_WIDTH-310, _segment.vtop, 110, _segment.vheight);
    [_choseBtn setImage:[UIImage imageNamed:@"index_icon_arrow_down"] forState:UIControlStateNormal];
    [_choseBtn addTarget:self action:@selector(choseRegion:) forControlEvents:UIControlEventTouchUpInside];
    [_choseBtn setTitle:title forState:UIControlStateNormal];
    _choseBtn.titleLabel.font = UFONT_16;
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

- (void)updateUI {
    RiverSectionModel *model;
    if ([[SynacInstance sharedInstance]riverSectionsArray].count > 0) {
        model = [[SynacInstance sharedInstance]riverSectionsArray][0];
    }
    NSString *title = model.riverSectionName.length ? model.riverSectionName : @"靖江段";
    [_choseBtn setTitle:title forState:UIControlStateNormal];
}

#pragma mark - Public
- (void)refrushMessageBox:(NSInteger)unReadCount {
    if (unReadCount <= 0) {
        _bage.hidden = YES;
        return;
    }else {
        _bage.hidden = NO;
    }
    
    if (unReadCount >= 99) {
        _bage.badgeString = @"99+";
    }else {
        _bage.badgeString = [NSString stringWithFormat:@"%ld",(long)unReadCount];
    }
    [_bage setNeedsDisplay];
}

#pragma mark - Private
/**
 *@brief 自动登录
 */
- (void)autoLogin {
    NSString *password = [[NSUserDefaults standardUserDefaults] objectForKey:kUserPasswordkey];
    NSString *userName = [[NSUserDefaults standardUserDefaults] objectForKey:kUserNamekey];
    
    NSString *append = [NSString stringWithFormat:@"%@%@",userName,password];
    NSString *securityStr = [append md5];
    if (userName.length>0 && password.length>0) {
        self.activityIndicatorView.frame = CGRectMake(0, 0, 40, 40);
        [self.activityIndicatorView startAnimating];
        self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:self.activityIndicatorView];
   
        __block typeof(self) this = self;
        NSMutableDictionary *param = [NSMutableDictionary dictionaryWithObjectsAndKeys:userName,@"username",securityStr,@"password",IntToNSNumber(1),@"clienttype",[Utilits ToGetdeviceToken],@"clientid", nil];
        if (kAppStoreVersion) {  // 如果是苹果商城的应用
            [param setObject:@"1" forKey:@"channelType"];
        }
        self.title = @"登录中...";
        [self requestWithURL:bUserLoginPath params:param
                  HTTPMethod:kHttpPostMethod
                 shouldCache:NO
                  needHeader:NO
               completeBlock:^(ASIHTTPRequest *request, id responseData) {
                   kASIResultLog;
            NSArray *dicArray = [responseData objectForKey:ServiceDataKey];
            [this _loginSuccess:dicArray];
        } dataConflictBlock:^(ASIHTTPRequest *request, id responseData) {
            [this _setupNavationItem];
        } failedBlock:^(ASIHTTPRequest *req){
            [this _setupNavationItem];
        }];
    }else {
        [self _setupNavationItem];
    }
}

/**
 *@brief 自动登入成功
 */
- (void)_loginSuccess:(NSArray *)datas {
    
    // 保存用户信息
    NSDictionary *dic = [NSDictionary dictionary];
    if (datas.count > 0)
        dic = datas.firstObject;
    UserModel *model = [[UserModel alloc] initWithDataDic:dic];
    UserInstance *userInstance = [UserInstance sharedInstance];
    userInstance.user = model;
    
    // 发送通知，用户已登入
    [[NSNotificationCenter defaultCenter] postNotificationName:kUserDidLoginNotification object:nil];
    
    // 如果有相应的推送消息就进行相应的跳转
    if ([[NSUserDefaults standardUserDefaults] objectForKey:kRemoteNotificationKey]) {
        PushMessageType type = [[PushInstance sharedInstance] pushType];
        if (type != TYPE_USER_LOGIN_OTHER_DEVICE) {
            [[PushInstance sharedInstance] handlePushMessage];
        }
    }
}

- (void)alertToAuth {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:@"很遗憾，您还未加入长江电商平台第三方权威认证体系。认证是免费的，认证通过后可永久点亮专属认证图标，搭建更信赖认证过的用户，同时您获取生意成交的机会也会越高！" delegate:self cancelButtonTitle:@"立即认证"  otherButtonTitles:@"跳过", nil];
    alert.tag = authAlertTag;
    [alert show];
}

/**
 *@brief 查询未读消息数量
 */
- (void)inquireUnreadMessage {
    UserInstance *userInstance = [UserInstance sharedInstance];
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:userInstance.user.cid,@"cid", nil];
    __block typeof(self) this = self;
    [self requestWithURL:bmessageTotal params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        NSDictionary *dic = responseData[ServiceDataKey];
        NSNumber *total = dic[@"total"];
        [this refrushMessageBox:[total integerValue]];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

- (void)checkUpdate {
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:@"3",@"devices",version, @"lastNo", nil];
    [self requestWithURL:bcheckVersion params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [self handleUpDateData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

- (void)handleUpDateData:(id)responseData {
    _updateModel = [[UpdateModel alloc] initWithDataDic:responseData[ServiceDataKey]];
    if (_updateModel.downurl) {
        
        if ([_updateModel.isforce boolValue]) {
            UIView *maskView = [[UIView alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
            maskView.backgroundColor = RGB(20, 20, 20, 0.5);
            [self.view.window addSubview:maskView];
            
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"新版本" message:_updateModel.mark delegate:self cancelButtonTitle:nil  otherButtonTitles:@"去更新", nil];
            alert.tag = updateAlertTag;
            [alert show];
        }else {
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"新版本" message:_updateModel.mark delegate:self cancelButtonTitle:nil  otherButtonTitles:globe_cancel_str,@"去更新", nil];
            alert.tag = updateAlertTag;
            [alert show];
        }

    }
}

/**
 *@brief 将应用程序角标数字消除为0
 */
- (void)seqlinBage {
    NSString *deviceToken = [Utilits ToGetdeviceToken];
    if (!deviceToken) {
        return;
    }
    
    if ([[UIApplication sharedApplication] applicationIconBadgeNumber] <= 0) {
        return;
    }
    
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObject:deviceToken forKey:@"clientid"];
    [self requestWithURL:bSeqlinBagePath params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
    
    [[UIApplication sharedApplication] setApplicationIconBadgeNumber:0];
}

/**
 *@brief 如果是登录了，显示“帮助”按钮；如果未登录，显示“登录”按钮
 */
- (void)_setupNavationItem {
    NSString *itemTitle;
    UserInstance *user = [UserInstance sharedInstance];
    SEL selector;
    self.title = @"长江电商";
    if ([user login]) {
        itemTitle = @"帮助";
        selector =  @selector(skipHelpVC);
        
        UIView *bgView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 40, 44)];
//        bgView.backgroundColor = [UIColor greenColor];
        [bgView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(showMessage)]];
        UIButton *messageBtn = [UIButton buttonWithType:UIButtonTypeCustom];
        [messageBtn setImage:[UIImage imageNamed:@"index_icon_envelope"] forState:UIControlStateNormal];
        [messageBtn addTarget:self action:@selector(showMessage) forControlEvents:UIControlEventTouchUpInside];
        messageBtn.frame = CGRectMake(0, 0, 20, 44);
        messageBtn.userInteractionEnabled = NO;
        [bgView addSubview:messageBtn];
        
        _bage = [[TDBadgeView alloc] initWithFrame:CGRectMake(13, 2, 20, 16)];
        _bage.badgeColor = [UIColor yellowColor];
        _bage.radius = 7;
        _bage.hidden = YES;
        [bgView addSubview:_bage];
        
        [self inquireUnreadMessage];
        
        UIBarButtonItem *leftItem = [[UIBarButtonItem alloc] initWithCustomView:bgView];
        self.navigationItem.leftBarButtonItem = leftItem;
    }else {
        itemTitle = @"登录";
        selector = @selector(login:);
        self.navigationItem.leftBarButtonItem = nil;
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
    
    [self requestWithURL:bProductTodayInfo params:params HTTPMethod:kHttpGetMethod shouldCache:NO completeBlock:^(ASIHTTPRequest *request, id responseData) {
        [_itemListView hideLoading];
        DLog(@"%@",request.responseString);
        kASIResultLog;
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
    NSString *pcode = _segment.selectedSegmentIndex ? glProduct_top_stone_code : glProduct_top_send_code;
    RiverSectionModel *cruentModel = nil;
    SynacInstance *synacObj = [SynacInstance sharedInstance];
    for (RiverSectionModel *model in [synacObj riverSectionsArray]) {
        if ([model.riverSectionName isEqualToString:_choseBtn.titleLabel.text]) {
            cruentModel = model;
        }
    }
    NSString *area = cruentModel.riverSectionVal;
    if (!area.length) {
        area = @"RS001";
    }
    [params addString:pcode forKey:@"pcode"];
    [params addString:area forKey:@"area"];
    
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

- (void)showMessage {
    MessageViewController *vc = [[MessageViewController alloc] init];
    [self.navigationController pushViewController:vc animated:YES];
}// 查看消息

- (void)choseProduct:(UISegmentedControl *)segment {
    self.itemListView.items = nil;
    [self.itemListView.collectionView reloadData];
    [self requestNet];
}

- (void)skipHelpVC {
    AssistViewController *vc = [[AssistViewController alloc] init];
    [self.navigationController pushViewController:vc animated:YES];
} // 跳转到帮助页面

- (void)skipToForcastVC:(UIButton *)btn {
    ForcastViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"ForcastViewControllerId"];
    vc.isSend = YES;
    [self.navigationController pushViewController:vc animated:YES];
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

- (void)skip:(UIButton *)btn {
    NSInteger itemTag = btn.tag;
    
    if (![[UserInstance sharedInstance] login] && itemTag != 200) {
        LoginViewController *loginVC = [mainStoryBoard instantiateViewControllerWithIdentifier:@"LoginViewControllerId"];
        [self.navigationController pushViewController:loginVC animated:YES];
        HUD(@"您好，请先登录");
        return;
    }
    
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
            ContractWaitSureViewController *vc1 = [[ContractWaitSureViewController alloc] init];
            ContractPorccesingViewController *vc2 = [[ContractPorccesingViewController alloc] init];
            ContractEndedViewController *vc3 = [[ContractEndedViewController alloc] init];
            
            MyContractViewController *vc = [[MyContractViewController alloc] initSlidingViewControllerWithTitle:@"待确认合同" viewController:vc1];
            [vc addControllerWithTitle:@"进行中合同" viewController:vc2];
            [vc addControllerWithTitle:@"已结束合同" viewController:vc3];
            vc.selectedLabelColor = [UIColor orangeColor];
            vc.unselectedLabelColor = RGB(100, 100, 100, 1);
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
    [self performSelectorOnMainThread:@selector(_setupNavationItem) withObject:nil waitUntilDone:NO];
    UserInstance *userInstance = [UserInstance sharedInstance];
    if ([userInstance.user.isAuthRemind boolValue] == YES) {
//        [self performSelector:@selector(alertToAuth) withObject:nil afterDelay:3];
        [self alertToAuth];
    }
}

- (void)userLogoutNotification:(NSNotification *)notification {
    [self performSelectorOnMainThread:@selector(_setupNavationItem) withObject:nil waitUntilDone:NO];
}

/**
 *@brief 记录应用进入后台的时刻
 */
- (void)saveTimeForBackground {
    NSString *preTime = [[EGOCache globalCache] stringForKey:kUpdateTokenKey];
    if (preTime.length == 0) {
        [[EGOCache globalCache] setString:[Utilits stringFromFomate:[NSDate date] formate:kTimeDetail_Format] forKey:kUpdateTokenKey];
    }
}

/**
 *@brief 更新用户token，保持token有效
 */
- (void)updateToken {
    // 用户未登录不更新token
    UserInstance *userInstance = [UserInstance sharedInstance];
    if (!userInstance.login) {
        return;
    }
    
    // 应用在后台时间未超过一天，不更新token
    NSString *prveTime = [[EGOCache globalCache] stringForKey:kUpdateTokenKey];
    NSDate *prveDate = [Utilits dateFromFomate:prveTime formate:kTimeDetail_Format];
    NSTimeInterval seconds = [[NSDate date] timeIntervalSinceDate:prveDate];
    if (seconds <= kOneDaySeconds) {
        return;
    }
    // 移除记录
    [[EGOCache globalCache] removeCacheForKey:kUpdateTokenKey];
    
    // 进行token更新
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:userInstance.user.userToken,@"oldUserToken",@"",@"clientid",IntToNSNumber(1),@"clienttype", nil];
    [self requestWithURL:bUpdateTokenPath params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        // 更新用户token
        NSArray *dicArray = [responseData objectForKey:ServiceDataKey];
        NSDictionary *dic = [NSDictionary dictionary];
        if (dicArray.count > 0)
            dic = dicArray.firstObject;
        UserModel *model = [[UserModel alloc] initWithDataDic:dic];
        UserInstance *userInstance = [UserInstance sharedInstance];
        userInstance.user.userToken = model.userToken;
        
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

#pragma mark - ImagePlayerView Delegate
- (NSInteger)numberOfItems
{
    return 2;
}
//更换图片
- (void)imagePlayerView:(ImagePlayerView *)imagePlayerView loadImageForImageView:(UIImageView *)imageView index:(NSInteger)index
{
    
    dispatch_async(dispatch_get_main_queue(), ^{
        NSString *imagName = iPhone4 ? [NSString stringWithFormat:@"index_banner%ld4",index+1] : [NSString stringWithFormat:@"index_banner_groupBuy%ld.jpg",index+1];
        imageView.image = [UIImage imageNamed:imagName];
    });
    
}
- (void)imagePlayerView:(ImagePlayerView *)imagePlayerView didTapAtIndex:(NSInteger)index
{
    //点击团购图片
    if (index == 0) {
        GroupBuyViewController *groupBuyVC = [[GroupBuyViewController alloc] init];
        
        [self.navigationController pushViewController:groupBuyVC animated:YES];
    }
    if (index == 1) {
        
        MediaViewController *mediaVC = [[MediaViewController alloc] init];
        
        [self.navigationController pushViewController:mediaVC animated:YES];
        
    }
 

}

#pragma mark - IBActionSheet Delegate
//- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
//    [self indicateArrow];
//
//    if (buttonIndex != actionSheet.cancelButtonIndex) {
//        [_choseBtn setTitle:[actionSheet buttonTitleAtIndex:buttonIndex] forState:UIControlStateNormal];
//        [self requestNet];
//    }
//}

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

#pragma mark - 数据同步
/**
 *@brief 每次启动需要同步一次数据
 */
- (void)syncData {
    NSArray *types = @[@{@"type":@"1",@"timeStamp": @"2014-5-6 11:22:33"},@{@"type":@"2",@"timeStamp": @"2014-5-6 11:22:33"},@{@"type":@"3",@"timeStamp": @"2014-5-6 11:22:33"},@{@"type":@"5",@"timeStamp": @"2014-5-6 11:22:33"},@{@"type":@"6",@"timeStamp": @"2014-5-6 11:22:33"}];
    NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithObjectsAndKeys:[types JSONString],@"typeInfo", nil];
    [self requestWithURL:bSyncGetInfo
                  params:dic HTTPMethod:kHttpPostMethod
             shouldCache:YES
           completeBlock:^(ASIHTTPRequest *request, id responseData) {
               
           NSFileManager *fileManager = [NSFileManager defaultManager];
           NSString *file = [[NSString documentsPath] stringByAppendingPathComponent:kSynacFileName];

           dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
               [fileManager removeItemAtPath:file error:nil];
               [fileManager createFileAtPath:file contents:nil attributes:nil];
               [request.responseString writeToFile:file atomically:YES encoding:NSUTF8StringEncoding error:nil];
               [SynacObject synacData];
           });
               
    } failedBlock:^ (ASIHTTPRequest *req){
        
    }];
}

/**
 *@brief 用户收到推送认证消息或者保证金变更消息后，更新用户信息。
 */
- (void)updateUserInfo {
    __block typeof(self) this = self;
    [self requestWithURL:bCompanyAuthInfoPath params:nil HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [this undateUserInfo:responseData];
    } failedBlock:^(ASIHTTPRequest *req){
        
    }];
}

- (void)undateUserInfo:(NSDictionary *)resonDic {
    UserInstance *usIn = [UserInstance sharedInstance];
    NSArray *datas = resonDic[ServiceDataKey];
    if (datas.count > 0) {
        NSDictionary *dic = datas.firstObject;
        [usIn updateUserInfo:dic];
    }

}

#pragma mark - UIAlertView Delegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (alertView.tag == updateAlertTag) { // 去更新
        BOOL isforce = [_updateModel.isforce boolValue];
        if ((buttonIndex == 0 && isforce) || buttonIndex == 1) {
            [[UIApplication sharedApplication] openURL:[NSURL URLWithString:_updateModel.downurl]];
            exit(0);
        }
    }else if (alertView.tag == authAlertTag) { // 去认证
        if (buttonIndex == 0) {
            AuthViewController *vc = [[AuthViewController alloc] init];
            [self.navigationController pushViewController:vc animated:YES];
        }
    }
    
}

@end
