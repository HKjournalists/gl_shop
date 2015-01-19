//
//  AddressViewController.m
//  Glshop
//
//  Created by River on 14-12-15.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "AddressViewController.h"
#import "PublicGuideView.h"
#import "UnloadAddressViewController.h"
#import "REPlaceholderTextView.h"
#import "CompanyAuthViewController.h"
#import "IndicateSubView.h"
#import "PreViewPublicViewController.h"
#import "HJActionSheet.h"
#import "PlaceSelect.h"

static NSString *commonStr = @"选择";

@interface AddressViewController () <UITableViewDataSource,UITableViewDelegate,UIActionSheetDelegate,UITextFieldDelegate,UITextViewDelegate,PlaceDidSelect>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSArray *sections;
@property (nonatomic, strong) UIDatePicker *datePicker;
@property (nonatomic, strong) IndicateSubView *listView; // 下拉选择单位
@property (nonatomic, strong) HJActionSheet *hjSheet;
@property (nonatomic, strong) PlaceSelect *place;

@property (nonatomic, strong) UITextField *sellAmountTextField;
@property (nonatomic, strong) UITextField *unitPriceTextField;

@end

@implementation AddressViewController

- (void)viewDidLoad {
    [super viewDidLoad];

}

#pragma mark - Override
- (void)initDatas {
    self.title = @"交易信息";
    _sections = @[@1,@2,@1,@2,@2,@1];
    
    if ([_publicModel.type integerValue] == BussinessTypeSell) {
        
        if ([_publicModel.addresstype[@"text"] isEqualToString:@"卖家"]) {
            _sections = @[@1,@2,@1,@2,@2,@1];
        }else {
            _sections = @[@1,@2,@1,@1,@2,@1];
        }
    }else {
        
        if ([_publicModel.addresstype[@"text"] isEqualToString:@"买家"]) {
            _sections = @[@1,@2,@1,@2,@2,@1];
        }else {
            _sections = @[@1,@2,@1,@1,@2,@1];
        }
    }
    
}

