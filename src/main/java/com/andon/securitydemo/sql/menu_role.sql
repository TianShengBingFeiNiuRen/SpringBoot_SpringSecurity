/*
Navicat MySQL Data Transfer

Source Server         : bida_count
Source Server Version : 50718
Source Host           : rm-2ze1363b0g4xh8fk53o.mysql.rds.aliyuncs.com:3306
Source Database       : security

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-11-30 16:28:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role` (
  `menu_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  KEY `menu_id_PK` (`menu_id`),
  KEY `role_id_PK2` (`role_id`),
  CONSTRAINT `menu_id_PK` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `role_id_PK2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu_role
-- ----------------------------
INSERT INTO `menu_role` VALUES ('0', '2');
INSERT INTO `menu_role` VALUES ('3', '1');
INSERT INTO `menu_role` VALUES ('4', '1');
INSERT INTO `menu_role` VALUES ('5', '1');
INSERT INTO `menu_role` VALUES ('3', '3');
INSERT INTO `menu_role` VALUES ('4', '4');
INSERT INTO `menu_role` VALUES ('5', '5');
INSERT INTO `menu_role` VALUES ('6', '6');
INSERT INTO `menu_role` VALUES ('6', '7');
INSERT INTO `menu_role` VALUES ('6', '9');
INSERT INTO `menu_role` VALUES ('6', '11');
