/*
Navicat MySQL Data Transfer

Source Server         : remote server
Source Server Version : 50619
Source Host           : 192.168.1.223:3306
Source Database       : glshop

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2014-11-04 16:16:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for T_ACCEPT_BANK
-- ----------------------------
DROP TABLE IF EXISTS `T_ACCEPT_BANK`;
CREATE TABLE `T_ACCEPT_BANK` (
  `ID` varchar(50) NOT NULL COMMENT '收款方编号',
  `CID` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `AUTHID` int(11) DEFAULT NULL COMMENT '认证记录ID',
  `BANKCARD` varchar(32) DEFAULT NULL COMMENT '银行卡号',
  `BANKACCOUNT` varchar(50) DEFAULT NULL COMMENT '银行账户',
  `CARDUSER` varchar(50) DEFAULT NULL COMMENT '持卡人姓名',
  `CARDUSERID` varchar(50) DEFAULT NULL COMMENT '持卡人证件号',
  `BANKTYPE` varchar(50) DEFAULT NULL COMMENT '招商、建行的类型',
  `BANKNAME` varchar(50) DEFAULT NULL COMMENT '开户行',
  `ADDR` varchar(255) DEFAULT NULL COMMENT '地址',
  `REMARK` varchar(50) DEFAULT NULL COMMENT '备注',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATETIME` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATOR` varchar(50) DEFAULT NULL COMMENT '创建人',
  `STATUS` int(10) DEFAULT NULL COMMENT '状态（默认1，普通0）',
  `AUTHSTATUS` int(11) DEFAULT NULL COMMENT '认证状态',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_40` (`CID`),
  KEY `FK_Reference_au_ab` (`AUTHID`),
  CONSTRAINT `FK_Reference_40` FOREIGN KEY (`CID`) REFERENCES `T_COMPANY_INFO` (`ID`),
  CONSTRAINT `FK_Reference_au_ab` FOREIGN KEY (`AUTHID`) REFERENCES `T_AUTH_RECORD` (`AUTHID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户收款方信息即账户收款方信息管理，即一个账户有可能有多个银行账户信息，而且这个账户，需求认证.';

-- ----------------------------
-- Table structure for T_AUTH_RECORD
-- ----------------------------
DROP TABLE IF EXISTS `T_AUTH_RECORD`;
CREATE TABLE `T_AUTH_RECORD` (
  `AUTHID` int(11) NOT NULL AUTO_INCREMENT,
  `CID` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `IMGID` int(11) DEFAULT NULL COMMENT '图片表关联ID',
  `AUTHSTATUS` varchar(20) NOT NULL COMMENT '认证状态（审核中，审核通过，审核不通过）',
  `DEALSTATUS` int(11) DEFAULT NULL COMMENT '是否有人处理（主要由于后续可能存在的任务分配）',
  `CREATEDATE` datetime DEFAULT NULL COMMENT '创建时间',
  `AUTHOR` varchar(20) DEFAULT NULL COMMENT '认证人',
  `AUTHRESULT` varchar(20) DEFAULT NULL COMMENT '认证通过或不通过',
  `AUTHDATE` datetime DEFAULT NULL COMMENT '认证时间',
  `REMARK` varchar(1000) DEFAULT NULL COMMENT '备注',
  `TYPE` int(10) NOT NULL COMMENT '认证类型（企业认证、提款信息认证）',
  `ABID` varchar(50) DEFAULT NULL COMMENT '收款人ID',
  PRIMARY KEY (`AUTHID`),
  KEY `FK_Reference_34` (`CID`),
  CONSTRAINT `FK_Reference_34` FOREIGN KEY (`CID`) REFERENCES `T_COMPANY_INFO` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='企业认证记录';

-- ----------------------------
-- Table structure for T_BANK_INFO
-- ----------------------------
DROP TABLE IF EXISTS `T_BANK_INFO`;
CREATE TABLE `T_BANK_INFO` (
  `BIID` varchar(32) NOT NULL COMMENT '银行信息ID',
  `BANKCARDNUM` varchar(32) DEFAULT NULL COMMENT '银行卡号',
  `CARDUSER` varchar(50) DEFAULT NULL COMMENT '持卡人姓名',
  `BLANKTYPE` varchar(50) DEFAULT NULL COMMENT '招商、建行的类型',
  `BANKNAME` varchar(50) DEFAULT NULL COMMENT '开户行',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATER` varchar(50) DEFAULT NULL COMMENT '创建人',
  `UPDATER` varchar(50) DEFAULT NULL COMMENT '更新人',
  `UPATETIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`BIID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户端选择银行转账时显示出平台所支持的银行账户信息';

-- ----------------------------
-- Table structure for T_BLACK_LIST
-- ----------------------------
DROP TABLE IF EXISTS `T_BLACK_LIST`;
CREATE TABLE `T_BLACK_LIST` (
  `BLID` int(11) NOT NULL AUTO_INCREMENT COMMENT '黑名单ID',
  `USERNAME` varchar(50) DEFAULT NULL COMMENT '用户登录账号',
  `IPADDRESS` varchar(50) DEFAULT NULL COMMENT '异常IP地址',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态(启用、未启用等)',
  `NUM` int(11) DEFAULT NULL COMMENT '拦截次数',
  `UPDATETIME` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATER` varchar(50) DEFAULT NULL COMMENT '创建人',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`BLID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='异常IP和用户';

-- ----------------------------
-- Table structure for T_CAL_RULE_DEFINITION
-- ----------------------------
DROP TABLE IF EXISTS `T_CAL_RULE_DEFINITION`;
CREATE TABLE `T_CAL_RULE_DEFINITION` (
  `RULEID` varchar(20) NOT NULL COMMENT '规则定义编号',
  `REMARK` varchar(50) DEFAULT NULL COMMENT '备注',
  `CREATOR` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CREATEDATE` datetime DEFAULT NULL COMMENT '创建时间',
  `EXPRESSION` varchar(20) DEFAULT NULL COMMENT '表达式',
  `RULE` varchar(20) DEFAULT NULL COMMENT '规则',
  `NAME` varchar(20) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`RULEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定义计算公式';

-- ----------------------------
-- Table structure for T_CAL_RULE_USE
-- ----------------------------
DROP TABLE IF EXISTS `T_CAL_RULE_USE`;
CREATE TABLE `T_CAL_RULE_USE` (
  `FID` varchar(20) NOT NULL COMMENT '规则发布ID',
  `RULEID` varchar(20) DEFAULT NULL COMMENT '规则定义编号',
  `STARTDATE` datetime DEFAULT NULL COMMENT '使用开始时间',
  `ENDDATE` datetime DEFAULT NULL COMMENT '使用结束时间',
  `ORDERBY` varchar(5) DEFAULT NULL COMMENT '排序',
  `CREATEDATE` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATOR` varchar(20) DEFAULT NULL COMMENT '创建人',
  `REMARK` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`FID`),
  KEY `FK_Reference_52` (`RULEID`),
  CONSTRAINT `FK_Reference_52` FOREIGN KEY (`RULEID`) REFERENCES `T_CAL_RULE_DEFINITION` (`RULEID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='1.发布一段时间内容的折扣信息（后台管理，卖家根据供应发布）';

-- ----------------------------
-- Table structure for T_COMPANY_ADDRESS
-- ----------------------------
DROP TABLE IF EXISTS `T_COMPANY_ADDRESS`;
CREATE TABLE `T_COMPANY_ADDRESS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CID` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `AREACODE` varchar(10) DEFAULT NULL COMMENT '地区编码（只存最后一级编码）',
  `ADDRESS` varchar(250) NOT NULL COMMENT '地址',
  `LONGITUDE` varchar(50) DEFAULT NULL COMMENT '经度',
  `LATITUDE` varchar(50) DEFAULT NULL COMMENT '纬度',
  `DEEP` float(10,2) DEFAULT NULL COMMENT '水深',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态（默认的卸货地址）',
  `REALDEEP` float(10,2) DEFAULT NULL COMMENT '实际吃水深度',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_Company_address` (`CID`),
  CONSTRAINT `FK_Reference_Company_address` FOREIGN KEY (`CID`) REFERENCES `T_COMPANY_INFO` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='企业卸货地址';

-- ----------------------------
-- Table structure for T_COMPANY_AUTH
-- ----------------------------
DROP TABLE IF EXISTS `T_COMPANY_AUTH`;
CREATE TABLE `T_COMPANY_AUTH` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `AUTHID` int(11) DEFAULT NULL COMMENT '认证记录ID',
  `CNAME` varchar(50) NOT NULL COMMENT '公司名称',
  `ADDRESS` varchar(250) NOT NULL COMMENT '注册地址',
  `RDATE` varchar(20) NOT NULL COMMENT '成立日期',
  `LPERSON` varchar(20) NOT NULL COMMENT '法定代表人',
  `ORGID` varchar(20) NOT NULL COMMENT '登记机构',
  `CTYPE` varchar(20) DEFAULT NULL COMMENT '企业类型',
  `CRATEDATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATEDATE` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_au_ca` (`AUTHID`),
  CONSTRAINT `FK_Reference_au_ca` FOREIGN KEY (`AUTHID`) REFERENCES `T_AUTH_RECORD` (`AUTHID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='企业认证信息';

-- ----------------------------
-- Table structure for T_COMPANY_CONTACT
-- ----------------------------
DROP TABLE IF EXISTS `T_COMPANY_CONTACT`;
CREATE TABLE `T_COMPANY_CONTACT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `CID` varchar(50) NOT NULL COMMENT '企业编号',
  `CNAME` varchar(20) NOT NULL COMMENT '姓名',
  `CPHONE` varchar(20) NOT NULL COMMENT '电话',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATER` varchar(50) DEFAULT NULL COMMENT '创建人',
  `TEL` varchar(20) DEFAULT NULL COMMENT '固定电话',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_Company_contact` (`CID`),
  CONSTRAINT `FK_Reference_Company_contact` FOREIGN KEY (`CID`) REFERENCES `T_COMPANY_INFO` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='企业联系人';

-- ----------------------------
-- Table structure for T_COMPANY_EVALUATION
-- ----------------------------
DROP TABLE IF EXISTS `T_COMPANY_EVALUATION`;
CREATE TABLE `T_COMPANY_EVALUATION` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '评价编号',
  `CID` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `OID` varchar(20) DEFAULT NULL COMMENT '订单编号',
  `SATISFACTION` int(11) NOT NULL COMMENT '企业服务满意度（打分）',
  `CREDIT` int(11) DEFAULT NULL COMMENT '信用度',
  `EVALUATION` varchar(1000) DEFAULT NULL COMMENT '企业服务评价',
  `CRATEDATE` date DEFAULT NULL COMMENT '评价时间',
  `CREATER` varchar(50) DEFAULT NULL COMMENT '创建人,评价人',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_8` (`CID`),
  KEY `FK_Reference_9` (`OID`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`CID`) REFERENCES `T_COMPANY_INFO` (`ID`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`OID`) REFERENCES `T_ORDER_INFO` (`OID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='企业评价表';

-- ----------------------------
-- Table structure for T_COMPANY_INFO
-- ----------------------------
DROP TABLE IF EXISTS `T_COMPANY_INFO`;
CREATE TABLE `T_COMPANY_INFO` (
  `ID` varchar(50) NOT NULL COMMENT '企业编号',
  `CNAME` varchar(50) DEFAULT NULL COMMENT '企业名称',
  `MARK` varchar(4000) DEFAULT NULL COMMENT '企业介绍',
  `CONTACT` varchar(20) DEFAULT NULL COMMENT '联系人姓名',
  `CPHONE` varchar(20) DEFAULT NULL COMMENT '联系人电话',
  `CTYPE` varchar(20) DEFAULT NULL COMMENT '企业类型（区分企业、船舶、个人）',
  `AUTHSTATUS` varchar(20) DEFAULT NULL COMMENT '认证状态(是否缴费、是否认证)',
  `STATUS` varchar(20) DEFAULT NULL COMMENT '处理是否禁用、禁言（预留状态）',
  `CREATEDATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATEDATE` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATER` varchar(50) DEFAULT NULL COMMENT '更新者',
  `TEL` varchar(20) DEFAULT NULL COMMENT '固定电话',
  `BAILSTATUS` varchar(20) DEFAULT NULL COMMENT '保证金状态（是否缴纳足额）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业信息表';

-- ----------------------------
-- Table structure for T_COMPANY_PERSONAL
-- ----------------------------
DROP TABLE IF EXISTS `T_COMPANY_PERSONAL`;
CREATE TABLE `T_COMPANY_PERSONAL` (
  `CPID` int(11) NOT NULL AUTO_INCREMENT COMMENT '个人ID',
  `AUTHID` int(11) DEFAULT NULL COMMENT '认证记录ID',
  `CPNAME` varchar(50) NOT NULL COMMENT '姓名',
  `SEX` int(11) DEFAULT NULL COMMENT '姓别',
  `IDENTIFICATION` varchar(20) NOT NULL COMMENT '身份证',
  `ORIGO` varchar(250) DEFAULT NULL COMMENT '籍贯',
  `CRATEDATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATEDATE` datetime DEFAULT NULL COMMENT '更新时间',
  `REMARK` varchar(1000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`CPID`),
  KEY `FK_Reference_au_cp` (`AUTHID`),
  CONSTRAINT `FK_Reference_au_cp` FOREIGN KEY (`AUTHID`) REFERENCES `T_AUTH_RECORD` (`AUTHID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='个人认证信息表';

-- ----------------------------
-- Table structure for T_COMPANY_RANKING
-- ----------------------------
DROP TABLE IF EXISTS `T_COMPANY_RANKING`;
CREATE TABLE `T_COMPANY_RANKING` (
  `SID` int(11) NOT NULL AUTO_INCREMENT COMMENT '统计ID',
  `CID` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `ORDERSNUM` int(11) DEFAULT NULL COMMENT '成功交易笔数',
  `ORDERSPERSENT` float(12,3) DEFAULT NULL COMMENT '交易成功率',
  `DEGRESS` float(12,3) DEFAULT NULL COMMENT '交易满意度',
  `EVALUATION` float(12,3) DEFAULT NULL COMMENT '企业交易评价',
  `STATDATE` datetime DEFAULT NULL COMMENT '统计日期',
  PRIMARY KEY (`SID`),
  KEY `FK_Reference_33` (`CID`),
  CONSTRAINT `FK_Reference_33` FOREIGN KEY (`CID`) REFERENCES `T_COMPANY_INFO` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='企业统计信息';

-- ----------------------------
-- Table structure for T_COMPANY_SHIPPING
-- ----------------------------
DROP TABLE IF EXISTS `T_COMPANY_SHIPPING`;
CREATE TABLE `T_COMPANY_SHIPPING` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `AUTHID` int(11) DEFAULT NULL,
  `SNAME` varchar(50) NOT NULL COMMENT '船舶名称',
  `PREGISTRY` varchar(50) NOT NULL COMMENT '船籍港',
  `SNO` varchar(50) NOT NULL COMMENT '船舶登记号',
  `SORG` varchar(50) NOT NULL COMMENT '船舶检验机构',
  `SOWNER` varchar(50) NOT NULL COMMENT '船舶所有人',
  `SBUSINESSER` varchar(50) NOT NULL COMMENT '船舶经营人',
  `STYPE` varchar(50) NOT NULL COMMENT '船舶种类',
  `SCREATETIME` varchar(50) NOT NULL COMMENT '船舶建成日期',
  `STOTAL` float NOT NULL COMMENT '总吨位',
  `SLOAD` float NOT NULL COMMENT '载重',
  `SLENGTH` float NOT NULL COMMENT '船长',
  `SWIDTH` float NOT NULL COMMENT '船宽',
  `SDEEP` float NOT NULL COMMENT '型深',
  `SOVER` float NOT NULL COMMENT '满载吃水',
  `SMATERIALl` float NOT NULL COMMENT '船体材料',
  `UPDATEDATE` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_au_cs` (`AUTHID`),
  CONSTRAINT `FK_Reference_au_cs` FOREIGN KEY (`AUTHID`) REFERENCES `T_AUTH_RECORD` (`AUTHID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='企业船舶信息';

-- ----------------------------
-- Table structure for T_MOBILE_APP_VERSION
-- ----------------------------
DROP TABLE IF EXISTS `T_MOBILE_APP_VERSION`;
CREATE TABLE `T_MOBILE_APP_VERSION` (
  `BID` int(11) NOT NULL COMMENT '版本ID',
  `DEVICES` varchar(20) NOT NULL COMMENT '反馈设备(安卓、苹果)',
  `LASTNAME` varchar(20) NOT NULL COMMENT '最新版本名称',
  `LASTEST` varchar(20) NOT NULL COMMENT '最新版本号',
  `LASTNO` int(11) NOT NULL COMMENT '最新版本序号',
  `MARK` varchar(500) NOT NULL COMMENT '最新版本描述',
  `FILESIZE` varchar(20) NOT NULL COMMENT '文件大小',
  `DOWNURL` varchar(200) NOT NULL COMMENT '下载地址',
  `ISFORCE` char(1) NOT NULL COMMENT '是否强制更新',
  `FEQUENCY` int(11) DEFAULT NULL COMMENT '更新频率',
  `UNIT` varchar(20) DEFAULT NULL COMMENT '更新（天、星期）',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATETIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`BID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手机版本检测';

-- ----------------------------
-- Table structure for T_OFFLINE_PAY
-- ----------------------------
DROP TABLE IF EXISTS `T_OFFLINE_PAY`;
CREATE TABLE `T_OFFLINE_PAY` (
  `OPID` varchar(50) NOT NULL COMMENT '订单收款ID',
  `PID` varchar(50) DEFAULT NULL COMMENT '支付流水号',
  `OID` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `OTYPE` varchar(20) DEFAULT NULL COMMENT '保证金、货款',
  `TOTAL` float(12,2) DEFAULT NULL COMMENT '应收金额',
  `AMOUNT` float(12,2) DEFAULT NULL COMMENT '实际收款金额',
  `PTYPE` varchar(20) DEFAULT NULL COMMENT '线上、线下（主要线下）',
  `CREATER` varchar(50) DEFAULT NULL COMMENT '收款人',
  `CREATTIME` date DEFAULT NULL COMMENT '收款时间',
  `STATUS` varchar(20) DEFAULT NULL COMMENT '状态',
  `DEALER` varchar(50) DEFAULT NULL COMMENT '处理人员',
  `DEALTIME` datetime DEFAULT NULL COMMENT '处理时间',
  `DEALRESULT` varchar(500) DEFAULT NULL COMMENT '处理结果',
  `BTYPE` varchar(20) DEFAULT NULL COMMENT '业务类型[支付和充值]',
  PRIMARY KEY (`OPID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='线下收款纪录（线下）';

-- ----------------------------
-- Table structure for T_ORDER_ADDRESS
-- ----------------------------
DROP TABLE IF EXISTS `T_ORDER_ADDRESS`;
CREATE TABLE `T_ORDER_ADDRESS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FID` varchar(50) DEFAULT NULL COMMENT '询单ID',
  `OID` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `TYPE` varchar(20) DEFAULT NULL COMMENT '询单原始地址、询单实体产生的地址',
  `CREATIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CRATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  `CID` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `REALDEEP` float(10,2) DEFAULT NULL COMMENT '实际吃水深度',
  `AREACODE` varchar(10) DEFAULT NULL COMMENT '地区编码（只存最后一级编码）',
  `ADDRESS` varchar(250) NOT NULL COMMENT '地址',
  `LONGITUDE` varchar(50) DEFAULT NULL COMMENT '经度',
  `LATITUDE` varchar(50) DEFAULT NULL COMMENT '纬度',
  `DEEP` float(12,2) DEFAULT NULL COMMENT '水深',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_16` (`FID`),
  KEY `FK_Reference_45` (`OID`),
  CONSTRAINT `FK_Reference_16` FOREIGN KEY (`FID`) REFERENCES `T_ORDER_FIND` (`FID`),
  CONSTRAINT `FK_Reference_45` FOREIGN KEY (`OID`) REFERENCES `T_ORDER_INFO` (`OID`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='订单的收货地址';

-- ----------------------------
-- Table structure for T_ORDER_ARBITRATION
-- ----------------------------
DROP TABLE IF EXISTS `T_ORDER_ARBITRATION`;
CREATE TABLE `T_ORDER_ARBITRATION` (
  `AID` varchar(20) NOT NULL COMMENT '仲裁ID',
  `LID` varchar(50) DEFAULT NULL COMMENT '操作编号',
  `CREATER` varchar(50) DEFAULT NULL COMMENT '申请企业ID',
  `CREATETIME` datetime DEFAULT NULL COMMENT '申请时间',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '申请备注',
  `DEALER` varchar(20) DEFAULT NULL COMMENT '处理人员',
  `DEALTIME` datetime DEFAULT NULL COMMENT '处理时间',
  `DEALRESULT` varchar(500) DEFAULT NULL COMMENT '处理结果',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`AID`),
  KEY `FK_Reference_57` (`LID`),
  CONSTRAINT `FK_Reference_57` FOREIGN KEY (`LID`) REFERENCES `T_ORDER_OPERATIONS` (`LID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用客服查询需要进行介入的合同列表';

-- ----------------------------
-- Table structure for T_ORDER_ARBITRATION_RESULT
-- ----------------------------
DROP TABLE IF EXISTS `T_ORDER_ARBITRATION_RESULT`;
CREATE TABLE `T_ORDER_ARBITRATION_RESULT` (
  `RID` varchar(20) NOT NULL COMMENT '结果ID',
  `AID` varchar(20) DEFAULT NULL COMMENT '仲裁ID',
  `PID` varchar(50) DEFAULT NULL COMMENT '支付流水号',
  `RTYPE` varchar(1000) DEFAULT NULL COMMENT '处理类型（返还、扣除、解冻）',
  `RCONTENT` varchar(20) DEFAULT NULL COMMENT '处理内容（保证金、货款）',
  `QYTYPE` varchar(20) DEFAULT NULL COMMENT '买方还是卖方',
  `QYID` varchar(20) DEFAULT NULL COMMENT '买卖双方企业编号',
  `AMOUNT` float(12,2) DEFAULT NULL COMMENT '金额',
  `PAYID` varchar(20) DEFAULT NULL COMMENT '对应流水ID',
  PRIMARY KEY (`RID`),
  KEY `FK_Reference_46` (`AID`),
  KEY `FK_Reference_60` (`PID`),
  CONSTRAINT `FK_Reference_46` FOREIGN KEY (`AID`) REFERENCES `T_ORDER_ARBITRATION` (`AID`),
  CONSTRAINT `FK_Reference_60` FOREIGN KEY (`PID`) REFERENCES `T_PASSBOOK_PAY` (`PID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用客服查询需要进行介入的操作结果表';

-- ----------------------------
-- Table structure for T_ORDER_CANCEL
-- ----------------------------
DROP TABLE IF EXISTS `T_ORDER_CANCEL`;
CREATE TABLE `T_ORDER_CANCEL` (
  `CID` varchar(50) NOT NULL COMMENT '取消编号',
  `LID` varchar(50) DEFAULT NULL COMMENT '操作编号',
  `CANCELER` varchar(50) DEFAULT NULL COMMENT '取消人（企业、客服、系统）',
  `CANCELTYPE` varchar(20) DEFAULT NULL COMMENT '取消类型（单方取消、协商取消、系统取消）',
  `CANCELTIME` datetime DEFAULT NULL COMMENT '取消时间',
  `REASON` varchar(50) DEFAULT NULL COMMENT '原因',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`CID`),
  KEY `FK_Reference_58` (`LID`),
  CONSTRAINT `FK_Reference_58` FOREIGN KEY (`LID`) REFERENCES `T_ORDER_OPERATIONS` (`LID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单取消记录';

-- ----------------------------
-- Table structure for T_ORDER_COSTDETAIL
-- ----------------------------
DROP TABLE IF EXISTS `T_ORDER_COSTDETAIL`;
CREATE TABLE `T_ORDER_COSTDETAIL` (
  `ID` varchar(32) NOT NULL COMMENT '订单明细编号',
  `OID` varchar(32) DEFAULT NULL COMMENT '订单编号',
  `PID` varchar(50) DEFAULT NULL COMMENT '支付流水号',
  `FID` varchar(20) DEFAULT NULL COMMENT '规则发布ID',
  `NAME` varchar(20) DEFAULT NULL COMMENT '名称',
  `AMOUNT` float(12,2) DEFAULT NULL COMMENT '总额',
  `CREATEDATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATEDATE` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATOR` varchar(20) DEFAULT NULL COMMENT '创建人',
  `REMARK` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_48` (`PID`),
  KEY `FK_Reference_51` (`OID`),
  KEY `FK_Reference_59` (`FID`),
  CONSTRAINT `FK_Reference_48` FOREIGN KEY (`PID`) REFERENCES `T_PASSBOOK_PAY` (`PID`),
  CONSTRAINT `FK_Reference_51` FOREIGN KEY (`OID`) REFERENCES `T_ORDER_INFO` (`OID`),
  CONSTRAINT `FK_Reference_59` FOREIGN KEY (`FID`) REFERENCES `T_CAL_RULE_USE` (`FID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单费用明细即，当前合同所有款项的明细，以及记录.';

-- ----------------------------
-- Table structure for T_ORDER_DIS_PRICE
-- ----------------------------
DROP TABLE IF EXISTS `T_ORDER_DIS_PRICE`;
CREATE TABLE `T_ORDER_DIS_PRICE` (
  `CID` varchar(50) NOT NULL COMMENT '变更编号',
  `LID` varchar(50) DEFAULT NULL COMMENT '操作编号',
  `TYPE` int(11) DEFAULT NULL COMMENT '抽样验收、全部验收',
  `CANCELER` varchar(50) DEFAULT NULL COMMENT '变更人',
  `CANCELTIME` datetime DEFAULT NULL COMMENT '变更时间',
  `REASON` varchar(50) DEFAULT NULL COMMENT '原因',
  `BEGINAMOUNT` float(9,2) DEFAULT NULL COMMENT '操作金额起始',
  `ENDAMOUNT` float(9,2) DEFAULT NULL COMMENT '操作金额结束',
  `BEGINNUM` float(11,2) DEFAULT NULL COMMENT '操作数量起始',
  `ENDNUM` float(11,2) DEFAULT NULL COMMENT '操作数量结束',
  `PUNREASON` varchar(500) DEFAULT NULL COMMENT '处罚原因',
  `PUNDAY` int(11) DEFAULT NULL COMMENT '处罚天数',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`CID`),
  KEY `FK_Reference_56` (`LID`),
  CONSTRAINT `FK_Reference_56` FOREIGN KEY (`LID`) REFERENCES `T_ORDER_OPERATIONS` (`LID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单议价记录';

-- ----------------------------
-- Table structure for T_ORDER_FIND
-- ----------------------------
DROP TABLE IF EXISTS `T_ORDER_FIND`;
CREATE TABLE `T_ORDER_FIND` (
  `FID` varchar(50) NOT NULL COMMENT '询单ID',
  `CID` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `TITlE` varchar(100) DEFAULT NULL COMMENT '标题',
  `TYPE` int(11) NOT NULL COMMENT '类型（买和卖）',
  `ADDRESSTYPE` int(11) DEFAULT NULL COMMENT '地址由谁来指定',
  `PRICE` float(10,2) DEFAULT NULL COMMENT '价格',
  `TOTALNUM` float(11,2) DEFAULT NULL COMMENT '数量',
  `NUM` float(11,2) DEFAULT NULL COMMENT '当前数量',
  `STARTTIME` datetime DEFAULT NULL COMMENT '开始日期',
  `ENDTIME` datetime DEFAULT NULL COMMENT '结束日期',
  `MOREAREA` char(1) DEFAULT NULL COMMENT '是否发布多个地区（卖家的需求）',
  `AREA` varchar(50) DEFAULT NULL COMMENT '区域',
  `CREATER` varchar(50) DEFAULT NULL COMMENT '创建人',
  `CREATIME` datetime DEFAULT NULL COMMENT '创建时间',
  `LIMITIME` datetime DEFAULT NULL COMMENT '有效期',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态(已发布，审核不通过，已取消，已过期，销售量为0，已产生订单)',
  `REMARK` varchar(1000) DEFAULT NULL COMMENT '备注',
  `PARENTID` varchar(50) DEFAULT NULL COMMENT '标识询单的层级',
  `UPDATETIME` datetime DEFAULT NULL COMMENT '更新时间',
  `UPDATER` varchar(50) DEFAULT NULL COMMENT '更新人',
  `OVERALLSTATUS` int(11) DEFAULT NULL COMMENT '询单大状态（有效、无效）',
  PRIMARY KEY (`FID`),
  KEY `FK_Reference_19` (`CID`),
  CONSTRAINT `FK_Reference_19` FOREIGN KEY (`CID`) REFERENCES `T_COMPANY_INFO` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供求基本信息（用于发布）';

-- ----------------------------
-- Table structure for T_ORDER_FIND_ITEM
-- ----------------------------
DROP TABLE IF EXISTS `T_ORDER_FIND_ITEM`;
CREATE TABLE `T_ORDER_FIND_ITEM` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FID` varchar(50) DEFAULT NULL COMMENT '询单ID',
  `UPDATER` varchar(50) DEFAULT NULL COMMENT '更新企业',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建日期',
  `DEALER` varchar(20) DEFAULT NULL COMMENT '处理人（客服）',
  `RESULT` varchar(20) DEFAULT NULL COMMENT '处理结果（客服）',
  `REMARK` varchar(1000) DEFAULT NULL COMMENT '备注',
  `STATUS` int(11) DEFAULT NULL,
  `DEALTIME` datetime DEFAULT NULL COMMENT '处理时间',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_54` (`FID`),
  CONSTRAINT `FK_Reference_54` FOREIGN KEY (`FID`) REFERENCES `T_ORDER_FIND` (`FID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='感兴趣、客服电话沟通列表';

-- ----------------------------
-- Table structure for T_ORDER_INFO
-- ----------------------------
DROP TABLE IF EXISTS `T_ORDER_INFO`;
CREATE TABLE `T_ORDER_INFO` (
  `OID` varchar(50) NOT NULL COMMENT '订单编号',
  `FID` varchar(50) DEFAULT NULL COMMENT '询单ID',
  `SELLERID` varchar(50) DEFAULT NULL COMMENT '卖家ID',
  `BUYERID` varchar(50) DEFAULT NULL COMMENT '卖家ID',
  `PRICE` float(10,2) DEFAULT NULL COMMENT '价格',
  `TOTALNUM` float(11,2) DEFAULT NULL COMMENT '数量',
  `CREATIME` datetime DEFAULT NULL COMMENT '生成时间',
  `CREATER` varchar(50) DEFAULT NULL COMMENT '生成人',
  `LIMITTIME` datetime DEFAULT NULL COMMENT '交易期限(存在一个算法)',
  `TOTALAMOUNT` float(12,2) DEFAULT NULL COMMENT '总金额（可能与单价乘以数量的总和不一样）',
  `AMOUNT` float(12,2) DEFAULT NULL COMMENT '支付金额',
  `STATUS` varchar(20) DEFAULT NULL COMMENT '订单状态(订单的进行中的状态)',
  `OTYPE` varchar(20) DEFAULT NULL COMMENT '订单的类型（未签订，已经签订）',
  `REMARK` varchar(1000) DEFAULT NULL COMMENT '备注',
  `UPDATER` varchar(50) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `LIFECYCLE` varchar(20) DEFAULT NULL COMMENT '订单生命周期',
  PRIMARY KEY (`OID`),
  KEY `FK_Reference_44` (`FID`),
  CONSTRAINT `FK_Reference_44` FOREIGN KEY (`FID`) REFERENCES `T_ORDER_FIND` (`FID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单基本信息';

-- ----------------------------
-- Table structure for T_ORDER_OPERATIONS
-- ----------------------------
DROP TABLE IF EXISTS `T_ORDER_OPERATIONS`;
CREATE TABLE `T_ORDER_OPERATIONS` (
  `LID` varchar(50) NOT NULL COMMENT '操作编号',
  `OID` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `OPERATOR` varchar(50) DEFAULT NULL COMMENT '操作人',
  `OPERATIONTIME` datetime DEFAULT NULL COMMENT '操作时间',
  `TYPE` varchar(20) DEFAULT NULL COMMENT '操作的具体内容',
  `RESULT` varchar(50) DEFAULT NULL COMMENT '操作结果',
  `PLID` varchar(50) DEFAULT NULL COMMENT '父操作ID',
  `REMARK` varchar(1000) DEFAULT NULL COMMENT '备注',
  `ORDERSTATUS` varchar(20) DEFAULT NULL COMMENT '操作时候的合同当前状态',
  PRIMARY KEY (`LID`),
  KEY `FK_Reference_55` (`OID`),
  CONSTRAINT `FK_Reference_55` FOREIGN KEY (`OID`) REFERENCES `T_ORDER_INFO` (`OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单操作记录表(买卖双方、客服、平台等操作记录)';

-- ----------------------------
-- Table structure for T_ORDER_PAY
-- ----------------------------
DROP TABLE IF EXISTS `T_ORDER_PAY`;
CREATE TABLE `T_ORDER_PAY` (
  `OPID` int(11) NOT NULL COMMENT '订单收款ID',
  `PID` varchar(50) DEFAULT NULL COMMENT '支付流水号',
  `OID` varchar(50) DEFAULT NULL COMMENT '订单编号',
  `OTYPE` varchar(20) DEFAULT NULL COMMENT '保证金、货款',
  `TOTAL` float(12,2) DEFAULT NULL COMMENT '应收金额',
  `AMOUNT` float(12,2) DEFAULT NULL COMMENT '实际收款金额',
  `PTPYE` varchar(20) DEFAULT NULL COMMENT '线上、线下（主要线下）',
  `CREATER` varchar(50) DEFAULT NULL COMMENT '收款人',
  `CREATTIME` date DEFAULT NULL COMMENT '收款时间',
  `STATUS` varchar(20) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`OPID`),
  KEY `FK_Reference_37` (`PID`),
  KEY `FK_Reference_39` (`OID`),
  CONSTRAINT `FK_Reference_37` FOREIGN KEY (`PID`) REFERENCES `T_PASSBOOK_PAY` (`PID`),
  CONSTRAINT `FK_Reference_39` FOREIGN KEY (`OID`) REFERENCES `T_ORDER_INFO` (`OID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单收款纪录（线下）';

-- ----------------------------
-- Table structure for T_ORDER_PRODUCT_INFO
-- ----------------------------
DROP TABLE IF EXISTS `T_ORDER_PRODUCT_INFO`;
CREATE TABLE `T_ORDER_PRODUCT_INFO` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `FID` varchar(50) DEFAULT NULL COMMENT '询单ID',
  `SID` varchar(50) DEFAULT NULL COMMENT '询单实体ID',
  `PID` varchar(50) DEFAULT NULL COMMENT '商品ID',
  `PNAME` varchar(50) NOT NULL COMMENT '商品名称',
  `PTYPE` varchar(50) DEFAULT NULL COMMENT '种类',
  `PSIZE` varchar(50) DEFAULT NULL COMMENT '规格',
  `PCOLOR` varchar(50) NOT NULL COMMENT '颜色',
  `PADDRESS` varchar(200) NOT NULL COMMENT '产地（必填）',
  `UNIT` varchar(50) DEFAULT NULL COMMENT '单位（立方，吨）',
  `REMARK` varchar(1000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_Find_Product` (`FID`),
  KEY `FK_Reference_Product_Find` (`PID`),
  CONSTRAINT `FK_Reference_Find_Product` FOREIGN KEY (`FID`) REFERENCES `T_ORDER_FIND` (`FID`),
  CONSTRAINT `FK_Reference_Product_Find` FOREIGN KEY (`PID`) REFERENCES `T_PRODUCT_INFO` (`PID`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='交易中的商品详细';

-- ----------------------------
-- Table structure for T_ORDER_PRODUCT_PROPERTY
-- ----------------------------
DROP TABLE IF EXISTS `T_ORDER_PRODUCT_PROPERTY`;
CREATE TABLE `T_ORDER_PRODUCT_PROPERTY` (
  `SID` int(11) NOT NULL AUTO_INCREMENT,
  `PPID` int(11) DEFAULT NULL COMMENT '编号',
  `PID` varchar(50) DEFAULT NULL COMMENT '商品ID',
  `NAME` varchar(50) NOT NULL COMMENT '属性名称',
  `TYPES` varchar(20) DEFAULT NULL COMMENT '属性值数据类型',
  `MAXV` float DEFAULT NULL COMMENT '最大值',
  `MINV` float DEFAULT NULL COMMENT '最小值',
  `CONTENT` varchar(50) NOT NULL COMMENT '值',
  `STATUS` varchar(10) DEFAULT NULL COMMENT '属性状态（类似分类,区分是否直接做为商品的详情展示）',
  `ORDERNO` int(11) DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (`SID`),
  KEY `FK_Reference_Product_Find_Property` (`PPID`),
  CONSTRAINT `FK_Reference_Product_Find_Property` FOREIGN KEY (`PPID`) REFERENCES `T_ORDER_PRODUCT_INFO` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='交易中的商品的属性';

-- ----------------------------
-- Table structure for T_PASSBOOK_DRAW
-- ----------------------------
DROP TABLE IF EXISTS `T_PASSBOOK_DRAW`;
CREATE TABLE `T_PASSBOOK_DRAW` (
  `TID` varchar(32) NOT NULL COMMENT '提取ID',
  `AID` varchar(50) DEFAULT NULL COMMENT '收款方编号',
  `AMOUNT` float(12,2) NOT NULL COMMENT '提取金额',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `DEALTIME` datetime DEFAULT NULL COMMENT '处理时间',
  `DEALER` varchar(50) DEFAULT NULL COMMENT '处理人',
  `DEALSTATUS` varchar(20) DEFAULT NULL COMMENT '处理结果',
  `PID` varchar(50) DEFAULT NULL COMMENT '支付流水号',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态',
  `MARK` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`TID`),
  KEY `FK_Reference_38` (`AID`),
  KEY `FK_Reference_43` (`PID`),
  CONSTRAINT `FK_Reference_38` FOREIGN KEY (`AID`) REFERENCES `T_ACCEPT_BANK` (`ID`),
  CONSTRAINT `FK_Reference_43` FOREIGN KEY (`PID`) REFERENCES `T_PASSBOOK_PAY` (`PID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户的钱包提取明细';

-- ----------------------------
-- Table structure for T_PASSBOOK_INFO
-- ----------------------------
DROP TABLE IF EXISTS `T_PASSBOOK_INFO`;
CREATE TABLE `T_PASSBOOK_INFO` (
  `PASSID` varchar(50) NOT NULL COMMENT '钱包编号',
  `CID` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `PASSTYPE` varchar(20) DEFAULT NULL COMMENT '保证金 或者 钱包',
  `AMOUNT` float(12,2) DEFAULT NULL COMMENT '总额',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `REMARK` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`PASSID`),
  KEY `FK_Reference_42` (`CID`),
  CONSTRAINT `FK_Reference_42` FOREIGN KEY (`CID`) REFERENCES `T_COMPANY_INFO` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户的钱包';

-- ----------------------------
-- Table structure for T_PASSBOOK_PAY
-- ----------------------------
DROP TABLE IF EXISTS `T_PASSBOOK_PAY`;
CREATE TABLE `T_PASSBOOK_PAY` (
  `PID` varchar(50) NOT NULL COMMENT '支付流水号',
  `PASSID` varchar(50) DEFAULT NULL COMMENT '钱包编号',
  `OID` varchar(50) DEFAULT NULL COMMENT '交易编号（订单号、支付纪录）',
  `OTYPE` varchar(20) DEFAULT NULL COMMENT '交易类型（充值、提取、交易缴款、交易收款、交易违约金扣款、交易服务费）',
  `PAYNO` varchar(50) DEFAULT NULL COMMENT '银行流水号',
  `NAME` varchar(50) DEFAULT NULL COMMENT '交易方名称',
  `AMOUNT` float(12,2) NOT NULL COMMENT '实际金额',
  `NEEDAMOUNT` float(12,2) DEFAULT NULL COMMENT '应收金额',
  `DIRECTION` int(11) NOT NULL COMMENT '流入流出',
  `PAYTYPE` varchar(20) NOT NULL COMMENT '支付方式',
  `PATYTIME` datetime NOT NULL COMMENT '支付时间',
  `STATUS` varchar(20) NOT NULL COMMENT '交易状态',
  `CREATEDATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATEDATE` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATOR` varchar(50) DEFAULT NULL COMMENT '创建人',
  `DEVICES` varchar(20) DEFAULT NULL COMMENT '指通过什么设备支付（手机、电脑）',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '备注',
  `PPID` varchar(50) DEFAULT NULL COMMENT '父操作ID',
  PRIMARY KEY (`PID`),
  KEY `FK_Reference_47` (`PASSID`),
  CONSTRAINT `FK_Reference_47` FOREIGN KEY (`PASSID`) REFERENCES `T_PASSBOOK_INFO` (`PASSID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户的支付记录';

-- ----------------------------
-- Table structure for T_PASSBOOK_PAY2
-- ----------------------------
DROP TABLE IF EXISTS `T_PASSBOOK_PAY2`;
CREATE TABLE `T_PASSBOOK_PAY2` (
  `PID` varchar(50) NOT NULL COMMENT '支付流水号',
  `PASSID` varchar(50) DEFAULT NULL COMMENT '钱包编号',
  `OID` varchar(50) DEFAULT NULL COMMENT '交易编号（订单号、支付纪录）',
  `OTYPE` varchar(20) DEFAULT NULL COMMENT '交易类型（充值、提取、交易缴款、交易收款、交易违约金扣款、交易服务费）',
  `PAYNO` varchar(50) DEFAULT NULL COMMENT '银行流水号',
  `NAME` varchar(50) DEFAULT NULL COMMENT '交易方名称',
  `AMOUNT` float(12,2) NOT NULL COMMENT '金额',
  `DIRECTION` int(11) NOT NULL COMMENT '流入流出',
  `PAYTYPE` varchar(20) NOT NULL COMMENT '支付方式',
  `PATYTIME` datetime NOT NULL COMMENT '支付时间',
  `STATUS` varchar(20) NOT NULL COMMENT '交易状态',
  `DEVICES` varchar(20) DEFAULT NULL COMMENT '指通过什么设备支付（手机、电脑）',
  `REMARK` varchar(200) NOT NULL COMMENT '备注',
  PRIMARY KEY (`PID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付第三方的对账表';

-- ----------------------------
-- Table structure for T_PASSBOOK_THIRD_CHECK
-- ----------------------------
DROP TABLE IF EXISTS `T_PASSBOOK_THIRD_CHECK`;
CREATE TABLE `T_PASSBOOK_THIRD_CHECK` (
  `PID` varchar(50) NOT NULL COMMENT '支付流水号',
  `PASSID` varchar(50) DEFAULT NULL COMMENT '钱包编号',
  `OID` varchar(50) DEFAULT NULL COMMENT '交易编号（订单号、支付纪录）',
  `OTYPE` varchar(20) DEFAULT NULL COMMENT '交易类型（充值、提取、交易缴款、交易收款、交易违约金扣款、交易服务费）',
  `PAYNO` varchar(50) NOT NULL COMMENT '银行流水号',
  `NAME` varchar(50) DEFAULT NULL COMMENT '交易方名称',
  `AMOUNT` float(12,2) NOT NULL COMMENT '金额',
  `DIRECTION` int(11) NOT NULL COMMENT '流入流出',
  `PAYTYPE` varchar(20) NOT NULL COMMENT '支付方式',
  `PATYTIME` datetime NOT NULL COMMENT '支付时间',
  `STATUS` varchar(20) NOT NULL COMMENT '交易状态',
  `DEVICES` varchar(20) DEFAULT NULL COMMENT '指通过什么设备支付（手机、电脑）',
  `REMARK` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`PID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付第三方的对账表';

-- ----------------------------
-- Table structure for T_PAY_DETAIL
-- ----------------------------
DROP TABLE IF EXISTS `T_PAY_DETAIL`;
CREATE TABLE `T_PAY_DETAIL` (
  `PAYDETAILID` varchar(32) NOT NULL COMMENT '充值明细编号',
  `PAYACCOUNTID` varchar(32) DEFAULT NULL COMMENT '支付账户编号',
  `PAYFLOWID` varchar(50) DEFAULT NULL COMMENT '支付流水编号',
  `PAYORGNUM` varchar(50) DEFAULT NULL COMMENT '支付流水号',
  `PAYTRADESTATUS` varchar(32) DEFAULT NULL COMMENT '支付交易状态',
  `BUZTRADENUM` varchar(32) DEFAULT NULL COMMENT '订单编号【业务系统编号】',
  `PARAMSCONTENT` text COMMENT '请求参数内容',
  `PAYOPERATETYPE` int(3) DEFAULT NULL COMMENT '支付操作类型，支付请求，支付返回',
  `BUZNOTIFYURL` varchar(255) DEFAULT NULL COMMENT '业务系统通知地址',
  `TRADETIME` datetime DEFAULT NULL COMMENT '交易时间',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATETIME` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATOR` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`PAYDETAILID`),
  KEY `FK_Reference_49` (`PAYACCOUNTID`),
  CONSTRAINT `FK_Reference_49` FOREIGN KEY (`PAYACCOUNTID`) REFERENCES `T_PAY_INFO` (`PAYACCOUNTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值明细信息即与第三方机构发生的交互信息';

-- ----------------------------
-- Table structure for T_PAY_INFO
-- ----------------------------
DROP TABLE IF EXISTS `T_PAY_INFO`;
CREATE TABLE `T_PAY_INFO` (
  `PAYACCOUNTID` varchar(32) NOT NULL COMMENT '支付账户编号',
  `PARTNERID` varchar(32) DEFAULT NULL COMMENT '合作身份者ID，以2088开头由16位纯数字组成的字符串',
  `PAYKEY` varchar(50) DEFAULT NULL COMMENT ',支付机构提供',
  `GATEWAYURL` varchar(255) DEFAULT NULL COMMENT '支付第三方机构提供',
  `SERVICENAME` varchar(50) DEFAULT NULL COMMENT '支付第三方机构提供，如:及时到账，担保交易',
  `SIGNTYPE` varchar(32) DEFAULT NULL COMMENT '签名类型',
  `SELLERACCOUNT` varchar(32) DEFAULT NULL COMMENT '卖家在第三方支付机构注册的账号，即收款账号',
  `PAYORGNAME` varchar(32) DEFAULT NULL COMMENT '如：支付宝，财付通，银联',
  `PAYORGCODE` varchar(32) DEFAULT NULL COMMENT '第三方支付机构名称编码',
  `IMGURL` varchar(32) DEFAULT NULL COMMENT '图片地址;  图片地址或者LOGO地址',
  `STATUS` int(3) DEFAULT NULL COMMENT '状态',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATETIME` datetime DEFAULT NULL COMMENT '更新时间',
  `CREATOR` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`PAYACCOUNTID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付账户信息即是收款方在第三方支付机构的信息';

-- ----------------------------
-- Table structure for T_PK
-- ----------------------------
DROP TABLE IF EXISTS `T_PK`;
CREATE TABLE `T_PK` (
  `ID` varchar(50) NOT NULL COMMENT '主键ID',
  `BID` varchar(50) DEFAULT NULL COMMENT '业务编码',
  `MAXVAL` int(11) DEFAULT NULL COMMENT '最大值',
  `MINVAL` int(11) DEFAULT NULL COMMENT '最小值',
  `CURVAL` int(11) DEFAULT NULL COMMENT '当前值',
  `LENGTH` int(11) DEFAULT NULL COMMENT '长度',
  `BPREFIX` varchar(50) DEFAULT NULL COMMENT '业务前缀(关键字YEAR,DAY,MONTH)',
  `BSUFFIX` varchar(50) DEFAULT NULL COMMENT '业务后缀',
  `CREATETIME` datetime DEFAULT NULL COMMENT '消息创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主键表';

-- ----------------------------
-- Table structure for T_PRODUCT_INFO
-- ----------------------------
DROP TABLE IF EXISTS `T_PRODUCT_INFO`;
CREATE TABLE `T_PRODUCT_INFO` (
  `PID` varchar(50) NOT NULL COMMENT '商品ID',
  `PNAME` varchar(50) NOT NULL COMMENT '商品名称',
  `PCODE` varchar(50) DEFAULT NULL COMMENT '商品类型(黄沙、石子)',
  `PTYPE` varchar(50) DEFAULT NULL COMMENT '种类',
  `PSIZE` varchar(50) DEFAULT NULL COMMENT '规格',
  `PCOLOR` varchar(50) NOT NULL COMMENT '颜色',
  `PADDRESS` varchar(200) NOT NULL COMMENT '产地',
  `UNIT` varchar(50) DEFAULT NULL COMMENT '单位',
  `REMARK` varchar(1000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`PID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品';

-- ----------------------------
-- Table structure for T_PRODUCT_PRICE
-- ----------------------------
DROP TABLE IF EXISTS `T_PRODUCT_PRICE`;
CREATE TABLE `T_PRODUCT_PRICE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PID` varchar(50) DEFAULT NULL COMMENT '商品ID',
  `PRICE` float(9,2) DEFAULT NULL COMMENT '价格',
  `UNIT` varchar(50) DEFAULT NULL COMMENT '单位',
  `DATEPOINT` datetime DEFAULT NULL COMMENT '时间点',
  `AREA` varchar(50) DEFAULT NULL COMMENT '地区',
  `UPDATER` varchar(50) DEFAULT NULL COMMENT '更新人',
  `UPDATETIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_Product_Price` (`PID`),
  CONSTRAINT `FK_Reference_Product_Price` FOREIGN KEY (`PID`) REFERENCES `T_PRODUCT_INFO` (`PID`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8 COMMENT='商品价格明细(当天)';

-- ----------------------------
-- Table structure for T_PRODUCT_PRICE_HOPE
-- ----------------------------
DROP TABLE IF EXISTS `T_PRODUCT_PRICE_HOPE`;
CREATE TABLE `T_PRODUCT_PRICE_HOPE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PID` varchar(50) DEFAULT NULL COMMENT '商品ID',
  `BASEPRICE` float(9,2) DEFAULT NULL COMMENT '价格',
  `PRICEMIN` float(9,2) DEFAULT NULL COMMENT '测试价格最小',
  `PRICEMAX` float(9,2) DEFAULT NULL COMMENT '测试价格最大',
  `UNIT` varchar(50) DEFAULT NULL COMMENT '单位',
  `STARTTIME` datetime DEFAULT NULL COMMENT '开始时间',
  `ENDTIME` datetime DEFAULT NULL COMMENT '结束时间',
  `TIMETYPE` varchar(20) DEFAULT NULL COMMENT '1周、2周',
  `AREA` varchar(50) DEFAULT NULL COMMENT '地区',
  `UPDATER` varchar(50) DEFAULT NULL COMMENT '预测人',
  `UPDATETIME` datetime DEFAULT NULL COMMENT '预测时间',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_20` (`PID`),
  CONSTRAINT `FK_Reference_20` FOREIGN KEY (`PID`) REFERENCES `T_PRODUCT_INFO` (`PID`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8 COMMENT='商品价格预测';

-- ----------------------------
-- Table structure for T_PRODUCT_PROPERTY
-- ----------------------------
DROP TABLE IF EXISTS `T_PRODUCT_PROPERTY`;
CREATE TABLE `T_PRODUCT_PROPERTY` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PID` varchar(50) DEFAULT NULL COMMENT '商品ID',
  `NAME` varchar(50) NOT NULL COMMENT '属性名称',
  `TYPES` varchar(20) DEFAULT NULL COMMENT '属性值数据类型',
  `MAXV` float DEFAULT NULL COMMENT '最大值',
  `MINV` float DEFAULT NULL COMMENT '最小值',
  `CONTENT` varchar(50) NOT NULL COMMENT '值',
  `STATUS` varchar(10) DEFAULT NULL COMMENT '属性状态（类似分类,区分是否直接做为商品的详情展示）',
  `ORDERNO` int(11) DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_Product_Property` (`PID`),
  CONSTRAINT `FK_Reference_Product_Property` FOREIGN KEY (`PID`) REFERENCES `T_PRODUCT_INFO` (`PID`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8 COMMENT='商品的其他属性';

-- ----------------------------
-- Table structure for T_PUBLIC_CODES
-- ----------------------------
DROP TABLE IF EXISTS `T_PUBLIC_CODES`;
CREATE TABLE `T_PUBLIC_CODES` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(50) NOT NULL COMMENT '代码集',
  `VAL` varchar(50) NOT NULL COMMENT '代码',
  `NAME` varchar(50) NOT NULL COMMENT '名称',
  `PCODE` varchar(50) DEFAULT NULL COMMENT '关联属性编号(父编号)',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8 COMMENT='公共代码集表';

-- ----------------------------
-- Table structure for T_PUSH_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS `T_PUSH_CONFIG`;
CREATE TABLE `T_PUSH_CONFIG` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `APPID` varchar(50) DEFAULT NULL COMMENT 'AppID',
  `APPKEY` varchar(50) DEFAULT NULL COMMENT 'AppKey',
  `APPSECRET` varchar(50) DEFAULT NULL COMMENT 'AppSecret',
  `MASTERSECRET` varchar(50) DEFAULT NULL COMMENT 'MasterSecret',
  `URL` varchar(255) DEFAULT NULL COMMENT 'url',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态(启用、停用)',
  `TYPE` varchar(20) DEFAULT NULL COMMENT '类型(ANDROID,IOS)',
  `UPDATER` varchar(50) DEFAULT NULL COMMENT '更新人',
  `UPDATETIIME` datetime DEFAULT NULL COMMENT '更新时间',
  `PORT` int(11) DEFAULT NULL COMMENT '服务器端口',
  `CERTIFICATEPATH` varchar(255) DEFAULT NULL COMMENT '(IOS配置)系统下导出的证书路径',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='XMPP消息推送配置表（个推）';

-- ----------------------------
-- Table structure for T_PUSH_RESULT
-- ----------------------------
DROP TABLE IF EXISTS `T_PUSH_RESULT`;
CREATE TABLE `T_PUSH_RESULT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `MSGTYPE` int(11) DEFAULT NULL COMMENT '消息类型(通知、透传)',
  `MSGTITLE` varchar(250) DEFAULT NULL COMMENT '消息标题',
  `MSGCONTENT` varchar(500) DEFAULT NULL COMMENT '消息内容',
  `PUSHTYPE` int(11) DEFAULT NULL COMMENT '推送类型（单个，群休）',
  `PUSHTARGET` varchar(100) DEFAULT NULL COMMENT '推送目标（消息接收者）',
  `PUSHSTATUS` int(11) DEFAULT NULL COMMENT '推送状态（成功、失败）',
  `PUSHTIME` datetime DEFAULT NULL COMMENT '推送时间',
  `RESULTCODE` varchar(20) DEFAULT NULL COMMENT '推送结果状态码',
  `RESULTCONTENT` varchar(250) DEFAULT NULL COMMENT '推送结果消息',
  `REMARK` varchar(250) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='消息推送结果';

-- ----------------------------
-- Table structure for T_QT_FQ
-- ----------------------------
DROP TABLE IF EXISTS `T_QT_FQ`;
CREATE TABLE `T_QT_FQ` (
  `FID` int(11) NOT NULL AUTO_INCREMENT COMMENT '反馈编号',
  `QUETSION` varchar(500) NOT NULL COMMENT '反馈内容',
  `ASKID` varchar(50) NOT NULL COMMENT '反馈人',
  `CREATETIME` datetime NOT NULL COMMENT '反馈时间',
  `DEVICES` varchar(20) DEFAULT NULL COMMENT '反馈设备(安卓、苹果)',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态(未处理、已处理)',
  PRIMARY KEY (`FID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='意见反馈';

-- ----------------------------
-- Table structure for T_QT_QUESTIONS
-- ----------------------------
DROP TABLE IF EXISTS `T_QT_QUESTIONS`;
CREATE TABLE `T_QT_QUESTIONS` (
  `QID` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题编号',
  `QUETSION` varchar(200) NOT NULL COMMENT '问题内容',
  `ANSWERS` varchar(500) NOT NULL COMMENT '问题答案',
  `TYPE` varchar(20) NOT NULL COMMENT '问题分类',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATETIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`QID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='常见问题';

-- ----------------------------
-- Table structure for T_SCHEDULE_INFO
-- ----------------------------
DROP TABLE IF EXISTS `T_SCHEDULE_INFO`;
CREATE TABLE `T_SCHEDULE_INFO` (
  `SCHID` int(11) NOT NULL AUTO_INCREMENT COMMENT '定时调度ID',
  `SCH_NAME` varchar(50) DEFAULT NULL COMMENT '调度名称',
  `REMARK` varchar(500) DEFAULT NULL COMMENT '调度描述',
  `JOB_NAME` varchar(50) DEFAULT NULL COMMENT 'JOBNAME',
  `JOB_GROUP` varchar(50) DEFAULT NULL COMMENT 'JOBGROUP',
  `JOBCLASSNAME` varchar(500) DEFAULT NULL COMMENT 'JOBCLASSNAME',
  `TRIGGERNAME` varchar(50) DEFAULT NULL COMMENT 'TRIGGERNAME',
  `TRIGGERGROUP` varchar(50) DEFAULT NULL COMMENT 'TRIGGERGROUP',
  `TRIGGERCLASSNAME` varchar(500) DEFAULT NULL COMMENT 'TRIGGERCLASSNAME',
  `ISVALID` int(2) DEFAULT NULL COMMENT '是否有效',
  `CREATEDATE` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`SCHID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='定时任务调度信息';

-- ----------------------------
-- Table structure for T_SHORT_MESSAGE_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS `T_SHORT_MESSAGE_CONFIG`;
CREATE TABLE `T_SHORT_MESSAGE_CONFIG` (
  `SMCID` int(11) NOT NULL AUTO_INCREMENT,
  `SUSER` varchar(50) DEFAULT NULL COMMENT '账号',
  `TEMPLATEPARAM` varchar(255) DEFAULT NULL COMMENT '模板参数',
  `TEMPLATEID` varchar(50) DEFAULT NULL COMMENT '模板ID',
  `SPWD` varchar(50) DEFAULT NULL COMMENT '密码',
  `SURL` varchar(200) DEFAULT NULL COMMENT '接口地址',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态(启用、停用)',
  `TYPE` int(11) DEFAULT NULL COMMENT '类型(电信、移动、联通等)',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '更新人',
  `UPDATETIIME` datetime DEFAULT NULL COMMENT '更新时间',
  `TOKENURL` varchar(255) DEFAULT NULL,
  `TEMPLATETYPE` varchar(20) DEFAULT NULL COMMENT '验证码模板，其它模板',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`SMCID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='短信的接口信息 接口SDK账号，请求地址等信息';

-- ----------------------------
-- Table structure for T_SHORT_MESSAGE_USED
-- ----------------------------
DROP TABLE IF EXISTS `T_SHORT_MESSAGE_USED`;
CREATE TABLE `T_SHORT_MESSAGE_USED` (
  `SMUID` int(11) NOT NULL AUTO_INCREMENT,
  `BUSINESSID` varchar(20) DEFAULT NULL COMMENT '业务ID',
  `BUSINESSTYPE` varchar(20) DEFAULT NULL COMMENT '业务类型',
  `SMCONTENT` varchar(500) DEFAULT NULL COMMENT '短信内容',
  `PHONENUMBER` varchar(20) DEFAULT NULL COMMENT '接收手机号',
  `SENDSTATUS` int(11) DEFAULT NULL COMMENT '短信发送状态（成功、失败）',
  `WAITTIME` datetime DEFAULT NULL COMMENT '待发送时间',
  `SENDTIME` datetime DEFAULT NULL COMMENT '发送时间',
  `REMARK` varchar(250) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`SMUID`)
) ENGINE=InnoDB AUTO_INCREMENT=196 DEFAULT CHARSET=utf8 COMMENT='T_SHORT_MESSAGE_USED\r\n短信接口使用记录';

-- ----------------------------
-- Table structure for T_SYSTEM_LOG
-- ----------------------------
DROP TABLE IF EXISTS `T_SYSTEM_LOG`;
CREATE TABLE `T_SYSTEM_LOG` (
  `BUSINESSID` varchar(20) DEFAULT NULL COMMENT '业务ID',
  `BUSINESSTYPE` varchar(20) DEFAULT NULL COMMENT '业务类型',
  `LOGID` int(11) NOT NULL COMMENT '日志ID',
  `LOGCONTENT` varchar(1000) DEFAULT NULL COMMENT '日志内容',
  `LOGTYPE` int(11) DEFAULT NULL COMMENT '日志类型',
  `LOGLEVEL` int(11) DEFAULT NULL COMMENT '日志级别',
  `LOGSTATUS` int(11) DEFAULT NULL COMMENT '日志状态',
  `CREATETIME` datetime DEFAULT NULL COMMENT '创建时间',
  `CREATER` varchar(20) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`LOGID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Table structure for T_SYSTEM_MESSAGE
-- ----------------------------
DROP TABLE IF EXISTS `T_SYSTEM_MESSAGE`;
CREATE TABLE `T_SYSTEM_MESSAGE` (
  `MSGID` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统消息ID',
  `QYID` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `CONTENT` varchar(1000) DEFAULT NULL COMMENT '消息内容',
  `TYPE` int(11) DEFAULT NULL COMMENT '消息类型',
  `BUSINESSID` varchar(20) DEFAULT NULL COMMENT '业务ID',
  `BUSINESSTYPE` varchar(20) DEFAULT NULL COMMENT '业务类型',
  `STATUS` int(11) DEFAULT NULL COMMENT '消息状态:已读，未读',
  `CREATETIME` datetime DEFAULT NULL COMMENT '消息创建时间',
  `READTIME` datetime DEFAULT NULL COMMENT '读取时间',
  PRIMARY KEY (`MSGID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统消息';

-- ----------------------------
-- Table structure for T_SYSTEM_PARAMS
-- ----------------------------
DROP TABLE IF EXISTS `T_SYSTEM_PARAMS`;
CREATE TABLE `T_SYSTEM_PARAMS` (
  `SID` int(11) NOT NULL AUTO_INCREMENT,
  `PNAME` varchar(50) NOT NULL COMMENT '系统参考名',
  `PVALUE` varchar(200) NOT NULL COMMENT '系统参数值',
  `PTYPE` varchar(20) NOT NULL COMMENT '系统参数类型(数据类型)',
  `DEFAULTVALUE` varchar(200) DEFAULT NULL COMMENT '默认值',
  `DESCRIPTION` varchar(200) DEFAULT NULL COMMENT '描述',
  `UPDATER` varchar(20) DEFAULT NULL COMMENT '修改人',
  `UPDATETIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`SID`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='系统参数';

-- ----------------------------
-- Table structure for T_UPLOAD_IMAGES
-- ----------------------------
DROP TABLE IF EXISTS `T_UPLOAD_IMAGES`;
CREATE TABLE `T_UPLOAD_IMAGES` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `OID` varchar(50) DEFAULT NULL COMMENT '对应编号',
  `OTYPE` varchar(20) DEFAULT NULL COMMENT '对应类型(企业认证图片、企业图片、卸货地址图片)',
  `FNAME` varchar(200) NOT NULL COMMENT '文件名',
  `FTYPE` varchar(50) DEFAULT NULL COMMENT '文件类型',
  `FSIZE` int(11) NOT NULL COMMENT '文件大小',
  `FPATH` varchar(200) NOT NULL COMMENT '文件目录',
  `FULLPATH` varchar(200) DEFAULT NULL COMMENT '全路径（直接外网访问路径）',
  `FSERVERID` varchar(20) NOT NULL COMMENT '文件服务器编号（引申出文件服务器配置表）',
  `COMMITSERVER` varchar(20) DEFAULT NULL COMMENT '提交系统',
  `CREATEDATE` datetime DEFAULT NULL COMMENT '提交时间',
  `PID` int(11) DEFAULT NULL COMMENT '略图的父ID',
  `FSTYLE` varchar(20) DEFAULT NULL COMMENT '图片格式（原图大中小）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2980 DEFAULT CHARSET=utf8 COMMENT='图片上传管理表';

-- ----------------------------
-- Table structure for T_USER
-- ----------------------------
DROP TABLE IF EXISTS `T_USER`;
CREATE TABLE `T_USER` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CID` varchar(50) DEFAULT NULL COMMENT '企业编号',
  `USERNAME` varchar(50) NOT NULL COMMENT '账号',
  `PASSWORD` varchar(50) NOT NULL COMMENT '密码',
  `NICK` varchar(50) DEFAULT NULL COMMENT '昵称',
  `PHONE` varchar(20) NOT NULL COMMENT '手机号',
  `LOGO` varchar(20) DEFAULT NULL COMMENT '头像ID',
  `STATUS` varchar(20) NOT NULL COMMENT '用户状态（正常、限制、禁用）',
  `CREATEDATE` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATEDATE` datetime DEFAULT NULL COMMENT '修改时间',
  `CLIENTID` varchar(50) DEFAULT NULL COMMENT '客户端推送ID',
  `CLIENTTYPE` varchar(64) DEFAULT NULL COMMENT '客户端类型(android、IOS)',
  PRIMARY KEY (`ID`),
  KEY `FK_Reference_53` (`CID`),
  CONSTRAINT `FK_Reference_53` FOREIGN KEY (`CID`) REFERENCES `T_COMPANY_INFO` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8 COMMENT='用户通行证';
