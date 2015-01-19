//
//  IndicateExtionView.h
//  新浪微博
//
//  Created by ios on 14-4-17.
//  Copyright (c) 2014年 Sunny. All rights reserved.
//  下拉列表控件，用于选择操作

#import <UIKit/UIKit.h>

typedef NS_ENUM(NSInteger, ListDirction){
    listUp,
    listDown
};

typedef void(^TapBlock)();
typedef void(^SelectRowAction)(NSInteger index);

@interface IndicateExtionView : UIView <UITableViewDataSource,UITableViewDelegate>
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
@property (nonatomic, assign) ListDirction dir;
@property (nonatomic, weak) UIViewController *weakViewController;
@property (nonatomic, strong) UIColor *borderColor;
@property (readonly) NSInteger selectRow;

- (id)initWithFrame:(CGRect)frame title:(NSString *)titleStr;

@end
