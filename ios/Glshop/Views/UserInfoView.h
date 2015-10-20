//
//  UserInfoView.h
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/28.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "GroupBuyBaseView.h"
@class UserInfoView;

@protocol UserInfoViewDelegate <NSObject>

- (void)userInfoView:(UserInfoView *)userInfoView didChangeRemarkView:(UITextView *)textView;

- (void)userInfoView:(UserInfoView *)userInfoView didSelectedJoinButton:(UIButton *)joinButton requestParamDict:(NSMutableDictionary *)param;
@end


@interface UserInfoView : GroupBuyBaseView

@property (nonatomic,assign)id<UserInfoViewDelegate>delegate;

@property(nonatomic,strong)UILabel *textViewPlacehoder;

@end
