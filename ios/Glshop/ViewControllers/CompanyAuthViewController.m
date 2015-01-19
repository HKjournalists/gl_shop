//
//  CompanyAuthViewController.m
//  Glshop
//
//  Created by River on 14-11-12.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "CompanyAuthViewController.h"
#import "REPlaceholderTextView.h"
#import "ChoseTypeViewController.h"
#import "DetailStandardViewController.h"
#import "PublicGuideView.h"
#import "AddressViewController.h"

@interface CompanyAuthViewController () <UIActionSheetDelegate,UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate,UITextViewDelegate>

@property (nonatomic, weak) SynacInstance *synac;

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) UITextField *productColorField;
@property (nonatomic, strong) UITextField *productPlaceField;

@end


@implementation CompanyAuthViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}

#pragma mark - Override
- (void)initDatas {
    self.title = @"货物信息";
    
    _synac = [SynacInstance sharedInstance];
    
    if (_publicInfoType == public_New) { // 如果不是编辑发布信息，需要初始化PublicInfoModel
        _publicModel = [[PublicInfoModel alloc] init];
        _publicModel.type = IntToNSNumber(0); // 不是买也不是卖
        GoodsModel *model = _synac.productTopModels[0];
        _publicModel.pcode = model.goodsVal;
        _publicModel.ptype = UnKnow;
        _publicModel.pid = UnKnow;
        _publicModel.unit = @{DataValueKey:MathUnitTon,@"text":@"吨"}; // 设置默认单位
        _publicModel.photoUploadView = [[PhotoUploadView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 100)];
        _sectionsArray = @[@1,@4,@2,@2,@1];
    }
}

