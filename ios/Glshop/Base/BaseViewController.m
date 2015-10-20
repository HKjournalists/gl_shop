//
//  BaseViewController.m
//  Noble
//
//  Created by ios on 14-9-1.
//  Copyright (c) 2014年 bis. All rights reserved.
//

#import "BaseViewController.h"
#import "MBProgressHUD.h"
#import "NetEngine.h" 
#import "ASIDownloadCache.h"

#define CacheDuration_Seconds 60
#define RequestTimeoutSeconds 30

@interface BaseViewController () <UINavigationControllerDelegate>

@property (nonatomic, strong) MBProgressHUD *hud;

@end

@implementation BaseViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self _setup];
    
    [self initDatas];
    
    [self loadSubViews];
    
//    // 监听网络
//    __block typeof(self) this = self;
//    [[[NetEngine sharedInstance] netEngine] setReachabilityChangedHandler:^(NetworkStatus status) {
//        if (status != NotReachable && this.shouldRerequesNet == YES) {
//            [this requestNet];
//        }
//    }];

}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    
    NSArray *viewControllers = self.navigationController.viewControllers;
    if (viewControllers.count > 1 && [viewControllers objectAtIndex:viewControllers.count-2] == self) {
        
    } else if ([viewControllers indexOfObject:self] == NSNotFound) {
        // pop出来时，取消网络请求
        [self cancleRequest];
    }
}

#pragma mark - Private
/**
 *@brief 通用配置
 */
- (void)_setup {
    self.automaticallyAdjustsScrollViewInsets=NO;
    self.edgesForExtendedLayout= UIRectEdgeNone;
    self.view.backgroundColor = ColorWithHex(@"#EDEDED");
    self.requestArray = [NSMutableArray array];
    
//    self.navigationItem.backBarButtonItem = [[UIBarButtonItem alloc]
//                                             initWithTitle:@"返回"
//                                             style:UIBarButtonItemStylePlain
//                                             target:self
//                                             action:@selector(backRootVC)];
    UIButton *btn = [UIButton buttonWithTip:@"返回" target:self selector:@selector(backRootVC)];
    btn.frame = CGRectMake(0, 0, 60, 44);
    btn.imageEdgeInsets = UIEdgeInsetsMake(0, -5, 0, 0);
    btn.titleEdgeInsets = UIEdgeInsetsMake(0, 0, 0, -5);
    btn.titleLabel.font = FontSystem(16.f);
    [btn setTitleColor:[UIColor grayColor] forState:UIControlStateHighlighted];
    [btn setImage:[UIImage imageNamed:@"back_arrow"] forState:UIControlStateNormal];
    
    if (self.navigationController.viewControllers.count > 1) {
        self.navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithCustomView:btn];
    }

}

#pragma mark - 加载异常显示
- (UIView *)failViewWithFrame:(CGRect)frame empty:(BOOL)isEmpty {
    return [self failViewWithFrame:frame expectionImgName:nil expectionTitle:nil expectionSubTitle:nil isNodata:isEmpty];
}

/**
 *@brief 加载数据异常，显示相应的提示图
 *@param frame 异常图frame,默认和视图控制器视图大小一样
 *@param imgName 异常显示的图片名
 *@param title 异常标题
 *@param subTitle 异常子标题
 *@param noData 是否请求到没有相应的数据 （如果第二个参数imgName为空，YES: 显示默认的空图片 NO:显示默认的异常图片）
 */
- (UIView *)failViewWithFrame:(CGRect)frame
             expectionImgName:(NSString *)imgName
               expectionTitle:(NSString *)title
            expectionSubTitle:(NSString *)subTitle
                     isNodata:(BOOL)noData {
    if (!_failView) {
        _failView =[[UIView alloc] initWithFrame:frame];
        _failView.backgroundColor = [UIColor clearColor];
        UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(requestNet)];
        [_failView addGestureRecognizer:tap];
        
        NSString *theImgName;
        if (imgName) {
            theImgName = imgName;
        }else {
            theImgName = noData ? EmptyDataImage : RequestNetErrorImage;
        }
        UIImageView *indicateImageView = [[UIImageView alloc] initWithFrame:CGRectMake(_failView.vwidth/2-40, _failView.vheight/2-60-35/2-20, 80, 80)];
        indicateImageView.userInteractionEnabled = YES;
        indicateImageView.image = [UIImage imageNamed:theImgName];
        [_failView addSubview:indicateImageView];
        
        NSString *targetTitle;
        if (title) {
            targetTitle = title;
        }else {
            targetTitle = noData ? ShowDataEmptyStr : ShowNetErrorStr;
        }
        UILabel *tipLabel = [UILabel labelWithTitle:targetTitle];
        tipLabel.frame = CGRectMake(30, indicateImageView.vbottom+10, _failView.vwidth-60, 25);
        tipLabel.textAlignment = NSTextAlignmentCenter;
        tipLabel.textColor = [UIColor grayColor];
        tipLabel.font = [UIFont systemFontOfSize:16.f];
        [_failView addSubview:tipLabel];
        
        if (subTitle) {
            float y = title ? tipLabel.vbottom+10 : indicateImageView.vbottom+10;
            tipLabel.hidden = title ? NO : YES;
            UILabel *subLabel = [UILabel labelWithTitle:subTitle];
            subLabel.frame = CGRectMake(tipLabel.vleft, y, tipLabel.vwidth, 60);
            subLabel.textAlignment = NSTextAlignmentCenter;
            subLabel.numberOfLines = 3;
            subLabel.textColor = [UIColor grayColor];
            subLabel.font = [UIFont systemFontOfSize:16.f];
            [_failView addSubview:subLabel];
        }
        
    }
    
    return _failView;
}

