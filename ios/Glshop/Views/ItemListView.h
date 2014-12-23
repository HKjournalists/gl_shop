//
//  ItemListView.h
//  Glshop
//
//  Created by River on 14-11-6.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ItemListView : UIView <UICollectionViewDataSource,UICollectionViewDelegate>
{
@private
    UICollectionView *_collectionView;
    UIButton         *_leftBtn;
    UIButton         *_rightBtn;
    BOOL              _drag;
}

@property (nonatomic, strong) NSArray *items;
@property (nonatomic, readonly) UICollectionView *collectionView;

@end