- (void)loadSubViews {
    // 引导图
    PublicGuideView *guideView = [[PublicGuideView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 60) stepIndex:1];
    [self.view addSubview:guideView];
    
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

#pragma mark - Setter
- (void)setPublicModel:(PublicInfoModel *)publicModel {
    _publicModel = publicModel;
    _publicModel.photoUploadView = [[PhotoUploadView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 100)];
    if (_publicModel.productImgList.count) { // 如果有实物图片
        NSMutableArray *temp = [NSMutableArray array];
        for (AddressImgModel *model in _publicModel.productImgList) {
            [temp addObject:model.thumbnailSmall];
        }
        _publicModel.photoUploadView.imageUrlArray = [NSArray arrayWithArray:temp];
    }
    if ([_publicModel.type integerValue] == BussinessTypeSell) { // 如果是发布出售信息
        _sectionsArray = @[@1,@4,@2,@2,@2,@1];
    }else {
        _sectionsArray = @[@1,@4,@2,@2,@1];
    }
    
    if ([_publicModel.pcode  isEqual: TopProductSendPcode]) { // 如果商品大类是黄砂
        NSMutableArray *temp = [NSMutableArray arrayWithArray:_sectionsArray];
        [temp replaceObjectAtIndex:1 withObject:@4];
        _sectionsArray = [NSArray arrayWithArray:temp];
    }else {
        NSMutableArray *temp = [NSMutableArray arrayWithArray:_sectionsArray];
        [temp replaceObjectAtIndex:1 withObject:@3];
        _sectionsArray = [NSArray arrayWithArray:temp];
    }
}

#pragma mark - UITableView DataSource / Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return _sectionsArray.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    
    return [_sectionsArray[section] integerValue];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.textLabel.font = [UIFont systemFontOfSize:16.f];
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    switch (indexPath.section) {
        case 0:
        {
            cell.textLabel.text = @"发布信息类型";
            NSString *value;
            if ([_publicModel.type intValue] == 0) {
                value = section_Value;
            }else if ([_publicModel.type intValue] == 1) {
                value = buyInfo;
            }else {
                value = sellInfo;
            }
            cell.detailTextLabel.text = value;
        }
            break;
        case 1:
        {
            cell.imageView.image = [UIImage imageNamed:@"attestation_icon_"];
            GoodsModel *model = [_synac goodsModelForPcode:_publicModel.pcode];
            if (indexPath.row == 0) {
                cell.textLabel.text = section1_key;
                cell.detailTextLabel.text = model.goodsName;
            }

            if ([model.goodsVal isEqualToString:TopProductSendPcode]) { // 黄砂
                GoodsModel *goods = [_synac goodsModelForPtype:_publicModel.ptype];
                switch (indexPath.row) {
                    case 1:
                    {   // 黄砂二级子类
                        cell.textLabel.text = section2_key;
                        NSString *value = goods ? goods.goodsName : section_Value;
                        cell.detailTextLabel.text = value;
                    }
                        break;
                    case 2:
                    {   // 黄砂三级子类规格
                        cell.textLabel.text = section3_key;
                        GoodChildModel *model = [_synac goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid];
                        if (![_publicModel.pid isEqualToString:UnKnow] && model) {
                           cell.detailTextLabel.text = [NSString stringWithFormat:@"%@(%@-%@)mm",model.sizeModel.name,model.sizeModel.minv,model.sizeModel.maxv];
                        }else {
                            cell.detailTextLabel.text = section_Value;
                        }

                    }
                        break;
                    case 3:
                    {   // 黄砂三级子类详细规格
                        cell.textLabel.text = section4_key;
                        cell.imageView.image = nil;
                        cell.indentationLevel = 3;
                        cell.detailTextLabel.text = @"设置";
                    }
                        break;
                    default:
                        break;
                }
            }else {
                if (indexPath.row == 1) {
                    cell.textLabel.text = section3_key;
                    GoodChildModel *model = [_synac goodsChildStone:_publicModel.pid];
                    NSString *value = model ? model.goodChildPname : section_Value;
                    cell.detailTextLabel.text = value;
                }else if (indexPath.row == 2) {
                    cell.imageView.image = nil;
                    cell.indentationLevel = 3;
                    cell.textLabel.text = section4_key;
                    cell.detailTextLabel.text = @"设置";
                }
               
            }
        }
            break;
        case 2:
        {
            cell.imageView.image = [UIImage imageNamed:@"attestation_icon_"];
            cell.accessoryType = UITableViewCellAccessoryNone;
            if (indexPath.row == 0) {
                cell.textLabel.text = @"货物颜色";
                
                _productColorField = [UITextField textFieldWithPlaceHodler:@"填写" withDelegate:self];
                _productColorField.text = _publicModel.pcolor;
                _productColorField.frame = CGRectMake(100, 22-15, 210, 30);
                _productColorField.textAlignment = NSTextAlignmentRight;
                [cell.contentView addSubview:_productColorField];
            }else if (indexPath.row == 1) {
                cell.textLabel.text = @"货物产地";
                _productPlaceField = [UITextField textFieldWithPlaceHodler:@"填写" withDelegate:self];
                _productPlaceField.text = _publicModel.paddress ;
                _productPlaceField.frame = CGRectMake(100, 22-15, 210, 30);
                _productPlaceField.textAlignment = NSTextAlignmentRight;
                [cell.contentView addSubview:_productPlaceField];

            }

        }
            break;
        case 3:
        {       cell.accessoryType = UITableViewCellAccessoryNone;
                if (indexPath.row == 0) {
                    cell.imageView.image = nil;
                    cell.textLabel.text = @"货物备注";
                }else {
                    REPlaceholderTextView *textView = [[REPlaceholderTextView alloc] initWithFrame:CGRectMake(15, 5,cell.vwidth-20, 80)];
                    textView.placeholder = @"请输入货物备注信息";
                    textView.returnKeyType = UIReturnKeyDone;
                    textView.delegate = self;
                    textView.layer.borderWidth = 1;
                    textView.text = _publicModel.premark;
                    textView.layer.borderColor = [UIColor lightGrayColor].CGColor;;
                    [cell addSubview:textView];
                }
        }
            break;
        case 4:
        {
            if ([_publicModel.type integerValue] == 2) {
                cell.selectionStyle = UITableViewCellSelectionStyleNone;
                cell.accessoryType = UITableViewCellAccessoryNone;
                cell.imageView.image = nil;
                if (indexPath.row == 0) {
                    cell.textLabel.text = @"实物信息";
                    cell.accessoryType = UITableViewCellAccessoryNone;
                }else if (indexPath.row == 1){

                    [cell addSubview:_publicModel.photoUploadView];
                }
            }else {
                [cell addSubview:[self tipView]];
            }
        }
            break;
        case 5:
        {
            [cell addSubview:[self tipView]];
        }
        default:
            break;
    }
    
    return cell;
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

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 3) {
        if (indexPath.row == 1) {
            return 90;
        }
    }
    if (indexPath.section == 4) {
        if ([_publicModel.type integerValue] == 2) {
            if (indexPath.row == 1) {
                return 100;
            }else {
                return 44;
            }
        }
       return 80;
    }
    if (indexPath.section == 5) {
        if ([_publicModel.type integerValue] == 2) {
            return 120;
        }
        return 90;
    }
    return 44;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    
    if (indexPath.section == 0) {
        UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:@"选择发布信息类型"
                                                           delegate:self
                                                  cancelButtonTitle:@"取消"
                                             destructiveButtonTitle:nil
                                                  otherButtonTitles:sellInfo,buyInfo, nil];
        sheet.tag = 100+indexPath.section;
        [sheet showInView:self.view];
        
    }else if (indexPath.section == 1 ) {
        if (indexPath.row == 0) {
            UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:@"选择货物信息类型"
                                                               delegate:self cancelButtonTitle:@"取消"
                                                 destructiveButtonTitle:nil
                                                      otherButtonTitles:nil, nil];
            for (NSString *name in [[SynacInstance sharedInstance] productTopNames]) {
                [sheet addButtonWithTitle:name];
            }
            sheet.tag = 200+indexPath.row;
            [sheet showInView:self.view];
        }
        
        if ([cell.textLabel.text isEqualToString:section2_key]) { // 商品分类（黄砂分类）
            [self sheetForSelectSendsType:sendsSubType];
        }else if ([cell.textLabel.text isEqualToString:section3_key]) {
            if (indexPath.row == 1) { // 石子规格
                [self sheetForSelectSendsType:stoneType];
                
            }else { // 黄砂规格
                if ([_publicModel.ptype isEqualToString:UnKnow]) {
                    HUD(@"请先选择分类");
                    return;
                }
                [self sheetForSelectSendsType:sendsGroundsonType];
            }
            
        }else if ([cell.textLabel.text isEqualToString:section4_key]) { // 详细规格
            if ([_publicModel.pid isEqualToString:UnKnow]) {
                HUD(@"请先选择规格");
                return;
            }
            DetailStandardViewController *vc = [[DetailStandardViewController alloc] init];
            vc.publicModel = _publicModel;
            vc.type = self.publicInfoType;
            UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:vc];
            [self presentViewController:nav animated:YES completion:nil];
        }

    }
}

