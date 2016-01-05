/*
Navicat MariaDB Data Transfer

Source Server         : localMDB
Source Server Version : 100108
Source Host           : localhost:3306
Source Database       : springd

Target Server Type    : MariaDB
Target Server Version : 100108
File Encoding         : 65001

Date: 2016-01-06 00:07:32
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
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tbl_menu
-- ----------------------------
INSERT INTO `tbl_menu` VALUES ('2', '顶级菜单', '0', '#', null, '1', '1', '0,', '2016-01-05 22:50:00');
INSERT INTO `tbl_menu` VALUES ('13', '系统管理员界面', '2', 'system:admin:*', null, '1', '1', '0,2,', '2015-01-13 17:44:09');
INSERT INTO `tbl_menu` VALUES ('21', '权限管理', '13', '#', null, '1', '1', '0,2,13,', '2016-01-05 22:50:13');
INSERT INTO `tbl_menu` VALUES ('22', '账号管理', '21', 'system:userManager:*', null, '1', '1', '0,', '2016-01-05 22:31:37');
INSERT INTO `tbl_menu` VALUES ('48', '菜单管理', '21', 'system:menu:*', null, '1', '1', '0,2,13,21,', '2016-01-05 22:31:47');
INSERT INTO `tbl_menu` VALUES ('49', '角色管理', '21', 'system:role:*', null, '1', '1', '0,2,13,21,', '2015-01-13 16:31:07');
INSERT INTO `tbl_menu` VALUES ('81', '修改密码', '2', 'system:admin:changePwd', null, '1', '1', '0,2,', '2016-01-05 22:32:36');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tbl_role
-- ----------------------------
INSERT INTO `tbl_role` VALUES ('1', '超级管理员', '1', '超级管理员，拥有所有权限', 'superadmin', '2,12,13,15,16,17,18,19,20,21,22,23,29,30,31,32,33,37,38,39,40,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63');
INSERT INTO `tbl_role` VALUES ('2', 'admin', '1', 'asdfasdf', 'a', '2,13,21,22,48,49,81');
INSERT INTO `tbl_role` VALUES ('4', '幼儿园管理员', '1', '幼儿园顶级管理员', 'k', '2,13,21,22,48,49,81');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
INSERT INTO `tbl_user` VALUES ('1', 'tanlsh', '7f733e92fadfd1df8ae68c9d35df5246', '123456@mima4.com', '13553167926', 'alTB3OCV', '1', '', '1', '1', null, '', null, '1', '1', '1');

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
