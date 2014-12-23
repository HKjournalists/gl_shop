//
//  BaseViewController.h
//  Noble
//
//  Created by ios on 14-9-1.
//  Copyright (c) 2014年 bis. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ASIFormDataRequest.h"

typedef void (^RequestSuccedBlock) (MKNetworkOperation *operation);
typedef void (^RequestFiledBlock)(MKNetworkOperation *operation, NSError *error);

/**
 *@brief 请求成功，返回正确的数据
 */
typedef void (^ASIRequestSuccedBlock) (ASIHTTPRequest *request,id responseData);

/**
 *@brief 请求成功，返回的数据有错
 */
typedef void (^ASISuccedDataConflictBlock)(ASIHTTPRequest *request,id responseData);

/**
 *@brief 请求服务器失败
 */
typedef void (^ASIRequestFiledBlock)();

@interface BaseViewController : UIViewController 

@property (nonatomic, copy) RequestSuccedBlock requestSuccedBlock;
@property (nonatomic, copy) RequestFiledBlock  requestFiledBlock;
@property (nonatomic, copy) ASIRequestSuccedBlock asiRequestSuccedBlock;
@property (nonatomic, copy) ASIRequestFiledBlock  asiRequestFiledBlock;
@property (nonatomic, copy) ASISuccedDataConflictBlock asiDataConflictBlock;

/**
 *@brief 添加ASIHTTPRequest对象
 */
@property (nonatomic, strong) NSMutableArray *requestArray; // 请求对象

@property (nonatomic, strong) UIView *failView; // 加载失败，显示此图
@property (nonatomic, assign) BOOL shouldShowFailView;

/**
 *@brief 当网络连接变成wifi或wwan的时候，是否重新请求网络
 *@discussion 默认为NO；如果是YES，当网络连接变成wifi或wwan的时候，控制器将自动调用requestNet，以便重新获得数据
 */
@property (nonatomic, assign) BOOL shouldRerequesNet;

#pragma mark - Override
- (void)initDatas;
- (void)loadSubViews;
- (void)requestNet;
- (void)cancleRequest;
- (void)handleRequestFailed;
- (UIView *)failViewWithFrame:(CGRect)frame empty:(BOOL)isEmpty;

#pragma mark - Net
/**
 *@brief 网络请求
 *@param path 请求路径
 *@param params 请求参数
 *@param method post/get请求
 *@param successBlock 请求成功的回调
 *@param filedBlock  请求失败的回调
 *@return MKNetworkOperation
 */
- (MKNetworkOperation *)requestWithPath:(NSString *)path
                 params:(NSMutableDictionary *)params
             httpMehtod:(NSString *)method
              HUDString:(NSString *)tipString
                success:(RequestSuccedBlock)successBlock
                  error:(RequestFiledBlock)filedBlock;

- (MKNetworkOperation *)requestWithPath:(NSString *)path
                 params:(NSMutableDictionary *)params
             httpMehtod:(NSString *)method
                success:(RequestSuccedBlock)successBlock
                  error:(RequestFiledBlock)filedBlock;

- (MKNetworkOperation *)requestWithPath:(NSString *)path
                 params:(NSMutableDictionary *)params
             httpMehtod:(NSString *)method
                showHUD:(BOOL)show
                success:(RequestSuccedBlock)successBlock
                  error:(RequestFiledBlock)filedBlock;

/**
 *@brief ASI网络请求
 */
- (ASIHTTPRequest *)requestWithURL:(NSString *)urlstring
                            params:(NSMutableDictionary *)params
                        HTTPMethod:(NSString *)httpMethod
                     completeBlock:(ASIRequestSuccedBlock)successBlock
                       failedBlock:(ASIRequestFiledBlock)failedBlock;

- (ASIHTTPRequest *)requestWithURL:(NSString *)urlstring
                            params:(NSMutableDictionary *)params
                        HTTPMethod:(NSString *)httpMethod
                       shouldCache:(BOOL)isCache
                     completeBlock:(ASIRequestSuccedBlock)successBlock
                       failedBlock:(ASIRequestFiledBlock)failedBlock;

- (ASIHTTPRequest *)requestWithURL:(NSString *)urlstring
                            params:(NSMutableDictionary *)params
                        HTTPMethod:(NSString *)httpMethod
                       shouldCache:(BOOL)isCache
                        needHeader:(BOOL)isNeedHeader
                     completeBlock:(ASIRequestSuccedBlock)successBlock
                       failedBlock:(ASIRequestFiledBlock)failedBlock;

- (ASIHTTPRequest *)requestWithURL:(NSString *)urlstring
                            params:(NSMutableDictionary *)params
                        HTTPMethod:(NSString *)httpMethod
                       shouldCache:(BOOL)isCache
                        needHeader:(BOOL)isNeedHeader
                     completeBlock:(ASIRequestSuccedBlock)successBlock
                 dataConflictBlock:(ASISuccedDataConflictBlock)dataConflictBlock
                       failedBlock:(ASIRequestFiledBlock)failedBlock;

/**
 *@brief 图片上传
 */
- (ASIFormDataRequest *)uploadImgWithURL:(NSString *)urlstring
                              HTTPMethod:(NSString *)httpMethod
                           completeBlock:(ASIRequestSuccedBlock)successBlock
                             failedBlock:(ASIRequestFiledBlock)failedBlock;

#pragma mark - HUD
- (void)showHUDWithDim:(BOOL)isDim;
- (void)showHUD:(NSString *)title isDim:(BOOL)isDim Yoffset:(float)offset;
- (void)hideHUD;
- (void)showTip:(NSString *)tipString;

@end

