//
//  RegisterViewController.m
//  Glshop
//
//  Created by River on 14-11-7.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "RegisterViewController.h"
#import "HLCheckbox.h"
#import "SettingPasswordViewController.h"

@interface RegisterViewController () <UITextFieldDelegate>

@property (nonatomic, strong) UITextField *phoneTextfield;
@property (nonatomic, strong) UILabel *agreeLabel;
@property (nonatomic, strong) UIButton *nextBtn;
@property (nonatomic, strong) HLCheckbox  *box;

@end

@implementation RegisterViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    
    [_phoneTextfield becomeFirstResponder];
}

- (void)loadSubViews {
    
    UIImageView *textFieldBG = [[UIImageView alloc] initWithFrame:CGRectMake(0, 15, self.view.vwidth, 44)];
    textFieldBG.image = [UIImage imageNamed:@"白色列表一条"];
    textFieldBG.userInteractionEnabled = YES;
    [self.view addSubview:textFieldBG];
    
    _phoneTextfield = [UITextField textFieldWithImageName:@"loginPhone" placeHodler:@"请输入手机号码" withDelegate:self];
    _phoneTextfield.frame = CGRectMake(0, 0, textFieldBG.vwidth, textFieldBG.vheight);
    _phoneTextfield.keyboardType = UIKeyboardTypeNumberPad;
    _phoneTextfield.returnKeyType = UIReturnKeyDone;
    [textFieldBG addSubview:_phoneTextfield];
    
    _box = [[HLCheckbox alloc] initWithBoxImage:[UIImage imageNamed:@"check_unselected"] selectImage:[UIImage imageNamed:@"check_selected"]];
    _box.frame = CGRectMake(10, textFieldBG.vbottom+30, 20, 20);
    _box.selected = YES;
    [self.view addSubview:_box];
    
    self.agreeLabel = [UILabel labelWithTitle:@"同意"];
    _agreeLabel.frame = CGRectMake(_box.vright+2, _box.vtop, 40, _box.vheight);
    [self.view addSubview:_agreeLabel];
    
    UIButton *proBtn = [UIButton buttonWithTip:@"长江电商用户协议" target:self selector:@selector(showProtocal)];
    [proBtn setTitleColor:ColorWithHex(@"#507daf") forState:UIControlStateNormal];
    proBtn.frame = CGRectMake(_agreeLabel.vright, _agreeLabel.vtop+1, 150, 20);
    proBtn.titleLabel.font = _agreeLabel.font;
    [self.view addSubview:proBtn];
    
    _nextBtn = [UIButton buttonWithTip:@"下一步" target:self selector:@selector(setPassword)];
    _nextBtn.frame = CGRectMake(10, proBtn.vbottom+20, self.view.vwidth-20, 40);
    _nextBtn.layer.cornerRadius = 2.5f;
    _nextBtn.enabled = NO;
    [_nextBtn setTitleColor:ColorWithHex(@"4987D9") forState:UIControlStateDisabled];
    _nextBtn.backgroundColor = CJBtnColor;
    [self.view addSubview:_nextBtn];
    
    __weak typeof(self) weakSelf = self;
    _box.tapBlock = ^(BOOL selected) {
        if (weakSelf.phoneTextfield.text.length > 0) {
            
            weakSelf.nextBtn.enabled = !selected;
        }
        weakSelf.agreeLabel.textColor = !selected ? [UIColor blackColor] : ColorWithHex(@"#999999");
    };

}

#pragma mark - UIActions
- (void)showProtocal {
    
}

/**
 *@brief 点击后，进行密码设置
 */
- (void)setPassword {
    if (_phoneTextfield.text.length <= 0) {
        HUD(@"请输入手机号");
        return;
    }
    
    if (![Utilits isMobilePhone:_phoneTextfield.text]) {
        HUD(@"手机格式错误！");
        return;
    }
    
    SettingPasswordViewController *vc = [mainStoryBoard instantiateViewControllerWithIdentifier:@"SettingPasswordViewControllerId"];
    vc.phone = _phoneTextfield.text;
    [self.navigationController pushViewController:vc animated:YES];
}

#pragma mark - UITextField Delegate
- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string {
    if ((string.length > 0 && _box.isSelected) || (textField.text.length > 1 && _box.isSelected)) {
        _nextBtn.enabled = YES;
    }else {
        _nextBtn.enabled = NO;
    }
    return YES;
}
@end

