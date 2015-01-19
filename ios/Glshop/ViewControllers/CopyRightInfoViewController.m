//
//  CopyRightInfoViewController.m
//  Glshop
//
//  Created by River on 15-1-4.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "CopyRightInfoViewController.h"
#import "REPlaceholderTextView.h"
#import "PhotoUploadView.h"
#import "ProfileViewController.h"
#import "AddressImgModel.h"
#import "JSONKit.h"
#import "ContactModel.h"
#import "AuthModel.h"
#import "AuthViewController.h"

@interface CopyRightInfoViewController () <UITableViewDataSource,UITableViewDelegate,UploadImageDelete>

@property (nonatomic, strong) REPlaceholderTextView *textView;
@property (nonatomic, strong) PhotoUploadView *photoView;
@property (nonatomic, strong) ContactModel *contact;
@property (nonatomic, strong) UITableView *tableView;

@end

@implementation CopyRightInfoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    UIBarButtonItem *rightItem = [[UIBarButtonItem alloc] initWithTitle:@"保存" style:UIBarButtonItemStylePlain target:self action:@selector(doneAction)];
    self.navigationItem.rightBarButtonItem = rightItem;
    
    [self fillData];
    
    [self requestNet];
}

- (void)requestNet {
    if (_opentionType == Add_Contact) {
        _tableView.hidden = YES;
        UserInstance *uInstance = [UserInstance sharedInstance];
        NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObject:uInstance.user.cid forKey:@"cid"];
        __block typeof(self) this = self;
        [self.view showWithTip:nil];
        [self requestWithURL:bCompanyContactListPath params:params HTTPMethod:kHttpGetMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
            kASIResultLog;
            [this handleNetData:responseData];
        } failedBlock:^(ASIHTTPRequest *request) {
            this.tableView.hidden = NO;
        }];
    }
}

- (void)loadSubViews {
    if (_opentionType == Fill_Brief) {
        _textView = [[REPlaceholderTextView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 160)];
        _textView.placeholder = @"请输入企业相关简介";
        [self.view addSubview:_textView];
        [_textView becomeFirstResponder];
    }else if (_opentionType == Upload_Image) {
        UILabel *tip = [UILabel labelWithTitle:@"上传企业照片"];
        tip.frame = CGRectMake(5, 5, 200, 20);
        tip.font = [UIFont systemFontOfSize:13.f];
        [self.view addSubview:tip];
        
        UIView *bgView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 100)];
        bgView.backgroundColor = [UIColor whiteColor];
        _photoView = [[PhotoUploadView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, 100)];
        _photoView.delegate = self;
        [bgView addSubview:_photoView];
        [self.view addSubview:bgView];
    }else if (_opentionType == Add_Contact) {
        _tableView = [[UITableView alloc] initWithFrame:self.view.bounds style:UITableViewStyleGrouped];
        _tableView.vheight -= kTopBarHeight;
        _tableView.dataSource = self;
        _tableView.delegate   = self;
        [self.view addSubview:_tableView];
    }
}

- (void)handleNetData:(id)responseData {
    _tableView.hidden = NO;
    NSArray *contactsDic = responseData[ServiceDataKey];
    if (contactsDic.count > 0) {
        NSDictionary *dic = contactsDic.firstObject;
        self.contact = [[ContactModel alloc] initWithDataDic:dic];
    }
}

#pragma mark - Setter
- (void)setContact:(ContactModel *)contact {
    _contact = contact;
    
    UITextField *filed1 = (UITextField *)[self.view viewWithTag:101];
    UITextField *filed2 = (UITextField *)[self.view viewWithTag:102];
    UITextField *filed3 = (UITextField *)[self.view viewWithTag:103];
    
    [filed1 becomeFirstResponder];
    filed1.text = _contact.cname;
    filed2.text = _contact.cphone;
    filed3.text = _contact.tel;
}

- (void)setOpentionType:(Opention_type)opentionType {
    _opentionType = opentionType;
    if (_opentionType == Add_Contact) {
        self.title = @"联系人信息";
    }else if (_opentionType == Fill_Brief) {
        self.title = @"企业简介";
    }else {
        self.title = @"企业照片";
    }
}

#pragma mark - UITableView
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 4;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:nil];
    cell.textLabel.font = [UIFont systemFontOfSize:14.f];
    
    NSArray *texts = @[@"联系信息",@"姓名",@"手机",@"电话",];
    cell.textLabel.text = texts[indexPath.row];
    if (indexPath.row) {
        UITextField *filed = [[UITextField alloc] initWithFrame:CGRectMake(cell.vright-160, 0, 160, cell.vheight)];
        filed.tag = 100+indexPath.row;
        filed.textAlignment = NSTextAlignmentRight;
        filed.placeholder = @"填写";
        [cell.contentView addSubview:filed];
        
        if (indexPath.row == 2) {
            filed.keyboardType = UIKeyboardTypeNumberPad;
        }else if (indexPath.row == 3) {
            filed.keyboardType = UIKeyboardTypeNumbersAndPunctuation;
        }
    }
    
    if (indexPath.row == 0) {
        UILabel *tip = [UILabel labelWithTitle:@"(*必填)"];
        tip.frame = CGRectMake(70, 0, 100, cell.vheight);
        tip.font = [UIFont systemFontOfSize:12.f];
        tip.textColor = [UIColor redColor];
        [cell addSubview:tip];
    }else if (indexPath.row == 1) {
        cell.imageView.image = [UIImage imageNamed:@"attestation_icon_name_xin"];
    }else if (indexPath.row == 2) {
        cell.imageView.image = [UIImage imageNamed:@"attestation_icon_cellphone_xin"];
    }else if (indexPath.row == 3) {
        cell.imageView.image = [UIImage imageNamed:@"attestation_icon_phone_xin"];
    }
    
    return cell;
}

