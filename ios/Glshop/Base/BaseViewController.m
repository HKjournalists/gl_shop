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
#import "OpenUDID.h"

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
    
    // 监听网络
    [[[NetEngine sharedInstance] netEngine] setReachabilityChangedHandler:^(NetworkStatus status) {
        if (status != NotReachable && _shouldRerequesNet == YES) {
            [self requestNet];
        }
    }];

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
    
    self.navigationItem.backBarButtonItem = [[UIBarButtonItem alloc]
                                             initWithTitle:@"返回"
                                             style:UIBarButtonItemStylePlain
                                             target:self
                                             action:nil];
}

#pragma mark - Getter
- (UIView *)failViewWithFrame:(CGRect)frame empty:(BOOL)isEmpty {
    if (!_failView) {
        _failView =[[UIView alloc] initWithFrame:frame];
        _failView.backgroundColor = [UIColor clearColor];
        UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(requestNet)];
        [_failView addGestureRecognizer:tap];
        
        NSString *imageName = isEmpty ? EmptyDataImage : RequestNetErrorImage;
        UIImageView *indicateImageView = [[UIImageView alloc] initWithFrame:CGRectMake(_failView.vwidth/2-40, _failView.vheight/2-40-35/2-20, 80, 80)];
        indicateImageView.userInteractionEnabled = YES;
        indicateImageView.image = [UIImage imageNamed:imageName];
        [_failView addSubview:indicateImageView];
        
        NSString *title = isEmpty ? ShowDataEmptyStr : ShowNetErrorStr;
        UILabel *tipLabel = [UILabel labelWithTitle:title];
        tipLabel.frame = CGRectMake(0, indicateImageView.vbottom+10, _failView.vwidth, 25);
        tipLabel.textAlignment = NSTextAlignmentCenter;
        tipLabel.font = [UIFont boldSystemFontOfSize:18.f];
        [_failView addSubview:tipLabel];

    }
    return _failView;
}

#pragma mark - Override
- (void)initDatas {
    
}

- (void)loadSubViews {
    
}

- (void)requestNet {
    [self.view showWithTip:nil Yoffset:kTopBarHeight];
}

- (void)cancleRequest {
    for (ASIHTTPRequest *request in self.requestArray) {
        [request cancel];
    }
}

- (void)handleNetData:(id)responseData {
    
}

- (void)hideViewsWhenNoData {

}

- (void)showViewsWhenDataComing {

}

- (void)backRootVC:(UIButton *)btn {
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
    
    // 将请求对象加到数组，当用户退出当前当前界面时，取消请求，防止崩溃
    if ([httpMethod isEqualToString:kHttpGetMethod]) {
        NSString *urlStringTag = [request.url absoluteString];
        NSMutableString *mutableStr = [NSMutableString stringWithString:urlStringTag];
        NSRange range = [mutableStr rangeOfString:@"sign"];
        NSRange tagretRange = {range.location,38};
        if (range.location != NSNotFound) {
            
            [mutableStr deleteCharactersInRange:tagretRange];
        }
        NSRange range1 = [mutableStr rangeOfString:@"timestamp"];
        NSRange tagretRange1 = {range1.location,24};
        if (range1.location != NSNotFound) {
            
            [mutableStr deleteCharactersInRange:tagretRange1];
        }
        urlStringTag = [NSString stringWithString:mutableStr];
        
        NSMutableArray *temp = [NSMutableArray array];
        for (ASIHTTPRequest *reqs in weakSelf.requestArray) {
            NSString *urlStr = [reqs.url absoluteString];
            NSMutableString *mutableStr = [NSMutableString stringWithString:urlStr];
            NSRange range = [mutableStr rangeOfString:@"sign"];
            NSRange tagretRange = {range.location,38};
            if (range.location != NSNotFound) {
                
                [mutableStr deleteCharactersInRange:tagretRange];
            }
            NSRange range1 = [mutableStr rangeOfString:@"timestamp"];
            NSRange tagretRange1 = {range1.location,24};
            if (range1.location != NSNotFound) {
                
                [mutableStr deleteCharactersInRange:tagretRange1];
            }
            urlStr  = [NSString stringWithString:mutableStr];
            
            [temp addObject:urlStr];
        }
        
        if (![temp containsObject:urlStringTag]) {
            [weakSelf.requestArray addObject:request];
        }
    }else {
        NSMutableArray *temp = [NSMutableArray array];
        if (![temp containsObject:request.url.absoluteString]) {
            [weakSelf.requestArray addObject:request];
        }
    }
    
    return request;
}

