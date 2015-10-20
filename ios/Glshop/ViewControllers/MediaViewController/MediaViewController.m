//
//  MediaViewController.m
//  Glshop
//
//  Created by shaouwangyunlei on 15/8/3.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  点击首页第二张广告展示的网页视频页面

#import "MediaViewController.h"

@interface MediaViewController ()

@end

@implementation MediaViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.title = @"长江电商";
    
    UIWebView *videoView = [[UIWebView alloc] initWithFrame:self.view.bounds];
    
    [self.view addSubview:videoView];
    
    NSURL *url = [NSURL URLWithString:@"http://mp.weixin.qq.com/s?__biz=MzAxNzQxOTQ2OQ==&mid=218283804&idx=1&sn=79aa7b2176ae1f99f1e052173a3f86dd#rd"];
    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    [videoView loadRequest:request];

}




@end
