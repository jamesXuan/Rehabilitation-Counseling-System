/*
SQLyog Professional v12.09 (64 bit)
MySQL - 5.7.39 : Database - rehab
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rehab` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `rehab`;

/*Table structure for table `answermodel` */

DROP TABLE IF EXISTS `answermodel`;

CREATE TABLE `answermodel` (
  `Answer_Id` varchar(32) NOT NULL,
  `Answer_type` varchar(10) NOT NULL,
  `Answer_example` varchar(128) NOT NULL,
  `Answer_style` varchar(10) NOT NULL,
  PRIMARY KEY (`Answer_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `articlerecommend` */

DROP TABLE IF EXISTS `articlerecommend`;

CREATE TABLE `articlerecommend` (
  `Article_Id` varchar(32) NOT NULL,
  `Article_type` varchar(10) NOT NULL,
  `Article_title` varchar(128) NOT NULL,
  `Article_context` text NOT NULL,
  `Article_time` varchar(32) NOT NULL,
  `Article_author` varchar(8) NOT NULL,
  `Article_viewnumber` int(16) NOT NULL,
  `Article_Img` varchar(10) DEFAULT NULL,
  `Article_compliment` int(16) NOT NULL,
  `Article_note` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`Article_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `consultrecord` */

DROP TABLE IF EXISTS `consultrecord`;

CREATE TABLE `consultrecord` (
  `Record_Id` varchar(32) NOT NULL,
  `User_Id` varchar(32) DEFAULT NULL,
  `Record_Time` datetime NOT NULL,
  `Record_topic` varchar(64) NOT NULL,
  `Record_context` text NOT NULL,
  `Record_title` varchar(32) NOT NULL,
  `Record_img` varchar(10) DEFAULT NULL,
  `Record_note` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`Record_Id`),
  KEY `fkuid` (`User_Id`),
  CONSTRAINT `fkuid` FOREIGN KEY (`User_Id`) REFERENCES `user` (`User_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `manager` */

DROP TABLE IF EXISTS `manager`;

CREATE TABLE `manager` (
  `Admin_Id` varchar(32) NOT NULL,
  `Admin_name` varchar(10) NOT NULL,
  `Admin_password` varchar(32) NOT NULL,
  `Admin_limit` int(3) NOT NULL,
  PRIMARY KEY (`Admin_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `photomanager` */

DROP TABLE IF EXISTS `photomanager`;

CREATE TABLE `photomanager` (
  `photo_Id` int(32) NOT NULL,
  `photo_type` varchar(10) DEFAULT NULL,
  `photo_src` varchar(16) NOT NULL,
  PRIMARY KEY (`photo_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `questionmodel` */

DROP TABLE IF EXISTS `questionmodel`;

CREATE TABLE `questionmodel` (
  `Question_Id` varchar(32) NOT NULL,
  `Question_type` varchar(32) NOT NULL,
  `Question_feature` varchar(32) NOT NULL,
  PRIMARY KEY (`Question_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `User_Id` varchar(32) NOT NULL,
  `User_name` varchar(32) NOT NULL,
  `User_sex` varchar(2) NOT NULL,
  `User_age` int(3) NOT NULL,
  `user_preference` varchar(128) DEFAULT NULL,
  `User_medicalhistory` varchar(128) DEFAULT NULL,
  `User_allergyhistory` varchar(128) DEFAULT NULL,
  `User_password` varchar(16) NOT NULL,
  `User_phonenumber` varchar(12) DEFAULT NULL,
  `User_email` varchar(32) DEFAULT NULL,
  `User_head` varchar(10) DEFAULT NULL,
  `User_note` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`User_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `welcome` */

DROP TABLE IF EXISTS `welcome`;

CREATE TABLE `welcome` (
  `Welcome_Id` varchar(32) NOT NULL,
  `Welcome_titleone` varchar(32) DEFAULT NULL,
  `Welcome_titletwo` varchar(32) DEFAULT NULL,
  `Welcome_context` varchar(128) DEFAULT NULL,
  `Welcome_img` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Welcome_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
