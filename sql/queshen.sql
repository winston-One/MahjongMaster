/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.7.35 : Database - queshen
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`queshen` /*!40100 DEFAULT CHARACTER SET utf8 */;

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

/*Table structure for table `tb_dianpingsession` */

DROP TABLE IF EXISTS `tb_dianpingsession`;

CREATE TABLE `tb_dianpingsession` (
  `dp_session` varchar(200) DEFAULT NULL,
  `dp_refresh_token` varchar(200) DEFAULT NULL,
  `dp_id` varchar(200) NOT NULL,
  PRIMARY KEY (`dp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_dianpingsession` */

insert  into `tb_dianpingsession`(`dp_session`,`dp_refresh_token`,`dp_id`) values ('e0379bd308d105db8c1a71103877ee40f4a7ac82','ff2d7b134da1cd10e03743e5434923c08fbcae36','1');

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

insert  into `tb_dpvoucherorder`(`dporder_id`,`user_id`,`price`,`dporder_duration`,`postscript`,`dporder_status`,`room`) values ('123456789',NULL,'49.90','3.00','大房先到先得',0,'[\"小\",\"中\",\"大\"]'),('dianping123456789','123456','49.90','3.00','大房先到先得',0,'[\"小\",\"中\",\"大\"]');

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
  `order_id` varchar(200) NOT NULL COMMENT '订单id',
  `user_id` varchar(200) DEFAULT NULL COMMENT '用户id',
  `room_id` varchar(200) DEFAULT NULL COMMENT '房间id',
  `price` decimal(10,2) DEFAULT NULL COMMENT '总金额',
  `start_time` datetime DEFAULT NULL COMMENT '订单开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '订单结束时间',
  `store_id` varchar(200) DEFAULT NULL COMMENT '门店号',
  `order_status` int(11) DEFAULT NULL COMMENT '订单状态：1->已付款，2->待付款，3->未支付',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(200) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_user` varchar(200) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  `partner_id` varchar(200) DEFAULT NULL COMMENT '商户号',
  `is_voucher` varchar(200) DEFAULT NULL COMMENT '是否用券 0为没用 1为美团  2为自身平台的券',
  `image` varchar(200) DEFAULT NULL COMMENT '图片路径',
  `voucher_id` varchar(200) DEFAULT NULL,
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_order` */

insert  into `tb_order`(`order_id`,`user_id`,`room_id`,`price`,`start_time`,`end_time`,`store_id`,`order_status`,`create_time`,`create_user`,`update_time`,`update_user`,`is_delete`,`partner_id`,`is_voucher`,`image`,`voucher_id`,`pay_time`) values ('25141671189736315queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:22:17','0','2022-12-16 19:22:17',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671189746627queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:22:27','0','2022-12-16 19:22:27',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671190312260queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:31:53','0','2022-12-16 19:31:53',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671190321024queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:32:02','0','2022-12-16 19:32:02',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671190348040queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:32:29','0','2022-12-16 19:32:29',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671190652199queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:37:33','0','2022-12-16 19:37:33',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671190666712queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:37:47','0','2022-12-16 19:37:47',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671190778895queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:39:39','0','2022-12-16 19:39:39',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671190974018queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:42:55','0','2022-12-16 19:42:55',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671191062810queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:45:01','0','2022-12-16 19:45:01',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671191227431queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:47:08','0','2022-12-16 19:47:08',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671191238613queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:47:19','0','2022-12-16 19:47:19',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671191338077queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:48:59','0','2022-12-16 19:48:59',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671191348705queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:49:09','0','2022-12-16 19:49:09',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671191550140queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:52:31','0','2022-12-16 19:52:31',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671191598204queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:53:19','0','2022-12-16 19:53:19',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671191760773queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 19:56:01','0','2022-12-16 19:56:01',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671192244137queshen','525141','001','66.00','2020-06-12 00:00:10','2020-06-12 23:59:10','97901',2,'2022-12-16 20:04:05','0','2022-12-16 20:04:05',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671192804205queshen','525141','001','66.00','2020-06-12 00:25:10','2020-06-12 23:46:10','97901',1,'2022-12-16 20:13:25','0','2022-12-16 20:14:13',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671193068495queshen','525141','001','66.00','2020-06-12 00:25:10','2020-06-12 23:46:10','97901',1,'2022-12-16 20:17:49','0','2022-12-16 20:18:22',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671193104481queshen','525141','001','66.00','2020-06-12 00:25:10','2020-06-12 23:46:10','97901',2,'2022-12-16 20:18:25','0','2022-12-16 20:18:25',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671193324089queshen','525141','001','66.00','2020-06-13 00:25:10','2020-06-14 23:46:10','97901',3,'2022-12-16 20:22:05','0','2022-12-16 20:42:42',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671193720003queshen','525141','001','66.00','2020-06-11 00:25:10','2020-06-12 23:46:10','97901',3,'2022-12-16 20:28:41','0','2022-12-16 20:44:34',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671193753998queshen','525141','001','66.00','2020-06-10 00:25:10','2020-06-13 23:46:10','97901',3,'2022-12-16 20:29:15','0','2022-12-16 20:44:34',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671193785400queshen','525141','001','66.00','2020-06-19 00:25:10','2020-06-20 23:46:10','97901',3,'2022-12-16 20:29:46','0','2022-12-16 20:49:24',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671194994346queshen','525141','001','66.00','2020-06-19 00:25:10','2020-06-20 23:46:10','97901',2,'2022-12-16 20:49:55','0','2022-12-16 20:49:55',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671195073117queshen','525141','001','66.00','2020-06-19 00:25:10','2020-06-20 23:46:10','97901',2,'2022-12-16 20:53:16','0','2022-12-16 20:53:16',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671195368938queshen','525141','001','66.00','2020-06-19 00:25:10','2020-06-20 23:46:10','97901',3,'2022-12-16 20:56:10','0','2022-12-16 21:12:24',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671195447207queshen','525141','001','66.00','2020-06-23 00:25:10','2020-06-24 23:46:10','97901',3,'2022-12-16 20:57:28','0','2022-12-17 00:15:12',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671196022538queshen','525141','001','66.00','2020-06-09 00:25:10','2020-06-09 23:46:10','97901',3,'2022-12-16 21:07:03','0','2022-12-17 00:15:12',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521',NULL),('25141671196045845queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2020-05-09 00:25:10','2020-05-09 23:46:10','97901',3,'2022-12-16 21:07:26','0','2022-12-23 23:40:20',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2022-12-23 23:39:48'),('25141671196053824queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2020-09-09 00:25:10','2020-09-09 23:46:10','97901',3,'2022-12-16 21:07:35','0','2022-12-23 23:40:20',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2022-12-23 23:39:53'),('25141671249125931queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2020-09-09 00:25:10','2020-09-09 23:46:10','97901',3,'2022-12-17 11:52:06','0','2022-12-23 23:40:21',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2022-12-23 23:39:54'),('25141671249299001queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2020-09-09 00:25:10','2020-09-09 23:46:10','97901',2,'2022-12-17 11:54:59','0','2022-12-23 23:40:22',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','202010244521','2022-12-23 23:39:56'),('25141671249387876queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',2,'2022-12-17 11:56:28','0','2022-12-24 15:11:03',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:39:58'),('25141671249495521queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',3,'2022-12-17 11:58:16','0','2022-12-24 15:11:02',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:01'),('25141671249548081queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',3,'2022-12-17 11:59:08','0','2022-12-24 15:11:01',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:03'),('25141671249713518queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',3,'2022-12-17 12:01:54','0','2022-12-24 15:10:58',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:05'),('25141671518971302queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-20 14:49:31','0','2022-12-24 15:10:57',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:07'),('25141671684285481queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-22 12:44:47','0','2022-12-24 15:10:56',NULL,NULL,NULL,'1','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-22 12:45:09'),('2eui1671638068097queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','297.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',3,'2022-12-21 23:54:28','0','2022-12-24 15:10:56',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:09'),('2eui1671854734010queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','462.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',3,'2022-12-24 12:05:33','0','2022-12-24 15:10:55',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png',NULL,NULL),('wxww1671039089222queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','002','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 00:10:45',NULL,'2023-04-24 18:11:29',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:56'),('wxww1671039089223queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','003','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 00:12:38',NULL,'2023-04-24 18:11:31',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:58'),('wxww1671039089224queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','003','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 00:12:53',NULL,'2023-04-24 18:11:30',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:40:59'),('wxww1671039089225queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','002','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 00:13:17',NULL,'2023-04-24 18:11:33',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:41:01'),('wxww1671039089226queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','002','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 00:13:35',NULL,'2023-04-24 18:11:32',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1','2022-12-23 23:41:03'),('wxww1671039089227queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','004','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 00:13:51',NULL,'2023-04-24 18:11:33',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089228queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','004','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 00:14:08',NULL,'2023-04-24 18:11:35',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089731queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-16 14:47:32','0','2023-04-24 18:11:36',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089732queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-16 14:49:18','0','2023-04-24 18:11:35',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089733queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 12:30:02',NULL,'2023-04-24 18:11:37',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089734queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 12:31:13',NULL,'2023-04-24 18:11:37',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089735queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 12:32:00',NULL,'2023-04-24 18:11:38',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089736queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 12:32:57',NULL,'2023-04-24 18:12:24',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','1',NULL),('wxww1671039089737queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-25 13:00:00','2022-12-25 17:00:00','97901',1,'2022-12-17 12:33:48',NULL,'2023-04-24 18:11:39',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png',NULL,NULL),('wxww1671039089738queshen','o2eui5ZuZQt2eEsO7lyq0psWFXYg','001','66.00','2022-12-24 17:00:00','2022-12-24 20:00:00','97901',1,'2022-12-17 12:34:43',NULL,'2023-04-24 18:12:18',NULL,NULL,NULL,NULL,'http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png',NULL,NULL);

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

insert  into `tb_room`(`room_id`,`room_name`,`store_id`,`status`,`price`,`image`,`room_remarks`) values ('001','九宝莲灯(222)','97901',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG8aAJX5pABJGXzfrx7w832.png','有窗/沙发'),('002','十三幺(333)','97901',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG_eAHOOZABLHFt0tMX0716.png','空调/沙发'),('003','杠上开花(777)','97901',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObHAaAKtTjABEp23iWzDA851.png','有窗'),('004','大四喜(999)','97901',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObHBOAKw1CABIbxCxq5PI890.png','有窗/空调'),('005','海底捞月(111)','97901',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObHCiAA-4FAA-vyoZK0hg583.png','有窗/空调/沙发'),('006','大三元(888)','97901',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObHDeAC1gPABAkkpxM8d8276.png','有窗/空调/沙发'),('007','天地胡(666)','97901',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObHEeAKQDWABWbADufYCI006.png','有窗'),('008','九宝莲灯(222)','97901',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObHDeAC1gPABAkkpxM8d8276.png','空调/沙发'),('009','十三幺(333)','97902',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObHEeAKQDWABWbADufYCI006.png','有窗/沙发'),('010','杠上开花(777)','97902',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObHCiAA-4FAA-vyoZK0hg583.png','有窗/沙发'),('011','大四喜(999)','97902',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObHCiAA-4FAA-vyoZK0hg583.png','有窗/空调/沙发'),('012','海底捞月(111)','97902',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObHCiAA-4FAA-vyoZK0hg583.png','有窗/空调'),('013','大三元(888)','97902',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObHCiAA-4FAA-vyoZK0hg583.png','有窗/空调/沙发'),('014','天地胡(666)','97902',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObHAaAKtTjABEp23iWzDA851.png','有窗/空调 '),('015','九宝莲灯(222)','97903',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObHAaAKtTjABEp23iWzDA851.png','有窗/沙发'),('016','十三幺(333)','97903',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObHAaAKtTjABEp23iWzDA851.png','沙发'),('017','杠上开花(777)','97903',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG_eAHOOZABLHFt0tMX0716.png','空调/沙发'),('018','大四喜(999)','97903',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG_eAHOOZABLHFt0tMX0716.png','有窗'),('019','十三幺(333)','97904',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG_eAHOOZABLHFt0tMX0716.png','有窗/沙发'),('020','大四喜(999)','97904',1,'66.00','http://43.143.88.250:22000/group1/M00/00/00/CgAEC2ObG_eAHOOZABLHFt0tMX0716.png','有窗/空调');

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

insert  into `tb_store`(`store_id`,`room_sum`,`store_status`,`emp_id`,`address`,`phone`,`wifi`,`store_name`,`wifi_password`) values ('97901',100,1,'001','惠城区安鸿商务大厦11楼110A','18688317496','mashangdao','安鸿店','MSD888888'),('97902',20,1,'003','惠城区麦地路69号达利大厦601','18688317496','mashangdao','数码街店','MSD888888'),('97903',30,1,'003','惠城区升平苑麦兴路23-16','18688317496','mashangdao','麦地升平苑店','MSD888888'),('97904',20,1,'004','惠城区河南岸演达大道12号海燕玉兰花园E栋2层08号房','18688317496','mashangdao','玉兰店','MSD888888');

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

/*Data for the table `tb_voucher_order` */

insert  into `tb_voucher_order`(`id`,`voucher_id`,`user_id`,`order_status`,`create_time`,`pay_time`) values ('20221224121955119554','1','o2eui5ZuZQt2eEsO7lyq0psWFXYg',1,'2022-12-24 12:19:56','2022-12-24 12:34:27');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
