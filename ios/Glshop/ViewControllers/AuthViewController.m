//
//  AuthViewController.m
//  Glshop
//
//  Created by River on 15-1-7.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "AuthViewController.h"
#import "AddressImgModel.h"
#import "PhotoUploadView.h"
#import "REPlaceholderTextView.h"
#import "WPHotspotLabel.h"
#import "WPAttributedStyleAction.h"
#import "NSString+WPAttributedMarkup.h"
#import "CopyRightInfoViewController.h"
#import "UnloadAddressViewController.h"
#import "ClickImage.h"
#import "ProfileViewController.h"
#import "HLCheckbox.h"
#import "WebViewController.h"
#import "IBActionSheet.h"

static NSString *autypeString = @"选择认证类型";

@interface AuthViewController ()<UITableViewDataSource,UITableViewDelegate,UITextViewDelegate,IBActionSheetDelegate,UploadImageDelete,UIAlertViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSArray *dataSource;

@property (nonatomic, strong) UILabel *agreeLabel;
@property (nonatomic, strong) HLCheckbox *box;
@property (nonatomic, strong) UIButton *nexBtn;
@property (nonatomic, assign) NSInteger markIndex;

/**
 *@brief 上传认证照片
 */
@property (nonatomic, strong) PhotoUploadView *authPhotoView;
/**
 *@brief 上传认证照片
 */
@property (nonatomic, strong) PhotoUploadView *auth2PhotoView;

/**
 *@brief 上传企业照片
 */
@property (nonatomic, strong) PhotoUploadView *photoUploadView;
@property (nonatomic, strong) WPHotspotLabel *tipActionLabel;

@end

@implementation AuthViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.shouldShowFailView = NO;
    
}

- (void)backRootVC {
    ProfileViewController *vc = [self findDesignatedViewController:[ProfileViewController class]];
    if (vc) {
        [self.navigationController popViewControllerAnimated:YES];
    }else {
        [self.navigationController popToRootViewControllerAnimated:YES];
    }
}

- (void)initDatas {
    self.title = @"认证申请";
    _dataSource = @[@2,@2,@4];
    _authModel = [[AuthModel alloc] init];
    
    UserInstance *userIns = [UserInstance sharedInstance];
    if (userIns.userType == user_personal) {
        _markIndex = 2;
        _authModel.ctypeValue = @"2";
        _dataSource = @[@2,@2,@4];
        _authModel.exampleImgName = @"bg_demo_profile_people.jpg";
    }else {
        _markIndex = -1;
    }
    
    if (_cModel) {
        _authModel.contact = _cModel.contact;
        _authModel.tel = _cModel.tel;
        _authModel.phone = _cModel.cphone;
        
        _authModel.addrAreaFullName = _cModel.addrAreaFullName;
        _authModel.address = _cModel.address;
        
        _authModel.mark = _cModel.mark;
        _authModel.companyImgIds = _cModel.companyImgIds;
    }
}

#pragma mark - UI
- (void)loadSubViews {
    UIView *header = [self loadTipView];
    
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vheight -= kTopBarHeight+50;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    _tableView.tableHeaderView = header;
    _tableView.tableFooterView = [self tableFooterView];
    [self.view addSubview:_tableView];
    
    _authPhotoView = [[PhotoUploadView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 100)];
    _authPhotoView.NotshowNextPhoto = YES;
    _authPhotoView.delegate = self;
    
    _auth2PhotoView = [[PhotoUploadView alloc] initWithFrame:CGRectMake(105, 0, SCREEN_WIDTH, 100)];
    _auth2PhotoView.NotshowNextPhoto = YES;
    _auth2PhotoView.delegate = self;
    
    _photoUploadView = [[PhotoUploadView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 100)];
    _photoUploadView.delegate = self;
    
    _nexBtn = [UIFactory createBtn:BlueButtonImageName bTitle:@"提交认证申请" bframe:CGRectMake(10, self.tableView.vbottom+5, self.view.vwidth-20, 40)];
    [_nexBtn addTarget:self action:@selector(authOption) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:_nexBtn];
}

