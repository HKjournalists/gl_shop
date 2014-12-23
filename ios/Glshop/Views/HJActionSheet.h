//
//  HJActionSheet.h
//  HJActionSheet
//
//  Created by River on 14-12-20.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HJActionSheet : UIView

- (instancetype)initWithTitle:(NSString *)title contentView:(UIView *)contentView;

- (void)showSheet;

- (void)hideSheet;

@end
