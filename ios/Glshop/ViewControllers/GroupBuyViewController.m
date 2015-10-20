//
//  GroupBuyViewController.m
//  Glshop
//
//  Created by shaouwangyunlei on 15/7/27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "GroupBuyViewController.h"
#import "GroupBuyBanerView.h"
#import "UserInfoView.h"
#import "SuperGiftView.h"
#import "ActivityRulesView.h"
#import "WXApi.h"
#import "ImagePlayerView.h"
#import "Reachability.h"

#define SHAREBGVIEWHEIGHT 86
#define NUMBEROFBANNER 4
@interface GroupBuyViewController ()
{
    ShareBgView *_backGroundView;

    UIView *overlapView;
    
    UITextView *remarkTextView;
}
@property (nonatomic,strong)GroupBuyBanerView *banerView;
@property (nonatomic,strong)UserInfoView *userInfoView;
@property (nonatomic,strong)SuperGiftView *superGiftView;
@property (nonatomic,strong)ActivityRulesView *activityRulesView;
@end


@implementation GroupBuyViewController


- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.title = @"超低价团购第1期";
    
    [self createSubViews];
    

    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithImage:[UIImage imageNamed:@"share_icon_"] style:UIBarButtonItemStyleBordered target:self action:@selector(rightBarButtonClick)];
}

- (void)createSubViews
{
    //主滚动view
    UIScrollView *mainScroll = [[UIScrollView alloc] initWithFrame:self.view.bounds];
    [self.view addSubview:mainScroll];
    self.automaticallyAdjustsScrollViewInsets = NO;
    mainScroll.showsHorizontalScrollIndicator = NO;
    mainScroll.bounces = NO;
    
    _banerView = [[GroupBuyBanerView alloc] initWithFrame:CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, 526*0.5) image:@"beijing1_"title:@"超级黄金砂"];
    [mainScroll addSubview:_banerView];
    _banerView.imageView.imagePlayerViewDelegate = self;
    DLog(@"banerView.imageView.delegate= %@",_banerView.imageView.imagePlayerViewDelegate);
    [_banerView.imageView reloadData];

    
    _userInfoView = [[UserInfoView alloc] initWithFrame:CGRectMake(0, CGRectGetMaxY(_banerView.frame), CGRectGetWidth(_banerView.frame), 597*0.5) image:@"bg2_" title:@"超级大团购"];
    _userInfoView.delegate = self;
    [mainScroll addSubview:_userInfoView];
    
    _superGiftView = [[SuperGiftView alloc]initWithFrame:CGRectMake(0, CGRectGetMaxY(_userInfoView.frame), CGRectGetWidth(_userInfoView.frame), 488*0.5) image:@"bg3_" title:@"超级大礼包"];
    [mainScroll addSubview:_superGiftView];
    
    _activityRulesView = [[ActivityRulesView alloc] initWithFrame:CGRectMake(0, CGRectGetMaxY(_superGiftView.frame), CGRectGetWidth(_superGiftView.frame), 584*0.5) image:@"bg4_" title:@"活动规则"];
    
    [mainScroll addSubview:_activityRulesView];
    
    mainScroll.contentSize = CGSizeMake([UIScreen mainScreen].bounds.size.width,CGRectGetMaxY(_activityRulesView.frame)+NAVIGATIONBARHEIGHT);
   
    //分享的背景试图
    _backGroundView = [[ShareBgView alloc] initWithFrame:CGRectMake(0, CGRectGetMaxY(self.view.frame), CGRectGetWidth(self.view.frame),SHAREBGVIEWHEIGHT)];
    _backGroundView.delegate = self;
    _backGroundView.backgroundColor = GROUPBUYWHITECOLOR;
    [self.view addSubview:_backGroundView];
    
    //覆盖层试图
    overlapView = [[UIView alloc] initWithFrame:self.view.bounds];
    overlapView.backgroundColor = [UIColor blackColor];
    overlapView.alpha = 0.6;
    overlapView.hidden = YES;
    [self.view addSubview:overlapView];
    

}

- (void)rightBarButtonClick
{

    
    if (_backGroundView.frame.origin.y == CGRectGetMaxY(self.view.frame)) {
        [self.view bringSubviewToFront:_backGroundView];
        overlapView.hidden = NO;
        [_backGroundView ShareViewAnimateWithDuration:0.5 moveToPoint:CGPointMake(0, CGRectGetMaxY(self.view.frame)-SHAREBGVIEWHEIGHT-NAVIGATIONBARHEIGHT)];
        
    }else{
        overlapView.hidden = YES;
        [UIView animateWithDuration:0.5 animations:^{
            
          [_backGroundView ShareViewAnimateWithDuration:0.5 moveToPoint:CGPointMake(0, CGRectGetMaxY(self.view.frame))];
            
        }];
    
        
    }
    
   
}



- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
    UITouch *touch = [touches anyObject];
    CGPoint touchPoint = [touch locationInView:self.view];
    if (touchPoint.y < _backGroundView.frame.origin.y) {
        overlapView.hidden = YES;
        [_backGroundView ShareViewAnimateWithDuration:0.5 moveToPoint:CGPointMake(0, CGRectGetMaxY(self.view.frame))];
    }

}

- (void) sendLinkContentWith:(int)Wxsence
{
    if (![WXApi isWXAppInstalled]) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"通知" message:@"尚未安装微信" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil, nil];
        [alert show];
        return;
    }
    
    WXMediaMessage *message = [WXMediaMessage message];
    message.title = @"长江电商大团购开抢了";
    message.description = @"我报名了洞庭湖黄砂大团购，快来和我一起组团吧!";
    [message setThumbImage:[UIImage imageNamed:@"logo108X.png"]];
    
    WXWebpageObject *ext = [WXWebpageObject object];
    ext.webpageUrl = @"http://mp.weixin.qq.com/s?__biz=MzAxNzQxOTQ2OQ==&mid=218293114&idx=1&sn=8b7eafdd20caeb005ed08733c65e1b9f#rd";
    
    message.mediaObject = ext;
   // message.mediaTagName = @"WECHAT_TAG_JUMP_SHOWRANK";
    
    SendMessageToWXReq* req = [[SendMessageToWXReq alloc] init];
    req.bText = NO;
    req.message = message;
    req.scene = Wxsence;
    [WXApi sendReq:req];
}
- (NSInteger)numberOfItems
{
    return 4;

}
- (void)imagePlayerView:(ImagePlayerView *)imagePlayerView loadImageForImageView:(UIImageView *)imageView index:(NSInteger)index
{
//    dispatch_async(dispatch_get_main_queue(), ^{
        NSString *imageName = [NSString stringWithFormat:@"banner%ld",index+1];
        imageView.image = [UIImage imageNamed:imageName];

//    });
    
}


- (void)userInfoView:(UserInfoView *)userInfoView didSelectedJoinButton:(UIButton *)joinButton requestParamDict:(NSMutableDictionary *)param
{
        [self showHUD];
  
        [self requestWithURL:bUserJoinGroupBuyPath params:param HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
            
         
                [self hideHUD];
                HUD(@"恭喜您，你已预定成功，我们将尽快与您联系！");
        
            
        } failedBlock:^(ASIHTTPRequest *request) {
            
            
            
        }];

    
    
    
}
- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (buttonIndex == 0) {
        remarkTextView.editable = YES;
    }
}
@end
