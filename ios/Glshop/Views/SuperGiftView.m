//
//  SuperGiftView.m
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/28.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "SuperGiftView.h"
#import "UILabel+adjustText.h"

@implementation SuperGiftView

- (void)loadSubViews
{
    [self loadHeadTitleView];
    
    UIButton *button1 = [UIButton buttonWithType:UIButtonTypeCustom];
    button1.frame = CGRectMake(CGRectGetMinX(self.titleLabel.frame), CGRectGetMaxY(self.lineView.frame)+10, CGRectGetWidth(self.frame)*0.5, 20);
    [self addSubview:button1];
    [button1 setImage:[UIImage imageNamed:@"shuzi1_"] forState:UIControlStateNormal];
    [button1 setTitle:join_gift forState:UIControlStateNormal];
    button1.titleLabel.font = [UIFont systemFontOfSize:14];
    [button1 setContentHorizontalAlignment:UIControlContentHorizontalAlignmentLeft];
    button1.userInteractionEnabled = NO;
    
    UILabel *desc_1 = [UILabel createLabelWith:CGSizeMake(CGRectGetWidth(self.frame), 2000) text:join_desc textColor:GROUPBUYWHITECOLOR textFont:GROUPBUYTEXTFONT point:CGPointMake(CGRectGetMinX(button1.frame), CGRectGetMaxY(button1.frame)+10)];
    [self addSubview:desc_1];
    CGFloat pad_first = 10;
    CGFloat pad = 14;
    CGFloat gift_y = CGRectGetMaxY(desc_1.frame)+15;
    UIImageView *imageView_bg;
    for (int i = 0; i < 4; i++) {
        imageView_bg = [[UIImageView alloc] initWithFrame:CGRectMake(pad_first+i*(pad+65), gift_y, 65, 65)];
        imageView_bg.image = [UIImage imageNamed:@"hd_"];
        [self addSubview:imageView_bg];

        UIImageView *giftImage = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 45, 57)];
        giftImage.center = imageView_bg.center;
        giftImage.image = [UIImage imageNamed:@"lw_"];
        [self addSubview:giftImage];
        
    }
    
    UIButton *button2 = [UIButton buttonWithType:UIButtonTypeCustom];
    button2.frame = CGRectMake(CGRectGetMinX(self.titleLabel.frame), CGRectGetMaxY(imageView_bg.frame)+10, CGRectGetWidth(self.frame)*0.5, 20);
    [self addSubview:button2];
    button2.userInteractionEnabled = NO;
    [button2 setImage:[UIImage imageNamed:@"shuzi2_"] forState:UIControlStateNormal];
    [button2 setTitle:weChat_gift forState:UIControlStateNormal];
    button2.titleLabel.font = [UIFont systemFontOfSize:14];
    [button2 setContentHorizontalAlignment:UIControlContentHorizontalAlignmentLeft];
    
    
    UILabel *descriptionLabel = [UILabel createLabelWith:CGSizeMake(CGRectGetWidth(self.frame)-10, CGRectGetHeight(self.frame)) text:weChat_desc textColor:GROUPBUYWHITECOLOR textFont:GROUPBUYTEXTFONT point:CGPointMake(CGRectGetMinX(self.titleLabel.frame), CGRectGetMaxY(button2.frame)+10)];
    descriptionLabel.numberOfLines = 0;
    
    NSMutableAttributedString *descriptionStr = [[NSMutableAttributedString alloc] initWithString:weChat_desc];
    [descriptionStr addAttribute:NSBackgroundColorAttributeName value:GROUPBUYTEXTYELLOWCOLOR range:NSMakeRange(22, 8)];
    [descriptionStr addAttribute:NSForegroundColorAttributeName value:GROUPBUYREDTEXTCOLOR range:NSMakeRange(23, 6)];
    
    [descriptionLabel setAttributedText:descriptionStr];
    [descriptionLabel sizeToFit];
    
    [self addSubview:descriptionLabel];
}
@end
