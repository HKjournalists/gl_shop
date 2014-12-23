//
//  FilterControl.m
//  Glshop
//
//  Created by River on 14-11-28.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "FilterControl.h"
#import "JGActionSheet.h"
#import "WLHorizontalSegmentedControl.h"

#define kDefaultSectionHeight 110

@interface FilterControl () <UITableViewDataSource,UITableViewDelegate,JGActionSheetDelegate>

@property (nonatomic, strong) JGActionSheet *sheet;
@property (nonatomic, strong) UITableView   *tableView;
@property (nonatomic, strong) UITableView   *tableView1;
@property (nonatomic, strong) UITableView   *tableView2;
@property (nonatomic, strong) UIView *filterView;
@property (nonatomic, assign) NSInteger selectedTag;

@property (nonatomic, strong) NSArray *sendArray;
@property (nonatomic, strong) NSArray *stoneArray;
@property (nonatomic, strong) NSArray *areaArray;


@end

@implementation FilterControl

- (instancetype)init
{
    self = [super init];
    if (self) {
        _selectedTag = 0;
        
        _areaArray = @[@"晋江段",@"江阴段"];
        _sendArray = @[@"江沙",@"淡化海砂",@"山砂",@"湖砂",@"机制砂",@"河砂",];
        _stoneArray = @[@"瓜子片",@"水石头",@"碎石1-3",@"碎石1-3",@"碎石1-3",@"碎石1-3",@"碎石1-3",@"碎石1-3",];
    }
    return self;
}

#pragma mark - Public
- (void)showFilter {
    JGActionSheetSection *s = [JGActionSheetSection sectionWithTitle:@"选择货物规格" message:nil contentView:[self filterView1]];
    JGActionSheetSection *s1 = [JGActionSheetSection sectionWithTitle:@"选择货物类型" message:nil contentView:[self filterView2]];
    JGActionSheetSection *s2 = [JGActionSheetSection sectionWithTitle:@"选择交易时间" message:nil contentView:[self filterView3]];
    JGActionSheetSection *s3 = [JGActionSheetSection sectionWithTitle:nil message:nil contentView:[self contentView]];
    
    _sheet = [JGActionSheet actionSheetWithSections:@[ s,s1,s2,s3]];
    _sheet.outsidePressBlock = ^(JGActionSheet *sheet){
        [sheet dismissAnimated:YES];
    };
    
    _sheet.buttonPressedBlock = ^(JGActionSheet *sheet, NSIndexPath *path) {
        if (path.section == 1) {
            [sheet dismissAnimated:YES];
        }
    };
    
    _sheet.delegate = self;
    [_tableView1 performSelector:@selector(flashScrollIndicators) withObject:nil afterDelay:1];
    
    [_sheet showInView:self.weakViewConterller.view animated:YES];
    
}

#pragma mark - Private
- (UIView *)filterView1 {
    _filterView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH-20, kDefaultSectionHeight)];
    _filterView.backgroundColor = [UIColor whiteColor];;
    
    _tableView = [[UITableView alloc] initWithFrame:_filterView.bounds style:UITableViewStylePlain];
    _tableView.dataSource = self;
    _tableView.delegate = self;
    _tableView.rowHeight = 30;
    _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    [_filterView addSubview:_tableView];
    
    return _filterView;
}

- (UIView *)filterView2 {
     UIView * contentView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH-20, kDefaultSectionHeight)];
    contentView.backgroundColor = [UIColor whiteColor];
    
    WLHorizontalSegmentedControl * _vSeg1 = [[WLHorizontalSegmentedControl alloc] initWithItems:@[@"黄沙",@"石子",  ]];
    _vSeg1.tintColor = RGB(230, 230, 230, 1);
    _vSeg1.selcetBlock = ^(NSInteger index) {
        if (index == 0) {
            _tableView1.hidden = NO;
            _tableView2.hidden = YES;
        }else {
            _tableView1.hidden = YES;
            _tableView2.hidden = NO;
        }
    };
    _vSeg1.frame = CGRectMake(0, 0, _filterView.vwidth, 30);
    _vSeg1.allowsMultiSelection = NO;
    _vSeg1.selectedSegmentIndex = 0;
    _vSeg1.selectedStyle = 1;
    [contentView addSubview:_vSeg1];
    
    _tableView1 = [[UITableView alloc] initWithFrame:CGRectMake(0, _vSeg1.vbottom, contentView.vwidth, contentView.vheight-_vSeg1.vheight) style:UITableViewStylePlain];
    _tableView1.dataSource = self;
    _tableView1.delegate = self;
    _tableView1.rowHeight = 30;
    _tableView1.separatorStyle = UITableViewCellSeparatorStyleNone;
    [contentView addSubview:_tableView1];
    
    _tableView2 = [[UITableView alloc] initWithFrame:CGRectMake(0, _vSeg1.vbottom, contentView.vwidth, contentView.vheight-_vSeg1.vheight) style:UITableViewStylePlain];
    _tableView2.dataSource = self;
    _tableView2.delegate = self;
    _tableView2.rowHeight = 30;
    _tableView2.hidden = YES;
    _tableView2.separatorStyle = UITableViewCellSeparatorStyleNone;
    [contentView addSubview:_tableView2];
    
    return contentView;
}

