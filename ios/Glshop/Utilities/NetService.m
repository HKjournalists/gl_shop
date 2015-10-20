//
//  NetService.m
//  Glshop
//
//  Created by River on 14-12-24.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "NetService.h"
#import "ASIFormDataRequest.h"

@implementation NetService

+ (ASIFormDataRequest *)uploadImgWithURL:(NSString *)urlstring
                              HTTPMethod:(NSString *)httpMethod
                           completeBlock:(ASIRequestSuccedBlock)successBlock
                             failedBlock:(ASIRequestFiledBlock)failedBlock {
    
    urlstring = [kBaseUrl stringByAppendingFormat:@"/%@",urlstring];
    urlstring = [@"http://" stringByAppendingString:urlstring];
    
    // 创建request对象
    NSURL *url = [NSURL URLWithString:urlstring];
    __block ASIFormDataRequest *request = [ASIFormDataRequest requestWithURL:url];
    [request setRequestMethod:httpMethod];
    
    [request setTimeOutSeconds:30];
    
    UserInstance *userInstance = [UserInstance sharedInstance];
    NSString *token = userInstance.user.userToken;
    if (token.length) {
        [request addRequestHeader:@"USER_TOKEN" value:token];
    }
    
    // 设置请求完成的block
    __block ASIFormDataRequest *req = request;
    //    __weak typeof(self) weakSelf = self;
    [request setCompletionBlock:^{
        id result = [NSJSONSerialization JSONObjectWithData:req.responseData options:NSJSONReadingMutableContainers error:nil];
        if (successBlock) {
            successBlock(req,result);
        }
        
    }];
    
    [request setFailedBlock:^{
        if (failedBlock != nil) {
            failedBlock();
        }
    }];
    
    return request;
}

@end
