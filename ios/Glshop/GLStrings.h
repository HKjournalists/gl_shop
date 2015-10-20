//
//  GLStrings.h
//  Glshop
//
//  Created by River on 15-1-28.
//  Copyright (c) 2015年 appabc. All rights reserved.
//

#ifndef Glshop_GLStrings_h
#define Glshop_GLStrings_h

#pragma mark - Globe string
//企业版的版本号,每一次更新版本就version+1;
static NSString *version = @"24";


#pragma mark - Globe string
static NSString *globe_sure_str = @"确定";
static NSString *globe_cancel_str = @"取消";
static NSString *globe_time_dateWithHour = @"yyyy-MM-dd HH:mm:ss";
static NSString *globe_time_date = @"yyyy-MM-dd";
static NSString *glVersionString = @"1.1.4";
static NSString *globe_name_str = @"姓名";
static NSString *globe_phoneNum_str = @"联系电话";
static NSString *globe_reqNum_str = @"需求黄砂数量";
static NSString *globe_remark_str = @"备注信息";

#pragma mark - 单位中文名
static NSString *unit_tun = @"吨";
static NSString *unit_per_price_tun = @"元/吨";

#pragma mark - 产品相关
static NSString *glProduct_top_stone_name = @"石子";
static NSString *glProduct_top_send_name = @"黄砂";
static NSString *glProduct_top_stone_code = @"G001";
static NSString *glProduct_top_send_code = @"G002";
static NSString *glProduct_goods_info = @"货物信息";
static NSString *glProduct_goods_category = @"分类";
static NSString *glProduce_goods_stand = @"规格";
static NSString *glProuct_goods_detailStand = @"货物详细规格";
static NSString *glProduct_select = @"选择";

static NSString *glProduct_goods_color = @"货物颜色";
static NSString *glProduct_goods_place = @"货物产地";

#pragma mark - 我的合同
static NSString *contract_view_title = @"查看合同";
static NSString *contract_processing_title = @"进行中的合同";
static NSString *contract_tip_three_sure = @"合同草案形成后15分钟内必须确认，过期视为无效";
static NSString *contract_tap_sure_tip = @"您正在确认一笔交易电子合同，请仔细查阅，确认后平台将开始冻结您的交易保证金，以保障您的交易权益。";
static NSString *contract_weait_sure_tap_cancel_tip = @"您正在取消合同，请再次确认，取消后该合同将即刻失效。";
static NSString *contract_single_cancel_tip = @"您正在单方面取消合同，确认取消后您的本次交易保证金将被赔偿给对方。";
static NSString *const contract_tip_cancel_successgl = @"您单方面取消了合同，您的交易保证金将被赔偿给对方。";
static NSString *const contract_move_alert_toEnd = @"请确认是否转移，确认后该合同将移至已结束的合同，请知悉！";
static NSString *const cancelWatiSureContract = @"您已成功取消该合同，该合同已失效。";
static NSString *const contract_expiry_moveToEnd = @"该合同已失效，转入已结束的合同，请知悉。";
static NSString *const contract_both_sure_tip = @"该合同双方确认成功已正式生效，转入正在进行中的合同，接下来请执行合同的相关进度。";
static NSString *const contract_surePayMoney = @"您的确认信息在卖方确认以后，平台将帮助完成您的实际货款支付。";
static NSString *const contract_sellerSurePayment = @"确认后，平台将按照实收金额将货款汇入您的平台货款账户。";
static NSString *const contract_tip_sellerSurePayment = @"后续平台将按实际金额将货款打入您的平台货款账户，敬请留意。";
static NSString *const contract_sure_applyArbite = @"请确认是否申请仲裁，申请仲裁后合同将被冻结。";
static NSString *const contract_applyArbiteTitle = @"申请仲裁";

static NSString *contract_invalid_timeout = @"15分钟有效期限已失效";
static NSString *contract_invalid_cancle_for_me = @"无效.己方已取消";
static NSString *contract_invalid_cancle_for_opposite = @"无效.对方已取消";
static NSString *contract_timout_for_both = @"无效.双方逾期未确认";
static NSString *contract_timeout_for_me = @"无效.己方逾期未确认";
static NSString *contract_timeout_for_oppsite = @"无效.对方逾期未确认";

