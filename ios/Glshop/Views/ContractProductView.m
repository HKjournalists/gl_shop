//
//  ContractProductView.m
//  Glshop
//
//  Created by River on 15-2-3.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "ContractProductView.h"
#import "ContractDetailViewController.h"

@interface ContractProductView ()

@property (nonatomic, assign) BOOL bottomLeft;

@end

@implementation ContractProductView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {

        
        UIImage *bgImg = [UIImage imageNamed:imgName_whiltebg];
        bgImg = [bgImg resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
        UIButton *imgViewHeader = [UIButton buttonWithType:UIButtonTypeCustom];
        imgViewHeader.tag = 201;
        [imgViewHeader addTarget:self action:@selector(kkk) forControlEvents:UIControlEventTouchUpInside];
        imgViewHeader.frame = CGRectMake(0, 0, self.vwidth, 50);
        [imgViewHeader setBackgroundImage:bgImg forState:UIControlStateNormal];
//        [self insertSubview:imgViewHeader atIndex:0];
        [self addSubview:imgViewHeader];
        
        UIView *view = [[[NSBundle mainBundle] loadNibNamed:@"ContractProductView" owner:self options:nil]lastObject];
        [self addSubview:view];
        
        UIImageView *imgViewFooter = [[UIImageView alloc] initWithFrame:CGRectMake(0, 60, self.vwidth, 40)];
        imgViewFooter.image = bgImg;
        [self insertSubview:imgViewFooter atIndex:0];
        
        _bottomLeft = YES;
    }
    return self;
}

- (IBAction)FirstAction:(UIButton *)sender {
    [self commAction:YES];
}
- (IBAction)secondAction:(id)sender {
    [self commAction:NO];
}

- (void)commAction:(BOOL)left {
    if (left) {
        [_btn1 setTitleColor:[UIColor orangeColor] forState:UIControlStateNormal];
        [_btn2 setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
        [UIView animateWithDuration:0.25 animations:^{
            _indicateLine.vleft = 0;
        }];
        [_weakScrollView setContentOffset:CGPointMake(0, 0) animated:YES];
    }else {
        [_btn1 setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
        [_btn2 setTitleColor:[UIColor orangeColor] forState:UIControlStateNormal];
        if (_indicateLine.vleft == 0) {
            [UIView animateWithDuration:0.25 animations:^{
                _indicateLine.vleft += 160;
            }];
        }
        [_weakScrollView setContentOffset:CGPointMake(self.vwidth, 0) animated:YES];
    }
}

//- (void)updateConstraints {
//    [_indicateLine remakeConstraints:^(MASConstraintMaker *make) {
//        if (self.bottomLeft) {
//            make.left.mas_equalTo(self);
//            make.trailing.mas_equalTo(self.centerX);
//        }else {
//            make.trailing.mas_equalTo(self);
//            make.left.mas_equalTo(self.centerX);
//        }
//        make.width.mas_equalTo(160);
//        make.height.mas_equalTo(2);
//        make.bottom.mas_equalTo(self);
//    }];
//    [super updateConstraints];
//}

- (void)kkk {
    DLog(@"fdg");
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    [super touchesBegan:touches withEvent:event];
    
    UIButton *btn = (UIButton *)[self viewWithTag:201];
    
    UITouch *touch = (UITouch *)touches.anyObject;
    CGPoint point = [touch locationInView:touch.view];
    CGRect rect = CGRectMake(0, 0, self.vwidth, 50);
    if (CGRectContainsPoint(rect, point)) {
        [btn setHighlighted:YES];
    }
}

- (void)touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event {
    [super touchesCancelled:touches withEvent:event];
    
    UIButton *btn = (UIButton *)[self viewWithTag:201];
    
    [btn setHighlighted:NO];
    
}

- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
    [super touchesEnded:touches withEvent:event];
    UIButton *btn = (UIButton *)[self viewWithTag:201];
    [btn setHighlighted:NO];
    
    UITouch *touch = (UITouch *)touches.anyObject;
    CGPoint point = [touch locationInView:touch.view];
    CGRect rect = CGRectMake(0, 0, self.vwidth, 50);
    if (CGRectContainsPoint(rect, point)) {
        ContractDetailViewController *vc = [[ContractDetailViewController alloc] init];
        vc.contractId = _contractId;
        [self.firstViewController.navigationController pushViewController:vc animated:YES];
    }

}

@end
