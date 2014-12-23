//
//  IGLDropDownItem.m
//  IGLDropDownMenuDemo
//
//  Created by Galvin Li on 8/30/14.
//  Copyright (c) 2014 Galvin Li. All rights reserved.
//

#import "IGLDropDownItem.h"

@interface IGLDropDownItem ()

@property (nonatomic, strong) UIImageView *iconImageView;
@property (nonatomic, strong) UIView *bgView;
@property (nonatomic, strong) UILabel *textLabel;
@property (nonatomic, assign) BOOL rotation;
@property (nonatomic, strong) UIImageView *indicateView;

@end

@implementation IGLDropDownItem

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code
        _paddingLeft = 5;
        [self initView];
    }
    return self;
}

- (void)setFrame:(CGRect)frame
{
    [super setFrame:frame];
    
    [self.bgView setFrame:self.bounds];
    
    [self updateLayout];
    
}

- (id)initWithCustomStyle {
    self = [super init];
    if (self) {
        self.bgView = [[UIView alloc] init];
        self.bgView.userInteractionEnabled = NO;
        self.bgView.backgroundColor = [UIColor clearColor];
        [self.bgView setFrame:self.bounds];
        [self addSubview:self.bgView];
        
        self.iconImageView = [[UIImageView alloc] init];
        self.iconImageView.contentMode = UIViewContentModeCenter;
        [self addSubview:self.iconImageView];
        
        _indicateView = [[UIImageView alloc] initWithFrame:CGRectMake(120, 44.5/2-5, 15, 10)];
        _indicateView.image = [UIImage imageNamed:@"Buy_sell_icon"];
        [self addSubview:_indicateView];
        _rotation = YES;
        
        self.textLabel = [[UILabel alloc] init];
        self.textLabel.numberOfLines = 1;
        self.textLabel.textColor = [UIColor whiteColor];
        self.textLabel.textAlignment = NSTextAlignmentCenter;
        self.textLabel.font = [UIFont boldSystemFontOfSize:20.f];
        [self addSubview:self.textLabel];
        
        [self updateLayout];
    }
    return self;
}

- (void)rotaionIndicate:(BOOL)expend {
    if (!expend) {
        [UIView animateWithDuration:0.4 animations:^{
            [_indicateView.layer setTransform:CATransform3DMakeRotation(-M_PI/1.000001, 0, 0, 1)];
        }];
    }else {
        [UIView animateWithDuration:0.4 animations:^{
            [_indicateView.layer setTransform:CATransform3DIdentity];
        }];
    }
}

- (void)initView
{
    self.bgView = [[UIView alloc] init];
    self.bgView.userInteractionEnabled = NO;
    self.bgView.backgroundColor = [UIColor whiteColor];
    self.bgView.layer.borderColor = [UIColor lightGrayColor].CGColor;
    self.bgView.layer.borderWidth = 0.5;
    [self.bgView setFrame:self.bounds];
    [self addSubview:self.bgView];
    
    self.iconImageView = [[UIImageView alloc] init];
    self.iconImageView.contentMode = UIViewContentModeCenter;
    [self addSubview:self.iconImageView];
    
    self.textLabel = [[UILabel alloc] init];
    self.textLabel.numberOfLines = 1;
    self.textLabel.textColor = [UIColor grayColor];
    self.textLabel.textAlignment = NSTextAlignmentCenter;
    [self addSubview:self.textLabel];
    
    [self updateLayout];
    
}

- (void)setIconImage:(UIImage *)iconImage
{
    _iconImage = iconImage;
    [self.iconImageView setImage:self.iconImage];
    
    [self updateLayout];
}

- (void)updateLayout
{
    
    CGFloat selfWidth = CGRectGetWidth(self.bounds);
    CGFloat selfHeight = CGRectGetHeight(self.bounds);
    
    [self.iconImageView setFrame:CGRectMake(self.paddingLeft, 0, selfHeight, selfHeight)];
    if (self.iconImage) {
        [self.textLabel setFrame:CGRectMake(CGRectGetMaxX(self.iconImageView.frame), 0, selfWidth - CGRectGetMaxX(self.iconImageView.frame), selfHeight)];
    } else {
        [self.textLabel setFrame:CGRectMake(self.paddingLeft, 0, selfWidth, selfHeight)];
    }
}

- (void)setPaddingLeft:(CGFloat)paddingLeft
{
    _paddingLeft = paddingLeft;
    
    [self updateLayout];
}

- (void)setText:(NSString *)text
{
    _text = text;
    self.textLabel.text = self.text;
}

@end
