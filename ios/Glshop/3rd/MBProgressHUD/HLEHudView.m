//
//  HLEHudView.m
//  HJActionSheet
//
//  Created by River on 15-1-21.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "HLEHudView.h"
#import "M13ProgressViewRing.h"
#import "UIImageView+WebCache.h"

@interface HLEHudView ()

@property (nonatomic, strong) UIView *bgView;
@property (nonatomic, strong) M13ProgressViewRing *pview;
@property (nonatomic, strong) UIView *targetView;
@property (nonatomic, strong) UIView *bgMaskView;
@property (nonatomic, strong) UIImageView *downloadImgView;

@end

@implementation HLEHudView

- (instancetype)initWithTagView:(UIView *)pressView {
    self = [super init];
    if (self) {
        _targetView = pressView;
        UIWindow *window = [[UIApplication sharedApplication] keyWindow];
        CGRect rect = [pressView.superview convertRect:pressView.frame toView:window];
        _bgMaskView = [[UIView alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
        _bgMaskView.backgroundColor = RGB(0, 0, 0, 0);
        _downloadImgView = [[UIImageView alloc] initWithFrame:rect];
        [_bgMaskView addSubview:_downloadImgView];
        
        [_bgMaskView addGestureRecognizer:[[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(dismiss:)]];
        
    }
    return self;
}

- (void)showProgressing {
    UIWindow *window = [[UIApplication sharedApplication] keyWindow];
    UIImage *placehodleImg = [UIImage imageNamed:@"attestation_icon_photo"];
    if (!_downloadImgView.image) {
        _downloadImgView.image = placehodleImg;
    }
    if ([_targetView isKindOfClass:[UIImageView class]]) {
        UIImageView *imgView = (UIImageView *)_targetView;
        if (imgView.image) {
            _downloadImgView.image = imgView.image;
            placehodleImg = imgView.image;
        }
    }
    CGRect rect = [_targetView.superview convertRect:_targetView.frame toView:window];
    _downloadImgView.frame = rect;
    
    [window addSubview:self.bgMaskView];
    [self.bgMaskView addSubview:self.bgView];
    [self.pview performAction:M13ProgressViewActionNone animated:YES];
    [self.bgView addSubview:self.pview];
    
    [UIView animateWithDuration:0.3 animations:^{
        self.bgMaskView.backgroundColor = RGB(0, 0, 0, 1);
        self.bgView.transform = CGAffineTransformIdentity;
        _downloadImgView.frame = CGRectMake(0, SCREEN_HEIGHT/2-100, SCREEN_WIDTH, 200);
    } completion:^(BOOL finished) {
        
    }];
    
    [self.downloadImgView sd_setImageWithPreviousCachedImageWithURL:[NSURL URLWithString:_imgUrl] andPlaceholderImage:placehodleImg options:SDWebImageCacheMemoryOnly|SDWebImageRetryFailed progress:^(NSInteger receivedSize, NSInteger expectedSize) {
        DLog(@"%ld,,,,,,,,,,,,,%ld",(long)receivedSize,(long)expectedSize);
        [_pview setProgress:(float)receivedSize/expectedSize animated:YES];
    } completed:^(UIImage *image, NSError *error, SDImageCacheType cacheType, NSURL *imageURL) {
        if (image) {
            float width = image.size.width > SCREEN_WIDTH ? SCREEN_WIDTH : image.size.width;
            float height = width*image.size.height/image.size.width;
            _downloadImgView.frame = CGRectMake(SCREEN_WIDTH/2-width/2, SCREEN_HEIGHT/2-height/2, width, height);
            [_downloadImgView setImage:image];
            [_bgView removeFromSuperview];
        }else {
            [self showDownFaile];
        }
    }];
    
}

- (void)dismiss:(UITapGestureRecognizer *)tap {
    UIWindow *window = [[UIApplication sharedApplication] keyWindow];
    CGRect rect = [_targetView.superview convertRect:_targetView.frame toView:window];
    [self.downloadImgView sd_cancelCurrentImageLoad];
    [UIView animateWithDuration:0.3 animations:^{
        _bgMaskView.backgroundColor = RGB(0, 0, 0, 0);
        _downloadImgView.frame = rect;
    } completion:^(BOOL finished) {
        [_bgMaskView removeFromSuperview];
    }];
}

- (void)showDownFaile {
    
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(0, _pview.vbottom, _bgView.vwidth, 20)];
    label.text = @"加载失败";
    label.textColor = [UIColor whiteColor];
    label.font = [UIFont systemFontOfSize:16.f];
    label.textAlignment = NSTextAlignmentCenter;
    [_bgView addSubview:label];
    
    [_pview performAction:M13ProgressViewActionFailure animated:NO];
    [_bgView performSelector:@selector(removeFromSuperview) withObject:nil afterDelay:1.3];
    _bgView = nil;

}

#pragma mark - Getter
- (UIView *)bgView {
    UIWindow *window = [[UIApplication sharedApplication] keyWindow];
    if (!_bgView) {
        _bgView = [[UIView alloc] initWithFrame:CGRectMake(window.vwidth/2-45 , window.vheight/2-45, 90, 90)];
        _bgView.backgroundColor = [UIColor colorWithWhite:0 alpha:0.8];
        _bgView.layer.cornerRadius = 10;
        _bgView.transform = CGAffineTransformMakeScale(0.2, 0.2);
    }
    return _bgView;
}

- (M13ProgressViewRing *)pview {
    if (!_pview) {
        _pview = [[M13ProgressViewRing alloc] initWithFrame:CGRectMake(25, 25, 40, 40)];
        _pview.primaryColor = [UIColor whiteColor];
        _pview.secondaryColor = [UIColor blackColor];
        _pview.showPercentage = NO;
        _pview.backgroundRingWidth = 3;
        _pview.progressRingWidth = 3;
    }
    return _pview;
}

@end
