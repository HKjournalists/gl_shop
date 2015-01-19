//
//  BarSegment.m
//  HJActionSheet
//
//  Created by River on 14-12-29.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "BarSegment.h"

#define BarHeight 40

@interface BarSegment ()

@property (nonatomic, strong) NSMutableArray *btnArray;
@property (nonatomic, assign) CGRect pathRect;
@property (nonatomic, assign) NSInteger selctedIndex;

@end

@implementation BarSegment

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = [UIColor whiteColor];
        self.btnArray = [NSMutableArray arrayWithCapacity:3];
        _selctedIndex = 0;
        NSArray *titles = @[@"求购信息",@"出售信息",@"筛选"];
        for (int i = 0; i < 3; i++) {
            UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
            [btn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
            if (i == 0) {
                [btn setSelected:YES];
            }
            if (i <= 1) {
                [btn setTitleColor:[UIColor orangeColor] forState:UIControlStateSelected];
            }
            if (i == 2) {
                [btn setImage:[UIImage imageNamed:@"Buy_sell_icon_shaixuan"] forState:UIControlStateNormal];
                
            }
            [btn setTitle:titles[i] forState:UIControlStateNormal];
            btn.frame = CGRectMake(0+i*320/3, 2, 320/3, BarHeight-4);
            [btn addTarget:self action:@selector(choseIndex:) forControlEvents:UIControlEventTouchUpInside];
            [self addSubview:btn];
            [self.btnArray addObject:btn];
        }
    }
    return self;
}


// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
#define PathGap 9
- (void)drawRect:(CGRect)rect {
    // Drawing code
    UIImage *image = [UIImage imageNamed:@"Buy_sell_icon_xian"];
    [image drawInRect:CGRectMake(320/3, 10, 1, 20)];
    
    [image drawInRect:CGRectMake(320/3*2, 10, 1, 20)];
    
    if (_selctedIndex <= 1) {
        UIView *view = [self.btnArray objectAtIndex:_selctedIndex];
        CGRect pathRect = view.frame;
        UIBezierPath *path=[UIBezierPath bezierPath];
        [path moveToPoint:CGPointMake(CGRectGetMinX(pathRect)+PathGap, CGRectGetMaxY(self.frame)-2)];
        [path setLineWidth:3];
        [[UIColor orangeColor] setStroke];
        [path addLineToPoint:CGPointMake(CGRectGetMaxX(pathRect)-PathGap, CGRectGetMaxY(self.frame)-2)];
        [path closePath];
        [path stroke];
    }

}

- (void)choseIndex:(UIButton *)button {
    [button setSelected:YES];
    NSInteger index = [self.btnArray indexOfObject:button];
    if (index < 2) {
        self.selctedIndex = index;
    }
    if (_selctedIndex != 2) {
        for (UIButton *btn in self.btnArray) {
            if (btn != button && index != 2) {
                [btn setSelected:NO];
            }
        }
    }
    
    if ([self.delegate respondsToSelector:@selector(barDidSelectedAtIndex:)]) {
        [self.delegate barDidSelectedAtIndex:index];
    }
    if (_selctedIndex != 2) {
        [self setNeedsDisplay];
    }
}

@end
