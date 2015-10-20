//
//  FilerProductView.m
//  FilerView
//
//  Created by River on 15-3-14.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "FilerProductView.h"
#import "FilerProductTableViewCell.h"

NSString *const reuse1 = @"filerProductCell";
static NSInteger upTableHeight = 180;
static NSInteger bottomTableHeight = 240;

@implementation FilerProductView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        self.backgroundColor = [UIColor clearColor];
        _table2SelectRow = 0;
        
        _filerTitleView = [[FilerTitleView alloc] initWithFrame:CGRectMake(0, 0, self.vwidth, 61)];
        _filerTitleView.delegate = self;
        BOOL isAll = YES;
        for (GoodsModel *model in [SynacObject productTopModels]) {
            if (model.selected) {
                isAll = NO;
            }
        }
        _filerTitleView.checkBox.selected = isAll;
        [self addSubview:_filerTitleView];
        
        _scrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, _filerTitleView.vbottom+5, self.vwidth, self.vheight-_filerTitleView.vheight-5)];
        _scrollView.backgroundColor = [UIColor clearColor];
        _scrollView.contentSize = CGSizeMake(320, 600);
        [self addSubview:_scrollView];
        
        _tableView1 = [[UITableView alloc] initWithFrame:CGRectMake(0, 0, 320.0/3-20, upTableHeight) style:UITableViewStylePlain];
        _tableView1.scrollEnabled = NO;
        _tableView1.dataSource = self;
        _tableView1.delegate = self;
        _tableView1.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_tableView1 registerNib:[UINib nibWithNibName:@"FilerProductTableViewCell" bundle:[NSBundle mainBundle]] forCellReuseIdentifier:reuse1];
        [_scrollView addSubview:_tableView1];
        
        _tableView2 = [[UITableView alloc] initWithFrame:CGRectMake(_tableView1.vright+1, 0, 320.0/3, _tableView1.vheight) style:UITableViewStylePlain];
        _tableView2.dataSource = self;
        _tableView2.delegate = self;
        _tableView2.scrollEnabled = NO;
        _tableView2.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_tableView2 registerNib:[UINib nibWithNibName:@"FilerProductTableViewCell" bundle:[NSBundle mainBundle]] forCellReuseIdentifier:reuse1];
        [_scrollView addSubview:_tableView2];
        [_tableView2 selectRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0] animated:NO scrollPosition:UITableViewScrollPositionNone];
        
        _tableView3 = [[UITableView alloc] initWithFrame:CGRectMake(_tableView2.vright+1, 0, 320.0/3+20, _tableView1.vheight) style:UITableViewStylePlain];
        _tableView3.scrollEnabled = NO;
        _tableView3.dataSource = self;
        _tableView3.delegate = self;
        _tableView3.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_tableView3 registerNib:[UINib nibWithNibName:@"FilerProductTableViewCell" bundle:[NSBundle mainBundle]] forCellReuseIdentifier:reuse1];
        [_scrollView addSubview:_tableView3];
        
        _tableView4 = [[UITableView alloc] initWithFrame:CGRectMake(0, _tableView1.vbottom+8, _tableView1.vwidth, bottomTableHeight) style:UITableViewStylePlain];
        _tableView4.scrollEnabled = NO;
        _tableView4.dataSource = self;
        _tableView4.delegate = self;
        _tableView4.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_tableView4 registerNib:[UINib nibWithNibName:@"FilerProductTableViewCell" bundle:[NSBundle mainBundle]] forCellReuseIdentifier:reuse1];
        [_scrollView addSubview:_tableView4];
        
        _tableView5 = [[UITableView alloc] initWithFrame:CGRectMake(_tableView4.vright+1, _tableView4.vtop, 320.0*2/3+20, bottomTableHeight) style:UITableViewStylePlain];
        _tableView5.scrollEnabled = NO;
        _tableView5.dataSource = self;
        _tableView5.delegate = self;
        _tableView5.separatorStyle = UITableViewCellSeparatorStyleNone;
        [_tableView5 registerNib:[UINib nibWithNibName:@"FilerProductTableViewCell" bundle:[NSBundle mainBundle]] forCellReuseIdentifier:reuse1];
        [_scrollView addSubview:_tableView5];

    }
    return self;
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (tableView == _tableView1) {
        return 1;
    }else if (tableView == _tableView2) {
        return [[SynacObject sendSubType] count];
    }else if (tableView == _tableView3) {
        GoodsModel *model = [SynacObject sendSubType][_table2SelectRow];
        NSArray *childs = [SynacObject sendsGroundSonProductType:model.goodsVal];
        return childs.count;
    }else if (tableView == _tableView4) {
        return 1;
    }else {
        return [[SynacObject stoneSubType] count];
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 30;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    FilerProductTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:reuse1];
    
    if (tableView == _tableView1) {
        GoodsModel *model = [SynacObject sendsModel];
        cell.nameLabel.text = model.goodsName;
        cell.box.selected = model.selected;
        
    }else if (tableView == _tableView2) {
        GoodsModel *model = [SynacObject sendSubType][indexPath.row];
        cell.nameLabel.text = model.goodsName;
        cell.box.selected = model.selected;
        
    }else if (tableView == _tableView3) {
        GoodsModel *model = [SynacObject sendSubType][_table2SelectRow];
        NSArray *names = [SynacObject sendsGroundSonProductTypeName:model.goodsVal];
        NSString *name = names[indexPath.row];
        NSMutableString *taName = [NSMutableString stringWithString:name];
        if ([name hasSuffix:@"mm"]) {
            [taName replaceOccurrencesOfString:@"mm" withString:@"" options:0 range:NSMakeRange(0, taName.length)];
        }
        cell.nameLabel.text = taName;
        NSArray *childs = [SynacObject sendsGroundSonProductType:model.goodsVal];
        GoodChildModel *childModel = childs[indexPath.row];
        cell.box.selected = childModel.selected;
        
    }else if (tableView == _tableView4) {
        GoodsModel *model = [SynacObject stoneModel];
        cell.nameLabel.text = model.goodsName;
        cell.box.selected = model.selected;
    }else if (tableView == _tableView5) {
        GoodChildModel *stone = [SynacObject stoneSubType][indexPath.row];
        cell.nameLabel.text = stone.nameWithUnit;
        cell.box.selected = stone.selected;
    }
    
    if (tableView == _tableView2) {
        [_tableView2 selectRowAtIndexPath:[NSIndexPath indexPathForRow:_table2SelectRow inSection:0] animated:NO scrollPosition:UITableViewScrollPositionNone];
    }
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    if (tableView == _tableView2) {
        _table2SelectRow = indexPath.row;
        [_tableView3 reloadData];
    }else {
        [tableView deselectRowAtIndexPath:indexPath animated:YES];
    }
}

