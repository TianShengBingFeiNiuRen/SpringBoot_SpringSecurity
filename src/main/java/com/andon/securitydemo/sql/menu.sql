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

 Date: 30/11/2018 00:07:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_id` int(11) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (0, '/**', '根', NULL, NULL);
INSERT INTO `menu` VALUES (2, '/menu1/**', '菜单一', 0, NULL);
INSERT INTO `menu` VALUES (3, '/security/user/**', '用户管理', 0, NULL);
INSERT INTO `menu` VALUES (4, '/security/role/**', '角色管理', 0, NULL);
INSERT INTO `menu` VALUES (5, '/security/menu/**', '菜单管理', 0, NULL);
INSERT INTO `menu` VALUES (6, '/menu5/**', '菜单五', 0, NULL);
INSERT INTO `menu` VALUES (7, '/menu1/**', '菜单一一', 2, NULL);
INSERT INTO `menu` VALUES (8, '/menu1/**', '菜单一二', 2, NULL);
INSERT INTO `menu` VALUES (9, '/menu1/**', '菜单一三', 2, NULL);
INSERT INTO `menu` VALUES (10, '/menu5/**', '菜单五一', 6, NULL);
INSERT INTO `menu` VALUES (11, '/menu5/**', '菜单五二', 6, NULL);
INSERT INTO `menu` VALUES (12, '/menu5/**', '菜单五三', 6, NULL);
INSERT INTO `menu` VALUES (13, '/menu6/**', '菜单六', 0, NULL);

SET FOREIGN_KEY_CHECKS = 1;
