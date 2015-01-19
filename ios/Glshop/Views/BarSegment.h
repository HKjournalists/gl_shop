//
//  BarSegment.h
//  HJActionSheet
//
//  Created by River on 14-12-29.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol BarDidSelected <NSObject>

- (void)barDidSelectedAtIndex:(NSInteger)index;

@end

@interface BarSegment : UIView

@property (nonatomic, assign) id <BarDidSelected> delegate;

@property (nonatomic, assign, readonly) NSInteger selctedIndex;

@end
