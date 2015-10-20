//
//  AppDelegate.m
//  Glshop
//
//  Created by River on 14-11-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "AppDelegate.h"
#import "NetEngine.h"
#import "AreaInstance.h"
#import "MainViewController.h"
#import "UIImage+ImageWithColor.h"
#import "WXApi.h"

@interface AppDelegate ()

@end

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application willFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    [[SynacInstance sharedInstance] synacData];
    [[AreaInstance sharedInstance] synacData];
    return YES;
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    // Override point for customization after application launch.
    [[UINavigationBar appearance] setBarTintColor:[Utilits colorWithHexString:@"#FF6700"]];
    [[UINavigationBar appearance] setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:
                                                          [UIColor whiteColor], NSForegroundColorAttributeName,
                                                          [UIFont fontWithName:@"AmericanTypewriter-Bold" size:19], NSFontAttributeName,
                                                          nil]];
    [[UINavigationBar appearance] setTintColor:[UIColor whiteColor]];
//    [[UINavigationBar appearance] setShadowImage:[UIImage imageWithColor:[UIColor clearColor]]];
    
    [[NSUserDefaults standardUserDefaults] removeObjectForKey:kRemoteNotificationKey];
    if (SYSTEM_VERSION_LESS_THAN(@"8")) {
        [application registerForRemoteNotificationTypes:UIRemoteNotificationTypeBadge | UIRemoteNotificationTypeSound | UIRemoteNotificationTypeAlert];
    }else {
        UIUserNotificationSettings *notiSet = [ UIUserNotificationSettings settingsForTypes:(UIUserNotificationTypeBadge | UIUserNotificationTypeAlert | UIRemoteNotificationTypeSound) categories:nil];
        [application registerUserNotificationSettings:notiSet];
        [application registerForRemoteNotifications];
    }
    //向微信注册APP
    NSDictionary *systemInfoDict = [[NSBundle mainBundle] infoDictionary];
    NSString *appstoreBundleID = [systemInfoDict objectForKey:@"CFBundleIdentifier"];
    if ([appstoreBundleID isEqualToString:AppStoreBundleID]) {
        [WXApi registerApp:@"wx4b642d81abd7eda4"];
    }else if([appstoreBundleID isEqualToString:EnterpriseBundleID]){
    
        [WXApi registerApp:@"wxfd1fb1381000cd53"];
    
    }
    
   
    return YES;
}

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
    
    
    DLog(@"deviceToken == %@",deviceToken);
    if (deviceToken != nil) {
        [[NSUserDefaults standardUserDefaults] setObject:deviceToken forKey:kDeviceTokenKey];
        [[NSUserDefaults standardUserDefaults] synchronize];
    }
}

- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error {
    DLog(@"%@",error);
}

- (void)application:(UIApplication *)application didRegisterUserNotificationSettings:(UIUserNotificationSettings *)notificationSettings {
    DLog(@"%@",notificationSettings);
}

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult result))handler {

        [[NSNotificationCenter defaultCenter] postNotificationName:kClearBageNotification object:nil];
        
        UserInstance *uIns = [UserInstance sharedInstance];
        [[NSUserDefaults standardUserDefaults] setObject:userInfo forKey:kRemoteNotificationKey];
        [[NSUserDefaults standardUserDefaults] synchronize];
        if (uIns.login) {
            [[PushInstance sharedInstance] handlePushMessage];
        }
}
#pragma -mark 微信分享功能
//- (void)sendTextContent
//{
//    SendMessageToWXReq* req = [[SendMessageToWXReq alloc] init];
//    req.text = @"APP微信分享测试文本,无任何实际含义,可能会占用一点点朋友圈的动态,各好友请自动忽略,嘎嘎";
//    req.bText = YES;
//    req.scene = WXSceneTimeline;
//    
//    [WXApi sendReq:req];
//}

- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation
{
    
    return [WXApi handleOpenURL:url delegate:self];
    
}
- (void)onResp:(BaseResp *)resp
{
    if ([resp isKindOfClass:[SendMessageToWXResp class]]) {
        
        
        if (resp.errCode == WXErrCodeUserCancel) {
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"分享通知" message:@"已取消分享" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil, nil];
            [alert show];
        }
        if (resp.errCode == WXSuccess) {
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"分享通知" message:@"分享成功" delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil, nil];
            [alert show];
        }
        
    }

}
- (void)applicationWillResignActive:(UIApplication *)application {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application {
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
    if (application.applicationIconBadgeNumber > 0) {
        [[NSNotificationCenter defaultCenter] postNotificationName:kClearBageNotification object:nil];
    }
    
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    
}

- (void)applicationWillTerminate:(UIApplication *)application {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
