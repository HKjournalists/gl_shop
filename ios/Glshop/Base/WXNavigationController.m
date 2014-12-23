//
//  WXNavigationController.m
//  WXWeibo
//
//  Created by wei.chen on 13-5-13.
//  Copyright (c) 2013年 www.iphonetrain.com 无限互联3G学院. All rights reserved.
//

#import "WXNavigationController.h"
#import <QuartzCore/QuartzCore.h>

#define KEY_WINDOW  [[UIApplication sharedApplication]keyWindow]

#pragma mark - WXNavigationBar 子类化UINavigationBar
@interface WXNavigationBar : UINavigationBar
@end
@implementation WXNavigationBar
//禁用导航栏的pop动画
- (UINavigationItem *)popNavigationItemAnimated:(BOOL)animated {
    return [super popNavigationItemAnimated:NO];
}
@end

@interface WXNavigationController () <UIGestureRecognizerDelegate>
{
    CGPoint startTouch;
    BOOL isMoving;
    
    UIImageView *backImageView;
    UIView *alphaView;
}

@property (nonatomic,retain) UIView *backgroundView;
@property (nonatomic,retain) NSMutableArray *backImages;

@end

@implementation WXNavigationController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        
        
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.backImages = [[NSMutableArray alloc]initWithCapacity:2];
    self.canDragBack = YES;
    
    //创建滑动手势,实现左右滑动视图
    _pan = [[UIPanGestureRecognizer alloc] initWithTarget:self action:@selector(pan:)];
    _pan.delegate = self;
    [_pan setEnabled:NO];
    [self.view addGestureRecognizer:_pan];
    

}

//////////////////////////////抽屉式导航////////////////////////////////////////
- (void)pan:(UIPanGestureRecognizer *)pan {
    //手势开始
    if (pan.state == UIGestureRecognizerStateBegan) {
        if (self.viewControllers.count <= 1 || !self.canDragBack) {
            return;
        }
        
        isMoving = NO;
//        startTouch = [((UITouch *)[touches anyObject])locationInView:KEY_WINDOW];
        startTouch = [pan locationInView:KEY_WINDOW];
    }
    else if(pan.state == UIGestureRecognizerStateChanged) {
        if (self.viewControllers.count <= 1 || !self.canDragBack) {
            return;
        }
        
//        CGPoint moveTouch = [((UITouch *)[touches anyObject])locationInView:KEY_WINDOW];
        CGPoint moveTouch = [pan locationInView:KEY_WINDOW];
        
        if (!isMoving && moveTouch.x-startTouch.x > 10) {
            backImageView.image = [self.backImages lastObject];
            isMoving = YES;
        }
        
        [self moveViewWithX:moveTouch.x - startTouch.x];
    
    }
    else if(pan.state == UIGestureRecognizerStateEnded) {
        if (self.viewControllers.count <= 1 || !self.canDragBack) {
            return;
        }
        
        CGPoint endTouch = [pan locationInView:KEY_WINDOW];
        
        if (endTouch.x - startTouch.x > 50) {
//#warning 设置动画时间
            animationTime =.35 - (endTouch.x - startTouch.x) / SCREEN_WIDTH * .35;
            [self popViewControllerAnimated:NO];
        } else {
            [UIView animateWithDuration:0.3 animations:^{
                [self moveViewWithX:0];
            } completion:^(BOOL finished) {
                isMoving = NO;
            }];
            
        }
    }
    else if(pan.state == UIGestureRecognizerStateCancelled) {
        if (self.viewControllers.count <= 1 || !self.canDragBack) {
            return;
        }
        
        [UIView animateWithDuration:0.3 animations:^{
            [self moveViewWithX:0];
        } completion:^(BOOL finished) {
            isMoving = NO;
        }];
    }
}

