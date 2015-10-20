//
//  FilerProductTableViewCell.h
//  Glshop
//
//  Created by River on 15-3-17.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HLCheckbox.h"

@interface FilerProductTableViewCell : UITableViewCell

@property (strong, nonatomic) IBOutlet HLCheckbox *box;
@property (strong, nonatomic) IBOutlet UILabel *nameLabel;

@end
