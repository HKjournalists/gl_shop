//
//  AreaItemTableViewCell.h
//  FilerView
//
//  Created by River on 15-3-16.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>
@class HLCheckbox;

@interface AreaItemTableViewCell : UITableViewCell

@property (strong, nonatomic) IBOutlet UILabel *nameLabel;
@property (strong, nonatomic) IBOutlet HLCheckbox *box;
@end
