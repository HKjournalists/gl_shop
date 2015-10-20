//
//  HLEHudView.h
//  HJActionSheet
//
//  Created by River on 15-1-21.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HLEHudView : NSObject

@property (nonatomic, copy) NSString *imgUrl;

- (instancetype)initWithTagView:(UIView *)pressView;

- (void)showProgressing;

@end
