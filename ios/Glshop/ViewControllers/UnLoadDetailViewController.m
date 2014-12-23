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

@interface UnLoadDetailViewController () <UITableViewDataSource,UITableViewDelegate,UINavigationControllerDelegate,UIImagePickerControllerDelegate,UIActionSheetDelegate>

@property (nonatomic, strong) UITableView *tableView;

@end

@implementation UnLoadDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
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

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return section == 0 ? 5 : 1;
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
                cell.textLabel.text = @"所在地区";
                cell.accessoryType = UITableViewCellAccessoryDisclosureIndicator;
                cell.detailTextLabel.text = _addressModel.address;
            }
                break;
            case 1:
            {
                cell.textLabel.text = @"详细交易地址";
                UITextField *textField = [UITextField textFieldWithPlaceHodler:@"填写" withDelegate:self];
                textField.frame = CGRectMake(140, 22-15, 150, 30);
                textField.textAlignment = NSTextAlignmentRight;
                [cell.contentView addSubview:textField];
            }
                break;
            case 2:
            {
                cell.imageView.image = nil;
                for (int i = 0; i < 3; i++) {
                    AddressImgModel *model;
                    if (_addressModel.addressImgModels.count > i) {
                        model = _addressModel.addressImgModels[i];
                    }
                    cell.selectionStyle = UITableViewCellSelectionStyleNone;
                    UIButton *imageBtn = [UIButton buttonWithTip:nil target:self selector:@selector(chosePhoto:)];
                    imageBtn.frame = CGRectMake(15+i*(260/3+15), 10, 260/3, 80);
                    imageBtn.tag = 100+i;
                    if (i == 0) {
                      [imageBtn setImage:[UIImage imageNamed:@"address_photo"] forState:UIControlStateNormal];
                    }else if (i == 1) {
                        [imageBtn setImage:[UIImage imageNamed:@"address_photo_add"] forState:UIControlStateNormal];
                    }else if (i == 2 ){
                        [imageBtn setImage:[UIImage imageNamed:@"address_photo_add"] forState:UIControlStateNormal];
                    }

                    if (model.thumbnailSmall) {
                        [imageBtn sd_setImageWithURL:[NSURL URLWithString:model.thumbnailSmall] forState:UIControlStateNormal];
                    }
                    
                    [cell addSubview:imageBtn];
                }

            }
                break;
            case 3:
            {
                cell.textLabel.text = @"卸货码头水深度(单位:米)";
                UITextField *textField = [UITextField textFieldWithPlaceHodler:@"填写" withDelegate:self];
                textField.frame = CGRectMake(140, 22-15, 150, 30);
                textField.textAlignment = NSTextAlignmentRight;
                [cell.contentView addSubview:textField];
                textField.text = [_addressModel.realdeep stringValue];
            }
                break;
            case 4:
            {
                cell.textLabel.text = @"可停泊载重船吨位(单位:吨)";
                UITextField *textField = [UITextField textFieldWithPlaceHodler:@"填写" withDelegate:self];
                textField.frame = CGRectMake(140, 22-15, 150, 30);
                textField.textAlignment = NSTextAlignmentRight;
                [cell.contentView addSubview:textField];
                textField.text = [_addressModel.deep stringValue];
            }
                break;
                
            default:
                break;
        }
    }else {
        cell.imageView.image = nil;
        cell.textLabel.textColor = [UIColor redColor];
        cell.textLabel.text = @"删除地址";
    }
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    if (indexPath.section == 0) {
        if (indexPath.row == 0) {
            
        }
    }
}

#define sectionHigh 10
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

#pragma mark - UIActions
/**
 *@brief 保存编辑
 */
- (void)doneAction {
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:_addressModel.id,@"id",_addressModel.address,@"address", nil];
    [self requestWithURL:bModifyUnloadAddress params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
    } failedBlock:^{
        
    }];
}

/**
 *@brief 选择实物图片
 */
static long photoTag = 0;
- (void)chosePhoto:(UIButton *)btn {
    UIActionSheet *choosePhotoActionSheet;
    photoTag = btn.tag-100;
    
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
    NSLog(@"##%@",info);
    [self dismissViewControllerAnimated:YES completion:^{
        UIImage *image = info[@"UIImagePickerControllerEditedImage"];
        UIButton *btn = (UIButton *)[self.view viewWithTag:100+photoTag];
        [btn setImage:image forState:UIControlStateNormal];

    }];
}

- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
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

@end
