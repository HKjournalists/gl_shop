//
//  PhotoListView.h
//  Glshop
//
//  Created by River on 14-12-4.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface PhotoListView : UIView

@property (nonatomic, strong) NSMutableArray *images;

- (void)addPhoto:(UIImage *)image;

@end