static NSString *contract_valid_waitMe_sure = @"有效.对方已确认,请尽快确认";
static NSString *contract_valid_waitOpposite_sure = @"有效.等待对方确认";
static NSString *contract_valid_waitBoth_sure = @"有效.请尽快确认";

static NSString *contract_valid_wait_pay = @"有效.未付款";
static NSString *const contract_buyer_supply_arbitrate = @"有效.买方已申请了平台仲裁";
static NSString *contract_valid_payed = @"有效.已向平台付货款";
static NSString *const contract_bussiness_freez = @"交易冻结";
static NSString *const contract_wait_seller_post = @"等待货到后实际确认货物并确认实付金额";
static NSString *contract_valid_wait_pay_seller = @"有效.买家正向平台付货款";
static NSString *contract_pay_tip = @"请速向平台付货款";
static NSString *contract_send_timely = @"请按时送货";
static NSString *const contract_valid_arrive_money = @"有效.货款已到平台";
static NSString *const contract_seller_post = @"请按时送货并配合抽样验货";
static NSString *const contract_post_sure = @"有效.已向卖方提交了实付货款确认";
static NSString *const contract_wait_seller_sure = @"等待卖方最终确认实付货款金额";
static NSString *const contract_valid_buyer_posted = @"有效.买方已提交了实付货款确认";
static NSString *const contract_sure_recive_money = @"请确认实收货款金额";
static NSString *const contract_did_sure_recive = @"有效.您已确认了实收货款";
static NSString *const contract_seller_sure = @"有效.卖方已确认了实收货款";
static NSString *const contract_seller_post_arbitrate = @"有效.卖方已申请了平台仲裁";
static NSString *const contract_buyer_post_arbitrate = @"有效.买方已申请了平台仲裁";
static NSString *const contract_self_post_arbitrate = @"有效.您已申请了平台仲裁";
static NSString *const contract_tip_comman = @"交易正常结束，请评价一下交易中的对方。";
static NSString *const contract_buisness_end = @"交易结束";
static NSString *const contract_buisness_normal_end = @"交易正常结束";
static NSString *const tip_contract_deleteSuccess = @"该合同已删除成功！";
static NSString *const tip_review_contract_success = @"您已评价成功！您的本次交易已结束，感谢你使用长江电商交易平台。";

static NSString *const contract_tip_time_header = @"货款支付期限为：";
static NSString *const contract_tipToPay = @"如果您在此之前未向平台支付货款，保证金赔偿给卖方。";
static NSString *const contract_tipToSellerRepay = @"如果在此之前买方未向平台支付货款将视为违约，本次交易的保证金将赔偿给您。";
static NSString *const contract_payed_already = @"您已向平台付支付货款";
static NSString *const contract_payed_already_buyer = @"买方已向平台付支付货款";
static NSString *const contract_self_arbitrate = @"您已经申请了平台仲裁。";
static NSString *const contract_freeze_arbitrate_detail = @"您已申请仲裁，稍后会有平台客服与您取得联系，敬请耐心等待。交易冻结，仲裁结果将由平台通知您。";
static NSString *const contract_freeze_arbitrate_seller_detail = @"卖家已申请仲裁，稍后会有平台客服与您取得联系，敬请耐心等待。交易冻结，仲裁结果将由平台通知您。";
static NSString *const contract_freeze_arbitrate_buyer_detail = @"买家已申请仲裁，稍后会有平台客服与您取得联系，敬请耐心等待。交易冻结，仲裁结果将由平台通知您。";
static NSString *const contract_seller_post_arbi = @"卖方已经申请了平台仲裁。";
static NSString *const contract_buyer_post_arbi = @"买方已经申请了平台仲裁。";
static NSString *const contract_payed_toSeller = @"您已经向卖方提交了实付货款确认。";
static NSString *const contract_buyer_surePayDet = @"您已经向卖方提交了实付货款确认。卖方确认实付货款后，平台将按实际金额支付货款并将货款返回给您的账户。";
static NSString *const contract_payer_tip_action0 = @"买方已向您提交了实付货款确认。";
static NSString *const contract_payer_tip_action3 = @"请您按时送货，货到后请配合买方实际确认货物，并根据实际情况确认应收货款金额。";
static NSString *const contract_payer_tip_action = @"请您在72小时内尽快确认实收金额（您可以选择确认同意或者是申请平台仲裁），若超过72小时逾期未操作，平台将视为默认同意，平台将按实际金额将货款打入您的平台货款账户。";
static NSString *const contract_tip_action1 = @"等待货到后，请根据合同记载事项，实际确认货物并最终确认实际应付货款金额。";
static NSString *const contract_tip_action2 = @"卖方将在72小时内确认实付货款，若逾期未确认，平台将按实际金额支付货款并将货款余额返回给您的账户。";
static NSString *const contract_buyer_canceled = @"买方已经取消了交易";
static NSString *const contract_seller_canceled = @"卖方已经取消了交易。";
static NSString *const contract_seller_canceled_self = @"您已经取消了交易。";
static NSString *const contract_pay_outTime = @"由于您逾期未支付货款，合同失效。";
static NSString *const contract_pay_outTime_seller = @"买方逾期未支付货款，合同失效。";
static NSString *const contract_pay_tip_repay_toSeller = @"买方的交易保证金已被作为违约金汇入您的平台账户。";
static NSString *const contract_pay_tip_repay = @"您的交易保证金已作为违约金汇入卖方的平台账户。";
static NSString *const contract_pay_tip_repay_toBuyer = @"您的交易保证金已作为违约金汇入买方的平台账号";
static NSString *const contract_tip_payToSeller = @"买方的交易保证金已被作为违约金汇入您的平台账户。";
static NSString *const contract_tip_payToBuyer = @"卖方的交易保证金已被作为违约金汇入您的平台账户。";
static NSString *const contract_tip_planRepay = @"平台将按实际金额支付货款并将货款余额返回给您的账户。请评价一下交易中的对方。";
static NSString *const contract_tip_planRepay_seller = @"平台已按实际金额将货款打入您的平台货款账户。请评价一下交易中的对方。";

