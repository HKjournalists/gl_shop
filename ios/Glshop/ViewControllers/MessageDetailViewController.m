//
//  MessageDetailViewController.m
//  Glshop
//
//  Created by River on 15-2-27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "MessageDetailViewController.h"
#import "ContractProcessDetailViewController.h"
#import "ContractDetailViewController.h"
#import "MessageModel.h"
#import "MyContractViewController.h"
#import "ContractWaitSureViewController.h"
#import "ContractPorccesingViewController.h"
#import "ContractEndedViewController.h"

@interface MessageDetailViewController ()

@property (strong, nonatomic) IBOutlet UILabel *typeLabel;
@property (strong, nonatomic) IBOutlet UILabel *timeLabel;
@property (strong, nonatomic) IBOutlet UILabel *contentLabel;
@property (strong, nonatomic) IBOutlet UIButton *seeBtn;

@end

@implementation MessageDetailViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.title = @"消息详情";
}

- (void)loadSubViews {
    _typeLabel.text = _messageModel.type[DataTextKey];
    _timeLabel.text = _messageModel.createtime;
    _contentLabel.text = _messageModel.content;
    
    UIColor *color = [UIColor colorWithRed:153/255.0 green:153/255.0 blue:153/255.0 alpha:1.0];
    UIColor *ccolor = [UIColor colorWithRed:51/255.0 green:51/255.0 blue:51/255.0 alpha:1.0];
    
    _typeLabel.textColor = color;
    _timeLabel.textColor = color;
    _contentLabel.textColor = ccolor;
    
    UIImage *image = [UIImage imageNamed:BlueButtonImageName];
    image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
    [_seeBtn setBackgroundImage:image forState:UIControlStateNormal];
    
    [_seeBtn addTarget:self action:@selector(goContacts:) forControlEvents:UIControlEventTouchUpInside];
    
    BOOL isHidde = YES;
    NSInteger businessType = [_messageModel.businesstype[DataValueKey] integerValue];
    if(businessType >= 400 && businessType <= 414){
        isHidde = NO;
    }
    _seeBtn.hidden = isHidde;
    
    
}

- (void)goContacts:(UIButton *)btn {
    NSInteger type = [_messageModel.businesstype[DataValueKey] integerValue];
    if (type == 407 || type == 406 || type == 405 || type == TYPE_CONTRACT_MAKE_MATCH) {
        ContractDetailViewController *vc = [[ContractDetailViewController alloc] init];
        vc.contractId = _messageModel.businessid;
        [self.navigationController pushViewController:vc animated:YES];
    }else {
        ContractProcessDetailViewController *vc = [[ContractProcessDetailViewController alloc] init];
        vc.contractId = _messageModel.businessid;
        [self.navigationController pushViewController:vc animated:YES];
    }
    
}

@end
