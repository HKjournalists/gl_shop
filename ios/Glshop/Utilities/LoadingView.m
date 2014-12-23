//
//  LoadingView.m
//  jfsdl
//
//  Created by River on 14-11-21.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "LoadingView.h"

@interface LoadingView ()

@property (nonatomic, strong) UIActivityIndicatorView *indicatorView;
@property (nonatomic, strong) UILabel *titleLabel;

@end

@implementation LoadingView

- (id)initWithFrame:(CGRect)frame {
    if (self = [super initWithFrame:frame]) {
        self.backgroundColor = [UIColor clearColor];
        
        _indicatorView = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
        _indicatorView.frame = CGRectMake(10, 0, 20, 20);
        [self addSubview:_indicatorView];
        
        _titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(CGRectGetMaxX(_indicatorView.frame), 0, 90, 20)];
        _titleLabel.text = @"正在加载...";
        _titleLabel.font = [UIFont boldSystemFontOfSize:16.f];
        _titleLabel.textColor = [UIColor grayColor];
        _titleLabel.backgroundColor = [UIColor clearColor];
        [self addSubview:_titleLabel];
    }
    return self;
}

- (void)willMoveToSuperview:(UIView *)newSuperview {
    [_indicatorView startAnimating];
}

- (void)layoutSubviews {
    [super layoutSubviews];
    
    _titleLabel.text = _tipTitle ? _tipTitle : @"正在加载...";
}


@end
