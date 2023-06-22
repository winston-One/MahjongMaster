CREATE DATABASE /*!32312 IF NOT EXISTS*/`queshen` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `queshen`;

DROP TABLE IF EXISTS `re_order_voucher`;

CREATE TABLE `re_order_voucher` (
  `order_id` varchar(200) DEFAULT NULL,
  `voucher_id` varchar(200) DEFAULT NULL,
  `voucher_type` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_chat_msg`;

CREATE TABLE `t_chat_msg` (
  `id` varchar(255) NOT NULL COMMENT '消息id',
  `content` varchar(255) DEFAULT NULL COMMENT '消息内容',
  `openid` varchar(255) DEFAULT NULL COMMENT '发送者id',
  `is_deleted` int(4) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_chat_msg` */

/*Table structure for table `t_comment` */

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `room_id` varchar(200) DEFAULT NULL COMMENT '房间id',
  `openid` varchar(255) DEFAULT NULL COMMENT '发送者id',
  `reply_id` varchar(255) DEFAULT NULL COMMENT '回复者id',
  `content` varchar(300) DEFAULT NULL COMMENT '评论内容',
  `top_comment_id` int(11) DEFAULT NULL COMMENT '该房间的一级评论',
  `parent_comment_id` int(10) DEFAULT NULL COMMENT '父级评论,就是回复的那条评论id',
  `is_deleted` tinyint(4) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_comment` */

/*Table structure for table `t_comment_like` */

DROP TABLE IF EXISTS `t_comment_like`;

CREATE TABLE `t_comment_like` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '点赞评论的id',
  `openid` varchar(255) NOT NULL COMMENT '点赞者的id',
  `comment_id` int(11) NOT NULL COMMENT '评论信息id',
  `is_like` int(4) NOT NULL DEFAULT '0' COMMENT '是否点赞',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_comment_like` */

/*Table structure for table `t_log_info` */

DROP TABLE IF EXISTS `t_log_info`;

CREATE TABLE `t_log_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `content` varchar(255) NOT NULL COMMENT '操作功能',
  `busines_type` int(4) DEFAULT '3' COMMENT '业务类型(0是授权，1是导出，2是导入，3是上传)',
  `method` varchar(255) DEFAULT NULL COMMENT 'conttoller方法',
  `request_method` varchar(255) DEFAULT 'POST' COMMENT '请求方式(GET，POST,PUT，DELETE)',
  `operator_id` varchar(255) DEFAULT 'o2eui5ZuZQt2eEsO7lyq0psWFXYg' COMMENT '操作者id(默认就是测试人员的openid)',
  `url` varchar(255) NOT NULL COMMENT '请求路径',
  `ip` varchar(255) NOT NULL COMMENT 'ip地址',
  `param` varchar(255) DEFAULT NULL COMMENT '请求参数',
  `result` varchar(255) DEFAULT NULL COMMENT '响应结果',
  `status` int(4) DEFAULT '0' COMMENT '操作状态，0是正常，1是异常',
  `error_msg` varchar(255) NOT NULL COMMENT '错误信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`,`error_msg`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_log_info` */

/*Table structure for table `tb_dianpingsession` */

DROP TABLE IF EXISTS `tb_dianpingsession`;

CREATE TABLE `tb_dianpingsession` (
  `dp_session` varchar(200) DEFAULT NULL,
  `dp_refresh_token` varchar(200) DEFAULT NULL,
  `dp_id` varchar(200) NOT NULL,
  PRIMARY KEY (`dp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_dianpingsession` */

insert  into `tb_dianpingsession`(`dp_session`,`dp_refresh_token`,`dp_id`) values ('e0379bd998d105db8c1a7110877ee40f4a7ac82','ff2d7b134da1cd10e03799rt434923c08fbcae36','1');

/*Table structure for table `tb_dpvoucherorder` */

DROP TABLE IF EXISTS `tb_dpvoucherorder`;

CREATE TABLE `tb_dpvoucherorder` (
  `dporder_id` varchar(200) COLLATE utf8_unicode_ci NOT NULL COMMENT '券码',
  `user_id` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户id',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `dporder_duration` decimal(10,2) DEFAULT NULL COMMENT '时长',
  `postscript` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `dporder_status` int(11) DEFAULT NULL COMMENT '状态 已使用',
  `room` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '可用房间类型',
  PRIMARY KEY (`dporder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `tb_dpvoucherorder` */

insert  into `tb_dpvoucherorder`(`dporder_id`,`user_id`,`price`,`dporder_duration`,`postscript`,`dporder_status`,`room`) values ('123456789','o2eui5ZuZQt2eEsO7lyq0psWFXYg','49.90','3.00','大房先到先得',0,'[\"小\",\"中\",\"大\"]'),('dianping123456789','o2eui5ZuZQt2eEsO7lyq0psWFXYg','49.90','3.00','大房先到先得',0,'[\"小\",\"中\",\"大\"]');

/*Table structure for table `tb_emp` */

DROP TABLE IF EXISTS `tb_emp`;

CREATE TABLE `tb_emp` (
  `emp_name` varchar(200) DEFAULT NULL,
  `emp_account` varchar(200) NOT NULL,
  `emp_password` varchar(200) DEFAULT NULL,
  `store_id` varchar(200) DEFAULT NULL,
  `authority` int(11) DEFAULT NULL COMMENT '员工权限',
  `phone` varbinary(200) DEFAULT NULL,
  PRIMARY KEY (`emp_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_emp` */

insert  into `tb_emp`(`emp_name`,`emp_account`,`emp_password`,`store_id`,`authority`,`phone`) values ('类类','1656891032','20020227','001',1,'13923626789');

/*Table structure for table `tb_order` */

DROP TABLE IF EXISTS `tb_order`;

CREATE TABLE `tb_order` (
  `order_id` varchar(200) CHARACTER SET utf8 NOT NULL COMMENT '订单id',
  `user_id` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户id',
  `room_id` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '房间id',
  `price` decimal(10,2) DEFAULT NULL COMMENT '总金额',
  `start_time` datetime DEFAULT NULL COMMENT '订单开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '订单结束时间',
  `store_id` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '门店号',
  `order_status` int(11) DEFAULT NULL COMMENT '订单状态：1->已付款，2->待付款，3->未支付',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_user` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `is_delete` int(11) DEFAULT '0',
  `partner_id` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '商户号',
  `is_voucher` varchar(200) CHARACTER SET utf8 DEFAULT '0' COMMENT '是否用券 0为没用 1为美团  2为自身平台的券',
  `image` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '图片路径',
  `voucher_id` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '使用的优惠券id',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_order` */

/*Table structure for table `tb_room` */

DROP TABLE IF EXISTS `tb_room`;

CREATE TABLE `tb_room` (
  `room_id` varchar(200) NOT NULL COMMENT '房间id',
  `room_name` varchar(200) DEFAULT NULL COMMENT '房间名',
  `store_id` varchar(200) DEFAULT NULL COMMENT '门店id',
  `status` int(11) DEFAULT NULL COMMENT '状态（关门还是开门）',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格/小时',
  `image` varchar(200) DEFAULT NULL COMMENT '图片',
  `room_remarks` varchar(200) DEFAULT NULL COMMENT '房间备注',
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_room` */
/*Table structure for table `tb_store` */

DROP TABLE IF EXISTS `tb_store`;

CREATE TABLE `tb_store` (
  `store_id` varchar(200) NOT NULL COMMENT '门店号',
  `room_sum` int(11) DEFAULT NULL COMMENT '房间数',
  `store_status` int(11) DEFAULT NULL COMMENT '状态',
  `emp_id` varchar(200) DEFAULT NULL COMMENT '员工id',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `phone` varchar(200) DEFAULT NULL COMMENT '手机号',
  `wifi` varchar(200) DEFAULT NULL COMMENT 'WiFi账号',
  `store_name` varchar(200) DEFAULT NULL COMMENT '门店名',
  `wifi_password` varbinary(200) DEFAULT NULL COMMENT 'WIFI密码',
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_store` */


/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `openid` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `union_id` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `update_time` bigint(20) DEFAULT NULL,
  `is_delete` int(10) DEFAULT '0',
  PRIMARY KEY (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_user` */

insert  into `tb_user`(`openid`,`nickname`,`avatar_url`,`union_id`,`phone`,`create_time`,`update_time`,`is_delete`) values ('o2eui5ZuZQt2eEsO7lyq0psWFXYg',NULL,'https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `tb_voucher` */

DROP TABLE IF EXISTS `tb_voucher`;

CREATE TABLE `tb_voucher` (
  `voucher_id` varchar(200) NOT NULL COMMENT '卡券id',
  `title` varchar(200) DEFAULT NULL COMMENT '卡券标题',
  `available_range` varchar(200) DEFAULT NULL COMMENT '可用范围',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `term` int(11) DEFAULT NULL COMMENT '有效期',
  `vou_status` int(11) DEFAULT NULL COMMENT '状态（1.上架2.下架）',
  `original_price` int(11) DEFAULT NULL COMMENT '原价',
  `duration` decimal(10,2) DEFAULT NULL COMMENT '使用时长',
  `is_delete` int(11) DEFAULT NULL COMMENT '逻辑删除：0',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`voucher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_voucher` */

insert  into `tb_voucher`(`voucher_id`,`title`,`available_range`,`price`,`term`,`vou_status`,`original_price`,`duration`,`is_delete`,`create_time`,`update_time`) values ('1','5小时次卡','十三幺(333)、杠上开花(777)','98.00',30,1,115,'5.00',1,'2022-11-15 21:43:02','2022-11-15 21:43:06'),('2','白天五小时畅玩券','大四喜(999)','78.00',30,1,110,'5.00',1,NULL,NULL);

/*Table structure for table `tb_voucher_order` */

DROP TABLE IF EXISTS `tb_voucher_order`;

CREATE TABLE `tb_voucher_order` (
  `id` varchar(200) NOT NULL COMMENT '主键',
  `voucher_id` varchar(200) DEFAULT NULL COMMENT '购买卡券的id',
  `user_id` varchar(200) DEFAULT NULL COMMENT '下单用户的id',
  `order_status` int(11) DEFAULT NULL COMMENT '订单状态（1.有效2.无效3.已使用）',
  `create_time` datetime DEFAULT NULL COMMENT '下单时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `tb_voucher_order`(`id`,`voucher_id`,`user_id`,`order_status`,`create_time`,`pay_time`) values ('20221224121955119554','1','o2eui5ZuZQt2eEsO7lyq0psWFXYg',1,'2022-12-24 12:19:56','2022-12-24 12:34:27');
