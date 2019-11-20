/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/11/20 14:50:31                          */
/*==============================================================*/


drop table if exists da_alipay_log;

drop table if exists da_app;

drop table if exists da_file;

drop table if exists da_goods;

drop table if exists da_member;

drop table if exists da_package;

drop table if exists da_provision;

drop table if exists da_recharge;

drop table if exists da_recharge_log;

drop table if exists da_web_hook;

drop table if exists da_wx_config;

drop table if exists da_wxpay_log;

/*==============================================================*/
/* Table: da_alipay_log                                         */
/*==============================================================*/
create table da_alipay_log
(
   id                   varchar(32) not null,
   notify_time          datetime comment '通知时间',
   notify_type          varchar(64) comment '通知类型',
   notify_id            varchar(128) comment '通知校验ID',
   out_trade_no         varchar(64) comment '商户网站唯一订单号',
   subject              varchar(225) comment '交易标题',
   trade_no             varchar(64) comment '支付宝交易号',
   trade_status         char(2) comment '交易状态',
   seller_id            varchar(16) comment '卖家支付宝用户号',
   seller_email         varchar(25) comment '卖家支付宝账号',
   buyer_id             varchar(16) comment '买家支付宝用户号',
   buyer_email          varchar(25) comment '买家支付宝账号',
   total_fee            decimal(20,4) comment '交易金额',
   gmt_create           datetime comment '交易创建时间',
   gmt_payment          datetime comment '交易付款时间',
   pay_status           char(2) comment '支付状态。0：充值中；1：交易成功；2：交易失败',
   order_money          decimal(20,4) comment '订单金额',
   primary key (id)
);

alter table da_alipay_log comment '支付宝支付日志';

