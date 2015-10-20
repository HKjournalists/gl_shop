//
//  FilerTitleView.m
//  FilerView
//
//  Created by River on 15-3-14.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "FilerTitleView.h"

@implementation FilerTitleView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        UIView *view = [[[NSBundle mainBundle] loadNibNamed:@"FilerTitleView" owner:self options:nil]lastObject];
        [self addSubview:view];
        
        UIImage *imgae = PNGIMAGE(@"dressing-by-screening_beijing@2x");
        imgae = [imgae resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10)];
        _imgView.image = imgae;
        
        _checkBox.boxImage = [UIImage imageNamed:@"check_unselected"];
        _checkBox.selectImage = [UIImage imageNamed:@"dressing-by-screening_gouxuan"];
        
        __block typeof(self) this = self;
        _checkBox.tapBlock = ^(BOOL selected) {
            if ([this.delegate respondsToSelector:@selector(filerSelectAll:)]) {
                [this.delegate filerSelectAll:selected];
            }
        };
    }
    return self;
}

- (void)awakeFromNib {
    [super awakeFromNib];
}

@end
