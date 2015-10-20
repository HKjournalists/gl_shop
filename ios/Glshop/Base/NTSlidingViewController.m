//
//  NTSlidingViewController.m
//  NTSlidingViewController
//
//  Created by nonstriater on 14-2-24.
//  Copyright (c) 2014年 xiaoran. All rights reserved.
//

#import "NTSlidingViewController.h"


static const CGFloat kMaximumNumberChildControllers = 4;
//static const CGFloat kNavbarButtonScaleFactor = 1.3f;
static const CGFloat kBarViewHeight = 35;


@interface UIColor (components)

- (CGFloat)red;
- (CGFloat)green;
- (CGFloat)blue;

@end

@implementation UIColor (components)

- (CGFloat)red{
    return CGColorGetComponents(self.CGColor)[0];
}
- (CGFloat)green{
    return CGColorGetComponents(self.CGColor)[1];
}
- (CGFloat)blue{
    return CGColorGetComponents(self.CGColor)[2];
}


@end


@interface NTSlidingViewController ()<UIScrollViewDelegate>{

    UIScrollView *_scrollView;
    CAShapeLayer *_shapLine;
}

@end

@implementation NTSlidingViewController

#pragma mark- contrller lifecircle

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        
    }
    return self;
}

- (void)awakeFromNib {
    
}

- (id)initWithCoder:(NSCoder *)aDecoder {
    if (self = [super initWithCoder:aDecoder]) {
        [self commonInit];
        self = [self initSlidingViewControllerWithTitlesAndControllers:nil];
    }
    
    return self;
}


- (instancetype)init{
    if (self=[super init]) {
        [self commonInit];
    }
    return self;
}

- (instancetype)initSlidingViewControllerWithTitle:(NSString *)title viewController:(UIViewController *)controller{

    self = [self init];
    if (self) {
        
        [self.titles addObject:title];
        [self.childControllers addObject:controller];
    }
    return  self;
}
- (instancetype)initSlidingViewControllerWithTitlesAndControllers:(NSDictionary *)titlesAndControllers{
    
    if (self = [self init]) {
        
        [titlesAndControllers enumerateKeysAndObjectsUsingBlock:^(id key,id obj,BOOL *stop){
            if ([key isKindOfClass:[NSString class]] && [obj isKindOfClass:[UIViewController class]]) {
                
                [self.titles addObject:key];
                [self.childControllers addObject:obj];
            }
        }];
        
    }
    return self;
}


- (void)addControllerWithTitle:(NSString *)title viewController:(UIViewController *)controller{
    
    [self.titles addObject:title];
    [self.childControllers addObject:controller];
    
}

+ (instancetype)slidingViewControllerWithTitlesAndControllers:(NSDictionary *)titlesAndControllers{
    return [[self alloc] initSlidingViewControllerWithTitlesAndControllers:titlesAndControllers];
}

- (void)commonInit{

    _titles = [[NSMutableArray alloc] init];
   
    _childControllers = [[NSMutableArray alloc] init];
    
    _unselectedLabelColor = [UIColor grayColor];
    _selectedLabelColor = [UIColor redColor];
    _selectedIndex = 1;
}


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


#pragma mark - config

-(UIInterfaceOrientation)interfaceOrientation{
    return UIInterfaceOrientationPortrait;
}

#pragma mark - view lifecircle
- (void)viewDidLoad
{
    [super viewDidLoad];
    //添加一根分割线
    UIView *line = [[UIView alloc] initWithFrame:CGRectMake(0, kBarViewHeight - 0.3, SCREEN_WIDTH, 0.3)];
    line.backgroundColor = [UIColor lightGrayColor];
    [self.view addSubview:line];
    
    [self.view addSubview:self.navigationBarView];
    [self.view addSubview:[self contenScrollView]];
}

