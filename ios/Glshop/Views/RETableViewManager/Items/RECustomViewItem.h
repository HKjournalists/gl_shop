//
//  RECustomViewItem.h
//  Glshop
//
//  Created by River on 14-11-12.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "RETableViewItem.h"

@interface RECustomViewItem : RETableViewItem

@property (nonatomic, strong) UIView *customView;

- (id)initItemWithCustomView:(UIView *)customView;

@end
