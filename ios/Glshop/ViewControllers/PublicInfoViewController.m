//
//  PublicInfoViewController.m
//  Glshop
//
//  Created by River on 14-12-2.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "PublicInfoViewController.h"
#import "CompanyAuthViewController.h"

@interface PublicInfoViewController ()

@end

@implementation PublicInfoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)loadSubViews {
    UILabel *tipLabel = [UILabel labelWithTitle:@"您正在发布信息，您可以选择致电平台客服免费帮您发布信息或自己手动输入发布信息。"];
    tipLabel.numberOfLines = 3;
    tipLabel.textAlignment = NSTextAlignmentCenter;
    tipLabel.frame = CGRectMake(15, 30, self.view.vwidth-30, 80);
    tipLabel.font = [UIFont boldSystemFontOfSize:18.f];
    [self.view addSubview:tipLabel];
    
    UIButton *btnPhone = [UIButton buttonWithTip:@"致电平台客服" target:self selector:nil];
    btnPhone.frame = CGRectMake(10, tipLabel.vbottom+30, self.view.vwidth-20, 40);
    btnPhone.backgroundColor = [UIColor orangeColor];
    [self.view addSubview:btnPhone];
    
    UIButton *btnPublic = [UIButton buttonWithTip:@"自己手动发布信息" target:self selector:@selector(publicInfo:)];
    btnPublic.frame = CGRectMake(btnPhone.vleft, btnPhone.vbottom+20, btnPhone.vwidth, btnPhone.vheight);
    btnPublic.backgroundColor = CJBtnColor;
    [self.view addSubview:btnPublic];
}

- (void)publicInfo:(UIButton *)button {
    CompanyAuthViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"CompanyAuthViewControllerId"];
    [self.navigationController pushViewController:vc animated:YES];
}

@end
