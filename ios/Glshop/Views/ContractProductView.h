//
//  ContractProductView.h
//  Glshop
//
//  Created by River on 15-2-3.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ContractProductView : UIView

@property (nonatomic, strong) IBOutlet UILabel *productLabel;
@property (nonatomic, strong) IBOutlet UILabel *priceLabel;
@property (nonatomic, strong) IBOutlet UILabel *totalLabel;
@property (nonatomic, strong) IBOutlet UIButton *btn1;
@property (nonatomic, strong) IBOutlet UIButton *btn2;
@property (strong, nonatomic) IBOutlet UIView *indicateLine;

@property (nonatomic, weak) UIScrollView *weakScrollView;
@property (nonatomic, copy) NSString *contractId;

- (void)commAction:(BOOL)left;

@end
