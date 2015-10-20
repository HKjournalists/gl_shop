//
//  UserInfoView.m
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/28.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "UserInfoView.h"
#import "UILabel+adjustText.h"
#import "UIButton+imageAndTitle.h"
#import "CustomTextField.h"



#define BUTTONHEIGHT 28
#define BUTTONWIDTH 114
#define EDGEINSETS UIEdgeInsetsMake(0, 10, 0, 0)
#define TEXTFIELDWIDTH 186
#define REMARKTEXTFIELDHEIGHT 51

typedef enum {
    
    CustomTextFieldName = 100,
    CustomTextFieldPhone,
    CustomTextFieldReqNum,
    CustomTextFieldReMark,

}CustomTextFieldStyle;

@interface UserInfoView ()<UITextViewDelegate,UITextFieldDelegate>

@property(nonatomic,strong)UILabel *descriptionText_1;
@property(nonatomic,strong)UILabel *descriptionText_2;
@property(nonatomic,strong)UILabel *descriptionText_3;
@property(nonatomic,strong)CustomTextField *nameTF;
@property(nonatomic,strong)CustomTextField *phoneTF;
@property(nonatomic,strong)CustomTextField *reqNumTF;
@property(nonatomic,strong)UITextView *remarkTextView;
@property(nonatomic,strong)UIButton *joinBtn;

@end

@implementation UserInfoView

- (void)loadSubViews
{
    [self loadHeadTitleView];
    
    _descriptionText_1 = [UILabel createLabelWith:CGSizeMake(CGRectGetWidth(self.frame),50) text:description_1 textColor:GROUPBUYWHITECOLOR textFont:GROUPBUYTEXTFONT point:CGPointMake(CGRectGetMinX(self.titleLabel.frame), CGRectGetMaxY(self.lineView.frame)+10)];
    
    [self addSubview:_descriptionText_1];
    
    _descriptionText_2 = [UILabel createLabelWith:CGSizeMake(CGRectGetWidth(self.frame), 50) text:description_2 textColor:GROUPBUYREDTEXTCOLOR backGroundColor:GROUPBUYTEXTYELLOWCOLOR textFont:GROUPBUYTEXTFONT point:CGPointMake(CGRectGetMaxX(_descriptionText_1.frame), CGRectGetMinY(_descriptionText_1.frame))];
    [self addSubview:_descriptionText_2];
    
    _descriptionText_3 = [UILabel createLabelWith:CGSizeMake(CGRectGetWidth(self.frame), 50) text:description_3 textColor:GROUPBUYWHITECOLOR textFont:GROUPBUYTEXTFONT point:CGPointMake(CGRectGetMaxX(_descriptionText_2.frame), CGRectGetMinY(_descriptionText_2.frame))];
    [self addSubview:_descriptionText_3];

    
    [self loadUserInfoView];
    
    self.userInteractionEnabled = YES;
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapAtView)];
    [self addGestureRecognizer:tap];
}
- (void)loadUserInfoView
{
    CGRect nameBtnFrame = CGRectMake(CGRectGetMinX(self.titleLabel.frame), CGRectGetMaxY(_descriptionText_1.frame)+20, BUTTONWIDTH, BUTTONHEIGHT);
    
    UIButton *nameBtn = [UIButton buttonWith:nameBtnFrame title:globe_name_str normalimage:@"tx_icon_name" selectedImage:nil userInteractionEnabled:NO edgeInset:EDGEINSETS titleFont:GROUPBUYTEXTFONT];
    
    [nameBtn setContentHorizontalAlignment:UIControlContentHorizontalAlignmentLeft];
    [self addSubview:nameBtn];
    
    _nameTF = [[CustomTextField alloc] initWithFrame:CGRectMake(CGRectGetMaxX(nameBtnFrame), CGRectGetMinY(nameBtnFrame), TEXTFIELDWIDTH, BUTTONHEIGHT)];
    [_nameTF setTextFieldStyleWith:@"请输入"];
    _nameTF.delegate = self;
    _nameTF.tag = CustomTextFieldName;
    [self addSubview:_nameTF];
    
    
    
    CGRect phoneBtnFrame = CGRectMake(CGRectGetMinX(nameBtnFrame), CGRectGetMaxY(nameBtnFrame)+7, BUTTONWIDTH, BUTTONHEIGHT);
    UIButton *phoneBtn = [UIButton buttonWith:phoneBtnFrame title:globe_phoneNum_str normalimage:@"tx_icon_phone" selectedImage:nil userInteractionEnabled:NO edgeInset:EDGEINSETS titleFont:GROUPBUYTEXTFONT];
    [phoneBtn setContentHorizontalAlignment:UIControlContentHorizontalAlignmentLeft];
    [self addSubview:phoneBtn];
    
    _phoneTF = [[CustomTextField alloc] initWithFrame:CGRectMake(CGRectGetMaxX(phoneBtn.frame), CGRectGetMinY(phoneBtn.frame), CGRectGetWidth(_nameTF.frame), CGRectGetHeight(_nameTF.frame))];
    _phoneTF.delegate = self;
    [_phoneTF setTextFieldStyleWith:@"请输入"];

    [self addSubview:_phoneTF];
    _phoneTF.tag = CustomTextFieldPhone;
    
    CGRect reqNumFrame = CGRectMake(CGRectGetMinX(phoneBtnFrame), CGRectGetMaxY(phoneBtnFrame)+7, BUTTONWIDTH, BUTTONHEIGHT);
    UIButton *reqNumBtn = [UIButton buttonWith:reqNumFrame title:globe_reqNum_str normalimage:@"tx_icon_reqnum" selectedImage:nil userInteractionEnabled:NO edgeInset:EDGEINSETS titleFont:GROUPBUYTEXTFONT];
    [reqNumBtn setContentHorizontalAlignment:UIControlContentHorizontalAlignmentLeft];
    [self addSubview:reqNumBtn];
    
    _reqNumTF = [[CustomTextField alloc] initWithFrame:CGRectMake(CGRectGetMaxX(reqNumFrame), CGRectGetMinY(reqNumBtn.frame), CGRectGetWidth(_phoneTF.frame), CGRectGetHeight(_phoneTF.frame))];
    _reqNumTF.delegate = self;
    [_reqNumTF setTextFieldStyleWith:@"请输入(单位:吨)"];
    [self addSubview:_reqNumTF];
    _reqNumTF.tag = CustomTextFieldReqNum;
    
    
    CGRect remarkFrame = CGRectMake(CGRectGetMinX(reqNumFrame), CGRectGetMaxY(reqNumFrame)+7, BUTTONWIDTH, BUTTONHEIGHT);
    UIButton *remarkBtn = [UIButton buttonWith:remarkFrame title:globe_remark_str normalimage:@"tx_icon_remark" selectedImage:nil userInteractionEnabled:NO edgeInset:EDGEINSETS titleFont:GROUPBUYTEXTFONT];
    [remarkBtn setContentHorizontalAlignment:UIControlContentHorizontalAlignmentLeft];
    [self addSubview:remarkBtn];
    
    
    _remarkTextView = [[UITextView alloc] initWithFrame:CGRectMake(CGRectGetMaxX(remarkBtn.frame), CGRectGetMinY(remarkBtn.frame), CGRectGetWidth(_reqNumTF.frame), REMARKTEXTFIELDHEIGHT)];
    [self addSubview:_remarkTextView];
    _remarkTextView.layer.cornerRadius = 5.0;
    _remarkTextView.backgroundColor = GROUPBUYWHITECOLOR;
    _remarkTextView.delegate = self;
    _remarkTextView.showsHorizontalScrollIndicator = NO;
    [self addSubview:_remarkTextView];
    
    _textViewPlacehoder = [UILabel createLabelWith:CGSizeMake(CGRectGetWidth(_remarkTextView.frame), CGRectGetHeight(_remarkTextView.frame)) text:remarkPlacehoder_str textColor:[UIColor colorWithRed:136/255.0 green:136/255.0 blue:136/255.0 alpha:1.0] textFont:GROUPBUYTEXTFONT point:CGPointMake(5, 5)];
    _textViewPlacehoder.enabled = NO;
    _textViewPlacehoder.backgroundColor = [UIColor clearColor];
    [_remarkTextView addSubview:_textViewPlacehoder];
    
    _joinBtn = [UIButton buttonWithType:UIButtonTypeCustom];

    _joinBtn.frame = CGRectMake((CGRectGetWidth(self.frame)-135)/2, CGRectGetMaxY(_remarkTextView.frame)+10, 135, 43);
    [_joinBtn setBackgroundImage:[UIImage imageNamed:@"button"] forState:UIControlStateNormal];
    [_joinBtn setBackgroundImage:[UIImage imageNamed:@"button_touch"] forState:UIControlStateHighlighted];
    [_joinBtn addTarget:self action:@selector(_joinBtnClick) forControlEvents:UIControlEventTouchUpInside];
    [self addSubview:_joinBtn];
}

