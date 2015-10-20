//
//  PublicGuideView.m
//  Glshop
//
//  Created by River on 14-12-16.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "PublicGuideView.h"

#define kDefaultHeight 60
#define Image_Size_Width 20
#define Image_Size_Height Image_Size_Width

@implementation PublicGuideView

- (instancetype)initWithFrame:(CGRect)frame stepIndex:(NSInteger)step
{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = [UIColor clearColor];
        
        UIImage *image = [UIImage imageNamed:@"wallet_beijing"];
        UIImageView *imgView = [[UIImageView alloc] initWithFrame:self.bounds];
        image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
        imgView.image = image;
        [self addSubview:imgView];
        
        NSArray *lightImageName = @[@"information_dot_lvse",@"information_dot_er",@"information_dot_san",];
        NSArray *grayImageName = @[@"information_dot_huise_yi",@"information_dot_huise_er",@"information_dot_huise_san",];
        NSArray *stepNames = @[@"货物信息",@"交易信息",@"预览发布"];
        for (int i = 0; i < 3; i++) {
            UIImage *lightImg = [UIImage imageNamed:lightImageName[i]];
            UIImage *grayImg = [UIImage imageNamed:grayImageName[i]];
            UIImageView *guideImageView = [[UIImageView alloc] initWithImage:grayImg];
            guideImageView.frame = CGRectMake((self.vwidth-3*Image_Size_Width)/4*(i+1)+(i*Image_Size_Width), 10, Image_Size_Width, Image_Size_Width);
            [self addSubview:guideImageView];
            
            UILabel *label = [UILabel labelWithTitle:stepNames[i]];
            label.size = CGSizeMake(100, 20);
            label.center = CGPointMake(guideImageView.center.x+20, guideImageView.vbottom+10);
            label.font = [UIFont boldSystemFontOfSize:14.f];
            [self addSubview:label];
            
            UIView *line = [[UIView alloc] initWithFrame:CGRectMake(((self.vwidth-3*Image_Size_Width)/4+Image_Size_Width)*i, 18, (self.vwidth-3*Image_Size_Width)/4, 5)];
            line.backgroundColor = RGB(199, 199, 199, 1);
            [self addSubview:line];
            
            UIView *lastLine = [[UIView alloc] initWithFrame:CGRectMake(guideImageView.vright, line.vtop, line.vwidth, line.vheight)];
            lastLine.backgroundColor = line.backgroundColor;
            if (i == 2) {
                [self addSubview:lastLine];
            }
            
            if (step == 1 && i == 0) {
                guideImageView.image = lightImg;
                line.backgroundColor = RGB(79, 189, 4, 1);
            }
            
            if (step == 2 && i <= 1) {
                guideImageView.image = lightImg;
                line.backgroundColor = RGB(79, 189, 4, 1);
            }
            
            if (step == 3) {
                guideImageView.image = lightImg;
                line.backgroundColor = RGB(79, 189, 4, 1);
                lastLine.backgroundColor = RGB(79, 189, 4, 1);
            }
        }
    }
    return self;
}

@end
