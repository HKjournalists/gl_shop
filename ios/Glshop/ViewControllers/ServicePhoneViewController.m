//
//  ServicePhoneViewController.m
//  Glshop
//
//  Created by River on 15-2-27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "ServicePhoneViewController.h"

@interface ServicePhoneViewController ()

@end

@implementation ServicePhoneViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    
    self.title = @"客服电话";
}

- (void)loadSubViews {
    NSString *imgName = iPhone4 ? @"help_custom-service_960@2x": @"help_custom-service_1136@2x";
    UIImage *image = PNGIMAGE(imgName);
    UIImageView *imageView = [[UIImageView alloc] initWithFrame:self.view.bounds];
    imageView.vtop -= 40;
    imageView.userInteractionEnabled = YES;
    imageView.image = image;
    [self.view insertSubview:imageView atIndex:0];
}

- (IBAction)callPhone:(UIButton *)sender {
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:@"tel://4009616816"]];
}
@end
