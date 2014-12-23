//
//  HLCheckbox.h
//  Glshop
//
//  Created by River on 14-11-10.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

typedef void(^BoxTaped)(BOOL selected);

@interface HLCheckbox : UIView

@property (nonatomic, assign,getter=isSelected) BOOL selected;
@property (nonatomic, copy) BoxTaped tapBlock;

- (instancetype)initWithBoxImage:(UIImage *)image selectImage:(UIImage *)selectImage;

@end