- (UIView *)loadTipView {
    UIImageView *imageview = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 55)];
    imageview.userInteractionEnabled = YES;
    UIImage *image = [UIImage imageNamed:@"attestation_prompt_background"];
    image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeTile];
    imageview.image = image;
    
    UIImageView *logo = [[UIImageView alloc] initWithFrame:CGRectMake(5, 9, 15, 15)];
    logo.image = [UIImage imageNamed:@"supply-and-demand_icon_laba"];
    [imageview addSubview:logo];
    
    _tipActionLabel = [[WPHotspotLabel alloc] initWithFrame:CGRectMake(logo.vright+2, 0, 295, 50)];
    _tipActionLabel.numberOfLines = 3;
    NSDictionary* style3 = @{@"body":@[[UIFont fontWithName:@"HelveticaNeue" size:14.0],ColorWithHex(@"#ba9057")],
                             @"help":[WPAttributedStyleAction styledActionWithAction:^{
                                 
                             }],
                             @"link": @[[UIFont boldSystemFontOfSize:16.f],ColorWithHex(@"#FF0000"),],
                             };
    
    self.tipActionLabel.attributedText = [@"认证是免费的，认证通过后可永久点亮专属认证图标，同时您获取生意成交的机会也会越高！" attributedStringWithStyleBook:style3];
    [imageview addSubview:_tipActionLabel];
    
    return imageview;
}

- (UIView *)tableFooterView {
    UIView *bgView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 200)];
    bgView.backgroundColor = _tableView.backgroundColor;
    
    _box = [[HLCheckbox alloc] initWithBoxImage:[UIImage imageNamed:@"check_unselected"] selectImage:[UIImage imageNamed:@"check_selected"]];
    _box.frame = CGRectMake(10, 5, 20, 20);
    _box.selected = YES;
    [bgView addSubview:_box];
    
    __block typeof(self) weakSelf = self;
    _box.tapBlock = ^(BOOL selected) {
        weakSelf.nexBtn.enabled = selected;
        weakSelf.agreeLabel.textColor = selected ? [UIColor blackColor] : ColorWithHex(@"#999999");
    };
    
    self.agreeLabel = [UILabel labelWithTitle:@"同意"];
    _agreeLabel.frame = CGRectMake(_box.vright+2, _box.vtop, 40, _box.vheight);
    [bgView addSubview:_agreeLabel];
    
    UIButton * proBtn = [UIButton buttonWithTip:@"长江电商认证服务协议" target:self selector:@selector(showProtocal)];
    [proBtn setTitleColor:ColorWithHex(@"#507daf") forState:UIControlStateNormal];
    proBtn.frame = CGRectMake(_agreeLabel.vright-13, _agreeLabel.vtop+0.5, 180, 20);
    proBtn.titleLabel.font = _agreeLabel.font;
    [bgView addSubview:proBtn];
    
    UIView *tipView = [UIFactory createPromptViewframe:CGRectMake(10, 30, self.view.vwidth-20, 60) tipTitle:nil];
    UILabel *label2 = [UILabel labelWithTitle:@"标*为必填项。"];
    label2.font = UFONT_14;
    label2.frame = CGRectMake(10, 30, self.view.vwidth-20, 30);
    
    [tipView addSubview:label2];
    [bgView addSubview:tipView];
    
    return bgView;
}

#pragma mark - UIAction
- (void)showExp {
    if (!_authModel.ctypeValue) {
        HUD(@"请先选择认证类型");
        return;
    }
}

- (void)showProtocal {
#define protocalFileName @"长江电商用户认证协议-141009版.html"
    WebViewController *vc = [[WebViewController alloc] initWithFileName:protocalFileName];
    vc.title = @"长江电商用户认证协议";
    [self.navigationController pushViewController:vc animated:YES];
    
#undef protocalFileName
}