#pragma mark - Public
- (void)resetFilerProductData {
    _filerTitleView.checkBox.selected = YES;
    for (GoodsModel *model in [SynacObject goodsArray]) {
        model.selected = NO;
    }
    for (GoodsModel *send in [SynacObject sendSubType]) {
        NSArray *childs = [SynacObject sendsGroundSonProductType:send.goodsVal];
        for (GoodChildModel *child in childs) {
            child.selected = NO;
        }
    }
    
    for (GoodChildModel *child in [SynacObject stoneSubType]) {
        child.selected = NO;
    }
    
    [_tableView1 reloadData];
    [_tableView2 reloadData];
    [_tableView3 reloadData];
    [_tableView4 reloadData];
    [_tableView5 reloadData];
}

- (void)tableView:(UITableView *)tableView didSelectBoxRowAtIndexPath:(NSIndexPath *)indexPath boxSelect:(BOOL)isSelect {
    if (isSelect) {
        _filerTitleView.checkBox.selected = NO;
    }
    
    if (tableView == _tableView2) {
        _table2SelectRow = indexPath.row;
    }
    
    if (isSelect) {
        if (tableView == _tableView1) {
            [self table1Seleted:YES];
        }else if (tableView == _tableView2) {
            [self table2Seleted:YES];
        }else if (tableView == _tableView3) {
            [self table3Seleted:YES indexRow:indexPath.row];
        }else if (tableView == _tableView4) {
            [self table4Seleted:YES];
        }else if (tableView == _tableView5) {
            [self table5Seleted:YES indexRow:indexPath.row];
        }
    }else {
        if (tableView == _tableView1) {
            [self table1Seleted:NO];
        }else if (tableView == _tableView2) {
            [self table2Seleted:NO];
        }else if (tableView == _tableView3) {
            [self table3Seleted:NO indexRow:indexPath.row];
        }else if (tableView == _tableView4) {
            [self table4Seleted:NO];
        }else if (tableView == _tableView5) {
            [self table5Seleted:NO indexRow:indexPath.row];
        }
    }
}

