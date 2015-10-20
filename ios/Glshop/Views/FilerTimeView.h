//
//  FilerTimeView.h
//  Glshop
//
//  Created by River on 15-3-17.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "HLCheckbox.h"

#define kUserDefalutsKeyStartTime @"filerStartTime"
#define kUserDefalutsKeyEndTime @"filerEndTime"

@interface FilerTimeView : UIView
{
@private
    UIDatePicker *_datePicker;
}

@property (strong, nonatomic) IBOutlet HLCheckbox *allTimeBox;
@property (strong, nonatomic) IBOutlet HLCheckbox *selectTimeBox;

- (void)resetFilerTimeData;

@end
