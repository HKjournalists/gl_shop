//
//  ContractDetailViewController.h
//  Glshop
//
//  Created by River on 15-1-27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  合同模板

#import "BaseViewController.h"
#import "ContractEnum.h"

@interface ContractDetailViewController : BaseViewController<UIWebViewDelegate>

@property (nonatomic, strong) UIWebView *webView;

/**
 *@brief 合同id
 */
@property (nonatomic, copy) NSString *contractId;

@end