#pragma mark - Private
- (ProfileViewController *)profileVC {
    for (UIViewController *vc in self.navigationController.viewControllers) {
        if ([vc isKindOfClass:[ProfileViewController class]]) {
            return (ProfileViewController *)vc;
        }
    }
    return nil;
}

- (void)fillData {
    switch (_opentionType) {
        case Add_Contact:
        {
            
        }
            break;
        case Fill_Brief:
        {
            ProfileViewController *vc = [self profileVC];
            if (vc.cModel.mark) {
                _textView.text = vc.cModel.mark;
            }
        }
            break;
        case Upload_Image:
        {
            ProfileViewController *vc = [self profileVC];
            if (vc.cModel.companyImgList.count) { // 如果有实物图片
                NSMutableArray *temp = [NSMutableArray array];
                for (AddressImgModel *model in vc.cModel.companyImgList) {
                    [temp addObject:model.thumbnailSmall];
                }
                _photoView.imageUrlArray = [NSArray arrayWithArray:temp];
            }
        }
            break;
        default:
            break;
    }
}

- (void)tipSaveSuccess {
    HUD(@"保存成功");
    [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushCompanyInfoNotification object:nil];
    [self.navigationController popViewControllerAnimated:YES];
}


#pragma mark - UIActions
- (void)doneAction {
    UserInstance *userInstance = [UserInstance sharedInstance];
    if (_opentionType == Fill_Brief) {
        if (!_textView.text.length) {
            HUD(@"请输入企业相关简介");
            return;
        }else {
            NSMutableDictionary *params = [NSMutableDictionary dictionary];
            [params addString:userInstance.user.cid forKey:@"cid"];
            [params addString:_textView.text forKey:@"mark"];
            ProfileViewController *vc = [self profileVC];
            
            if (vc.cModel.companyImgList) {
                NSMutableArray *temp = [NSMutableArray arrayWithCapacity:vc.cModel.companyImgList.count];
                for (AddressImgModel *model in vc.cModel.companyImgList) {
                    [temp addObject:model.id];
                }
                [params addString:[temp componentsJoinedByString:@","] forKey:@"companyImgIds"];
            }
            
            [self requestWithURL:bUpdateCompanyInfoPath
                          params:params
                      HTTPMethod:kHttpPostMethod
                   completeBlock:^(ASIHTTPRequest *request, id responseData) {
                       [self tipSaveSuccess];
            } failedBlock:^(ASIHTTPRequest *request) {
                
            }];
        }
    }else if (_opentionType == Upload_Image) {
        if (_photoView.imageArray.count <= 0) {
            HUD(@"请选择企业照片");
            return;
        }else {
            [self showHUD:@"正在保存..." isDim:NO Yoffset:0];
            [_photoView uploadImage];
        }
    }else if (_opentionType == Add_Contact) {
        UITextField *filed1 = (UITextField *)[self.view viewWithTag:101];
        UITextField *filed2 = (UITextField *)[self.view viewWithTag:102];
        UITextField *filed3 = (UITextField *)[self.view viewWithTag:103];
        
        if (!filed1.text.length || !filed2.text.length || !filed3.text.length) {
            [self showTip:@"请将信息填写完整"];
            return;
        }else {
            NSMutableDictionary *param = [NSMutableDictionary dictionaryWithObjectsAndKeys:filed1.text,@"cname",filed2.text,@"cphone",filed3.text,@"tel", @"1",@"status",nil];
            if (_contact.compId.length) {
                [param addString:_contact.compId forKey:@"id"];
            }
            NSArray *temp = @[param];
            DLog(@"%@",[temp JSONString]);
            NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:[temp JSONString],@"contactList",userInstance.user.cid,@"cid", nil];
            [self requestWithURL:bSaveContactPath
                          params:params
                      HTTPMethod:kHttpPostMethod
                   completeBlock:^(ASIHTTPRequest *request, id responseData) {
                kASIResultLog;
                       if (_authModel) {
                           _authModel.contact = filed1.text;
                           _authModel.phone = filed2.text;
                           _authModel.tel = filed3.text;
                           AuthViewController *vc = (AuthViewController *)self.navigationController.viewControllers[2];
                           [vc.tableView reloadData];
                       }
                       [self tipSaveSuccess];
            } failedBlock:^(ASIHTTPRequest *request) {
                DLog(@"failed");
            }];
        }
    }
}

#pragma mark - UploadImageDelete

- (void)uploadImageSuccess:(NSString *)imgsId uploadView:(PhotoUploadView *)uploadView {
    [self hideHUD];
    ProfileViewController *vc = [self profileVC];
    
    UserInstance *userInstance = [UserInstance sharedInstance];
    NSMutableDictionary *params = [NSMutableDictionary dictionary];
    [params addString:userInstance.user.cid forKey:@"cid"];
    if (vc.cModel.mark) {
        [params addString:vc.cModel.mark forKey:@"mark"];
    }
    [params addString:imgsId forKey:@"companyImgIds"];
    
    
    [self requestWithURL:bUpdateCompanyInfoPath
                  params:params
              HTTPMethod:kHttpPostMethod
           completeBlock:^(ASIHTTPRequest *request, id responseData) {
        kASIResultLog;
               [self tipSaveSuccess];
    } failedBlock:^(ASIHTTPRequest *request) {
        
    }];
}

- (void)uploadImageFaile:(PhotoUploadView *)uploadView {
    [self hideHUD];
    
}

@end
