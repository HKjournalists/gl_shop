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
#import "NVDate.h"
#import "UIImageView+WebCache.h"
#import "IBActionSheet.h"

static NSString *commonStr = @"选择";
static NSInteger cellEdgeInsetes = 15;

@interface AddressViewController () <UITableViewDataSource,UITableViewDelegate,IBActionSheetDelegate,UITextFieldDelegate,UITextViewDelegate,PlaceDidSelect>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) UIDatePicker *datePicker;
@property (nonatomic, strong) IndicateSubView *listView; // 下拉选择单位
@property (nonatomic, strong) HJActionSheet *hjSheet;
@property (nonatomic, strong) PlaceSelect *place;

@property (nonatomic, strong) WTReTextField *sellAmountTextField;
@property (nonatomic, strong) WTReTextField *unitPriceTextField;

/**
 *@brief 为IBActionSheet记录选择的索引，默认为0
 */
@property (nonatomic, assign) NSInteger markIndex;

@end

@implementation AddressViewController

- (void)viewDidLoad {
    [super viewDidLoad];

}

#pragma mark - Override
- (void)initDatas {
    self.title = @"发布信息";
    _markIndex = -1;
    if ([_publicModel.addresstype[DataValueKey] integerValue] == 1) { // 己方指定
        NSInteger addressRows = _publicModel.addressImgModels.count ? 6 : 5;
        _sections = @[@1,@2,@1,[NSNumber numberWithInteger:addressRows],@2,@1];
    }else {
        _sections = @[@1,@2,@1,@1,@2,@1];
    }
    
}

- (void)loadSubViews {
    
    PublicGuideView *guideView = [[PublicGuideView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 60) stepIndex:2];
    UIView *header = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 70)];
    [header addSubview:guideView];
    
    _place = [[PlaceSelect alloc] init];
    _place.delegate = self;
    
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vheight -= kTopBarHeight+guideView.vheight;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    _tableView.sectionHeaderHeight = 5;
    _tableView.sectionFooterHeight = 5;
    _tableView.tableHeaderView = header;
    [self.view addSubview:_tableView];
    
    UIButton *nexBtn = [UIFactory createBtn:BlueButtonImageName bTitle:btntitle_next bframe:CGRectMake(10, self.tableView.vbottom+10, self.view.vwidth-20, 40)];
    [nexBtn addTarget:self action:@selector(nextOption:) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:nexBtn];
    
}

