//
//  UnLoadDetailViewController.m
//  Glshop
//
//  Created by River on 14-12-17.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "UnLoadDetailViewController.h"
#import "AddressPublicModel.h"
#import "UIButton+WebCache.h"
#import "REPlaceholderTextView.h"
#import "HLCheckbox.h"
#import "PlaceSelect.h"
#import "PhotoUploadView.h"



@interface UnLoadDetailViewController () <UITableViewDataSource,UITableViewDelegate,UITextViewDelegate, PlaceDidSelect,UploadImageDelete,UITextFieldDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) HLCheckbox *box;
@property (nonatomic, strong) UILabel *agreeLabel;
@property (nonatomic, strong) PlaceSelect *place;
@property (nonatomic, strong) PhotoUploadView *photoView;

@end

@implementation UnLoadDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
}

- (void)initDatas {
    if (!_editorAddress) {
        self.title = @"新增交易地址";
        _addressModel = [[AddressPublicModel alloc] init];
    }
    
    _place = [[PlaceSelect alloc] init];
    _place.delegate = self;
    
    if (!_editorAddress) {
        _photoView = [[PhotoUploadView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 100)];
        _photoView.delegate = self;
        _addressModel = [[AddressPublicModel alloc] init];
    }

}

-(void)viewDidLayoutSubviews
{
    if ([self.tableView respondsToSelector:@selector(setSeparatorInset:)]) {
        [self.tableView setSeparatorInset:UIEdgeInsetsMake(0,kCellLeftEdgeInsets,0,0)];
    }
    
    if ([self.tableView respondsToSelector:@selector(setLayoutMargins:)]) {
        [self.tableView setLayoutMargins:UIEdgeInsetsMake(0,kCellLeftEdgeInsets,0,0)];
    }
}

- (void)loadSubViews {
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithTitle:@"保存" style:UIBarButtonItemStylePlain target:self action:@selector(doneAction)];
    self.navigationItem.rightBarButtonItem = rightItem;
    
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.dataSource = self;
    _tableView.delegate = self;
    _tableView.vheight -= kTopBarHeight;
    [self.view addSubview:_tableView];
}

#pragma mark - Setter
- (void)setAddressModel:(AddressPublicModel *)addressModel {
    _addressModel = addressModel;
    _photoView = [[PhotoUploadView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 100)];
    _photoView.delegate = self;
    NSMutableArray *temp = [NSMutableArray array];
    if (_addressModel.addressImgModels.count) { // 如果卸货地址有图片，需要展示
        for (AddressImgModel *model in _addressModel.addressImgModels) {
            if (model.thumbnailSmall) {
                [temp addObject:model.thumbnailSmall];
            }
        }
        _photoView.imageUrlArray = [NSArray arrayWithArray:temp];
    }
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
//    return section == 0 ? 6 : 1;
    if (section == 0) {
        return _editorAddress ? 5 : 6;
    }else {
        return 1;
    }
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.row == 2) {
        return 100;
    }
    return 44;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.imageView.image = [UIImage imageNamed:RedStartImageName];
    cell.textLabel.frame = CGRectMake(0, 0, 10, 10);
    cell.textLabel.font = [UIFont systemFontOfSize:FONT_16];
    cell.selectionStyle = indexPath.row == 0 ? UITableViewCellSelectionStyleDefault : UITableViewCellSelectionStyleNone;
    if (indexPath.section == 0) {
        switch (indexPath.row) {
            case 0:
            {
                cell.textLabel.text =  _addressModel.areaFullName ? _addressModel.areaFullName : @"省、市、区";
                cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
                
            }
                break;
            case 1:
            {
                REPlaceholderTextView *textView = [[REPlaceholderTextView alloc] initWithFrame:CGRectMake(10, 0, cell.vwidth-30, 44)];
                textView.placeholder = @"请输入详细的交易地址";
                textView.font = [UIFont systemFontOfSize:FONT_16];
                textView.delegate = self;
                textView.textColor = C_GRAY;
                textView.text = _addressModel.address;
                [cell.contentView addSubview:textView];
            }
                break;
            case 2:
            {
                cell.imageView.image = nil;
                if (!_photoView.superview) {
                    [cell addSubview:_photoView];
                }

            }
                break;
            case 3:
            {
                cell.textLabel.text = cell_address_depth;
                UITextField *textField = [UITextField textFieldWithPlaceHodler:placehold_input_write withDelegate:self];
                textField.frame = CGRectMake(SCREEN_WIDTH-110, 22-15, 90, 30);
                textField.tag = 2013;
                textField.keyboardType = UIKeyboardTypeDecimalPad;
                textField.textAlignment = NSTextAlignmentRight;
                textField.font  = UFONT_16;
                textField.textColor = C_GRAY;
                [cell.contentView addSubview:textField];
                textField.text = [_addressModel.deep stringValue];
            }
                break;
            case 4:
            {
                cell.textLabel.text = cell_boat_tun;
                UITextField *textField = [UITextField textFieldWithPlaceHodler:placehold_input_write withDelegate:self];
                textField.frame = CGRectMake(SCREEN_WIDTH-110, 22-15, 90, 30);
                textField.textAlignment = NSTextAlignmentRight;
                textField.keyboardType = UIKeyboardTypeDecimalPad;
                textField.tag = 2014;
                textField.font  = UFONT_16;
                textField.textColor = C_GRAY;
                [cell.contentView addSubview:textField];
                textField.text = [_addressModel.shippington stringValue];
            }
                break;
                
                case 5:
            {
                _box = [[HLCheckbox alloc] initWithBoxImage:[UIImage imageNamed:@"check_unselected"] selectImage:[UIImage imageNamed:@"check_selected"]];
                _box.frame = CGRectMake(15, cell.vheight/2-10, 20, 20);
                [cell.contentView addSubview:_box];
                
                self.agreeLabel = [UILabel labelWithTitle:@"设置为默认的交易地址"];
                _agreeLabel.font = [UIFont systemFontOfSize:FONT_16];
                _agreeLabel.frame = CGRectMake(_box.vright+2, _box.vtop, 200, _box.vheight);
                [cell.contentView addSubview:_agreeLabel];
 
            }
                break;
                
            default:
                break;
        }
    }
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    if (indexPath.section == 0) {
        if (indexPath.row == 0) {
            [_place showPlaceSelectView];
        }
    }
}

