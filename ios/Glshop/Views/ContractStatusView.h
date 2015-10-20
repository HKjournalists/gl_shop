//
//  ContractStatusView.h
//  Glshop
//
//  Created by River on 15-2-10.
//  Copyright (c) 2015年 appabc. All rights reserved.
//  合同状态图

#import <UIKit/UIKit.h>

@class ContractModel;
@interface ContractStatusView : UIView
{
@private
    NSMutableArray *_leftItems;
    NSMutableArray *_rightItems;
    NSMutableArray *_pointerViewArray;
    UIImageView    *_headerView;
    UILabel        *_leftTitleLabel;
    UILabel        *_rightTitleLabel;
}

@property (nonatomic, weak) ContractModel *contractModel;

@property (nonatomic, strong) NSArray *leftItemAttributes;
@property (nonatomic, strong) NSArray *rightItemAttributes;

@end