- (ASIFormDataRequest *)uploadImgWithURL:(NSString *)urlstring
                              HTTPMethod:(NSString *)httpMethod
                           completeBlock:(ASIRequestSuccedBlock)successBlock
                             failedBlock:(ASIRequestFiledBlock)failedBlock {
    
    urlstring = [kBaseUrl stringByAppendingFormat:@"/%@",urlstring];
    urlstring = [@"http://" stringByAppendingString:urlstring];
    
    // 创建request对象
    NSURL *url = [NSURL URLWithString:urlstring];
    __block ASIFormDataRequest *request = [ASIFormDataRequest requestWithURL:url];
    [request setRequestMethod:httpMethod];
    
    [request setTimeOutSeconds:60];
    
    UserInstance *userInstance = [UserInstance sharedInstance];
    NSString *token = userInstance.user.userToken;
    if (token.length) {
        [request addRequestHeader:@"USER_TOKEN" value:token];
    }
    
    // 设置请求完成的block
    __block ASIFormDataRequest *req = request;
    __weak typeof(self) weakSelf = self;
    [request setCompletionBlock:^{
        id result = [NSJSONSerialization JSONObjectWithData:req.responseData options:NSJSONReadingMutableContainers error:nil];
        if (successBlock) {
            successBlock(req,result);
        }
        
    }];
    
    [request setFailedBlock:^{
        if (failedBlock != nil) {
            [weakSelf handleRequestFailed:req];
            failedBlock(req);
        }
    }];
    
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
    if (self.failView.superview) {
        [self.failView removeFromSuperview];
    }
    [self commandHandle:req];
    
    NSDictionary *resultDic = (NSDictionary *)result;
    
    if (![resultDic isKindOfClass:[NSDictionary class]]) {
        DLog(@"获取的数据异常");
        return;
    }
    BOOL flag = [resultDic[@"RESULT"] boolValue];
    if (flag) {
        successBlock(req,result);
        [self showViewsWhenDataComing];
    }else {
        NSString *message = resultDic[@"MESSAGE"];
        [self showTip:message];
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
    if (self.shouldShowFailView && !self.failView.superview) {
        [self.view addSubview:[self failViewWithFrame:self.view.bounds empty:NO]];
    }
}

/**
 *@brief 隐藏HUD、将req清除
 */
- (void)commandHandle:(ASIHTTPRequest *)req {
    [self.view hideLoading];
    [self hideHUD];
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


#pragma mark - MK
- (MKNetworkOperation *)requestWithPath:(NSString *)path
                 params:(NSMutableDictionary *)params
             httpMehtod:(NSString *)method
                success:(RequestSuccedBlock)successBlock
                  error:(RequestFiledBlock)filedBlock {
    
    return     [self requestWithPath:path
                              params:params
                          httpMehtod:method
                             showHUD:YES
                             success:successBlock
                               error:filedBlock];
    
}

- (MKNetworkOperation *)requestWithPath:(NSString *)path
                 params:(NSMutableDictionary *)params
             httpMehtod:(NSString *)method
                HUDString:(NSString *)tipString
                success:(RequestSuccedBlock)successBlock
                  error:(RequestFiledBlock)filedBlock {
    
    MKNetworkEngine *engine = [[NetEngine sharedInstance] netEngine];
    
    MKNetworkOperation *op = [engine operationWithPath:path params:[Utilits packSevrverRequestParams:params] httpMethod:method];
    [self showHUD:tipString isDim:NO Yoffset:0];
    [op addCompletionHandler:^(MKNetworkOperation *completedOperation) {
        [self successRequest:completedOperation success:successBlock];
        
    } errorHandler:^(MKNetworkOperation *completedOperation, NSError *error) {
        
        [self failRequest:error failedBlock:filedBlock operation:completedOperation];
    }];
    
    [engine enqueueOperation:op];
    
    return op;
}

- (MKNetworkOperation *)requestWithPath:(NSString *)path
                 params:(NSMutableDictionary *)params
             httpMehtod:(NSString *)method
                showHUD:(BOOL)show
                success:(RequestSuccedBlock)successBlock
                  error:(RequestFiledBlock)filedBlock {
    
    MKNetworkEngine *engine = [[NetEngine sharedInstance] netEngine];
    
    MKNetworkOperation *op = [engine operationWithPath:path params:[Utilits packSevrverRequestParams:params] httpMethod:method];
    if (show) [self showHUDWithDim:NO];
    [op addCompletionHandler:^(MKNetworkOperation *completedOperation) {
        [self successRequest:completedOperation success:successBlock];
        
    } errorHandler:^(MKNetworkOperation *completedOperation, NSError *error) {
        [self hideHUD];
        filedBlock(completedOperation,error);
    }];
    
    [engine enqueueOperation:op];
    
    return op;
}

- (void)successRequest:(MKNetworkOperation *)completedOperation success:(RequestSuccedBlock)successBlock {
    [self hideHUD];
    
    NSDictionary *resultDic = completedOperation.responseJSON;
    if (![resultDic isKindOfClass:[NSDictionary class]]) return;
    BOOL flag = [resultDic[@"RESULT"] boolValue];
    if (flag) {
        successBlock(completedOperation);
    }else {
        NSString *message = resultDic[@"MESSAGE"];
        HUD(message);
    }
}

- (void)failRequest:(NSError *)error failedBlock:(RequestFiledBlock)failBlock operation:(MKNetworkOperation *)operation{
    [self hideHUD];
    failBlock(operation,error);
}

#pragma mark- MPHUD
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
    self.hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    self.hud.detailsLabelText = tipString;
    self.hud.yOffset = -kTopBarHeight;
    self.hud.mode = MBProgressHUDModeText;
    
    [self performSelector:@selector(hideHUD) withObject:nil afterDelay:2];
}

@end
