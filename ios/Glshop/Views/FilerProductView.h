//
//  FilerProductView.h
//  FilerView
//
//  Created by River on 15-3-14.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "FilerTitleView.h"

@interface FilerProductView : UIView <UITableViewDataSource,UITableViewDelegate,FilerSelectAllDelegate>
{
@private
    FilerTitleView *_filerTitleView;
    UIScrollView   *_scrollView;
    
    UITableView *_tableView1;
    UITableView *_tableView2;
    UITableView *_tableView3;
    UITableView *_tableView4;
    UITableView *_tableView5;
    
    NSInteger _table2SelectRow;
}

- (void)resetFilerProductData;

- (void)tableView:(UITableView *)tableView didSelectBoxRowAtIndexPath:(NSIndexPath *)indexPath boxSelect:(BOOL)isSelect;

@end