#pragma mark - Override
- (void)initDatas {
    
}

- (void)loadSubViews {
    
}

- (void)requestNet {
    float yoffset = self.view.vtop == kTopBarHeight ? 0 : kTopBarHeight;
    if (self.edgesForExtendedLayout ==  UIRectEdgeAll) {
        yoffset = 0;
    }

    if (!_isRefrushTable) {
        [self.view showWithTip:nil Yoffset:yoffset];
    }
    
    self.shouldShowFailView = YES;
    self.isNotActionRequest = YES;
}

- (void)cancleRequest {
    for (ASIHTTPRequest *request in self.requestArray) {
        [request cancel];
    }
    [self.requestArray removeAllObjects];
}

- (void)handleNetData:(id)responseData {
    
}

/**
 *@brief 显示错误提示，可重载
 */
- (void)tipErrorCode:(NSInteger)errorCode {
    if (errorCode == 300001 || errorCode == 300002) {
        [[PushInstance sharedInstance] handleUserLoginPush];
    }else {
        [self showTip:[Utilits handleErrorCode:errorCode] time:2.0];
    }
}

- (void)backRootVC {
    [self.navigationController popViewControllerAnimated:YES];
    
}

#pragma mark - Net
- (ASIHTTPRequest *)requestWithURL:(NSString *)urlstring
                            params:(NSMutableDictionary *)params
                        HTTPMethod:(NSString *)httpMethod
                     completeBlock:(ASIRequestSuccedBlock)successBlock
                       failedBlock:(ASIRequestFiledBlock)failedBlock {
    return [self requestWithURL:urlstring
                         params:params
                     HTTPMethod:httpMethod
                    shouldCache:NO
                  completeBlock:successBlock
                    failedBlock:failedBlock];
}

- (ASIHTTPRequest *)requestWithURL:(NSString *)urlstring
                            params:(NSMutableDictionary *)params
                        HTTPMethod:(NSString *)httpMethod
                       shouldCache:(BOOL)isCache
                     completeBlock:(ASIRequestSuccedBlock)successBlock
                       failedBlock:(ASIRequestFiledBlock)failedBlock {
    return [self requestWithURL:urlstring params:params HTTPMethod:httpMethod shouldCache:isCache needHeader:NO completeBlock:successBlock failedBlock:failedBlock];
    
}

- (ASIHTTPRequest *)requestWithURL:(NSString *)urlstring
                            params:(NSMutableDictionary *)params
                        HTTPMethod:(NSString *)httpMethod
                       shouldCache:(BOOL)isCache
                        needHeader:(BOOL)isNeedHeader
                     completeBlock:(ASIRequestSuccedBlock)successBlock
                       failedBlock:(ASIRequestFiledBlock)failedBlock {
    
    return [self requestWithURL:urlstring params:params HTTPMethod:httpMethod shouldCache:isCache needHeader:isNeedHeader completeBlock:successBlock dataConflictBlock:nil failedBlock:failedBlock];
}

