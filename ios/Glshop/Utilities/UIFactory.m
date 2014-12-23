//
//  UIFactory.m
//  Glshop
//
//  Created by River on 14-11-13.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "UIFactory.h"

@interface UIFactory ()

@end

@implementation UIFactory

+ (UIView *)createPromptViewframe:(CGRect)frame tipTitle:(NSString *)title {
    UIImageView *imageview = [[UIImageView alloc] initWithFrame:frame];
    imageview.userInteractionEnabled = YES;
    UIImage *image = [UIImage imageNamed:@"attestation_prompt_background"];
    image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeTile];
    imageview.image = image;
    
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(imageview.vwidth/2-50, 10, 100, 25)];
    label.text = @"温馨提示";
    if (title) {
        label.text = title;
    }
    label.textAlignment = NSTextAlignmentCenter;
    label.font = [UIFont boldSystemFontOfSize:16.f];
    label.textColor = ColorWithHex(@"#36a830");
    [imageview addSubview:label];
    
    return imageview;
}

@end
