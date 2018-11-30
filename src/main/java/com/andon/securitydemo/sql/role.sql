/*
Navicat MySQL Data Transfer

Source Server         : bida_count
Source Server Version : 50718
Source Host           : rm-2ze1363b0g4xh8fk53o.mysql.rds.aliyuncs.com:3306
Source Database       : security

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-11-30 16:27:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `role_name_cn` varchar(255) DEFAULT NULL COMMENT '角色名称(中文)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'ROLE_ADMIN', '管理员', null);
INSERT INTO `role` VALUES ('2', 'ROLE_SUPER', '超级管理员', null);
INSERT INTO `role` VALUES ('3', 'ROLE_USER', '用户管理员', null);
INSERT INTO `role` VALUES ('4', 'ROLE_ROLE', '角色管理员', null);
INSERT INTO `role` VALUES ('5', 'ROLE_MENU', '菜单管理员', null);
INSERT INTO `role` VALUES ('6', 'ROLE_USER', '普通用户', null);
INSERT INTO `role` VALUES ('7', 'ROLE_USER1', '普通用户1', null);
INSERT INTO `role` VALUES ('8', 'ROLE_USER5', '普通用户5', null);
INSERT INTO `role` VALUES ('9', 'ROLE_USER6', '普通用户6', null);
INSERT INTO `role` VALUES ('11', 'ROLE_user7', '普通用户7', null);
