//
//  RECustomViewItem.m
//  Glshop
//
//  Created by River on 14-11-12.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "RECustomViewItem.h"

@implementation RECustomViewItem

- (id)initItemWithCustomView:(UIView *)customView {
    self = [super init];
    if (self) {
        self.customView = customView;
    }
    return self;
}

@end
