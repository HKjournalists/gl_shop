//
//  ItmeCell.h
//  Glshop
//
//  Created by River on 14-11-6.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "ProductTodayModel.h"

@interface ItmeCell : UICollectionViewCell
{
@private
    UILabel *_priceLabel;
    UILabel *_productNameLabel;
    UILabel *_subProductNameLabel;
    UIImageView *_indicateView;
}

@property (nonatomic, strong) ProductTodayModel *todayModel;

@end
