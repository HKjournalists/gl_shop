//
//  HLCheckbox.m
//  Glshop
//
//  Created by River on 14-11-10.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "HLCheckbox.h"
#import "UIImage+ImageWithColor.h"

@interface HLCheckbox ()

@property (nonatomic, strong) UIImage *boxImage;
@property (nonatomic, strong) UIImage *selectImage;
@property (nonatomic, strong) UIImageView *boxImageView;
@property (nonatomic, strong) UIButton *indicateButton;


@end

@implementation HLCheckbox

- (instancetype)initWithBoxImage:(UIImage *)image selectImage:(UIImage *)selectImage {
    if (self = [super init]) {
        _selected = NO;
        self.boxImage = image;
        self.selectImage = selectImage;
        
        _boxImageView = [[UIImageView alloc] init];
        _boxImageView.image = image;
        [self addSubview:_boxImageView];
        
        _indicateButton = [UIButton buttonWithType:UIButtonTypeCustom];
        [_indicateButton addTarget:self action:@selector(checkState) forControlEvents:UIControlEventTouchUpInside];
        [self addSubview:_indicateButton];
    }
    return self;
}

- (void)checkState {
    if (_tapBlock) {
        _tapBlock(_selected);
    }

    _selected = !_selected;
    if (!_selected) {
        [_indicateButton setBackgroundImage:_boxImage forState:UIControlStateNormal];
    }else {
        [_indicateButton setBackgroundImage:_selectImage forState:UIControlStateNormal];
    }
}

- (void)setSelected:(BOOL)selected {
    _selected = selected;
    if (!_selected) {
        [_indicateButton setBackgroundImage:_boxImage forState:UIControlStateNormal];
    }else {
        [_indicateButton setBackgroundImage:_selectImage forState:UIControlStateNormal];
    }
}

- (void)layoutSubviews {
    [super layoutSubviews];
    
    _boxImageView.frame = self.bounds;
    _indicateButton.frame = self.bounds;
}

@end
