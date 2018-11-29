/*
 Navicat Premium Data Transfer

 Source Server         : bida
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : rm-2ze1363b0g4xh8fk53o.mysql.rds.aliyuncs.com:3306
 Source Schema         : security

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 30/11/2018 00:07:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_name_cn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_ADMIN', '管理员', NULL);
INSERT INTO `role` VALUES (2, 'ROLE_SUPER', '超级管理员', NULL);
INSERT INTO `role` VALUES (3, 'ROLE_USER', '用户管理员', NULL);
INSERT INTO `role` VALUES (4, 'ROLE_ROLE', '角色管理员', NULL);
INSERT INTO `role` VALUES (5, 'ROLE_MENU', '菜单管理员', NULL);
INSERT INTO `role` VALUES (6, 'ROLE_USER', '普通用户', NULL);
INSERT INTO `role` VALUES (7, 'ROLE_USER1', '普通用户1', NULL);
INSERT INTO `role` VALUES (8, 'ROLE_USER5', '普通用户5', NULL);
INSERT INTO `role` VALUES (9, 'ROLE_USER6', '普通用户6', NULL);

SET FOREIGN_KEY_CHECKS = 1;
