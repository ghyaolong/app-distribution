/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50628
Source Host           : localhost:3306
Source Database       : app_distribution

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2019-11-21 17:55:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for da_alipay_log
-- ----------------------------
DROP TABLE IF EXISTS `da_alipay_log`;
CREATE TABLE `da_alipay_log` (
  `id` varchar(32) NOT NULL,
  `notify_time` datetime DEFAULT NULL COMMENT '通知时间',
  `notify_type` varchar(64) DEFAULT NULL COMMENT '通知类型',
  `notify_id` varchar(128) DEFAULT NULL COMMENT '通知校验ID',
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '商户网站唯一订单号',
  `subject` varchar(225) DEFAULT NULL COMMENT '交易标题',
  `trade_no` varchar(64) DEFAULT NULL COMMENT '支付宝交易号',
  `trade_status` char(2) DEFAULT NULL COMMENT '交易状态',
  `seller_id` varchar(16) DEFAULT NULL COMMENT '卖家支付宝用户号',
  `seller_email` varchar(25) DEFAULT NULL COMMENT '卖家支付宝账号',
  `buyer_id` varchar(16) DEFAULT NULL COMMENT '买家支付宝用户号',
  `buyer_email` varchar(25) DEFAULT NULL COMMENT '买家支付宝账号',
  `total_fee` decimal(20,4) DEFAULT NULL COMMENT '交易金额',
  `gmt_create` datetime DEFAULT NULL COMMENT '交易创建时间',
  `gmt_payment` datetime DEFAULT NULL COMMENT '交易付款时间',
  `pay_status` char(2) DEFAULT NULL COMMENT '支付状态。0：充值中；1：交易成功；2：交易失败',
  `order_money` decimal(20,4) DEFAULT NULL COMMENT '订单金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付宝支付日志';

-- ----------------------------
-- Records of da_alipay_log
-- ----------------------------

-- ----------------------------
-- Table structure for da_app
-- ----------------------------
DROP TABLE IF EXISTS `da_app`;
CREATE TABLE `da_app` (
  `id` varchar(32) NOT NULL,
  `bundle_id` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `platform` varchar(255) DEFAULT NULL,
  `short_code` varchar(255) DEFAULT NULL,
  `current_id` varchar(32) DEFAULT NULL,
  `member_id` varchar(32) DEFAULT NULL COMMENT '会员id',
  `download_count` int(11) DEFAULT '0' COMMENT '下载次数',
  `status` char(2) DEFAULT NULL COMMENT '0:正常   1：下架(禁止下载)',
  `remark` varchar(200) DEFAULT NULL COMMENT '一般情况下，当status是1时，remark作为解说字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of da_app
-- ----------------------------
INSERT INTO `da_app` VALUES ('1', '123', '2019-11-21 16:32:38', '123', '123', 'android', '12', '1', '1', '111', '0', '1');

-- ----------------------------
-- Table structure for da_file
-- ----------------------------
DROP TABLE IF EXISTS `da_file`;
CREATE TABLE `da_file` (
  `id` varchar(32) NOT NULL,
  `package_id` varchar(32) DEFAULT NULL COMMENT '包名',
  `path` varchar(250) DEFAULT NULL COMMENT '包名所在路径',
  `file_name` varchar(50) DEFAULT NULL COMMENT '文件名称',
  `type` char(2) DEFAULT '0' COMMENT '0:APP   1:jpg小图标   2：npg小图标',
  `oss_path` varchar(250) DEFAULT NULL COMMENT 'oss所在路径',
  `oss_type` char(2) DEFAULT '0' COMMENT '0：阿里OSS平台      1：百度OSS     2：京东OSS',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` char(2) DEFAULT '0',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `member_id` varchar(32) DEFAULT NULL COMMENT '会员id',
  `app_id` varchar(32) DEFAULT NULL COMMENT '所属app',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='APP包管理';

-- ----------------------------
-- Records of da_file
-- ----------------------------

-- ----------------------------
-- Table structure for da_goods
-- ----------------------------
DROP TABLE IF EXISTS `da_goods`;
CREATE TABLE `da_goods` (
  `id` varchar(32) NOT NULL,
  `product_id` varchar(32) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `download_amount` int(11) DEFAULT NULL COMMENT '下载次数',
  `seq` int(11) DEFAULT NULL COMMENT '次序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` char(2) DEFAULT '0' COMMENT '0:正常   1：下架',
  `is_default` char(2) DEFAULT NULL COMMENT '是否是推荐 0:否   1：是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of da_goods
-- ----------------------------

-- ----------------------------
-- Table structure for da_member
-- ----------------------------
DROP TABLE IF EXISTS `da_member`;
CREATE TABLE `da_member` (
  `id` varchar(32) NOT NULL,
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户登录名称/即邮箱登录',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `tel` varchar(13) DEFAULT NULL COMMENT '手机',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `id_number` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `qq` varchar(15) DEFAULT NULL,
  `company` varchar(50) DEFAULT NULL COMMENT '公司名称',
  `position` varchar(50) DEFAULT NULL COMMENT '职位',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `create_time` datetime DEFAULT NULL,
  `status` char(2) DEFAULT NULL COMMENT '0：未审核   1：审核中  2：审核通过   3：审核驳回',
  `is_forbidden` char(2) DEFAULT NULL COMMENT '0:正常   1：已冻结',
  `download_count` int(11) DEFAULT '0' COMMENT '剩余下载次数',
  `front_photo_path` varchar(200) DEFAULT NULL COMMENT '正面照',
  `back_photo_path` varchar(200) DEFAULT NULL COMMENT '背面照',
  `hand_photo_path` varchar(200) DEFAULT NULL COMMENT '手持照',
  `front_photo_oss_path` varchar(200) DEFAULT NULL COMMENT '正面照oss路径',
  `back_photo_oss_path` varchar(200) DEFAULT NULL COMMENT '背面照oss路径',
  `hand_photo_oss_path` varchar(200) DEFAULT NULL COMMENT '手持照oss路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员表';

-- ----------------------------
-- Records of da_member
-- ----------------------------
INSERT INTO `da_member` VALUES ('1', 'zhagnsan', '123123', '1', '1', '610112198908271111', '123', '5656565', '5656', '5656', '5656', '2019-11-21 16:33:46', '0', '0', '12', '12', '12', '12', '12', '12', '12');

-- ----------------------------
-- Table structure for da_package
-- ----------------------------
DROP TABLE IF EXISTS `da_package`;
CREATE TABLE `da_package` (
  `id` varchar(32) NOT NULL,
  `build_version` varchar(255) DEFAULT NULL,
  `bundle_id` varchar(255) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `extra` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `min_version` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `platform` varchar(255) DEFAULT NULL,
  `size` bigint(20) NOT NULL,
  `version` varchar(255) DEFAULT NULL,
  `provision_id` varchar(32) DEFAULT NULL,
  `status` char(2) DEFAULT '0' COMMENT '0:正常   1：已废弃(回退到上一个版本，当前版本废弃，只有一个版本的时候，无回退功能)',
  `change_log` varchar(1000) DEFAULT NULL COMMENT '更新日志',
  `app_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of da_package
-- ----------------------------
INSERT INTO `da_package` VALUES ('1', '1', '1', '2019-11-21 16:33:15', '1', '1', '1', '1', 'android', '11', '1', '1', '0', '123', '1');

-- ----------------------------
-- Table structure for da_provision
-- ----------------------------
DROP TABLE IF EXISTS `da_provision`;
CREATE TABLE `da_provision` (
  `id` varchar(32) NOT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `device_count` int(11) NOT NULL,
  `devices` mediumblob,
  `expiration_date` datetime DEFAULT NULL,
  `is_enterprise` bit(1) NOT NULL,
  `teamid` varchar(255) DEFAULT NULL,
  `team_name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of da_provision
-- ----------------------------

-- ----------------------------
-- Table structure for da_recharge
-- ----------------------------
DROP TABLE IF EXISTS `da_recharge`;
CREATE TABLE `da_recharge` (
  `id` varchar(32) NOT NULL,
  `order_id` varchar(64) DEFAULT NULL COMMENT '订单id/充值记录id',
  `member_id` varchar(32) DEFAULT NULL,
  `recharge_amount` decimal(10,2) DEFAULT NULL COMMENT '充值金额',
  `recharge_time` datetime DEFAULT NULL,
  `pay_type` char(2) DEFAULT NULL COMMENT '付款方式。1:微信；2：支付宝',
  `order_number` varchar(32) DEFAULT NULL COMMENT '订单号',
  `transaction_id` varchar(32) DEFAULT NULL COMMENT '交易流水号',
  `whether_sms` char(2) DEFAULT '0' COMMENT '是否发送短信（0表示不发送，1表示发送）',
  `recharge_status` char(2) DEFAULT NULL COMMENT '充值状态。0：待支付；1：充值中；2：交易成功；3：交易失败；4：已退款；5：交易关闭',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `goods_id` varchar(32) DEFAULT NULL COMMENT '购买的下载次数包',
  `goods_price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值记录';

-- ----------------------------
-- Records of da_recharge
-- ----------------------------

-- ----------------------------
-- Table structure for da_recharge_log
-- ----------------------------
DROP TABLE IF EXISTS `da_recharge_log`;
CREATE TABLE `da_recharge_log` (
  `id` varchar(32) NOT NULL,
  `order_number` varchar(32) DEFAULT NULL COMMENT '订单号',
  `step_number` char(2) DEFAULT NULL COMMENT '充值步骤',
  `pay_content` varchar(200) DEFAULT NULL COMMENT '充值详情描述',
  `create_time` datetime DEFAULT NULL,
  `pay_record` text COMMENT '存储数据',
  `member_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值流水';

-- ----------------------------
-- Records of da_recharge_log
-- ----------------------------

-- ----------------------------
-- Table structure for da_web_hook
-- ----------------------------
DROP TABLE IF EXISTS `da_web_hook`;
CREATE TABLE `da_web_hook` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of da_web_hook
-- ----------------------------

-- ----------------------------
-- Table structure for da_wxpay_log
-- ----------------------------
DROP TABLE IF EXISTS `da_wxpay_log`;
CREATE TABLE `da_wxpay_log` (
  `id` varchar(32) NOT NULL,
  `memeber_id` varchar(32) DEFAULT NULL COMMENT '会员id',
  `out_trade_no` varchar(32) DEFAULT NULL COMMENT '商户订单号',
  `result_code` varchar(100) DEFAULT NULL COMMENT '业务结果',
  `return_code` varchar(100) DEFAULT NULL COMMENT '状态码',
  `total_fee` decimal(10,2) DEFAULT NULL COMMENT '总金额（单位：分）',
  `trade_type` varchar(20) DEFAULT NULL COMMENT '交易类型',
  `transaction_id` varchar(32) DEFAULT NULL COMMENT '微信支付订单号',
  `gmt_create` datetime DEFAULT NULL COMMENT '交易创建时间',
  `gmt_payment` datetime DEFAULT NULL COMMENT '交易付款时间',
  `time_end` datetime DEFAULT NULL COMMENT '支付完成时间',
  `pay_status` char(2) DEFAULT NULL COMMENT '支付状态。0：充值中；1：交易成功；2：交易失败',
  `order_money` decimal(10,2) DEFAULT NULL COMMENT '订单金额',
  `body` varchar(200) DEFAULT NULL COMMENT '交易描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信支付日志';

-- ----------------------------
-- Records of da_wxpay_log
-- ----------------------------

-- ----------------------------
-- Table structure for da_wx_config
-- ----------------------------
DROP TABLE IF EXISTS `da_wx_config`;
CREATE TABLE `da_wx_config` (
  `id` varchar(32) NOT NULL,
  `app_id` varchar(32) DEFAULT NULL COMMENT 'appid',
  `mch_id` varchar(32) DEFAULT NULL COMMENT '商户id',
  `return_url` varchar(300) DEFAULT NULL COMMENT '同步回调地址',
  `notify_url` varchar(300) DEFAULT NULL COMMENT '异步回调地址',
  `create_time` datetime DEFAULT NULL,
  `status` char(2) DEFAULT NULL COMMENT '0:正常   1：禁用',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `nonce_str` varchar(100) DEFAULT NULL COMMENT '随机字符串',
  `sign` varchar(400) DEFAULT NULL COMMENT '签名',
  `trade_type` varchar(15) DEFAULT NULL COMMENT '交易类型',
  `app_secret` varchar(400) DEFAULT NULL COMMENT '应用对应的凭证（保留）',
  `app_key` varchar(400) DEFAULT NULL COMMENT '应用对应的密钥',
  `gate_url` varchar(400) DEFAULT NULL COMMENT '获取预支付id的接口URL',
  `extension` varchar(400) DEFAULT NULL COMMENT '其它参数。json字符串',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of da_wx_config
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('RenrenScheduler', 'TASK_2', 'DEFAULT', '*/5 * * * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('RenrenScheduler', 'TASK_2', 'DEFAULT', null, 'com.cube.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002D636F6D2E637562652E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000016E87E42EB17874000E2A2F3330202A202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000274000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('RenrenScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('RenrenScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('RenrenScheduler', 'DESKTOP-HEGMAP31574239800403', '1574239878084', '15000');

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('RenrenScheduler', 'TASK_2', 'DEFAULT', 'TASK_2', 'DEFAULT', null, '1574237910000', '-1', '5', 'WAITING', 'CRON', '1574237908000', '0', null, '2', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002D636F6D2E637562652E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000016E87E430087874000D2A2F35202A202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000274000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES ('2', 'testTask', 'renren', '*/5 * * * * ?', '0', '参数测试', '2019-11-20 16:18:29');

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=252 DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
INSERT INTO `schedule_job_log` VALUES ('1', '1', 'testTask', 'renren', '0', null, '0', '2019-11-20 15:00:00');
INSERT INTO `schedule_job_log` VALUES ('2', '2', 'testTask', 'renren', '0', null, '4653', '2019-11-20 16:18:44');
INSERT INTO `schedule_job_log` VALUES ('3', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:19:00');
INSERT INTO `schedule_job_log` VALUES ('4', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:19:30');
INSERT INTO `schedule_job_log` VALUES ('5', '2', 'testTask', 'renren', '0', null, '2271', '2019-11-20 16:20:00');
INSERT INTO `schedule_job_log` VALUES ('6', '2', 'testTask', 'renren', '0', null, '6989', '2019-11-20 16:23:05');
INSERT INTO `schedule_job_log` VALUES ('7', '2', 'testTask', 'renren', '0', null, '1154', '2019-11-20 16:23:12');
INSERT INTO `schedule_job_log` VALUES ('8', '2', 'testTask', 'renren', '0', null, '1933', '2019-11-20 16:23:15');
INSERT INTO `schedule_job_log` VALUES ('9', '2', 'testTask', 'renren', '0', null, '1026', '2019-11-20 16:23:20');
INSERT INTO `schedule_job_log` VALUES ('10', '2', 'testTask', 'renren', '0', null, '1689', '2019-11-20 16:23:25');
INSERT INTO `schedule_job_log` VALUES ('11', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:23:30');
INSERT INTO `schedule_job_log` VALUES ('12', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:23:35');
INSERT INTO `schedule_job_log` VALUES ('13', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:23:40');
INSERT INTO `schedule_job_log` VALUES ('14', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:23:45');
INSERT INTO `schedule_job_log` VALUES ('15', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:23:50');
INSERT INTO `schedule_job_log` VALUES ('16', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:23:55');
INSERT INTO `schedule_job_log` VALUES ('17', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:24:00');
INSERT INTO `schedule_job_log` VALUES ('18', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:24:05');
INSERT INTO `schedule_job_log` VALUES ('19', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:24:10');
INSERT INTO `schedule_job_log` VALUES ('20', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:24:15');
INSERT INTO `schedule_job_log` VALUES ('21', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:24:20');
INSERT INTO `schedule_job_log` VALUES ('22', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:24:25');
INSERT INTO `schedule_job_log` VALUES ('23', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:24:30');
INSERT INTO `schedule_job_log` VALUES ('24', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:24:35');
INSERT INTO `schedule_job_log` VALUES ('25', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:24:40');
INSERT INTO `schedule_job_log` VALUES ('26', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:24:45');
INSERT INTO `schedule_job_log` VALUES ('27', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:24:50');
INSERT INTO `schedule_job_log` VALUES ('28', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:24:55');
INSERT INTO `schedule_job_log` VALUES ('29', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:25:00');
INSERT INTO `schedule_job_log` VALUES ('30', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:25:05');
INSERT INTO `schedule_job_log` VALUES ('31', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:25:10');
INSERT INTO `schedule_job_log` VALUES ('32', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:25:15');
INSERT INTO `schedule_job_log` VALUES ('33', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:25:20');
INSERT INTO `schedule_job_log` VALUES ('34', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:25:25');
INSERT INTO `schedule_job_log` VALUES ('35', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:25:30');
INSERT INTO `schedule_job_log` VALUES ('36', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:25:35');
INSERT INTO `schedule_job_log` VALUES ('37', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:25:40');
INSERT INTO `schedule_job_log` VALUES ('38', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:25:45');
INSERT INTO `schedule_job_log` VALUES ('39', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:25:50');
INSERT INTO `schedule_job_log` VALUES ('40', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:25:55');
INSERT INTO `schedule_job_log` VALUES ('41', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:26:00');
INSERT INTO `schedule_job_log` VALUES ('42', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:26:05');
INSERT INTO `schedule_job_log` VALUES ('43', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:26:10');
INSERT INTO `schedule_job_log` VALUES ('44', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:26:15');
INSERT INTO `schedule_job_log` VALUES ('45', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:26:20');
INSERT INTO `schedule_job_log` VALUES ('46', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:26:25');
INSERT INTO `schedule_job_log` VALUES ('47', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:26:30');
INSERT INTO `schedule_job_log` VALUES ('48', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:26:35');
INSERT INTO `schedule_job_log` VALUES ('49', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:26:40');
INSERT INTO `schedule_job_log` VALUES ('50', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:26:45');
INSERT INTO `schedule_job_log` VALUES ('51', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:26:50');
INSERT INTO `schedule_job_log` VALUES ('52', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:26:55');
INSERT INTO `schedule_job_log` VALUES ('53', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:27:00');
INSERT INTO `schedule_job_log` VALUES ('54', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:27:05');
INSERT INTO `schedule_job_log` VALUES ('55', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:27:10');
INSERT INTO `schedule_job_log` VALUES ('56', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:27:15');
INSERT INTO `schedule_job_log` VALUES ('57', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:27:20');
INSERT INTO `schedule_job_log` VALUES ('58', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:27:25');
INSERT INTO `schedule_job_log` VALUES ('59', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:27:30');
INSERT INTO `schedule_job_log` VALUES ('60', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:27:35');
INSERT INTO `schedule_job_log` VALUES ('61', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:27:40');
INSERT INTO `schedule_job_log` VALUES ('62', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:27:45');
INSERT INTO `schedule_job_log` VALUES ('63', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:27:50');
INSERT INTO `schedule_job_log` VALUES ('64', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:27:55');
INSERT INTO `schedule_job_log` VALUES ('65', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:28:00');
INSERT INTO `schedule_job_log` VALUES ('66', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:28:05');
INSERT INTO `schedule_job_log` VALUES ('67', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:28:10');
INSERT INTO `schedule_job_log` VALUES ('68', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:28:15');
INSERT INTO `schedule_job_log` VALUES ('69', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:28:20');
INSERT INTO `schedule_job_log` VALUES ('70', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:28:25');
INSERT INTO `schedule_job_log` VALUES ('71', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:28:30');
INSERT INTO `schedule_job_log` VALUES ('72', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:28:35');
INSERT INTO `schedule_job_log` VALUES ('73', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:28:40');
INSERT INTO `schedule_job_log` VALUES ('74', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:28:45');
INSERT INTO `schedule_job_log` VALUES ('75', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:28:50');
INSERT INTO `schedule_job_log` VALUES ('76', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:28:55');
INSERT INTO `schedule_job_log` VALUES ('77', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:29:00');
INSERT INTO `schedule_job_log` VALUES ('78', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:29:05');
INSERT INTO `schedule_job_log` VALUES ('79', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:29:10');
INSERT INTO `schedule_job_log` VALUES ('80', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:29:15');
INSERT INTO `schedule_job_log` VALUES ('81', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:29:20');
INSERT INTO `schedule_job_log` VALUES ('82', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:29:25');
INSERT INTO `schedule_job_log` VALUES ('83', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:29:30');
INSERT INTO `schedule_job_log` VALUES ('84', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:29:35');
INSERT INTO `schedule_job_log` VALUES ('85', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:29:40');
INSERT INTO `schedule_job_log` VALUES ('86', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:29:45');
INSERT INTO `schedule_job_log` VALUES ('87', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:29:50');
INSERT INTO `schedule_job_log` VALUES ('88', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:29:55');
INSERT INTO `schedule_job_log` VALUES ('89', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:30:00');
INSERT INTO `schedule_job_log` VALUES ('90', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:30:05');
INSERT INTO `schedule_job_log` VALUES ('91', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:30:10');
INSERT INTO `schedule_job_log` VALUES ('92', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:30:15');
INSERT INTO `schedule_job_log` VALUES ('93', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:30:20');
INSERT INTO `schedule_job_log` VALUES ('94', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:30:25');
INSERT INTO `schedule_job_log` VALUES ('95', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:30:30');
INSERT INTO `schedule_job_log` VALUES ('96', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:30:35');
INSERT INTO `schedule_job_log` VALUES ('97', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:30:40');
INSERT INTO `schedule_job_log` VALUES ('98', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:30:45');
INSERT INTO `schedule_job_log` VALUES ('99', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:30:50');
INSERT INTO `schedule_job_log` VALUES ('100', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:30:55');
INSERT INTO `schedule_job_log` VALUES ('101', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:31:00');
INSERT INTO `schedule_job_log` VALUES ('102', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:31:05');
INSERT INTO `schedule_job_log` VALUES ('103', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:31:10');
INSERT INTO `schedule_job_log` VALUES ('104', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:31:15');
INSERT INTO `schedule_job_log` VALUES ('105', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:31:20');
INSERT INTO `schedule_job_log` VALUES ('106', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:31:25');
INSERT INTO `schedule_job_log` VALUES ('107', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:31:30');
INSERT INTO `schedule_job_log` VALUES ('108', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:31:35');
INSERT INTO `schedule_job_log` VALUES ('109', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:31:40');
INSERT INTO `schedule_job_log` VALUES ('110', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:31:45');
INSERT INTO `schedule_job_log` VALUES ('111', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:31:50');
INSERT INTO `schedule_job_log` VALUES ('112', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:31:55');
INSERT INTO `schedule_job_log` VALUES ('113', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:32:00');
INSERT INTO `schedule_job_log` VALUES ('114', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:32:05');
INSERT INTO `schedule_job_log` VALUES ('115', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:32:10');
INSERT INTO `schedule_job_log` VALUES ('116', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:32:15');
INSERT INTO `schedule_job_log` VALUES ('117', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:32:20');
INSERT INTO `schedule_job_log` VALUES ('118', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:32:25');
INSERT INTO `schedule_job_log` VALUES ('119', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:32:30');
INSERT INTO `schedule_job_log` VALUES ('120', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:32:35');
INSERT INTO `schedule_job_log` VALUES ('121', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:32:40');
INSERT INTO `schedule_job_log` VALUES ('122', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:32:45');
INSERT INTO `schedule_job_log` VALUES ('123', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:32:50');
INSERT INTO `schedule_job_log` VALUES ('124', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:32:55');
INSERT INTO `schedule_job_log` VALUES ('125', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:33:00');
INSERT INTO `schedule_job_log` VALUES ('126', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:33:05');
INSERT INTO `schedule_job_log` VALUES ('127', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:33:10');
INSERT INTO `schedule_job_log` VALUES ('128', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:33:15');
INSERT INTO `schedule_job_log` VALUES ('129', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:33:20');
INSERT INTO `schedule_job_log` VALUES ('130', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:33:25');
INSERT INTO `schedule_job_log` VALUES ('131', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:33:30');
INSERT INTO `schedule_job_log` VALUES ('132', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:33:35');
INSERT INTO `schedule_job_log` VALUES ('133', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:33:40');
INSERT INTO `schedule_job_log` VALUES ('134', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:33:45');
INSERT INTO `schedule_job_log` VALUES ('135', '2', 'testTask', 'renren', '0', null, '18419', '2019-11-20 16:36:40');
INSERT INTO `schedule_job_log` VALUES ('136', '2', 'testTask', 'renren', '0', null, '2421', '2019-11-20 16:36:58');
INSERT INTO `schedule_job_log` VALUES ('137', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:37:01');
INSERT INTO `schedule_job_log` VALUES ('138', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:37:01');
INSERT INTO `schedule_job_log` VALUES ('139', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:37:01');
INSERT INTO `schedule_job_log` VALUES ('140', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:37:05');
INSERT INTO `schedule_job_log` VALUES ('141', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:37:10');
INSERT INTO `schedule_job_log` VALUES ('142', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:37:15');
INSERT INTO `schedule_job_log` VALUES ('143', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:37:20');
INSERT INTO `schedule_job_log` VALUES ('144', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:37:25');
INSERT INTO `schedule_job_log` VALUES ('145', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:37:30');
INSERT INTO `schedule_job_log` VALUES ('146', '2', 'testTask', 'renren', '0', null, '8255', '2019-11-20 16:37:35');
INSERT INTO `schedule_job_log` VALUES ('147', '2', 'testTask', 'renren', '0', null, '6314', '2019-11-20 16:37:43');
INSERT INTO `schedule_job_log` VALUES ('148', '2', 'testTask', 'renren', '0', null, '9314', '2019-11-20 16:37:50');
INSERT INTO `schedule_job_log` VALUES ('149', '2', 'testTask', 'renren', '0', null, '2666', '2019-11-20 16:37:59');
INSERT INTO `schedule_job_log` VALUES ('150', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:38:02');
INSERT INTO `schedule_job_log` VALUES ('151', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:38:02');
INSERT INTO `schedule_job_log` VALUES ('152', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:38:05');
INSERT INTO `schedule_job_log` VALUES ('153', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:38:10');
INSERT INTO `schedule_job_log` VALUES ('154', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:38:15');
INSERT INTO `schedule_job_log` VALUES ('155', '2', 'testTask', 'renren', '0', null, '7638', '2019-11-20 16:38:20');
INSERT INTO `schedule_job_log` VALUES ('156', '2', 'testTask', 'renren', '0', null, '2480', '2019-11-20 16:38:28');
INSERT INTO `schedule_job_log` VALUES ('157', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:38:30');
INSERT INTO `schedule_job_log` VALUES ('158', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:38:35');
INSERT INTO `schedule_job_log` VALUES ('159', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:38:40');
INSERT INTO `schedule_job_log` VALUES ('160', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:38:45');
INSERT INTO `schedule_job_log` VALUES ('161', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:38:50');
INSERT INTO `schedule_job_log` VALUES ('162', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:38:55');
INSERT INTO `schedule_job_log` VALUES ('163', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:39:00');
INSERT INTO `schedule_job_log` VALUES ('164', '2', 'testTask', 'renren', '0', null, '8813', '2019-11-20 16:39:50');
INSERT INTO `schedule_job_log` VALUES ('165', '2', 'testTask', 'renren', '0', null, '930', '2019-11-20 16:39:59');
INSERT INTO `schedule_job_log` VALUES ('166', '2', 'testTask', 'renren', '0', null, '1665', '2019-11-20 16:40:00');
INSERT INTO `schedule_job_log` VALUES ('167', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:40:05');
INSERT INTO `schedule_job_log` VALUES ('168', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:40:10');
INSERT INTO `schedule_job_log` VALUES ('169', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:40:15');
INSERT INTO `schedule_job_log` VALUES ('170', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:40:20');
INSERT INTO `schedule_job_log` VALUES ('171', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:40:25');
INSERT INTO `schedule_job_log` VALUES ('172', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:40:30');
INSERT INTO `schedule_job_log` VALUES ('173', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:40:35');
INSERT INTO `schedule_job_log` VALUES ('174', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:40:40');
INSERT INTO `schedule_job_log` VALUES ('175', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:40:45');
INSERT INTO `schedule_job_log` VALUES ('176', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:40:50');
INSERT INTO `schedule_job_log` VALUES ('177', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:40:55');
INSERT INTO `schedule_job_log` VALUES ('178', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:41:00');
INSERT INTO `schedule_job_log` VALUES ('179', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:41:05');
INSERT INTO `schedule_job_log` VALUES ('180', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:41:10');
INSERT INTO `schedule_job_log` VALUES ('181', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:41:15');
INSERT INTO `schedule_job_log` VALUES ('182', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:41:20');
INSERT INTO `schedule_job_log` VALUES ('183', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:41:25');
INSERT INTO `schedule_job_log` VALUES ('184', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:41:30');
INSERT INTO `schedule_job_log` VALUES ('185', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:41:35');
INSERT INTO `schedule_job_log` VALUES ('186', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:41:40');
INSERT INTO `schedule_job_log` VALUES ('187', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:41:45');
INSERT INTO `schedule_job_log` VALUES ('188', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:41:50');
INSERT INTO `schedule_job_log` VALUES ('189', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:41:55');
INSERT INTO `schedule_job_log` VALUES ('190', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:42:00');
INSERT INTO `schedule_job_log` VALUES ('191', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:42:05');
INSERT INTO `schedule_job_log` VALUES ('192', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:42:10');
INSERT INTO `schedule_job_log` VALUES ('193', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:42:15');
INSERT INTO `schedule_job_log` VALUES ('194', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:42:20');
INSERT INTO `schedule_job_log` VALUES ('195', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:42:25');
INSERT INTO `schedule_job_log` VALUES ('196', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:42:30');
INSERT INTO `schedule_job_log` VALUES ('197', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:42:35');
INSERT INTO `schedule_job_log` VALUES ('198', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:42:40');
INSERT INTO `schedule_job_log` VALUES ('199', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:42:45');
INSERT INTO `schedule_job_log` VALUES ('200', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:42:50');
INSERT INTO `schedule_job_log` VALUES ('201', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:42:55');
INSERT INTO `schedule_job_log` VALUES ('202', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:43:00');
INSERT INTO `schedule_job_log` VALUES ('203', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:43:05');
INSERT INTO `schedule_job_log` VALUES ('204', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:43:10');
INSERT INTO `schedule_job_log` VALUES ('205', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:43:15');
INSERT INTO `schedule_job_log` VALUES ('206', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:43:20');
INSERT INTO `schedule_job_log` VALUES ('207', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:43:25');
INSERT INTO `schedule_job_log` VALUES ('208', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:43:30');
INSERT INTO `schedule_job_log` VALUES ('209', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:43:35');
INSERT INTO `schedule_job_log` VALUES ('210', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:43:40');
INSERT INTO `schedule_job_log` VALUES ('211', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:43:45');
INSERT INTO `schedule_job_log` VALUES ('212', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:43:50');
INSERT INTO `schedule_job_log` VALUES ('213', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:43:55');
INSERT INTO `schedule_job_log` VALUES ('214', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:44:00');
INSERT INTO `schedule_job_log` VALUES ('215', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:44:05');
INSERT INTO `schedule_job_log` VALUES ('216', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:44:10');
INSERT INTO `schedule_job_log` VALUES ('217', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:44:15');
INSERT INTO `schedule_job_log` VALUES ('218', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:44:20');
INSERT INTO `schedule_job_log` VALUES ('219', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:44:25');
INSERT INTO `schedule_job_log` VALUES ('220', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:44:31');
INSERT INTO `schedule_job_log` VALUES ('221', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:44:35');
INSERT INTO `schedule_job_log` VALUES ('222', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:44:40');
INSERT INTO `schedule_job_log` VALUES ('223', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:44:45');
INSERT INTO `schedule_job_log` VALUES ('224', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:44:50');
INSERT INTO `schedule_job_log` VALUES ('225', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:44:55');
INSERT INTO `schedule_job_log` VALUES ('226', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:45:00');
INSERT INTO `schedule_job_log` VALUES ('227', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:45:05');
INSERT INTO `schedule_job_log` VALUES ('228', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:45:10');
INSERT INTO `schedule_job_log` VALUES ('229', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:45:15');
INSERT INTO `schedule_job_log` VALUES ('230', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:45:20');
INSERT INTO `schedule_job_log` VALUES ('231', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:45:25');
INSERT INTO `schedule_job_log` VALUES ('232', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:45:30');
INSERT INTO `schedule_job_log` VALUES ('233', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:45:35');
INSERT INTO `schedule_job_log` VALUES ('234', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:45:40');
INSERT INTO `schedule_job_log` VALUES ('235', '2', 'testTask', 'renren', '0', null, '0', '2019-11-20 16:45:45');
INSERT INTO `schedule_job_log` VALUES ('236', '2', 'testTask', 'renren', '0', null, '10228', '2019-11-20 16:46:35');
INSERT INTO `schedule_job_log` VALUES ('237', '2', 'testTask', 'renren', '0', null, '1810', '2019-11-20 16:46:45');
INSERT INTO `schedule_job_log` VALUES ('238', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:46:47');
INSERT INTO `schedule_job_log` VALUES ('239', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:46:50');
INSERT INTO `schedule_job_log` VALUES ('240', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:46:55');
INSERT INTO `schedule_job_log` VALUES ('241', '2', 'testTask', 'renren', '0', null, '6831', '2019-11-20 16:50:35');
INSERT INTO `schedule_job_log` VALUES ('242', '2', 'testTask', 'renren', '0', null, '1613', '2019-11-20 16:50:42');
INSERT INTO `schedule_job_log` VALUES ('243', '2', 'testTask', 'renren', '0', null, '2', '2019-11-20 16:50:45');
INSERT INTO `schedule_job_log` VALUES ('244', '2', 'testTask', 'renren', '0', null, '2', '2019-11-20 16:50:50');
INSERT INTO `schedule_job_log` VALUES ('245', '2', 'testTask', 'renren', '0', null, '4', '2019-11-20 16:50:55');
INSERT INTO `schedule_job_log` VALUES ('246', '2', 'testTask', 'renren', '0', null, '12', '2019-11-20 16:51:00');
INSERT INTO `schedule_job_log` VALUES ('247', '2', 'testTask', 'renren', '0', null, '2', '2019-11-20 16:51:05');
INSERT INTO `schedule_job_log` VALUES ('248', '2', 'testTask', 'renren', '0', null, '1', '2019-11-20 16:51:10');
INSERT INTO `schedule_job_log` VALUES ('249', '2', 'testTask', 'renren', '0', null, '6', '2019-11-20 16:51:15');
INSERT INTO `schedule_job_log` VALUES ('250', '2', 'testTask', 'renren', '0', null, '7', '2019-11-20 16:51:20');
INSERT INTO `schedule_job_log` VALUES ('251', '2', 'testTask', 'renren', '0', null, '2', '2019-11-20 16:51:25');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', '0', '云存储配置信息');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '0', '人人开源集团', '0', '0');
INSERT INTO `sys_dept` VALUES ('2', '1', '长沙分公司', '1', '0');
INSERT INTO `sys_dept` VALUES ('3', '1', '上海分公司', '2', '0');
INSERT INTO `sys_dept` VALUES ('4', '3', '技术部', '0', '0');
INSERT INTO `sys_dept` VALUES ('5', '3', '销售部', '1', '0');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '字典名称',
  `type` varchar(100) NOT NULL COMMENT '字典类型',
  `code` varchar(100) NOT NULL COMMENT '字典码',
  `value` varchar(1000) NOT NULL COMMENT '字典值',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标记  -1：已删除  0：正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`,`code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='数据字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '性别', 'sex', '0', '女', '0', null, '0');
INSERT INTO `sys_dict` VALUES ('2', '性别', 'sex', '1', '男', '1', null, '0');
INSERT INTO `sys_dict` VALUES ('3', '性别', 'sex', '2', '未知', '3', null, '0');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":1,\"roleName\":\"代理\",\"deptId\":4,\"deptName\":\"技术部\",\"menuIdList\":[1,2,15,16,17,18],\"deptIdList\":[],\"createTime\":\"Nov 20, 2019 2:55:04 PM\"}', '45', '0:0:0:0:0:0:0:1', '2019-11-20 14:55:05');
INSERT INTO `sys_log` VALUES ('2', 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":2,\"username\":\"test\",\"password\":\"81ef1f3342c66c0f427976a233d50fb98c6ce24eaef1b0dc77311c75d171fe91\",\"salt\":\"2IzBHY7PrqaOHbBDdpES\",\"email\":\"test@qq.com\",\"status\":1,\"roleIdList\":[1],\"createTime\":\"Nov 20, 2019 2:55:31 PM\",\"deptId\":4,\"deptName\":\"技术部\"}', '80', '0:0:0:0:0:0:0:1', '2019-11-20 14:55:32');
INSERT INTO `sys_log` VALUES ('3', 'admin', '修改定时任务', 'com.cube.modules.job.controller.ScheduleJobController.update()', '{\"jobId\":1,\"beanName\":\"testTask\",\"params\":\"renren\",\"cronExpression\":\"*/30 * * * * ?\",\"status\":0,\"remark\":\"参数测试\",\"createTime\":\"Nov 20, 2019 2:37:04 PM\"}', '172', '0:0:0:0:0:0:0:1', '2019-11-20 16:08:31');
INSERT INTO `sys_log` VALUES ('4', 'admin', '暂停定时任务', 'com.cube.modules.job.controller.ScheduleJobController.pause()', '[1]', '20', '0:0:0:0:0:0:0:1', '2019-11-20 16:08:37');
INSERT INTO `sys_log` VALUES ('5', 'admin', '修改定时任务', 'com.cube.modules.job.controller.ScheduleJobController.update()', '{\"jobId\":1,\"beanName\":\"testTask\",\"params\":\"renren\",\"cronExpression\":\"*/30 * * * * ?\",\"status\":1,\"remark\":\"参数测试\",\"createTime\":\"Nov 20, 2019 2:37:04 PM\"}', '17611', '0:0:0:0:0:0:0:1', '2019-11-20 16:10:15');
INSERT INTO `sys_log` VALUES ('6', 'admin', '删除定时任务', 'com.cube.modules.job.controller.ScheduleJobController.delete()', '[1]', '37', '0:0:0:0:0:0:0:1', '2019-11-20 16:18:02');
INSERT INTO `sys_log` VALUES ('7', 'admin', '保存定时任务', 'com.cube.modules.job.controller.ScheduleJobController.save()', '{\"jobId\":2,\"beanName\":\"testTask\",\"params\":\"renren\",\"cronExpression\":\"*/30 * * * * ?\",\"status\":0,\"remark\":\"参数测试\",\"createTime\":\"Nov 20, 2019 4:18:28 PM\"}', '115', '0:0:0:0:0:0:0:1', '2019-11-20 16:18:29');
INSERT INTO `sys_log` VALUES ('8', 'admin', '暂停定时任务', 'com.cube.modules.job.controller.ScheduleJobController.pause()', '[2]', '24', '0:0:0:0:0:0:0:1', '2019-11-20 16:22:20');
INSERT INTO `sys_log` VALUES ('9', 'admin', '修改定时任务', 'com.cube.modules.job.controller.ScheduleJobController.update()', '{\"jobId\":2,\"beanName\":\"testTask\",\"params\":\"renren\",\"cronExpression\":\"*/5 * * * * ?\",\"status\":1,\"remark\":\"参数测试\",\"createTime\":\"Nov 20, 2019 4:18:29 PM\"}', '1975', '0:0:0:0:0:0:0:1', '2019-11-20 16:22:29');
INSERT INTO `sys_log` VALUES ('10', 'admin', '恢复定时任务', 'com.cube.modules.job.controller.ScheduleJobController.resume()', '[2]', '4351', '0:0:0:0:0:0:0:1', '2019-11-20 16:22:39');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'fa fa-cog', '0');
INSERT INTO `sys_menu` VALUES ('2', '1', '管理员管理', 'modules/sys/user.html', null, '1', 'fa fa-user', '1');
INSERT INTO `sys_menu` VALUES ('3', '1', '角色管理', 'modules/sys/role.html', null, '1', 'fa fa-user-secret', '2');
INSERT INTO `sys_menu` VALUES ('4', '1', '菜单管理', 'modules/sys/menu.html', null, '1', 'fa fa-th-list', '3');
INSERT INTO `sys_menu` VALUES ('5', '1', 'SQL监控', 'druid/sql.html', null, '1', 'fa fa-bug', '4');
INSERT INTO `sys_menu` VALUES ('6', '1', '定时任务', 'modules/job/schedule.html', null, '1', 'fa fa-tasks', '5');
INSERT INTO `sys_menu` VALUES ('7', '6', '查看', null, 'sys:schedule:list,sys:schedule:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('8', '6', '新增', null, 'sys:schedule:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('9', '6', '修改', null, 'sys:schedule:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('10', '6', '删除', null, 'sys:schedule:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('11', '6', '暂停', null, 'sys:schedule:pause', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('12', '6', '恢复', null, 'sys:schedule:resume', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('13', '6', '立即执行', null, 'sys:schedule:run', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('14', '6', '日志列表', null, 'sys:schedule:log', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('15', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('16', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('17', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('18', '2', '删除', null, 'sys:user:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('19', '3', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('20', '3', '新增', null, 'sys:role:save,sys:menu:perms', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('21', '3', '修改', null, 'sys:role:update,sys:menu:perms', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('22', '3', '删除', null, 'sys:role:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('23', '4', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('24', '4', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('25', '4', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('26', '4', '删除', null, 'sys:menu:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('27', '1', '参数管理', 'modules/sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', '1', 'fa fa-sun-o', '6');
INSERT INTO `sys_menu` VALUES ('29', '1', '系统日志', 'modules/sys/log.html', 'sys:log:list', '1', 'fa fa-file-text-o', '7');
INSERT INTO `sys_menu` VALUES ('30', '1', '文件上传', 'modules/oss/oss.html', 'sys:oss:all', '1', 'fa fa-file-image-o', '6');
INSERT INTO `sys_menu` VALUES ('31', '1', '部门管理', 'modules/sys/dept.html', null, '1', 'fa fa-file-code-o', '1');
INSERT INTO `sys_menu` VALUES ('32', '31', '查看', null, 'sys:dept:list,sys:dept:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('33', '31', '新增', null, 'sys:dept:save,sys:dept:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('34', '31', '修改', null, 'sys:dept:update,sys:dept:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('35', '31', '删除', null, 'sys:dept:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('36', '1', '字典管理', 'modules/sys/dict.html', null, '1', 'fa fa-bookmark-o', '6');
INSERT INTO `sys_menu` VALUES ('37', '36', '查看', null, 'sys:dict:list,sys:dict:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('38', '36', '新增', null, 'sys:dict:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('39', '36', '修改', null, 'sys:dict:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('40', '36', '删除', null, 'sys:dict:delete', '2', null, '6');

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传';

-- ----------------------------
-- Records of sys_oss
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '代理', null, '4', '2019-11-20 14:55:05');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色与部门对应关系';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('2', '1', '2');
INSERT INTO `sys_role_menu` VALUES ('3', '1', '15');
INSERT INTO `sys_role_menu` VALUES ('4', '1', '16');
INSERT INTO `sys_role_menu` VALUES ('5', '1', '17');
INSERT INTO `sys_role_menu` VALUES ('6', '1', '18');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', '1', '1', '2016-11-11 11:11:11');
INSERT INTO `sys_user` VALUES ('2', 'test', '81ef1f3342c66c0f427976a233d50fb98c6ce24eaef1b0dc77311c75d171fe91', '2IzBHY7PrqaOHbBDdpES', 'test@qq.com', null, '1', '4', '2019-11-20 14:55:32');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '2', '1');
