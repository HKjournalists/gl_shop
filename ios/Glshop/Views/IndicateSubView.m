//
//  IndicateSubView.m
//  Glshop
//
//  Created by River on 14-12-16.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "IndicateSubView.h"
#import "UIImage+ImageWithColor.h"

#define KIndicateViewDefaultHeight 30
#define KTableCellHeight 30

@interface IndicateSubView ()

@property (nonatomic, strong) UIView *maskView;
@property (nonatomic, assign) NSInteger selectRow;

@end

@implementation IndicateSubView

#pragma mark - InitMethods
- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        _selectRow = 0;
        [self initSubViews];
    }
    return self;
}

- (id)initWithCoder:(NSCoder *)aDecoder
{
    if (self = [super initWithCoder:aDecoder]) {
        [self initSubViews];
    }
    return self;
}

- (void)initSubViews
{
    _tapBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    _tapBtn.titleLabel.font = [UIFont boldSystemFontOfSize:13.f];
    [_tapBtn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [_tapBtn addTarget:self action:@selector(doSomeThing:) forControlEvents:UIControlEventTouchUpInside];
    [self addSubview:_tapBtn];
    
    _indicateImageView = [[UIImageView alloc] initWithFrame:CGRectZero];
    _indicateImageView.image = [UIImage imageNamed:@"information_icon_down"];
    [self addSubview:_indicateImageView];
    
    _tableView = [[UITableView alloc] initWithFrame:CGRectZero style:UITableViewStylePlain];
    _tableView.frame = CGRectMake(self.vleft, self.vtop, self.vwidth, 0);
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    _tableView.rowHeight = 30;
    _tableView.delegate = self;
    _tableView.dataSource = self;
}

#pragma mark - Setter
- (void)setBorderColor:(UIColor *)borderColor
{
    self.layer.borderWidth = 1;
    self.layer.borderColor = borderColor.CGColor;
    [self setNeedsDisplay];
}

- (void)setCurrentSelect:(NSInteger)currentSelect {
    _currentSelect = currentSelect;
    
    _selectRow = currentSelect;
}

- (void)setDataSource:(NSArray *)dataSource
{
    if (_dataSource != dataSource) {
        _dataSource = dataSource;
    }
    
    if (_dataSource.count == 0) {
        return;
    }
    [_tapBtn setTitle:_dataSource[_selectRow] forState:UIControlStateNormal];
}

- (UIView *)maskView {
    if (!_maskView) {
        _maskView = [[UIView alloc] initWithFrame:self.weakViewController.view.bounds];
        _maskView.backgroundColor = RGB(0, 0, 0, 0.0);
        UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(hide)];
        [_maskView addGestureRecognizer:tap];
    }
    return _maskView;
}

#pragma mark - UITableView DataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return self.dataSource.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellIdentifier = @"cell";
    
    UIView *bgView = [[UIView alloc] initWithFrame:CGRectZero];
    bgView.backgroundColor = RGB(239, 239, 239, 1);
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:cellIdentifier] ;
    cell.vheight = 30;
    UILabel *title = [[UILabel alloc] initWithFrame:CGRectMake(0, -0.25, self.vwidth-0.25, cell.vheight)];
    title.layer.borderColor = [UIColor lightGrayColor].CGColor;
    title.layer.borderWidth = .25f;
    title.font = [UIFont systemFontOfSize:16];
    title.textAlignment = NSTextAlignmentCenter;
    title.tag = 2014;
    [cell setSelectedBackgroundView:bgView];
    [cell.contentView addSubview:title];

    title.text = self.dataSource[indexPath.row];
    
    return cell;
    
}

- (void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.row == _selectRow) {
        cell.selected = YES;
    }else {
        cell.selected = NO;
    }
}

#pragma mark - UITableView Delegate
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    UILabel *label = (UILabel *)[cell viewWithTag:2014];
    [_tapBtn setTitle:label.text forState:UIControlStateNormal];
    _selectRow = indexPath.row;
    if ( _selectRow != _currentSelect) {
        UITableViewCell *cell = [tableView cellForRowAtIndexPath:[NSIndexPath indexPathForRow:0 inSection:0]];
        cell.selected = NO;
    }
    [tableView setNeedsDisplay];
    if (self.selectBlock) {
        _selectBlock(indexPath.row);
    }
    [self hide];
}

#pragma mark - Layout
- (void)layoutSubviews
{
    [super layoutSubviews];
    
    [_tapBtn setFrame:CGRectMake(-10, 0, self.vwidth, self.vheight)];
    
    [_indicateImageView setFrame:CGRectMake(_tapBtn.vright-10, self.vheight/2-10/2, 15, 10)];
    
    CGRect rect = [self convertRect:_tapBtn.frame toView:self.weakViewController.view];
    if (!_isShowList) {
        _tableView.frame = CGRectMake(rect.origin.x+10, rect.origin.y+self.vheight-10, self.vwidth, 0);
    }
}

#pragma mark - Private
- (void)showList
{
    [self.weakViewController.view insertSubview:self.maskView belowSubview:self];
    CGRect rect = [self convertRect:_tapBtn.frame toView:self.weakViewController.view];
    float tableHeight = self.dataSource.count*30;
    // 列表fram不超出父视图，否则点击cell没反应
    _tableView.frame = CGRectMake(rect.origin.x+10, rect.origin.y+self.vheight, self.vwidth, tableHeight);
    
    [self.weakViewController.view addSubview:_tableView];
}

- (void)hide
{
    [_maskView removeFromSuperview];
    [self setBackgroundColor:[UIColor clearColor]];
    [UIView animateWithDuration:0.25 animations:^{
        _indicateImageView.layer.transform = CATransform3DIdentity;
        _isShowList = NO;
        _tableView.vheight = 0;
    }];
}

#pragma mark - Action
- (void)doSomeThing:(UIButton *)btn
{
    if (!_isShowList) {
        [UIView animateWithDuration:0.25 animations:^{
            [_indicateImageView.layer setTransform:CATransform3DMakeRotation(-M_PI/1.000001, 0, 0, 1)];
            _isShowList = YES;
            
            [self showList];
        }];
    }else if (_isShowList) {
        
        [self hide];
    }
    
    if (_tapBlock) {
        _tapBlock();
    }
}
@end
