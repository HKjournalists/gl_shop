//
//  UILabel+WDText.m
//  Glshop
//
//  Created by River on 15-1-19.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "UILabel+WDText.h"

@implementation UILabel (WDText)

- (void)setSafeText:(NSString *)text {
    if (![text isKindOfClass:[NSString class]]) {
        [self setText:@""];
    }else {
        [self setText:text];
    }
}

@end