-(void)viewDidLayoutSubviews
{
    if ([self.tableView respondsToSelector:@selector(setSeparatorInset:)]) {
        [self.tableView setSeparatorInset:UIEdgeInsetsMake(0,cellEdgeInsetes,0,0)];
    }
    
    if ([self.tableView respondsToSelector:@selector(setLayoutMargins:)]) {
        [self.tableView setLayoutMargins:UIEdgeInsetsMake(0,cellEdgeInsetes,0,0)];
    }
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
    cell.imageView.image = [UIImage imageNamed:RedStartImageName];
    cell.textLabel.textColor = C_GRAY;
    cell.textLabel.font = [UIFont systemFontOfSize:FONT_16];
    cell.detailTextLabel.textColor = C_BLACK;
    cell.detailTextLabel.font = [UIFont systemFontOfSize:FONT_16];
    
    if (indexPath.section == 0) {
        cell.textLabel.text = [_publicModel.type integerValue] == 1 ? @"购买量" : @"销售量";

        [cell addSubview:self.listView];
        
        _sellAmountTextField = [[WTReTextField alloc] init];
        _sellAmountTextField.placeholder = @"填写";
        _sellAmountTextField.delegate = self;
        _sellAmountTextField.frame = CGRectMake(_listView.vright, 22-15, 130, 30);
        _sellAmountTextField.textAlignment = NSTextAlignmentRight;
        _sellAmountTextField.keyboardType = UIKeyboardTypeDecimalPad;
        _sellAmountTextField.delegate = self;
        _sellAmountTextField.pattern = @"^[0-9]+(.[0-9]{1,2})?$";
        _sellAmountTextField.text = [_publicModel.totalnum stringValue];
        [cell.contentView addSubview:_sellAmountTextField];
    }//-----------------------------------------------------------------------------------------------------------
    
    else if (indexPath.section == 1) {

        if (indexPath.row == 0) {
            cell.textLabel.text = @"交易日期范围";
            NSString *startStr = _publicModel.starttime;
            NSString *targetStr = startStr ? [startStr substringToIndex:10] : glProduct_select;
            UIButton *startBtn = [UIButton buttonWithTip:targetStr target:self selector:@selector(selectTime:)];
            startBtn.tag = 100*indexPath.section+1;
            startBtn.frame = CGRectMake(147, 6, 80, 30);
            [startBtn setTitleColor:C_BLACK forState:UIControlStateNormal];
            startBtn.titleLabel.font = [UIFont systemFontOfSize:13.f];
            [cell addSubview:startBtn];
            
            UILabel *contact = [UILabel labelWithTitle:@"至"];
            contact.frame = CGRectMake(startBtn.vright, 0, 15, cell.vheight);
            [cell addSubview:contact];
            
            NSString *endStr = _publicModel.endtime;
            NSString *endTStr = endStr ? [endStr substringToIndex:10] : glProduct_select;
            UIButton *endBtn = [UIButton buttonWithTip:endTStr target:self selector:@selector(selectTime:)];
            endBtn.tag = 100*indexPath.section+2;
            [endBtn setTitleColor:C_BLACK forState:UIControlStateNormal];
            endBtn.frame = CGRectMake(contact.vright+2, startBtn.vtop, startBtn.vwidth, startBtn.vheight);
            endBtn.titleLabel.font = [UIFont systemFontOfSize:13.f];
            [cell addSubview:endBtn];
        }else {
            cell.textLabel.text = @"交易地域";
            cell.detailTextLabel.font = [UIFont systemFontOfSize:FONT_16];
            cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
            cell.detailTextLabel.text = _publicModel.areaFullName;
        }
        
    }//-----------------------------------------------------------------------------------------------------------
    
    else if (indexPath.section == 2) {
        cell.textLabel.text = @"到港单价（单位:元)";
        _unitPriceTextField = [[WTReTextField alloc] init];
        _unitPriceTextField.placeholder = @"填写";
        _unitPriceTextField.delegate = self;
        _unitPriceTextField.pattern = @"^[0-9]+(.[0-9]{1,2})?$";
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
        if (indexPath.row == 0 || indexPath.row == 1) {
            cell.selectionStyle = UITableViewCellSelectionStyleDefault;
        }else {
            cell.selectionStyle = UITableViewCellEditingStyleNone;
        }
        if (indexPath.row == 0) {
            cell.textLabel.text = @"交货地址指定方式";
            if (_publicModel.addresstype.count == 0) {
                cell.detailTextLabel.text = glProduct_select;
            }else {
                cell.detailTextLabel.text = [_publicModel.addresstype[DataValueKey] integerValue] == 1 ? @"己方指定" : @"对方指定";
                _markIndex = [_publicModel.addresstype[DataValueKey] integerValue] == 1 ? 0 : 1;
            }
            cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        }else if (indexPath.row == 1) {
            cell.textLabel.text = @"详细交易地址";
            cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
        }else if (indexPath.row == 2) {
            cell.imageView.image = nil;
            cell.textLabel.textColor = [UIColor blackColor];
            cell.textLabel.text = FommatString(_publicModel.addrAreaFullName, _publicModel.address);
        }else if (indexPath.row == 3) {
            cell.imageView.image = nil;
            if (_publicModel.addressImgModels.count) {
                int j = 0;
                for (AddressImgModel *model in _publicModel.addressImgModels) {
                    UIImageView *imgView = [[UIImageView alloc] initWithFrame:CGRectMake(15+j*(260/3+15), 10, 260/3, 80)];
                    [imgView sd_setImageWithURL:[NSURL URLWithString:model.thumbnailSmall] placeholderImage:nil];
                    j++;
                    [cell addSubview:imgView];
                }
            }else {
                cell.textLabel.text = cell_address_depth;
                cell.detailTextLabel.text = [_publicModel.deep stringValue];
            }
            
        }else if (indexPath.row == 4) {
            cell.imageView.image = nil;
            if (_publicModel.addressImgModels.count) {
                cell.textLabel.text = cell_address_depth;
                cell.detailTextLabel.text = [_publicModel.deep stringValue];
            }else {
                cell.textLabel.text = cell_boat_tun;
                cell.detailTextLabel.text = [_publicModel.shippington stringValue];
            }
            
        }else if (indexPath.row == 5) {
            cell.imageView.image = nil;
            cell.textLabel.text = cell_boat_tun;
            cell.detailTextLabel.text = [_publicModel.shippington stringValue];
        }
    }//-----------------------------------------------------------------------------------------------------------

    else if (indexPath.section == 4 ) {
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        if (indexPath.row == 0) {
            cell.imageView.image = nil;
            cell.textLabel.text = [_publicModel.type integerValue] == 1 ? @"购买备注" : @"销售备注";
        }else {
            REPlaceholderTextView *textView = [[REPlaceholderTextView alloc] initWithFrame:CGRectMake(15, 5,cell.vwidth-20, 80)];
            textView.placeholder = [_publicModel.type integerValue] == 1 ? @"请输入购买备注信息" : @"请输入销售备注信息";
            textView.returnKeyType = UIReturnKeyDone;
            textView.delegate = self;
            textView.layer.borderWidth = 1;
            textView.font = UFONT_16;
            textView.text = _publicModel.remark;
            textView.layer.borderColor = [UIColor lightGrayColor].CGColor;;
            [cell addSubview:textView];
        }

    }//-----------------------------------------------------------------------------------------------------------

    else if (indexPath.section == 5) {
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        cell.imageView.image = nil;
        [cell addSubview:[self tipView]];
    }
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 3 && _publicModel.addressImgModels.count) {
        if (indexPath.row == 3) {
            return 100;
        }
    }
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

