//
//  SettingPasswordViewController.h
//  Glshop
//
//  Created by River on 14-11-7.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "BaseViewController.h"

typedef NS_ENUM(NSInteger, OperationType) {
    RegisterSetpassWord,  // 注册设置密码
    ForgetSetPassword,    // 找回密码
    ModifySetPassword,    // 修改密码
};

@interface SettingPasswordViewController : BaseViewController
{
@private
    UITextField *_codeTextfield;
    UITextField *_pwTextField;
    UITextField *_rePwTextField;
    UITextField *_phoneTextField;
    UITextField *_oldPWTextField;
    UIButton    *btnVerifCode;
    
}

@property (nonatomic, copy) NSString *phone; // 手机号
@property (nonatomic, assign) OperationType operation;

@end
