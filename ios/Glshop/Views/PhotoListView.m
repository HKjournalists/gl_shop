//
//  PhotoListView.m
//  Glshop
//
//  Created by River on 14-12-4.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "PhotoListView.h"

#define Photo_Size_Width  60
#define Photo_Size_Height 80

#define ItemStartTag 5555
#define ItemLeftGap 10

@interface PhotoListView ()

@property (nonatomic, strong) UIScrollView *scrollView;

@end

@implementation PhotoListView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        _images = [NSMutableArray array];
        _scrollView = [[UIScrollView alloc] initWithFrame:self.bounds];
    }
    return self;
}

- (void)addPhoto:(UIImage *)image {
    [_images addObject:image];
    NSInteger index = [_images indexOfObject:image];
    [self createItemViewTag:ItemStartTag+index];
}

- (UIView *)createItemViewTag:(NSInteger)itemTag {
    int item_gap = (SCREEN_WIDTH-2*ItemLeftGap-4*Photo_Size_Width)/3;
    NSInteger i = itemTag - ItemStartTag;
    UIImageView *itemView = [[UIImageView alloc] initWithFrame:CGRectMake(ItemLeftGap+i*(Photo_Size_Width+item_gap), self.vheight/2-Photo_Size_Height/2, Photo_Size_Width, Photo_Size_Height)];
    itemView.tag = itemTag;
    
    return itemView;
}

@end