#pragma mark - Private
/**
 *@brief 跳到产品子类型页面选择相应的产品类型
 */
- (void)sheetForSelectSendsType:(ProductType)type {

    ChoseTypeViewController *vc = [[ChoseTypeViewController alloc] init];
    if (type == sendsGroundsonType) {
        vc.ptype = _publicModel.ptype;
    }
    vc.productType = type;
    vc.publicModel = self.publicModel;
    
    UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:vc];
    [self presentViewController:nav animated:YES completion:nil];
    
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


/**
 *@brief 重置发布model
 */
- (void)resetPublicModel {
    _publicModel.ptype = UnKnow;
    _publicModel.pid = UnKnow;
    
}

#pragma mark - UIActionSheet Delegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    switch (actionSheet.tag) {
        case 100: // 选择发布信息类型
        {
            NSIndexPath *apath = [NSIndexPath indexPathForRow:0 inSection:0];
            UITableViewCell *cell = [_tableView cellForRowAtIndexPath:apath];
            if (buttonIndex == 0) {
                cell.detailTextLabel.text = sellInfo;
                _publicModel.type = IntToNSNumber(2);
                if (_sectionsArray.count == 5) {
                    NSMutableArray *temp = [NSMutableArray arrayWithArray:_sectionsArray];
                    [temp insertObject:@2 atIndex:3];
                    _sectionsArray = [NSArray arrayWithArray:temp];
                }
 
            }else if (buttonIndex == 1) {
                cell.detailTextLabel.text = buyInfo;
                _publicModel.type = IntToNSNumber(1);
                if (_sectionsArray.count == 6) {
                    NSMutableArray *temp = [NSMutableArray arrayWithArray:_sectionsArray];
                    [temp removeObjectAtIndex:4];
                    _sectionsArray = [NSArray arrayWithArray:temp];
                }
            }
            [_tableView reloadData];
        }
            break;
        case 200: // 选择货物信息
        {
            NSIndexPath *apath = [NSIndexPath indexPathForRow:0 inSection:1];
            UITableViewCell *cell = [_tableView cellForRowAtIndexPath:apath];
            if (buttonIndex) {
                cell.detailTextLabel.text = [actionSheet buttonTitleAtIndex:buttonIndex];
                [self resetPublicModel];
            }
            if (buttonIndex == 1) {
                if ([_sectionsArray[1] integerValue] == 4) {
                    return;
                }
                _publicModel.pcode = TopProductSendPcode;
                NSIndexPath *path = [NSIndexPath indexPathForRow:1 inSection:1];
                NSMutableArray *temp = [NSMutableArray arrayWithArray:_sectionsArray];
                [temp replaceObjectAtIndex:1 withObject:@4];
                _sectionsArray = [NSArray arrayWithArray:temp];
                
                [_tableView beginUpdates];
                [_tableView insertRowsAtIndexPaths:@[path] withRowAnimation:UITableViewRowAnimationAutomatic];
                [_tableView endUpdates];
                [_tableView reloadData];
                
            }else if (buttonIndex == 2) {
                if ([_sectionsArray[1] integerValue] == 3) {
                    return;
                }
                _publicModel.pcode = TopProductStonePcode;
                NSIndexPath *path = [NSIndexPath indexPathForRow:1 inSection:1];
                NSMutableArray *temp = [NSMutableArray arrayWithArray:_sectionsArray];
                [temp replaceObjectAtIndex:1 withObject:@3];
                _sectionsArray = [NSArray arrayWithArray:temp];
                [_tableView beginUpdates];
                [_tableView deleteRowsAtIndexPaths:@[path] withRowAnimation:UITableViewRowAnimationAutomatic];
                [_tableView endUpdates];
                [_tableView reloadData];
            }
        }
            break;
        case 201:
        {
            NSIndexPath *apath = [NSIndexPath indexPathForRow:1 inSection:1];
            UITableViewCell *cell = [_tableView cellForRowAtIndexPath:apath];
            if (buttonIndex != 0) {
                cell.detailTextLabel.text = [actionSheet buttonTitleAtIndex:buttonIndex];
            }
        }
            break;
            
        default:
            break;
    }
}