- (void)loadSubViews {
    
    PublicGuideView *guideView = [[PublicGuideView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 60) stepIndex:2];
    [self.view addSubview:guideView];
    
    _place = [[PlaceSelect alloc] init];
    _place.delegate = self;
    
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vtop = guideView.vbottom;
    _tableView.vheight -= kTopBarHeight+50+guideView.vheight;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    [self.view addSubview:_tableView];
    
    UIButton *nexBtn = [UIFactory createBtn:BlueButtonImageName bTitle:@"下一步" bframe:CGRectMake(10, self.tableView.vbottom+5, self.view.vwidth-20, 40)];
    [nexBtn addTarget:self action:@selector(nextOption:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:nexBtn];
    
}

#pragma mark - Getter
- (IndicateSubView *)listView {
    if (!_listView) {
        _listView = [[IndicateSubView alloc] initWithFrame:CGRectMake(96, 10, 90, 24)];
        if (_publicModel.unit) {
            DLog(@"%@",_publicModel.unit[@"val"]);
            _listView.currentSelect = [_publicModel.unit[DataValueKey]  isEqualToString: MathUnitTon] ? 0 : 1;
        }else {
            _listView.currentSelect = 0;
        }
        _listView.dataSource = @[@"单位:吨",@"单位:立方"];
        _listView.weakViewController = self;
        __block typeof(self) weakSelf = self;
        _listView.selectBlock = ^(NSInteger index) {
            if (index == 0) {
                weakSelf.publicModel.unit = [NSDictionary dictionaryWithObjectsAndKeys:MathUnitTon,DataValueKey,@"吨",DataTextKey, nil];
            }else {
                weakSelf.publicModel.unit = [NSDictionary dictionaryWithObjectsAndKeys:MathUnitCube,DataValueKey,@"立方",DataTextKey, nil];
            }
        };
        _listView.borderColor = [UIColor lightGrayColor];
    }
    return _listView;
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return _sections.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [_sections[section] integerValue];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.imageView.image = [UIImage imageNamed:@"attestation_icon_"];
    cell.textLabel.font = [UIFont systemFontOfSize:14.5];
    
    if (indexPath.section == 0) {
        cell.textLabel.text = @"销售量";

        [cell addSubview:self.listView];
        
        _sellAmountTextField = [UITextField textFieldWithPlaceHodler:@"填写" withDelegate:self];
        _sellAmountTextField.frame = CGRectMake(_listView.vright, 22-15, 130, 30);
        _sellAmountTextField.textAlignment = NSTextAlignmentRight;
        _sellAmountTextField.keyboardType = UIKeyboardTypeDecimalPad;
        _sellAmountTextField.delegate = self;
        _sellAmountTextField.text = [_publicModel.totalnum stringValue];
        [cell.contentView addSubview:_sellAmountTextField];
    }//-----------------------------------------------------------------------------------------------------------
    
    else if (indexPath.section == 1) {

        if (indexPath.row == 0) {
            cell.textLabel.text = @"交易日期范围";
            NSString *startStr = _publicModel.starttime;
            NSString *targetStr = startStr.length > 10 ? [startStr substringToIndex:10] : section_Value;
            UIButton *startBtn = [UIButton buttonWithTip:targetStr target:self selector:@selector(selectTime:)];
            startBtn.tag = 100*indexPath.section+1;
            startBtn.frame = CGRectMake(147, 6, 80, 30);
            [startBtn setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
            startBtn.titleLabel.font = [UIFont systemFontOfSize:13.f];
            [cell addSubview:startBtn];
            
            UILabel *contact = [UILabel labelWithTitle:@"至"];
            contact.frame = CGRectMake(startBtn.vright, 0, 15, cell.vheight);
            [cell addSubview:contact];
            
            NSString *endStr = _publicModel.endtime;
            NSString *endTStr = endStr.length > 10 ? [endStr substringToIndex:10] : section_Value;
            UIButton *endBtn = [UIButton buttonWithTip:endTStr target:self selector:@selector(selectTime:)];
            endBtn.tag = 100*indexPath.section+2;
            [endBtn setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
            endBtn.frame = CGRectMake(contact.vright+2, startBtn.vtop, startBtn.vwidth, startBtn.vheight);
            endBtn.titleLabel.font = [UIFont systemFontOfSize:13.f];
            [cell addSubview:endBtn];
        }else {
            cell.textLabel.text = @"交易地域";
            cell.detailTextLabel.font = [UIFont systemFontOfSize:14.f];
            cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
            cell.detailTextLabel.text = _publicModel.areaFullName;
        }
        
    }//-----------------------------------------------------------------------------------------------------------
    
    else if (indexPath.section == 2) {
        cell.textLabel.text = @"到港单价（单位:元)";
        _unitPriceTextField = [UITextField textFieldWithPlaceHodler:@"填写" withDelegate:self];
        _unitPriceTextField.frame = CGRectMake(100, 22-15, 210, 30);
        _unitPriceTextField.keyboardType = UIKeyboardTypeDecimalPad;
        _unitPriceTextField.textAlignment = NSTextAlignmentRight;
        if (_publicModel.price) {
            _unitPriceTextField.text = [NSString stringWithFormat:@"%@", _publicModel.price];
        }
        _unitPriceTextField.placeholder = @"填写";
        _unitPriceTextField.delegate = self;
        [cell.contentView addSubview:_unitPriceTextField];
    }//-----------------------------------------------------------------------------------------------------------
    
    else if (indexPath.section == 3) {
        if (indexPath.row == 0) {
            cell.textLabel.text = @"卸货地点指定方式";
            if (_publicModel.addresstype.count == 0) {
                cell.detailTextLabel.text = section_Value;
            }else if ([_publicModel.type integerValue] == BussinessTypeSell) {
                if ([_publicModel.addresstype[@"text"] isEqualToString:@"卖家"]) {
                    cell.detailTextLabel.text = @"己方指定";
                    _publicModel.isOwen = YES;
                }else {
                    cell.detailTextLabel.text = @"对方指定";
                    _publicModel.isOwen = NO;
                }
            }else {
                if ([_publicModel.addresstype[@"text"] isEqualToString:@"买家"]) {
                    cell.detailTextLabel.text = @"己方指定";
                    _publicModel.isOwen = YES;
                }else {
                    cell.detailTextLabel.text = @"对方指定";
                    _publicModel.isOwen = NO;
                }
            }
            cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        }else {
            cell.imageView.image = nil;
            cell.textLabel.text = @"详细交易地址";
            cell.detailTextLabel.font = [UIFont systemFontOfSize:13.f];
            cell.detailTextLabel.numberOfLines = 2;
            if (_publicModel.areaFullName.length && _publicModel.address) {
                cell.detailTextLabel.text = FommatString(_publicModel.areaFullName, _publicModel.address);
            }
            cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        }
        
    }//-----------------------------------------------------------------------------------------------------------

    else if (indexPath.section == 4 ) {
        if (indexPath.row == 0) {
            cell.imageView.image = nil;
            cell.textLabel.text = [_publicModel.type integerValue] == 1 ? @"购买备注" : @"销售备注";
        }else {
            REPlaceholderTextView *textView = [[REPlaceholderTextView alloc] initWithFrame:CGRectMake(10, 5,cell.vwidth-20, 80)];
            textView.placeholder = [_publicModel.type integerValue] == 1 ? @"请输入购买备注信息" : @"请输入销售备注信息";
            textView.returnKeyType = UIReturnKeyDone;
            textView.delegate = self;
            textView.layer.borderWidth = 1;
            textView.text = _publicModel.remark;
            textView.layer.borderColor = [UIColor lightGrayColor].CGColor;;
            [cell addSubview:textView];
        }

    }//-----------------------------------------------------------------------------------------------------------

    else if (indexPath.section == 5) {
        cell.imageView.image = nil;
        [cell addSubview:[self tipView]];
    }
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.row == 1 && indexPath.section == 4) {
        return 100;
    }else if (indexPath.section == 5){
        if ([_publicModel.type integerValue] == 2) {
            return 120;
        }
        return 80;
    }
    return 44;
}

#define sectionHigh 5
- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    return sectionHigh;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return sectionHigh;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, sectionHigh)];
    return view;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, sectionHigh)];
    return view;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    switch (indexPath.section) {
        case 0:
        {
            
        }
            break;
        case 1:
        {
            if (indexPath.row == 1) { // 选择河段
                [_place showPlaceSelectView];
            }
            
        }
            break;
        case 2:
        {
            
        }
            break;
        case 3:
        {
            if (indexPath.row == 0) {
                UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:@"选择指定方式" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:@"己方指定",@"对方指定", nil];
                sheet.tag = indexPath.section*100+5;
                [sheet showInView:self.view];
            }else {
                UnloadAddressViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"UnloadAddressViewControllerId"];
                [self.navigationController pushViewController:vc animated:YES];
            }
        }
            break;
            case 4:
        {

        }
            break;
            
        default:
            break;
    }

}

