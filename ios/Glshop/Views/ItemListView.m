//
//  ItemListView.m
//  Glshop
//
//  Created by River on 14-11-6.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "ItemListView.h"
#import "ItmeCell.h"
#import "ForcastViewController.h"

#define item_size_width  83
#define item_size_height 73
#define item_gap 5

static NSString *resueIdentify = @"ItemresueIdentify";

@implementation ItemListView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = RGB(240, 240, 240, 1);
        
        _leftBtn = [UIButton buttonWithTip:nil target:self selector:@selector(scrollToleft:)];
        _leftBtn.frame = CGRectMake(0, 0, 30, self.vheight);
        [self setButton:_leftBtn enable:NO];
        [self addSubview:_leftBtn];
        
        _rightBtn = [UIButton buttonWithTip:nil target:self selector:@selector(scrollToRight:)];
        _rightBtn.frame = CGRectMake(self.vwidth-_leftBtn.vwidth, 0, _leftBtn.vwidth, _leftBtn.vheight);
        [self addSubview:_rightBtn];
        
        UICollectionViewFlowLayout *layout = [[UICollectionViewFlowLayout alloc] init];
        layout.itemSize = CGSizeMake(item_size_width, item_size_height);
        layout.scrollDirection = UICollectionViewScrollDirectionHorizontal;
        layout.sectionInset = UIEdgeInsetsMake(1, 3, 1, 3);
        layout.minimumLineSpacing = item_gap;
        _collectionView = [[UICollectionView alloc] initWithFrame:CGRectMake(_leftBtn.vright, 0, self.vwidth-60, self.vheight) collectionViewLayout:layout];
        _collectionView.showsHorizontalScrollIndicator = NO;
        _collectionView.backgroundColor = [UIColor clearColor];
        _collectionView.decelerationRate = 0.5;
        _collectionView.dataSource = self;
        _collectionView.delegate = self;
        [_collectionView registerClass:[ItmeCell class] forCellWithReuseIdentifier:resueIdentify];
        [self addSubview:_collectionView];
        
        [self hideButtons:YES];
    }
    return self;
}

#pragma mark - Setters
- (void)setItems:(NSArray *)items {
    _items = items;
    
    if (_items.count <= 0) {
        [self hideButtons:YES];
    }else {
        [self hideButtons:NO];
    }
    
    if (_items.count <= [self per_row_count]) {
        [self setButton:_rightBtn enable:NO];
    }else {
        [self setButton:_rightBtn enable:YES];
    }
    
}

#pragma mark - Private
- (NSInteger)per_row_count {
    if (iPhone6) {
        return 5;
    }else if (iPhone6plus) {
        return 6;
    }else {
        return 3;
    }
}

- (void)hideButtons:(BOOL)isHidden {
    _rightBtn.hidden = isHidden;
    _leftBtn.hidden = isHidden;
}

- (void)setButton:(UIButton *)btn enable:(BOOL)enable {
    btn.enabled = enable;
    if (enable) {
        if (btn == _rightBtn) {
            [_rightBtn setImage:[UIImage imageNamed:@"index_icon_arrow_xiao"] forState:UIControlStateNormal];
        }else {
            [_leftBtn setImage:[UIImage imageNamed:@"index_icon_arrow_you"] forState:UIControlStateNormal];
        }
    }else {
        if (btn == _rightBtn) {
           [_rightBtn setImage:[UIImage imageNamed:@"index_icon_arrow_hui"] forState:UIControlStateNormal];
        }else {
            [_leftBtn setImage:[UIImage imageNamed:@"index_icon_arrow_huise"] forState:UIControlStateNormal];
        }
    }
}

#pragma mark - UIActions
- (void)scrollToRight:(UIButton *)btn {
    
    if (self.items.count < [self per_row_count]) {
        return;
    }
    
    float index = _collectionView.contentSize.width / _collectionView.contentOffset.x;
    if (index >= 2) {
        [self setButton:_rightBtn enable:YES];
        [_collectionView scrollRectToVisible:CGRectMake(_collectionView.contentOffset.x+_collectionView.vwidth, 0, _collectionView.vwidth, _collectionView.vheight) animated:YES];
    }else {
        float poor = _collectionView.contentSize.width-_collectionView.contentOffset.x;
        [_collectionView setContentOffset:CGPointMake(_collectionView.contentOffset.x+poor-_collectionView.vwidth, 0) animated:YES];
        [self setButton:_rightBtn enable:NO];
    }
    
}

- (void)scrollToleft:(UIButton *)btn {
    float index =  _collectionView.contentOffset.x/_collectionView.vwidth;
    if (index >= 1) {
        [self setButton:_leftBtn enable:YES];
        [_collectionView scrollRectToVisible:CGRectMake(_collectionView.contentOffset.x-_collectionView.vwidth, 0, _collectionView.vwidth, _collectionView.vheight) animated:YES];
    }else {
        [self setButton:_leftBtn enable:NO];
        [_collectionView setContentOffset:CGPointMake(0, 0) animated:YES];
    }

}

#pragma mark - UICollectionView Delegate
- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{
    return _items.count;
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
    ItmeCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:resueIdentify forIndexPath:indexPath];
    if (cell == nil) {
        cell = [[ItmeCell alloc] initWithFrame:CGRectZero];
    }
    cell.todayModel = _items[indexPath.row];
    return cell;
}

- (void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath
{
    [collectionView deselectItemAtIndexPath:indexPath animated:YES];
    
    ForcastViewController *vc = [[ForcastViewController alloc] init];
    [self.firstViewController.navigationController pushViewController:vc animated:YES];
}

#pragma mark - UIScrollView Delegate
- (void)scrollViewDidScroll:(UIScrollView *)scrollView {
    if (scrollView.contentOffset.x > 0) {
        [self setButton:_leftBtn enable:YES];
    }else {
        [self setButton:_leftBtn enable:NO];
    }
    
    float index = _collectionView.contentSize.width / (_collectionView.contentOffset.x+_collectionView.vwidth);
    if (index <= 1) {
        [self setButton:_rightBtn enable:NO];
    }else {
        [self setButton:_rightBtn enable:YES];
    }
}

@end
