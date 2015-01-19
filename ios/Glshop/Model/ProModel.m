//
//  ProModel.m
//  Glshop
//
//  Created by River on 14-12-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "ProModel.h"

@implementation ProModel

- (NSDictionary *)attributeMapDictionary {
    NSDictionary *mapAtt = @{
                             @"proCode":@"code",
                             @"proid":@"id",
                             @"proPid":@"pid",
                             @"pname":@"name",
                             @"proTypes":@"types",
                             @"proMaxv":@"maxv",
                             @"proMinv":@"minv",
                             @"proContent":@"content",
                             @"orderno":@"orderno",
                             @"proStatus":@"status"
                             };
    return mapAtt;
}


- (id)mutableCopyWithZone:(NSZone *)zone {
    ProModel *model = [[[self class] allocWithZone:zone] init ];
    model.proCode = self.proCode;
    model.proid = self.proid;
    model.proPid = self.proPid;
    model.pname = self.pname;
    model.proTypes = self.proTypes;
    model.proMaxv = self.proMaxv;
    model.proMinv = self.proMinv;
    model.proContent = self.proContent;
    model.orderno = self.orderno;
    model.proStatus = self.proStatus;
    
    return model;
}

@end
