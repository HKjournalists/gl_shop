//
//  RatingOpertViewController.m
//  Glshop
//
//  Created by River on 15-2-28.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#import "RatingOpertViewController.h"
#import "TPFloatRatingView.h"
#import "REPlaceholderTextView.h"
#import "ContractProcessDetailViewController.h"
#import "TipSuccessViewController.h"

@interface RatingOpertViewController () <UITableViewDataSource,UITableViewDelegate,UITextViewDelegate>

@property (strong, nonatomic) IBOutlet UITableView *tableView;
@property (strong, nonatomic) IBOutlet UIButton *postBtn;
@property (nonatomic, strong) TPFloatRatingView *ratingViewSatisfied;
@property (nonatomic, strong) TPFloatRatingView *ratingViewIntergrity;
@property (nonatomic, strong) REPlaceholderTextView *textView;

@end

@implementation RatingOpertViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    self.view.backgroundColor = self.tableView.backgroundColor;
    _tableView.contentInset = UIEdgeInsetsMake(-15, 0, 0, 0);
    self.title = @"评价对方";
    UIImage *image = [UIImage imageNamed:BlueButtonImageName];
    image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
    [_postBtn setBackgroundImage:image forState:UIControlStateNormal];
}

#pragma mark - UITableView DataSource/Delegate
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 2;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return section == 0 ? 3 : 2;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    if (indexPath.section == 0 && indexPath.row == 0) {
        return 55;
    }
    
    if (indexPath.section == 1) {
        if (indexPath.row) {
            return 80;
        }
    }
    return 44;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:nil];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    
    if (indexPath.section == 0) {
        if (indexPath.row == 0) {
            UIImage *image = [UIImage imageNamed:@"agreement_beijing"];
            UIImageView *bgView = [[UIImageView alloc] initWithFrame:cell.bounds];
            bgView.vheight = 55;
            bgView.image = [image resizableImageWithCapInsets:UIEdgeInsetsMake(10, 10, 10, 10) resizingMode:UIImageResizingModeStretch];
            [cell.contentView addSubview:bgView];
            
            cell.textLabel.text = @"给对方的评价";
            cell.detailTextLabel.text = @"(如果您不评价，7天后系统会默认给好评)";
            cell.detailTextLabel.textColor = [UIColor lightGrayColor];
        }else if (indexPath.row == 1) {
            cell.imageView.image = [UIImage imageNamed:@"appraise_niming"];
            cell.textLabel.font = [UIFont systemFontOfSize:15.f];
            cell.textLabel.text = evaBusinessSatified;
            [cell.contentView addSubview:self.ratingViewSatisfied];
        }else if (indexPath.row == 2) {
            cell.imageView.image = [UIImage imageNamed:@"appraise_chengxin"];
            cell.textLabel.font = [UIFont systemFontOfSize:15.f];
            cell.textLabel.text = @"交易诚信度";
            [cell.contentView addSubview:self.ratingViewIntergrity];
        }
    }else if (indexPath.section == 1) {
        if (indexPath.row == 0) {
            cell.textLabel.text = @"详细评价";
        }else {
            _textView = [[REPlaceholderTextView alloc] initWithFrame:CGRectMake(15, 0,cell.vwidth-20, 80)];
            _textView.placeholder = @"您的评价会对其他交易者提供非常重要的参考，请务必认真、公正填写。";
            _textView.returnKeyType = UIReturnKeyDone;
            _textView.font = [UIFont systemFontOfSize:15.f];
            _textView.delegate = self;
            _textView.layer.borderColor = [UIColor lightGrayColor].CGColor;;
            [cell addSubview:_textView];
        }
    }
    
    
    return cell;
}

#pragma mark - Private
- (TPFloatRatingView *)createRatingView {
    TPFloatRatingView *ratingView = [[TPFloatRatingView alloc] initWithFrame:CGRectMake(self.view.vwidth-130, 8.5, 120, 30)];
    ratingView.emptySelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star-huise"];
    ratingView.fullSelectedImage = [UIImage imageNamed:@"Buy_sell_icon_star_huangse"];
    ratingView.editable = YES;
    
    return ratingView;
}

#pragma mark - Getter
- (TPFloatRatingView *)ratingViewSatisfied {
    if (!_ratingViewSatisfied) {
        _ratingViewSatisfied = [self createRatingView];
        _ratingViewSatisfied.rating = 5.f;
    }
    return _ratingViewSatisfied;
}

- (TPFloatRatingView *)ratingViewIntergrity {
    if (!_ratingViewIntergrity) {
        _ratingViewIntergrity = [self createRatingView];
        _ratingViewIntergrity.rating = 5.f;
    }
    return _ratingViewIntergrity;
}

#pragma mark - UITextView Delegate
- (BOOL)textView:(UITextView *)textView shouldChangeTextInRange:(NSRange)range replacementText:(NSString *)text {
    if ([text isEqualToString:@"\n"]) {
        [textView resignFirstResponder];
        return NO;
    }
    return YES;
}

- (IBAction)postRating:(id)sender {
    ContractProcessDetailViewController *vc = [self findDesignatedViewController:[ContractProcessDetailViewController class]];
    BOOL isBuyer = [vc.contractModel.saleType[DataValueKey] integerValue] == ORDER_TYPE_BUY;
    NSString *cid = isBuyer ? vc.contractModel.sellerid : vc.contractModel.buyerid;
    NSString *oid = vc.contractModel.contractId;
    NSNumber *sati = [NSNumber numberWithFloat:_ratingViewSatisfied.rating];
    NSNumber *ins = [NSNumber numberWithFloat:_ratingViewIntergrity.rating];
    NSMutableDictionary *params = [NSMutableDictionary dictionaryWithObjectsAndKeys:cid,@"CID",oid,@"OID",sati,@"satisfaction",ins,@"credit", nil];
    if (_textView.text.length) {
        [params setObject:_textView.text forKey:@"evaluation"];
    }
    
    [self showHUD];
    __block typeof(self) this = self;
    [self requestWithURL:btoEvaluateContract params:params HTTPMethod:kHttpPostMethod completeBlock:^(ASIHTTPRequest *request, id responseData) {
        [this handleNetData:responseData];
    } failedBlock:^(ASIHTTPRequest *request) {
       
    }];
}

- (void)handleNetData:(id)responseData {
    [[NSNotificationCenter defaultCenter] postNotificationName:kRefrushContractNotification object:nil];
    ContractProcessDetailViewController *pvc = [self findDesignatedViewController:[ContractProcessDetailViewController class]];
    TipSuccessViewController *vc = [[TipSuccessViewController alloc] init];
    vc.operationType = tip_reviewContract;
    vc.contractId = pvc.contractModel.contractId;
    [self.navigationController pushViewController:vc animated:YES];
}

@end
