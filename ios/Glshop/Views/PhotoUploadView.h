//
//  PhotoUploadView.h
//  Glshop
//
//  Created by River on 14-12-23.
//  Copyright (c) 2014年 appabc. All rights reserved.
//  图片上传视图，针对本项目（实物照片、卸货地址照片）

#import <UIKit/UIKit.h>

@class PhotoUploadView;
@protocol UploadImageDelete <NSObject>

- (void)uploadImageSuccess:(NSString *)imgsId uploadView:(PhotoUploadView *)uploadView;
- (void)uploadImageFaile:(PhotoUploadView *)uploadView;

@optional
- (void)pickerImageDidReplace;

@end

@class ASINetworkQueue;
@interface PhotoUploadView : UIView <UINavigationControllerDelegate,UIImagePickerControllerDelegate,UIActionSheetDelegate>
{
@private
    NSMutableArray *_imageArray;
}

@property (nonatomic, copy) NSString *defaultImgName;

@property (nonatomic, strong) NSMutableArray *imageArray;
@property (nonatomic, strong) NSArray *imgIdArray;
@property (nonatomic, strong) NSArray *imageUrlArray;
@property (nonatomic, assign) id <UploadImageDelete> delegate;
@property (nonatomic, strong,readonly) ASINetworkQueue *netQueue;

/**
 *@brief 当添加完一张图片后，是否不允许添加另一张 默认为NO
 */
@property (nonatomic, assign) BOOL NotshowNextPhoto;

- (void)uploadImage;

@end
