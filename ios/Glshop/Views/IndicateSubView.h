//
//  IndicateSubView.h
//  Glshop
//
//  Created by River on 14-12-16.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "IndicateExtionView.h"

typedef void(^TapBlock)();
typedef void(^SelectRowAction)(NSInteger index);

@interface IndicateSubView : UIView <UITableViewDataSource,UITableViewDelegate>
{
@private
    UIButton *_tapBtn;
    UIImageView *_indicateImageView;
    BOOL _isShowList;
    UITableView *_tableView;
}

@property (nonatomic, copy) TapBlock tapBlock;
@property (nonatomic, strong) NSArray *dataSource;
@property (nonatomic, copy) SelectRowAction selectBlock;
@property (nonatomic, weak) UIViewController *weakViewController;
@property (nonatomic, strong) UIColor *borderColor;
@property (nonatomic, assign) NSInteger currentSelect;


@end
