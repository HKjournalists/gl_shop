//
//  PushAlertView.h
//  Glshop
//
//  Created by River on 15-4-7.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface PushAlertView : UIAlertView

/**
 *@brief 存储的推送消息
 */
@property (nonatomic, strong) NSDictionary *pushMessage;

@end
