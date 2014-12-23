//
//  FilterControl.h
//  Glshop
//
//  Created by River on 14-11-28.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef NS_ENUM(NSInteger, FilterType) {
    TypeWithTwoTable,
    TypeWithThreeTable,
};

@interface FilterControl : UIView

@property (nonatomic, assign) UIViewController *weakViewConterller;
@property (nonatomic, readonly,strong) NSMutableDictionary *resultDictionary;

- (void)showFilter;

@end