-(void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if ([cell respondsToSelector:@selector(setSeparatorInset:)]) {
        [cell setSeparatorInset:UIEdgeInsetsMake(0,cellEdgeInsetes,0,0)];
    }

    if ([cell respondsToSelector:@selector(setLayoutMargins:)]) {
        [cell setLayoutMargins:UIEdgeInsetsMake(0,cellEdgeInsetes,0,0)];
    }
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
                IBActionSheet *sheet = [[IBActionSheet alloc] initWithTitle:@"选择指定方式" delegate:self cancelButtonTitle:globe_cancel_str destructiveButtonTitle:nil otherButtonTitles:@"己方指定",@"对方指定", nil];
                sheet.tag = indexPath.section*100+5;
                sheet.markIndex = _markIndex;
                [sheet showInView:self.view];
            }else if (indexPath.row == 1){
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
- (void)actionSheet:(IBActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (fmodf(actionSheet.tag, 100) == 5) {
        if (buttonIndex != actionSheet.cancelButtonIndex) {
            self.markIndex = buttonIndex;
        }
        
        if (buttonIndex == 0) {
            if ([_publicModel.type integerValue] == BussinessTypeSell) {
                _publicModel.addresstype = [NSDictionary dictionaryWithObjectsAndKeys:@1,@"val",@"卖家",@"text", nil];
            }else {
                _publicModel.addresstype = [NSDictionary dictionaryWithObjectsAndKeys:@1,@"val",@"买家",@"text", nil];
            }
            _sections = @[@1,@2,@1,@2,@2,@1];
        }else if (buttonIndex == 1) {
            if ([_publicModel.type integerValue] == BussinessTypeSell) {
                _publicModel.addresstype = [NSDictionary dictionaryWithObjectsAndKeys:@2,@"val",@"卖家",@"text", nil];
            }else {
                _publicModel.addresstype = [NSDictionary dictionaryWithObjectsAndKeys:@2,@"val",@"买家",@"text", nil];
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

- (float)valueForSERVICE_PERCENT {
    for (NSDictionary *dic in [SynacObject sysParams]) {
        if ([dic[@"pname"] isEqualToString:@"SERVICE_PERCENT"]) {
            NSNumber *num = dic[@"pvalue"];
            return [num floatValue];
        }
    }
    return 0;
}

/**
 *@brief 创建警告图
 */
- (UIView *)tipView {
    float height = [_publicModel.type integerValue] == 2 ? 100 : 60;
    
    UIView *tipView = [UIFactory createPromptViewframe:CGRectMake(10, 10, self.view.vwidth-20, height) tipTitle:nil];
    NSString *norStr = @"1、*号为发布信息必填项；";
    NSString *beattStr = @"*";
    UILabel *label1 = [UILabel labelWithTitle:norStr];
    NSDictionary *attDic = [NSDictionary dictionaryWithObjectsAndKeys:[UIColor orangeColor],NSForegroundColorAttributeName,nil];
    label1.attributedText = [Utilits attString:norStr attTargetStr:beattStr attrubites:attDic];
    label1.font = [UIFont systemFontOfSize:14.f];
    label1.frame = CGRectMake(10, 30, 280, 20);
    [tipView addSubview:label1];
    
    if ([_publicModel.type integerValue] == BussinessTypeSell) {
        NSString *beattStr = @"3.00%";
        beattStr = [NSString stringWithFormat:@"%.2f%%",[self valueForSERVICE_PERCENT]];
        NSString *norStr = [NSString stringWithFormat:@"2、若该信息产生交易且成功，平台将收取货款实际金额的%@作为平台交易服务费。",beattStr];
        NSAttributedString *attStr = [Utilits attString:norStr attTargetStr:beattStr attrubites:[NSDictionary dictionaryWithObject:[UIColor orangeColor] forKey:NSForegroundColorAttributeName]];
        
        UILabel *label2 = [UILabel labelWithTitle:norStr];
        label2.attributedText = attStr;
        label2.font = label1.font;
        label2.frame = CGRectMake(10, label1.vbottom, 280, 40);
        label2.numberOfLines = 2;
        [tipView addSubview:label2];
    }else {
        NSString *norStr = @"*号为发布信息必填项。";
        NSString *beattStr = @"*";
        NSDictionary *attDic = [NSDictionary dictionaryWithObjectsAndKeys:[UIColor orangeColor],NSForegroundColorAttributeName,nil];
        label1.attributedText = [Utilits attString:norStr attTargetStr:beattStr attrubites:attDic];
        
    }
    
    return tipView;
}



#pragma mark - UIActions
/**
 *@brief 跳转到发布页面
 */
- (void)nextOption:(UIButton *)button {
//    [self.navigationController popToRootViewControllerAnimated:NO];
//    MypurseViewController *vc = [[MypurseViewController alloc] init];
//    [[PushInstance sharedInstance] pushViewContrller:vc];
//    return;
    
    if (![self authPublicData]) {
        return;
    }
    
    PreViewPublicViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"PreViewPublicViewControllerId"];
    vc.publicModel = _publicModel;
    [self.navigationController pushViewController:vc animated:YES];
}

/**
 *@brief 验证信息
 */
- (BOOL)authPublicData {
    if (!_publicModel.totalnum) {
        NSString *tip = _publicModel.type.integerValue == BussinessTypeBuy ? @"请填写购买量" : @"请填写销售量";
        [self showTip:tip];
        return NO;
    }
    
    if ([_publicModel.totalnum integerValue] == 0) {
        [self showTip:@"货物总量不能为0"];
        return NO;
    }
    
    if (!_publicModel.starttime || !_publicModel.endtime) {
        [self showTip:@"请选择交易时间范围"];
        return NO;
    }
    
    // 交易开始时间不能小于当前时间  交易结束时间不能小于开始时间
    NSDate *starteDate = _publicModel.starttime.length > 10 ? [Utilits dateFromFomate:_publicModel.starttime formate:kTimeDetail_Format] : [Utilits dateFromFomate:[NSString stringWithFormat:@"%@ 00:00:00",_publicModel.starttime] formate:kTimeDetail_Format];
    NSDate * endDate =  _publicModel.endtime.length > 10 ? [Utilits dateFromFomate:_publicModel.endtime formate:kTimeDetail_Format] : [Utilits dateFromFomate:[NSString stringWithFormat:@"%@ 00:00:00",_publicModel.endtime] formate:kTimeDetail_Format];
    NSString *nowDateStr = [[Utilits stringFromFomate:[NSDate date] formate:kTimeDetail_Format] substringToIndex:10];
    NSTimeInterval t = [starteDate timeIntervalSinceDate:[Utilits dateFromFomate:[NSString stringWithFormat:@"%@ 00:00:00",nowDateStr] formate:kTimeDetail_Format]];
    if (t < 0) {
        [self showTip:@"交易开始时间不能小于当前时间"];
        return NO;
    }
    
    if ([endDate timeIntervalSinceDate:starteDate] < 0) {
        [self showTip:@"交易结束时间不能小于开始时间"];
        return NO;
    }
    
    if (!_publicModel.areaFullName) {
        [self showTip:@"请选择交易地域"];
        return NO;
    }
    
    if (!_publicModel.price) {
        HUD(@"请填写到港单价");
        return NO;
    }
    
    if ([_publicModel.price integerValue] == 0) {
        [self showTip:@"货物单价不能为0"];
        return NO;
    }
    
    if (!_publicModel.addresstype) {
        HUD(@"请选择交易地址指定方式");
        return NO;
    }
    
    if ([_publicModel.addresstype[DataValueKey] integerValue] == 1) {
        if (!_publicModel.addrAreaFullName) {
            [self showTip:@"请选择详细交易地址"];
            return NO;
        }
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
    
    _datePicker = [[UIDatePicker alloc] initWithFrame:CGRectMake(0, tilteLab.vbottom, bgView.vwidth, 0)];
    _datePicker.datePickerMode = UIDatePickerModeDate;
    _datePicker.minimumDate = [NSDate date];
    NSLocale *locale = [[NSLocale alloc] initWithLocaleIdentifier:@"zh_CN"];//设置为中
    _datePicker.locale = locale;
    _datePicker.date = [NSDate date];
    if ( button.tag == 102) { // 交易结束时间，默认推后一周
        NVDate *nvdate = [[NVDate alloc] initUsingDate:[NSDate date]];
        NSDate *date = [nvdate nextWeek].date;
        _datePicker.date = date;
    }
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
    NSString *dateModelStr = [Utilits stringFromFomate:_datePicker.date formate:kTimeFormart];
    
    if (fmodf(tag, 100) == 1) {
        _publicModel.starttime = dateModelStr;
    }else {
        _publicModel.endtime = dateModelStr;
    }
    [tBtn setTitle:dateStr forState:UIControlStateNormal];
    
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
    _publicModel.area = areaCode;
    NSIndexPath *indexPath = [NSIndexPath indexPathForRow:1 inSection:1];
    [_tableView reloadRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationAutomatic];
}

@end