#pragma mark - UIActions
- (void)nextOption:(UIButton *)btn {
//    if (![self authPublicData]) {
//        return;
//    }

    AddressViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"AddressViewControllerId"];
    vc.publicModel = _publicModel;
    [self.navigationController pushViewController:vc animated:YES];
    
}

/**
 *@brief 校验填写信息
 */
- (BOOL)authPublicData {
    
    // 是否选择了发布信息类型
    if ([_publicModel.type integerValue] == 0) {
        HUD(@"请选择发布信息类型");
        return NO;
    }
    
    if ([_publicModel.pcode isEqualToString:TopProductSendPcode]) {
        GoodsModel *goods = [_synac goodsModelForPtype:_publicModel.ptype];
        if (!goods) {
            HUD(@"请选择分类");
            return NO;
        }
        
        GoodChildModel *model = [_synac goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid];
        if ([_publicModel.pid isEqualToString:UnKnow] || !model) {
            HUD(@"请选择规格");
            return NO;
        }
        
    }else {
        GoodChildModel *model = [_synac goodsChildStone:_publicModel.pid];
        if (!model) {
            HUD(@"请选择规格");
            return NO;
        }
    }
    
    if (!_publicModel.pcolor.length) {
        HUD(@"请填写货物颜色");
        return NO;
    }

    
    if (!_publicModel.paddress.length) {
        HUD(@"请填写货物产地");
        return NO;
    }
    
    return YES;
}

#pragma mark - UITextView Delegate
- (BOOL)textView:(UITextView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString *)text {
    if ([text isEqualToString:@"\n"]) {
//        _publicModel.premark = textView.text;
        [textView resignFirstResponder];
        return NO;
    }
    return YES;
}

- (void)textViewDidEndEditing:(UITextView *)textView {
    _publicModel.premark = textView.text;
}

#pragma mark - UITextField Delegate
- (void)textFieldDidEndEditing:(UITextField *)textField {
    if (textField == _productColorField) {
        _publicModel.pcolor = _productColorField.text;
    }else if (textField == _productPlaceField) {
        _publicModel.paddress = _productPlaceField.text;
    }
}

@end
