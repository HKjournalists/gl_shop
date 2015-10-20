//
//  MessageTableViewCell.m
//  Glshop
//
//  Created by River on 15-2-27.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "MessageTableViewCell.h"
#import "MessageModel.h"

@interface MessageTableViewCell ()

@property (strong, nonatomic) IBOutlet UIImageView *markImgView;
@property (strong, nonatomic) IBOutlet UILabel *messageTypeLabel;
@property (strong, nonatomic) IBOutlet UILabel *contentLabel;
@property (strong, nonatomic) IBOutlet UILabel *timeLabel;

@end

@implementation MessageTableViewCell

- (void)awakeFromNib {
    // Initialization code
}

- (void)setMessage:(MessageModel *)message {
    if (_message != message) {
        _message = message;
        
        NSInteger read = [_message.status[DataValueKey] integerValue];
        if (read == 1) {
            UIColor *color = [UIColor colorWithRed:153/255.0 green:153/255.0 blue:153/255.0 alpha:1.0];
            _markImgView.image = nil;
            _messageTypeLabel.textColor = color;
            _contentLabel.textColor = color;
            _timeLabel.textColor = color;
            //[_markImgView.image].hidden = YES;
        }else {
            _markImgView.image = PNGIMAGE(@"news_drop@2x");
            
            UIColor *color = [UIColor colorWithRed:153/255.0 green:153/255.0 blue:153/255.0 alpha:1.0];
            UIColor *scolor = [UIColor colorWithRed:100/255.0 green:100/255.0 blue:100/255.0 alpha:1.0];
            UIColor *ccolor = [UIColor colorWithRed:51/255.0 green:51/255.0 blue:51/255.0 alpha:1.0];
            _messageTypeLabel.textColor = scolor;
            _contentLabel.textColor = ccolor;
            _timeLabel.textColor = color;
            
            
        }
        
        _messageTypeLabel.text = _message.type[DataTextKey];
        _contentLabel.text = _message.content;
        _timeLabel.text = _message.createtime;
        
        DebugLog(@"build:%@",_message.content);
    }
}

@end
