//
//  GroupBuyBaseView.m
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/28.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "GroupBuyBaseView.h"

@implementation GroupBuyBaseView

-(instancetype)initWithFrame:(CGRect)frame image:(NSString *)imageName title:(NSString *)title
{
    self = [super initWithFrame:frame];
    
    if (self) {
        UIImageView *imageView = [[UIImageView alloc] initWithFrame:self.bounds];
        [self addSubview:imageView];
        self.bgView = imageView;
        self.bgView.image = [UIImage imageNamed:imageName];
        self.bgView.userInteractionEnabled = YES;
        UILabel *titleLabel = [[UILabel alloc] initWithFrame:CGRectMake(10, 10, frame.size.width-10, 34.5)];
        [self addSubview:titleLabel];
        self.titleLabel = titleLabel;
        self.titleLabel.text = [NSString stringWithFormat:@"| %@",title];
        self.titleLabel.font = [UIFont boldSystemFontOfSize:14];
        self.titleLabel.textColor = GROUPBUYTEXTYELLOWCOLOR;
        [self loadSubViews];
    }
    
    return self;
}
- (void)loadSubViews
{

}
- (void)loadHeadTitleView
{
    _lineView = [[UIImageView alloc] init];
    _lineView.frame = CGRectMake(0, CGRectGetMaxY(self.titleLabel.frame), CGRectGetWidth(self.frame), 2);
    _lineView.image = [UIImage imageNamed:@"Line"];
    [self addSubview:_lineView];
}
@end
