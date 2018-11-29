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

 Date: 30/11/2018 00:08:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role`  (
  `menu_id` int(11) NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  INDEX `menu_id_PK`(`menu_id`) USING BTREE,
  INDEX `role_id_PK2`(`role_id`) USING BTREE,
  CONSTRAINT `menu_id_PK` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_id_PK2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu_role
-- ----------------------------
INSERT INTO `menu_role` VALUES (0, 2);
INSERT INTO `menu_role` VALUES (3, 1);
INSERT INTO `menu_role` VALUES (4, 1);
INSERT INTO `menu_role` VALUES (5, 1);
INSERT INTO `menu_role` VALUES (3, 3);
INSERT INTO `menu_role` VALUES (4, 4);
INSERT INTO `menu_role` VALUES (5, 5);
INSERT INTO `menu_role` VALUES (6, 6);
INSERT INTO `menu_role` VALUES (13, 6);
INSERT INTO `menu_role` VALUES (6, 7);
INSERT INTO `menu_role` VALUES (13, 8);
INSERT INTO `menu_role` VALUES (6, 9);
INSERT INTO `menu_role` VALUES (13, 9);

SET FOREIGN_KEY_CHECKS = 1;
