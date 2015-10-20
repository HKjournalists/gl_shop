//
//  HJActionSheet.m
//  HJActionSheet
//
//  Created by River on 14-12-20.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "HJActionSheet.h"
#import "ViewUtils.h"

#define AnimationDuration 0.25

@interface HJActionSheet ()

@property (nonatomic, strong) UIView *contanierView;
@property (nonatomic, strong) UIView *contentView;
@property (nonatomic, strong) UILabel *titleLabel;

@end

@implementation HJActionSheet

- (instancetype)initWithTitle:(NSString *)title contentView:(UIView *)contentView {
    if (self = [super init]) {
        self.backgroundColor = RGB(0, 0, 0, 0);
        UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapAction:)];
        [self addGestureRecognizer:tap];
        if (title) {
            [self addSubview:_titleLabel];
            _titleLabel.text = title;
        }
        self.contentView = contentView;
        [self addSubview:self.contentView];
    }
    return self;
}

#pragma mark - Getter
- (UILabel *)titleLabel {
    if (!_titleLabel) {
        _titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, self.frame.size.width, 30)];
        _titleLabel.backgroundColor = [UIColor clearColor];
    }
    return _titleLabel;
}

#pragma mark - Public Methods
- (void)showSheet {
    self.frame = [self topViewController].view.bounds;
    [[self topViewController].view addSubview:self];
    
    self.contentView.frame = CGRectMake(0, self.vbottom, self.vwidth, self.contentView.vheight);
    [UIView animateWithDuration:AnimationDuration animations:^{
        self.backgroundColor = RGB(0, 0, 0, 0.4);
        self.contentView.vtop -= self.contentView.vheight;
    } completion:^(BOOL finished) {
        
    }];
}

- (void)hideSheet {
    [UIView animateWithDuration:AnimationDuration animations:^{
        self.contentView.vtop += self.contentView.vheight;
        self.backgroundColor = RGB(0, 0, 0, 0);
    } completion:^(BOOL finished) {
        [self removeFromSuperview];
    }];
}

#pragma mark - Private

- (void)tapAction:(UITapGestureRecognizer *)tap {
    CGPoint point = [tap locationInView:self];
    if (!CGRectContainsPoint(self.contentView.frame, point)) {
        [self hideSheet];
    }
}

@end