/*==============================================================*/
/* Table: da_app                                                */
/*==============================================================*/
create table da_app
(
   id                   varchar(32) not null,
   bundleid             varchar(255) default NULL,
   create_time          datetime not null default CURRENT_TIMESTAMP,
   description          varchar(255) default NULL,
   name                 varchar(255) default NULL,
   platform             varchar(255) default NULL,
   short_code           varchar(255) default NULL,
   currentid            varchar(32) default NULL,
   member_id            varchar(32) comment '会员id',
   download_count       int default 0 comment '下载次数',
   status               char(2) comment '0:正常   1：下架(禁止下载)',
   remark               varchar(200) comment '一般情况下，当status是1时，remark作为解说字段',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: da_file                                               */
/*==============================================================*/
create table da_file
(
   id                   varchar(32) not null,
   package_id           varchar(32) comment '包名',
   path                 varchar(250) comment '包名所在路径',
   file_name            varchar(50) comment '文件名称',
   type                 char(2) default '0' comment '0:APP   1:jpg小图标   2：npg小图标',
   oss_path             varchar(250) comment 'oss所在路径',
   oss_type             char(2) default '0' comment '0：阿里OSS平台      1：百度OSS     2：京东OSS',
   create_time          datetime default CURRENT_TIMESTAMP,
   status               char(2) default '0',
   remark               varchar(100) comment '备注',
   member_id            varchar(32) comment '会员id',
   app_id               varchar(32) comment '所属app',
   primary key (id)
);

alter table da_file comment 'APP包管理';

/*==============================================================*/
/* Table: da_goods                                              */
/*==============================================================*/
create table da_goods
(
   id                   varchar(32) not null,
   product_id           varchar(32),
   name                 varchar(100) comment '商品名称',
   price                decimal(10,2) comment '价格',
   download_amount      int comment '下载次数',
   seq                  int comment '次序',
   create_time          datetime comment '创建时间',
   status               char(2) default '0' comment '0:正常   1：下架',
   is_default           char(2) comment '是否是推荐 0:否   1：是',
   primary key (id)
);

/*==============================================================*/
/* Table: da_member                                             */
/*==============================================================*/
create table da_member
(
   id                   varchar(32) not null,
   user_name            varchar(50) comment '用户登录名称/即邮箱登录',
   password             varchar(50) comment '密码',
   tel                  varchar(13) comment '手机',
   real_name            varchar(20) comment '真实姓名',
   id_number            varchar(20) comment '身份证号',
   address              varchar(200) comment '详细地址',
   qq                   varchar(15),
   company              varchar(50) comment '公司名称',
   position             varchar(50) comment '职位',
   avatar               varchar(200) comment '头像',
   create_time          datetime,
   status               char(2) comment '0：未审核   1：审核中  2：审核通过   3：审核驳回',
   is_forbidden         char(2) comment '0:正常   1：已冻结',
   download_count       int default 0 comment '剩余下载次数',
   front_photo_path     varchar(200) comment '正面照',
   back_photo_path      varchar(200) comment '背面照',
   hand_photo_path      varchar(200) comment '手持照',
   front_photo_oss_path varchar(200) comment '正面照oss路径',
   back_photo_oss_path  varchar(200) comment '背面照oss路径',
   hand_photo_oss_path  varchar(200) comment '手持照oss路径',
   primary key (id)
);

alter table da_member comment '会员表';

/*==============================================================*/
/* Table: da_package                                            */
/*==============================================================*/
create table da_package
(
   id                   varchar(32) not null,
   build_version        varchar(255) default NULL,
   bundleid             varchar(255) default NULL,
   create_time          datetime not null default CURRENT_TIMESTAMP,
   extra                varchar(255) default NULL,
   file_name            varchar(255) default NULL,
   min_version          varchar(255) default NULL,
   name                 varchar(255) default NULL,
   platform             varchar(255) default NULL,
   size                 bigint(20) not null,
   version              varchar(255) default NULL,
   provision_id         varchar(32) default NULL,
   status               char(2) default '0' comment '0:正常   1：已废弃(回退到上一个版本，当前版本废弃，只有一个版本的时候，无回退功能)',
   change_log           varchar(1000) comment '更新日志',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: da_provision                                          */
/*==============================================================*/
create table da_provision
(
   id                   varchar(32) not null,
   uuid                 varchar(255) default NULL,
   create_date          datetime default NULL,
   device_count         int(11) not null,
   devices              mediumblob,
   expiration_date      datetime default NULL,
   is_enterprise        bit(1) not null,
   teamid               varchar(255) default NULL,
   team_name            varchar(255) default NULL,
   type                 varchar(255) default NULL,
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: da_recharge                                           */
/*==============================================================*/
create table da_recharge
(
   id                   varchar(32) not null,
   order_id             varchar(64) comment '订单id/充值记录id',
   member_id            varchar(32),
   recharge_amount      decimal(10,2) comment '充值金额',
   recharge_time        datetime,
   pay_type             char(2) comment '付款方式。1:微信；2：支付宝',
   order_number         varchar(32) comment '订单号',
   transaction_id       varchar(32) comment '交易流水号',
   whether_sms          char(2) default '0' comment '是否发送短信（0表示不发送，1表示发送）',
   recharge_status      char(2) comment '充值状态。0：待支付；1：充值中；2：交易成功；3：交易失败；4：已退款；5：交易关闭',
   remark               varchar(200) comment '备注',
   goods_id             varchar(32) comment '购买的下载次数包',
   goods_price          decimal(10,2) comment '商品价格',
   primary key (id)
);

alter table da_recharge comment '充值记录';

/*==============================================================*/
/* Table: da_recharge_log                                       */
/*==============================================================*/
create table da_recharge_log
(
   id                   varchar(32) not null,
   order_number         varchar(32) comment '订单号',
   step_number          char(2) comment '充值步骤',
   pay_content          varchar(200) comment '充值详情描述',
   create_time          datetime,
   pay_record           text comment '存储数据',
   member_id            varchar(32),
   primary key (id)
);

alter table da_recharge_log comment '充值流水';

/*==============================================================*/
/* Table: da_web_hook                                           */
/*==============================================================*/
create table da_web_hook
(
   id                   varchar(32) not null,
   name                 varchar(255) default NULL,
   type                 varchar(255) default NULL,
   url                  varchar(255) default NULL,
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: da_wx_config                                          */
/*==============================================================*/
create table da_wx_config
(
   id                   varchar(32) not null,
   app_id               varchar(32) comment 'appid',
   mch_id               varchar(32) comment '商户id',
   return_url           varchar(300) comment '同步回调地址',
   notify_url           varchar(300) comment '异步回调地址',
   create_time          datetime,
   status               char(2) comment '0:正常   1：禁用',
   remark               varchar(100) comment '备注',
   nonce_str            varchar(100) comment '随机字符串',
   sign                 varchar(400) comment '签名',
   trade_type           varchar(15) comment '交易类型',
   app_secret           varchar(400) comment '应用对应的凭证（保留）',
   app_key              varchar(400) comment '应用对应的密钥',
   gate_url             varchar(400) comment '获取预支付id的接口URL',
   extension            varchar(400) comment '其它参数。json字符串',
   primary key (id)
);

/*==============================================================*/
/* Table: da_wxpay_log                                          */
/*==============================================================*/
create table da_wxpay_log
(
   id                   varchar(32) not null,
   memeber_id           varchar(32) comment '会员id',
   out_trade_no         varchar(32) comment '商户订单号',
   result_code          varchar(100) comment '业务结果',
   return_code          varchar(100) comment '状态码',
   total_fee            decimal(10,2) comment '总金额（单位：分）',
   trade_type           varchar(20) comment '交易类型',
   transaction_id       varchar(32) comment '微信支付订单号',
   gmt_create           datetime comment '交易创建时间',
   gmt_payment          datetime comment '交易付款时间',
   time_end             datetime comment '支付完成时间',
   pay_status           char(2) comment '支付状态。0：充值中；1：交易成功；2：交易失败',
   order_money          decimal(10,2) comment '订单金额',
   body                 varchar(200) comment '交易描述',
   primary key (id)
);

alter table da_wxpay_log comment '微信支付日志';

