/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014-7-31 11:27:39                           */
/*==============================================================*/


drop table if exists brand_organization;

drop table if exists dealerManage;

drop table if exists dealerPlatformInfo;

drop table if exists inventoryInfo;

drop table if exists oly_product_info;

drop table if exists oly_product_platform;

drop table if exists oly_sales_details;

drop table if exists orderInfo;

drop table if exists orderItemsDetails;

drop table if exists ordertemp;

drop table if exists platformInfo;

drop table if exists platform_dealer_details;

drop table if exists platform_manager;

drop table if exists productinfo;

drop table if exists promotion;

drop table if exists promotionDetails;

drop table if exists salesDetails;

drop table if exists sales_monthly_target;

drop table if exists storeMangeInfo;

drop table if exists storePlatformInfo;

drop table if exists submit_report;

drop table if exists submit_report_amazon_field;

drop table if exists submit_report_amazon_fieldvalue;

drop table if exists submit_report_amazon_sku;

drop table if exists submit_report_amazon_skuxx;

drop table if exists submit_report_jumei_category;

drop table if exists submit_report_jumei_sku;

drop table if exists submit_report_jumei_suit;

drop table if exists submit_report_jumei_suit_detail;

drop table if exists submit_report_lefeng_sku;

drop table if exists submit_report_lefeng_suit;

drop table if exists submit_report_lefeng_suit_detail;

drop table if exists submit_report_setting;

drop table if exists submit_report_yhd_sku;

drop table if exists submit_report_yixun_category;

drop table if exists submit_report_yixun_sku;

drop table if exists suitInfo;

drop table if exists suitdetails;

drop table if exists suitdetails_bak;

/*==============================================================*/
/* Table: brand_organization                                    */
/*==============================================================*/
create table brand_organization
(
   id                   int not null auto_increment,
   brand_id             varchar(50),
   brand_name           varchar(100),
   organization_id      integer,
   organization_name    varchar(100),
   remarks              varchar(1000),
   updatetime           varchar(100),
   reserve1             varchar(100),
   reserve2             varchar(100),
   reserve3             varchar(100),
   primary key (id)
);

alter table brand_organization comment '品牌与所属组织对应表';

alter table brand_organization modify column id int comment '序列';

alter table brand_organization modify column brand_id varchar(50) comment '品牌编号';

alter table brand_organization modify column brand_name varchar(100) comment '品牌名称';

alter table brand_organization modify column organization_id integer comment '所属组织';

alter table brand_organization modify column organization_name varchar(100) comment '所属组织名称';

alter table brand_organization modify column remarks varchar(1000) comment '备注';

alter table brand_organization modify column updatetime varchar(100) comment '数据更新时间';

alter table brand_organization modify column reserve1 varchar(100) comment '预留字段1';

alter table brand_organization modify column reserve2 varchar(100) comment '预留字段2';

alter table brand_organization modify column reserve3 varchar(100) comment '预留字段3';

/*==============================================================*/
/* Table: dealerManage                                          */
/*==============================================================*/
create table dealerManage
(
   id                   int not null auto_increment,
   dealer_name          varchar(255),
   dealer_number        varchar(255),
   remarks              varchar(1000),
   organization_id      integer,
   updatetime           varchar(100),
   reserve1             varchar(100),
   reserve2             varchar(100),
   reserve3             varchar(100),
   primary key (id)
);

alter table dealerManage comment '供应商信息表';

alter table dealerManage modify column id int comment '序列';

alter table dealerManage modify column dealer_name varchar(255) comment '经销商名称';

alter table dealerManage modify column dealer_number varchar(255) comment '经销商编号';

alter table dealerManage modify column remarks varchar(1000) comment '备注';

alter table dealerManage modify column organization_id integer comment '所属组织';

alter table dealerManage modify column updatetime varchar(100) comment '数据更新时间';

alter table dealerManage modify column reserve1 varchar(100) comment '预留字段1';

alter table dealerManage modify column reserve2 varchar(100) comment '预留字段2';

alter table dealerManage modify column reserve3 varchar(100) comment '预留字段3';

/*==============================================================*/
/* Table: dealerPlatformInfo                                    */
/*==============================================================*/
create table dealerPlatformInfo
(
   id                   int not null auto_increment,
   dealer_number        varchar(255),
   platform_id          varchar(255),
   remarks              varchar(1000),
   category             varchar(100),
   ratio                float(11,2),
   organization_id      integer,
   updatetime           varchar(100),
   reserve1             varchar(100),
   reserve2             varchar(100),
   reserve3             varchar(100),
   primary key (id)
);

alter table dealerPlatformInfo comment '供应商与平台对应表';

alter table dealerPlatformInfo modify column id int comment '序列';

alter table dealerPlatformInfo modify column dealer_number varchar(255) comment '经销商编号';

alter table dealerPlatformInfo modify column platform_id varchar(255) comment '平台编号';

alter table dealerPlatformInfo modify column remarks varchar(1000) comment '备注';

alter table dealerPlatformInfo modify column category varchar(100) comment '品类';

alter table dealerPlatformInfo modify column ratio float(11,2) comment '比例';

alter table dealerPlatformInfo modify column organization_id integer comment '所属组织';

alter table dealerPlatformInfo modify column updatetime varchar(100) comment '数据更新时间';

alter table dealerPlatformInfo modify column reserve1 varchar(100) comment '预留字段1';

alter table dealerPlatformInfo modify column reserve2 varchar(100) comment '预留字段2';

alter table dealerPlatformInfo modify column reserve3 varchar(100) comment '预留字段3';

/*==============================================================*/
/* Table: inventoryInfo                                         */
/*==============================================================*/
create table inventoryInfo
(
   id                   int not null auto_increment,
   sku                  varchar(255),
   inventory_name       varchar(255),
   online_inventory     int,
   transit_inventory    int,
   curr_inventory       int,
   retail_purchases     int,
   shipments_number     int,
   delivery_time        varchar(100),
   curr_inventory_day   int,
   safety_inventory_day int,
   inventory_time       varchar(100),
   updatetime           varchar(100),
   primary key (id)
);

alter table inventoryInfo comment '库存表';

alter table inventoryInfo modify column id int comment '序列';

alter table inventoryInfo modify column sku varchar(255)  comment 'SKU编号';

alter table inventoryInfo modify column inventory_name varchar(255) comment '仓库';

alter table inventoryInfo modify column online_inventory int comment '在库数';

alter table inventoryInfo modify column transit_inventory int comment '在途库存';

alter table inventoryInfo modify column curr_inventory int comment '库存量';

alter table inventoryInfo modify column retail_purchases int comment '进货量';

alter table inventoryInfo modify column shipments_number int comment '出货量';

alter table inventoryInfo modify column delivery_time varchar(100) comment '到货时间';

alter table inventoryInfo modify column curr_inventory_day int comment '当前库存天数';

alter table inventoryInfo modify column safety_inventory_day int comment '安全库存天数';

alter table inventoryInfo modify column inventory_time varchar(100) comment '库存时间';

alter table inventoryInfo modify column updatetime varchar(100) comment '数据更新时间';

/*==============================================================*/
/* Table: oly_product_info                                      */
/*==============================================================*/
create table oly_product_info
(
   id                   int not null auto_increment,
   upccode              varchar(100),
   category             varchar(255),
   sub_category         varchar(255),
   material_number      varchar(100),
   brand                varchar(100),
   product_name         varchar(255),
   abbreviation         varchar(100),
   englis_name          varchar(255),
   grade                varchar(100),
   market_price         float(11,2),
   distri_type          varchar(100),
   stock_up_grade       varchar(100),
   origin               varchar(255),
   color                varchar(100),
   product_efficacy     varchar(255),
   efficacy_descriptor  varchar(255),
   usagea               varchar(255),
   suitableCroud        varchar(255),
   product_size         varchar(100),
   sku_gross_weight     float(11,2),
   box_specification    varchar(255),
   ingredient           varchar(255),
   specifications       varchar(100),
   remarks              varchar(255),
   organization_id      integer,
   update_time          varchar(100),
   reserve1             varchar(100),
   reserve2             varchar(100),
   reserve3             varchar(100),
   primary key (id),
   unique key AK_Key_2 (organization_id)
);

alter table oly_product_info comment '欧莱雅-商品信息表';

alter table oly_product_info modify column id int comment '序列';

alter table oly_product_info modify column upccode varchar(100) comment 'UPC条码';

alter table oly_product_info modify column category varchar(255) comment '品类';

alter table oly_product_info modify column sub_category varchar(255) comment '子品类';

alter table oly_product_info modify column material_number varchar(100) comment '物料编号';

alter table oly_product_info modify column brand varchar(100) comment '品牌';

alter table oly_product_info modify column product_name varchar(255) comment '产品名称';

alter table oly_product_info modify column abbreviation varchar(100) comment '简称';

alter table oly_product_info modify column englis_name varchar(255) comment '英文名';

alter table oly_product_info modify column grade varchar(100) comment '产品等级';

alter table oly_product_info modify column market_price float(11,2) comment '市场价';

alter table oly_product_info modify column distri_type varchar(100) comment '分销类型';

alter table oly_product_info modify column stock_up_grade varchar(100) comment '备货等级';

alter table oly_product_info modify column origin varchar(255) comment '产地';

alter table oly_product_info modify column color varchar(100) comment '颜色';

alter table oly_product_info modify column product_efficacy varchar(255) comment '功效分类';

alter table oly_product_info modify column efficacy_descriptor varchar(255) comment '功效说明';

alter table oly_product_info modify column usagea varchar(255) comment '使用方法';

alter table oly_product_info modify column suitableCroud varchar(255) comment '适用人群';

alter table oly_product_info modify column product_size varchar(100) comment '产品尺寸（长*宽*高mm）';

alter table oly_product_info modify column sku_gross_weight float(11,2) comment '产品克重（kg）';

alter table oly_product_info modify column box_specification varchar(255) comment '箱规';

alter table oly_product_info modify column ingredient varchar(255) comment '成分';

alter table oly_product_info modify column specifications varchar(100) comment '规格';

alter table oly_product_info modify column remarks varchar(255) comment '备注';

alter table oly_product_info modify column organization_id integer comment '所属组织';

alter table oly_product_info modify column update_time varchar(100) comment '数据更新时间';

alter table oly_product_info modify column reserve1 varchar(100) comment '预留字段1';

alter table oly_product_info modify column reserve2 varchar(100) comment '预留字段2';

alter table oly_product_info modify column reserve3 varchar(100) comment '预留字段3';

/*==============================================================*/
/* Table: oly_product_platform                                  */
/*==============================================================*/
create table oly_product_platform
(
   id                   int not null auto_increment,
   upccode              varchar(100),
   platform_product_id  varchar(100),
   remarks              varchar(255),
   organization_id      integer,
   update_time          varchar(100),
   reserve1             varchar(100),
   reserve2             varchar(100),
   reserve3             varchar(100),
   primary key (id),
   unique key AK_Key_2 (organization_id)
);

alter table oly_product_platform comment '欧莱雅-基础信息表与平台编码对应表';

alter table oly_product_platform modify column id int comment '序列';

alter table oly_product_platform modify column upccode varchar(100) comment 'UPC条码';

alter table oly_product_platform modify column platform_product_id varchar(100) comment '平台编码';

alter table oly_product_platform modify column remarks varchar(255) comment '备注';

alter table oly_product_platform modify column organization_id integer comment '所属组织';

alter table oly_product_platform modify column update_time varchar(100) comment '数据更新时间';

alter table oly_product_platform modify column reserve1 varchar(100) comment '预留字段1';

