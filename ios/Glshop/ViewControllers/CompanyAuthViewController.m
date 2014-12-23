//
//  CompanyAuthViewController.m
//  Glshop
//
//  Created by River on 14-11-12.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "CompanyAuthViewController.h"
#import "ButtonWithTitleAndImage.h"
#import "REPlaceholderTextView.h"
#import "IQKeyboardManager.h"
#import "KGModal.h"
#import "JGActionSheet.h"
#import "ChoseTypeViewController.h"
#import "DetailStandardViewController.h"
#import "PublicGuideView.h"
#import "AddressViewController.h"
#import "NSString+DTPaths.h"

@interface CompanyAuthViewController () <UIActionSheetDelegate,UITableViewDataSource,UITableViewDelegate,UIPickerViewDataSource,UIPickerViewDelegate,UITextFieldDelegate,UINavigationControllerDelegate,UIImagePickerControllerDelegate,UITextViewDelegate>

@property (nonatomic, weak) SynacInstance *synac;

@property (nonatomic, strong) UITableView *tableView;

@property (nonatomic, strong) UIPickerView *pickerView;
@property (nonatomic, strong) NSMutableArray *sendsGroundsonArray;
@property (nonatomic, strong) JGActionSheet *jgSheet;
@property (nonatomic, strong) UITextField *productColorField;
@property (nonatomic, strong) UITextField *productPlaceField;

@end


@implementation CompanyAuthViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
}

