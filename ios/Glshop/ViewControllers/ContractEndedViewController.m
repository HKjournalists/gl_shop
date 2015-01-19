//
//  ContractEndedViewController.m
//  Glshop
//
//  Created by River on 14-12-9.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "ContractEndedViewController.h"

@interface ContractEndedViewController ()

@end

@implementation ContractEndedViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    UIImageView *imgView = [[UIImageView alloc] initWithFrame:CGRectMake(50, 100, 200, 200)];
    UIImage *img = [UIImage imageNamed:@"wallet_beijing"];
    img = [img resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
    imgView.image = img;
    [self.view addSubview:imgView];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
