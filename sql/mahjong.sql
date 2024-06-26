/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.7.37 : Database - queshen
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`queshen` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `queshen`;

/*Table structure for table `re_order_voucher` */

DROP TABLE IF EXISTS `re_order_voucher`;

CREATE TABLE `re_order_voucher` (
  `order_id` varchar(200) DEFAULT NULL,
  `voucher_id` varchar(200) DEFAULT NULL,
  `voucher_type` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `re_order_voucher` */

/*Table structure for table `t_chat_msg` */

DROP TABLE IF EXISTS `t_chat_msg`;

CREATE TABLE `t_chat_msg` (
  `id` varchar(255) NOT NULL COMMENT '消息id',
  `content` varchar(255) DEFAULT NULL COMMENT '消息内容',
  `type` int(4) DEFAULT '1' COMMENT '1是文字，2是图片',
  `openid` varchar(255) DEFAULT NULL COMMENT '发送者id',
  `receive_id` varchar(255) DEFAULT NULL COMMENT '接收者id',
  `conversation_id` varchar(255) DEFAULT NULL COMMENT '会话id',
  `is_unread` int(4) DEFAULT '1' COMMENT '该消息是否未读,1是未读，0是已读',
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

/*Table structure for table `t_conversation` */

DROP TABLE IF EXISTS `t_conversation`;

CREATE TABLE `t_conversation` (
  `id` varchar(255) NOT NULL COMMENT '会话唯一标识',
  `last_msg` varchar(255) NOT NULL COMMENT '会话中存放的内容是聊天的最后一条记录',
  `type` int(4) NOT NULL DEFAULT '1' COMMENT '最后一条消息的类型，1是文字，2是图片',
  `is_deleted` int(4) NOT NULL DEFAULT '1',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_conversation` */

/*Table structure for table `t_log_info` */

DROP TABLE IF EXISTS `t_log_info`;

CREATE TABLE `t_log_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `content` varchar(255) DEFAULT NULL COMMENT '操作功能',
  `busines_type` int(4) DEFAULT '3' COMMENT '业务类型(0是授权，1是导出，2是导入，3是上传)',
  `method` varchar(255) DEFAULT NULL COMMENT 'conttoller方法',
  `request_method` varchar(255) DEFAULT 'POST' COMMENT '请求方式(GET，POST,PUT，DELETE)',
  `operator_id` varchar(255) DEFAULT 'o2eui5ZuZQt2eEsO7lyq0psWFXYg' COMMENT '操作者id(默认就是测试人员的openid)',
  `url` varchar(255) DEFAULT NULL COMMENT '请求路径',
  `ip` varchar(255) DEFAULT NULL COMMENT 'ip地址',
  `param` varchar(255) DEFAULT NULL COMMENT '请求参数',
  `result` varchar(255) DEFAULT NULL COMMENT '响应结果',
  `status` int(4) DEFAULT '0' COMMENT '操作状态，0是正常，1是异常',
  `error_msg` varchar(255) DEFAULT NULL COMMENT '错误信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_log_info` */

insert  into `t_log_info`(`id`,`content`,`busines_type`,`method`,`request_method`,`operator_id`,`url`,`ip`,`param`,`result`,`status`,`error_msg`,`create_time`,`update_time`) values (1,'操作查询可预约房间',3,NULL,'POST','o2eui5ZuZQt2eEsO7lyq0psWFXYg','1','1','1',NULL,0,'无','2023-06-22 15:16:54','2023-06-22 15:16:56');

/*Table structure for table `t_test` */

DROP TABLE IF EXISTS `t_test`;

CREATE TABLE `t_test` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_test` */

insert  into `t_test`(`id`,`name`,`parent_id`) values ('1','节点1','-1'),('2','节点2','1'),('3','节点3','1'),('4','节点4','1'),('5','节点5','3'),('6','节点6','3');

/*Table structure for table `t_user_conversation` */

DROP TABLE IF EXISTS `t_user_conversation`;

CREATE TABLE `t_user_conversation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(255) DEFAULT NULL COMMENT '发送者',
  `receive_id` varchar(255) DEFAULT NULL COMMENT '接收者',
  `conversation_id` varchar(255) DEFAULT NULL COMMENT '会话id',
  `unread_count` int(11) NOT NULL DEFAULT '0' COMMENT '会话聊天中未读的消息数',
  `is_deleted` int(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_user_conversation` */

/*Table structure for table `tb_dianping_session` */

DROP TABLE IF EXISTS `tb_dianping_session`;

CREATE TABLE `tb_dianping_session` (
  `dp_session` varchar(200) DEFAULT NULL,
  `dp_refresh_token` varchar(200) DEFAULT NULL,
  `dp_id` varchar(200) NOT NULL,
  PRIMARY KEY (`dp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_dianping_session` */

insert  into `tb_dianping_session`(`dp_session`,`dp_refresh_token`,`dp_id`) values ('e0379bd308d105db8c1a71103877ee40f4a7ac82','ff2d7b134da1cd10e03743e5434923c08fbcae36','1');

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

insert  into `tb_order`(`order_id`,`user_id`,`room_id`,`price`,`start_time`,`end_time`,`store_id`,`order_status`,`create_time`,`create_user`,`update_time`,`update_user`,`is_delete`,`partner_id`,`is_voucher`,`image`,`voucher_id`,`pay_time`) values ('25141671189736315queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:22:17','0','2023-06-26 14:16:09',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2023-06-26 14:16:05'),('25141671189746627queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:22:27','0','2023-06-26 14:16:11',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2023-06-26 14:16:08'),('25141671190312260queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:31:53','0','2023-06-26 14:16:13',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2023-06-26 14:16:09'),('25141671190321024queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:32:02','0','2023-06-26 14:16:14',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2023-06-26 14:16:11'),('25141671190348040queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:32:29','0','2023-06-26 14:16:16',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2023-06-26 14:16:13'),('25141671190652199queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:37:33','0','2023-06-26 14:16:20',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2023-06-26 14:16:17'),('25141671190666712queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:37:47','0','2023-06-26 14:16:21',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2023-06-26 14:16:18'),('25141671190778895queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:39:39','0','2022-12-16 19:39:39',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671190974018queshen','525141','001','66.00','2020-06-12 00:00:00','2020-06-12 23:59:00','97901',3,'2022-12-16 19:42:55','0','2023-06-26 14:16:26',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2023-06-26 14:16:20'),('25141671191062810queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:45:01','0','2023-06-26 14:15:44',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671191227431queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:47:08','0','2023-06-26 14:15:44',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671191238613queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:47:19','0','2023-06-26 14:15:44',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671191338077queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:48:59','0','2023-06-26 14:15:45',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671191348705queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:49:09','0','2023-06-26 14:15:45',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671191550140queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:52:31','0','2023-06-26 14:15:46',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671191598204queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:53:19','0','2023-06-26 14:15:46',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671191760773queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:56:01','0','2023-06-26 14:15:47',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671192244137queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 20:04:05','0','2023-06-26 14:15:49',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671192804205queshen','525141','001','66.00','2020-06-12 00:25:10','2020-06-12 23:46:10','97901',1,'2022-12-16 20:13:25','0','2023-06-26 14:15:47',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671193068495queshen','525141','001','66.00','2020-06-12 00:25:10','2020-06-12 23:46:10','97901',1,'2022-12-16 20:17:49','0','2023-06-26 14:15:47',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671193104481queshen','525141','001','66.00','2020-06-12 00:25:10','2020-06-12 23:46:10','97901',2,'2022-12-16 20:18:25','0','2023-06-26 14:15:48',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671193324089queshen','525141','001','66.00','2020-06-13 00:25:10','2020-06-14 23:46:10','97901',3,'2022-12-16 20:22:05','0','2023-06-26 14:15:49',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671193720003queshen','525141','001','66.00','2020-06-11 00:25:10','2020-06-12 23:46:10','97901',3,'2022-12-16 20:28:41','0','2023-06-26 14:15:50',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671193753998queshen','525141','001','66.00','2020-06-10 00:25:10','2020-06-13 23:46:10','97901',3,'2022-12-16 20:29:15','0','2023-06-26 14:15:50',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671193785400queshen','525141','001','66.00','2020-06-19 00:25:10','2020-06-20 23:46:10','97901',3,'2022-12-16 20:29:46','0','2023-06-26 14:15:53',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671194994346queshen','525141','001','66.00','2020-06-19 00:25:10','2020-06-20 23:46:10','97901',2,'2022-12-16 20:49:55','0','2023-06-26 14:15:50',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671195073117queshen','525141','001','66.00','2020-06-19 00:25:10','2020-06-20 23:46:10','97901',2,'2022-12-16 20:53:16','0','2023-06-26 14:15:51',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671195368938queshen','525141','001','66.00','2020-06-19 00:25:10','2020-06-20 23:46:10','97901',3,'2022-12-16 20:56:10','0','2023-06-26 14:15:51',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671195447207queshen','525141','001','66.00','2020-06-23 00:25:10','2020-06-24 23:46:10','97901',3,'2022-12-16 20:57:28','0','2023-06-26 14:15:52',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671196022538queshen','525141','001','66.00','2020-06-09 00:25:10','2020-06-09 23:46:10','97901',3,'2022-12-16 21:07:03','0','2023-06-26 14:15:56',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671196045845queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2020-05-09 00:25:10','2020-05-09 23:46:10','97901',3,'2022-12-16 21:07:26','0','2022-12-23 23:40:20',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2022-12-23 23:39:48'),('25141671196053824queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2020-09-09 00:25:10','2020-09-09 23:46:10','97901',3,'2022-12-16 21:07:35','0','2022-12-23 23:40:20',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2022-12-23 23:39:53'),('25141671249125931queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2020-09-09 00:25:10','2020-09-09 23:46:10','97901',3,'2022-12-17 11:52:06','0','2022-12-23 23:40:21',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2022-12-23 23:39:54'),('25141671249299001queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2020-09-09 00:25:10','2020-09-09 23:46:10','97901',2,'2022-12-17 11:54:59','0','2022-12-23 23:40:22',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2022-12-23 23:39:56'),('25141671249387876queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',2,'2022-12-17 11:56:28','0','2022-12-24 15:11:03',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:39:58'),('25141671249495521queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',3,'2022-12-17 11:58:16','0','2022-12-24 15:11:02',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:01'),('25141671249548081queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',3,'2022-12-17 11:59:08','0','2022-12-24 15:11:01',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:03'),('25141671249713518queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',3,'2022-12-17 12:01:54','0','2022-12-24 15:10:58',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:05'),('25141671518971302queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-20 14:49:31','0','2023-06-26 14:15:38',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:07'),('25141671684285481queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-22 12:44:47','0','2023-06-26 14:15:36',NULL,0,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-22 12:45:09'),('2eui1671638068097queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','297.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',3,'2022-12-21 23:54:28','0','2023-06-26 14:15:37',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:09'),('2eui1671854734010queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','462.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',3,'2022-12-24 12:05:33','0','2023-06-26 14:15:33',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png',NULL,NULL),('2eui1687661564650queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','330.00','2023-06-26 00:00:00','2023-06-26 04:30:00','97901',3,'2023-06-25 14:14:51','0','2023-06-26 14:15:07',NULL,0,NULL,'0','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png',NULL,NULL),('2eui1687761582662queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','330.00','2023-06-26 15:00:00','2023-06-26 19:30:00','97901',1,NULL,'0','2023-06-26 14:39:44',NULL,0,NULL,'0','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png',NULL,'2023-06-26 14:39:43'),('2eui1687762416116queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','330.00','2023-06-27 00:00:00','2023-06-27 04:30:00','97901',1,NULL,'0','2023-06-26 14:53:38',NULL,0,NULL,'0','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png',NULL,'2023-06-26 14:53:36'),('wxww1671039089222queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','002','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 00:10:45',NULL,'2023-06-26 14:15:33',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:56'),('wxww1671039089223queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','003','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 00:12:38',NULL,'2023-06-26 14:15:32',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:58'),('wxww1671039089224queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','003','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 00:12:53',NULL,'2023-06-26 14:15:32',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:59'),('wxww1671039089225queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','002','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 00:13:17',NULL,'2023-06-26 14:15:31',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:41:01'),('wxww1671039089226queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','002','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 00:13:35',NULL,'2023-06-26 14:15:31',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:41:03'),('wxww1671039089227queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','004','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 00:13:51',NULL,'2023-06-26 14:15:30',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089228queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','004','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 00:14:08',NULL,'2023-06-26 14:15:29',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089731queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-16 14:47:32','0','2023-06-26 14:15:28',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089732queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-16 14:49:18','0','2023-06-26 14:15:28',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089733queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 12:30:02',NULL,'2023-05-01 18:45:08',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089734queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 12:31:13',NULL,'2023-05-01 18:45:06',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089735queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 12:32:00',NULL,'2023-05-01 18:45:05',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089736queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 12:32:57',NULL,'2023-05-01 18:45:04',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089737queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2023-05-01 12:00:00','2023-05-01 13:00:00','97901',1,'2022-12-17 12:33:48',NULL,'2023-05-01 19:58:41',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png',NULL,NULL),('wxww1671039089738queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2023-05-01 17:00:00','2023-05-01 20:00:00','97901',1,'2022-12-17 12:34:43',NULL,'2023-05-01 18:45:04',NULL,0,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png',NULL,NULL);

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
  `is_deleted` int(11) DEFAULT '0',
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_room` */

insert  into `tb_room`(`room_id`,`room_name`,`store_id`,`status`,`price`,`image`,`room_remarks`,`is_deleted`) values ('001','九宝莲灯(222)','97901',1,'77.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗/沙发',0),('002','十三幺(333)','97901',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','空调/沙发',0),('003','杠上开花(777)','97901',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗',0),('004','大四喜(999)','97901',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗/空调',0),('005','海底捞月(111)','97901',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗/空调/沙发',0),('006','大三元(888)','97901',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗/空调/沙发',0),('007','天地胡(666)','97901',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗',0),('008','九宝莲灯(222)','97901',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','空调/沙发',0),('009','十三幺(333)','97902',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗/沙发',0),('010','杠上开花(777)','97902',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗/沙发',0),('011','大四喜(999)','97902',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗/空调/沙发',0),('012','海底捞月(111)','97902',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗/空调',0),('013','大三元(888)','97902',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗/空调/沙发',0),('014','天地胡(666)','97902',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗/空调 ',0),('015','九宝莲灯(222)','97903',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗/沙发',0),('016','十三幺(333)','97903',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','沙发',0),('017','杠上开花(777)','97903',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','空调/沙发',0),('018','大四喜(999)','97903',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗',0),('019','十三幺(333)','97904',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗/沙发/空调',0),('020','大四喜(999)','97904',1,'66.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','有窗/空调',0),('1111111','赢硬(1111)','97904',1,'12.00','https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg','空调/窗户',0);

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
  `is_deleted` int(11) DEFAULT '0',
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_store` */

insert  into `tb_store`(`store_id`,`room_sum`,`store_status`,`emp_id`,`address`,`phone`,`wifi`,`store_name`,`wifi_password`,`is_deleted`) values ('97901',100,1,'001','惠城区安鸿商务大厦11楼110A','18688317496','mashangdao','安鸿店','MSD888888',0),('97902',20,1,'003','惠城区麦地路69号达利大厦601','18688317496','mashangdao','数码街店','MSD888888',0),('97903',30,1,'003','惠城区升平苑麦兴路23-16','18688317496','mashangdao','麦地升平苑店','MSD888888',0),('97904',20,1,'004','惠城区河南岸演达大道12号海燕玉兰花园E栋2层08号房','186883174','mashangdao','玉兰店','MSD888888',0);

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `openid` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `union_id` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_delete` int(10) DEFAULT '0',
  PRIMARY KEY (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_user` */

insert  into `tb_user`(`openid`,`nickname`,`avatar_url`,`password`,`union_id`,`phone`,`money`,`create_time`,`update_time`,`is_delete`) values ('admin','WinstonYv',NULL,'123456',NULL,'13662508979',1000,NULL,'2024-04-30 09:27:34',0),('o2eui5ZuZQt2eEsO7lyq0psWFXYg','WinstonYv','https://thirdwx.qlogo.cn/mmopen/vi_32/POgEwh4mIHO4nibH0KlMECNjjGxQUq24ZEaGT4poC6icRiccVGKSyXwibcPq4BWmiaIGuG1icwxaQX6grC9VemZoJ8rg/132',NULL,NULL,'13662399903',999,NULL,NULL,0);

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

insert  into `tb_voucher`(`voucher_id`,`title`,`available_range`,`price`,`term`,`vou_status`,`original_price`,`duration`,`is_delete`,`create_time`,`update_time`) values ('1','5小时次卡','十三幺(333)、杠上开花(777)','78.00',30,1,115,'5.00',0,'2022-11-15 21:43:02','2024-04-29 14:17:40'),('2','白天五小时畅玩券','大四喜(999)','78.00',30,1,110,'5.00',0,'2024-04-29 11:22:14','2024-04-29 11:22:17');

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

/*Data for the table `tb_voucher_order` */

insert  into `tb_voucher_order`(`id`,`voucher_id`,`user_id`,`order_status`,`create_time`,`pay_time`) values ('20221224121955119554','1','o2eui5ZuZQt2eEsO7lyq0psWFXYg',1,'2022-12-24 12:19:56','2022-12-24 12:34:27');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
