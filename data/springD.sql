/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50534
Source Host           : localhost:3306
Source Database       : tanframework

Target Server Type    : MYSQL
Target Server Version : 50534
File Encoding         : 65001

Date: 2015-08-29 23:04:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_menu
-- ----------------------------
DROP TABLE IF EXISTS `tbl_menu`;
CREATE TABLE `tbl_menu` (
  `id` smallint(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT '菜单名称',
  `parent_id` int(10) DEFAULT NULL COMMENT '父ID',
  `url` varchar(255) DEFAULT NULL COMMENT '菜单地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注描述',
  `isshow` tinyint(1) DEFAULT NULL COMMENT '是否显示',
  `listorder` smallint(5) DEFAULT NULL COMMENT '排序',
  `parent_ids` varchar(100) DEFAULT NULL COMMENT '所有父ID',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tbl_menu
-- ----------------------------
INSERT INTO `tbl_menu` VALUES ('2', '顶级菜单', '0', '#', null, '1', '1', '0,', '2015-01-13 15:19:16');
INSERT INTO `tbl_menu` VALUES ('13', '系统管理员界面', '2', 'system:admin:*', null, '1', '1', '0,2,', '2015-01-13 17:44:09');
INSERT INTO `tbl_menu` VALUES ('21', '权限管理', '13', 'system:permission:view', null, '1', '1', '0,2,13,', '2015-01-13 16:31:36');
INSERT INTO `tbl_menu` VALUES ('22', '用户管理', '13', 'system:userManager:view', null, '1', '1', '0,2,13,', '2015-01-13 16:42:07');
INSERT INTO `tbl_menu` VALUES ('48', '菜单管理', '21', 'system:menu:*', null, '1', '1', '0,2,13,21,', '2015-01-13 16:30:50');
INSERT INTO `tbl_menu` VALUES ('49', '角色管理', '21', 'system:role:*', null, '1', '1', '0,2,13,21,', '2015-01-13 16:31:07');
INSERT INTO `tbl_menu` VALUES ('51', '用户列表', '22', 'system:user:*', null, '1', '1', '0,2,13,22,', '2015-01-13 16:46:20');
INSERT INTO `tbl_menu` VALUES ('52', '宝宝家长列表', '22', 'system:parentBaby:*', null, '1', '1', '0,2,13,22,', '2015-01-13 16:46:45');
INSERT INTO `tbl_menu` VALUES ('79', '用户列表(查看)', '22', 'system:user:showList', null, '1', '1', '0,2,13,22,', '2015-04-15 11:30:27');
INSERT INTO `tbl_menu` VALUES ('81', '修改密码', '2', 'kindergarten:garten:changePwd', null, '1', '1', '0,2,', '2015-04-15 14:06:44');

-- ----------------------------
-- Table structure for tbl_org
-- ----------------------------
DROP TABLE IF EXISTS `tbl_org`;
CREATE TABLE `tbl_org` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '组织名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父级ID',
  `parent_ids` varchar(100) DEFAULT NULL COMMENT '所有父级编号',
  `type` tinyint(1) DEFAULT NULL COMMENT '组织类型',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `grade` char(1) DEFAULT NULL COMMENT '机构等级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_org
-- ----------------------------
INSERT INTO `tbl_org` VALUES ('1', '顶级组织', '0', '0,', '1', '1', '顶级组织', '2014-10-23 19:45:45', null);
INSERT INTO `tbl_org` VALUES ('2', '二级组织', '1', '0,1,', '1', '1', '二级组织', '2014-10-23 19:45:45', null);
INSERT INTO `tbl_org` VALUES ('3', '三级组织', '2', '0,1,2,', '1', '1', '三级组织', '2014-10-23 19:51:48', null);
INSERT INTO `tbl_org` VALUES ('4', '二级组织2', '1', '0,1,', '1', '1', '二级组织2', '2014-10-24 10:17:55', null);
INSERT INTO `tbl_org` VALUES ('5', '三级组织2', '2', '0,1,2,', '1', '1', '三级组织2', '2014-10-24 10:18:31', null);
INSERT INTO `tbl_org` VALUES ('6', '三级组织2', '1', '0,1,', null, null, '三级组织2', '2014-10-26 09:41:05', null);
INSERT INTO `tbl_org` VALUES ('7', '华南大区', '4', '0,1,4,', '1', '1', '华南大区', '2014-10-27 16:29:56', null);

-- ----------------------------
-- Table structure for tbl_role
-- ----------------------------
DROP TABLE IF EXISTS `tbl_role`;
CREATE TABLE `tbl_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL COMMENT '角色名称',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `code` varchar(50) NOT NULL COMMENT '角色编码，唯一',
  `menu_ids` text COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tbl_role
-- ----------------------------
INSERT INTO `tbl_role` VALUES ('1', '超级管理员', '1', '超级管理员，拥有所有权限', 'superadmin', '2,12,13,15,16,17,18,19,20,21,22,23,29,30,31,32,33,37,38,39,40,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63');
INSERT INTO `tbl_role` VALUES ('2', 'admin', '1', 'asdfasdf', 'a', '2,3,5,24');
INSERT INTO `tbl_role` VALUES ('3', '客服人员', '1', '客服账号,只能查看统计报表', 'kf', '2,13,20,50,74,81,82');
INSERT INTO `tbl_role` VALUES ('4', '幼儿园管理员', '1', '幼儿园顶级管理员', 'k', '2,12,15,16,17,18,19,29,30,31,32,33,37,38,39,40,42,43,44,45,46,47,65,71,83,84');
INSERT INTO `tbl_role` VALUES ('5', '产品管理员', '1', '产品管理员专用', 'product', '2,13,20,50,74');
INSERT INTO `tbl_role` VALUES ('6', 'app管理员', '1', 'app管理员', 'app', '2,13,22,52,61,62,63,76');
INSERT INTO `tbl_role` VALUES ('7', '工程商', '1', '工程商角色', 'contractor', '2,80,81');

-- ----------------------------
-- Table structure for tbl_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tbl_role_menu`;
CREATE TABLE `tbl_role_menu` (
  `role_id` smallint(5) NOT NULL COMMENT '角色ID',
  `menu_id` smallint(5) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色权限对应表';

-- ----------------------------
-- Records of tbl_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'uid',
  `username` char(15) NOT NULL COMMENT '用户名',
  `password` char(32) DEFAULT NULL COMMENT '密码',
  `email` char(40) NOT NULL COMMENT '邮箱',
  `phone` varchar(16) DEFAULT NULL,
  `salt` char(10) DEFAULT NULL COMMENT '加密salt',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别 1：男、2：女',
  `regip` char(15) NOT NULL COMMENT '注册ip',
  `regdate` date DEFAULT NULL COMMENT '注册时间',
  `lastloginip` char(15) DEFAULT NULL COMMENT '最后登录IP',
  `lastlogindate` date DEFAULT NULL COMMENT '最后登录时间',
  `role_id` int(10) DEFAULT NULL COMMENT '角色id',
  `kindergarten_id` int(10) DEFAULT NULL COMMENT '关联幼儿园id',
  `usertype` char(2) DEFAULT NULL COMMENT '1管理员 2幼儿园管理员 3待确定',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=508 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
INSERT INTO `tbl_user` VALUES ('1', 'tanlsh', '81ca1ec204e581381698c5c5e2511261', '123456@mima4.com', '13553167926', '53iTek8y', '1', '', '1', '1', null, '', null, '1', '1', '1');

-- ----------------------------
-- Table structure for tbl_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_role`;
CREATE TABLE `tbl_user_role` (
  `uid` int(11) NOT NULL COMMENT '用户id',
  `role_id` smallint(5) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`uid`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tbl_user_role
-- ----------------------------
