//
//  NetEngine.h
//  Glshop
//
//  Created by River on 14-11-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NetEngine : NSObject

+ (id)sharedInstance;

@property (nonatomic, strong) MKNetworkEngine *netEngine;

@end
