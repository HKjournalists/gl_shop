//
//  NetService.h
//  Glshop
//
//  Created by River on 14-12-24.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ASIFormDataRequest.h"

/**
 *@brief 请求成功，返回正确的数据
 */
typedef void (^ASIRequestSuccedBlock) (ASIHTTPRequest *request,id responseData);

/**
 *@brief 请求服务器失败
 */
typedef void (^ASIRequestFiledBlock)();

@interface NetService : NSObject

@property (nonatomic, copy) ASIRequestSuccedBlock asiRequestSuccedBlock;
@property (nonatomic, copy) ASIRequestFiledBlock  asiRequestFiledBlock;

+ (ASIFormDataRequest *)uploadImgWithURL:(NSString *)urlstring
                              HTTPMethod:(NSString *)httpMethod
                           completeBlock:(ASIRequestSuccedBlock)successBlock
                             failedBlock:(ASIRequestFiledBlock)failedBlock;

@end