#define sectionHigh 10
- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section
{
    return 90;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return sectionHigh;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 90)];
    
    UIButton *btn0 = [UIFactory createBtn:BlueButtonImageName bTitle:@"设置为默认卸货地址" bframe:CGRectZero];
    [btn0 addTarget:self action:@selector(installDefaultAddress) forControlEvents:UIControlEventTouchUpInside];
    btn0.frame = CGRectMake(10, 15, SCREEN_WIDTH-20, 35);
    
    UIButton *btn = [UIButton buttonWithTip:@"删除该交易地址" target:self selector:@selector(deleteAddressAlert)];
    btn.frame = CGRectMake(10, btn0.vbottom+10, SCREEN_WIDTH-20, 35);
    UIImage *image = [UIImage imageNamed:@"attestation_icon"];
    image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(20, 20, 20, 20) resizingMode:UIImageResizingModeStretch];
    [btn setBackgroundImage:image forState:UIControlStateNormal];
    if (_editorAddress) {
        [view addSubview:btn0];
        [view addSubview:btn];
    }
    
    return view;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, sectionHigh)];
    return view;
}

-(void)tableView:(UITableView *)tableView willDisplayCell:(UITableViewCell *)cell forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if ([cell respondsToSelector:@selector(setSeparatorInset:)]) {
        [cell setSeparatorInset:UIEdgeInsetsMake(0,kCellLeftEdgeInsets,0,0)];
    }
    
    if ([cell respondsToSelector:@selector(setLayoutMargins:)]) {
        [cell setLayoutMargins:UIEdgeInsetsMake(0,kCellLeftEdgeInsets,0,0)];
    }
}


#pragma mark - UIActions
/**
 *@brief 保存编辑
 */
- (void)doneAction {
    
    if (!_addressModel.areacode.length) {
        HUD(@"请选择省市区");
        return;
    }
    
    if (!_addressModel.address.length) {
        HUD(@"请填写详细地址");
        return;
    }
    
    if (!_addressModel.deep) {
        HUD(@"请填写卸货码头水深度");
        return;
    }
    
    if (!_addressModel.shippington) {
        HUD(@"请填写载重吨位");
        return;
    }
    
    [self showHUD];
    if (_photoView.imageArray.count > 0) { // 新添地址附带图片时，先上传图片
        [_photoView uploadImage];
    }else {
        if (_editorAddress) {
            [self editorAddresses:nil];
        }else {
            [self addAddress:nil];
        }
    }
}

/**
 *@brief 将交易地址加到服务器
 */
- (void)addAddress:(NSString *)imgId {
    UserInstance *userInstance = [UserInstance sharedInstance];
    NSString *cid = userInstance.user.cid;
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_addressModel.address,@"address",cid,@"cid",_addressModel.deep,@"deep",_addressModel.shippington,@"shippington",_addressModel.areacode,@"areacode", nil];
    if (imgId) {
        [params addString:imgId forKey:@"addressImgIds"];
    }
    if (_box.isSelected) {
        [params setObject:@1 forKey:@"status"];
    }
    [self requestWithURL:bAddUnloadAddress params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [self handleRequestSuccess];

    } failedBlock:^ (ASIHTTPRequest *req){

    }];
}

