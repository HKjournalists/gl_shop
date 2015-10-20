//
//  LoadImageView.m
//  Glshop
//
//  Created by River on 15-1-22.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "LoadImageView.h"
#import "HLEHudView.h"

@interface LoadImageView ()

@property (nonatomic, strong) HLEHudView *load;
@property (nonatomic, strong) NSString *imgUrl;

@end

@implementation LoadImageView

- (instancetype)initWithFrame:(CGRect)frame bigImageUrl:(NSString *)url
{
    self = [super initWithFrame:frame];
    if (self) {
        _load = [[HLEHudView alloc] initWithTagView:self];
        _imgUrl = [url copy];
        self.userInteractionEnabled = YES;
        [self addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(loadBigImage:)]];
    }
    return self;
}

- (void)loadBigImage:(UITapGestureRecognizer *)tap {
    if (_imgUrl == nil) {
        return;
    }
    _load.imgUrl = _imgUrl;
    [_load showProgressing];
}

@end