- (UIView *)filterView3 {
    UIView * contentView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH-20, 100)];
    contentView.backgroundColor = [UIColor whiteColor];
    
    UIDatePicker *datePicker = [[UIDatePicker alloc] init];
    datePicker.frame = CGRectMake(0, -30, contentView.vwidth, 100);
    datePicker.datePickerMode = UIDatePickerModeDate;
    datePicker.transform = CGAffineTransformMakeScale(0.8, 0.8);
    NSLocale *locale = [[NSLocale alloc] initWithLocaleIdentifier:@"zh_CN"];//设置为中
    datePicker.locale = locale;
    [contentView addSubview:datePicker];
    
    return contentView;
}

- (UIView *)contentView {
    UIView *contentView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH-20, 30)];
    contentView.backgroundColor = [UIColor clearColor];
    
    UIButton *leftBtn = [UIButton buttonWithTip:@"取消" target:nil selector:nil];
    leftBtn.layer.cornerRadius = 2.f;
    [leftBtn addTarget:self action:@selector(canlceSheet:) forControlEvents:UIControlEventTouchUpInside];
    leftBtn.backgroundColor = [UIColor whiteColor];
    [leftBtn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    leftBtn.frame = CGRectMake(0, 0, 130, 30);
    [contentView addSubview:leftBtn];
    
    UIButton *rightBtn = [UIButton buttonWithTip:@"确定" target:nil selector:nil];
    rightBtn.layer.cornerRadius = 2.f;
    rightBtn.backgroundColor = [UIColor whiteColor];
    [rightBtn addTarget:self action:@selector(canlceSheet:) forControlEvents:UIControlEventTouchUpInside];
    [rightBtn setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    rightBtn.frame = CGRectMake(contentView.vright-leftBtn.vwidth, leftBtn.vtop, leftBtn.vwidth, leftBtn.vheight);
    [contentView addSubview:rightBtn];
    
    return contentView;
}

#pragma mark - UITableView DataSource
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    if (tableView == _tableView) {
        return _areaArray.count;
    }else if (tableView == _tableView1) {
        return _sendArray.count;
    }else {
        return _stoneArray.count;
    }
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *s = @"gdsgs";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:s];
    if (!cell) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:nil];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        cell.frame = CGRectMake(0, 0, SCREEN_WIDTH-20, 30);
        UILabel *label = [[UILabel alloc] initWithFrame:cell.bounds];
        label.tag = 100;
        label.font = [UIFont boldSystemFontOfSize:14.f];
        label.textAlignment = NSTextAlignmentCenter;
        label.backgroundColor = [UIColor clearColor];
        [cell.contentView addSubview:label];
        UIView *line = [[UIView alloc] initWithFrame:CGRectMake(0, cell.vbottom, cell.vwidth, 0.5)];
        line.backgroundColor = [UIColor lightGrayColor];
        [cell addSubview:line];
        
        UIView *line1 = [[UIView alloc] initWithFrame:CGRectMake(0, cell.vtop, cell.vwidth, 0.5)];
        line1.backgroundColor = [UIColor lightGrayColor];
        [cell addSubview:line1];
    }
    
    UILabel *label = (UILabel *)[cell viewWithTag:100];
    label.text = @"石头沙子石头沙子";
    
    if (tableView == _tableView) {
        label.text = _areaArray[indexPath.row];
    }else if (tableView == _tableView1) {
        label.text = _sendArray[indexPath.row];
    }else {
        label.text = _stoneArray[indexPath.row];
    }
    
    if (indexPath.row == _selectedTag) {
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
    }
    
    return cell;

}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    _selectedTag = indexPath.row;
    [tableView reloadData];
}

#pragma mark - UIActions
- (void)canlceSheet:(UIButton *)button {
    [_sheet dismissAnimated:YES];
}

@end
