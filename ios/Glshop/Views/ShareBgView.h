//
//  ShareBgView.h
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/28.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol ShareToWeChatDelegate <NSObject>

@optional
//分享纯文本到微信
-(void) sendTextContent;
//分享连接到微信
- (void) sendLinkContentWith:(int)Wxsence;

@end


@interface ShareBgView : UIView

@property (nonatomic,assign)id<ShareToWeChatDelegate>delegate;

- (void)ShareViewAnimateWithDuration:(NSTimeInterval)time moveToPoint:(CGPoint)point;
@end
