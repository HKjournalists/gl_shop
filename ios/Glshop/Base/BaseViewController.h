//
//  BaseViewController.h
//  Noble
//
//  Created by ios on 14-9-1.
//  Copyright (c) 2014年 bis. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ASIFormDataRequest.h"

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
typedef void (^ASIRequestFiledBlock)(ASIHTTPRequest *request);

@interface BaseViewController : UIViewController 

/**
 *@brief 网络请求成功时候的回调
 */
@property (nonatomic, copy) ASIRequestSuccedBlock asiRequestSuccedBlock;
/**
 *@brief 网络请求失败时的回调
 */
@property (nonatomic, copy) ASIRequestFiledBlock  asiRequestFiledBlock;
/**
 *@brief 网络请求成功，但是数据错误时候的回调
 */
@property (nonatomic, copy) ASISuccedDataConflictBlock asiDataConflictBlock;

/**
 *@brief 添加ASIHTTPRequest对象
 */
@property (nonatomic, strong) NSMutableArray *requestArray; // 请求对象

/**
 *@brief 默认为no
 */
@property (nonatomic, assign) BOOL isRefrushTable;

/**
 *@brief 加载失败或数据为空显示此视图
 *@discussion 点击failView会触发requestNet方法，已重新请求网络
 */
@property (nonatomic, strong) UIView *failView; // 加载失败，显示此图
/**
 *@brief 默认为YES,如果为YES，在网络请求失败时将添加提示视图，提示加载数据失败; 否则不显示
 */
@property (nonatomic, assign) BOOL shouldShowFailView;

/**
 *@brief 是否是视图控制器requestNet发出的请求 YES 是 NO 不是 默认为NO
 *@discussion 如果是且shouldShowFailView也是YES就显示加载失败，否则不显示
 */
@property (nonatomic, assign) BOOL isNotActionRequest;

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

- (void)handleNetData:(id)responseData;
- (void)commandHandle:(ASIHTTPRequest *)req;
- (void)handleRequestFailed:(ASIHTTPRequest *)req;
- (void)showErrorHUDIndicate;
- (void)tipErrorCode:(NSInteger)errorCode;

- (void)backRootVC;

/**
 *@brief 请求成功，数据为空
 *@discussion 默认显示一张图片和一行文字，提示用户没有数据,可重写
 */
- (void)requestSuccessButNoData;

/**
 *@brief 隐藏HUD、将req清除
 *@discussion 不重写，只是super调用
 */
- (void)commandHandle:(ASIHTTPRequest *)req;
- (UIView *)failViewWithFrame:(CGRect)frame empty:(BOOL)isEmpty;
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
                     isNodata:(BOOL)noData;

#pragma mark - Net
/**
 *@brief 网络请求
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

#pragma mark - HUD
- (void)showHUD;
- (void)showHUDWithDim:(BOOL)isDim;
- (void)showHUD:(NSString *)title isDim:(BOOL)isDim Yoffset:(float)offset;
- (void)hideHUD;
- (void)showTip:(NSString *)tipString;

@end