#pragma mark - UIActionSheet Delegae
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (fmodf(actionSheet.tag, 100) == 5) {
        if (buttonIndex == 0) {
            if ([_publicModel.type integerValue] == BussinessTypeSell) {
                _publicModel.addresstype = [NSDictionary dictionaryWithObjectsAndKeys:@2,@"val",@"卖家",@"text", nil];
            }else {
                _publicModel.addresstype = [NSDictionary dictionaryWithObjectsAndKeys:@1,@"val",@"买家",@"text", nil];
            }
            _sections = @[@1,@2,@1,@2,@2,@1];
        }else if (buttonIndex == 1) {
            if ([_publicModel.type integerValue] == BussinessTypeSell) {
                _publicModel.addresstype = [NSDictionary dictionaryWithObjectsAndKeys:@1,@"val",@"买家",@"text", nil];
            }else {
                _publicModel.addresstype = [NSDictionary dictionaryWithObjectsAndKeys:@2,@"val",@"卖家",@"text", nil];
            }
            _sections = @[@1,@2,@1,@1,@2,@1];
        }
        [_tableView reloadData];
    }
    
}

#pragma mark - UITextField Delegate
- (void)textFieldDidEndEditing:(UITextField *)textField {
    if (textField == _sellAmountTextField) {
        _publicModel.totalnum = [NSNumber numberWithFloat:[textField.text floatValue]];
    }else if (textField == _unitPriceTextField) {
        _publicModel.price = [NSNumber numberWithFloat:[textField.text floatValue]];
    }
}

#pragma mark - Private
- (CompanyAuthViewController *)publicInfoVC {
    for (UIViewController *vc in self.navigationController.viewControllers) {
        if ([vc isKindOfClass:[CompanyAuthViewController class]]) {
            return (CompanyAuthViewController *)vc;
        }
    }
    return nil;
}

/**
 *@brief 创建警告图
 */
- (UIView *)tipView {
    float height = [_publicModel.type integerValue] == 2 ? 100 : 60;
    
    UIView *tipView = [UIFactory createPromptViewframe:CGRectMake(10, 10, self.view.vwidth-20, height) tipTitle:nil];
    UILabel *label1 = [UILabel labelWithTitle:@"1、*号为必填项；"];
    label1.font = [UIFont systemFontOfSize:14.f];
    label1.frame = CGRectMake(10, 30, 280, 20);
    [tipView addSubview:label1];
    
    if ([_publicModel.type integerValue] == 2) {
        UILabel *label2 = [UILabel labelWithTitle:@"2、本平台提供真实高效的交易，成交后平台将收取您的交易手续费。"];
        label2.font = label1.font;
        label2.frame = CGRectMake(10, label1.vbottom, 280, 40);
        label2.numberOfLines = 2;
        [tipView addSubview:label2];
    }
    
    return tipView;
}


#pragma mark - UIActions
/**
 *@brief 跳转到发布页面
 */
- (void)nextOption:(UIButton *)button {
//    if (![self authPublicData]) {
//        return;
//    }
    
    PreViewPublicViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"PreViewPublicViewControllerId"];
    vc.publicModel = _publicModel;
    [self.navigationController pushViewController:vc animated:YES];
}

