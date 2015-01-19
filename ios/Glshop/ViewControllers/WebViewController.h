//
//  WebViewController.h
//  Glshop
//
//  Created by River on 15-1-12.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "BaseViewController.h"

@interface WebViewController : BaseViewController <UIWebViewDelegate>
{
    NSString *_fileName;
}

- (instancetype)initWithFileName:(NSString *)fileName;

@property (nonatomic, strong) UIWebView *webView;

@end
