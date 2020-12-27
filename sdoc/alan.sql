/*
 Navicat Premium Data Transfer

 Source Server         : 本地-MySQL
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : alan

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 27/12/2020 20:00:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_action_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_action_log`;
CREATE TABLE `sys_action_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志名称',
  `type` tinyint(0) NULL DEFAULT NULL COMMENT '日志类型',
  `ipaddr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作IP地址',
  `clazz` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产生日志的类',
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产生日志的方法',
  `model` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产生日志的表',
  `record_id` bigint(0) NULL DEFAULT NULL COMMENT '产生日志的数据id',
  `message` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '日志消息',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '记录时间',
  `oper_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产生日志的用户昵称',
  `oper_by` bigint(0) NULL DEFAULT NULL COMMENT '产生日志的用户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK32gm4dja0jetx58r9dc2uljiu`(`oper_by`) USING BTREE,
  CONSTRAINT `FK32gm4dja0jetx58r9dc2uljiu` FOREIGN KEY (`oper_by`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 340 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `pid` bigint(0) NULL DEFAULT NULL COMMENT '父级ID',
  `pids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所有父级编号',
  `level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门层级',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建用户',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新用户',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态（1:正常,2:冻结,3:删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKifwd1h4ciusl3nnxrpfpv316u`(`create_by`) USING BTREE,
  INDEX `FK83g45s1cjqqfpifhulqhv807m`(`update_by`) USING BTREE,
  CONSTRAINT `FK83g45s1cjqqfpifhulqhv807m` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKifwd1h4ciusl3nnxrpfpv316u` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '遵义师范学院', 0, '[0]', '学校', 1, NULL, '2018-12-02 17:41:23', '2020-12-27 14:19:59', 1, 1, 1);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典键名',
  `type` tinyint(0) NULL DEFAULT NULL COMMENT '字典类型',
  `value` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '字典键值',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建用户',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新用户',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态（1:正常,2:冻结,3:删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKag4shuprf2tjot9i1mhh37kk6`(`create_by`) USING BTREE,
  INDEX `FKoyng5jlifhsme0gc1lwiub0lr`(`update_by`) USING BTREE,
  CONSTRAINT `FKag4shuprf2tjot9i1mhh37kk6` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKoyng5jlifhsme0gc1lwiub0lr` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, '数据状态', 'DATA_STATUS', 2, '1:正常,2:冻结,3:删除', '', '2018-10-05 16:03:11', '2018-10-05 16:11:41', 1, 1, 1);
INSERT INTO `sys_dict` VALUES (2, '字典类型', 'DICT_TYPE', 2, '2:键值对', '', '2018-10-05 20:08:55', '2019-01-17 23:39:23', 1, 1, 1);
INSERT INTO `sys_dict` VALUES (3, '用户性别', 'USER_SEX', 2, '1:男,2:女', '', '2018-10-05 20:12:32', '2018-10-05 20:12:32', 1, 1, 1);
INSERT INTO `sys_dict` VALUES (4, '菜单类型', 'MENU_TYPE', 2, '1:目录,2:菜单,3:按钮', '', '2018-10-05 20:24:57', '2019-11-06 20:08:46', 1, 1, 1);
INSERT INTO `sys_dict` VALUES (5, '搜索栏状态', 'SEARCH_STATUS', 2, '1:正常,2:冻结', '', '2018-10-05 20:25:45', '2019-02-26 00:34:34', 1, 1, 1);
INSERT INTO `sys_dict` VALUES (6, '日志类型', 'LOG_TYPE', 2, '1:业务,2:登录,3:系统', '', '2018-10-05 20:28:47', '2019-02-26 00:31:43', 1, 1, 1);
INSERT INTO `sys_dict` VALUES (8, '部门层级', 'DEPT_LEVEL', 2, '学校:学校,院系:院系,专业:专业,班级:班级', '', '2020-12-27 14:30:17', '2020-12-27 15:29:44', 1, 1, 1);

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `mime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MIME文件类型',
  `size` bigint(0) NULL DEFAULT NULL COMMENT '文件大小',
  `md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MD5值',
  `sha1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'SHA1值',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '上传者',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKkkles8yp0a156p4247cc22ovn`(`create_by`) USING BTREE,
  CONSTRAINT `FKkkles8yp0a156p4247cc22ovn` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `pid` bigint(0) NULL DEFAULT NULL COMMENT '父级编号',
  `pids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所有父级编号',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'URL地址',
  `perms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `type` tinyint(0) NULL DEFAULT NULL COMMENT '类型（1:一级菜单,2:子级菜单,3:不是菜单）',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建用户',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新用户',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态（1:正常,2:冻结,3:删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKoxg2hi96yr9pf2m0stjomr3we`(`create_by`) USING BTREE,
  INDEX `FKsiko0qcr8ddamvrxf1tk4opgc`(`update_by`) USING BTREE,
  CONSTRAINT `FKoxg2hi96yr9pf2m0stjomr3we` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKsiko0qcr8ddamvrxf1tk4opgc` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 147 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '菜单管理', 2, '[0],[2]', '/system/menu/index', 'system:menu:index', '', 2, 3, '', '2018-09-29 00:02:10', '2019-02-24 16:10:40', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (2, '系统管理', 0, '[0]', '#', '#', 'fa fa-cog', 1, 2, '', '2018-09-29 00:05:50', '2019-02-27 21:34:56', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (3, '添加', 1, '[0],[2],[1]', '/system/menu/add', 'system:menu:add', '', 3, 1, '', '2018-09-29 00:06:57', '2019-02-24 16:12:59', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (4, '角色管理', 2, '[0],[2]', '/system/role/index', 'system:role:index', '', 2, 2, '', '2018-09-29 00:08:14', '2019-02-24 16:10:34', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (5, '添加', 4, '[0],[2],[4]', '/system/role/add', 'system:role:add', '', 3, 1, '', '2018-09-29 00:09:04', '2019-02-24 16:12:04', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (6, '主页', 0, '[0]', '/index', 'index', 'layui-icon layui-icon-home', 1, 1, '', '2018-09-29 00:09:56', '2019-02-27 21:34:56', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (7, '用户管理', 2, '[0],[2]', '/system/user/index', 'system:user:index', '', 2, 1, '', '2018-09-29 00:43:50', '2019-04-05 17:43:25', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (8, '编辑', 1, '[0],[2],[1]', '/system/menu/edit', 'system:menu:edit', '', 3, 2, '', '2018-09-29 00:57:33', '2019-02-24 16:13:05', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (9, '详细', 1, '[0],[2],[1]', '/system/menu/detail', 'system:menu:detail', '', 3, 3, '', '2018-09-29 01:03:00', '2019-02-24 16:13:12', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (10, '修改状态', 1, '[0],[2],[1]', '/system/menu/status', 'system:menu:status', '', 3, 4, '', '2018-09-29 01:03:43', '2019-02-24 16:13:21', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (11, '编辑', 4, '[0],[2],[4]', '/system/role/edit', 'system:role:edit', '', 3, 2, '', '2018-09-29 01:06:13', '2019-02-24 16:12:10', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (12, '授权', 4, '[0],[2],[4]', '/system/role/auth', 'system:role:auth', '', 3, 3, '', '2018-09-29 01:06:57', '2019-02-24 16:12:17', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (13, '详细', 4, '[0],[2],[4]', '/system/role/detail', 'system:role:detail', '', 3, 4, '', '2018-09-29 01:08:00', '2019-02-24 16:12:24', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (14, '修改状态', 4, '[0],[2],[4]', '/system/role/status', 'system:role:status', '', 3, 5, '', '2018-09-29 01:08:22', '2019-02-24 16:12:43', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (15, '编辑', 7, '[0],[2],[7]', '/system/user/edit', 'system:user:edit', '', 3, 2, '', '2018-09-29 21:17:17', '2019-02-24 16:11:03', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (16, '添加', 7, '[0],[2],[7]', '/system/user/add', 'system:user:add', '', 3, 1, '', '2018-09-29 21:17:58', '2019-02-24 16:10:28', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (17, '修改密码', 7, '[0],[2],[7]', '/system/user/pwd', 'system:user:pwd', '', 3, 3, '', '2018-09-29 21:19:40', '2019-02-24 16:11:11', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (18, '权限分配', 7, '[0],[2],[7]', '/system/user/role', 'system:user:role', '', 3, 4, '', '2018-09-29 21:20:35', '2019-02-24 16:11:18', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (19, '详细', 7, '[0],[2],[7]', '/system/user/detail', 'system:user:detail', '', 3, 5, '', '2018-09-29 21:21:00', '2019-02-24 16:11:26', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (20, '修改状态', 7, '[0],[2],[7]', '/system/user/status', 'system:user:status', '', 3, 6, '', '2018-09-29 21:21:18', '2019-02-24 16:11:35', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (21, '字典管理', 2, '[0],[2]', '/system/dict/index', 'system:dict:index', '', 2, 5, '', '2018-10-05 00:55:52', '2019-02-24 16:14:24', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (22, '字典添加', 21, '[0],[2],[21]', '/system/dict/add', 'system:dict:add', '', 3, 1, '', '2018-10-05 00:56:26', '2019-02-24 16:14:10', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (23, '字典编辑', 21, '[0],[2],[21]', '/system/dict/edit', 'system:dict:edit', '', 3, 2, '', '2018-10-05 00:57:08', '2019-02-24 16:14:34', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (24, '字典详细', 21, '[0],[2],[21]', '/system/dict/detail', 'system:dict:detail', '', 3, 3, '', '2018-10-05 00:57:42', '2019-02-24 16:14:41', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (25, '修改状态', 21, '[0],[2],[21]', '/system/dict/status', 'system:dict:status', '', 3, 4, '', '2018-10-05 00:58:27', '2019-02-24 16:14:49', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (26, '行为日志', 2, '[0],[2]', '/system/actionLog/index', 'system:actionLog:index', '', 2, 6, '', '2018-10-14 16:52:01', '2019-02-27 21:34:15', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (27, '日志详细', 26, '[0],[2],[26]', '/system/actionLog/detail', 'system:actionLog:detail', '', 3, 1, '', '2018-10-14 21:07:11', '2019-02-27 21:34:15', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (28, '日志删除', 26, '[0],[2],[26]', '/system/actionLog/delete', 'system:actionLog:delete', '', 3, 2, '', '2018-10-14 21:08:17', '2019-02-27 21:34:15', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (30, '开发中心', 0, '[0]', '#', '#', 'fa fa-gavel', 1, 3, '', '2018-10-19 16:38:23', '2019-02-27 21:34:56', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (31, '代码生成', 30, '[0],[30]', '/dev/code', '#', '', 2, 1, '', '2018-10-19 16:39:04', '2019-03-13 17:43:58', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (125, '表单构建', 30, '[0],[30]', '/dev/build', '#', '', 2, 2, '', '2018-11-25 16:12:23', '2019-02-24 16:16:40', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (136, '部门管理', 2, '[0],[2]', '/system/dept/index', 'system:dept:index', '', 2, 4, '', '2018-12-02 16:33:23', '2019-02-24 16:10:50', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (137, '添加', 136, '[0],[2],[136]', '/system/dept/add', 'system:dept:add', '', 3, 1, '', '2018-12-02 16:33:23', '2019-02-24 16:13:34', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (138, '编辑', 136, '[0],[2],[136]', '/system/dept/edit', 'system:dept:edit', '', 3, 2, '', '2018-12-02 16:33:23', '2019-02-24 16:13:42', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (139, '详细', 136, '[0],[2],[136]', '/system/dept/detail', 'system:dept:detail', '', 3, 3, '', '2018-12-02 16:33:23', '2019-02-24 16:13:49', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (140, '改变状态', 136, '[0],[2],[136]', '/system/dept/status', 'system:dept:status', '', 3, 4, '', '2018-12-02 16:33:23', '2019-02-24 16:13:57', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (146, '数据接口', 30, '[0],[30]', '/dev/swagger', '#', '', 2, 3, '', '2018-12-09 21:11:11', '2019-02-24 23:38:18', 1, 1, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称（中文名）',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标识名称',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(0) NULL DEFAULT NULL COMMENT '创建用户',
  `update_by` bigint(0) NULL DEFAULT NULL COMMENT '更新用户',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态（1:正常,2:冻结,3:删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKdkwvv0rm6j3d5l6hwsy2dplol`(`create_by`) USING BTREE,
  INDEX `FKrouqqi3f2bgc5o83wdstlh6q4`(`update_by`) USING BTREE,
  CONSTRAINT `FKdkwvv0rm6j3d5l6hwsy2dplol` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKrouqqi3f2bgc5o83wdstlh6q4` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'administrator', '', '2018-09-29 00:12:40', '2020-12-27 12:37:41', 1, 1, 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(0) NOT NULL,
  `menu_id` bigint(0) NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE,
  INDEX `FKf3mud4qoc7ayew8nml4plkevo`(`menu_id`) USING BTREE,
  CONSTRAINT `FKf3mud4qoc7ayew8nml4plkevo` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKkeitxsgxwayackgqllio4ohn5` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (1, 8);
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 11);
INSERT INTO `sys_role_menu` VALUES (1, 12);
INSERT INTO `sys_role_menu` VALUES (1, 13);
INSERT INTO `sys_role_menu` VALUES (1, 14);
INSERT INTO `sys_role_menu` VALUES (1, 15);
INSERT INTO `sys_role_menu` VALUES (1, 16);
INSERT INTO `sys_role_menu` VALUES (1, 17);
INSERT INTO `sys_role_menu` VALUES (1, 18);
INSERT INTO `sys_role_menu` VALUES (1, 19);
INSERT INTO `sys_role_menu` VALUES (1, 20);
INSERT INTO `sys_role_menu` VALUES (1, 21);
INSERT INTO `sys_role_menu` VALUES (1, 22);
INSERT INTO `sys_role_menu` VALUES (1, 23);
INSERT INTO `sys_role_menu` VALUES (1, 24);
INSERT INTO `sys_role_menu` VALUES (1, 25);
INSERT INTO `sys_role_menu` VALUES (1, 26);
INSERT INTO `sys_role_menu` VALUES (1, 27);
INSERT INTO `sys_role_menu` VALUES (1, 28);
INSERT INTO `sys_role_menu` VALUES (1, 30);
INSERT INTO `sys_role_menu` VALUES (1, 31);
INSERT INTO `sys_role_menu` VALUES (1, 125);
INSERT INTO `sys_role_menu` VALUES (1, 136);
INSERT INTO `sys_role_menu` VALUES (1, 137);
INSERT INTO `sys_role_menu` VALUES (1, 138);
INSERT INTO `sys_role_menu` VALUES (1, 139);
INSERT INTO `sys_role_menu` VALUES (1, 140);
INSERT INTO `sys_role_menu` VALUES (1, 146);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `password` char(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '秘钥',
  `dept_id` bigint(0) NULL DEFAULT NULL COMMENT '部门ID',
  `picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `sex` tinyint(0) NULL DEFAULT NULL COMMENT '性别（1:男,2:女）',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态（1:正常,2:冻结,3:删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKb3pkx0wbo6o8i8lj0gxr37v1n`(`dept_id`) USING BTREE,
  CONSTRAINT `FKb3pkx0wbo6o8i8lj0gxr37v1n` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'administrator', '超级管理员', 'e4d86c7079368ed52ca206b661a1ffdeecc14c806bcff104102303beb9d2a9f1', 'vLPCp5', 1, NULL, 1, 'cxxwl96@sina.com', '', '', '2018-08-09 23:00:03', '2020-12-27 18:54:41', 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(0) NOT NULL,
  `role_id` bigint(0) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `FKhh52n8vd4ny9ff4x9fb8v65qx`(`role_id`) USING BTREE,
  CONSTRAINT `FKb40xxfch70f5qnyfw8yme1n1s` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKhh52n8vd4ny9ff4x9fb8v65qx` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;