#pragma mark - Override
- (void)initDatas {
    _synac = [SynacInstance sharedInstance];
    
    _publicModel = [[PublicInfoModel alloc] init];
    _publicModel.type = IntToNSNumber(0); // 不是买也不是卖
    GoodsModel *model = _synac.productTopModels[0];
    _publicModel.pcode = model.goodsVal;
    _publicModel.ptype = UnKnow;
    _publicModel.pid = UnKnow;
    _publicModel.unit = MathUnitTon; // 设置默认单位
    _publicModel.entryImages = [NSMutableArray array];
    
    _sectionsArray = @[@1,@4,@2,@2,@1];
    
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
    
    UIButton *nexBtn = [UIButton buttonWithTip:@"下一步" target:self selector:@selector(nextOption:)];
    nexBtn.backgroundColor = CJBtnColor;
    nexBtn.layer.cornerRadius = 3.f;
    nexBtn.frame = CGRectMake(10, self.tableView.vbottom+5, self.view.vwidth-20, 40);
    [self.view addSubview:nexBtn];
    
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
                           cell.detailTextLabel.text = [NSString stringWithFormat:@"%@(%@-%@)",model.sizeModel.name,model.sizeModel.minv,model.sizeModel.maxv];
                        }else {
                            cell.detailTextLabel.text = section_Value;
                        }

                    }
                        break;
                    case 3:
                    {   // 黄砂三级子类详细规格
                        cell.textLabel.text = section4_key;
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
                    REPlaceholderTextView *textView = [[REPlaceholderTextView alloc] initWithFrame:CGRectMake(10, 5,cell.vwidth-20, 80)];
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
                    for (int i = 0; i < 3; i++) {
                        cell.selectionStyle = UITableViewCellSelectionStyleNone;
                        UIButton *imageBtn = [UIButton buttonWithTip:nil target:self selector:@selector(chosePhoto:)];
                        imageBtn.frame = CGRectMake(15+i*(260/3+15), 10, 260/3, 80);
                        imageBtn.tag = indexPath.section*100+i;
                        if (i == 0 && fmod(imageBtn.tag, indexPath.section*100) == 0) {
                            UIImage *image = _publicModel.image1 ? _publicModel.image1 : [UIImage imageNamed:@"address_photo"];
                            [imageBtn setImage:image forState:UIControlStateNormal];
                        }else if (i == 1 && fmod(imageBtn.tag, indexPath.section*100) == 1) {
                            UIImage *image = _publicModel.image2 ? _publicModel.image2 : [UIImage imageNamed:@"address_photo_add"];
                            [imageBtn setImage:image forState:UIControlStateNormal];
                        }else if (i == 2 && fmod(imageBtn.tag, indexPath.section*100) == 2){
                            UIImage *image = _publicModel.image3 ? _publicModel.image3 : [UIImage imageNamed:@"address_photo_add"];
                            [imageBtn setImage:image forState:UIControlStateNormal];
                        }
                        
                        [cell addSubview:imageBtn];
                    }
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
            if (![_publicModel.productCateory isEqualToString:section_Value]) { // 选择的是黄砂所属的详细规格
                NSRange range = [_publicModel.productStandra rangeOfString:@"("]; // 因为拼接了括号，此处是为了得到产品名如：中砂
                if (range.location != NSNotFound) {
                    NSString *str = [_publicModel.productStandra substringToIndex:range.location];
                    // 用产品名找到产品模型
                    GoodsModel *model = [_synac productNameMapGoodsModel:_publicModel.productCateory];
                    vc.ptype = model.goodsVal;
                    vc.productName = str;
                }
            }
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
                _publicModel.product = [actionSheet buttonTitleAtIndex:buttonIndex];
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
    
    if (actionSheet.tag == 5000) {
        NSUInteger sourceType = 0;
        if([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
            switch (buttonIndex) {
                case 0:
                    sourceType = UIImagePickerControllerSourceTypeCamera;
                    break;
                case 1:
                    sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
                    break;
                case 2:
                    return;
            }
        } else {
            if (buttonIndex == 1) {
                return;
            } else {
                sourceType = UIImagePickerControllerSourceTypeSavedPhotosAlbum;
            }
        }
        
        UIImagePickerController *imagePickerController = [[UIImagePickerController alloc] init];
        imagePickerController.delegate = self;
        imagePickerController.allowsEditing = YES;
        imagePickerController.sourceType = sourceType;
        [self presentViewController:imagePickerController animated:YES completion:nil];
        
    }

}

#pragma mark - UIActions
- (void)nextOption:(UIButton *)btn {
//    if ([_publicModel.publicType isEqualToString:section_Value]) {
//        [self showTip:@"请选择发布信息类型！"];
//        return;
//    }else if ([_publicModel.product isEqualToString:kTopProductSend] && [_publicModel.productCateory isEqualToString:section_Value]) {
//        [self showTip:@"请选择分类"];
//        return;
//    }else if ([_publicModel.productStandra isEqualToString:section_Value]) {
//        [self showTip:@"请选择货物规格"];
//        return;
//    }else if (_publicModel.productColor.length == 0) {
//        [self showTip:@"请填写货物颜色"];
//        return;
//    }else if (_publicModel.productPlace.length == 0) {
//        [self showTip:@"请填写货物产地"];
//        return;
//    }
    AddressViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"AddressViewControllerId"];
    vc.publicModel = _publicModel;
    [self.navigationController pushViewController:vc animated:YES];
    
}

/**
 *@brief 选择黄砂下属商品
 */
- (void)selectSendsSubType {
    UIView *bgView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 300, 130)];
    
    _pickerView = [[UIPickerView alloc] init];
    _pickerView.frame = CGRectMake(0, 0, bgView.vwidth, 100);
    _pickerView.dataSource = self;
    _pickerView.delegate = self;
    [_pickerView selectedRowInComponent:0];
    [bgView addSubview:_pickerView];
    JGActionSheetSection *s1 = [JGActionSheetSection sectionWithTitle:@"选择货物规格" message:nil contentView:bgView];
    JGActionSheetSection *s2 = [JGActionSheetSection sectionWithTitle:nil message:nil contentView:[self contentView]];
    _jgSheet = [JGActionSheet actionSheetWithSections:@[s1,s2]];
    _jgSheet.outsidePressBlock = ^ (JGActionSheet *sheet){
        [sheet dismissAnimated:YES];
    };
    SynacInstance *synac = [SynacInstance sharedInstance];
    GoodsModel *model = synac.sendSubType[0];
    NSArray *array = [synac sendsGroundSonProductType:model.goodsVal];
    _sendsGroundsonArray = [NSMutableArray arrayWithArray:array];
    [_jgSheet showInView:self.view animated:YES];
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

