//
//  PhotoUploadView.m
//  Glshop
//
//  Created by River on 14-12-23.
//  Copyright (c) 2014年 appabc. All rights reserved.
//

#import "PhotoUploadView.h"
#import "ASINetworkQueue.h"
#import "NetService.h"
#import "UIButton+WebCache.h"

#define BtnStartTag 200

@interface PhotoUploadView ()

@property (nonatomic, strong) ASINetworkQueue *netQueue;

@end

@implementation PhotoUploadView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        _imageArray = [NSMutableArray array];
        for (int i = 0; i < 3; i++) {

            UIButton *imageBtn = [UIButton buttonWithTip:nil target:self selector:@selector(chosePhoto:)];
            imageBtn.frame = CGRectMake(15+i*(260/3+15), 10, 260/3, 80);
            imageBtn.tag = BtnStartTag+i;
            if (i == 0) {
                UIImage *image = [UIImage imageNamed:@"address_photo"];
                [imageBtn setImage:image forState:UIControlStateNormal];
            }else if (i == 1) {
                UIImage *image = [UIImage imageNamed:@"address_photo_add"];
                [imageBtn setImage:image forState:UIControlStateNormal];
                [imageBtn setHidden:YES];
            }else if (i == 2){
                UIImage *image = [UIImage imageNamed:@"address_photo_add"];
                [imageBtn setImage:image forState:UIControlStateNormal];
                [imageBtn setHidden:YES];
            }
            
            [self addSubview:imageBtn];
        }
    }
    return self;
}

#pragma mark - Setter
- (void)setImageArray:(NSMutableArray *)imageArray {
    _imageArray = imageArray;
    if (imageArray.count > 3) {
        _imageArray = [NSMutableArray arrayWithObjects:imageArray[0],imageArray[1],imageArray[2], nil];
    }
    
    int i = 0;
    for (UIImage *image in _imageArray) {
        UIButton *btn = (UIButton *)[self viewWithTag:BtnStartTag+i];
        [btn setImage:image forState:UIControlStateNormal];
        if (i >= 0) {
            btn.hidden = NO;
        }
        i++;
    }
}

- (void)setImageUrlArray:(NSArray *)imageUrlArray {
    _imageUrlArray = imageUrlArray;
    if (imageUrlArray.count == 0) {
        return;
    }
    
    int i = 0;
    for (NSString *urlStr in _imageUrlArray) {
        NSURL *url = [NSURL URLWithString:urlStr];
        UIButton *btn = (UIButton *)[self viewWithTag:BtnStartTag+i];

        [(UIButton *)[self viewWithTag:BtnStartTag+i+1] setHidden:NO];
    
        [btn sd_setImageWithURL:url forState:UIControlStateNormal placeholderImage:[UIImage imageNamed:PlaceHodelImageName] completed:^(UIImage *image, NSError *error, SDImageCacheType cacheType, NSURL *imageURL) {
            if (image) {
                [_imageArray addObject:image];
            }
        }];
        
        i++;
    }
}