- (void)handleRequestSuccess {
    NSString *tip = _editorAddress ? @"编辑地址成功" : @"保存地址成功";
    HUD(tip);
    [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushAddressListNotification object:nil];
    [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushCompanyInfoNotification object:nil];
    [self.navigationController popViewControllerAnimated:YES];

}

/**
 *@brief 编辑卸货地址
 */
- (void)editorAddresses:(NSString *)imgId {

    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_addressModel.address,@"address",_addressModel.id,@"id",_addressModel.deep,@"deep",_addressModel.shippington,@"shippington",_addressModel.areacode,@"areacode", nil];

    if (imgId) {
        [params addString:imgId forKey:@"addressImgIds"];
    }

    [self requestWithURL:bModifyUnloadAddress
                  params:params
              HTTPMethod:kHttpPostMethod
           completeBlock:^(ASIHTTPRequest *request, id responseData) {
               [self handleRequestSuccess];
    } failedBlock:^(ASIHTTPRequest *req){
//        [self showTip:@"编辑地址失败，请稍后尝试！"];
    }];
}

/**
 *@brief 点击删除交易地址
 */
- (void)deleteAddress {
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_addressModel.id,@"id", nil];
    [self requestWithURL:bDeleUnloadAddress
                  params:params
              HTTPMethod:kHttpPostMethod
           completeBlock:^(ASIHTTPRequest *request, id responseData) {
               HUD(@"删除成功");
               [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushAddressListNotification object:nil];
               [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushCompanyInfoNotification object:nil];
               [self.navigationController popViewControllerAnimated:YES];
           } failedBlock:^(ASIHTTPRequest *req){

           }];
}

/**
 *@brief 设置为默认交易地址
 */
- (void)installDefaultAddress {
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_addressModel.id,@"id", nil];
    [self showHUD];
    [self requestWithURL:bSetDefaultUnloadAddress
                  params:params
              HTTPMethod:kHttpPostMethod
           completeBlock:^(ASIHTTPRequest *request, id responseData) {
               HUD(@"设置为默认地址成功");
               [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushCompanyInfoNotification object:nil];
               [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushAddressListNotification object:nil];
               [self.navigationController popViewControllerAnimated:YES];
           } failedBlock:^(ASIHTTPRequest *req){
               
           }];
}

#pragma mark - PlaceDidSelect Delegate
- (void)placeDidSelect:(NSString *)place theAreaCode:(NSString *)areaCode{
    _addressModel.areacode = areaCode;
    _addressModel.areaFullName = place;
    [_tableView reloadRowsAtIndexPaths:@[[NSIndexPath indexPathForRow:0 inSection:0]] withRowAnimation:0];
}

#pragma mark - UITextView Delegate
- (void)textViewDidEndEditing:(UITextView *)textView {
    _addressModel.address = textView.text;
}

#pragma mark - 
- (void)textFieldDidEndEditing:(UITextField *)textField {
    if (!textField.text.length) {
        return;
    }
    
    float deep = [textField.text floatValue];
    
    if (textField.tag == 2013) {
        _addressModel.deep = [NSNumber numberWithFloat:deep];
    }
    
    if (textField.tag == 2014) {
        _addressModel.shippington = [NSNumber numberWithFloat:deep];
    }
}


#pragma mark - UploadImageDelete
- (void)uploadImageSuccess:(NSString *)imgsId uploadView:(PhotoUploadView *)uploadView{
    if (_editorAddress) {
        [self editorAddresses:imgsId];
    }else {
        [self addAddress:imgsId];
    }
}

- (void)uploadImageFaile:(PhotoUploadView *)uploadView{
    if (_editorAddress) {
        [self editorAddresses:nil];
    }else {
        [self addAddress:nil];
    }

}

- (void)pickerImageDidReplace {
    if (_editorAddress) {
        
    }
}

- (void)deleteAddressAlert {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"删除地址" message:@"请确认是否要删除该地址信息" delegate:self cancelButtonTitle:nil otherButtonTitles:globe_cancel_str,globe_sure_str, nil];
    alert.tag = 1001;
    [alert show];
}

#pragma mark - UIAlertView Delegate
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (alertView.tag == 1001) {
        if (buttonIndex) { // 确认取消发布
            [self deleteAddress];
        }
    }
}


@end
