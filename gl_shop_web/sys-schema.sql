set autocommit=0;
SET FOREIGN_KEY_CHECKS = 0;

drop table if exists `SYS_USERS`;
CREATE TABLE `SYS_USERS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(20) NOT NULL COMMENT '账号',
  `REALNAME` varchar(20) NOT NULL COMMENT '姓名',
  `PASSWORD` varchar(100) NOT NULL COMMENT '密码',
  `ENABLED` int(11) NOT NULL COMMENT '用户状态（正常、禁用）',
  `LAST_LOGIN_TIME` datetime DEFAULT NULL COMMENT '最近登录时间',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE (`USERNAME`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='管理系统帐号';

drop table if exists `SYS_USER_PERMISSIONS`;
create table SYS_USER_PERMISSIONS (
    ID int(11) not null AUTO_INCREMENT,
    USER_ID int(11) not null,
    PERMISSION int(11) not null,
    primary key (`ID`),
    constraint fk_permission_user foreign key(USER_ID) references SYS_USERS(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create unique index ix_user_permission on SYS_USER_PERMISSIONS (USER_ID,PERMISSION);

drop table if exists `SYS_GROUPS`;
create table SYS_GROUPS (
    `ID` int(11) not null AUTO_INCREMENT,
    `NAME` varchar(50) not null,
    primary key (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists `SYS_GROUP_MEMBERS`;
create table SYS_GROUP_MEMBERS (
    ID int(11) not null AUTO_INCREMENT,
    USER_ID int(11) not null,
    GROUP_ID int(11) not null,
    primary key (ID),
    constraint fk_group_members_group foreign key(GROUP_ID) references SYS_GROUPS(ID)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists `SYS_TASKS`;
CREATE TABLE `SYS_TASKS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CID` varchar(50) NOT NULL COMMENT '公司ID',
  `TYPE` int(11) NOT NULL COMMENT '任务类型',
  `OBJECT_ID` varchar(50) NOT NULL COMMENT '实际任务的对象',
  `OWNER` int(11) NULL COMMENT '任务执行人',
  `CLAIM_TIME` datetime DEFAULT NULL COMMENT '任务领取时间',
  `FINISHED` int(11) NOT NULL DEFAULT 0 COMMENT '已完成',
  `FINISH_TIME` datetime DEFAULT NULL COMMENT "完成时间",
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  INDEX (`CID`),
  INDEX (`OWNER`),
  FOREIGN KEY (`CID`) REFERENCES `T_COMPANY_INFO` (`ID`),
  FOREIGN KEY (`OWNER`) REFERENCES `SYS_USERS` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统任务';

drop table if exists `SYS_FINANCE_REMITS`;
CREATE TABLE `SYS_FINANCE_REMITS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AMOUNT` float(12,2) NOT NULL COMMENT '收到汇款金额',
  `REMITTER_ACCOUNT` varchar(30) NULL COMMENT '汇款人银行帐号',
  `RECEIVE_TIME` datetime DEFAULT NULL COMMENT '收到款项时间',
  `BANK_SERIAL_NUMBER` varchar(20) DEFAULT NULL COMMENT '银行汇款流水号',
  `MOBILE` varchar(20) NULL COMMENT '收款人手机号码',
  `REMITTER` varchar(10) NULL COMMENT '汇款人',
  `CONTRACT_ID` varchar(50) NULL COMMENT '合同编号',
  `COMPANY` varchar(50) NULL COMMENT '公司名称',
  `CREATOR` int(11) NOT NULL COMMENT '汇款录入者',
  `PROCESSOR` int(11) NULL COMMENT '汇款处理者',
  `AUDITOR` int(11) NULL COMMENT '汇款审核者',
  `STATUS` int(11) NULL COMMENT '0: 待处理, 1: 待审核, 2: 审核通过, 3: 无法处理',
  `TARGET_COMPANY_ID` varchar(20) COMMENT '实际汇款对象ID',
  `TARGET_WALLET_TYPE` char(1) COMMENT '钱包类型，参见T_OFFLINE_PAY',
  `REMARK` varchar(200) NULL COMMENT '其他信息',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  `FINISH_TIME` datetime DEFAULT NULL COMMENT '处理完成时间',
  PRIMARY KEY (`ID`),
  INDEX (`CREATOR`),
  INDEX (`PROCESSOR`),
  INDEX (`AUDITOR`),
  FOREIGN KEY (`CREATOR`) REFERENCES `SYS_USERS` (`ID`),
  FOREIGN KEY (`PROCESSOR`) REFERENCES `SYS_USERS` (`ID`),
  FOREIGN KEY (`AUDITOR`) REFERENCES `SYS_USERS` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='汇款录入记录';

drop table if exists `SYS_SERVICE_LOGS`;
CREATE TABLE `SYS_SERVICE_LOGS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CID` varchar(50) NOT NULL COMMENT '企业ID',
  `OBJECT_ID` varchar(50) NOT NULL COMMENT '服务对象',
  `TYPE` int(11) NOT NULL COMMENT '对象类型',
  `OPERATOR` int(11) NULL COMMENT '操作人',
  `CONTENT` varchar(200) NOT NULL COMMENT '服务对象',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  INDEX (`CID`),
  INDEX (`OBJECT_ID`),
  INDEX (`TYPE`),
  FOREIGN KEY (`OPERATOR`) REFERENCES `SYS_USERS` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='服务记录';

drop table if exists `SYS_SERVICE_LOG_TEMPLATES`;
CREATE TABLE `SYS_SERVICE_LOG_TEMPLATES` (
  `ID` CHAR(5) NOT NULL,
  `CONTENT` varchar(200) NOT NULL COMMENT '模板内容',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务记录内容模板';

commit;
set autocommit=1;
