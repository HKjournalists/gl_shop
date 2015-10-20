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
#import "IBActionSheet.h"

@interface CompanyAuthViewController () <IBActionSheetDelegate,UITableViewDataSource,UITableViewDelegate,UITextFieldDelegate,UITextViewDelegate>

@property (nonatomic, weak) SynacInstance *synac;

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) UITextField *productColorField;
@property (nonatomic, strong) UITextField *productPlaceField;

/**
 *@brief 为IBActionSheet记录选择的索引，默认为0
 */
@property (nonatomic, assign) NSInteger markSellTypeIndex;

/**
 *@brief 为IBActionSheet记录选择的索引，默认为0
 */
@property (nonatomic, assign) NSInteger markProductTypeIndex;

@end


@implementation CompanyAuthViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}

#pragma mark - Override
- (void)initDatas {
    self.title = @"发布信息";
    
    _synac = [SynacInstance sharedInstance];
    
    if (_publicInfoType == public_New) { // 如果不是编辑发布信息，需要初始化PublicInfoModel
        _markProductTypeIndex = -1;
        _markSellTypeIndex = -1;
        
        _publicModel = [[PublicInfoModel alloc] init];
        _publicModel.type = IntToNSNumber(0); // 不是买也不是卖
        _publicModel.pcode = UnKnow;
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
    UIView *header = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 70)];
    [header addSubview:guideView];
    
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vheight -= kTopBarHeight+guideView.vheight;
    _tableView.sectionFooterHeight = 5;
    _tableView.sectionHeaderHeight = 5;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    _tableView.tableHeaderView = header;
    [self.view addSubview:_tableView];
    
    UIButton *nexBtn = [UIFactory createBtn:BlueButtonImageName bTitle:btntitle_next bframe:CGRectMake(10, self.tableView.vbottom+10, self.view.vwidth-20, 40)];
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
        _markSellTypeIndex = 1;
        _sectionsArray = @[@1,@4,@2,@2,@2,@1];
    }else {
        _markSellTypeIndex = 0;
        _sectionsArray = @[@1,@4,@2,@2,@1];
    }
    
    if ([_publicModel.pcode  isEqualToString: glProduct_top_send_code]) { // 如果商品大类是黄砂
        NSMutableArray *temp = [NSMutableArray arrayWithArray:_sectionsArray];
        [temp replaceObjectAtIndex:1 withObject:@4];
        _sectionsArray = [NSArray arrayWithArray:temp];
        _markProductTypeIndex = 0;
    }else {
        NSMutableArray *temp = [NSMutableArray arrayWithArray:_sectionsArray];
        [temp replaceObjectAtIndex:1 withObject:@3];
        _sectionsArray = [NSArray arrayWithArray:temp];
        _markProductTypeIndex = 1;
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
    cell.textLabel.textColor = C_GRAY;
    cell.textLabel.font = [UIFont systemFontOfSize:FONT_16];
    cell.detailTextLabel.textColor = C_BLACK;
    cell.detailTextLabel.font = [UIFont systemFontOfSize:FONT_16];
    
    if (indexPath.section == 0 || indexPath.section == 1) {
        cell.selectionStyle = UITableViewCellSelectionStyleDefault;
    }else {
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }
    
    cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
    switch (indexPath.section) {
        case 0:
        {
            cell.imageView.image = [UIImage imageNamed:RedStartImageName];
            cell.textLabel.text = @"发布信息类型";
            NSString *value;
            if ([_publicModel.type intValue] == 0) {
                value = glProduct_select;
            }else if ([_publicModel.type intValue] == 1) {
                value = buyInfo;
            }else {
                value = sellInfo;
            }
            if (self.publicInfoType == public_Modify) { // 如果是修改发布信息，那么不能修改发布信息类型
                cell.accessoryType = UITableViewCellAccessoryNone;
                cell.selectionStyle = UITableViewCellSelectionStyleNone;
            }
            cell.detailTextLabel.text = value;
        }
            break;
        case 1:
        {
            cell.imageView.image = [UIImage imageNamed:RedStartImageName];
            GoodsModel *model = [_synac goodsModelForPcode:_publicModel.pcode];
            if (indexPath.row == 0) {
                cell.textLabel.text = glProduct_goods_info;
                cell.detailTextLabel.text = model ? model.goodsName : glProduct_select;
            }

            if ([model.goodsVal isEqualToString:glProduct_top_send_code]) { // 黄砂
                GoodsModel *goods = [_synac goodsModelForPtype:_publicModel.ptype];
                switch (indexPath.row) {
                    case 1:
                    {   // 黄砂二级子类
                        cell.textLabel.text = glProduct_goods_category;
                        NSString *value = goods ? goods.goodsName : glProduct_select;
                        cell.detailTextLabel.text = value;
                    }
                        break;
                    case 2:
                    {   // 黄砂三级子类规格
                        cell.textLabel.text = glProduce_goods_stand;
                        GoodChildModel *model = [_synac goodsChildModlelFor:_publicModel.ptype deepId:_publicModel.pid];
                        if (![_publicModel.pid isEqualToString:UnKnow] && model) {
                           cell.detailTextLabel.text = [model combineNameWithUnit];
                        }else {
                            cell.detailTextLabel.text = glProduct_select;
                        }

                    }
                        break;
                    case 3:
                    {   // 黄砂三级子类详细规格
                        cell.textLabel.text = glProuct_goods_detailStand;
                        cell.imageView.image = nil;
                        cell.indentationLevel = 3;
                        cell.detailTextLabel.text = @"";
                    }
                        break;
                    default:
                        break;
                }
            }else if ([model.goodsVal isEqualToString:glProduct_top_stone_code]) {
                if (indexPath.row == 1) {
                    cell.textLabel.text = glProduce_goods_stand;
                    GoodChildModel *model = [_synac goodsChildStone:_publicModel.pid];
                    NSString *value = model ? [model combineNameWithUnit] : glProduct_select;
                    cell.detailTextLabel.text = value;
                }else if (indexPath.row == 2) {
                    cell.imageView.image = nil;
                    cell.indentationLevel = 3;
                    cell.indentationWidth = 7.5;
                    cell.textLabel.text = glProuct_goods_detailStand;
                    cell.detailTextLabel.text = otherofSetUp;
                }
               
            }else {
                switch (indexPath.row) {
                    case 1:
                    {   // 黄砂二级子类
                        cell.textLabel.text = glProduct_goods_category;
                        cell.detailTextLabel.text = glProduct_select;
                    }
                        break;
                    case 2:
                    {   // 黄砂三级子类规格
                        cell.textLabel.text = glProduce_goods_stand;
                        cell.detailTextLabel.text = glProduct_select;
                    }
                        break;
                    case 3:
                    {   // 黄砂三级子类详细规格
                        cell.textLabel.text = glProuct_goods_detailStand;
                        cell.imageView.image = nil;
                        cell.indentationLevel = 3;
                        cell.indentationWidth = 7.5;
                        cell.detailTextLabel.text = @"";
                    }
                        break;
                    default:
                        break;
                }

            }
        }
            break;
        case 2:
        {
            cell.accessoryType = UITableViewCellAccessoryNone;
            if (indexPath.row == 0) {
                cell.textLabel.text = glProduct_goods_color;
                
                _productColorField = [UITextField textFieldWithPlaceHodler:@"填写" withDelegate:self];
                _productColorField.text = _publicModel.pcolor;
                _productColorField.frame = CGRectMake(100, 22-15, 210, 30);
                _productColorField.textAlignment = NSTextAlignmentRight;
                [cell.contentView addSubview:_productColorField];
            }else if (indexPath.row == 1) {
                cell.textLabel.text = glProduct_goods_place;
                _productPlaceField = [UITextField textFieldWithPlaceHodler:@"填写" withDelegate:self];
                _productPlaceField.text = _publicModel.paddress ;
                _productPlaceField.frame = CGRectMake(100, 22-15, 210, 30);
                _productPlaceField.textAlignment = NSTextAlignmentRight;
                [cell.contentView addSubview:_productPlaceField];

            }

        }
            break;
        case 3:
        {
            cell.accessoryType = UITableViewCellAccessoryNone;
            if ([_publicModel.type integerValue] == 2) {
                cell.selectionStyle = UITableViewCellSelectionStyleNone;
                cell.accessoryType = UITableViewCellAccessoryNone;
                cell.imageView.image = nil;
                if (indexPath.row == 0) {
                    cell.textLabel.text = @"实物照片";
                    cell.accessoryType = UITableViewCellAccessoryNone;
                }else if (indexPath.row == 1){
                    
                    [cell addSubview:_publicModel.photoUploadView];
                }
            }else {
                if (indexPath.row == 0) {
                    cell.imageView.image = nil;
                    cell.textLabel.text = @"货物备注";
                }else {
                    REPlaceholderTextView *textView = [[REPlaceholderTextView alloc] initWithFrame:CGRectMake(15, 5,cell.vwidth-20, 80)];
                    textView.placeholder = @"请输入货物备注信息";
                    textView.returnKeyType = UIReturnKeyDone;
                    textView.delegate = self;
                    textView.layer.borderWidth = 1;
                    textView.font = UFONT_16;
                    textView.text = _publicModel.premark;
                    textView.layer.borderColor = [UIColor lightGrayColor].CGColor;;
                    [cell addSubview:textView];
                }
            }
            
        }
            break;
        case 4:
        {
            cell.accessoryType = UITableViewCellAccessoryNone;
            if ([_publicModel.type integerValue] == 2) {
                if (indexPath.row == 0) {
                    cell.imageView.image = nil;
                    cell.textLabel.text = @"货物备注";
                }else {
                    REPlaceholderTextView *textView = [[REPlaceholderTextView alloc] initWithFrame:CGRectMake(15, 5,cell.vwidth-20, 80)];
                    textView.placeholder = @"请输入货物备注信息";
                    textView.returnKeyType = UIReturnKeyDone;
                    textView.delegate = self;
                    textView.layer.borderWidth = 1;
                    textView.font = UFONT_16;
                    textView.text = _publicModel.premark;
                    textView.layer.borderColor = [UIColor lightGrayColor].CGColor;;
                    [cell addSubview:textView];
                }
            }else {
                [cell addSubview:[self tipView]];
            }
        }
            break;
        case 5:
        {
            cell.accessoryType = UITableViewCellAccessoryNone;
            [cell addSubview:[self tipView]];
        }
        default:
            break;
    }
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 3) {
        if (indexPath.row == 1) {
            return [_publicModel.type integerValue]==1 ? 90 : 100;
        }
    }
    if (indexPath.section == 4) {
        if ([_publicModel.type integerValue] == 2) {
            if (indexPath.row == 1) {
                return 90;
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
        if (self.publicInfoType != public_Modify) { // 如果是修改发布信息，那么不能修改发布信息类型
            IBActionSheet *sheet = [[IBActionSheet alloc] initWithTitle:@"选择发布信息类型"
                                                               delegate:self
                                                      cancelButtonTitle:globe_cancel_str
                                                 destructiveButtonTitle:nil
                                                      otherButtonTitlesArray:@[@"求购信息",@"出售信息",]];
            sheet.tag = 100+indexPath.section;
            sheet.markIndex = _markSellTypeIndex;
            [sheet showInView:self.view];
        }
    }else if (indexPath.section == 1 ) {
        if (indexPath.row == 0) {
            IBActionSheet *sheet = [[IBActionSheet alloc] initWithTitle:@"选择货物信息类型"
                                                               delegate:self cancelButtonTitle:globe_cancel_str
                                                 destructiveButtonTitle:nil
                                                      otherButtonTitles:nil, nil];
            for (NSString *name in [[SynacInstance sharedInstance] productTopNames]) {
                [sheet addButtonWithTitle:name];
            }
            sheet.tag = 200+indexPath.row;
            sheet.markIndex = _markProductTypeIndex;
            [sheet showInView:self.view];
        }
        
        if ([cell.textLabel.text isEqualToString:glProduct_goods_category]) { // 商品分类（黄砂分类）
            if ([_publicModel.pcode isEqualToString:UnKnow]) {
                [self showTip:@"请先选择货物信息"];
                return;
            }
            [self sheetForSelectSendsType:sendsSubType];
        }else if ([cell.textLabel.text isEqualToString:glProduce_goods_stand]) {
            if (indexPath.row == 1) { // 石子规格
                [self sheetForSelectSendsType:stoneType];
                
            }else { // 黄砂规格
                if ([_publicModel.ptype isEqualToString:UnKnow]) {
                    HUD(@"请先选择分类");
                    return;
                }
                [self sheetForSelectSendsType:sendsGroundsonType];
            }
            
        }else if ([cell.textLabel.text isEqualToString:glProuct_goods_detailStand]) { // 详细规格
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


/**
 *@brief 重置发布model
 */
- (void)resetPublicModel {
    _publicModel.ptype = UnKnow;
    _publicModel.pid = UnKnow;
    
}

#pragma mark - UIActionSheet Delegate
- (void)actionSheet:(IBActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    switch (actionSheet.tag) {
        case 100: // 选择发布信息类型
        {
            if (buttonIndex != actionSheet.cancelButtonIndex) {
                _markSellTypeIndex = buttonIndex;
            }
            NSIndexPath *apath = [NSIndexPath indexPathForRow:0 inSection:0];
            UITableViewCell *cell = [_tableView cellForRowAtIndexPath:apath];
            if (buttonIndex == 1) {
                cell.detailTextLabel.text = sellInfo;
                _publicModel.type = IntToNSNumber(2);
                if (_sectionsArray.count == 5) {
                    NSMutableArray *temp = [NSMutableArray arrayWithArray:_sectionsArray];
                    [temp insertObject:@2 atIndex:3];
                    _sectionsArray = [NSArray arrayWithArray:temp];
                }
 
            }else if (buttonIndex == 0) {
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
            if (buttonIndex != actionSheet.cancelButtonIndex) {
                _markProductTypeIndex = buttonIndex;
            }
            
            NSIndexPath *apath = [NSIndexPath indexPathForRow:0 inSection:1];
            UITableViewCell *cell = [_tableView cellForRowAtIndexPath:apath];
            if (buttonIndex != actionSheet.cancelButtonIndex) {
                cell.detailTextLabel.text = [actionSheet buttonTitleAtIndex:buttonIndex];
            }
            
            if (buttonIndex == 0) {
                _publicModel.pcode = glProduct_top_send_code;
                if ([_sectionsArray[1] integerValue] == 4) {
                    return;
                }
                [self resetPublicModel];
                _publicModel.proList = nil;
                NSIndexPath *path = [NSIndexPath indexPathForRow:1 inSection:1];
                NSMutableArray *temp = [NSMutableArray arrayWithArray:_sectionsArray];
                [temp replaceObjectAtIndex:1 withObject:@4];
                _sectionsArray = [NSArray arrayWithArray:temp];
                
                [_tableView beginUpdates];
                [_tableView insertRowsAtIndexPaths:@[path] withRowAnimation:UITableViewRowAnimationAutomatic];
                [_tableView endUpdates];
                [_tableView reloadData];
                
            }else if (buttonIndex == 1) {
                _publicModel.pcode = glProduct_top_stone_code;
                if ([_sectionsArray[1] integerValue] == 3) {
                    return;
                }
                [self resetPublicModel];
                _publicModel.proList = nil;
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
    if (![self authPublicData]) {
        return;
    }

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
    
    if ([_publicModel.pcode isEqualToString:UnKnow]) {
        [self showTip:@"请选择货物信息"];
        return NO;
    }
    
    if ([_publicModel.pcode isEqualToString:glProduct_top_send_code]) {
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
    
    return YES;
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
