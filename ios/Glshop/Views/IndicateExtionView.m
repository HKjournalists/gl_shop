//
//  IndicateExtionView.m
//  新浪微博
//
//  Created by ios on 14-4-17.
//  Copyright (c) 2014年 Sunny. All rights reserved.
//

#import "IndicateExtionView.h"
#import "UIImage+ImageWithColor.h"

#define KIndicateViewDefaultHeight 30
#define KTableCellHeight 44

@interface IndicateExtionView ()

@property (nonatomic, strong) UIView *maskView;
@property (nonatomic, assign) NSInteger selectRow;

@end

@implementation IndicateExtionView

#pragma mark - InitMethods
- (id)initWithFrame:(CGRect)frame title:(NSString *)titleStr
{
    self = [super initWithFrame:frame];
    if (self) {
        _selectRow = 0;
        [self initSubViews:titleStr];
    }
    return self;
}

- (id)initWithCoder:(NSCoder *)aDecoder
{
    if (self = [super initWithCoder:aDecoder]) {
        [self initSubViews:nil];
    }
    return self;
}

- (void)initSubViews:(NSString *)title
{
    _tapBtn = [UIButton buttonWithType:UIButtonTypeCustom];
    [_tapBtn setTitle:title forState:UIControlStateNormal];
    _tapBtn.titleLabel.font = [UIFont boldSystemFontOfSize:18.f];
    [_tapBtn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [_tapBtn addTarget:self action:@selector(doSomeThing:) forControlEvents:UIControlEventTouchUpInside];
    [self addSubview:_tapBtn];
    
    _indicateImageView = [[UIImageView alloc] initWithFrame:CGRectZero];
    _indicateImageView.image = [UIImage imageNamed:@"Buy_sell_icon"];
    [self addSubview:_indicateImageView];
    
    _tableView = [[UITableView alloc] initWithFrame:CGRectZero style:UITableViewStylePlain];
    _tableView.frame = CGRectMake(0, 0, self.vwidth, 0);
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    _tableView.delegate = self;
    _tableView.dataSource = self;
}

#pragma mark - Setter
- (void)setBorderColor:(UIColor *)borderColor
{
    self.layer.borderColor = borderColor.CGColor;
    [self setNeedsDisplay];
}

- (void)setDataSource:(NSArray *)dataSource
{
    if (_dataSource != dataSource) {
        _dataSource = dataSource;
    }
    
    if (_dataSource.count == 0) {
        return;
    }
//    [_tapBtn setTitle:_dataSource[0] forState:UIControlStateNormal];
}

- (UIView *)maskView {
    if (!_maskView) {
        _maskView = [[UIView alloc] initWithFrame:self.weakViewController.view.bounds];
        _maskView.backgroundColor = RGB(0, 0, 0, 0.4);
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
    
//    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];

//    if (cell == nil) {
    
        UIView *bgView = [[UIView alloc] initWithFrame:CGRectZero];
        bgView.backgroundColor = RGB(254, 227, 208, 1);
        UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:cellIdentifier] ;
        UILabel *title = [[UILabel alloc] initWithFrame:CGRectMake(0, -0.25, self.vwidth-0.25, cell.vheight)];
        title.layer.borderColor = [UIColor lightGrayColor].CGColor;
        title.layer.borderWidth = .25f;
        title.font = [UIFont boldSystemFontOfSize:16];
        title.textAlignment = NSTextAlignmentCenter;
        title.tag = 2014;
        [cell setSelectedBackgroundView:bgView];
        [cell.contentView addSubview:title];
    
//    }
    
//    UILabel *title  = (UILabel *)[cell.contentView viewWithTag:2014];
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
//    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
////    UILabel *label = (UILabel *)[cell viewWithTag:2014];
////    [_tapBtn setTitle:label.text forState:UIControlStateNormal];
    _selectRow = indexPath.row;
    if ( _selectRow != 0) {
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
    
    [_tapBtn setFrame:CGRectMake(0, 0, self.vwidth, self.vheight)];
    
    [_indicateImageView setFrame:CGRectMake(_tapBtn.vright-35, self.vheight/2-10/2, 15, 10)];
    
    CGRect rect = [self convertRect:_tapBtn.frame toView:self.superview];
    if (!_isShowList) {
        float height = _dir == listDown ? self.vheight : 0;
        _tableView.frame = CGRectMake(rect.origin.x, rect.origin.y+height-10, self.vwidth, 0);
    }
}

#pragma mark - Private
- (void)showList
{
    [self.weakViewController.view insertSubview:self.maskView belowSubview:self];
    [self setBackgroundColor:RGB(220, 220, 220, 0.8)];
    CGRect rect = [self convertRect:_tapBtn.frame toView:self.superview];
    if (_dir == listDown) {
        float height = self.superview.vheight-self.vbottom;
        float tableHeight = self.dataSource.count*44;
        // 列表fram不超出父视图，否则点击cell没反应
        float aviableHeight = tableHeight>height ? height : tableHeight;
        _tableView.frame = CGRectMake(rect.origin.x, rect.origin.y+self.vheight, self.vwidth, aviableHeight);
    }else {
        float height = self.vtop;
        float tableHeight = self.dataSource.count*44;
        // 列表fram不超出父视图，否则点击cell没反应
        float aviableHeight = tableHeight>height ? height : tableHeight;
        _tableView.frame = CGRectMake(rect.origin.x, rect.origin.y-aviableHeight, self.vwidth, aviableHeight);
    }
    [self.superview addSubview:_tableView];
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
