//
//  FilerTimeView.m
//  Glshop
//
//  Created by River on 15-3-17.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "FilerTimeView.h"
#import "HJActionSheet.h"

@interface FilerTimeView ()

@property (strong, nonatomic) IBOutlet UIButton *startTimeBtn;
@property (strong, nonatomic) IBOutlet UIButton *endTimeBtn;
@property (strong, nonatomic) IBOutlet UIImageView *imgView1;
@property (strong, nonatomic) IBOutlet UIImageView *imgView2;
@property (nonatomic, strong) HJActionSheet *sheet;

@property (nonatomic, assign) BOOL isStartTime;

@end

@implementation FilerTimeView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        UIView *view = [[[NSBundle mainBundle] loadNibNamed:@"FilerTimeView" owner:self options:nil]lastObject];
        [self addSubview:view];
        
        UIImage *bjImg = [UIImage imageNamed:@"wallet_beijing"];
        bjImg = [bjImg resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
        _imgView1.image = _imgView2.image = bjImg;
        
        UIImage *btnImg = [UIImage imageNamed:@"Buy_sell_icon_arrowhead"];
        [_startTimeBtn setImage:btnImg forState:UIControlStateNormal];
        [_startTimeBtn setTitleEdgeInsets:UIEdgeInsetsMake(0, -20, 0, 0)];
        [_startTimeBtn setImageEdgeInsets:UIEdgeInsetsMake(0, 100, 0, 0)];
        
        [_endTimeBtn setImage:btnImg forState:UIControlStateNormal];
        [_endTimeBtn setTitleEdgeInsets:UIEdgeInsetsMake(0, -20, 0, 0)];
        [_endTimeBtn setImageEdgeInsets:UIEdgeInsetsMake(0, 100, 0, 0)];
        
        _allTimeBox.boxImage = [UIImage imageNamed:@"check_unselected"];
        _allTimeBox.selectImage = [UIImage imageNamed:@"dressing-by-screening_gouxuan"];
        
        _selectTimeBox.boxImage = [UIImage imageNamed:@"check_unselected"];
        _selectTimeBox.selectImage = [UIImage imageNamed:@"dressing-by-screening_gouxuan"];
        
        __block typeof(self) this = self;
        _allTimeBox.tapBlock = ^(BOOL selected) {
            if (selected) { 
                [this resetFilerTimeData];
                [this.startTimeBtn setTitle:@"选择" forState:UIControlStateNormal];
                [this.endTimeBtn setTitle:@"选择" forState:UIControlStateNormal];
            }else {
                this.selectTimeBox.selected = YES;
            }
        };
        
        _selectTimeBox.tapBlock = ^(BOOL selected) {
            if (selected) {
                this.allTimeBox.selected = NO;
            }else {
                this.allTimeBox.selected = YES;
                [this.startTimeBtn setTitle:@"选择" forState:UIControlStateNormal];
                [this.endTimeBtn setTitle:@"选择" forState:UIControlStateNormal];
            }
        };
        
        
        NSString *start = [[NSUserDefaults standardUserDefaults] objectForKey:kUserDefalutsKeyStartTime];
        NSString *end = [[NSUserDefaults standardUserDefaults] objectForKey:kUserDefalutsKeyEndTime];
        if (start.length && end.length) {
            _selectTimeBox.selected = YES;
            [_startTimeBtn setTitle:start forState:UIControlStateNormal];
            [_endTimeBtn setTitle:end forState:UIControlStateNormal];
        }else {
            _allTimeBox.selected = YES;
        }
    }
    return self;
}

- (IBAction)selectTime:(UIButton *)button {
    
    _isStartTime = button == _startTimeBtn ? YES : NO;
    
    NSString *title = @"选择交易时间";
    UIView *bgView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 245)];
    bgView.backgroundColor = [UIColor whiteColor];
    
    UILabel *tilteLab = [UILabel labelWithTitle:title];
    tilteLab.frame = CGRectMake(0, 0, bgView.vwidth, 25);
    tilteLab.font = [UIFont boldSystemFontOfSize:16.f];
    tilteLab.textAlignment = NSTextAlignmentCenter;
    
    _datePicker = [[UIDatePicker alloc] initWithFrame:CGRectMake(0, tilteLab.vbottom, bgView.vwidth, 0)];
    _datePicker.datePickerMode = UIDatePickerModeDate;
    NSLocale *locale = [[NSLocale alloc] initWithLocaleIdentifier:@"zh_CN"];//设置为中
    _datePicker.locale = locale;
    _datePicker.date = [NSDate date];
    [bgView addSubview:_datePicker];
    
    UIView *headerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, bgView.vwidth, 40)];
    headerView.backgroundColor = RGB(200, 200, 200, 1);
    [bgView addSubview:headerView];
    
    UIButton *sure = [UIFactory createBtn:YelloCommnBtnImgName bTitle:@"完成" bframe:CGRectMake(headerView.vwidth-70-10, 5, 70, 30)];
    [sure addTarget:self action:@selector(sureDate:) forControlEvents:UIControlEventTouchUpInside];
    sure.tag = button.tag+1000;
    [headerView addSubview:sure];
    
    UIButton *cancel = [UIButton buttonWithTip:globe_cancel_str target:self selector:@selector(cancelSelectDate:)];
    cancel.frame = CGRectMake(0, sure.vtop, 80, 30);
    [cancel setTitleColor:[UIColor orangeColor] forState:UIControlStateNormal];
    [headerView addSubview:cancel];
    
    _sheet = [[HJActionSheet alloc] initWithTitle:nil contentView:bgView];
    [_sheet showSheet];
}

/**
 *@brief 确认时间
 */
- (void)sureDate:(UIButton *)button {
    _selectTimeBox.selected = YES;
    _allTimeBox.selected = NO;

    NSString *dateStr = [Utilits stringFromFomate:_datePicker.date formate:kTimeFormart];
    if (_isStartTime) {
        [[NSUserDefaults standardUserDefaults] setObject:dateStr forKey:kUserDefalutsKeyStartTime];
        [[NSUserDefaults standardUserDefaults] synchronize];
        [_startTimeBtn setTitle:dateStr forState:UIControlStateNormal];
    }else {
        [[NSUserDefaults standardUserDefaults] setObject:dateStr forKey:kUserDefalutsKeyEndTime];
        [[NSUserDefaults standardUserDefaults] synchronize];
        [_endTimeBtn setTitle:dateStr forState:UIControlStateNormal];
    }
    
    [_sheet hideSheet];
}

- (void)cancelSelectDate:(UIButton *)button {
    [_sheet hideSheet];
}

#pragma mark - Public
- (void)resetFilerTimeData {
    [[NSUserDefaults standardUserDefaults] removeObjectForKey:kUserDefalutsKeyEndTime];
    [[NSUserDefaults standardUserDefaults] removeObjectForKey:kUserDefalutsKeyStartTime];
    _allTimeBox.selected = YES;
    _selectTimeBox.selected = NO;
    [self.startTimeBtn setTitle:@"选择" forState:UIControlStateNormal];
    [self.endTimeBtn setTitle:@"选择" forState:UIControlStateNormal];
}

@end
