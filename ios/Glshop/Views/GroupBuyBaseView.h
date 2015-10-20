//
//  GroupBuyBaseView.h
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/28.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GroupBuyBaseView : UIView

@property (nonatomic,strong)UIImageView *bgView;
@property (nonatomic,strong)UILabel *titleLabel;
@property(nonatomic,strong)UIImageView *lineView;


-(instancetype)initWithFrame:(CGRect)frame image:(NSString *)imageName title:(NSString *)title;
- (void)loadSubViews;
- (void)loadHeadTitleView;
@end
