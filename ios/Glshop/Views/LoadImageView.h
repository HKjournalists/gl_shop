//
//  LoadImageView.h
//  Glshop
//
//  Created by River on 15-1-22.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "UIImageView+WebCache.h"

@interface LoadImageView : UIImageView

- (instancetype)initWithFrame:(CGRect)frame bigImageUrl:(NSString *)url;

@end
