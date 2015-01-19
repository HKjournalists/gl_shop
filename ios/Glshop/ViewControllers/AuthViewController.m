//
//  AuthViewController.m
//  Glshop
//
//  Created by River on 15-1-7.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "AuthViewController.h"
#import "PhotoUploadView.h"
#import "REPlaceholderTextView.h"
#import "WPHotspotLabel.h"
#import "WPAttributedStyleAction.h"
#import "NSString+WPAttributedMarkup.h"
#import "CopyRightInfoViewController.h"
#import "UnloadAddressViewController.h"
#import "ClickImage.h"

@interface AuthViewController ()<UITableViewDataSource,UITableViewDelegate,UITextViewDelegate,UIActionSheetDelegate,UploadImageDelete,UIAlertViewDelegate>

@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, strong) NSArray *dataSource;

/**
 *@brief 上传认证照片
 */
@property (nonatomic, strong) PhotoUploadView *authPhotoView;

/**
 *@brief 上传企业照片
 */
@property (nonatomic, strong) PhotoUploadView *photoUploadView;
@property (nonatomic, strong) WPHotspotLabel *tipActionLabel;

@end

@implementation AuthViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    
}

- (void)initDatas {
    _dataSource = @[@2,@2,@4,@2,@2,@2];
    _authModel = [[AuthModel alloc] init];
}

- (void)loadSubViews {
    UIView *header = [self loadTipView];
    
    _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
    _tableView.vheight -= kTopBarHeight+50;
    _tableView.dataSource = self;
    _tableView.delegate   = self;
    _tableView.tableHeaderView = header;
    [self.view addSubview:_tableView];
    
    _authPhotoView = [[PhotoUploadView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 100)];
    _authPhotoView.NotshowNextPhoto = YES;
    _authPhotoView.delegate = self;
    
    _photoUploadView = [[PhotoUploadView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 100)];
    _photoUploadView.delegate = self;
    
    UIButton *nexBtn = [UIFactory createBtn:BlueButtonImageName bTitle:@"提交认证申请" bframe:CGRectMake(10, self.tableView.vbottom+5, self.view.vwidth-20, 40)];
    [nexBtn addTarget:self action:@selector(authOption) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:nexBtn];
}

- (UIView *)loadTipView {
    UIImageView *imageview = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, self.view.vwidth, 55)];
    imageview.userInteractionEnabled = YES;
    UIImage *image = [UIImage imageNamed:@"attestation_prompt_background"];
    image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeTile];
    imageview.image = image;
//    [imageview addSubview:imageview];
    
    UIImageView *logo = [[UIImageView alloc] initWithFrame:CGRectMake(5, 9, 15, 15)];
    logo.image = [UIImage imageNamed:@"supply-and-demand_icon_laba"];
    [imageview addSubview:logo];
    
    _tipActionLabel = [[WPHotspotLabel alloc] initWithFrame:CGRectMake(logo.vright+2, 0, 290, 50)];
    _tipActionLabel.numberOfLines = 3;
    NSDictionary* style3 = @{@"body":@[[UIFont fontWithName:@"HelveticaNeue" size:15.0],ColorWithHex(@"#ba9057")],
                             @"help":[WPAttributedStyleAction styledActionWithAction:^{
                                 
                             }],
                             @"link": @[[UIFont boldSystemFontOfSize:16.f],ColorWithHex(@"#FF0000"),],
                             };
    
    self.tipActionLabel.attributedText = [@"本平台的<help>所有用户都需认证</help>，以保障平台的信息真实与交易真实。" attributedStringWithStyleBook:style3];
    [imageview addSubview:_tipActionLabel];
    
    return imageview;
}