static NSString *const contract_invalid_platform_end = @"无效.平台仲裁结束";
static NSString *const contract_buyer_pay_timeout = @"无效.买方逾期未支付货款";
static NSString *const contract_buyer_self_timeout_pay = @"无效.您逾期未支付货款";
static NSString *const contract_timeout_pay = @"无效.逾期未支付货款";
static NSString *const contract_breach_paytoSeller = @"违约金已被汇入卖方的平台账号";
static NSString *const contract_breach_payforyou = @"违约金已被汇入您的平台账号";
static NSString *const contract_invalid_buyer_cancel = @"无效.买方已经取消了交易";
static NSString *const contract_invalid_seller_cancel = @"无效.卖方已经取消了交易";
static NSString *const contract_invalid_self_cancel = @"无效.您已经取消了交易";
static NSString *const contract_invalid_seller_posted = @"无效.卖方已提交了实付货款确认";
static NSString *const contract_invalid_self_posted = @"无效.您已确认了实付货款确认";
static NSString *const contract_freeze_end = @"无效.平台仲裁结束";
static NSString *const contract_arbitrate_ended = @"平台仲裁结束";
static NSString *const contract_breach_money = @" 违约金已汇入您的平台账号";
static NSString *const contract_breach_money_toOppsite = @" 违约金已汇入对方的平台账号";

static NSString *const contract_satus_freez_money = @"冻结保证金";
static NSString *const contract_status_post = @"按时送货";
static NSString *const contract_status_goodsSure = @"货物实际确认";
static NSString *const contract_status_pay = @"向平台支付货款";
static NSString *const contract_status_pay_sure = @"实付金额确认";
static NSString *const contract_status_recive_sure = @"实收金额确认";
static NSString *const contract_status_nopro = @"无异议";
static NSString *const contract_status_havepro = @"有异议";
static NSString *const contract_status_paltarbtti = @"平台仲裁解决";
static NSString *const contract_status_platform_pay = @"平台实际支付";
static NSString *const contract_status_platform_statement = @"平台账单结算";
static NSString *const contract_status_end = @"交易结束";
static NSString *const contract_status_commen = @"合同评价";
static NSString *const contract_status_beEnded = @"被交易结束";
static NSString *const contract_status_outtime = @"逾期未支付货款";
static NSString *const contract_status_plating = @"平台介入";
static NSString *const contract_status_endHandling = @"合同结束处理";
static NSString *const contract_status_freez = @"交易冻结";