#pragma mark - override UINavigationController方法覆写
- (void)pushViewController:(UIViewController *)viewController animated:(BOOL)animated {
    UIImage *capture = [self capture];
    if (capture != nil) {
        [self.backImages addObject:capture];
    }
    
    if (self.viewControllers.count == 1) {
        [_pan setEnabled:YES];
    }
    
    [super pushViewController:viewController animated:NO];
    
    if (self.backgroundView == nil) {
//#warning 之前设置的backImageView->frame不对,图片拉伸的比例就不对
        CGRect frame = CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        self.backgroundView = [[UIView alloc]initWithFrame:frame] ;
        self.backgroundView.backgroundColor = [UIColor blackColor];
        
        backImageView = [[UIImageView alloc] initWithFrame:frame];
        [self.backgroundView addSubview:backImageView];
//        NSLog(@"%@",NSStringFromCGRect(self.view.bounds) );
        alphaView = [[UIView alloc] initWithFrame:frame];
        alphaView.backgroundColor = [UIColor blackColor];
        [self.backgroundView addSubview:alphaView];
    }
    if (self.backgroundView.superview == nil) {
        [self.view.superview insertSubview:self.backgroundView belowSubview:self.view];
    }
    
    if (self.viewControllers.count == 1) {
        return;
    }
    
    backImageView.image = [self.backImages lastObject];
    alphaView.alpha = 0;
    
    [self moveViewWithX:320];
    [UIView animateWithDuration:.35 animations:^{
        [self moveViewWithX:0];
    }];
}

- (UIViewController *)popViewControllerAnimated:(BOOL)animated {
//#warning 注意写按钮返回事件的时候animated给YES / 手势返回的时候 animated给NO
    if (animated == YES) {
        animationTime = .35;
    }
    if (self.viewControllers.count == 2) {
        [_pan setEnabled:NO];
    }
    
    if (self.view.frame.origin.x == 0) {
        backImageView.transform = CGAffineTransformMakeScale(0.95, 0.95);
    }
    [UIView animateWithDuration:animationTime animations:^{
        [self moveViewWithX:320];
    } completion:^(BOOL finished) {
        CGRect frame = CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.origin.x = 0;
        self.view.frame = frame;
//#warning 先导航控制器，在移除图片
        [super popViewControllerAnimated:NO];
        
        [self.backImages removeLastObject];
        backImageView.image = [self.backImages lastObject];
        
        // IOS8需要屏蔽次处
//        CFRunLoopStop(CFRunLoopGetCurrent());
    }];
    
        // IOS8需要屏蔽次处
//    CFRunLoopRun();
    
    return nil;
}

#pragma mark - Utility Methods -
//获取当前屏幕视图的快照图片
- (UIImage *)capture {
    if (self.viewControllers.count==0) {
        return nil;
    }
//    UIViewController *vc = self.viewControllers[0];
//    UITabBarController *mvc = (UITabBarController *)vc.tabBarController;
    UIView *view = self.view;
    if (view == nil) {
        return nil;
    }
    
    
    UIGraphicsBeginImageContextWithOptions(view.bounds.size, view.opaque, 0.0);
    [view.layer renderInContext:UIGraphicsGetCurrentContext()];
    
    UIImage * img = UIGraphicsGetImageFromCurrentImageContext();
//    NSLog(@"%f,%f",img.size.height,img.size.width);
    UIGraphicsEndImageContext();
    
    return img;
}

//移动导航控制器的根视图self.view
- (void)moveViewWithX:(float)x {
    
    x = x>320?320:x;
    x = x<0?0:x;
    
    CGRect frame = CGRectMake(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    frame.origin.x = x;
    self.view.frame = frame;
    float scale = (x/6400)+0.95;
    float alpha = 0.4 - (x/800);
    backImageView.transform = CGAffineTransformMakeScale(scale, scale);
    alphaView.alpha = alpha;
}

#pragma mark - override Touch 触摸方法

@end


//@implementation UINavigationBar (customBackground)
//
//- (void)drawRect:(CGRect)rect
//{
//    UIImage *img = [UIImage imageNamed:@"nav_bg_all"];
//    [img drawInRect:rect];
//}
//
//@end
