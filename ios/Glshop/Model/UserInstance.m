//
//  UserInstance.m
//  Glshop
//
//  Created by River on 14-11-11.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "UserInstance.h"

@implementation UserInstance

+ (id)sharedInstance {
    static id sharedInstance;
    static dispatch_once_t once;
    dispatch_once(&once, ^{
        sharedInstance = [[[self class] alloc] init];
    });;
    return sharedInstance;
}

- (void)destory {
    _user.cid = nil;
    _user.username = nil;
    _user.phone = nil;
    _user.clientid = nil;
    _user = nil;
}

- (BOOL)login {
    if (_user.username.length > 0) {
        return YES;
    }
    return NO;
}

- (UserType)userType {
    NSInteger t = [_user.ctype[DataValueKey] integerValue];
    if (t == 0) {
        return user_company;
    }else if (t == 1) {
        return user_ship;
    }else if (t == 2) {
        return user_personal;
    }
    return NSNotFound;
}


@end
