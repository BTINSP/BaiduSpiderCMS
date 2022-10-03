/*
 Navicat Premium Data Transfer

 Source Server         : 82.157.64.59_3307
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : 82.157.64.59:3307
 Source Schema         : spider_net

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 03/10/2022 21:11:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for net_comment
-- ----------------------------
DROP TABLE IF EXISTS `net_comment`;
CREATE TABLE `net_comment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `net_comment_id_uindex`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for net_user
-- ----------------------------
DROP TABLE IF EXISTS `net_user`;
CREATE TABLE `net_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `table_name_id_uindex`(`id` ASC) USING BTREE,
  UNIQUE INDEX `table_name_username_uindex`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for spider_article
-- ----------------------------
DROP TABLE IF EXISTS `spider_article`;
CREATE TABLE `spider_article`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `source` int NULL DEFAULT NULL COMMENT '百度: 1',
  `date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `spider_article_id_uindex`(`id` ASC) USING BTREE,
  FULLTEXT INDEX `spider_article_serach_uindex`(`title`, `content`) WITH PARSER `ngram`
) ENGINE = InnoDB AUTO_INCREMENT = 18839 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for spider_keyword
-- ----------------------------
DROP TABLE IF EXISTS `spider_keyword`;
CREATE TABLE `spider_keyword`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `keyword` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` int NULL DEFAULT NULL,
  `date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `spider_keyword_id_uindex`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4227 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