/**
 *@brief 验证信息
 */
- (BOOL)authPublicData {
    if (!_publicModel.totalnum) {
        HUD(@"请填写销售量");
        return NO;
    }
    
    if (!_publicModel.starttime || !_publicModel.endtime) {
        HUD(@"请选择交易时间范围");
        return NO;
    }
    
    if (!_publicModel.price) {
        HUD(@"请填写到港单价");
        return NO;
    }
    
    if (!_publicModel.addresstype) {
        HUD(@"请选择交易地点指定方式");
        return NO;
    }
    
    return YES;
}

/**
 *@brief 弹出时间选择框
 *@discussion 开始时间按钮的tag = indexPath.section*100+1 开始时间按钮的tag = indexPath.section*100+2。根据tag来找到button和model
 */
- (void)selectTime:(UIButton *)button {
    [_sellAmountTextField resignFirstResponder];
    [_unitPriceTextField resignFirstResponder];
    
    
    NSString *title = @"选择交易时间";
    UIView *bgView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 245)];
    bgView.backgroundColor = [UIColor whiteColor];
    
    UILabel *tilteLab = [UILabel labelWithTitle:title];
    tilteLab.frame = CGRectMake(0, 0, bgView.vwidth, 25);
    tilteLab.font = [UIFont boldSystemFontOfSize:16.f];
    tilteLab.textAlignment = NSTextAlignmentCenter;
//    [bgView addSubview:tilteLab];
    
    _datePicker = [[UIDatePicker alloc] initWithFrame:CGRectMake(0, tilteLab.vbottom, bgView.vwidth, 0)];
    _datePicker.datePickerMode = UIDatePickerModeDate;
    NSLocale *locale = [[NSLocale alloc] initWithLocaleIdentifier:@"zh_CN"];//设置为中
    _datePicker.locale = locale;
    _datePicker.date = [NSDate date];
    [bgView addSubview:_datePicker];
    
    UIView *headerView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, bgView.vwidth, 40)];
    headerView.backgroundColor = RGB(200, 200, 200, 1);
    [bgView addSubview:headerView];
    
//    UIButton *sure = [UIButton buttonWithTip:@"确定" target:self selector:@selector(sureDate:)];
    UIButton *sure = [UIFactory createBtn:@"登录-未触及状态" bTitle:@"完成" bframe:CGRectMake(headerView.vwidth-70-10, 5, 70, 30)];
    [sure addTarget:self action:@selector(sureDate:) forControlEvents:UIControlEventTouchUpInside];
    sure.tag = button.tag+1000;
    [headerView addSubview:sure];
    
    UIButton *cancel = [UIButton buttonWithTip:@"取消" target:self selector:@selector(cancelSelectDate:)];
    cancel.frame = CGRectMake(0, sure.vtop, 80, 30);
    [cancel setTitleColor:[UIColor orangeColor] forState:UIControlStateNormal];
    [headerView addSubview:cancel];
    
    _hjSheet = [[HJActionSheet alloc] initWithTitle:nil contentView:bgView];
    [_hjSheet showSheet];
}

/**
 *@brief 确认时间
 */
- (void)sureDate:(UIButton *)button {
    NSInteger tag = button.tag - 1000;
    UIButton *tBtn = (UIButton *)[_tableView viewWithTag:tag];
    NSString *dateStr = [Utilits stringFromFomate:_datePicker.date formate:kTimeFormart];
    NSString *dateModelStr = [Utilits stringFromFomate:_datePicker.date formate:kTimeDetail_Format];
    [tBtn setTitle:dateStr forState:UIControlStateNormal];
    
    if (fmodf(tag, 100) == 1) {
        _publicModel.starttime = dateModelStr;
    }else {
        _publicModel.endtime = dateModelStr;
    }
    
    [_hjSheet hideSheet];
}

- (void)cancelSelectDate:(UIButton *)button {
    [_hjSheet hideSheet];
}


#pragma mark - UITextView Delegate
- (BOOL)textView:(UITextView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString *)text {
    if ([text isEqualToString:@"\n"]) {
        [textView resignFirstResponder];
        return NO;
    }
    return YES;
}

- (void)textViewDidEndEditing:(UITextView *)textView {
    _publicModel.remark = textView.text;
}

#pragma mark - placeDidSelect Delegate
- (void)placeDidSelect:(NSString *)place theAreaCode:(NSString *)areaCode{
    _publicModel.areaFullName = place;
    _publicModel.areaCode = areaCode;
    NSIndexPath *indexPath = [NSIndexPath indexPathForRow:1 inSection:1];
    [_tableView reloadRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationAutomatic];
}

@end

