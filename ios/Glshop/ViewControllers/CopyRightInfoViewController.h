//
//  CopyRightInfoViewController.h
//  Glshop
//
//  Created by River on 15-1-4.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  企业简介/企业照片上传

#import "BaseViewController.h"

typedef NS_ENUM(NSInteger, Opention_type) {
    Add_Contact,
    Fill_Brief,
    Upload_Image,
};

@class AuthModel;
@interface CopyRightInfoViewController : BaseViewController

@property (nonatomic, assign) Opention_type opentionType;

@property (nonatomic, weak) AuthModel *authModel;

@end
