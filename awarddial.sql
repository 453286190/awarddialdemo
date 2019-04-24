/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50559
 Source Host           : localhost:3306
 Source Schema         : awarddial

 Target Server Type    : MySQL
 Target Server Version : 50559
 File Encoding         : 65001

 Date: 23/04/2019 18:37:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for award
-- ----------------------------
DROP TABLE IF EXISTS `award`;
CREATE TABLE `award`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `award_level` int(20) NOT NULL COMMENT '奖品等级',
  `award_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '奖品秘钥',
  `is_send` bit(1) NOT NULL COMMENT '是否已发出',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for award_base
-- ----------------------------
DROP TABLE IF EXISTS `award_base`;
CREATE TABLE `award_base`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `award_level` int(20) NOT NULL COMMENT '奖品等级',
  `award_odd` double(10, 5) NOT NULL COMMENT '中奖概率',
  `award_total` int(20) NOT NULL COMMENT '奖品总数',
  `award_send` int(20) NOT NULL COMMENT '发放数量',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '奖品说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2001 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_award
-- ----------------------------
DROP TABLE IF EXISTS `user_award`;
CREATE TABLE `user_award`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `award_id` bigint(20) NOT NULL,
  `award_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '奖品秘钥',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2000 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
