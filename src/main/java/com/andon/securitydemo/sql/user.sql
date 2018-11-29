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

 Date: 30/11/2018 00:07:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', NULL, NULL);
INSERT INTO `user` VALUES (2, 'root', 'root', NULL, NULL);
INSERT INTO `user` VALUES (3, 'zhangsan', '123', NULL, NULL);
INSERT INTO `user` VALUES (4, 'lisi', '123', NULL, NULL);
INSERT INTO `user` VALUES (5, 'wangwu', '123', NULL, NULL);
INSERT INTO `user` VALUES (6, 'zhaoliu', '123', NULL, NULL);
INSERT INTO `user` VALUES (7, 'tianqi', '123', NULL, NULL);
INSERT INTO `user` VALUES (8, 'v8', '123', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