alter table oly_product_platform modify column reserve2 varchar(100) comment '预留字段2';

alter table oly_product_platform modify column reserve3 varchar(100) comment '预留字段3';

/*==============================================================*/
/* Table: oly_sales_details                                     */
/*==============================================================*/
create table oly_sales_details
(
   id                   int not null auto_increment,
   platform_name        varchar(100),
   market_start_time    varchar(100),
   market_end_time      varchar(100),
   platform_product_id  varchar(100),
   product_name         varchar(255),
   sales_number         float(11,2),
   inventory_total      integer(10),
   online_inventory     integer(10),
   transit_inventory    integer(10),
   inventory_balance_time varchar(100),
   remarks              varchar(255),
   organization_id      integer,
   update_time          varchar(100),
   reserve1             varchar(100),
   reserve2             varchar(100),
   reserve3             varchar(100),
   primary key (id),
   unique key AK_Key_2 (organization_id)
);

alter table oly_sales_details comment '欧莱雅-销售详情表';

alter table oly_sales_details modify column id int comment '序列';

alter table oly_sales_details modify column platform_name varchar(100) comment '平台名称';

alter table oly_sales_details modify column market_start_time varchar(100) comment '销售起止时间';

alter table oly_sales_details modify column market_end_time varchar(100) comment '销售结束时间';

alter table oly_sales_details modify column platform_product_id varchar(100) comment '平台编号';

alter table oly_sales_details modify column product_name varchar(255) comment '商品名称';

alter table oly_sales_details modify column sales_number float(11,2) comment '销售数量';

alter table oly_sales_details modify column inventory_total integer(10) comment '总库存';

alter table oly_sales_details modify column online_inventory integer(10) comment '在库库存';

alter table oly_sales_details modify column transit_inventory integer(10) comment '在途库存';

alter table oly_sales_details modify column inventory_balance_time varchar(100) comment '库存结余时间';

alter table oly_sales_details modify column remarks varchar(255) comment '备注';

alter table oly_sales_details modify column organization_id integer comment '所属组织';

alter table oly_sales_details modify column update_time varchar(100) comment '数据更新时间';

alter table oly_sales_details modify column reserve1 varchar(100) comment '预留字段1';

alter table oly_sales_details modify column reserve2 varchar(100) comment '预留字段2';

alter table oly_sales_details modify column reserve3 varchar(100) comment '预留字段3';

/*==============================================================*/
/* Table: orderInfo                                             */
/*==============================================================*/
create table orderInfo
(
   id                   int not null auto_increment,
   order_id             text,
   money                float(11,2),
   order_time           datetime,
   payment_time         varchar(100),
   number               int,
   total_money          double(11,2),
   updatetime           varchar(100),
   primary key (id)
);

alter table orderInfo comment '订单表';

alter table orderInfo modify column id int comment '序列';

alter table orderInfo modify column order_id text comment '订单编号';

alter table orderInfo modify column money float(11,2) comment '订单金额';

alter table orderInfo modify column order_time datetime comment '下单时间';

alter table orderInfo modify column payment_time varchar(100) comment '付款时间';

alter table orderInfo modify column number int comment '宝贝数量';

alter table orderInfo modify column total_money double(11,2) comment '总金额';

alter table orderInfo modify column updatetime varchar(100) comment '数据更新时间';

/*==============================================================*/
/* Table: orderItemsDetails                                     */
/*==============================================================*/
create table orderItemsDetails
(
   id                   int not null auto_increment,
   oder_id              varchar(100),
   sku                  varchar(100),
   updatetime           varchar(100),
   primary key (id)
);

alter table orderItemsDetails comment '订单项详细表';

alter table orderItemsDetails modify column id int comment '订单详情编号';

alter table orderItemsDetails modify column oder_id varchar(100) comment '订单编号';

alter table orderItemsDetails modify column sku varchar(100) comment 'SKU编码';

alter table orderItemsDetails modify column updatetime varchar(100) comment '数据更新时间';

/*==============================================================*/
/* Table: ordertemp                                             */
/*==============================================================*/
create table ordertemp
(
   id                   int not null auto_increment,
   product_id           varchar(255),
   order_id             varchar(100),
   user_name            varchar(100),
   alipay_accounts      varchar(100),
   trade_payable        double(11,2),
   shipping             double(11,2),
   pay_integral         int,
   total_money          double(11,2),
   rebates_integral     int,
   fact_pay_money       double(11,2),
   fact_pay_integral    int,
   order_status         varchar(255),
   buyer_message        varchar(1000),
   consignee_name       varchar(100),
   consignee_addr       varchar(1000),
   shipping_method      varchar(100),
   contact_phone        varchar(25),
   contact_pobile       varchar(25),
   create_time          datetime,
   pay_time             datetime,
   title                varchar(255),
   baby_type            varchar(25),
   logistics_number     varchar(25),
   logistics_company    varchar(100),
   remarks              varchar(100),
   baby_total           int,
   store_id             varchar(100),
   store_name           varchar(100),
   closue               varchar(200),
   sellers_servic_money double(11,2),
   buyer_servic_money   double(11,2),
   invoice_title        varchar(100),
   is_moblie            varchar(25),
   grading_info         varchar(100),
   front_money_mrder    varchar(25),
   modified_sku         varchar(100),
   modified_adder       varchar(1000),
   abnormal_info        varchar(100),
   updatetime           varchar(100),
   primary key (id)
);

alter table ordertemp comment '淘宝订单临时表';

alter table ordertemp modify column id int comment '序列';

alter table ordertemp modify column product_id varchar(255) comment '宝贝编号(平台对应编号)';

alter table ordertemp modify column order_id varchar(100) comment '订单编号';

alter table ordertemp modify column user_name varchar(100) comment '买家会员名';

alter table ordertemp modify column alipay_accounts varchar(100) comment '买家支付宝帐号';

alter table ordertemp modify column trade_payable double(11,2) comment '买家应付货款';

alter table ordertemp modify column shipping double(11,2) comment '买家应付邮费';

alter table ordertemp modify column pay_integral int comment '买家支付积分';

alter table ordertemp modify column total_money double(11,2) comment '总金额';

alter table ordertemp modify column rebates_integral int comment '返点积分';

alter table ordertemp modify column fact_pay_money double(11,2) comment '买家实际支付金额';

alter table ordertemp modify column fact_pay_integral int comment '买家实际支付积分';

alter table ordertemp modify column order_status varchar(255) comment '订单状态';

alter table ordertemp modify column buyer_message varchar(1000) comment '买家留言';

alter table ordertemp modify column consignee_name varchar(100) comment '收货人姓名';

alter table ordertemp modify column consignee_addr varchar(1000) comment '收货地址';

alter table ordertemp modify column shipping_method varchar(100) comment '运送方式';

alter table ordertemp modify column contact_phone varchar(25) comment '联系电话';

alter table ordertemp modify column contact_pobile varchar(25) comment '联系手机';

alter table ordertemp modify column create_time datetime comment '订单创建时间';

alter table ordertemp modify column pay_time datetime comment '订单付款时间';

alter table ordertemp modify column title varchar(255) comment '宝贝标题';

alter table ordertemp modify column baby_type varchar(25) comment '宝贝种类';

alter table ordertemp modify column logistics_number varchar(25) comment '物流单号';

alter table ordertemp modify column logistics_company varchar(100) comment '物流公司';

alter table ordertemp modify column remarks varchar(100) comment '订单备注';

alter table ordertemp modify column baby_total int comment '宝贝总数量';

alter table ordertemp modify column store_id varchar(100) comment '店铺Id';

alter table ordertemp modify column store_name varchar(100) comment '店铺名称';

alter table ordertemp modify column closue varchar(200) comment '订单关闭原因';

alter table ordertemp modify column sellers_servic_money double(11,2) comment '卖家服务费';

alter table ordertemp modify column buyer_servic_money double(11,2) comment '买家服务费';

alter table ordertemp modify column invoice_title varchar(100) comment '发票抬头';

alter table ordertemp modify column is_moblie varchar(25) comment '是否手机订单';

alter table ordertemp modify column grading_info varchar(100) comment '分阶段订单信息';

alter table ordertemp modify column front_money_mrder varchar(25) comment '定金排名';

alter table ordertemp modify column modified_sku varchar(100) comment '修改后的sku';

alter table ordertemp modify column modified_adder varchar(1000) comment '修改后的收货地址';

alter table ordertemp modify column abnormal_info varchar(100) comment '异常信息';

alter table ordertemp modify column updatetime varchar(100) comment '数据更新时间';

/*==============================================================*/
/* Table: platformInfo                                          */
/*==============================================================*/
create table platformInfo
(
   id                   int not null auto_increment,
   platform_id          varchar(255),
   platform_name        varchar(25),
   remarks              varchar(1000),
   updatetime           varchar(100),
   primary key (id)
);

alter table platformInfo comment '平台信息表';

alter table platformInfo modify column id int comment '序列';

alter table platformInfo modify column platform_id varchar(255) comment '平台编号';

alter table platformInfo modify column platform_name varchar(25) comment '平台名称';

alter table platformInfo modify column remarks varchar(1000) comment '备注';

alter table platformInfo modify column updatetime varchar(100) comment '数据更新时间';

/*==============================================================*/
/* Table: platform_dealer_details                               */
/*==============================================================*/
create table platform_dealer_details
(
   id                   int not null auto_increment,
   dealer_number        varchar(100),
   platform_id          varchar(100),
   remarks              varchar(1000),
   organization_id      integer,
   updatetime           varchar(100),
   reserve1             varchar(100),
   reserve2             varchar(100),
   reserve3             varchar(100),
   primary key (id)
);

alter table platform_dealer_details comment '平台与经销商对应表';

alter table platform_dealer_details modify column id int comment '序列';

alter table platform_dealer_details modify column dealer_number varchar(100) comment '经销商编码';

alter table platform_dealer_details modify column platform_id varchar(100) comment '平台编码';

alter table platform_dealer_details modify column remarks varchar(1000) comment '备注';

alter table platform_dealer_details modify column organization_id integer comment '所属组织';

alter table platform_dealer_details modify column updatetime varchar(100) comment '数据更新时间';

alter table platform_dealer_details modify column reserve1 varchar(100) comment '预留字段1';

alter table platform_dealer_details modify column reserve2 varchar(100) comment '预留字段2';

alter table platform_dealer_details modify column reserve3 varchar(100) comment '预留字段3';

/*==============================================================*/
/* Table: platform_manager                                      */
/*==============================================================*/
create table platform_manager
(
   id                   int not null auto_increment,
   platform_name        varchar(100),
   platform_id          varchar(100),
   remarks              varchar(1000),
   organization_id      integer,
   updatetime           varchar(100),
   reserve1             varchar(100),
   reserve2             varchar(100),
   reserve3             varchar(100),
   primary key (id)
);

alter table platform_manager comment '平台管理功能';

alter table platform_manager modify column id int comment '序列';

alter table platform_manager modify column platform_name varchar(100) comment '平台名称';

alter table platform_manager modify column platform_id varchar(100) comment '平台编码';

alter table platform_manager modify column remarks varchar(1000) comment '备注';

alter table platform_manager modify column organization_id integer comment '所属组织';

alter table platform_manager modify column updatetime varchar(100) comment '数据更新时间';

alter table platform_manager modify column reserve1 varchar(100) comment '预留字段1';

alter table platform_manager modify column reserve2 varchar(100) comment '预留字段2';

alter table platform_manager modify column reserve3 varchar(100) comment '预留字段3';

