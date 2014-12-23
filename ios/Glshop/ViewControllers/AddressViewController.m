//
//  AddressViewController.m
//  Glshop
//
//  Created by River on 14-12-15.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "AddressViewController.h"
#import "PublicGuideView.h"
#import "PublicOtherInfoModel.h"
#import "KGModal.h"
#import "UnloadAddressViewController.h"
#import "REPlaceholderTextView.h"
#import "CompanyAuthViewController.h"
#import "IndicateSubView.h"
#import "JGActionSheet.h"
#import "PreViewPublicViewController.h"
#import "HJActionSheet.h"

static NSString *commonStr = @"选择";

@interface AddressViewController () <UITableViewDataSource,UITableViewDelegate,UIActionSheetDelegate,UITextFieldDelegate,UIImagePickerControllerDelegate,UINavigationControllerDelegate,UITextViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSMutableArray *datas;
@property (nonatomic, strong) NSArray *sections;
@property (nonatomic, strong) UIDatePicker *datePicker;
@property (nonatomic, strong) IndicateSubView *listView; // 下拉选择单位
@property (nonatomic, strong) JGActionSheet *actionShet;
@property (nonatomic, strong) HJActionSheet *hjSheet;

@property (nonatomic, strong) UITextField *sellAmountTextField;
@property (nonatomic, strong) UITextField *unitPriceTextField;

@end

@implementation AddressViewController

- (void)viewDidLoad {
    [super viewDidLoad];

}

#pragma mark - Override
- (void)initDatas {
    
    _sections = @[@1,@2,@1,@2,@2,@1];
    
}

- (void)loadSubViews {
    
    PublicGuideView *guideView = [[PublicGuideView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 60) stepIndex:2];
    [self.view addSubview:guideView];
    
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vtop = guideView.vbottom;
    _tableView.vheight -= kTopBarHeight+50+guideView.vheight;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    [self.view addSubview:_tableView];
    
    UIButton *nexBtn = [UIButton buttonWithTip:@"下一步" target:self selector:@selector(nextOption:)];
    nexBtn.backgroundColor = CJBtnColor;
    nexBtn.layer.cornerRadius = 3.f;
    nexBtn.frame = CGRectMake(10, self.tableView.vbottom+5, self.view.vwidth-20, 40);
    [self.view addSubview:nexBtn];
    
}

