/*
Navicat MySQL Data Transfer

Source Server         : bida_count
Source Server Version : 50718
Source Host           : rm-2ze1363b0g4xh8fk53o.mysql.rds.aliyuncs.com:3306
Source Database       : security

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-11-30 16:27:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `url` varchar(255) DEFAULT NULL COMMENT '请求路径',
  `menu_name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父菜单id',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `FKgeupubdqncc1lpgf2cn4fqwbc` (`parent_id`),
  CONSTRAINT `FKgeupubdqncc1lpgf2cn4fqwbc` FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('0', '/**', '根', null, '根');
INSERT INTO `menu` VALUES ('2', '/menu1/**', '菜单一', '0', '菜单一模块');
INSERT INTO `menu` VALUES ('3', '/security/user/**', '用户管理', '0', '用户管理模块');
INSERT INTO `menu` VALUES ('4', '/security/role/**', '角色管理', '0', '角色管理模块');
INSERT INTO `menu` VALUES ('5', '/security/menu/**', '菜单管理', '0', '菜单管理模块');
INSERT INTO `menu` VALUES ('6', '/menu5/**', '菜单五五开', '0', '菜单五五开模块');
INSERT INTO `menu` VALUES ('7', '/menu1/**', '菜单一一', '2', '菜单一一模块');
INSERT INTO `menu` VALUES ('8', '/menu1/**', '菜单一二', '2', '菜单一二模块');
INSERT INTO `menu` VALUES ('9', '/menu1/**', '菜单一三', '2', '菜单一三模块');
INSERT INTO `menu` VALUES ('10', '/menu5/**', '菜单五五开一', '6', '菜单五五开一模块');
INSERT INTO `menu` VALUES ('11', '/menu5/**', '菜单五五开二', '6', '菜单五五开二模块');
INSERT INTO `menu` VALUES ('12', '/menu5/**', '菜单五五开三', '6', '菜单五五开三模块');
