//
//  TipSuccessViewController.m
//  Glshop
//
//  Created by River on 15-1-16.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "TipSuccessViewController.h"
#import "MypurseViewController.h"

@interface TipSuccessViewController ()

@end

@implementation TipSuccessViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)loadSubViews {
    [self loadSetPasswordSuccessView];
}

- (void)loadSetPasswordSuccessView {
    UIImageView *markView = [[UIImageView alloc] initWithFrame:CGRectMake(SCREEN_WIDTH/2-80/2, 80, 80, 80)];
    markView.image = PNGIMAGE(@"success@2x");
    [self.view addSubview:markView];
    
    UILabel *tipLabel = [UILabel labelWithTitle:@"提交成功！"];
    tipLabel.frame = CGRectMake(0, markView.vbottom+20, SCREEN_WIDTH, 25);
    tipLabel.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:tipLabel];
    
    UILabel *detailLabel = [UILabel labelWithTitle:@"等待客服审核通过后即可进行提现，敬请留意"];
    detailLabel.frame = CGRectMake(20, tipLabel.vbottom+20, SCREEN_WIDTH-40, 45);
    detailLabel.numberOfLines = 2;
    detailLabel.textColor = [UIColor lightGrayColor];
    detailLabel.textAlignment = NSTextAlignmentCenter;
    [self.view addSubview:detailLabel];
    
    UIButton *sureBtn = [UIFactory createBtn:BlueButtonImageName bTitle:@"查看我的钱包" bframe:CGRectMake(detailLabel.vleft, detailLabel.vbottom+30, detailLabel.vwidth, 40)];
    [sureBtn addTarget:self action:@selector(skipToMypurse) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:sureBtn];
    
}

- (void)skipToMypurse {
    MypurseViewController *vc = [self findDesignatedViewController:[MypurseViewController class]];
    [self.navigationController popToViewController:vc animated:YES];
}

@end
