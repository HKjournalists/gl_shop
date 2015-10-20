//
//  ActivityRulesView.m
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/28.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "ActivityRulesView.h"
#import "UILabel+adjustText.h"
@implementation ActivityRulesView


-(void)loadSubViews
{
    [self loadHeadTitleView];
    
    CGRect desc_1 = [self createSubDescriptionsWith:@"shuzi1_" detailDescription:activity_desc1 numPoint:CGPointMake(CGRectGetMinX(self.titleLabel.frame), CGRectGetMaxY(self.lineView.frame)+10)];
    CGRect desc_2 = [self createSubDescriptionsWith:@"shuzi2_" detailDescription:activity_desc2 numPoint:CGPointMake(CGRectGetMinX(self.titleLabel.frame), CGRectGetMaxY(desc_1)+10)];
   CGRect desc_3 = [self createSubDescriptionsWith:@"shuzi3_" detailDescription:activity_desc3 numPoint:CGPointMake(CGRectGetMinX(self.titleLabel.frame), CGRectGetMaxY(desc_2)+10)];
    CGRect desc_4 = [self createSubDescriptionsWith:@"shuzi4_" detailDescription:activity_desc4 numPoint:CGPointMake(CGRectGetMinX(self.titleLabel.frame), CGRectGetMaxY(desc_3)+10)];
    
//    UILabel *textLabel = [[UILabel alloc] initWithFrame:CGRectMake(0, CGRectGetMaxY(desc_3)+10, CGRectGetWidth(self.frame), CGRectGetHeight(self.frame))];
//    NSMutableAttributedString *testStr = [[NSMutableAttributedString alloc] initWithString:@"kajdsklfjaksfjkajfkajfjasjjakdsfj"];
//    [testStr addAttribute:NSBackgroundColorAttributeName value:GROUPBUYTEXTYELLOWCOLOR range:NSMakeRange(0, testStr.length)];
//    [textLabel setAttributedText:testStr];
//    [textLabel sizeToFit];
//    
//    [self addSubview:textLabel];
    
}
- (CGRect)createSubDescriptionsWith:(NSString *)imageName detailDescription:(NSString *)desc numPoint:(CGPoint)numPoint
{
    UIImageView *numImage = [[UIImageView alloc] initWithFrame:CGRectMake(numPoint.x, numPoint.y, 13, 13)];
    numImage.image = [UIImage imageNamed:imageName];
    [self addSubview:numImage];
    
    

    UILabel *descLabel = [[UILabel alloc] initWithFrame:CGRectMake(CGRectGetMaxX(numImage.frame),CGRectGetMinY(numImage.frame),CGRectGetWidth(self.frame)-CGRectGetMaxX(numImage.frame)-10,50)];
    descLabel.font = GROUPBUYTEXTFONT;
    descLabel.textColor = GROUPBUYWHITECOLOR;
    descLabel.numberOfLines = 0;
    NSMutableAttributedString *descStr = [[NSMutableAttributedString alloc] initWithString:desc];
    NSMutableParagraphStyle *paragraphStyle = [[NSMutableParagraphStyle alloc] init];
    [paragraphStyle setLineSpacing:10];
    [descStr addAttribute:NSParagraphStyleAttributeName value:paragraphStyle range:NSMakeRange(0, descStr.length)];
    
    [descLabel setAttributedText:descStr];
    [descLabel sizeToFit];
    
    
    [self addSubview:descLabel];
    return descLabel.frame;

}
@end