#pragma mark - Private
static long photoTag = 0;
- (void)chosePhoto:(UIButton *)btn {
    UIActionSheet *choosePhotoActionSheet;
    photoTag = btn.tag-BtnStartTag;
    
    if([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
        choosePhotoActionSheet = [[UIActionSheet alloc] initWithTitle:NSLocalizedString(@"选择头像图片", @"")
                                                             delegate:self
                                                    cancelButtonTitle:@"取消"
                                               destructiveButtonTitle:nil
                                                    otherButtonTitles:@"拍照", @"从相册获取", nil];
    } else {
        choosePhotoActionSheet = [[UIActionSheet alloc] initWithTitle:NSLocalizedString(@"选择照片", @"")
                                                             delegate:self
                                                    cancelButtonTitle:NSLocalizedString(@"cancel", @"")
                                               destructiveButtonTitle:nil
                                                    otherButtonTitles:NSLocalizedString(@"take_photo_from_library", @""), nil];
    }
    [choosePhotoActionSheet showInView:[UIApplication sharedApplication].keyWindow];
}

- (void)addImage:(UIImage *)photo {
    if (_imageArray.count == 0) {
        UIButton *btn = (UIButton *)[self viewWithTag:BtnStartTag];
        [btn setImage:photo forState:UIControlStateNormal];
        if (!_NotshowNextPhoto) {
            UIButton *twobtn = (UIButton *)[self viewWithTag:BtnStartTag+1];
            twobtn.hidden = NO;
        }
        [_imageArray addObject:photo];
    }else if (_imageArray.count >= 1 && photoTag == 0) {
        UIButton *btn = (UIButton *)[self viewWithTag:BtnStartTag];
        [btn setImage:photo forState:UIControlStateNormal];
        [_imageArray replaceObjectAtIndex:0 withObject:photo];
    }else if (_imageArray.count == 1) {
        UIButton *btn = (UIButton *)[self viewWithTag:BtnStartTag+1];
        [btn setImage:photo forState:UIControlStateNormal];
        if (!_NotshowNextPhoto) {
            UIButton *threebtn = (UIButton *)[self viewWithTag:BtnStartTag+2];
            threebtn.hidden = NO;
        }
        [_imageArray addObject:photo];
    }else if (_imageArray.count >= 2 && photoTag == 1) {
        UIButton *btn = (UIButton *)[self viewWithTag:BtnStartTag+1];
        [btn setImage:photo forState:UIControlStateNormal];
        [_imageArray replaceObjectAtIndex:1 withObject:photo];
    }else if (_imageArray.count == 2) {
        UIButton *btn = (UIButton *)[self viewWithTag:BtnStartTag+2];
        [btn setImage:photo forState:UIControlStateNormal];
        [_imageArray addObject:photo];
    }else if (_imageArray.count == 3 && photoTag == 2) {
        UIButton *btn = (UIButton *)[self viewWithTag:BtnStartTag+2];
        [btn setImage:photo forState:UIControlStateNormal];
        [_imageArray replaceObjectAtIndex:2 withObject:photo];
    }
    
}

#pragma mark - UIImagePickerControllerDelegate
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info
{
    __block typeof(self) weakSelf = self;
    [self.firstViewController dismissViewControllerAnimated:YES completion:^{
        UIImage *image = info[@"UIImagePickerControllerEditedImage"];
        if ([weakSelf.delegate respondsToSelector:@selector(pickerImageDidReplace)]) {
            [weakSelf.delegate pickerImageDidReplace];
        }
        [weakSelf addImage:image];
    }];
    
}

#pragma mark - UIActionSheet Delegate
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex {
    NSUInteger sourceType = 0;
    if([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]) {
        switch (buttonIndex) {
            case 0:
                sourceType = UIImagePickerControllerSourceTypeCamera;
                break;
            case 1:
                sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
                break;
            case 2:
                return;
        }
    } else {
        if (buttonIndex == 1) {
            return;
        } else {
            sourceType = UIImagePickerControllerSourceTypeSavedPhotosAlbum;
        }
    }
    
    UIImagePickerController *imagePickerController = [[UIImagePickerController alloc] init];
    imagePickerController.delegate = self;
    imagePickerController.allowsEditing = YES;
    imagePickerController.sourceType = sourceType;
    [self.firstViewController presentViewController:imagePickerController animated:YES completion:nil];
}

- (void)uploadImage {
    _netQueue = [ASINetworkQueue queue];
    NSMutableArray *tempIdArray = [NSMutableArray array];
    int i = 0;
    for (UIImage *image in self.imageArray) {
        NSData *data = UIImageJPEGRepresentation(image, 1);
        NSString *name = [NSString stringWithFormat:@"entityImg%d.jpeg",i];
        NSString *filePath = [[NSString documentsPath] stringByAppendingPathComponent:name];
        [data writeToFile:filePath atomically:YES];
        
        __block typeof(self) this = self;
        ASIFormDataRequest *request = [NetService uploadImgWithURL:bfileupload
                                                  HTTPMethod:kHttpPostMethod
                                               completeBlock:^(ASIHTTPRequest *request, id responseData) {
                                                   
                                                   NSArray *array = [responseData objectForKey:ServiceDataKey];
                                                   if (array.count > 0) {
                                                       NSDictionary *dic = array.firstObject;
                                                       [tempIdArray addObject:dic[@"id"]];
                                                   }else{
                                                       return;
                                                   }
                                                   if (i == this.imageArray.count-1) { // 最有最后一次图片上传成功，才进行下一步操作
 
                                                       NSString *parmStr = [tempIdArray componentsJoinedByString:@","];
                                                       this.imgIdArray = [NSArray arrayWithArray:tempIdArray];
                                                       if ([this.delegate respondsToSelector:@selector(uploadImageSuccess:uploadView:)]) {
                                                           [this.delegate uploadImageSuccess:parmStr uploadView:this];
                                                       }
 
                                                   }
                                                   
                                               } failedBlock:^{
                                                   if (i == this.imageArray.count-1) { // 只有最后一次的图片上传失败，才进行下一步操作
                                                       if ([this.delegate respondsToSelector:@selector(uploadImageFaile:)]) {
                                                           [this.delegate uploadImageFaile:this];
                                                       }
                                                    }
                                               } ];
        [request addData:data withFileName:filePath andContentType:@"image/jpeg" forKey:@"file"];
        [_netQueue addOperation:request];
        i++;
    }
    [_netQueue setMaxConcurrentOperationCount:1];
    [_netQueue go];

}

- (void)layoutSubviews {
    [super layoutSubviews];
    
    if (_defaultImgName && _imageArray.count == 0) {
        UIButton *btn = (UIButton *)[self viewWithTag:BtnStartTag];
        [btn setImage:[UIImage imageNamed:_defaultImgName] forState:UIControlStateNormal];
    }
}

@end
