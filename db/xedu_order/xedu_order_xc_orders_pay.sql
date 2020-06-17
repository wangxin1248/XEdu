create table xc_orders_pay
(
    id           varchar(32) not null
        primary key,
    order_number varchar(32) not null comment '订单号',
    pay_number   varchar(32) null comment '支付系统订单号',
    status       varchar(32) not null comment '交易状态',
    constraint xc_orders_pay_order_number_unique
        unique (order_number),
    constraint xc_orders_pay_pay_number_unique
        unique (pay_number)
);

INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('297e02f76397e0d7016397e11ec00001', '317320500991102976', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('297e02f76397e0d7016397e14b190003', '317320549372399616', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('297e02f76397e0d7016397f5ed510005', '317326221119983616', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('297e02f7639ae4fc01639ae54fac0001', '317532756458737664', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('297e02f7639ae4fc01639ae57c940003', '317532808245809152', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('297e02f7639ae4fc01639ae5c9440005', '317532890600968192', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('297e02f7639ae4fc01639bac6aeb0007', '317587489890373632', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('297e02f7639ae4fc01639bb66c500009', '317590240250695680', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('297e02f7639ae4fc01639bdd75d1000b', '317600970689613824', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('297e02f7639c1a3c01639c95149f0001', '317651443140399104', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('297e02f7639c1a3c01639c9520db0003', '317651457044516864', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('297e02f763a6634d0163a66d529f0001', '318344201396162560', null, '402002');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('402881e663bbbf210163bbe8cf160001', '319855888196571136', null, '402002');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('402881e663bc11970163bc12a2740001', '319867386222481408', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('402881e663bc11970163bc12b2370003', '319867403872112640', null, '402002');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('40288581627cdb0e01627ce2f8ad0009', '297406656009342976', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('4028858162863b6d0162864231b60001', '298066138997592064', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('4028858162940c510162940de2150001', '299036931059486720', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('4028858162944c9801629535db3f0001', '299118286820741120', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('4028858162944c980162953676d80003', '299118455888941056', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('402885816295920a0162959464670001', '299144273360982016', null, '402002');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('402885816295be9901629668afa30001', '299202627802370048', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('402885816295be99016296bf1df10003', '299226261577142272', null, '402001');
INSERT INTO xedu_order.xc_orders_pay (id, order_number, pay_number, status) VALUES ('402885816295be99016296bf85b70005', '299226499146715136', null, '402001');