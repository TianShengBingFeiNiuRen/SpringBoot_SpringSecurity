/*
Navicat MySQL Data Transfer

Source Server         : bida_count
Source Server Version : 50718
Source Host           : rm-2ze1363b0g4xh8fk53o.mysql.rds.aliyuncs.com:3306
Source Database       : security

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-11-30 16:27:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  KEY `user_id_PK` (`user_id`),
  KEY `role_id_PK` (`role_id`),
  CONSTRAINT `role_id_PK` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `user_id_PK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('2', '2');
INSERT INTO `user_role` VALUES ('3', '3');
INSERT INTO `user_role` VALUES ('4', '4');
INSERT INTO `user_role` VALUES ('5', '5');
INSERT INTO `user_role` VALUES ('7', '7');
INSERT INTO `user_role` VALUES ('7', '9');
INSERT INTO `user_role` VALUES ('8', '8');
INSERT INTO `user_role` VALUES ('6', '6');
INSERT INTO `user_role` VALUES ('6', '7');
INSERT INTO `user_role` VALUES ('6', '8');
INSERT INTO `user_role` VALUES ('6', '9');
INSERT INTO `user_role` VALUES ('3', '7');
INSERT INTO `user_role` VALUES ('10', '6');
INSERT INTO `user_role` VALUES ('10', '7');
INSERT INTO `user_role` VALUES ('10', '9');
