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

 Date: 30/11/2018 00:08:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int(11) NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  INDEX `user_id_PK`(`user_id`) USING BTREE,
  INDEX `role_id_PK`(`role_id`) USING BTREE,
  CONSTRAINT `role_id_PK` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `user_id_PK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (2, 2);
INSERT INTO `user_role` VALUES (3, 3);
INSERT INTO `user_role` VALUES (4, 4);
INSERT INTO `user_role` VALUES (5, 5);
INSERT INTO `user_role` VALUES (7, 7);
INSERT INTO `user_role` VALUES (7, 9);
INSERT INTO `user_role` VALUES (8, 8);
INSERT INTO `user_role` VALUES (6, 6);
INSERT INTO `user_role` VALUES (6, 7);
INSERT INTO `user_role` VALUES (6, 8);
INSERT INTO `user_role` VALUES (6, 9);

SET FOREIGN_KEY_CHECKS = 1;