#pragma mark - Private
- (void)table1Seleted:(BOOL)isSelected {
    GoodsModel *model = [SynacObject sendsModel];
    model.selected = isSelected;
    for (GoodsModel *model in [SynacObject sendSubType]) {
        model.selected = isSelected;
        NSArray *childs = [SynacObject sendsGroundSonProductType:model.goodsVal];
        for (GoodChildModel *child in childs) {
            child.selected = isSelected;
        }
    }
    [self updateUpTableViews];
}

- (void)table2Seleted:(BOOL)isSelected {
    GoodsModel *model = [SynacObject sendSubType][_table2SelectRow];
    model.selected = isSelected;
    NSArray *childs = [SynacObject sendsGroundSonProductType:model.goodsVal];
    for (GoodChildModel *child in childs) {
        child.selected = isSelected;
    }
    if (isSelected) {
        GoodsModel *sends = [SynacObject sendsModel];
        sends.selected = YES;
    }
    
    BOOL isAllSendsUnSelected = YES;
    for (GoodsModel *model in [SynacObject sendSubType]) {
        if (model.selected) {
            isAllSendsUnSelected = NO;
            break;
        }
    }
    if (isAllSendsUnSelected) {
        GoodsModel *sends = [SynacObject sendsModel];
        sends.selected = NO;
    }
    [self updateUpTableViews];
}

- (void)table3Seleted:(BOOL)isSelected indexRow:(NSInteger)row {
    GoodsModel *model = [SynacObject sendSubType][_table2SelectRow];
    
    NSArray *childs = [SynacObject sendsGroundSonProductType:model.goodsVal];
    GoodChildModel *childModel = childs[row];
    childModel.selected = isSelected;
    
    if (isSelected) {
        model.selected = YES;
        
        GoodsModel *sends = [SynacObject sendsModel];
        sends.selected = YES;
    }
    
    BOOL isAllChildUnSelected = YES;
    for (GoodChildModel *child in [SynacObject sendsGroundSonProductType:model.goodsVal]) {
        if (child.isSelected) {
            isAllChildUnSelected = NO;
        }
    }
    
    if (isAllChildUnSelected) {
        model.selected = NO;
    }
    
    BOOL isAllSendsUnSelected = YES;
    for (GoodsModel *send in [SynacObject sendSubType]) {
        if (send.selected) {
            isAllSendsUnSelected = NO;
            break;
        }
    }
    if (isAllSendsUnSelected) {
        GoodsModel *sends = [SynacObject sendsModel];
        sends.selected = NO;
    }
    
    [self updateUpTableViews];
}

- (void)table4Seleted:(BOOL)isSelected {
    GoodsModel *stone = [SynacObject stoneModel];
    stone.selected = isSelected;
    
    for (GoodChildModel *child in [SynacObject stoneSubType]) {
        child.selected = isSelected;
    }
    
    [self updateBottomTableViews];
}

- (void)table5Seleted:(BOOL)isSelected indexRow:(NSInteger)row{
    GoodChildModel *model = [SynacObject stoneSubType][row];
    model.selected = isSelected;
    if (isSelected) {
        GoodsModel *stone = [SynacObject stoneModel];
        stone.selected = isSelected;
    }
    
    BOOL isAllStoneUnSelected = YES;
    for (GoodChildModel *model in [SynacObject stoneSubType]) {
        if (model.isSelected) {
            isAllStoneUnSelected = NO;
            break;
        }
    }
    
    if (isAllStoneUnSelected) {
        GoodsModel *stone = [SynacObject stoneModel];
        stone.selected = NO;
    }
    
    [self  updateBottomTableViews];
}

- (void)updateUpTableViews {
    [_tableView2 reloadData];
    [_tableView3 reloadData];
    [_tableView1 reloadData];
}

- (void)updateBottomTableViews {
    [_tableView4 reloadData];
    [_tableView5 reloadData];
}

#pragma mark - FilerSelectAllDelegate
- (void)filerSelectAll:(BOOL)isSelectAll {
    if (isSelectAll) {
        [self resetFilerProductData];
    }
}

@end
