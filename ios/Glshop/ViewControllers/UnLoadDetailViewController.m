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
            [temp addObject:model.thumbnailSmall];
        }
        _photoView.imageUrlArray = [NSArray arrayWithArray:temp];
    }
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return section == 0 ? 6 : 1;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.row == 2) {
        return 100;
    }
    return 44;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.imageView.image = [UIImage imageNamed:@"attestation_icon_"];
    cell.textLabel.font = [UIFont systemFontOfSize:14.5f];
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
                REPlaceholderTextView *textView = [[REPlaceholderTextView alloc] initWithFrame:CGRectMake(45, 0, cell.vwidth-30, 44)];
                textView.placeholder = @"请输入详细的交易地址";
                textView.font = [UIFont systemFontOfSize:14.f];
                textView.delegate = self;
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
                cell.textLabel.text = @"卸货码头水深度(单位:米)";
                UITextField *textField = [UITextField textFieldWithPlaceHodler:@"填写" withDelegate:self];
                textField.frame = CGRectMake(140, 22-15, 150, 30);
                textField.tag = 2013;
                textField.keyboardType = UIKeyboardTypeDecimalPad;
                textField.textAlignment = NSTextAlignmentRight;
                [cell.contentView addSubview:textField];
                textField.text = [_addressModel.deep stringValue];
            }
                break;
            case 4:
            {
                cell.textLabel.text = @"可停泊载重船吨位(单位:吨)";
                UITextField *textField = [UITextField textFieldWithPlaceHodler:@"填写" withDelegate:self];
                textField.frame = CGRectMake(140, 22-15, 150, 30);
                textField.textAlignment = NSTextAlignmentRight;
                textField.keyboardType = UIKeyboardTypeDecimalPad;
                textField.tag = 2014;
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
                _agreeLabel.font = [UIFont systemFontOfSize:14.5f];
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
    return 60;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section
{
    return sectionHigh;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 60)];
    
    UIButton *btn = [UIButton buttonWithTip:@"删除该交易地址" target:self selector:@selector(deleteAddress)];
    btn.frame = CGRectMake(SCREEN_WIDTH/2-75, 30-35/2, 150, 35);
    UIImage *image = [UIImage imageNamed:@"attestation_icon"];
    image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(20, 20, 20, 20) resizingMode:UIImageResizingModeStretch];
    [btn setBackgroundImage:image forState:UIControlStateNormal];
    if (_editorAddress) {
        [view addSubview:btn];
    }
    
    return view;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section
{
    UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, sectionHigh)];
    return view;
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
    
    NSString *tip = _editorAddress ? @"正在修改..." : @"正在添加...";
    [self showHUD:tip isDim:NO Yoffset:0];
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
    [self requestWithURL:bAddUnloadAddress params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        [self handleRequestSuccess];
        

    } failedBlock:^ (ASIHTTPRequest *req){
        [self showTip:@"添加地址失败，请稍后尝试！"];
    }];
}

- (void)handleRequestSuccess {
    if (_box.isSelected) {
        NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_addressModel.id,@"id", nil];
        [self requestWithURL:bSetDefaultUnloadAddress params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
            NSString *tip = _editorAddress ? @"修改交易地址成功。" : @"添加交易地址成功。";
            HUD(tip);
            [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushAddressListNotification object:nil];
            [self.navigationController popViewControllerAnimated:YES];            
        } failedBlock:^ (ASIHTTPRequest *req){
            NSString *tip = _editorAddress ? @"修改交易地址失败，请稍后尝试。" : @"添加交易地址失败，请稍后尝试。";
            HUD(tip);
        }];
    }else {
        NSString *tip = _editorAddress ? @"修改交易地址成功。" : @"添加交易地址成功。";
        HUD(tip);
        [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushAddressListNotification object:nil];
        [self.navigationController popViewControllerAnimated:YES];
    }
}

/**
 *@brief 编辑卸货地址
 */
- (void)editorAddresses:(NSString *)imgId {
    
//    UserInstance *userInstance = [UserInstance sharedInstance];
//    NSString *cid = userInstance.user.cid;
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
        [self showTip:@"编辑地址失败，请稍后尝试！"];
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
               HUD(@"删除成功！");
               [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushAddressListNotification object:nil];
               [self.navigationController popViewControllerAnimated:YES];
           } failedBlock:^(ASIHTTPRequest *req){
               [self showTip:@"删除地址失败，请稍后尝试！"];
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

@end