- (void)tapAtView
{
    [self endEditing:YES];

}

- (void)textFieldDidBeginEditing:(UITextField *)textField
{
    
    if (textField.tag == CustomTextFieldReqNum || textField.tag == CustomTextFieldPhone) {
        textField.keyboardType = UIKeyboardTypeNumberPad;
        
    }

    

}
#pragma  mark -textViewdelegate
- (void)textViewDidChange:(UITextView *)textView
{
    self.textViewPlacehoder.text = @"";

}
- (void)textViewDidEndEditing:(UITextView *)textView
{
   
        if (textView.text.length != 0) {
        
            if (textView.text.length >= 200) {
                textView.editable = NO;
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"友情提示" message:@"备注信息不能超过200字" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil, nil];
                [alert show];
                
                NSString *remarkInfo = [textView.text substringToIndex:200];
                textView.text = remarkInfo;
            
                textView.editable = YES;
            }
            
         
            
        }else{
            self.textViewPlacehoder.text = remarkPlacehoder_str;
        }
        
        
    

}
- (void)textFieldDidEndEditing:(UITextField *)textField
{
    if (textField.tag == CustomTextFieldPhone) {
        if (textField.text.length > 11) {
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"提醒" message:@"电话号不能超过11位" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil, nil];
            [alert show];
            textField.text = [textField.text substringToIndex:11];
        }
    }
    
}
#pragma mark -
- (void)_joinBtnClick
{
    if ([self.phoneTF.text isEqualToString:@""]||[self.nameTF.text isEqualToString:@""]||[self.reqNumTF.text isEqualToString:@""]) {
        
       HUD(@"请完善信息后继续操作");
    
    }else{
    
       
        NSNumber *reqNum = [NSNumber numberWithFloat:[self.reqNumTF.text floatValue]];
        
        NSMutableDictionary *param = [NSMutableDictionary dictionaryWithObjectsAndKeys:self.phoneTF.text,@"phone",self.nameTF.text,@"name",reqNum,@"reqnum",self.remarkTextView.text,@"remark", nil];
        
        [self.delegate userInfoView:self didSelectedJoinButton:_joinBtn requestParamDict:param];
        
        self.nameTF.text = @"";
        self.phoneTF.text = @"";
        self.reqNumTF.text = @"";
        self.remarkTextView.text = @"";
        self.textViewPlacehoder.text = remarkPlacehoder_str;
    }
 

}

@end
