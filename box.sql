/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.31 : Database - box
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`box` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `box`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='管理员';

/*Data for the table `admin` */

insert  into `admin`(`id`,`username`,`password`,`phone`) values (1,'admin','admin',NULL);

/*Table structure for table `box` */

DROP TABLE IF EXISTS `box`;

CREATE TABLE `box` (
  `box_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `box_addr` varchar(255) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`box_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='仓位';

/*Data for the table `box` */

insert  into `box`(`box_id`,`box_addr`,`product_id`) values (1,'238',6),(3,'69',15),(4,'2',18),(5,'5',19),(6,'1',20),(7,'3',24),(10,'请问',NULL);

/*Table structure for table `brokens` */

DROP TABLE IF EXISTS `brokens`;

CREATE TABLE `brokens` (
  `broken_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `broken_date` datetime DEFAULT NULL,
  PRIMARY KEY (`broken_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `brokens` */

insert  into `brokens`(`broken_id`,`admin_id`,`product_id`,`broken_date`) values (1,NULL,15,'2021-12-11 15:15:20');

/*Table structure for table `inbox` */

DROP TABLE IF EXISTS `inbox`;

CREATE TABLE `inbox` (
  `in_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `box_id` int(11) DEFAULT NULL,
  `in_pname` varchar(255) DEFAULT NULL,
  `in_quantity` int(11) DEFAULT NULL,
  `in_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`in_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='入库单\n';

/*Data for the table `inbox` */

insert  into `inbox`(`in_id`,`admin_id`,`product_id`,`box_id`,`in_pname`,`in_quantity`,`in_datetime`) values (1,NULL,15,3,'java',22,'2021-12-11 15:00:29'),(2,NULL,15,3,'java',23,'2021-12-11 16:00:00'),(3,NULL,19,5,'asdas',23,'2021-12-11 15:07:24'),(4,NULL,20,6,'螺丝',3,'2021-12-07 16:00:00'),(5,NULL,20,6,'螺丝',2,'2021-12-07 16:00:00'),(6,NULL,15,3,'java',99,'2021-12-11 15:11:05');

/*Table structure for table `outbox` */

DROP TABLE IF EXISTS `outbox`;

CREATE TABLE `outbox` (
  `out_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `box_id` int(11) DEFAULT NULL,
  `out_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`out_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='出库单';

/*Data for the table `outbox` */

insert  into `outbox`(`out_id`,`user_id`,`box_id`,`out_datetime`) values (1,NULL,6,'2021-12-01 16:00:00'),(2,NULL,3,'2021-12-11 15:20:14');

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `path` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资源路径',
  `comment` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `icon` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `permission` */

insert  into `permission`(`id`,`name`,`path`,`comment`,`icon`) values (1,'Home','/home','主页','el-icon-house'),(2,'Product','/product','物资管理','el-icon-files'),(3,'Inbox','/inbox','入库管理','el-icon-menu'),(4,'Outbox','/outbox','出库管理','el-icon-s-order'),(6,'Box','/box','仓位管理','el-icon-map-location'),(7,'Suppliers','/suppliers','供应商管理','el-icon-office-building'),(8,'Broken','/broken','报损管理','el-icon-message'),(9,'User','/user','用户管理','el-icon-user'),(10,'Purchase','/purchase','采购管理','el-icon-menu'),(13,'Person','/person','个人信息','el-icon-user-solid');

/*Table structure for table `products` */

DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COMMENT='物资\r\n';

/*Data for the table `products` */

insert  into `products`(`product_id`,`product_name`,`intro`) values (3,'螺丝刀','十字螺丝刀'),(4,'3M手套','good'),(5,'牛子','酸酸甜甜 真好吃'),(6,'仓位1',NULL),(8,'1',NULL),(9,'2',NULL),(10,'wa',NULL),(11,'嘉壕','单身狗 万年'),(13,'123',NULL),(15,'java',NULL),(18,'农夫山泉',NULL),(19,'asdas',NULL),(20,'螺丝',NULL),(21,'12123',NULL),(22,'嘉豪','爱的色放'),(24,'请问',NULL);

/*Table structure for table `purchases` */

DROP TABLE IF EXISTS `purchases`;

CREATE TABLE `purchases` (
  `purchase_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) DEFAULT NULL,
  `supply_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `purchase_date` datetime DEFAULT NULL,
  PRIMARY KEY (`purchase_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='采购单\r\n';

/*Data for the table `purchases` */

insert  into `purchases`(`purchase_id`,`admin_id`,`supply_id`,`product_id`,`purchase_date`) values (1,NULL,8,13,'2021-12-11 14:53:18'),(2,NULL,9,14,'2021-12-11 14:55:13'),(3,NULL,12,24,'2021-12-11 15:34:51');

/*Table structure for table `suppliers` */

DROP TABLE IF EXISTS `suppliers`;

CREATE TABLE `suppliers` (
  `supply_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) DEFAULT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `supply_phone` varchar(255) DEFAULT NULL,
  `supply_addr` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`supply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='供应商';

/*Data for the table `suppliers` */

insert  into `suppliers`(`supply_id`,`company_name`,`contact_name`,`supply_phone`,`supply_addr`) values (1,'广东工业大学','马如意','111111111111111','广州市'),(2,'广东工业大学','刘大斌','2222222222222','广州市'),(3,'广东工业大学','欧阳凑合','333333333333','广州市'),(4,'广东工业大学','刘芒','1','广州市'),(6,'2','马大哈','1881934','淘宝'),(7,'asd',NULL,NULL,NULL),(8,'qwe',NULL,NULL,NULL),(9,'123',NULL,NULL,NULL),(10,'3D打印','你姐','1993456','南亭'),(11,'123123',NULL,NULL,NULL),(12,'请问',NULL,NULL,NULL);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `stu_id` varchar(255) DEFAULT NULL,
  `studio_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`name`,`phone`,`email`,`stu_id`,`studio_name`) values (4,'1','1','1','1','1','1','1'),(5,'1','2','1','1','1','1','1'),(6,'1','2','3','3','3','3','3');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