- (ASIHTTPRequest *)requestWithURL:(NSString *)urlstring
                            params:(NSMutableDictionary *)params
                        HTTPMethod:(NSString *)httpMethod
                       shouldCache:(BOOL)isCache
                        needHeader:(BOOL)isNeedHeader
                     completeBlock:(ASIRequestSuccedBlock)successBlock
                 dataConflictBlock:(ASISuccedDataConflictBlock)dataConflictBlock
                       failedBlock:(ASIRequestFiledBlock)failedBlock {
    if (_failView.superview) {
        [_failView removeFromSuperview];
    }
    
    urlstring = [kBaseUrl stringByAppendingFormat:@"/%@",urlstring];
    urlstring = [@"http://" stringByAppendingString:urlstring];
    DLog(@"urlstring == %@",urlstring);
    params = [Utilits packSevrverRequestParams:params];
    
    // 处理GET方法
    NSComparisonResult comparRetGET = [httpMethod caseInsensitiveCompare:@"GET"];
    if (comparRetGET == NSOrderedSame) {
        NSArray *allkeys = [params allKeys];
        NSMutableString *pamramsString = [NSMutableString string];
        
        // 拼接参数
        for (int i = 0; i < params.count; i++) {
            NSString *key = [allkeys objectAtIndex:i];
            id value = [params objectForKey:key];
            [pamramsString appendFormat:@"%@=%@",key,value];
            
            if (i < params.count - 1) {
                [pamramsString appendFormat:@"&"];
            }
        }
        
        if (pamramsString.length > 0) {
            urlstring = [urlstring stringByAppendingFormat:@"?%@",pamramsString];
        }
    }
    
    // 创建request对象
    NSURL *url = [NSURL URLWithString:urlstring];
    __block ASIFormDataRequest *request = [ASIFormDataRequest requestWithURL:url];
    ASIDownloadCache * cache = [ASIDownloadCache sharedCache];
    [request setRequestMethod:httpMethod];
    
    if (isCache) {
        cache.defaultCachePolicy = ASIOnlyLoadIfNotCachedCachePolicy;
        [request setDownloadCache:cache];
        [request setCachePolicy:ASIAskServerIfModifiedWhenStaleCachePolicy];
        request.secondsToCache = CacheDuration_Seconds;
        [request setCacheStoragePolicy:ASICachePermanentlyCacheStoragePolicy];
    }
    [request setTimeOutSeconds:RequestTimeoutSeconds];
    
    UserInstance *userInstance = [UserInstance sharedInstance];
    NSString *token = userInstance.user.userToken;
    if (token) {
        [request addRequestHeader:@"USER_TOKEN" value:token];
    }
    [request addRequestHeader:@"VERSION" value:glVersionString];
    
    // 处理post请求方式
    NSComparisonResult comparRetPost = [httpMethod caseInsensitiveCompare:@"POST"];
    if (comparRetPost == NSOrderedSame) {
        NSArray *allkeys = [params allKeys];
        for (int i = 0; i < params.count; i++) {
            NSString *key = [allkeys objectAtIndex:i];
            id value = [params objectForKey:key];
            
            if ([value isKindOfClass:[NSData class]]) {
                [request addData:value forKey:key];
            }else {
                [request addPostValue:value forKey:key];
            }
        }
    }
    
    // 设置请求完成的block
    __block ASIFormDataRequest *req = request;
    
    __block typeof(self) weakSelf = self;
    [request setCompletionBlock:^{
        id result = [NSJSONSerialization JSONObjectWithData:req.responseData options:NSJSONReadingMutableContainers error:nil];
        [weakSelf handleSucclessRequest:result successBlock:successBlock asiRequest:req dataConflictBlock:dataConflictBlock];
        
    }];
    
    [request setFailedBlock:^{
        if (failedBlock != nil) {
            [weakSelf handleRequestFailed:req];
            failedBlock(req);
        }
    }];
    
    [request startAsynchronous];
    
    [self.requestArray addObject:req];
    
//    // 将请求对象加到数组，当用户退出当前当前界面时，取消请求，防止崩溃
//    if ([httpMethod isEqualToString:kHttpGetMethod]) {
//        NSString *urlStringTag = [request.url absoluteString];
//        NSMutableString *mutableStr = [NSMutableString stringWithString:urlStringTag];
//        NSRange range = [mutableStr rangeOfString:@"sign"];
//        NSRange tagretRange = {range.location,38};
//        if (range.location != NSNotFound) {
//            
//            [mutableStr deleteCharactersInRange:tagretRange];
//        }
//        NSRange range1 = [mutableStr rangeOfString:@"timestamp"];
//        NSRange tagretRange1 = {range1.location,24};
//        if (range1.location != NSNotFound) {
//            
//            [mutableStr deleteCharactersInRange:tagretRange1];
//        }
//        urlStringTag = [NSString stringWithString:mutableStr];
//        
//        NSMutableArray *temp = [NSMutableArray array];
//        for (ASIHTTPRequest *reqs in weakSelf.requestArray) {
//            NSString *urlStr = [reqs.url absoluteString];
//            NSMutableString *mutableStr = [NSMutableString stringWithString:urlStr];
//            NSRange range = [mutableStr rangeOfString:@"sign"];
//            NSRange tagretRange = {range.location,38};
//            if (range.location != NSNotFound) {
//                
//                [mutableStr deleteCharactersInRange:tagretRange];
//            }
//            NSRange range1 = [mutableStr rangeOfString:@"timestamp"];
//            NSRange tagretRange1 = {range1.location,24};
//            if (range1.location != NSNotFound) {
//                
//                [mutableStr deleteCharactersInRange:tagretRange1];
//            }
//            urlStr  = [NSString stringWithString:mutableStr];
//            
//            [temp addObject:urlStr];
//        }
//        
//        if (![temp containsObject:urlStringTag]) {
//            [weakSelf.requestArray addObject:request];
//        }
//    }else {
//        NSMutableArray *temp = [NSMutableArray array];
//        if (![temp containsObject:request.url.absoluteString]) {
//            [weakSelf.requestArray addObject:request];
//        }
//    }
    
    return request;
}

