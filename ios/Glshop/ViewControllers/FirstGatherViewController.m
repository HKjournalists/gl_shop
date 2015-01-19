//
//  FirstGatherViewController.m
//  Glshop
//
//  Created by River on 15-1-13.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "FirstGatherViewController.h"
#import "PhotoUploadView.h"
#import "ClickImage.h"
#import "SecondGatherViewController.h"

@interface FirstGatherViewController () <UploadImageDelete>

@property (nonatomic, strong) PhotoUploadView *photoView;
@property (nonatomic, strong) UITextField *tfName;

@end

@implementation FirstGatherViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"新增收款人";
}

- (void)loadSubViews {
    UIImageView *imgView = [[UIImageView alloc] initWithFrame:CGRectZero];
    UIImage *img = [UIImage imageNamed:@"wallet_beijing"];
    img = [img resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
    imgView.image = img;
    imgView.userInteractionEnabled = YES;
    [self.view addSubview:imgView];
    
    UserInstance *uInstance = [UserInstance sharedInstance];
    float height = uInstance.userType == user_personal ? 44 : 150;
    [imgView makeConstraints:^(MASConstraintMaker *make) {
        make.width.mas_equalTo(self.view);
        make.leading.mas_equalTo(self.view);
        make.height.mas_equalTo(height);
        make.top.mas_equalTo(self.view).offset(30);
    }];
    
    UILabel *labelName = [UILabel labelWithTitle:@"收款人姓名"];
    [imgView addSubview:labelName];
    [labelName makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(imgView).offset(15);
        make.height.mas_equalTo(44);
        make.width.mas_equalTo(80);
        make.top.mas_equalTo(imgView);
    }];
    
    UIView *line = [[UIView alloc] init];
    line.backgroundColor = RGB(230, 230, 230, 1);
    [imgView addSubview:line];
    [line makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(imgView);
        make.right.mas_equalTo(imgView);
        make.height.mas_equalTo(0.5);
        make.top.mas_equalTo(labelName.bottom).offset(-1);
    }];
    
    _tfName = [UITextField textFieldWithPlaceHodler:@"请输入" withDelegate:self];
    [imgView addSubview:_tfName];
    _tfName.textAlignment = NSTextAlignmentRight;
    [_tfName makeConstraints:^(MASConstraintMaker *make) {
        make.right.mas_equalTo(imgView).offset(-10);
        make.leading.mas_equalTo(labelName.right).offset(10);
        make.height.mas_equalTo(labelName);
        make.top.mas_equalTo(labelName);
        
    }];
    
    if (uInstance.userType == user_personal) {
        UIButton *nextBtn = [UIFactory createBtn:BlueButtonImageName bTitle:@"下一步" bframe:CGRectZero];
        [nextBtn addTarget:self action:@selector(next) forControlEvents:UIControlEventTouchUpInside];
        [self.view addSubview:nextBtn];
        [nextBtn makeConstraints:^(MASConstraintMaker *make) {
            make.leading.mas_equalTo(self.view).offset(10);
            make.height.mas_equalTo(40);
            make.top.mas_equalTo(imgView.bottom).offset(20);
            make.right.mas_equalTo(self.view).offset(-10);
        }];
        return;
    }
    
    _photoView = [[PhotoUploadView alloc] initWithFrame:CGRectZero];
    _photoView.NotshowNextPhoto = YES;
    _photoView.defaultImgName = @"wallet_photo";
    _photoView.delegate = self;
    [imgView addSubview:_photoView];
    [_photoView makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(imgView);
        make.height.mas_equalTo(100);
        make.top.mas_equalTo(line.bottom).offset(5);
        make.width.mas_equalTo(SCREEN_WIDTH);
    }];
 
    ClickImage *clickView = [[ClickImage alloc] initWithFrame:CGRectZero];
    clickView.image = [UIImage imageNamed:@"wallet_photos_exm"];
    clickView.showImg = [UIImage imageNamed:@"bg_demo_payee.jpg"];
    clickView.canClick = YES;
    [imgView addSubview:clickView];
    [clickView makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(labelName.right).offset(15);
        make.size.mas_equalTo(CGSizeMake(80, 80));
        make.top.mas_equalTo(_photoView);
    }];
    
    UIButton *nextBtn = [UIFactory createBtn:BlueButtonImageName bTitle:@"下一步" bframe:CGRectZero];
    [nextBtn addTarget:self action:@selector(next) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:nextBtn];
    [nextBtn makeConstraints:^(MASConstraintMaker *make) {
        make.leading.mas_equalTo(self.view).offset(10);
        make.height.mas_equalTo(40);
        make.top.mas_equalTo(imgView.bottom).offset(20);
        make.right.mas_equalTo(self.view).offset(-10);
    }];
}

#pragma mark - UIAction
- (void)next {
    UserInstance *uInstance = [UserInstance sharedInstance];
    if (!_tfName.text.length) {
        HUD(@"请输入收款人姓名");
        return;
    }
    
    if (uInstance.userType == user_personal) {
        SecondGatherViewController *vc = [[SecondGatherViewController alloc] init];
        vc.name = _tfName.text;
        [self.navigationController pushViewController:vc animated:YES];
    }else {
        if (!_photoView.imageArray.count) {
            HUD(@"请上传认证照片");
            return;
        }
        [self showHUD:nil isDim:NO Yoffset:0];
        [_photoView uploadImage];
    }
    
}

#pragma mark - UploadImageDelete
- (void)uploadImageSuccess:(NSString *)imgsId uploadView:(PhotoUploadView *)uploadView {
    [self hideHUD];
    SecondGatherViewController *vc = [[SecondGatherViewController alloc] init];
    vc.imgId = imgsId;
    vc.name = _tfName.text;
    [self.navigationController pushViewController:vc animated:YES];
}

- (void)uploadImageFaile:(PhotoUploadView *)uploadView {
    [self hideHUD];
    HUD(kNetError);
}

@end
