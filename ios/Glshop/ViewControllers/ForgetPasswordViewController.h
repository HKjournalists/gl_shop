//
//  ForgetPasswordViewController.h
//  Glshop
//
//  Created by River on 14-11-7.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "BaseViewController.h"

@interface ForgetPasswordViewController : BaseViewController
{
@private
    UITextField *_userNameField;
    UITextField *_codeField;
    UIImageView *_codeImageView;
    UIButton    *_refrushBtn;
    UIButton    *_nextBtn;
    UIView      *_tipView;
}

@end