// 动态适配
// 最多 6 个
//
-(UIView *)navigationBarView{

    if (!_navigationBarView) {
        _navigationBarView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, SCREEN_WIDTH, kBarViewHeight)];
        _navigationBarView.backgroundColor = [UIColor whiteColor];
        
        UIView *line = [[UIView alloc] initWithFrame:CGRectMake(0, kBarViewHeight-0.5, _navigationBarView.vwidth, 0.5)];
        line.backgroundColor = RGB(212, 212, 212, 1);
        [_navigationBarView addSubview:line];
        
        CGFloat barViewWidth = self.view.frame.size.width;
        CGFloat itemWidth = barViewWidth/kMaximumNumberChildControllers ;
        NSUInteger itemCount = [self.titles count];
        CGFloat itemMargin = (barViewWidth - itemCount*itemWidth)/(itemCount+1);
        for (int i=0; i<itemCount; i++) {
            UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
            [button setFrame:CGRectMake(i*itemWidth+(i+1)*itemMargin, 0, itemWidth, kBarViewHeight)];
            [button setTitleColor:self.unselectedLabelColor forState:UIControlStateNormal];
            [button setTitleColor:self.selectedLabelColor forState:UIControlStateSelected];
            [button.titleLabel setFont:[UIFont systemFontOfSize:14.]];
            [button.titleLabel setTextAlignment:NSTextAlignmentCenter];
            [button setTitle:self.titles[i] forState:UIControlStateNormal];
            button.tag = i+1;
            [button addTarget:self action:@selector(navigationBarButtonItemClicked:) forControlEvents:UIControlEventTouchUpInside];
            [_navigationBarView addSubview:button];
            
            if (i==0) {
                [button setTitleColor:self.selectedLabelColor forState:UIControlStateNormal];
                button.transform = CGAffineTransformIdentity;
                
                _shapLine = [[CAShapeLayer alloc] init];
                _shapLine.backgroundColor = [UIColor orangeColor].CGColor;
                _shapLine.frame = CGRectMake(button.vleft, button.vbottom-2, button.vwidth, 2);
                [_navigationBarView.layer addSublayer:_shapLine];
            }
            
        }

    }
    
    return _navigationBarView;
}

- (UIScrollView *)contenScrollView{

    if (!_scrollView) {
        
        _scrollView = [[UIScrollView alloc] initWithFrame:CGRectMake(0, kBarViewHeight,self.view.frame.size.width  , self.view.frame.size.height-kBarViewHeight )];
        _scrollView.backgroundColor = [UIColor whiteColor];
        _scrollView.pagingEnabled = YES;
        _scrollView.alwaysBounceHorizontal = YES;
        _scrollView.showsHorizontalScrollIndicator = NO;
        
        _scrollView.contentSize = CGSizeMake(_scrollView.frame.size.width*[self.titles count], _scrollView.frame.size.height);
        _scrollView.delegate = self;
        
        for (int i=0; i<[self.childControllers count]; i++) {
            id obj = [self.childControllers objectAtIndex:i];
            if ([obj isKindOfClass:[UIViewController class]]) {
                UIViewController *controller = (UIViewController *)obj;
                CGFloat scrollWidth = _scrollView.frame.size.width;
                CGFloat scrollHeight = _scrollView.frame.size.height;
                
                if (i == 0) { //初始化只加载第一个页面，提升性能
                    [self addChildViewController:controller];
                    [controller.view setFrame:CGRectMake(0, 0, scrollWidth, scrollHeight)];
                    [_scrollView addSubview:controller.view];
                    [controller didMoveToParentViewController:self];
                }
            }
      
        }

    }
    
    return _scrollView;
    
}


- (void)navigationBarButtonItemClicked:(UIButton *)button{
    self.selectedIndex = button.tag;
    
    [self loadViewController];
}

- (void)setSelectedIndex:(NSUInteger)index{
    
    if (index != self.selectedIndex) {
        UIButton *origin = (UIButton *)[_navigationBarView viewWithTag:self.selectedIndex];
        if ([origin isKindOfClass:[UIButton class]]) {
            //origin.transform = CGAffineTransformIdentity;
            origin.transform = CGAffineTransformMakeScale(1.f, 1.f);
            [origin setTitleColor:self.unselectedLabelColor forState:UIControlStateNormal];
        }
        index = index < 1 ? 1 : index;
        UIButton *button = (UIButton *)[_navigationBarView viewWithTag:index];
        [button setTitleColor:self.selectedLabelColor forState:UIControlStateNormal];
//        button.transform = CGAffineTransformMakeScale(kNavbarButtonScaleFactor, kNavbarButtonScaleFactor);
        
        _selectedIndex = index;
        _shapLine.frame = CGRectMake(button.vleft, button.vbottom-2, button.vwidth, 2);
        [self transitionToViewControllerAtIndex:index-1];
    }


}


#pragma mark - delegages

- (void)scrollViewWillBeginDecelerating:(UIScrollView *)scrollView{}

- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView{

    DLog(@"%.f---%.f",scrollView.contentOffset.x,scrollView.frame.size.width);
    self.selectedIndex =  scrollView.contentOffset.x/scrollView.frame.size.width+1;
    if (0==fmod(scrollView.contentOffset.x,scrollView.frame.size.width)){
        [self loadViewController];
    }
    
    
}

- (void)scrollViewWillBeginDragging:(UIScrollView *)scrollView{}

- (void)scrollViewWillEndDragging:(UIScrollView *)scrollView withVelocity:(CGPoint)velocity targetContentOffset:(inout CGPoint *)targetContentOffset {

}

- (void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate{
 
}


- (void)scrollViewDidScroll:(UIScrollView *)scrollView{
    
    // 左 or 右
    UIButton *relativeButton = nil;
    UIButton *currentButton = (UIButton *)[_navigationBarView viewWithTag:_selectedIndex];
    CGFloat offset = scrollView.contentOffset.x-(_selectedIndex-1)*scrollView.frame.size.width;
    
    if(offset>0 && _selectedIndex<[self.titles count]){//右
        relativeButton = (UIButton *)[_navigationBarView viewWithTag:_selectedIndex+1];
    }else if(offset<0 && _selectedIndex>1){
        relativeButton = (UIButton *)[_navigationBarView viewWithTag:_selectedIndex-1];
    }
    
    offset = fabsf(offset);
    if (relativeButton) {
        
        CGFloat scrollViewWidth = scrollView.frame.size.width;
//        CGFloat currentScaleFactor = (kNavbarButtonScaleFactor)-(kNavbarButtonScaleFactor-1)*fabsf(offset)/scrollViewWidth;
//        CGFloat relativeScaleFactor = (kNavbarButtonScaleFactor-1)*fabsf(offset)/scrollViewWidth+1.f;
        
        
        
        UIColor *currentColor = [UIColor colorWithRed:((self.unselectedLabelColor.red-self.selectedLabelColor.red)*offset/scrollViewWidth+self.selectedLabelColor.red)
                                               green:((self.unselectedLabelColor.green-self.selectedLabelColor.green)*offset/scrollViewWidth+self.selectedLabelColor.green)
                                                blue:((self.unselectedLabelColor.blue-self.selectedLabelColor.blue)*offset/scrollViewWidth+self.selectedLabelColor.blue)
                                               alpha:1];
        
        UIColor *relativeColor = [UIColor colorWithRed:((self.selectedLabelColor.red-self.unselectedLabelColor.red)*offset/scrollViewWidth+self.unselectedLabelColor.red)
                                                green:((self.selectedLabelColor.green-self.unselectedLabelColor.green)*offset/scrollViewWidth+self.unselectedLabelColor.green)
                                                 blue:((self.selectedLabelColor.blue-self.unselectedLabelColor.blue)*offset/scrollViewWidth+self.unselectedLabelColor.blue)
                                                alpha:1];
        
//        currentButton.transform = CGAffineTransformMakeScale(currentScaleFactor,currentScaleFactor );
        [currentButton setTitleColor:currentColor forState:UIControlStateNormal];
        
//        relativeButton.transform = CGAffineTransformMakeScale(relativeScaleFactor,relativeScaleFactor );
        [relativeButton setTitleColor:relativeColor forState:UIControlStateNormal];
        
    }
   
}

- (void)loadViewController {
    UIViewController *vc = self.childControllers[_selectedIndex-1];
    if (/*!vc.view.window*/ !vc.parentViewController) {  // 不访问根视图
        [self addChildViewController:vc];
        vc.view.frame = CGRectMake((_selectedIndex-1)*CGRectGetWidth(_scrollView.frame), 0, CGRectGetWidth(_scrollView.frame), CGRectGetHeight(_scrollView.frame));
//        [vc didMoveToParentViewController:nil];
        [_scrollView addSubview:vc.view];
        [vc didMoveToParentViewController:self];
    }
}


#pragma mark - public 


- (void)transitionToViewControllerAtIndex:(NSUInteger)index{
    
    [_scrollView setContentOffset:CGPointMake(index*_scrollView.frame.size.width, 0)];
    
}
- (void)transitionToViewController:(UIViewController *)controller{
    NSLog(@"%@",[controller class]);

    [self transitionToViewControllerAtIndex:[self.childControllers indexOfObject:controller]];
}

- (void)transitionToViewController:(UIViewController *)controller complteBlock:(NTTransitionCompleteBlock)block{

}



@end
