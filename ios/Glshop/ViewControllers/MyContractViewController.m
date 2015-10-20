//
//  MyContractViewController.m
//  Glshop
//
//  Created by River on 14-11-18.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "MyContractViewController.h"
#import "ContractPorccesingViewController.h"
#import "ContractEndedViewController.h"
#import "ContractWaitSureViewController.h"

@interface MyContractViewController ()

@property (nonatomic) NSUInteger numberOfTabs;

@end

@implementation MyContractViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.title = @"我的合同";
 
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];
    
}

- (void)transitionToViewControllerAtIndex:(NSUInteger)index {
    [super transitionToViewControllerAtIndex:index];

    if (index == 0) {
        ContractWaitSureViewController *vc = self.childControllers[index];
        [vc fireRefrush];
    }
}

- (void)backRootVC {
    [self.navigationController popToRootViewControllerAnimated:YES];
}

@end
