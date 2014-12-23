

#import "HUDHelper.h"

@implementation HUDHelper

static HUDHelper *_instance = nil;


@synthesize window = _window;


+ (HUDHelper *)sharedInstance
{
    @synchronized(_instance)
    {
        if (_instance == nil)
        {
            _instance = [[HUDHelper alloc] init];
        }
        return _instance;
    }
}

- (id)init
{
    if (self = [super init])
    {
        self.window = [UIApplication sharedApplication].keyWindow;
        self.window.windowLevel=100.f;
        _showingHUDs = [[NSMutableArray alloc] init];
    }
    return self;
}



- (void)hudWasHidden:(MBProgressHUD *)hud
{
    @synchronized(_loadingHud)
    {
        if (_loadingHud == hud) {
            [_loadingHud removeFromSuperview];
            [self removeHUD:_loadingHud];
            _loadingHud = nil;
        }
    }
    
}

- (void)addHUD:(MBProgressHUD *)hud
{
    @synchronized(_showingHUDs)
    {
        [_showingHUDs addObject:hud];
    }
}
- (void)removeHUD:(MBProgressHUD *)hud
{
    @synchronized(_showingHUDs)
    {
        [_showingHUDs removeObject:hud];
    }
}

- (void)serviceLoading:(NSInteger)maxRequestCount
{
    @synchronized(_loadingHud)
    {
        if (_loadingHud == nil) {
            _loadingHud = [MBProgressHUD showHUDAddedTo:self.window animated:YES];
            _loadingHud.delegate = self;
            _loadingHud.tag = 0;
            [self addHUD:_loadingHud];
            [_loadingHud show:YES];
        }
        if (_loadingHud)
        {
            
            _loadingHud.tag++;
        }
    }
}

- (void)loading
{
    @synchronized(_loadingHud)
    {
        if (_loadingHud == nil) {
            _loadingHud = [MBProgressHUD showHUDAddedTo:self.window animated:YES];
            _loadingHud.delegate = self;
            _loadingHud.tag = 0;
            [self addHUD:_loadingHud];
            [_loadingHud show:YES];
            [_loadingHud hide:YES afterDelay:100];
        }
        if (_loadingHud)
        {
            
            _loadingHud.tag++;
        }
    }
}

- (void)stopLoading
{
    @synchronized(_loadingHud)
    {
        if (_loadingHud)
        {
            if (--_loadingHud.tag <= 0)
            {
                [_loadingHud hide:YES];
            }
            
        }
    }
}

- (void)tipMessage:(NSString *)msg delay:(CGFloat)seconds
{
    if (!msg) {
        return;
    }

    MBProgressHUD *HUD = [[MBProgressHUD alloc] initWithWindow:_window];
    [_window addSubview:HUD];
    HUD.mode = MBProgressHUDModeText;
    HUD.delegate = self;
    HUD.detailsLabelText = msg;
    [HUD show:YES];
    [HUD hide:YES afterDelay:seconds];
}


- (void)delayTipMessage:(NSString *)msg
{
    [self tipMessage:msg delay:2.0];
}

- (void)tipMessage:(NSString *)msg
{
    if (!msg) {
        return;
    }
    
    if ([NSThread isMainThread])
    {
        [self performSelector:@selector(delayTipMessage:) withObject:msg afterDelay:0.2];
    }
    else
    {
        [self performSelectorOnMainThread:@selector(tipMessage:) withObject:msg waitUntilDone:NO];
    }
    
}


@end