#pragma mark - Getter
- (IndicateSubView *)listView {
    if (!_listView) {
        _listView = [[IndicateSubView alloc] initWithFrame:CGRectMake(96, 10, 90, 24)];
        _listView.currentSelect = [_publicModel.unit  isEqual: MathUnitTon] ? 0 : 1;
        _listView.dataSource = @[@"单位:吨",@"单位:立方"];
        _listView.weakViewController = self;
        __weak typeof(self) weakSelf = self;
        _listView.selectBlock = ^(NSInteger index) {
            weakSelf.publicModel.unit = index == 0 ? MathUnitTon : MathUnitCube;
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
        _sellAmountTextField.delegate = self;
        _sellAmountTextField.text = [_publicModel.totalnum stringValue];
        [cell.contentView addSubview:_sellAmountTextField];
    }//-----------------------------------------------------------------------------------------------------------
    
    else if (indexPath.section == 1) {

        if (indexPath.row == 0) {
            cell.textLabel.text = @"交易日期范围";
            NSString *startStr = _publicModel.starttime;
            NSString *targetStr = startStr.length > 0 ? startStr : section_Value;
            UIButton *startBtn = [UIButton buttonWithTip:targetStr target:self selector:@selector(selectTime:)];
            startBtn.tag = 100*indexPath.section+1;
            startBtn.frame = CGRectMake(147, 6, 70, 30);
            [startBtn setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
            startBtn.titleLabel.font = [UIFont systemFontOfSize:13.f];
            [cell addSubview:startBtn];
            
            UILabel *contact = [UILabel labelWithTitle:@"至"];
            contact.frame = CGRectMake(startBtn.vright+5, 0, 15, cell.vheight);
            [cell addSubview:contact];
            
            NSString *endStr = _publicModel.limitime;
            NSString *endTStr = endStr.length > 0 ? endStr : section_Value;
            UIButton *endBtn = [UIButton buttonWithTip:endTStr target:self selector:@selector(selectTime:)];
            endBtn.tag = 100*indexPath.section+2;
            [endBtn setTitleColor:[UIColor grayColor] forState:UIControlStateNormal];
            endBtn.frame = CGRectMake(contact.vright+10, startBtn.vtop, startBtn.vwidth, startBtn.vheight);
            endBtn.titleLabel.font = [UIFont systemFontOfSize:13.f];
            [cell addSubview:endBtn];
        }else {
            cell.textLabel.text = @"交易地域";
            cell.detailTextLabel.text = _publicModel.publicOtherInfoModel.bussinessPlace;
            cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        }
        
    }//-----------------------------------------------------------------------------------------------------------
    
    else if (indexPath.section == 2) {
        cell.textLabel.text = @"到港单价（单位:元)";
        _unitPriceTextField = [UITextField textFieldWithPlaceHodler:@"填写" withDelegate:self];
        _unitPriceTextField.frame = CGRectMake(100, 22-15, 210, 30);
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
            NSNumber *number = [_publicModel.addressType objectForKey:@"val"];
            if (_publicModel.addressType.count == 0) {
                cell.detailTextLabel.text = section_Value;
            }else {
                cell.detailTextLabel.text = [number integerValue] == 2 ? @"对方指定" : @"己方指定";
            }
            cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        }else {
            cell.imageView.image = nil;
            cell.textLabel.text = @"详细交易地址";
            cell.detailTextLabel.text = _publicModel.addressModel.address;
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
//                UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:@"选择交易段" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:nil, nil];
//                for (NSString *name in [[SynacInstance sharedInstance] riverSectionsNames]) {
//                    [sheet addButtonWithTitle:name];
//                }
//                sheet.tag = indexPath.section*100+3;
//                [sheet showInView:self.view];
                
                JGActionSheetSection *sheet = [JGActionSheetSection sectionWithTitle:@"选择交易地域" message:nil contentView:[self contryPlace]];
                _actionShet = [JGActionSheet actionSheetWithSections:@[sheet]];
                _actionShet.outsidePressBlock = ^(JGActionSheet *sheet){
                    [sheet dismissAnimated:YES];
                };
                [_actionShet showInView:self.view animated:YES];
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
            _publicModel.addressType = [NSDictionary dictionaryWithObjectsAndKeys:@1,@"val",@"买家",@"text", nil];
        }else if (buttonIndex == 1) {
            _publicModel.addressType = [NSDictionary dictionaryWithObjectsAndKeys:@2,@"val",@"卖家",@"text", nil];
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
 *@brief 省市区
 */
- (UIView *)contryPlace {
    UIView * contentView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH-20, 260)];
    contentView.backgroundColor = [UIColor whiteColor];
    
    NSBundle *bundle = [NSBundle mainBundle];
    NSString *plistPath = [bundle pathForResource:@"area" ofType:@"plist"];
    areaDic = [[NSDictionary alloc] initWithContentsOfFile:plistPath];
    
    NSArray *components = [areaDic allKeys];
    NSArray *sortedArray = [components sortedArrayUsingComparator: ^(id obj1, id obj2) {
        
        if ([obj1 integerValue] > [obj2 integerValue]) {
            return (NSComparisonResult)NSOrderedDescending;
        }
        
        if ([obj1 integerValue] < [obj2 integerValue]) {
            return (NSComparisonResult)NSOrderedAscending;
        }
        return (NSComparisonResult)NSOrderedSame;
    }];
    
    NSMutableArray *provinceTmp = [[NSMutableArray alloc] init];
    for (int i=0; i<[sortedArray count]; i++) {
        NSString *index = [sortedArray objectAtIndex:i];
        NSArray *tmp = [[areaDic objectForKey: index] allKeys];
        [provinceTmp addObject: [tmp objectAtIndex:0]];
    }
    
    province = [[NSArray alloc] initWithArray: provinceTmp];
    
    NSString *index = [sortedArray objectAtIndex:0];
    NSString *selected = [province objectAtIndex:0];
    NSDictionary *dic = [NSDictionary dictionaryWithDictionary: [[areaDic objectForKey:index]objectForKey:selected]];
    
    NSArray *cityArray = [dic allKeys];
    NSDictionary *cityDic = [NSDictionary dictionaryWithDictionary: [dic objectForKey: [cityArray objectAtIndex:0]]];
    city = [[NSArray alloc] initWithArray: [cityDic allKeys]];
    
    
    NSString *selectedCity = [city objectAtIndex: 0];
    district = [[NSArray alloc] initWithArray: [cityDic objectForKey: selectedCity]];
    
    
    
    picker = [[UIPickerView alloc] initWithFrame: CGRectMake(0, 20, 320, 240)];
    picker.dataSource = self;
    picker.delegate = self;
    picker.showsSelectionIndicator = YES;
    [picker selectRow: 0 inComponent: 0 animated: YES];
    [self.view addSubview: picker];
    
    selectedProvince = [province objectAtIndex: 0];
    
    [contentView addSubview:picker];
    
    return contentView;
}

#pragma mark - UIActions
/**
 *@brief 跳转到发布页面
 */
- (void)nextOption:(UIButton *)button {
    PreViewPublicViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"PreViewPublicViewControllerId"];
    vc.publicModel = _publicModel;
    [self.navigationController pushViewController:vc animated:YES];
}

/**
 *@brief 弹出时间选择框
 *@discussion 开始时间按钮的tag = indexPath.section*100+1 开始时间按钮的tag = indexPath.section*100+2。根据tag来找到button和model
 */
- (void)selectTime:(UIButton *)button {
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
    
    UIButton *sure = [UIButton buttonWithTip:@"确定" target:self selector:@selector(sureDate:)];
    sure.frame = CGRectMake(bgView.vwidth-120-10, 5, 120, 30);
    sure.tag = button.tag+1000;
    [sure setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
    [headerView addSubview:sure];
    
    UIButton *cancel = [UIButton buttonWithTip:@"取消" target:self selector:@selector(cancelSelectDate:)];
    cancel.frame = CGRectMake(10, sure.vtop, 120, 30);
    [cancel setTitleColor:[UIColor blackColor] forState:UIControlStateNormal];
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
        _publicModel.limitime = dateModelStr;
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

#pragma mark - UIPickerView DataSource
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView
{
    return 3;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component
{
    if (component == PROVINCE_COMPONENT) {
        return [province count];
    }
    else if (component == CITY_COMPONENT) {
        return [city count];
    }
    else {
        return [district count];
    }
}


#pragma mark- Picker Delegate Methods

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component
{
    if (component == PROVINCE_COMPONENT) {
        return [province objectAtIndex: row];
    }
    else if (component == CITY_COMPONENT) {
        return [city objectAtIndex: row];
    }
    else {
        return [district objectAtIndex: row];
    }
}


- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component
{
    if (component == PROVINCE_COMPONENT) {
        selectedProvince = [province objectAtIndex: row];
        NSDictionary *tmp = [NSDictionary dictionaryWithDictionary: [areaDic objectForKey: [NSString stringWithFormat:@"%ld", (long)row]]];
        NSDictionary *dic = [NSDictionary dictionaryWithDictionary: [tmp objectForKey: selectedProvince]];
        NSArray *cityArray = [dic allKeys];
        NSArray *sortedArray = [cityArray sortedArrayUsingComparator: ^(id obj1, id obj2) {
            
            if ([obj1 integerValue] > [obj2 integerValue]) {
                return (NSComparisonResult)NSOrderedDescending;//递减
            }
            
            if ([obj1 integerValue] < [obj2 integerValue]) {
                return (NSComparisonResult)NSOrderedAscending;//上升
            }
            return (NSComparisonResult)NSOrderedSame;
        }];
        
        NSMutableArray *array = [[NSMutableArray alloc] init];
        for (int i=0; i<[sortedArray count]; i++) {
            NSString *index = [sortedArray objectAtIndex:i];
            NSArray *temp = [[dic objectForKey: index] allKeys];
            [array addObject: [temp objectAtIndex:0]];
        }
        

        city = [[NSArray alloc] initWithArray: array];


        NSDictionary *cityDic = [dic objectForKey: [sortedArray objectAtIndex: 0]];
        district = [[NSArray alloc] initWithArray: [cityDic objectForKey: [city objectAtIndex: 0]]];
        [picker selectRow: 0 inComponent: CITY_COMPONENT animated: YES];
        [picker selectRow: 0 inComponent: DISTRICT_COMPONENT animated: YES];
        [picker reloadComponent: CITY_COMPONENT];
        [picker reloadComponent: DISTRICT_COMPONENT];
        
    }
    else if (component == CITY_COMPONENT) {
        NSString *provinceIndex = [NSString stringWithFormat: @"%lu", (unsigned long)[province indexOfObject: selectedProvince]];
        NSDictionary *tmp = [NSDictionary dictionaryWithDictionary: [areaDic objectForKey: provinceIndex]];
        NSDictionary *dic = [NSDictionary dictionaryWithDictionary: [tmp objectForKey: selectedProvince]];
        NSArray *dicKeyArray = [dic allKeys];
        NSArray *sortedArray = [dicKeyArray sortedArrayUsingComparator: ^(id obj1, id obj2) {
            
            if ([obj1 integerValue] > [obj2 integerValue]) {
                return (NSComparisonResult)NSOrderedDescending;
            }
            
            if ([obj1 integerValue] < [obj2 integerValue]) {
                return (NSComparisonResult)NSOrderedAscending;
            }
            return (NSComparisonResult)NSOrderedSame;
        }];
        
        NSDictionary *cityDic = [NSDictionary dictionaryWithDictionary: [dic objectForKey: [sortedArray objectAtIndex: row]]];
        NSArray *cityKeyArray = [cityDic allKeys];
        
        district = [[NSArray alloc] initWithArray: [cityDic objectForKey: [cityKeyArray objectAtIndex:0]]];
        [picker selectRow: 0 inComponent: DISTRICT_COMPONENT animated: YES];
        [picker reloadComponent: DISTRICT_COMPONENT];
    }
    
}


- (CGFloat)pickerView:(UIPickerView *)pickerView widthForComponent:(NSInteger)component
{
    if (component == PROVINCE_COMPONENT) {
        return 80;
    }
    else if (component == CITY_COMPONENT) {
        return 100;
    }
    else {
        return 115;
    }
}

- (UIView *)pickerView:(UIPickerView *)pickerView viewForRow:(NSInteger)row forComponent:(NSInteger)component reusingView:(UIView *)view
{
    UILabel *myView = nil;
    UIFont *font = [UIFont systemFontOfSize:15.f];
    
    if (component == PROVINCE_COMPONENT) {
        myView = [[UILabel alloc] initWithFrame:CGRectMake(0.0, 0.0, 78, 30)];
        myView.textAlignment = NSTextAlignmentCenter;
        myView.text = [province objectAtIndex:row];
        myView.font = font;
        myView.backgroundColor = [UIColor clearColor];
    }
    else if (component == CITY_COMPONENT) {
        myView = [[UILabel alloc] initWithFrame:CGRectMake(0.0, 0.0, 95, 30)];
        myView.textAlignment = NSTextAlignmentCenter;
        myView.text = [city objectAtIndex:row];
        myView.font = font;
        myView.backgroundColor = [UIColor clearColor];
    }
    else {
        myView = [[UILabel alloc] initWithFrame:CGRectMake(0.0, 0.0, 110, 30)];
        myView.textAlignment = NSTextAlignmentCenter;
        myView.text = [district objectAtIndex:row];
        myView.font = font;
        myView.backgroundColor = [UIColor clearColor];
    }
    
    return myView;
}


@end

