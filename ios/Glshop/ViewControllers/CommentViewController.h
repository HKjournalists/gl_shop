//
//  CommentViewController.h
//  Glshop
//
//  Created by River on 15-1-21.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  评论

typedef NS_ENUM(NSInteger, CommentCheckStyle) {
    copyrightCheck,
    
    contractCheck,
};
#import "BaseViewController.h"

@interface CommentViewController : BaseViewController

@property (nonatomic, assign) CommentCheckStyle checkStyle;

@property (nonatomic, copy) NSString *contractId;
@property (nonatomic, copy) NSString *cid;

@end