- (void)authOption {
    if (!_authModel.ctypeValue.length) {
        HUD(@"请选择认证类型");
        return;
    }
    
    if (!_authPhotoView.imageArray.count) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"缺少认证照片" message:@"您必须上传认证照片，才能进行认证申请！" delegate:nil cancelButtonTitle:@"知道了" otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    if ([_authModel.ctypeValue isEqualToString:@"2"]) { // 个人认证
        if (!_authPhotoView.imageArray.count || !_auth2PhotoView.imageArray.count) {
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"缺少认证照片" message:@"您必须上传认证照片，才能进行认证申请！" delegate:nil cancelButtonTitle:@"知道了" otherButtonTitles:nil, nil];
            [alert show];
            return;
        }
    }
    
    if (!_authModel.phone.length) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"缺少联系人信息" message:@"您必须填写联系人信息，才能进行认证申请！" delegate:nil cancelButtonTitle:@"知道了" otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    [self showHUD];
    [_authPhotoView uploadImage];
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return _dataSource.count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [_dataSource[section] integerValue];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.textLabel.font = UFONT_16;
    cell.textLabel.textColor = C_BLACK;
    cell.detailTextLabel.font = UFONT_16;
    cell.detailTextLabel.textColor = C_GRAY;
    
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    switch (indexPath.section) {
        case 0:
        {
            if (indexPath.row == 0) {
                
                cell.textLabel.text = @"认证类型";
                cell.imageView.image = [UIImage imageNamed:RedStartImageName];
                cell.textLabel.font = UFONT_14;
                cell.textLabel.textColor = C_GRAY;
            }else {
                NSString *title;
                if ([_authModel.ctypeValue isEqualToString:@"0"]) {
                    title = profile_company;
                }else if ([_authModel.ctypeValue isEqualToString:@"1"]) {
                    title = profile_bota;
                }else if ([_authModel.ctypeValue isEqualToString:@"2"]) {
                    title = profile_persion;
                }else {
                    title = @"选择认证类型";
                }
                cell.textLabel.text = title;
                cell.indentationLevel = 3;
                cell.selectionStyle = UITableViewCellSelectionStyleGray;
                cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
            }
        }
            break;
        case 1:
        {
            if (indexPath.row == 0) {
                cell.imageView.image = [UIImage imageNamed:RedStartImageName];
                cell.textLabel.text = @"认证照片";
                cell.textLabel.font = UFONT_14;
                cell.textLabel.textColor = C_GRAY;
            }else {
                [cell addSubview:_authPhotoView];
                
                ClickImage *imgView = [[ClickImage alloc] initWithFrame:CGRectMake(15+(260/3+15), 10, 260/3, 80)];
                
                if ([_authModel.ctypeValue isEqualToString:@"2"]) {
                    [cell addSubview:_auth2PhotoView];
                    imgView.frame =CGRectMake(15+(260/3+15)+105, 10, 260/3, 80);
                }
                
                imgView.image = [UIImage imageNamed:@"wallet_photos_exm"];
                imgView.showImg = _authModel.exampleImgName ? [UIImage imageNamed:_authModel.exampleImgName]:[UIImage imageNamed:@"bg_demo_profile_company.jpg"];
                imgView.canClick = YES;
                if ([[self authTypeStr] isEqualToString:autypeString]) {
                    imgView.hidden = YES;
                }else {
                    imgView.hidden = NO;
                }
                [cell addSubview:imgView];

            }
        }
            break;
        case 2:
        {
            NSArray *texts = @[@"联系信息",@"姓名",@"手机",@"电话",];
            NSString *none = @"无";
            cell.textLabel.text = texts[indexPath.row];
            
            if (indexPath.row == 0) {
                cell.textLabel.font = UFONT_14;
                cell.textLabel.textColor = C_GRAY;
                cell.selectionStyle = UITableViewCellSelectionStyleDefault;
                cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
            }else if (indexPath.row == 1) {
                cell.imageView.image = [UIImage imageNamed:@"attestation_icon_name_xin"];
                cell.detailTextLabel.text = _authModel.contact ? _authModel.contact : none;
            }else if (indexPath.row == 2) {
                cell.imageView.image = [UIImage imageNamed:@"attestation_icon_cellphone_xin"];
                cell.detailTextLabel.text = _authModel.phone ? _authModel.phone : none;
            }else if (indexPath.row == 3) {
                cell.imageView.image = [UIImage imageNamed:@"attestation_icon_phone_extan"];
                cell.detailTextLabel.text = _authModel.tel ? _authModel.tel : none;
            }
        }
            break;
            
           default:
            break;
    }
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 1) {
        if (indexPath.row == 1) {
            return 100;
        }
    }
    
    return 44;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
    
    if (indexPath.section == 0 && indexPath.row == 1) {
        IBActionSheet *sheet = [[IBActionSheet alloc] initWithTitle:@"选择认证类型" delegate:self cancelButtonTitle:globe_cancel_str destructiveButtonTitle:nil otherButtonTitles:profile_company,profile_bota,profile_persion, nil];
        
        
        sheet.markIndex = _markIndex;
        [sheet showInView:self.view];
    }
    
    if (indexPath.section == 2 && indexPath.row == 0) {
        CopyRightInfoViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"CopyRightInfoViewControllerId"];
        vc.opentionType = Add_Contact;
        vc.authModel = _authModel;
        [self.navigationController pushViewController:vc animated:YES];
    }
    
    if (indexPath.section == 3 && indexPath.row == 1) {
        UnloadAddressViewController *vc = [[UnloadAddressViewController alloc] init];
        vc.type = Address_Auth;
        [self.navigationController pushViewController:vc animated:YES];
    }
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

