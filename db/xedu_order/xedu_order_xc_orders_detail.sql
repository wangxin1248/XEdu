create table xc_orders_detail
(
    id           varchar(32) not null
        primary key,
    order_number varchar(32) not null comment '订单号',
    item_id      varchar(32) not null comment '商品id',
    item_num     int(8)      not null comment '商品数量',
    item_price   float(8, 2) not null comment '金额',
    valid        varchar(32) not null comment '课程有效性',
    start_time   datetime    not null comment '课程开始时间',
    end_time     datetime    not null comment '课程结束时间',
    constraint xc_orders_detail_unique
        unique (order_number, item_id),
    constraint fk_xc_orders_detail_order_number
        foreign key (order_number) references xc_orders (order_number)
);

INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('297e02f76397e0d7016397e11eba0000', '317320500991102976', '4028e58161bd22e60161bd23672a0001', 1, 0.01, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('297e02f76397e0d7016397e14b190002', '317320549372399616', '4028e58161bd22e60161bd23672a0001', 1, 0.01, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('297e02f76397e0d7016397f5ed510004', '317326221119983616', '402885816243d2dd016243f24c030002', 1, 0.01, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('297e02f7639ae4fc01639ae54fa70000', '317532756458737664', '4028e58161bd22e60161bd23672a0001', 1, 0.01, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('297e02f7639ae4fc01639ae57c940002', '317532808245809152', '4028e58161bd22e60161bd23672a0001', 1, 0.01, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('297e02f7639ae4fc01639ae5c9440004', '317532890600968192', '4028e58161bd22e60161bd23672a0001', 1, 0.01, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('297e02f7639ae4fc01639bac6ae90006', '317587489890373632', '402885816243d2dd016243f24c030002', 1, 99, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('297e02f7639ae4fc01639bb66c500008', '317590240250695680', '402885816243d2dd016243f24c030002', 1, 99, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('297e02f7639ae4fc01639bdd75d1000a', '317600970689613824', '402885816243d2dd016243f24c030002', 1, 99, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('297e02f7639c1a3c01639c95149c0000', '317651443140399104', '402885816243d2dd016243f24c030002', 1, 99, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('297e02f7639c1a3c01639c9520db0002', '317651457044516864', '402885816243d2dd016243f24c030002', 1, 99, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('297e02f763a6634d0163a66d529b0000', '318344201396162560', '402885816243d2dd016243f24c030002', 1, 0.01, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('402881e663bbbf210163bbe8cf120000', '319855888196571136', '4028e58161bd22e60161bd23672a0001', 1, 0.01, '', '2018-04-01 10:50:31', '2020-04-01 10:50:37');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('402881e663bc11970163bc12a26e0000', '319867386222481408', '4028e58161bd22e60161bd23672a0001', 1, 0.01, '204001', '2018-04-01 10:50:31', '2020-04-01 10:50:37');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('402881e663bc11970163bc12b2370002', '319867403872112640', '4028e58161bd22e60161bd23672a0001', 1, 0.01, '204001', '2018-04-01 10:50:31', '2020-04-01 10:50:37');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('4028858162940c510162940de2100000', '299036931059486720', '4028e581617f945f01617f9dabc40000', 1, 0.01, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('4028858162944c9801629535db390000', '299118286820741120', '4028e58161bd22e60161bd23672a0001', 1, 0.01, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('4028858162944c980162953676d80002', '299118455888941056', '4028e58161bd22e60161bd23672a0001', 1, 0.01, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('402885816295920a0162959464640000', '299144273360982016', '4028e58161bcf7f40161bcf8b77c0000', 1, 0.01, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('402885816295be9901629668af9c0000', '299202627802370048', '4028e58161bd22e60161bd23672a0001', 1, 0.01, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('402885816295be99016296bf1df10002', '299226261577142272', '4028e58161bd3b380161bd3bcd2f0000', 1, 0.01, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');
INSERT INTO xedu_order.xc_orders_detail (id, order_number, item_id, item_num, item_price, valid, start_time, end_time) VALUES ('402885816295be99016296bf85b70004', '299226499146715136', '4028e58161bd3b380161bd3bcd2f0000', 1, 0.01, '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');