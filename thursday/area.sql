/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : o2o

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2019-08-08 13:40:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `area_id` int(2) NOT NULL auto_increment COMMENT 'id',
  `area_name` varchar(200) NOT NULL COMMENT '区域名',
  `priority` int(2) NOT NULL default '0' COMMENT '权重',
  `creat_time` datetime default NULL COMMENT '创建时间',
  `last_edit_time` datetime default NULL COMMENT '更新时间',
  PRIMARY KEY  (`area_id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
