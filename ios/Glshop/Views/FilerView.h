//
//  FilerView.h
//  FilerView
//
//  Created by River on 15-3-14.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "FilerTimeView.h"

@class FilerProductView;
@interface FilerView : UIView
{
@private
    UISegmentedControl *_segmentControl;
    FilerProductView   *_filerProductView;
}

@property (nonatomic, strong, readonly) FilerTimeView *timeView;

- (void)resetFilerData;

/**
 *@brief 收集最终的产品筛选信息
 */
- (NSString *)gatherFilerProductData;

/**
 *@brief 收集最终的省份筛选信息
 */
- (NSString *)gatherFilerProvinceData;

/**
 *@brief 收集最终的区域筛选信息
 */
- (NSString *)gatherFilerRegionData;

@end
