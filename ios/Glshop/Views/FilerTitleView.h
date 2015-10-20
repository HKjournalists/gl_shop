//
//  FilerTitleView.h
//  FilerView
//
//  Created by River on 15-3-14.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HLCheckbox.h"

@protocol FilerSelectAllDelegate <NSObject>

/**
 *@brief 选中全部
 */
- (void)filerSelectAll:(BOOL)isSelectAll;

@end

@interface FilerTitleView : UIView
@property (strong, nonatomic) IBOutlet UIImageView *imgView;

@property (strong, nonatomic) IBOutlet UILabel *labe1;
@property (strong, nonatomic) IBOutlet UILabel *label2;
@property (strong, nonatomic) IBOutlet UILabel *label3;
@property (strong, nonatomic) IBOutlet HLCheckbox *checkBox;

@property (nonatomic, weak) id <FilerSelectAllDelegate> delegate;

@end
