//
//  NetEngine.m
//  Glshop
//
//  Created by River on 14-11-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "NetEngine.h"

#define kTestBaseUrl @"192.168.31.243:8080/gl_shop_http"

@implementation NetEngine

+ (id)sharedInstance {
    static id sharedInstance;
    static dispatch_once_t once;
    dispatch_once(&once, ^{
        sharedInstance = [[[self class] alloc] init];
    });
    return sharedInstance;
}

- (MKNetworkEngine *)netEngine {
    if (!_netEngine) {
        _netEngine = [[MKNetworkEngine alloc] initWithHostName:kBaseUrl];
        [_netEngine useCache];
        if (_netEngine.isReachable) {
            DebugLog(@"reachable");
        }else {
            DebugLog(@"notReachable");
        }
    }
    return _netEngine;
}

@end