/**
 *@brief 请求网络成功
 */
- (void)handleSucclessRequest:(id)result
                 successBlock:(ASIRequestSuccedBlock)successBlock
                   asiRequest:(ASIHTTPRequest *)req {
    [self handleSucclessRequest:result successBlock:successBlock asiRequest:req dataConflictBlock:nil];
}

- (void)handleSucclessRequest:(id)result
                 successBlock:(ASIRequestSuccedBlock)successBlock
                   asiRequest:(ASIHTTPRequest *)req
            dataConflictBlock:(ASISuccedDataConflictBlock)conflictBlock {

    [self commandHandle:req];
    
    NSDictionary *resultDic = (NSDictionary *)result;
    
    if (![resultDic isKindOfClass:[NSDictionary class]]) {
        DLog(@"获取的数据异常");
        return;
    }
    BOOL flag = [resultDic[@"RESULT"] boolValue];
    if (flag) {
        successBlock(req,result);
    }else {
        NSInteger errorCode = [resultDic[@"ERRORCODE"] integerValue];
        [self tipErrorCode:errorCode];
        if (conflictBlock) {
            conflictBlock(req,result);
        }
    }
}

/**
 *@brief 请求网络失败，可以重载
 */
- (void)handleRequestFailed:(ASIHTTPRequest *)req {
    [self commandHandle:req];
    if (self.shouldShowFailView && self.isNotActionRequest && !self.failView.superview) {
        [self.view addSubview:[self failViewWithFrame:self.view.bounds empty:NO]];
    }else {
        [self showErrorHUDIndicate];
    }
}

/**
 *@brief 显示网络不给力提示，可重载
 */
- (void)showErrorHUDIndicate {
    [self showTip:kNetError];
}

- (void)showErrorCodeHUD {
    
}

/**
 *@brief 隐藏HUD、将req清除
 */
- (void)commandHandle:(ASIHTTPRequest *)req {
    if (self.failView.superview) {
        [self.failView removeFromSuperview];
    }
    if (!req.error) { // 如果请求成功
        self.isNotActionRequest = NO;
    }
    if (self.view.isShowLoading) {
        [self.view hideLoading];
    }
    
    if (self.hud.superview && !self.isNotActionRequest) {
        [self hideHUD];
    }
    
    if ([self.requestArray containsObject:req]) {
        [self.requestArray removeObject:req];
    }
}

/**
 *@brief 请求成功，数据为空
 *@discussion 默认显示一张图片和一行文字，提示用户没有数据
 */
- (void)requestSuccessButNoData {
    if (self.shouldShowFailView && !self.failView.superview) {
        [self.view addSubview:[self failViewWithFrame:self.view.bounds empty:YES]];
    }
}

#pragma mark- MPHUD
- (void)showHUD {
    [self showHUD:nil isDim:NO Yoffset:0.f];
}

- (void)showHUDWithDim:(BOOL)isDim {
    [self showHUD:@"正在加载..." isDim:isDim Yoffset:0.f];
}

- (void)showHUD:(NSString *)title isDim:(BOOL)isDim Yoffset:(float)offset
{
    self.hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    self.hud.labelText = title;
    self.hud.yOffset = offset;
    self.hud.dimBackground = isDim;
}

- (void)hideHUD
{
    [self.hud hide:YES];
}

- (void)showTip:(NSString *)tipString {
    [self showTip:tipString time:1.3];
}

- (void)showTip:(NSString *)tipString time:(float)timeIntevl {
    self.hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    self.hud.detailsLabelText = tipString;
    self.hud.yOffset = -kTopBarHeight;
    self.hud.mode = MBProgressHUDModeText;
    
    [self performSelector:@selector(hideHUD) withObject:nil afterDelay:timeIntevl];
}

@end
