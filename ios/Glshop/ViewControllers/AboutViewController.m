//
//  AboutViewController.m
//  Glshop
//
//  Created by River on 15-2-27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "AboutViewController.h"

@interface AboutViewController ()

@property (weak, nonatomic) IBOutlet UILabel *contentLabel;

@end

@implementation AboutViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.title = @"关于我们";
    _contentLabel.textColor = RGB(100, 100, 100, 1);
    NSString *str = @"      江苏国立网络技术有限公司（简称“长江电商”）是一家依托长江的黄金水道优势，为长江沿岸中小型企业的供需提供电子化交易服务的高科技企业，注册资本1000万元。办公场地近1200平方米。公司现在入驻于江苏省靖江市恒天商务广场。\n\n（江苏国立网络技术有限公司长江电商）发展覆盖了长江沿岸11个省、市的物流园网络体系以及众多企业资源、船舶资源，结合线上“网上交易”平台与线下“实体物流”网络，打造联系上下游的综合交易服务平台。";
    
    NSMutableAttributedString *attributedString = [[NSMutableAttributedString alloc] initWithString:str];
    NSMutableParagraphStyle *paragraphStyle = [[NSMutableParagraphStyle alloc] init];
    
    [paragraphStyle setLineSpacing:10];//调整行间距
    
    [attributedString addAttribute:NSParagraphStyleAttributeName value:paragraphStyle range:NSMakeRange(0, [str length])];
    _contentLabel.attributedText = attributedString;
    _contentLabel.numberOfLines = 0;
    [_contentLabel sizeToFit];
    
    
    self.view.backgroundColor = [UIColor whiteColor];
}

@end