#pragma mark- 支付相关
static NSString *pay_pgoods_balance = @"货款账户余额";
static NSString *pay_needPay_money = @"需支付货款金额";
static NSString *pay_input_money = @"请输入金额";
static NSString *pay_offline_bankaddres_k = @"开户行";
static NSString *pay_offline_bankaddres_v = @"上海浦东发展银行靖江工业园区支行";
static NSString *pay_offline_bankname_k = @"开户人姓名";
static NSString *pay_offline_bankname_v = @"江苏国立网络技术有限公司";
static NSString *pay_offline_bank_k = @"银行帐号";
static NSString *pay_offline_bank_v = @"6701 0154 7400 08882";
static NSString *pay_offline_tel_k = @"客服电话";
static NSString *pay_offline_tel_v = @"4009616816";

static NSString *pay_offline_title = @"请联系我们的工作人员进行上门收取或前往我们的柜台进行现场充值交易保证金";
static NSString *pay_offline_tel = @"客服电话:";
static NSString *pay_offline_address_v = @"江苏省靖江市南环东路88号恒天商务广场4楼";
static NSString *pay_offline_address_k = @"联系地址:";


#pragma mark - 评价相关
static NSString *const evaBusinessSatified = @"交易满意度";
static NSString *const evaBusinessInterd = @"交易诚信度";

#pragma mark - 个人资料
static NSString *const profile_persion = @"个人";
static NSString *const profile_company = @"企业";
static NSString *const profile_bota = @"船舶";

#pragma mark- 短信相关
static NSString *sms_valide = @"短信验证:";
static NSString *sms_post_to = @"验证码发送至:";
static NSString *sms_input = @"请输入验证码";
static NSString *sms_get = @"获取验证码";

#pragma mark- Placehold String
static NSString *placehold_input_login_pw = @"请输入登录密码";
static NSString *placehold_input_authcode = @"请输入验证码";
static NSString *placehold_input_write = @"填写";
static NSString *placehold_write_optional = @"选填";
static NSString *placehold_perPrice = @"请输入单价";
static NSString *placehold_total = @"请输入吨位";
static NSString *const placehold_indrouce = @"请输入企业简介";
static NSString *const placehold_get_money = @"请输入提现金额";
static NSString *const placehold_money = @"请输入金额";

#pragma mark- Button Title String
static NSString *btnTitle_sure_pay = @"确认支付";
static NSString *const btntilte_sure_rightnow = @"立即确认";
static NSString *const btntitle_repost_code = @"重发验证码";
static NSString *const btntitle_cancel_contract = @"取消合同";
static NSString *const btntitle_move_end = @"移至已结束合同";
static NSString *const btntitle_sure_pay = @"货物与货款实际确认";
static NSString *const btntitle_pay_plan = @"向平台支付货款";
static NSString *const btntitle_pay_actual_sure = @"实收货款确认";
static NSString *const btntitle_contract_comman = @"合同评价";
static NSString *const btntitle_view_comman = @"查看评价";
static NSString *const btntitle_review_mycontract = @"查看我的合同";
static NSString *const btntitle_waite_handle = @"待处理";
static NSString *const btntitle_contract_ended = @"交易已结束";
static NSString *const btntitle_contract_frzee = @"交易冻结";
static NSString *const btntitle_contract_waite_suer = @"等待对方确认";
static NSString *const btntitle_contract_order = @"合同结算账单";
static NSString *const btntitle_post_toSellerSure = @"提交给卖方确认实付金额";
static NSString *const btntitle_apply_plan = @"申请平台仲裁";
static NSString *const btntitle_sure_agree = @"确认并同意";
static NSString *const btntitle_logout = @"退出登录";
static NSString *const btntitle_next = @"下一步";

#pragma mark- ViewController title
static NSString *vc_title_myContract = @"我的合同";
static NSString *vc_title_payment = @"支付交易货款";
static NSString *const vc_title_contract_address = @"合同交易地址";