-(void)viewDidLayoutSubviews
{
    if ([self.tableView respondsToSelector:@selector(setSeparatorInset:)]) {
        [self.tableView setSeparatorInset:UIEdgeInsetsMake(0,kCellLeftEdgeInsets,0,0)];
    }
    
    if ([self.tableView respondsToSelector:@selector(setLayoutMargins:)]) {
        [self.tableView setLayoutMargins:UIEdgeInsetsMake(0,kCellLeftEdgeInsets,0,0)];
    }
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

#pragma mark - IBActionSheet Delegate
- (void)actionSheet:(IBActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex != actionSheet.cancelButtonIndex) {
        _markIndex = buttonIndex;
        
        if (buttonIndex == 0) {
            _authModel.ctypeValue = @"0";
            _dataSource = @[@2,@2,@4];
            _authModel.exampleImgName = @"bg_demo_profile_company.jpg";
        }else if (buttonIndex == 1) {
            _authModel.ctypeValue = @"1";
            _dataSource = @[@2,@2,@4];
            _authModel.exampleImgName = @"bg_demo_profile_ship.jpg";
        }else if (buttonIndex == 2) {
            _authModel.ctypeValue = @"2";
            _dataSource = @[@2,@2,@4];
            _authModel.exampleImgName = @"bg_demo_profile_people.jpg";
        }
        
        if (buttonIndex != 3) {
            [_tableView reloadData];
        }
    }
}

#pragma mark - UITextView Delegate
- (void)textViewDidEndEditing:(UITextView *)textView {
    _authModel.mark = textView.text;
}

#pragma mark - Private
- (NSString *)authTypeStr {
    NSString *title;
    if ([_authModel.ctypeValue isEqualToString:@"0"]) {
        title = profile_company;
    }else if ([_authModel.ctypeValue isEqualToString:@"1"]) {
        title = profile_bota;
    }else if ([_authModel.ctypeValue isEqualToString:@"2"]) {
        title = profile_persion;
    }else {
        title = @"选择认证类型";
    }
    return title;
}

#pragma mark - UploadImageDelete
- (void)uploadImageSuccess:(NSString *)imgsId uploadView:(PhotoUploadView *)uploadView {
    DLog(@"%@",imgsId);
    if (uploadView == _authPhotoView) {
        if ([_authModel.ctypeValue isEqualToString:@"2"]) {
            [_auth2PhotoView uploadImage];
        }else{
            if (_photoUploadView.imageArray.count) {
                [_photoUploadView uploadImage];
            }else {
                [self auth];
            }

        }
    }
    
    if(uploadView == _auth2PhotoView){
        if (_photoUploadView.imageArray.count) {
            [_photoUploadView uploadImage];
        }else {
            [self auth];
        }
    }
    
    
    if (uploadView == _photoUploadView) {
        [self auth];
    }
}

- (void)uploadImageFaile:(PhotoUploadView *)uploadView {
    if (uploadView == _authPhotoView) {
        [self hideHUD];
        HUD(@"网络不给力，请稍后尝试");
    }
    
    if (uploadView == _photoUploadView) {
        [self auth];
    }
}

/**
 *@brief 进行认证
 */
- (void)auth {
    NSString *authPhotoId = [_authPhotoView.imgIdArray componentsJoinedByString:@","];
    if ([_authModel.ctypeValue isEqualToString:@"2"]){
        NSString *authPhotoId2 = [_auth2PhotoView.imgIdArray componentsJoinedByString:@","];
        authPhotoId = [authPhotoId stringByAppendingFormat:@","];
        authPhotoId = [authPhotoId stringByAppendingString:authPhotoId2];
    }
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_authModel.ctypeValue,@"ctypeValue",authPhotoId,@"imgid", nil];
    if (_authModel.mark.length) {
        [params addString:_authModel.mark forKey:@"mark"];
    }

    if (_authModel.addressid.length) {
        [params addString:_authModel.addressid forKey:@"addressid"];
    }
    
    if (_photoUploadView.imgIdArray.count) {
        NSString *photoIdstr = [_photoUploadView.imgIdArray componentsJoinedByString:@","];
        [params addString:photoIdstr forKey:@"companyImgIds"];
    }
    
    [self requestWithURL:bCompanyAuthPath params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:@"认证申请已发送，请等待审核通过。" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles:nil, nil];
        alert.tag = 2001;
        [alert show];
    } failedBlock:^(ASIHTTPRequest *request) {

    }];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (alertView.tag == 2001) {
        [self.navigationController popToRootViewControllerAnimated:YES];
    }
}

@end
