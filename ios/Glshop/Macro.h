//
//  Macro.h
//  Glshop
//
//  Created by River on 14-11-5.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#ifndef Glshop_Macro_h
#define Glshop_Macro_h

#define UIColorFromRGB(rgbValue) [UIColor colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0 green:((float)((rgbValue & 0xFF00) >> 8))/255.0 blue:((float)(rgbValue & 0xFF))/255.0 alpha:1.0]

#define ColorWithHex(hexStr) [Utilits colorWithHexString:hexStr]

#define GLTextCommenColor RGB(100, 100, 100, 1)

//颜色和透明度设置
#define RGBA(r,g,b,a)               [UIColor colorWithRed:(float)r/255.0f green:(float)g/255.0f blue:(float)b/255.0f alpha:a]
#define  strFormatForInt(i) [NSString stringWithFormat:@"%d", (int)i]
#define  strFormatForFloat(f) [NSString stringWithFormat:@"%.1f", (float)f]
#define IntToNSNumber(i)     [NSNumber numberWithInt:i]

#define CJBtnColor RGB(27, 75, 164, 1)

#define DFFont(s) [UIFont systemFontOfSize:(float)s]
#define DFBoldFont(s) [UIFont fontWithName:@"Helvetica-Bold" size:(float)s]

#define mainStoryBoard   [UIStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]]
#define PublicPushVC(VCIdName) ([[UIStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]]instantiateViewControllerWithIdentifier:(VCIdName)])

#define FommatString(a,b) [NSString stringWithFormat:@"%@%@",a,b];

#define HUD(string)  [[HUDHelper sharedInstance] tipMessage:string delay:1.2]

#ifdef DEBUG
#define DebugLog(fmt, ...) NSLog((@"[%s Line %d] " fmt), __PRETTY_FUNCTION__, __LINE__, ##__VA_ARGS__)

#else

#define DebugLog(...)

#endif

#pragma mark -  版本判断
#define SYSTEM_VERSION_LESS_THAN(version) ([[[UIDevice currentDevice] systemVersion] compare:version options:NSNumericSearch] == NSOrderedAscending)

#define IOS7  ([[[UIDevice currentDevice] systemVersion] floatValue]>=7.0)
#define SCREEN_HEIGHT ([UIScreen mainScreen].bounds.size.height)//屏高
#define SCREEN_WIDTH ([UIScreen mainScreen].bounds.size.width)//屏宽

//角度与弧度转换
#define degreesToRadian(x) (M_PI * (x) / 180.0)
#define radianToDegrees(x) (180.0 * (x) / M_PI)

//判断设备是否IPHONE5
#define iPhone4 ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(640, 960), [[UIScreen mainScreen] currentMode].size) : NO)

#define iPhone5 ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? CGSizeEqualToSize(CGSizeMake(640, 1136), [[UIScreen mainScreen] currentMode].size) : NO)

#define iPhone6 ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? (CGSizeEqualToSize(CGSizeMake(750, 1334), [[UIScreen mainScreen] currentMode].size) || CGSizeEqualToSize(CGSizeMake(640, 1136), [[UIScreen mainScreen] currentMode].size)) : NO)

#define iPhone6plus ([UIScreen instancesRespondToSelector:@selector(currentMode)] ? (CGSizeEqualToSize(CGSizeMake(1125, 2001), [[UIScreen mainScreen] currentMode].size) || CGSizeEqualToSize(CGSizeMake(1242, 2208), [[UIScreen mainScreen] currentMode].size)) : NO)

#define RGB(r,g,b,a) [UIColor colorWithRed:r/255.0 green:g/255.0 blue:b/255.0 alpha:a]

#define DFGlayBackColor RGB(242.f, 242.f, 242.f, 1.f)

#define kHDebug DebugLog(@"json%@----string%@",operation.responseJSON,operation.responseString)
#define kASIResultLog DebugLog(@"json%@-------jsonString%@",responseData,request.responseString);

#define Device_WIDTH ((([UIApplication sharedApplication].statusBarOrientation == UIInterfaceOrientationPortrait) || ([UIApplication sharedApplication].statusBarOrientation == UIInterfaceOrientationPortraitUpsideDown)) ? [[UIScreen mainScreen] bounds].size.width : [[UIScreen mainScreen] bounds].size.height)


#define AppStoreBundleID @"com.jiangsuguoli.glshopappstore"
#define EnterpriseBundleID @"com.jiangsuguoli.glshopapp"


#pragma mark -
#pragma mark -  系统控件默认值
#define kStatusBarHeight        (20.f)
#define kNavagtionBarHeight     (44.f)

#define kCellLeftEdgeInsets     (15.f)

#define kTopBarHeight           (64.f)

#define kBottomBarHeight        (49.f)

#define kCellDefaultHeight      (44.f)

#define kEnglishKeyboardHeight  (216.f)
#define kChineseKeyboardHeight  (252.f)

#pragma mark - Font
#define FontSystem(x) [UIFont systemFontOfSize:x]
#define FontBoldSystem(x) [UIFont boldSystemFontOfSize:x]
#define kCellTextLabelFont [UIFont systemFontOfSize:15.f]

#pragma mark - 图片
// PNG JPG 图片路径
#define PNGPATH(NAME)           [[NSBundle mainBundle] pathForResource:[NSString stringWithUTF8String:NAME] ofType:@"png"]
#define JPGPATH(NAME)           [[NSBundle mainBundle] pathForResource:[NSString stringWithUTF8String:NAME] ofType:@"jpg"]
#define PATH(NAME, EXT)         [[NSBundle mainBundle] pathForResource:(NAME) ofType:(EXT)]

// 加载图片
#define PNGIMAGE(NAME)          [UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:(NAME) ofType:@"png"]]
#define JPGIMAGE(NAME)          [UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:(NAME) ofType:@"jpg"]]
#define IMAGE(NAME, EXT)        [UIImage imageWithContentsOfFile:[[NSBundle mainBundle] pathForResource:(NAME) ofType:(EXT)]]

#define Strong @property (nonatomic, strong)

#define FomartObj @"%@"

#endif