/*==============================================================*/
/* Table: productinfo                                           */
/*==============================================================*/
create table productinfo
(
   id                   int not null auto_increment,
   sku                  varchar(100),
   batch_number         varchar(100),
   material_number      varchar(100),
   brand                varchar(100),
   brand_id             integer,
   product_name         varchar(255),
   upccode              varchar(100),
   packaging            varchar(255),
   warehouse69          varchar(100),
   distri_type          varchar(100),
   category             varchar(255),
   series               varchar(255),
   product_efficacy     varchar(255),
   product_type         varchar(255),
   suitableCroud        varchar(255),
   usagea               varchar(255),
   specifications       varchar(100),
   market_price         float(11,2),
   lengtha              float,
   width                float,
   height               float,
   out_length           float,
   out_width            float,
   out_height           float,
   sku_gross_weight     float(11,2),
   gross_weight         float,
   box_specification    varchar(255),
   color                varchar(100),
   chinese_color        varchar(100),
   origin               varchar(255),
   ingredient           varchar(255),
   abbreviation         varchar(100),
   grade                varchar(100),
   status               varchar(100),
   market_time          varchar(100),
   englis_name          varchar(255),
   deails               varchar(255),
   expiration_date      varchar(100),
   organization_id      integer,
   updatetime           varchar(100),
   classification1      varchar(255),
   classification2      varchar(255),
   classification3      varchar(255),
   classification4      varchar(255),
   primary key (id),
   unique key AK_Key_2 (sku, organization_id)
);

alter table productinfo comment '商品信息表';

alter table productinfo modify column id int comment '序列';

alter table productinfo modify column sku varchar(100) comment 'SKU编号';

alter table productinfo modify column batch_number varchar(100) comment '批次号';

alter table productinfo modify column material_number varchar(100) comment '物料编号';

alter table productinfo modify column brand varchar(100) comment '品牌';

alter table productinfo modify column brand_id integer comment '品牌类型';

alter table productinfo modify column product_name varchar(255) comment '产品名称';

alter table productinfo modify column upccode varchar(100) comment 'UPC条码';

alter table productinfo modify column packaging varchar(255) comment '包装';

alter table productinfo modify column warehouse69 varchar(100) comment '仓库69码';

alter table productinfo modify column distri_type varchar(100) comment '分销类型';

alter table productinfo modify column category varchar(255) comment '品类';

alter table productinfo modify column series varchar(255) comment '系列';

alter table productinfo modify column product_efficacy varchar(255) comment '产品功效';

alter table productinfo modify column product_type varchar(255) comment '产品类型';

alter table productinfo modify column suitableCroud varchar(255) comment '适用人群';

alter table productinfo modify column usagea varchar(255) comment '使用方法';

alter table productinfo modify column specifications varchar(100) comment '规格';

alter table productinfo modify column market_price float(11,2) comment '市场价';

alter table productinfo modify column lengtha float comment '长（mm）';

alter table productinfo modify column width float comment '宽（mm）';

alter table productinfo modify column height float comment '高（mm）';

alter table productinfo modify column out_length float comment '外长（mm）';

alter table productinfo modify column out_width float comment '外宽（mm）';

alter table productinfo modify column out_height float comment '外高（mm）';

alter table productinfo modify column sku_gross_weight float(11,2) comment '单品毛重（g）';

alter table productinfo modify column gross_weight float comment '箱重（g）';

alter table productinfo modify column box_specification varchar(255) comment '箱规';

alter table productinfo modify column color varchar(100) comment '颜色';

alter table productinfo modify column chinese_color varchar(100) comment '中文颜色';

alter table productinfo modify column origin varchar(255) comment '产地';

alter table productinfo modify column ingredient varchar(255) comment '成分';

alter table productinfo modify column abbreviation varchar(100) comment '简称';

alter table productinfo modify column grade varchar(100) comment '产品等级';

alter table productinfo modify column status varchar(100) comment '状态';

alter table productinfo modify column market_time varchar(100) comment '上市时间';

alter table productinfo modify column englis_name varchar(255) comment '英文名';

alter table productinfo modify column deails varchar(255) comment '详细说明（200字内）';

alter table productinfo modify column expiration_date varchar(100) comment '产品有效期';

alter table productinfo modify column organization_id integer comment '所属组织';

alter table productinfo modify column updatetime varchar(100) comment '入库时间';

alter table productinfo modify column classification1 varchar(255) comment '预留分类一';

alter table productinfo modify column classification2 varchar(255) comment '预留分类二';

alter table productinfo modify column classification3 varchar(255) comment '预留分类三';

alter table productinfo modify column classification4 varchar(255) comment '预留分类四';

/*==============================================================*/
/* Table: promotion                                             */
/*==============================================================*/
create table promotion
(
   id                   int not null auto_increment,
   marketing_name       varchar(255),
   marketing_id         varchar(255),
   start_time           varchar(100),
   end_time             varchar(100),
   marketinghype        varchar(1000),
   content              varchar(255),
   discounts            float(11,2),
   updatetime           varchar(100),
   primary key (id)
);

alter table promotion comment '促销信息表';

alter table promotion modify column id int comment '序列';

alter table promotion modify column marketing_name varchar(255) comment '促销名称';

alter table promotion modify column marketing_id varchar(255) comment '促销编号';

alter table promotion modify column start_time varchar(100) comment '促销开始时间';

alter table promotion modify column end_time varchar(100) comment '促销结束时间';

alter table promotion modify column marketinghype varchar(1000) comment '促销方式';

alter table promotion modify column content varchar(255) comment '促销内容';

alter table promotion modify column discounts float(11,2) comment '折扣力度';

alter table promotion modify column updatetime varchar(100) comment '数据更新时间';

/*==============================================================*/
/* Table: promotionDetails                                      */
/*==============================================================*/
create table promotionDetails
(
   id                   int not null auto_increment,
   marketing_id         varchar(255),
   sku                  varchar(255),
   item_sold            int,
   price                double(11,2),
   updatetime           varchar(100),
   primary key (id),
   unique key AK_Key_2 (marketing_id)
);

alter table promotionDetails comment '促销详情表';

alter table promotionDetails modify column id int comment '序列';

alter table promotionDetails modify column marketing_id varchar(255) comment '促销编号';

alter table promotionDetails modify column sku varchar(255) comment 'SKU编码';

alter table promotionDetails modify column item_sold int comment '单品销量';

alter table promotionDetails modify column price double(11,2) comment '单品促销价格';

alter table promotionDetails modify column updatetime varchar(100) comment '数据更新时间';

/*==============================================================*/
/* Table: salesDetails                                          */
/*==============================================================*/
create table salesDetails
(
   id                   int not null auto_increment,
   sku                  varchar(100),
   platform_id          varchar(255),
   plat_poructid        varchar(100),
   link                 varchar(2000),
   current_price        float(10,2),
   day_sales            int,
   month_sales          int,
   sales_amount         float(10,2),
   sales_accounted      varchar(100),
   sales_order          int,
   sales_time           varchar(100),
   keyword1             varchar(255),
   keyword2             varchar(255),
   keyword3             varchar(255),
   suit_id              varchar(100),
   inventory_name       varchar(255),
   online_inventory     int,
   transit_inventory    int,
   curr_inventory       int,
   retail_purchases     int,
   shipments_number     int,
   time_delivery        varchar(100),
   curr_inventory_day   int,
   safety_inventory_day int,
   inventory_time       varchar(100),
   monitor_type         integer,
   organization_id      integer,
   updatetime           varchar(100),
   primary key (id)
);

alter table salesDetails comment '平台与基础销售信息对应表';

alter table salesDetails modify column id int comment '序列';

alter table salesDetails modify column sku varchar(100) comment 'SKU编号';

alter table salesDetails modify column platform_id varchar(255) comment '平台编号';

alter table salesDetails modify column plat_poructid varchar(100) comment '平台产品ID';

alter table salesDetails modify column link varchar(2000) comment '链接地址';

alter table salesDetails modify column current_price float(10,2) comment '当前售价';

alter table salesDetails modify column day_sales int comment '日销量';

alter table salesDetails modify column month_sales int comment '月销量';

alter table salesDetails modify column sales_amount float(10,2) comment '销售金额（零售价）';

alter table salesDetails modify column sales_accounted varchar(100) comment '销售占比(百分比)';

alter table salesDetails modify column sales_order int comment '销售排名';

alter table salesDetails modify column sales_time varchar(100) comment '销售时间';

alter table salesDetails modify column keyword1 varchar(255) comment '关键字一（平台分类）';

alter table salesDetails modify column keyword2 varchar(255) comment '关键字二（产品功效）';

alter table salesDetails modify column keyword3 varchar(255) comment '关键字三（品牌提供）';

alter table salesDetails modify column suit_id varchar(100) comment '套装编码';

alter table salesDetails modify column inventory_name varchar(255) comment '仓库';

alter table salesDetails modify column online_inventory int comment '在库数';

alter table salesDetails modify column transit_inventory int comment '在途库存';

alter table salesDetails modify column curr_inventory int comment '库存量';

alter table salesDetails modify column retail_purchases int comment '进货量';

alter table salesDetails modify column shipments_number int comment '出货量';

alter table salesDetails modify column time_delivery varchar(100) comment '到货时间';

alter table salesDetails modify column curr_inventory_day int comment '当前库存天数';

alter table salesDetails modify column safety_inventory_day int comment '安全库存天数';

alter table salesDetails modify column inventory_time varchar(100) comment '库存时间';

alter table salesDetails modify column monitor_type integer comment '1:爬虫数据 空或null：正数添加数据';

alter table salesDetails modify column organization_id integer comment '所属组织';

alter table salesDetails modify column updatetime varchar(100) comment '数据更新时间';

/*==============================================================*/
/* Table: sales_monthly_target                                  */
/*==============================================================*/
create table sales_monthly_target
(
   id                   int not null auto_increment,
   user_name            varchar(100),
   user_id              varchar(100),
   sales_target         float(11),
   sales_time           varchar(100),
   remarks              varchar(255),
   update_time          varchar(100),
   reserve1             varchar(100),
   reserve2             varchar(100),
   reserve3             varchar(100),
   organization_id      integer,
   primary key (id)
);

alter table sales_monthly_target comment '销售月度目标表';

alter table sales_monthly_target modify column id int comment '序列';

alter table sales_monthly_target modify column user_name varchar(100) comment '用户名';

alter table sales_monthly_target modify column user_id varchar(100) comment '用户ID';

alter table sales_monthly_target modify column sales_target float(11) comment '销售目标';

alter table sales_monthly_target modify column sales_time varchar(100) comment '时间';

alter table sales_monthly_target modify column remarks varchar(255) comment '备注';

alter table sales_monthly_target modify column update_time varchar(100) comment '数据更新时间';

alter table sales_monthly_target modify column reserve1 varchar(100) comment '预留字段1';

alter table sales_monthly_target modify column reserve2 varchar(100) comment '预留字段2';

alter table sales_monthly_target modify column reserve3 varchar(100) comment '预留字段3';

alter table sales_monthly_target modify column organization_id integer comment '所属组织';

/*==============================================================*/
/* Table: storeMangeInfo                                        */
/*==============================================================*/
create table storeMangeInfo
(
   id                   int not null auto_increment,
   store_name           varchar(255),
   store_id             varchar(100),
   remarks              varchar(1000),
   updatetime           varchar(100),
   reserve1             varchar(100),
   reserve2             varchar(100),
   reserve3             varchar(100),
   primary key (id)
);

alter table storeMangeInfo comment '仓库信息表';

alter table storeMangeInfo modify column id int comment '序列';

