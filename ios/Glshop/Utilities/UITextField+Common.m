//
//  UITextField+Common.m
//  TCLSales
//
//  Created by 陈耀武 on 14-1-17.
//  Copyright (c) 2014年 Insigma. All rights reserved.
//

#import "UITextField+Common.h"

@implementation UITextField (Common)

+ (instancetype)textFieldWithPlaceHodler:(NSString *)placeHodler withDelegate:(id)delegate
{
    UITextField *input = [[UITextField alloc] init] ;
    input.borderStyle = UITextBorderStyleNone;
    input.placeholder = placeHodler;
    input.clearButtonMode = UITextFieldViewModeWhileEditing;
    input.leftViewMode = UITextFieldViewModeAlways;
    input.delegate = delegate;
    
    return input;

}

+ (instancetype)textFieldWithPlaceHodler:(NSString *)placeHodler leftView:(NSString *)tip withDelegate:(id)delegate
{
    UITextField *field = [UITextField textFieldWithPlaceHodler:placeHodler withDelegate:delegate];
    UILabel *label = [[UILabel alloc] init];
    label.textAlignment = NSTextAlignmentLeft;
    label.backgroundColor = [UIColor clearColor];
    label.textColor = [UIColor blackColor];
    label.text = tip;
    label.frame = CGRectMake(0, 0, 70, 30);
    [label sizeToFit];
    label.textAlignment = NSTextAlignmentCenter;
    field.leftView = label;
    
    return field;
}

+ (instancetype)textFieldWithImageName:(NSString *)image placeHodler:(NSString *)placeHodler withDelegate:(id)delegate
{
    UITextField *input = [[UITextField alloc] init];
    input.backgroundColor = [UIColor clearColor];
    input.textAlignment = NSTextAlignmentLeft;
    input.placeholder = placeHodler;
    input.keyboardType = UIKeyboardTypeDefault;
    input.contentVerticalAlignment = UIControlContentVerticalAlignmentCenter;
    input.borderStyle = UITextBorderStyleNone;
    input.leftViewMode = UITextFieldViewModeAlways;
    input.clearButtonMode = UITextFieldViewModeWhileEditing;
    input.delegate = delegate;
    
    
    UIImage *leftImg = [UIImage imageNamed:image];
    UIImageView *imageView = [[UIImageView alloc] initWithImage:leftImg];
    imageView.frame = CGRectMake(10, 0, leftImg.size.width, leftImg.size.height);
    //    input.leftView = imageView;
    
    UIView *view = [[UIView alloc] init];
    view.frame = CGRectMake(0, 0, leftImg.size.width + 20, leftImg.size.height);
    
    [view addSubview:imageView];
    input.leftView = view;
    return input;
    
}


@end
