/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.94
Source Server Version : 50537
Source Host           : 192.168.0.94:3306
Source Database       : base

Target Server Type    : MYSQL
Target Server Version : 50537
File Encoding         : 65001

Date: 2014-07-29 10:17:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for fill_list
-- ----------------------------
DROP TABLE IF EXISTS `fill_list`;
CREATE TABLE `fill_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序列',
  `type_id` int(10) DEFAULT NULL COMMENT '属性类别编号，1：品类，2：系列，3：功效,4:产品类型，5：分销类型 6：产品等级、7：状态',
  `brand_id` int(10) DEFAULT '0' COMMENT '平台所属，0：所有可见',
  `name` varchar(25) DEFAULT NULL COMMENT '名称',
  `remarks` varchar(1000) DEFAULT NULL COMMENT '备注',
  `organization_id` int(10) unsigned DEFAULT NULL COMMENT '所属组织编号',
  `updatetime` varchar(100) DEFAULT NULL COMMENT '数据更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of fill_list
-- ----------------------------
INSERT INTO `fill_list` VALUES ('1', '1', '0', '男士护肤', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('2', '1', '0', '女士护肤', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('3', '1', '0', '彩妆', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('4', '1', '0', '洗护', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('5', '1', '0', '染发', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('6', '1', '0', '其他', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('7', '2', '0', 'UV系列', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('8', '2', '0', '雪颜系列', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('9', '2', '0', '青春密码系列', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('10', '2', '0', '复颜系列', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('11', '2', '0', '创世新肌源系列', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('12', '2', '0', '金致臻颜系列', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('13', '2', '0', '劲能系列', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('14', '2', '0', '控油系列', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('15', '2', '0', '抗皱系列', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('16', '2', '0', '多效修复系列', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('17', '2', '0', '精油润养系列', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('18', '2', '0', '丝质蛋白系列', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('19', '2', '0', '卷烫修复系列', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('20', '2', '0', '卓韵霜系列', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('21', '2', '0', '可丝莹系列', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('22', '2', '0', '眼妆', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('23', '2', '0', '唇妆', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('24', '2', '0', '脸妆', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('25', '2', '0', '甲妆', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('26', '3', '0', '洁面', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('27', '3', '0', '水', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('28', '3', '0', '面霜', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('29', '3', '0', '其他面部护理', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('30', '3', '0', '洗发', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('31', '3', '0', '护发', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('32', '3', '0', '润发', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('33', '3', '0', '其他头发护理', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('34', '3', '0', '眼部', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('35', '3', '0', '唇部', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('36', '3', '0', '脸部', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('37', '3', '0', '指甲', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('38', '4', '0', '正品', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('39', '4', '0', '物理套装', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('40', '4', '0', '虚拟套装', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('41', '4', '0', '小样', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('42', '4', '0', '赠品', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('43', '5', '0', '建议分销', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('44', '5', '0', '可以分销', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('45', '5', '0', '必须分销', null, '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('46', '5', '0', '其它', '', '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('47', '6', '0', 'A+', '', '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('48', '6', '0', 'A', '', '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('49', '6', '0', 'B', '', '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('50', '6', '0', 'C', '', '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('51', '7', '0', '卖（旧包装）', '', '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('52', '7', '0', '卖（新包装）', '', '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('53', '7', '0', '停产', '', '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('54', '7', '0', '新品上市', '', '7', '2014-07-29 10:05:10');
INSERT INTO `fill_list` VALUES ('55', '7', '0', '赠品', '', '7', '2014-07-29 10:05:10');
