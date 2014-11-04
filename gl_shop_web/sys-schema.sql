set autocommit=0;
SET FOREIGN_KEY_CHECKS = 0;

drop table if exists `sys_users`;
CREATE TABLE `sys_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL COMMENT '账号',
  `realname` varchar(20) NOT NULL COMMENT '姓名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `enabled` int(11) NOT NULL COMMENT '用户状态（正常、限制、禁用）',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近登录时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='管理系统帐号';

drop table if exists `sys_user_permissions`;
create table sys_user_permissions (
    id int(11) not null AUTO_INCREMENT,
    user_id int(11) not null,
    permission int(11) not null,
    primary key (`id`),
    constraint fk_permission_user foreign key(user_id) references sys_users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
create unique index ix_user_permission on sys_user_permissions (user_id,permission);

drop table if exists `sys_groups`;
create table sys_groups (
    `id` int(11) not null AUTO_INCREMENT,
    `name` varchar(50) not null,
    primary key (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists `sys_group_members`;
create table sys_group_members (
    id int(11) not null AUTO_INCREMENT,
    user_id int(11) not null,
    group_id int(11) not null,
    primary key (id),
    constraint fk_group_members_group foreign key(group_id) references sys_groups(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists `sys_tasks`;
CREATE TABLE `sys_tasks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL COMMENT '任务类型',
  `object_id` int(11) NOT NULL COMMENT '实际任务的对象',
  `owner` int(11) NULL COMMENT '执行人',
  `claim_time` datetime DEFAULT NULL,
  `finished` int(11) NOT NULL DEFAULT 0 COMMENT '已完成',
  `finish_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_Reference_Task_User` FOREIGN KEY (`owner`) REFERENCES `sys_users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='系统任务';

commit;
set autocommit=1;