#pragma mark - UIAction
- (void)showExp {
    if (!_authModel.ctypeValue) {
        HUD(@"请先选择认证类型");
        return;
    }
    
    
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
    
    if (!_authModel.phone.length) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"缺少联系人信息" message:@"您必须填写联系人信息，才能进行认证申请！" delegate:nil cancelButtonTitle:@"知道了" otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    [self showHUD:@"正在提交认证..." isDim:NO Yoffset:0];
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
    cell.textLabel.font = [UIFont systemFontOfSize:14.f];

    switch (indexPath.section) {
        case 0:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = @"认证类型";
                cell.imageView.image = [UIImage imageNamed:RedStartImageName];
            }else {
                NSString *title;
                if ([_authModel.ctypeValue isEqualToString:@"0"]) {
                    title = @"企业";
                }else if ([_authModel.ctypeValue isEqualToString:@"1"]) {
                    title = @"船舶";
                }else if ([_authModel.ctypeValue isEqualToString:@"2"]) {
                    title = @"个人";
                }else {
                    title = @"选择认证类型";
                }
                cell.textLabel.text = title;
                cell.indentationLevel = 3;
                cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
            }
        }
            break;
        case 1:
        {
            if (indexPath.row == 0) {
                cell.imageView.image = [UIImage imageNamed:RedStartImageName];
                cell.textLabel.text = @"认证照片";
            }else {
                [cell addSubview:_authPhotoView];
                
                ClickImage *imgView = [[ClickImage alloc] initWithFrame:CGRectMake(15+(260/3+15), 10, 260/3, 60)];
                imgView.image = [UIImage imageNamed:_authModel.exampleImgName];
                imgView.showImg = _authModel.exampleImgName ? [UIImage imageNamed:_authModel.exampleImgName]:[UIImage imageNamed:@"bg_demo_profile_company.jpg"];
                imgView.canClick = YES;
                [cell addSubview:imgView];
                
                UILabel *label = [UILabel labelWithTitle:@"点击查看样例图"];
                label.font = [UIFont systemFontOfSize:13.f];
                label.textColor = [UIColor orangeColor];
                label.frame = CGRectMake(imgView.vleft, imgView.vbottom+2, imgView.vwidth+20, 20);
                [cell addSubview:label];
            }
        }
            break;
        case 2:
        {
            NSArray *texts = @[@"联系信息",@"姓名",@"手机",@"电话",];
            NSString *none = @"无";
            cell.textLabel.text = texts[indexPath.row];
            if (indexPath.row == 0) {
                cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
            }else if (indexPath.row == 1) {
                cell.imageView.image = [UIImage imageNamed:@"attestation_icon_name_xin"];
                cell.detailTextLabel.text = _authModel.contact ? _authModel.contact : none;
            }else if (indexPath.row == 2) {
                cell.imageView.image = [UIImage imageNamed:@"attestation_icon_cellphone_xin"];
                cell.detailTextLabel.text = _authModel.phone ? _authModel.phone : none;
            }else if (indexPath.row == 3) {
                cell.imageView.image = [UIImage imageNamed:@"attestation_icon_phone_xin"];
                cell.detailTextLabel.text = _authModel.tel ? _authModel.tel : none;
            }
        }
            break;
        case 3:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = @"交易地址";
            }else {
                cell.textLabel.numberOfLines = 2;
                NSString *title;
                if (_authModel.addrAreaFullName&&_authModel.address) {
                    title = FommatString(_authModel.addrAreaFullName, _authModel.address);
                }else {
                    title = @"请选择交易地址";
                }
                cell.textLabel.text = title;
                cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;                
            }
        }
            break;
        case 4:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = @"企业简介";
            }else {
                REPlaceholderTextView *textView = [[REPlaceholderTextView alloc] initWithFrame:CGRectMake(15, 5,cell.vwidth-25, 80)];
                textView.placeholder = @"请输入企业简介";
                textView.returnKeyType = UIReturnKeyDone;
                textView.font = [UIFont systemFontOfSize:14.f];
                textView.delegate = self;
                textView.layer.borderWidth = 1;
                textView.text = _authModel.mark;
                textView.layer.borderColor = [UIColor lightGrayColor].CGColor;;
                [cell addSubview:textView];
            }
        }
            break;
        case 5:
        {
            if (indexPath.row == 0) {
                cell.textLabel.text = @"企业照片";
            }else {
                [cell addSubview:_photoUploadView];
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
    
    if (indexPath.section == 4) {
        if (indexPath.row == 1) {
            return 90;
        }
    }
    
    if (indexPath.section == 5) {
        if (indexPath.row == 1) {
            return 100;
        }
    }
    
    return 44;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:NO];
    
    if (indexPath.section == 0 && indexPath.row == 1) {
        UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:@"请选择认证类型" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:@"企业",@"船舶",@"个人", nil];
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

#pragma mark - UIActionSheet Delegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex == 0) {
        _authModel.ctypeValue = @"0";
        _dataSource = @[@2,@2,@4,@2,@2,@2];
        _authModel.exampleImgName = @"bg_demo_profile_company.jpg";
    }else if (buttonIndex == 1) {
        _authModel.ctypeValue = @"1";
        _dataSource = @[@2,@2,@4,@2,@2,@2];
        _authModel.exampleImgName = @"bg_demo_profile_ship.jpg";
    }else {
        _authModel.ctypeValue = @"2";
        _dataSource = @[@2,@2,@4,@2];
        _authModel.exampleImgName = @"bg_demo_profile_people.jpg";        
    }
    
    if (buttonIndex != 3) {
        [_tableView reloadData];
    }
}

#pragma mark - UITextView Delegate
- (void)textViewDidEndEditing:(UITextView *)textView {
    _authModel.mark = textView.text;
}

#pragma mark - UploadImageDelete
- (void)uploadImageSuccess:(NSString *)imgsId uploadView:(PhotoUploadView *)uploadView {
    DLog(@"%@",imgsId);
    if (uploadView == _authPhotoView) {
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
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:@"认证申请已发送，请等待审核通过" delegate:self cancelButtonTitle:@"知道了" otherButtonTitles:nil, nil];
        alert.tag = 2001;
        [alert show];
    } failedBlock:^(ASIHTTPRequest *request) {
        HUD(kNetError);
    }];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (alertView.tag == 2001) {
        [self.navigationController popToRootViewControllerAnimated:YES];
    }
}

@end