alter table storeMangeInfo modify column store_name varchar(255) comment '仓库名称';

alter table storeMangeInfo modify column store_id varchar(100) comment '仓库编码';

alter table storeMangeInfo modify column remarks varchar(1000) comment '备注';

alter table storeMangeInfo modify column updatetime varchar(100) comment '数据更新时间';

alter table storeMangeInfo modify column reserve1 varchar(100) comment '预留字段1';

alter table storeMangeInfo modify column reserve2 varchar(100) comment '预留字段2';

alter table storeMangeInfo modify column reserve3 varchar(100) comment '预留字段3';

/*==============================================================*/
/* Table: storePlatformInfo                                     */
/*==============================================================*/
create table storePlatformInfo
(
   id                   int not null auto_increment,
   store_id             varchar(100),
   platform_id          varchar(100),
   remarks              varchar(1000),
   updatetime           varchar(100),
   organization_id      integer,
   reserve1             varchar(100),
   reserve2             varchar(100),
   reserve3             varchar(100),
   primary key (id)
);

alter table storePlatformInfo comment '平台与仓库对应表';

alter table storePlatformInfo modify column id int comment '序列';

alter table storePlatformInfo modify column store_id varchar(100) comment '仓库编码';

alter table storePlatformInfo modify column platform_id varchar(100) comment '平台编码';

alter table storePlatformInfo modify column remarks varchar(1000) comment '备注';

alter table storePlatformInfo modify column updatetime varchar(100) comment '数据更新时间';

alter table storePlatformInfo modify column organization_id integer comment '所属组织';

alter table storePlatformInfo modify column reserve1 varchar(100) comment '预留字段1';

alter table storePlatformInfo modify column reserve2 varchar(100) comment '预留字段2';

alter table storePlatformInfo modify column reserve3 varchar(100) comment '预留字段3';

