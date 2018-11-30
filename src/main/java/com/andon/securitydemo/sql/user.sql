/*
Navicat MySQL Data Transfer

Source Server         : bida_count
Source Server Version : 50718
Source Host           : rm-2ze1363b0g4xh8fk53o.mysql.rds.aliyuncs.com:3306
Source Database       : security

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-11-30 16:27:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin', null, null);
INSERT INTO `user` VALUES ('2', 'root', 'root', null, null);
INSERT INTO `user` VALUES ('3', 'zhangsan', '123', null, null);
INSERT INTO `user` VALUES ('4', 'lisi', '123', null, null);
INSERT INTO `user` VALUES ('5', 'wangwu', '123', null, null);
INSERT INTO `user` VALUES ('6', 'zhaoliu', '123', null, null);
INSERT INTO `user` VALUES ('7', 'tianqi', '123', null, null);
INSERT INTO `user` VALUES ('8', 'v8', '123', null, null);
INSERT INTO `user` VALUES ('10', 'fengjiu', '123', 'fengjiu520@163.com', '冯九');
