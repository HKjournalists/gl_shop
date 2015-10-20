

#import "MBProgressHUD.h"

@interface HUDHelper : NSObject <MBProgressHUDDelegate>
{
@private
    NSMutableArray *_showingHUDs;
    
    MBProgressHUD *_loadingHud;
    
}

@property (nonatomic, weak) UIWindow *window;

- (void)addHUD:(MBProgressHUD *)hud;
- (void)removeHUD:(MBProgressHUD *)hud;

+ (HUDHelper *)sharedInstance;
- (void)serviceLoading:(NSInteger)maxRequestCount;
- (void)loading;
- (void)stopLoading;

- (void)tipMessage:(NSString *)msg;
- (void)tipMessage:(NSString *)msg delay:(CGFloat)seconds;

@end
