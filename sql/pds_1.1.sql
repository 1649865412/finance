/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014-6-11 10:21:23                           */
/*==============================================================*/


drop table if exists dealerManage;

drop table if exists dealerPlatformInfo;

drop table if exists inventoryInfo;

drop table if exists orderInfo;

drop table if exists orderItemsDetails;

drop table if exists ordertemp;

drop table if exists platformInfo;

drop table if exists productinfo;

drop table if exists promotion;

drop table if exists promotionDetails;

drop table if exists salesDetails;

drop table if exists storeMangeInfo;

drop table if exists storePlatformInfo;

drop table if exists suitInfo;

drop table if exists suitdetails;

/*==============================================================*/
/* Table: dealerManage                                          */
/*==============================================================*/
create table dealerManage
(
   id                   int not null auto_increment,
   dealer_name          varchar(255),
   dealer_number        varchar(255),
   category             varchar(100),
   ratio                int,
   remarks              varchar(1000),
   updatetime           varchar(100),
   primary key (id)
);

alter table dealerManage comment '经销商信息表';

alter table dealerManage modify column id int comment '序列';

alter table dealerManage modify column dealer_name varchar(255) comment '经销商名称';

alter table dealerManage modify column dealer_number varchar(255) comment '经销商编号';

alter table dealerManage modify column category varchar(100) comment '品类';

alter table dealerManage modify column ratio int comment '比例';

alter table dealerManage modify column remarks varchar(1000) comment '备注';

alter table dealerManage modify column updatetime varchar(100) comment '数据更新时间';

/*==============================================================*/
/* Table: dealerPlatformInfo                                    */
/*==============================================================*/
create table dealerPlatformInfo
(
   id                   int not null auto_increment,
   dealer_number        varchar(255),
   platform_id          varchar(255),
   remarks              varchar(1000),
   updatetime           varchar(100),
   primary key (id)
);

alter table dealerPlatformInfo comment '经销商与平台对应表';

alter table dealerPlatformInfo modify column id int comment '序列';

alter table dealerPlatformInfo modify column dealer_number varchar(255) comment '经销商编号';

alter table dealerPlatformInfo modify column platform_id varchar(255) comment '平台编号';

alter table dealerPlatformInfo modify column remarks varchar(1000) comment '备注';

alter table dealerPlatformInfo modify column updatetime varchar(100) comment '数据更新时间';

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
   rebates_integral      int,
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
/* Table: productinfo                                           */
/*==============================================================*/
create table productinfo
(
   id                   int not null auto_increment,
   sku                  varchar(100),
   batch_number         varchar(100),
   material_number      varchar(100),
   brand                varchar(100),
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
   updatetime           varchar(100),
   classification1      varchar(255),
   classification2      varchar(255),
   classification3      varchar(255),
   classification4      varchar(255),
   primary key (id),
   unique key AK_Key_2 (sku)
);

alter table productinfo comment '商品信息表';

alter table productinfo modify column id int comment '序列';

alter table productinfo modify column sku varchar(100) comment 'SKU编号';

alter table productinfo modify column batch_number varchar(100) comment '批次号';

alter table productinfo modify column material_number varchar(100) comment '物料编号';

alter table productinfo modify column brand varchar(100) comment '品牌';

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

alter table salesDetails modify column updatetime varchar(100) comment '数据更新时间';

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
   primary key (id)
);

alter table storeMangeInfo comment '仓库信息表';

alter table storeMangeInfo modify column id int comment '序列';

alter table storeMangeInfo modify column store_name varchar(255) comment '仓库名称';

alter table storeMangeInfo modify column store_id varchar(100) comment '仓库编码';

alter table storeMangeInfo modify column remarks varchar(1000) comment '备注';

alter table storeMangeInfo modify column updatetime varchar(100) comment '数据更新时间';

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
   primary key (id)
);

alter table storePlatformInfo comment '平台与仓库对应表';

alter table storePlatformInfo modify column id int comment '序列';

alter table storePlatformInfo modify column store_id varchar(100) comment '仓库编码';

alter table storePlatformInfo modify column platform_id varchar(100) comment '平台编码';

alter table storePlatformInfo modify column remarks varchar(1000) comment '备注';

alter table storePlatformInfo modify column updatetime varchar(100) comment '数据更新时间';

/*==============================================================*/
/* Table: suitInfo                                              */
/*==============================================================*/
create table suitInfo
(
   id                   int not null auto_increment,
   product_id           varchar(255),
   suit_name            varchar(255) not null,
   sale_count           int,
   sale_money           float(11,2),
   original_price       float(11,2),
   markting_price       float(11,2),
   suitid               varchar(255) not null,
   updatetime           varchar(100),
   primary key (id),
   unique key AK_Key_2 (suitid)
);

alter table suitInfo comment '套装信息表';

alter table suitInfo modify column id int comment '序列';

alter table suitInfo modify column product_id varchar(255) comment '商品编号';

alter table suitInfo modify column suit_name varchar(255) comment '套装名称';

alter table suitInfo modify column sale_count int comment '销售件数';

alter table suitInfo modify column sale_money float(11,2) comment '销售额';

alter table suitInfo modify column original_price float(11,2) comment '套装原价';

alter table suitInfo modify column markting_price float(11,2) comment '套装促销价';

alter table suitInfo modify column suitid varchar(255) comment '套装编码';

alter table suitInfo modify column updatetime varchar(100) comment '数据更新时间';

/*==============================================================*/
/* Table: suitdetails                                           */
/*==============================================================*/
create table suitdetails
(
   id                   int not null auto_increment,
   suitid               varchar(255) not null,
   sku                  varchar(100),
   sales_code           varchar(100),
   product_name         varchar(255),
   price                float(11,2),
   nature               varchar(100),
   discount_price       float(11,2),
   sales_count          int,
   updatetime           varchar(100),
   primary key (id)
);

alter table suitdetails comment '套装详情表';

alter table suitdetails modify column id int comment '序列';

alter table suitdetails modify column suitid varchar(255) comment '套装编码';

alter table suitdetails modify column sku varchar(100) comment 'SKU编码';

alter table suitdetails modify column sales_code varchar(100) comment '销售条码';

alter table suitdetails modify column product_name varchar(255) comment '单品名称';

alter table suitdetails modify column price float(11,2) comment '售价';

alter table suitdetails modify column nature varchar(100) comment '性质';

alter table suitdetails modify column discount_price float(11,2) comment '折扣价';

alter table suitdetails modify column sales_count int comment '套装内单品销量';

alter table suitdetails modify column updatetime varchar(100) comment '数据更新时间';