- (void)canlceSheet:(UIButton *)button {
    [_jgSheet dismissAnimated:YES];
}

/**
 *@brief 选择实物图片
 */
static long photoTag = 0;
- (void)chosePhoto:(UIButton *)btn {
    UIActionSheet *choosePhotoActionSheet;
    photoTag = btn.tag-400;
    
    if([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
        choosePhotoActionSheet = [[UIActionSheet alloc] initWithTitle:NSLocalizedString(@"选择头像图片", @"")
                                                             delegate:self
                                                    cancelButtonTitle:@"取消"
                                               destructiveButtonTitle:nil
                                                    otherButtonTitles:@"拍照", @"从相册获取", nil];
    } else {
        choosePhotoActionSheet = [[UIActionSheet alloc] initWithTitle:NSLocalizedString(@"选择照片", @"")
                                                             delegate:self
                                                    cancelButtonTitle:NSLocalizedString(@"cancel", @"")
                                               destructiveButtonTitle:nil
                                                    otherButtonTitles:NSLocalizedString(@"take_photo_from_library", @""), nil];
    }
    choosePhotoActionSheet.tag = 5000;
    [choosePhotoActionSheet showInView:[UIApplication sharedApplication].keyWindow];
}

#pragma mark - UIImagePickerControllerDelegate
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info
{

       [self dismissViewControllerAnimated:YES completion:^{
        UIImage *image = info[@"UIImagePickerControllerEditedImage"];
        UIButton *btn = (UIButton *)[self.view viewWithTag:400+photoTag];
        [btn setImage:image forState:UIControlStateNormal];
        if (photoTag == 0) {
            NSInteger index = [_publicModel.entryImages indexOfObject:_publicModel.image1];
            if (index != NSNotFound) {
                [_publicModel.entryImages replaceObjectAtIndex:index withObject:image];
            }else {
                [_publicModel.entryImages addObject:image];
            }
            _publicModel.image1 = image;
        }else if (photoTag == 1) {
            NSInteger index = [_publicModel.entryImages indexOfObject:_publicModel.image2];
            if (index != NSNotFound) {
                [_publicModel.entryImages replaceObjectAtIndex:index withObject:image];
            }else {
                [_publicModel.entryImages addObject:image];
            }
            _publicModel.image2 = image;
        }else {
            NSInteger index = [_publicModel.entryImages indexOfObject:_publicModel.image3];
            if (index != NSNotFound) {
                [_publicModel.entryImages replaceObjectAtIndex:index withObject:image];
            }else {
                [_publicModel.entryImages addObject:image];
            }
            _publicModel.image3 = image;
        }
    }];
    
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


#pragma mark - UIPickerView DataSource / Delegate
- (NSInteger)numberOfComponentsInPickerView:(UIPickerView *)pickerView {
    return 2;
}

- (NSInteger)pickerView:(UIPickerView *)pickerView numberOfRowsInComponent:(NSInteger)component {
    SynacInstance *synac = [SynacInstance sharedInstance];
    if (component == 0) {
        return synac.sendSubType.count;
    }else {
        return _sendsGroundsonArray.count;
    }
}

- (NSString *)pickerView:(UIPickerView *)pickerView titleForRow:(NSInteger)row forComponent:(NSInteger)component {
    SynacInstance *synac = [SynacInstance sharedInstance];
    if (component == 0) {
        GoodsModel *model = synac.sendSubType[row];
        return model.goodsName;
    }else {
        GoodChildModel *childModel = _sendsGroundsonArray[row];
        return childModel.goodChildPname;
    }
}

- (void)pickerView:(UIPickerView *)pickerView didSelectRow:(NSInteger)row inComponent:(NSInteger)component {
    SynacInstance *synac = [SynacInstance sharedInstance];
    [pickerView reloadComponent:1];
    if (component == 0) {
        GoodsModel *model = synac.sendSubType[row];
        NSArray *array = [synac sendsGroundSonProductType:model.goodsVal];
        _sendsGroundsonArray = [NSMutableArray arrayWithArray:array];
    }
}

@end