/*==============================================================*/
/* Table: submit_report                                         */
/*==============================================================*/
create table submit_report
(
   id                   int(11) not null auto_increment,
   platform_id          int(11) default NULL comment '平台ID',
   platform_name        varchar(100) default NULL comment '平台名称',
   report_type          varchar(10) default NULL comment '表报类型',
   updated_user         int(100) default NULL comment '更新人ID',
   updated_time         datetime default NULL comment '更新时间',
   role_id              int(11) default NULL comment '角色编码',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report comment 'submit_report';

alter table submit_report modify column id int(11) comment 'id';

alter table submit_report modify column platform_id int(11) comment '平台ID';

alter table submit_report modify column platform_name varchar(100) comment '平台名称';

alter table submit_report modify column report_type varchar(10) comment '表报类型';

alter table submit_report modify column updated_user int(100) comment '更新人ID';

alter table submit_report modify column updated_time datetime comment '更新时间';

alter table submit_report modify column role_id int(11) comment '角色编码';

/*==============================================================*/
/* Table: submit_report_amazon_field                            */
/*==============================================================*/
create table submit_report_amazon_field
(
   id                   int(11) not null auto_increment,
   field_name           varchar(20) not null,
   updated_time         datetime default NULL,
   updated_user         varchar(20) default NULL,
   role_id              int(11) default NULL,
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report_amazon_field comment 'submit_report_amazon_field';

alter table submit_report_amazon_field modify column id int(11) comment 'id';

alter table submit_report_amazon_field modify column field_name varchar(20) comment 'field_name';

alter table submit_report_amazon_field modify column updated_time datetime comment 'updated_time';

alter table submit_report_amazon_field modify column updated_user varchar(20) comment 'updated_user';

alter table submit_report_amazon_field modify column role_id int(11) comment 'role_id';

/*==============================================================*/
/* Table: submit_report_amazon_fieldvalue                       */
/*==============================================================*/
create table submit_report_amazon_fieldvalue
(
   id                   int(11) not null auto_increment,
   field_value          varchar(255) not null comment '字段属性值',
   field_id             int(11) default NULL comment '字段ID',
   updated_time         datetime default NULL,
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report_amazon_fieldvalue comment 'submit_report_amazon_fieldvalue';

alter table submit_report_amazon_fieldvalue modify column id int(11) comment 'id';

alter table submit_report_amazon_fieldvalue modify column field_value varchar(255) comment '字段属性值';

alter table submit_report_amazon_fieldvalue modify column field_id int(11) comment '字段ID';

alter table submit_report_amazon_fieldvalue modify column updated_time datetime comment 'updated_time';

/*==============================================================*/
/* Table: submit_report_amazon_sku                              */
/*==============================================================*/
create table submit_report_amazon_sku
(
   id                   int(11) not null auto_increment,
   your_email           varchar(255) not null comment '您的联系邮箱-(供应商邮箱)',
   purchaser_manager    varchar(255) not null comment '负责该品牌的亚马逊采购经理',
   remark               varchar(1000) default NULL comment '备注',
   supplier_name_code   varchar(255) not null comment '供应商名称和编码 (由4-5位数字或字母组成) ',
   brand_name           varchar(255) default NULL comment '品牌名称',
   brand_name_add       varchar(255) default NULL comment '品牌名称（新增）',
   brand_type           varchar(20) not null comment '品牌类型',
   brand_description    varchar(5000) not null comment '品牌介绍(20-3000字)',
   scan_bar_code        varchar(20) not null comment '是否有可扫描条形码?',
   bar_code_type        varchar(20) default NULL comment '条形码类型',
   upc                  varchar(20) default NULL comment 'EAN / UPC / 非国际标准条形码',
   discern_code         varchar(20) default NULL comment '商品唯一识别编码(货号)',
   market_price         decimal(11,2) not null comment '市场定价',
   yamaxun_purchase_price decimal(11,2) not null comment '亚马逊采购价',
   product_type         varchar(20) not null comment '商品类别',
   product_second_category varchar(20) not null comment '商品二级分类',
   internal_physical_state varchar(20) not null comment '内容物的物理状态',
   pc_relationship_type varchar(20) not null comment 'PC关系类型',
   pc_relationship_type_code varchar(20) default NULL comment '款式编码-PC关系识别码',
   style_code           varchar(20) default NULL comment '商品容量-PC关系(单位：毫升ml)',
   product_volume       varchar(20) default NULL comment '商品容量-PC关系(单位：毫升ml)',
   color                varchar(20) default NULL comment '商品颜色-PC关系',
   producing_area_foreign varchar(20) default NULL comment '商品生产地-外国',
   producing_area_china varchar(20) default NULL comment '商品生产地-中国',
   component            varchar(100) not null comment '成分',
   perfume_mediterranean varchar(20) default NULL comment '香水香调',
   suitable_person      varchar(20) not null comment '适用人群',
   suitable_skin        varchar(20) default NULL comment '适合肌肤',
   suitable_hair        varchar(20) default NULL comment '适合发质',
   limit_date           varchar(20) not null comment '是否需要限制保质期？',
   expiration_date      varchar(20) default NULL comment '保质期',
   product_name         varchar(1000) not null comment '商品名称-150字内，不要包含“(进)”',
   product_description  varchar(5000) not null comment '使用说明',
   package_list         varchar(255) default NULL comment '包装清单',
   product_other_des    varchar(1000) not null comment '商品其他详细描述-至少10个字符',
   search_key_function  varchar(255) default NULL comment '搜索关键词 -功能',
   search_key_min       varchar(255) default NULL comment '搜索关键词 -套装/小样',
   search_key_supplier  varchar(255) default NULL comment '搜索关键词 -供应商自填',
   some_package_num     varchar(20) default NULL comment '多包装产品数量',
   some_package_unit    varchar(20) default NULL comment '多包装产品数量单位',
   delevery_package     varchar(20) not null comment '送货时是否为成箱包装',
   box_regular          varchar(255) default NULL comment '箱规(大包内所含商品数量)',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report_amazon_sku comment 'submit_report_amazon_sku';

alter table submit_report_amazon_sku modify column id int(11) comment 'id';

alter table submit_report_amazon_sku modify column your_email varchar(255) comment '您的联系邮箱-(供应商邮箱)';

alter table submit_report_amazon_sku modify column purchaser_manager varchar(255) comment '负责该品牌的亚马逊采购经理';

alter table submit_report_amazon_sku modify column remark varchar(1000) comment '备注';

alter table submit_report_amazon_sku modify column supplier_name_code varchar(255) comment '供应商名称和编码 (由4-5位数字或字母组成) ';

alter table submit_report_amazon_sku modify column brand_name varchar(255) comment '品牌名称';

alter table submit_report_amazon_sku modify column brand_name_add varchar(255) comment '品牌名称（新增）';

alter table submit_report_amazon_sku modify column brand_type varchar(20) comment '品牌类型';

alter table submit_report_amazon_sku modify column brand_description varchar(5000) comment '品牌介绍(20-3000字)';

alter table submit_report_amazon_sku modify column scan_bar_code varchar(20) comment '是否有可扫描条形码?';

alter table submit_report_amazon_sku modify column bar_code_type varchar(20) comment '条形码类型';

alter table submit_report_amazon_sku modify column upc varchar(20) comment 'EAN / UPC / 非国际标准条形码';

alter table submit_report_amazon_sku modify column discern_code varchar(20) comment '商品唯一识别编码(货号)';

alter table submit_report_amazon_sku modify column market_price decimal(11,2) comment '市场定价';

alter table submit_report_amazon_sku modify column yamaxun_purchase_price decimal(11,2) comment '亚马逊采购价';

alter table submit_report_amazon_sku modify column product_type varchar(20) comment '商品类别';

alter table submit_report_amazon_sku modify column product_second_category varchar(20) comment '商品二级分类';

alter table submit_report_amazon_sku modify column internal_physical_state varchar(20) comment '内容物的物理状态';

alter table submit_report_amazon_sku modify column pc_relationship_type varchar(20) comment 'PC关系类型';

alter table submit_report_amazon_sku modify column pc_relationship_type_code varchar(20) comment '款式编码-PC关系识别码';

alter table submit_report_amazon_sku modify column style_code varchar(20) comment '商品容量-PC关系(单位：毫升ml)';

alter table submit_report_amazon_sku modify column product_volume varchar(20) comment '商品容量-PC关系(单位：毫升ml)';

alter table submit_report_amazon_sku modify column color varchar(20) comment '商品颜色-PC关系';

alter table submit_report_amazon_sku modify column producing_area_foreign varchar(20) comment '商品生产地-外国';

alter table submit_report_amazon_sku modify column producing_area_china varchar(20) comment '商品生产地-中国';

alter table submit_report_amazon_sku modify column component varchar(100) comment '成分';

alter table submit_report_amazon_sku modify column perfume_mediterranean varchar(20) comment '香水香调';

alter table submit_report_amazon_sku modify column suitable_person varchar(20) comment '适用人群';

alter table submit_report_amazon_sku modify column suitable_skin varchar(20) comment '适合肌肤';

alter table submit_report_amazon_sku modify column suitable_hair varchar(20) comment '适合发质';

alter table submit_report_amazon_sku modify column limit_date varchar(20) comment '是否需要限制保质期？';

alter table submit_report_amazon_sku modify column expiration_date varchar(20) comment '保质期';

alter table submit_report_amazon_sku modify column product_name varchar(1000) comment '商品名称-150字内，不要包含“(进)”';

alter table submit_report_amazon_sku modify column product_description varchar(5000) comment '使用说明';

alter table submit_report_amazon_sku modify column package_list varchar(255) comment '包装清单';

alter table submit_report_amazon_sku modify column product_other_des varchar(1000) comment '商品其他详细描述-至少10个字符';

alter table submit_report_amazon_sku modify column search_key_function varchar(255) comment '搜索关键词 -功能';

alter table submit_report_amazon_sku modify column search_key_min varchar(255) comment '搜索关键词 -套装/小样';

alter table submit_report_amazon_sku modify column search_key_supplier varchar(255) comment '搜索关键词 -供应商自填';

alter table submit_report_amazon_sku modify column some_package_num varchar(20) comment '多包装产品数量';

alter table submit_report_amazon_sku modify column some_package_unit varchar(20) comment '多包装产品数量单位';

alter table submit_report_amazon_sku modify column delevery_package varchar(20) comment '送货时是否为成箱包装';

alter table submit_report_amazon_sku modify column box_regular varchar(255) comment '箱规(大包内所含商品数量)';

/*==============================================================*/
/* Table: submit_report_amazon_skuxx                            */
/*==============================================================*/
create table submit_report_amazon_skuxx
(
   id                   int(11) not null auto_increment,
   your_mail            varchar(255) default NULL comment '您的联系邮箱-(供应商的邮箱)',
   purchasing_manager   varchar(255) default NULL comment '负责该品牌的亚马逊采购经理',
   remark               varchar(1000) default NULL comment '备注',
   supplier_or_code     varchar(255) not null comment '供应商名称和编码',
   brand_name           varchar(255) default NULL comment '品牌名称',
   brand_name_new       varchar(255) default NULL comment '品牌名称（新增）',
   brand_type           varchar(255) not null comment '品牌类型',
   brand_description    varchar(5000) not null comment '品牌介绍(20-3000字)',
   scan_code            varchar(20) not null comment '是否有可扫描条形码?',
   code_type            varchar(20) default NULL comment '条形码类型',
   upc                  varchar(20) default NULL comment 'EAN / UPC / 非国际标准条形码',
   article_no           varchar(20) default NULL comment '商品唯一识别编码(货号)',
   market_price         decimal(11,2) default NULL comment '市场定价',
   amazon_price         decimal(11,2) default NULL comment '亚马逊采购价',
   product_type         varchar(255) default NULL comment '商品类别',
   product_second_category varchar(255) default NULL comment '商品二级分类',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report_amazon_skuxx comment 'submit_report_amazon_skuxx';

alter table submit_report_amazon_skuxx modify column id int(11) comment 'id';

alter table submit_report_amazon_skuxx modify column your_mail varchar(255) comment '您的联系邮箱-(供应商的邮箱)';

alter table submit_report_amazon_skuxx modify column purchasing_manager varchar(255) comment '负责该品牌的亚马逊采购经理';

alter table submit_report_amazon_skuxx modify column remark varchar(1000) comment '备注';

alter table submit_report_amazon_skuxx modify column supplier_or_code varchar(255) comment '供应商名称和编码';

alter table submit_report_amazon_skuxx modify column brand_name varchar(255) comment '品牌名称';

alter table submit_report_amazon_skuxx modify column brand_name_new varchar(255) comment '品牌名称（新增）';

alter table submit_report_amazon_skuxx modify column brand_type varchar(255) comment '品牌类型';

alter table submit_report_amazon_skuxx modify column brand_description varchar(5000) comment '品牌介绍(20-3000字)';

alter table submit_report_amazon_skuxx modify column scan_code varchar(20) comment '是否有可扫描条形码?';

alter table submit_report_amazon_skuxx modify column code_type varchar(20) comment '条形码类型';

alter table submit_report_amazon_skuxx modify column upc varchar(20) comment 'EAN / UPC / 非国际标准条形码';

alter table submit_report_amazon_skuxx modify column article_no varchar(20) comment '商品唯一识别编码(货号)';

alter table submit_report_amazon_skuxx modify column market_price decimal(11,2) comment '市场定价';

alter table submit_report_amazon_skuxx modify column amazon_price decimal(11,2) comment '亚马逊采购价';

alter table submit_report_amazon_skuxx modify column product_type varchar(255) comment '商品类别';

alter table submit_report_amazon_skuxx modify column product_second_category varchar(255) comment '商品二级分类';

/*==============================================================*/
/* Table: submit_report_jumei_category                          */
/*==============================================================*/
create table submit_report_jumei_category
(
   id                   bigint(12) not null auto_increment,
   brand_name           varchar(100) default NULL comment '品牌名称',
   brand_id             int(12) default NULL comment '品牌编码',
   new_category_id      int(12) default NULL comment '新分类ID',
   new_category_name    varchar(100) default NULL comment '新分类名称',
   four_category_id     int(12) default NULL comment '四级分类ID',
   four_category_name   varchar(100) default NULL comment '四级分类名称',
   mom_category_leve1   varchar(100) default NULL comment '母婴分类一级',
   mom_category_leve2   varchar(100) default NULL comment '母婴分类二级',
   mom_category_leve3   varchar(100) default NULL comment '母婴分类三级',
   mom_category_leve4   varchar(100) default NULL comment '母婴分类四级',
   updated_time         datetime default NULL comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report_jumei_category comment 'submit_report_jumei_category';

alter table submit_report_jumei_category modify column id bigint(12) comment 'id';

alter table submit_report_jumei_category modify column brand_name varchar(100) comment '品牌名称';

alter table submit_report_jumei_category modify column brand_id int(12) comment '品牌编码';

alter table submit_report_jumei_category modify column new_category_id int(12) comment '新分类ID';

alter table submit_report_jumei_category modify column new_category_name varchar(100) comment '新分类名称';

alter table submit_report_jumei_category modify column four_category_id int(12) comment '四级分类ID';

alter table submit_report_jumei_category modify column four_category_name varchar(100) comment '四级分类名称';

alter table submit_report_jumei_category modify column mom_category_leve1 varchar(100) comment '母婴分类一级';

alter table submit_report_jumei_category modify column mom_category_leve2 varchar(100) comment '母婴分类二级';

alter table submit_report_jumei_category modify column mom_category_leve3 varchar(100) comment '母婴分类三级';

alter table submit_report_jumei_category modify column mom_category_leve4 varchar(100) comment '母婴分类四级';

alter table submit_report_jumei_category modify column updated_time datetime comment '更新时间';

/*==============================================================*/
/* Table: submit_report_jumei_sku                               */
/*==============================================================*/
create table submit_report_jumei_sku
(
   id                   int(11) not null auto_increment,
   supply_name          varchar(100) default NULL comment '供应商编码',
   barcode              varchar(20) default NULL comment '条码',
   product_name         varchar(255) default NULL comment '产品名称',
   brand_name           varchar(255) default NULL comment '品牌名称',
   standard             varchar(100) default NULL comment '规格',
   brand_id             int(11) default NULL comment '品牌id（分类中）',
   new_category         int(11) default NULL comment '新分类ID',
   four_category        int(11) default NULL comment '四级分类',
   deal                 varchar(100) default NULL comment 'dealmall',
   hors                 varchar(100) default NULL comment 'H或S',
   formal_or_min        varchar(100) default NULL comment '正装或中小样',
   has_prepare_word     varchar(100) default NULL comment '是否有备进字',
   purchase_reason      varchar(255) default NULL comment '采购理由',
   price                decimal(11,2) default NULL comment '零售价',
   stock_price          decimal(11,2) default NULL comment '进货价',
   group_price          decimal(11,2) default NULL comment '团购价',
   store_price          decimal(11,2) default NULL comment '商城价',
   group_profit_rate    decimal(11,2) default NULL comment '团购毛利率',
   store_profit_rate    decimal(11,2) default NULL comment '商城毛利率',
   taobao_price         decimal(11,2) default NULL comment '淘宝价',
   lefeng_price         decimal(11,2) default NULL comment '乐峰价',
   zhuoyue_price        decimal(11,2) default NULL comment '卓越价',
   updated_time         datetime default NULL comment '更新时间',
   updated_user         int(11) default NULL comment '更新人',
   role_id              int(11) default NULL comment '角色',
   report_id            int(11) default NULL comment '提表编码',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report_jumei_sku comment 'submit_report_jumei_sku';

alter table submit_report_jumei_sku modify column id int(11) comment 'id';

alter table submit_report_jumei_sku modify column supply_name varchar(100) comment '供应商编码';

alter table submit_report_jumei_sku modify column barcode varchar(20) comment '条码';

alter table submit_report_jumei_sku modify column product_name varchar(255) comment '产品名称';

alter table submit_report_jumei_sku modify column brand_name varchar(255) comment '品牌名称';

alter table submit_report_jumei_sku modify column standard varchar(100) comment '规格';

alter table submit_report_jumei_sku modify column brand_id int(11) comment '品牌id（分类中）';

alter table submit_report_jumei_sku modify column new_category int(11) comment '新分类ID';

alter table submit_report_jumei_sku modify column four_category int(11) comment '四级分类';

alter table submit_report_jumei_sku modify column deal varchar(100) comment 'dealmall';

alter table submit_report_jumei_sku modify column hors varchar(100) comment 'H或S';

alter table submit_report_jumei_sku modify column formal_or_min varchar(100) comment '正装或中小样';

alter table submit_report_jumei_sku modify column has_prepare_word varchar(100) comment '是否有备进字';

alter table submit_report_jumei_sku modify column purchase_reason varchar(255) comment '采购理由';

alter table submit_report_jumei_sku modify column price decimal(11,2) comment '零售价';

alter table submit_report_jumei_sku modify column stock_price decimal(11,2) comment '进货价';

alter table submit_report_jumei_sku modify column group_price decimal(11,2) comment '团购价';

alter table submit_report_jumei_sku modify column store_price decimal(11,2) comment '商城价';

alter table submit_report_jumei_sku modify column group_profit_rate decimal(11,2) comment '团购毛利率';

alter table submit_report_jumei_sku modify column store_profit_rate decimal(11,2) comment '商城毛利率';

alter table submit_report_jumei_sku modify column taobao_price decimal(11,2) comment '淘宝价';

alter table submit_report_jumei_sku modify column lefeng_price decimal(11,2) comment '乐峰价';

alter table submit_report_jumei_sku modify column zhuoyue_price decimal(11,2) comment '卓越价';

alter table submit_report_jumei_sku modify column updated_time datetime comment '更新时间';

alter table submit_report_jumei_sku modify column updated_user int(11) comment '更新人';

alter table submit_report_jumei_sku modify column role_id int(11) comment '角色';

alter table submit_report_jumei_sku modify column report_id int(11) comment '提表编码';

/*==============================================================*/
/* Table: submit_report_jumei_suit                              */
/*==============================================================*/
create table submit_report_jumei_suit
(
   id                   int(11) not null,
   suit_name            varchar(100) default NULL comment '虚拟组套产品名称',
   brand_name           varchar(100) default NULL comment '品牌名称',
   combination_num      int(11) default NULL comment '组合数量',
   market_price         decimal(11,2) default NULL comment '组合后市场价',
   store_price          decimal(11,2) default NULL comment '组合后进货价',
   group_price          decimal(11,2) default NULL comment '组合后团购价',
   price                decimal(11,2) default NULL comment '组合后商城价',
   updated_user         varchar(11) default NULL comment '更新人',
   updated_time         datetime default NULL comment '更新时间',
   role_id              int(11) default NULL comment '角色',
   report_id            bit(11) default NULL comment '提报ID',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report_jumei_suit comment 'submit_report_jumei_suit';

alter table submit_report_jumei_suit modify column id int(11) comment 'id';

alter table submit_report_jumei_suit modify column suit_name varchar(100) comment '虚拟组套产品名称';

alter table submit_report_jumei_suit modify column brand_name varchar(100) comment '品牌名称';

alter table submit_report_jumei_suit modify column combination_num int(11) comment '组合数量';

alter table submit_report_jumei_suit modify column market_price decimal(11,2) comment '组合后市场价';

alter table submit_report_jumei_suit modify column store_price decimal(11,2) comment '组合后进货价';

alter table submit_report_jumei_suit modify column group_price decimal(11,2) comment '组合后团购价';

alter table submit_report_jumei_suit modify column price decimal(11,2) comment '组合后商城价';

alter table submit_report_jumei_suit modify column updated_user varchar(11) comment '更新人';

alter table submit_report_jumei_suit modify column updated_time datetime comment '更新时间';

alter table submit_report_jumei_suit modify column role_id int(11) comment '角色';

alter table submit_report_jumei_suit modify column report_id bit(11) comment '提报ID';

/*==============================================================*/
/* Table: submit_report_jumei_suit_detail                       */
/*==============================================================*/
create table submit_report_jumei_suit_detail
(
   id                   int(11) not null auto_increment,
   product_id           int(11) default NULL comment '平台产品ID',
   sub_sku              int(11) default NULL comment '子sku',
   product_name         varchar(255) default NULL comment '产品名称',
   sub_sku_price        decimal(11,2) default NULL comment '子sku价格',
   sub_sku_store_price  decimal(11,2) default NULL comment '子sku进货价格',
   sub_sku_group_price  decimal(11,2) default NULL comment '子sku团购价格',
   sub_sku_market_price decimal(11,2) default NULL comment '子sku市场价格',
   bj_process_num       int(11) default NULL comment 'bj加工数量',
   sh_process_num       int(11) default NULL comment 'sh加工数量',
   cd_process_num       int(11) default NULL comment 'cd加工数量',
   gz_process_num       int(11) default NULL comment 'gz加工数量',
   updated_user         varchar(100) default NULL comment '更新人',
   updated_time         datetime default NULL comment '更新时间',
   suit_id              int(11) default NULL comment '套装ID',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report_jumei_suit_detail comment 'submit_report_jumei_suit_detail';

alter table submit_report_jumei_suit_detail modify column id int(11) comment 'id';

alter table submit_report_jumei_suit_detail modify column product_id int(11) comment '平台产品ID';

alter table submit_report_jumei_suit_detail modify column sub_sku int(11) comment '子sku';

alter table submit_report_jumei_suit_detail modify column product_name varchar(255) comment '产品名称';

alter table submit_report_jumei_suit_detail modify column sub_sku_price decimal(11,2) comment '子sku价格';

alter table submit_report_jumei_suit_detail modify column sub_sku_store_price decimal(11,2) comment '子sku进货价格';

alter table submit_report_jumei_suit_detail modify column sub_sku_group_price decimal(11,2) comment '子sku团购价格';

alter table submit_report_jumei_suit_detail modify column sub_sku_market_price decimal(11,2) comment '子sku市场价格';

alter table submit_report_jumei_suit_detail modify column bj_process_num int(11) comment 'bj加工数量';

alter table submit_report_jumei_suit_detail modify column sh_process_num int(11) comment 'sh加工数量';

alter table submit_report_jumei_suit_detail modify column cd_process_num int(11) comment 'cd加工数量';

alter table submit_report_jumei_suit_detail modify column gz_process_num int(11) comment 'gz加工数量';

alter table submit_report_jumei_suit_detail modify column updated_user varchar(100) comment '更新人';

alter table submit_report_jumei_suit_detail modify column updated_time datetime comment '更新时间';

alter table submit_report_jumei_suit_detail modify column suit_id int(11) comment '套装ID';

/*==============================================================*/
/* Table: submit_report_lefeng_sku                              */
/*==============================================================*/
create table submit_report_lefeng_sku
(
   id                   int(11) not null auto_increment,
   description          varchar(1000) default NULL comment '商品描述',
   upc                  varchar(24) default NULL comment '国际条形码',
   supplier_code        varchar(24) default NULL comment '供应商代码',
   supplier_name        varchar(100) default NULL comment '供应商名称',
   brand_code           int(12) default NULL comment '品牌代码',
   product_series       varchar(255) default NULL comment '商品系列',
   producing_area       varchar(24) default NULL comment '产地',
   sell_unit            varchar(10) default NULL comment '销售单位',
   product_four_category int(11) default NULL comment '商品第四级分类ID',
   store_price          decimal(11,2) default NULL comment '进货价格（含税）',
   market_price         decimal(11,2) default NULL comment '市场价格（元）',
   lefeng_sell_price    decimal(11,2) default NULL comment '乐蜂标准零售价格（元）',
   product_useful_life  int(11) default NULL comment '商品有效期（月）',
   product_property_code int(11) default NULL comment '商品属性代码',
   search_keyword       varchar(255) default NULL comment '搜索关键字',
   color                varchar(255) default NULL comment '颜色',
   size                 varchar(10) default NULL comment '尺寸',
   prohibited           varchar(10) default NULL comment '是否禁航',
   belong_depart_code   varchar(10) default NULL comment '商品所属部门代码',
   purchase_principal   varchar(10) default NULL comment '采购负责人',
   approve_reference_number varchar(100) default NULL comment '批准文号/生产许可证号',
   tax_rate             decimal(11,2) default NULL comment '税率',
   gross_profit_rate    decimal(11,2) default NULL comment '毛利率',
   estimate_sales_volume varchar(20) default NULL comment '预估销量（个/天）',
   in_number            varchar(10) default NULL comment '是否【进】字商品',
   mid_min              varchar(10) default NULL comment '是否中小样',
   dangdang_price       decimal(11,2) default NULL comment '当当价格（元）',
   jd_price             decimal(11,2) default NULL comment '京东价格（元）',
   zhuoyue_price        decimal(11,2) default NULL comment '卓越价格（元）',
   honghaizi_price      decimal(11,2) default NULL comment '红孩子价格（元）',
   other                decimal(11,2) default NULL comment '其他（元）',
   pic_num              int(11) default NULL comment '组图数量',
   detail_description   varchar(2000) default NULL comment '产品详情描述',
   product_standard     varchar(100) default NULL comment '产品规格',
   product_intro        varchar(1000) default NULL comment '产品简介',
   remark               varchar(1000) default NULL comment '备注',
   report_id            int(11) default NULL comment '提报ID',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report_lefeng_sku comment 'submit_report_lefeng_sku';

alter table submit_report_lefeng_sku modify column id int(11) comment 'id';

alter table submit_report_lefeng_sku modify column description varchar(1000) comment '商品描述';

alter table submit_report_lefeng_sku modify column upc varchar(24) comment '国际条形码';

alter table submit_report_lefeng_sku modify column supplier_code varchar(24) comment '供应商代码';

alter table submit_report_lefeng_sku modify column supplier_name varchar(100) comment '供应商名称';

alter table submit_report_lefeng_sku modify column brand_code int(12) comment '品牌代码';

alter table submit_report_lefeng_sku modify column product_series varchar(255) comment '商品系列';

alter table submit_report_lefeng_sku modify column producing_area varchar(24) comment '产地';

alter table submit_report_lefeng_sku modify column sell_unit varchar(10) comment '销售单位';

alter table submit_report_lefeng_sku modify column product_four_category int(11) comment '商品第四级分类ID';

alter table submit_report_lefeng_sku modify column store_price decimal(11,2) comment '进货价格（含税）';

alter table submit_report_lefeng_sku modify column market_price decimal(11,2) comment '市场价格（元）';

alter table submit_report_lefeng_sku modify column lefeng_sell_price decimal(11,2) comment '乐蜂标准零售价格（元）';

alter table submit_report_lefeng_sku modify column product_useful_life int(11) comment '商品有效期（月）';

alter table submit_report_lefeng_sku modify column product_property_code int(11) comment '商品属性代码';

alter table submit_report_lefeng_sku modify column search_keyword varchar(255) comment '搜索关键字';

alter table submit_report_lefeng_sku modify column color varchar(255) comment '颜色';

alter table submit_report_lefeng_sku modify column size varchar(10) comment '尺寸';

alter table submit_report_lefeng_sku modify column prohibited varchar(10) comment '是否禁航';

alter table submit_report_lefeng_sku modify column belong_depart_code varchar(10) comment '商品所属部门代码';

alter table submit_report_lefeng_sku modify column purchase_principal varchar(10) comment '采购负责人';

alter table submit_report_lefeng_sku modify column approve_reference_number varchar(100) comment '批准文号/生产许可证号';

alter table submit_report_lefeng_sku modify column tax_rate decimal(11,2) comment '税率';

alter table submit_report_lefeng_sku modify column gross_profit_rate decimal(11,2) comment '毛利率';

alter table submit_report_lefeng_sku modify column estimate_sales_volume varchar(20) comment '预估销量（个/天）';

alter table submit_report_lefeng_sku modify column in_number varchar(10) comment '是否【进】字商品';

alter table submit_report_lefeng_sku modify column mid_min varchar(10) comment '是否中小样';

alter table submit_report_lefeng_sku modify column dangdang_price decimal(11,2) comment '当当价格（元）';

alter table submit_report_lefeng_sku modify column jd_price decimal(11,2) comment '京东价格（元）';

alter table submit_report_lefeng_sku modify column zhuoyue_price decimal(11,2) comment '卓越价格（元）';

alter table submit_report_lefeng_sku modify column honghaizi_price decimal(11,2) comment '红孩子价格（元）';

alter table submit_report_lefeng_sku modify column other decimal(11,2) comment '其他（元）';

alter table submit_report_lefeng_sku modify column pic_num int(11) comment '组图数量';

alter table submit_report_lefeng_sku modify column detail_description varchar(2000) comment '产品详情描述';

alter table submit_report_lefeng_sku modify column product_standard varchar(100) comment '产品规格';

alter table submit_report_lefeng_sku modify column product_intro varchar(1000) comment '产品简介';

alter table submit_report_lefeng_sku modify column remark varchar(1000) comment '备注';

alter table submit_report_lefeng_sku modify column report_id int(11) comment '提报ID';

/*==============================================================*/
/* Table: submit_report_lefeng_suit                             */
/*==============================================================*/
create table submit_report_lefeng_suit
(
   id                   int(11) not null auto_increment,
   suit_name            varchar(100) default NULL comment '套装名称',
   updated_user         varchar(100) default NULL comment '更新人',
   role_id              int(11) default NULL comment '角色ID',
   report_id            int(11) default NULL comment '提报编码',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report_lefeng_suit comment 'submit_report_lefeng_suit';

alter table submit_report_lefeng_suit modify column id int(11) comment 'id';

alter table submit_report_lefeng_suit modify column suit_name varchar(100) comment '套装名称';

alter table submit_report_lefeng_suit modify column updated_user varchar(100) comment '更新人';

alter table submit_report_lefeng_suit modify column role_id int(11) comment '角色ID';

alter table submit_report_lefeng_suit modify column report_id int(11) comment '提报编码';

/*==============================================================*/
/* Table: submit_report_lefeng_suit_detail                      */
/*==============================================================*/
create table submit_report_lefeng_suit_detail
(
   id                   int(11) not null auto_increment,
   product_name         varchar(100) default NULL comment '商品名称',
   product_code         varchar(100) default NULL comment '商品编码',
   sku                  varchar(20) default NULL comment 'SKU',
   lefeng_price         decimal(11,2) default NULL comment '现乐蜂价',
   purchase_cost        decimal(11,2) default NULL comment '采购成本',
   gross_profit_rate    decimal(11,2) default NULL comment '原毛利率%',
   discount_price       decimal(11,2) default NULL comment '折让后售价',
   discount_rang_price  decimal(11,2) default NULL comment '单品折让金额',
   discount_gross_profit_rate decimal(11,2) default NULL comment '折让后毛利率%',
   suit_id              int(11) default NULL comment '套装编码',
   updated_user         varchar(100) default NULL comment '更新人',
   updated_time         datetime default NULL comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report_lefeng_suit_detail comment 'submit_report_lefeng_suit_detail';

alter table submit_report_lefeng_suit_detail modify column id int(11) comment 'id';

alter table submit_report_lefeng_suit_detail modify column product_name varchar(100) comment '商品名称';

alter table submit_report_lefeng_suit_detail modify column product_code varchar(100) comment '商品编码';

alter table submit_report_lefeng_suit_detail modify column sku varchar(20) comment 'SKU';

alter table submit_report_lefeng_suit_detail modify column lefeng_price decimal(11,2) comment '现乐蜂价';

alter table submit_report_lefeng_suit_detail modify column purchase_cost decimal(11,2) comment '采购成本';

alter table submit_report_lefeng_suit_detail modify column gross_profit_rate decimal(11,2) comment '原毛利率%';

alter table submit_report_lefeng_suit_detail modify column discount_price decimal(11,2) comment '折让后售价';

alter table submit_report_lefeng_suit_detail modify column discount_rang_price decimal(11,2) comment '单品折让金额';

alter table submit_report_lefeng_suit_detail modify column discount_gross_profit_rate decimal(11,2) comment '折让后毛利率%';

alter table submit_report_lefeng_suit_detail modify column suit_id int(11) comment '套装编码';

alter table submit_report_lefeng_suit_detail modify column updated_user varchar(100) comment '更新人';

alter table submit_report_lefeng_suit_detail modify column updated_time datetime comment '更新时间';

/*==============================================================*/
/* Table: submit_report_setting                                 */
/*==============================================================*/
create table submit_report_setting
(
   id                   int(11) not null auto_increment,
   platform_id          int(11) default NULL comment '平台编码',
   submit_type          varchar(10) default NULL comment '提报类型(0为单品，1为套装)',
   updated_time         datetime default NULL comment '更新时间',
   sub_name             varchar(100) default NULL comment '字表名',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report_setting comment 'submit_report_setting';

alter table submit_report_setting modify column id int(11) comment 'id';

alter table submit_report_setting modify column platform_id int(11) comment '平台编码';

alter table submit_report_setting modify column submit_type varchar(10) comment '提报类型(0为单品，1为套装)';

alter table submit_report_setting modify column updated_time datetime comment '更新时间';

alter table submit_report_setting modify column sub_name varchar(100) comment '字表名';

/*==============================================================*/
/* Table: submit_report_yhd_sku                                 */
/*==============================================================*/
create table submit_report_yhd_sku
(
   id                   int(11) not null auto_increment,
   code                 varchar(20) default NULL comment '一号店码',
   product_name         varchar(255) default NULL comment '新品名称',
   product_category     varchar(100) default NULL comment '产品类别',
   supplier_name        varchar(100) default NULL comment '供应商名称',
   brand_name           varchar(100) default NULL comment '品牌',
   market_price         decimal(11,2) default NULL comment '市场价',
   store_price          decimal(11,2) default NULL comment '进价',
   sell_price           decimal(11,2) default NULL comment '售价',
   sell_type            decimal(11,2) default NULL comment '销售类型',
   in_tax_rate          decimal(11,2) default NULL comment '进项税率',
   donation             varchar(20) default NULL comment '是否赠品',
   merchant             varchar(100) default NULL comment '商家',
   grough_weight        decimal(11,2) default NULL comment '单品毛重（公斤）',
   "long"               int(11) default NULL comment '长（CM）',
   width                int(11) default NULL comment '宽（CM）',
   high                 int(11) default NULL comment '高（CM）',
   packing              varchar(20) default NULL comment '箱规',
   bar_code             varchar(20) default NULL comment '条码（Ean-13）',
   sub_product_name     varchar(255) default NULL comment '产品基本副标题',
   standard             varchar(20) default NULL comment '规格',
   color                varchar(20) default NULL comment '颜色',
   size                 varchar(20) default NULL comment '尺码',
   producing_area       varchar(20) default NULL comment '产地（国）',
   expiration_date_manage varchar(20) default NULL comment '是否需要保质期管理',
   expiration_day       varchar(20) default NULL comment '保质天数',
   threec               varchar(20) default NULL comment '是否3C产品',
   serial_controll      varchar(20) default NULL comment '是否需要序列号控制',
   support_virtual_stock varchar(20) default NULL comment '是否支持虚拟库存',
   virtual_stock        int(11) default NULL comment '虚拟库存',
   import               varchar(20) default NULL comment '是否进口',
   output_tax           decimal(11,2) default NULL comment '销项税率',
   outsourcing          varchar(20) default NULL comment '是否外包',
   sample_back          varchar(20) default NULL comment '样品是否需返还',
   sample_back_add      varchar(255) default NULL comment '样品返还地址',
   report_id            int(11) default NULL comment '报表编码',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report_yhd_sku comment 'submit_report_yhd_sku';

alter table submit_report_yhd_sku modify column id int(11) comment 'id';

alter table submit_report_yhd_sku modify column code varchar(20) comment '一号店码';

alter table submit_report_yhd_sku modify column product_name varchar(255) comment '新品名称';

alter table submit_report_yhd_sku modify column product_category varchar(100) comment '产品类别';

alter table submit_report_yhd_sku modify column supplier_name varchar(100) comment '供应商名称';

alter table submit_report_yhd_sku modify column brand_name varchar(100) comment '品牌';

alter table submit_report_yhd_sku modify column market_price decimal(11,2) comment '市场价';

alter table submit_report_yhd_sku modify column store_price decimal(11,2) comment '进价';

alter table submit_report_yhd_sku modify column sell_price decimal(11,2) comment '售价';

alter table submit_report_yhd_sku modify column sell_type decimal(11,2) comment '销售类型';

alter table submit_report_yhd_sku modify column in_tax_rate decimal(11,2) comment '进项税率';

alter table submit_report_yhd_sku modify column donation varchar(20) comment '是否赠品';

alter table submit_report_yhd_sku modify column merchant varchar(100) comment '商家';

alter table submit_report_yhd_sku modify column grough_weight decimal(11,2) comment '单品毛重（公斤）';

alter table submit_report_yhd_sku modify column "long" int(11) comment '长（CM）';

alter table submit_report_yhd_sku modify column width int(11) comment '宽（CM）';

alter table submit_report_yhd_sku modify column high int(11) comment '高（CM）';

alter table submit_report_yhd_sku modify column packing varchar(20) comment '箱规';

alter table submit_report_yhd_sku modify column bar_code varchar(20) comment '条码（Ean-13）';

alter table submit_report_yhd_sku modify column sub_product_name varchar(255) comment '产品基本副标题';

alter table submit_report_yhd_sku modify column standard varchar(20) comment '规格';

alter table submit_report_yhd_sku modify column color varchar(20) comment '颜色';

alter table submit_report_yhd_sku modify column size varchar(20) comment '尺码';

alter table submit_report_yhd_sku modify column producing_area varchar(20) comment '产地（国）';

alter table submit_report_yhd_sku modify column expiration_date_manage varchar(20) comment '是否需要保质期管理';

alter table submit_report_yhd_sku modify column expiration_day varchar(20) comment '保质天数';

alter table submit_report_yhd_sku modify column threec varchar(20) comment '是否3C产品';

alter table submit_report_yhd_sku modify column serial_controll varchar(20) comment '是否需要序列号控制';

alter table submit_report_yhd_sku modify column support_virtual_stock varchar(20) comment '是否支持虚拟库存';

alter table submit_report_yhd_sku modify column virtual_stock int(11) comment '虚拟库存';

alter table submit_report_yhd_sku modify column import varchar(20) comment '是否进口';

alter table submit_report_yhd_sku modify column output_tax decimal(11,2) comment '销项税率';

alter table submit_report_yhd_sku modify column outsourcing varchar(20) comment '是否外包';

alter table submit_report_yhd_sku modify column sample_back varchar(20) comment '样品是否需返还';

alter table submit_report_yhd_sku modify column sample_back_add varchar(255) comment '样品返还地址';

alter table submit_report_yhd_sku modify column report_id int(11) comment '报表编码';

/*==============================================================*/
/* Table: submit_report_yixun_category                          */
/*==============================================================*/
create table submit_report_yixun_category
(
   id                   bigint(12) not null auto_increment,
   brand_name           varchar(100) default NULL comment '品牌名称',
   brand_id             int(12) default NULL comment '品牌ID',
   big_category_id      int(12) default NULL comment '大类ID',
   big_category_name    varchar(100) default NULL comment '大类名称',
   mid_category_id      int(12) default NULL comment '中类ID',
   mid_category_name    varchar(100) default NULL comment '中类名称',
   min_category_id      int(12) default NULL comment '小类ID',
   min_category_name    varchar(100) default NULL comment '小类名称',
   category_id          int(12) default NULL comment '分类ID',
   category_name        varchar(100) default NULL comment '分类名称',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report_yixun_category comment 'submit_report_yixun_category';

alter table submit_report_yixun_category modify column id bigint(12) comment 'id';

alter table submit_report_yixun_category modify column brand_name varchar(100) comment '品牌名称';

alter table submit_report_yixun_category modify column brand_id int(12) comment '品牌ID';

alter table submit_report_yixun_category modify column big_category_id int(12) comment '大类ID';

alter table submit_report_yixun_category modify column big_category_name varchar(100) comment '大类名称';

alter table submit_report_yixun_category modify column mid_category_id int(12) comment '中类ID';

alter table submit_report_yixun_category modify column mid_category_name varchar(100) comment '中类名称';

alter table submit_report_yixun_category modify column min_category_id int(12) comment '小类ID';

alter table submit_report_yixun_category modify column min_category_name varchar(100) comment '小类名称';

alter table submit_report_yixun_category modify column category_id int(12) comment '分类ID';

alter table submit_report_yixun_category modify column category_name varchar(100) comment '分类名称';

/*==============================================================*/
/* Table: submit_report_yixun_sku                               */
/*==============================================================*/
create table submit_report_yixun_sku
(
   id                   int(11) not null auto_increment,
   product_name         varchar(1000) default NULL comment '商品名称',
   model                varchar(255) default NULL comment '型号',
   product_style        varchar(20) default NULL comment '商品类型',
   brand_name           varchar(255) default NULL comment '品牌',
   brand_sysno          varchar(255) default NULL comment '品牌SysNo',
   big_category         varchar(255) default NULL comment '大类',
   big_category_sysno   varchar(255) default NULL comment '大类SysNo',
   mid_category         varchar(255) default NULL comment '中类',
   mid_category_sysno   varchar(255) default NULL comment '中类SysNo',
   min_category         varchar(255) default NULL comment '小类',
   min_category_sysno   varchar(255) default NULL comment '小类SysNo',
   tengxun_product_property varchar(255) default NULL comment '腾讯品类属性',
   tengxun_product_id   varchar(20) default NULL comment '腾讯品类ID',
   purchasing_specialist_no varchar(20) default NULL comment '采购专员工号',
   purchasing_specialist varchar(20) default NULL comment '采购专员',
   purchasing_seller    varchar(20) default NULL comment '采销专员',
   purchasing_seller_no varchar(20) default NULL comment '采销专员工号',
   seller_no            varchar(20) default NULL comment '销售专员工号',
   seller               varchar(20) default NULL comment '销售专员',
   information_no       varchar(20) default NULL comment '资料专员工号',
   information          varchar(20) default NULL comment '资料专员',
   sell_mode            varchar(20) default NULL comment '销售模式',
   standard_price       varchar(20) default NULL comment '基础价格',
   length               int(11) default NULL comment '长(cm)',
   width                int(11) default NULL comment '宽(cm)',
   height               int(11) default NULL comment '高(cm)',
   gross_weight         int(11) default NULL comment '毛重(g)',
   suttle               int(11) default NULL comment '净重(g)',
   upc                  varchar(20) default NULL comment '商品PN码',
   num_box              int(11) default NULL comment '箱入数',
   delivery_package     varchar(20) default NULL comment '配送包材',
   supplier_no          varchar(20) default NULL comment '供应商编号',
   supplier_name        varchar(20) default NULL comment '供应商名称',
   sell_unit            varchar(20) default NULL comment '销售单位',
   sell_standard        varchar(20) default NULL comment '销售规格',
   expiration_date_manage varchar(20) default NULL comment '保质期管理',
   expiration_date_day  int(11) default NULL comment '保质期天数',
   import_commodity     varchar(20) default NULL comment '是否进口商品',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table submit_report_yixun_sku comment 'submit_report_yixun_sku';

alter table submit_report_yixun_sku modify column id int(11) comment 'id';

alter table submit_report_yixun_sku modify column product_name varchar(1000) comment '商品名称';

alter table submit_report_yixun_sku modify column model varchar(255) comment '型号';

alter table submit_report_yixun_sku modify column product_style varchar(20) comment '商品类型';

alter table submit_report_yixun_sku modify column brand_name varchar(255) comment '品牌';

alter table submit_report_yixun_sku modify column brand_sysno varchar(255) comment '品牌SysNo';

alter table submit_report_yixun_sku modify column big_category varchar(255) comment '大类';

alter table submit_report_yixun_sku modify column big_category_sysno varchar(255) comment '大类SysNo';

alter table submit_report_yixun_sku modify column mid_category varchar(255) comment '中类';

alter table submit_report_yixun_sku modify column mid_category_sysno varchar(255) comment '中类SysNo';

alter table submit_report_yixun_sku modify column min_category varchar(255) comment '小类';

alter table submit_report_yixun_sku modify column min_category_sysno varchar(255) comment '小类SysNo';

alter table submit_report_yixun_sku modify column tengxun_product_property varchar(255) comment '腾讯品类属性';

alter table submit_report_yixun_sku modify column tengxun_product_id varchar(20) comment '腾讯品类ID';

alter table submit_report_yixun_sku modify column purchasing_specialist_no varchar(20) comment '采购专员工号';

alter table submit_report_yixun_sku modify column purchasing_specialist varchar(20) comment '采购专员';

alter table submit_report_yixun_sku modify column purchasing_seller varchar(20) comment '采销专员';

alter table submit_report_yixun_sku modify column purchasing_seller_no varchar(20) comment '采销专员工号';

alter table submit_report_yixun_sku modify column seller_no varchar(20) comment '销售专员工号';

alter table submit_report_yixun_sku modify column seller varchar(20) comment '销售专员';

alter table submit_report_yixun_sku modify column information_no varchar(20) comment '资料专员工号';

alter table submit_report_yixun_sku modify column information varchar(20) comment '资料专员';

alter table submit_report_yixun_sku modify column sell_mode varchar(20) comment '销售模式';

alter table submit_report_yixun_sku modify column standard_price varchar(20) comment '基础价格';

alter table submit_report_yixun_sku modify column length int(11) comment '长(cm)';

alter table submit_report_yixun_sku modify column width int(11) comment '宽(cm)';

alter table submit_report_yixun_sku modify column height int(11) comment '高(cm)';

alter table submit_report_yixun_sku modify column gross_weight int(11) comment '毛重(g)';

alter table submit_report_yixun_sku modify column suttle int(11) comment '净重(g)';

alter table submit_report_yixun_sku modify column upc varchar(20) comment '商品PN码';

alter table submit_report_yixun_sku modify column num_box int(11) comment '箱入数';

alter table submit_report_yixun_sku modify column delivery_package varchar(20) comment '配送包材';

alter table submit_report_yixun_sku modify column supplier_no varchar(20) comment '供应商编号';

alter table submit_report_yixun_sku modify column supplier_name varchar(20) comment '供应商名称';

alter table submit_report_yixun_sku modify column sell_unit varchar(20) comment '销售单位';

alter table submit_report_yixun_sku modify column sell_standard varchar(20) comment '销售规格';

alter table submit_report_yixun_sku modify column expiration_date_manage varchar(20) comment '保质期管理';

alter table submit_report_yixun_sku modify column expiration_date_day int(11) comment '保质期天数';

alter table submit_report_yixun_sku modify column import_commodity varchar(20) comment '是否进口商品';

/*==============================================================*/
/* Table: suitInfo                                              */
/*==============================================================*/
create table suitInfo
(
   id                   int not null auto_increment,
   suit_name            varchar(255),
   suit_platform_id     varchar(255),
   suit_id              varchar(255),
   sales_bar_code       varchar(255),
   suit_price           float(11,2),
   suit_category        varchar(255),
   suit_series          varchar(255),
   lowest_suit_price    float(11,2),
   suit_efficacy        varchar(255),
   suit_classify        varchar(100),
   create_time          varchar(100),
   remarks              varchar(255),
   organization_id      integer,
   updatetime           varchar(100),
   reserve1             varchar(100),
   reserve2             varchar(100),
   reserve3             varchar(100),
   primary key (id),
   unique key AK_Key_2 (suit_id, organization_id)
);

alter table suitInfo comment '套装信息表';

alter table suitInfo modify column id int comment '序列';

alter table suitInfo modify column suit_name varchar(255) comment '套装名称';

alter table suitInfo modify column suit_platform_id varchar(255) comment '套装平台编码';

alter table suitInfo modify column suit_id varchar(255) comment '套装编码';

alter table suitInfo modify column sales_bar_code varchar(255) comment '套装条形码';

alter table suitInfo modify column suit_price float(11,2) comment '套装售价';

alter table suitInfo modify column suit_category varchar(255) comment '套装品类';

alter table suitInfo modify column suit_series varchar(255) comment '套装系列';

alter table suitInfo modify column lowest_suit_price float(11,2) comment '最低售价';

alter table suitInfo modify column suit_efficacy varchar(255) comment '套装功效分类';

alter table suitInfo modify column suit_classify varchar(100) comment '套装分类';

alter table suitInfo modify column create_time varchar(100) comment '套装创建时间';

alter table suitInfo modify column remarks varchar(255) comment '备注';

alter table suitInfo modify column organization_id integer comment '所属组织';

alter table suitInfo modify column updatetime varchar(100) comment '数据更新时间';

alter table suitInfo modify column reserve1 varchar(100) comment '预留字段1';

alter table suitInfo modify column reserve2 varchar(100) comment '预留字段2';

alter table suitInfo modify column reserve3 varchar(100) comment '预留字段3';

/*==============================================================*/
/* Table: suitdetails                                           */
/*==============================================================*/
create table suitdetails
(
   id                   int not null auto_increment,
   suit_id              varchar(255) not null,
   platform_name        varchar(100),
   platform_product_id  varchar(100),
   sku                  varchar(100),
   material             varchar(100),
   product_name         varchar(255),
   price                float(11,2),
   product_number       integer,
   remarks              varchar(255),
   updatetime           varchar(100),
   reserve1             varchar(100),
   reserve2             varchar(100),
   reserve3             varchar(100),
   organization_id      integer,
   primary key (id)
);

alter table suitdetails comment '套装详情表';

alter table suitdetails modify column id int comment '序列';

alter table suitdetails modify column suit_id varchar(255) comment '套装编码';

alter table suitdetails modify column platform_name varchar(100) comment '平台名称';

alter table suitdetails modify column platform_product_id varchar(100) comment '平台产品ID';

alter table suitdetails modify column sku varchar(100) comment 'SKU编码';

alter table suitdetails modify column material varchar(100) comment '物料编码';

alter table suitdetails modify column product_name varchar(255) comment '单品名称';

alter table suitdetails modify column price float(11,2) comment '单品售价';

alter table suitdetails modify column product_number integer comment '单品数量';

alter table suitdetails modify column remarks varchar(255) comment '备注';

alter table suitdetails modify column updatetime varchar(100) comment '数据更新时间';

alter table suitdetails modify column reserve1 varchar(100) comment '预留字段1';

alter table suitdetails modify column reserve2 varchar(100) comment '预留字段2';

alter table suitdetails modify column reserve3 varchar(100) comment '预留字段3';

alter table suitdetails modify column organization_id integer comment '所属组织';

/*==============================================================*/
/* Table: suitdetails_bak                                       */
/*==============================================================*/
create table suitdetails_bak
(
   id                   int not null auto_increment,
   suitid               varchar(255) not null,
   merchant_number      varchar(100),
   platform_name        varchar(100),
   platform_product_id  varchar(100),
   sku                  varchar(100),
   material             varchar(100),
   product_name         varchar(255),
   union_type           varchar(100),
   split_number         integer,
   totail_inventory     integer,
   split_inventory      integer,
   suit_inventory       integer,
   then_sales_count     integer,
   price                float(11,2),
   sales_amount         float(11,2),
   sales_count          integer,
   suggestions_number   integer,
   discount_price       integer,
   finally_number       integer,
   remarks              varchar(255),
   updatetime           varchar(100),
   reserve1             varchar(100),
   reserve2             varchar(100),
   reserve3             varchar(100),
   primary key (id)
);

alter table suitdetails_bak comment '套装详情表_备份';

alter table suitdetails_bak modify column id int comment '序列';

alter table suitdetails_bak modify column suitid varchar(255) comment '套装编码';

alter table suitdetails_bak modify column merchant_number varchar(100) comment '商家编码';

alter table suitdetails_bak modify column platform_name varchar(100) comment '平台名称';

alter table suitdetails_bak modify column platform_product_id varchar(100) comment '平台产品ID';

alter table suitdetails_bak modify column sku varchar(100) comment 'SKU编码';

alter table suitdetails_bak modify column material varchar(100) comment '品牌商编';

alter table suitdetails_bak modify column product_name varchar(255) comment '单品名称';

alter table suitdetails_bak modify column union_type varchar(100) comment '组合类型';

alter table suitdetails_bak modify column split_number integer comment '拆分数量';

alter table suitdetails_bak modify column totail_inventory integer comment '总库存';

alter table suitdetails_bak modify column split_inventory integer comment '拆分库存';

alter table suitdetails_bak modify column suit_inventory integer comment '套装库存';

alter table suitdetails_bak modify column then_sales_count integer comment '已销售量';

alter table suitdetails_bak modify column price float(11,2) comment '单品售价';

alter table suitdetails_bak modify column sales_amount float(11,2) comment '销售金额';

alter table suitdetails_bak modify column sales_count integer comment '单品销量';

alter table suitdetails_bak modify column suggestions_number integer comment '建议上架数';

alter table suitdetails_bak modify column discount_price integer comment '折扣价';

alter table suitdetails_bak modify column finally_number integer comment '最终上架数';

alter table suitdetails_bak modify column remarks varchar(255) comment '备注';

alter table suitdetails_bak modify column updatetime varchar(100) comment '数据更新时间';

alter table suitdetails_bak modify column reserve1 varchar(100) comment '预留字段1';

alter table suitdetails_bak modify column reserve2 varchar(100) comment '预留字段2';

alter table suitdetails_bak modify column reserve3 varchar(100) comment '预留字段3';

