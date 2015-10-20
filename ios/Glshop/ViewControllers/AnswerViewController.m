//
//  AnswerViewController.m
//  Glshop
//
//  Created by River on 15-2-27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "AnswerViewController.h"

@interface AnswerViewController ()

@property (strong, nonatomic) IBOutlet UILabel *qusionLabel;
@property (strong, nonatomic) IBOutlet UILabel *answerLabel;

@end

@implementation AnswerViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.title = @"常见问题解答";
    
    _qusionLabel.text = _qustionString;
    _answerLabel.text = _answerString;
    
    _qusionLabel.font = UFONT_16;
    _qusionLabel.textColor = C_BLACK;
    
    _answerLabel.font = UFONT_14;
    _answerLabel.textColor = C_GRAY;
}

@end
