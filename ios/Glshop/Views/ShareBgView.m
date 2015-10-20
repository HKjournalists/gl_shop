//
//  ShareBgView.m
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/28.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "ShareBgView.h"
#import "UILabel+adjustText.h"
#import "WXApiObject.h"

#define SHARELABELTEXTCOLOR [UIColor colorWithRed:100/255.0 green:100/255.0 blue:100/255.0 alpha:1.0]
#define SHARELABELFONT [UIFont systemFontOfSize:10]

@interface ShareBgView()
@property (nonatomic,strong)UIButton *weChatFriendButton;

@property (nonatomic,strong)UIButton *friendGroupButton;
@end


@implementation ShareBgView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        [self createSubViews];
    }
    return self;
}
- (void)createSubViews
{

    
    UILabel *weChat = [UILabel createLabelWith:CGSizeMake(CGRectGetWidth(self.frame), 15) text:@"分享至微信好友" textColor:SHARELABELTEXTCOLOR textFont:SHARELABELFONT point:CGPointMake(50, 61)];
    [self addSubview:weChat];
    
    _weChatFriendButton = [UIButton buttonWithType:UIButtonTypeCustom];
    
    _weChatFriendButton.frame = CGRectMake(CGRectGetMinX(weChat.frame), 10,CGRectGetWidth(weChat.frame), 46);
    [_weChatFriendButton setImage:[UIImage imageNamed:@"weixin_"] forState:UIControlStateNormal];
    [_weChatFriendButton addTarget:self action:@selector(TapWeChatShareButton:) forControlEvents:UIControlEventTouchUpInside];
    _weChatFriendButton.tag = WXSceneSession;
    [self addSubview:_weChatFriendButton];
    
    
    UILabel *friendGroup = [UILabel createLabelWith:CGSizeMake(CGRectGetWidth(self.frame), 15) text:@"分享至朋友圈" textColor:SHARELABELTEXTCOLOR textFont:SHARELABELFONT point:CGPointMake(CGRectGetMaxX(_weChatFriendButton.frame)+77, CGRectGetMinY(weChat.frame))];
    
    [self addSubview:friendGroup];

    _friendGroupButton = [UIButton buttonWithType:UIButtonTypeCustom];
    _friendGroupButton.frame = CGRectMake(CGRectGetMinX(friendGroup.frame), CGRectGetMinY(_weChatFriendButton.frame), CGRectGetWidth(friendGroup.frame), 46);
    [_friendGroupButton setImage:[UIImage imageNamed:@"pyq_"] forState:UIControlStateNormal];
    [_friendGroupButton addTarget:self action:@selector(TapWeChatShareButton:) forControlEvents:UIControlEventTouchUpInside];
    _friendGroupButton.tag = WXSceneTimeline;
    [self addSubview:_friendGroupButton];

}

- (void)TapWeChatShareButton:(UIButton *)sender
{
    if (sender.tag == WXSceneTimeline) {
        [self.delegate sendLinkContentWith:(int)sender.tag];

    }
    if (sender.tag == WXSceneSession) {
        [self.delegate sendLinkContentWith:(int)sender.tag];
    }
    
}
- (void)ShareViewAnimateWithDuration:(NSTimeInterval)time moveToPoint:(CGPoint)point
{
    [UIView animateWithDuration:time animations:^{
        self.frame = CGRectMake(point.x,point.y,CGRectGetWidth(self.frame), CGRectGetHeight(self.frame));
        
    }];
    
}
@end