#pragma mark- Cell text
static NSString *const cell_address_depth = @"卸货地港口水深度(单位:米)";
static NSString *const cell_boat_tun = @"可停泊载重船吨位(单位:吨)";
static NSString *const cell_business_addressInfo = @"交易地址信息";
static NSString *const cell_companyIndrouce = @"用户简介";
static NSString *const cell_noIndrouce = @"暂无简介";
static NSString *const cell_NoaddressInfo = @"暂无地址信息";
static NSString *const cell_select_address = @"请选择交易地址";
static NSString *const cell_address = @"交易地址";
static NSString *const cell_margin_unfreez = @"交易保证金解冻";
static NSString *const cell_platform_poundage = @"平台手续费";
static NSString *const cell_payment_reback = @"货款返还";
static NSString *const cell_breach_money = @"被违约赔偿金额";
static NSString *const cell_margin_del = @"违约交易保证金扣除";
static NSString *const cell_plan_handlingCharge = @"平台手续费";
static NSString *const cell_actual_recive = @"实收货款";
static NSString *const cell_paln_final_statement = @"平台对我的结算：";
static NSString *const cell_temp_none = @"暂无";

#pragma mark - Img Name
static NSString *const imgName_whiltebg = @"wallet_beijing";
static NSString *const imgName_btnBg_lightGray = @"Buy_sell_publish";

#pragma mark - Alert String
static NSString *const alert_contract_price = @"实际货物单价不能高于合同单价";
static NSString *const alert_contract_total = @"实际货物总量不能高于合同总量";
static NSString *const alert_tip_delete_contract = @"该合同已无效，请确认是否删除。";
static NSString *const alert_tip_no_waitsureContract = @"暂时没有需要您确认的合同，赶快去发布您的供求信息吧，获取更多的生意机会。";
static NSString *const alert_tip_no_gongqiu = @"您还没有任何供求信息，赶快发布您的供求。让更多潜在生意伙伴和您谈生意，争取更多的生意机会！";

#pragma mark -团购

static NSString *description_sand1 = @"       洞庭湖黄砂为800里洞庭湖水下10几米底层开采清洗而成,符合国家的《建筑用砂》(GB/T14684-2001)标准。";
static NSString *description_sand2 = @"       蕴涵量丰富,颗粒饱满、圆润、大小均匀,含泥少、无毒无污染、抗腐耐酸碱强,品质上乘的优质洞庭湖黄砂,是房地产,高铁,轻轨,地铁,路桥和管桩公司等大型工程的主要建筑材料。";
static NSString *navigationTitle = @"超低价团购地1期";
static NSString *description_1 = @"钜惠大酬宾,低于";
static NSString *description_2 = @"市场价";
static NSString *description_3 = @", 数量有限,先到先得!";
static NSString *free_book_str = @"立即免费约定";
static NSString *join_gift = @"参与有奖";
static NSString *join_desc = @"凡是现场来宾均会收到长江电商提供的惊喜大礼包!";
static NSString *weChat_gift = @"微信红包";
static NSString *weChat_desc = @"凡参加报名团购成功且关注长江电商微信公众号: wxcjds 回复【超低价团购】即有一定机会获得微信红包!";
static NSString *activity_desc1 = @"团购流程：免费报名》电话邀约参团》现场参加团购》抽取奖品;";
static NSString *activity_desc2 = @"该活动仅限在长江电商平台注册成功并认证成功的用户参与团购;";
static NSString *activity_desc3 = @"在法律许可的合法范畴内, 本活动最终解释权归长江电商所有,24小时免费客服热线：400-9616-816;";
static NSString *activity_desc4 = @"交易地点:江苏靖江金马一号浮吊;";
static NSString *remarkPlacehoder_str = @"请输入(限200字内)";

#pragma mark - 其它
static NSString *cancel_success = @"取消成功！";
static NSString *sure_success = @"确认成功！";
static NSString *pay_success = @"支付成功！";
static NSString *operateSuccess = @"操作成功！";
static NSString *hudDeleteSuccess = @"删除成功";
static NSString *otherofSetUp = @"设置";
static NSString *deleteContract = @"合同删除";

static NSString *const tipMoveToEndSuccessContent = @"该合同已移至已结束的合同，请知悉！";

#pragma mark - 服务器接口key
static NSString *const path_key_companyId = @"cid";
static NSString *const path_key_fid = @"fid";
static NSString *const path_key_contractId = @"OID";
static NSString *const path_key_contract_operType = @"operateType";
static NSString *const path_key_password = @"password";
static NSString *const path_key_authcode = @"validateCode";

#endif
