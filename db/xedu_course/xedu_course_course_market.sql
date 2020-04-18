create table course_market
(
    id         varchar(32)                         not null comment '课程id'
        primary key,
    charge     varchar(32)                         not null comment '收费规则，对应数据字典',
    valid      varchar(32)                         not null comment '有效性，对应数据字典',
    expires    timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '过期时间',
    qq         varchar(32)                         null comment '咨询qq',
    price      float(10, 2)                        null comment '价格',
    price_old  float(10, 2)                        null comment '原价',
    start_time datetime                            null comment '课程有效期-开始时间',
    end_time   datetime                            null comment '课程有效期-结束时间'
)
    charset = utf8;

INSERT INTO xedu_course.course_market (id, charge, valid, expires, qq, price, price_old, start_time, end_time) VALUES ('297e7c7c62b888f00162b8a7dec20000', '203002', '204002', '2018-05-28 19:03:54', '32432', 0.01, null, null, null);
INSERT INTO xedu_course.course_market (id, charge, valid, expires, qq, price, price_old, start_time, end_time) VALUES ('297e7c7c62b888f00162b8a965510001', '203001', '204001', '2018-05-28 19:03:56', '443242', 0.01, null, null, null);
INSERT INTO xedu_course.course_market (id, charge, valid, expires, qq, price, price_old, start_time, end_time) VALUES ('297e7c7c62b8aa9d0162b8ab13910000', '203001', '204001', '2018-05-28 19:03:57', null, 0.01, null, null, null);
INSERT INTO xedu_course.course_market (id, charge, valid, expires, qq, price, price_old, start_time, end_time) VALUES ('402847eb71205ec501712061978c0001', '203002', '204001', '2020-04-18 13:40:03', '12345677', 400, null, null, null);
INSERT INTO xedu_course.course_market (id, charge, valid, expires, qq, price, price_old, start_time, end_time) VALUES ('402847eb71205ec50171208a3b660002', '203001', '204001', '2020-04-18 07:45:13', null, null, null, null, null);
INSERT INTO xedu_course.course_market (id, charge, valid, expires, qq, price, price_old, start_time, end_time) VALUES ('402847eb71208f46017120901ec60000', '203002', '204001', '2020-04-18 10:00:08', null, 20, null, null, null);
INSERT INTO xedu_course.course_market (id, charge, valid, expires, qq, price, price_old, start_time, end_time) VALUES ('402847eb71208f46017120964f5e0003', '203002', '204001', '2020-03-28 12:55:48', '123456789', 201, null, null, null);
INSERT INTO xedu_course.course_market (id, charge, valid, expires, qq, price, price_old, start_time, end_time) VALUES ('402885816243d2dd016243f24c030002', '203002', '204002', '2018-06-01 23:40:39', null, 0.01, 199, '2018-04-01 10:50:31', '2020-04-01 00:00:00');
INSERT INTO xedu_course.course_market (id, charge, valid, expires, qq, price, price_old, start_time, end_time) VALUES ('4028858162e0bc0a0162e0bfdf1a0000', '203002', '204002', '2018-05-28 19:03:52', '45323453', 0.01, null, null, null);
INSERT INTO xedu_course.course_market (id, charge, valid, expires, qq, price, price_old, start_time, end_time) VALUES ('4028e581617f945f01617f9dabc40000', '203002', '204001', '2018-04-05 10:50:48', '4455432', 0.01, null, '2018-04-01 10:50:31', '2020-04-01 10:50:37');
INSERT INTO xedu_course.course_market (id, charge, valid, expires, qq, price, price_old, start_time, end_time) VALUES ('4028e58161bcf7f40161bcf8b77c0000', '203002', '204002', '2018-04-05 10:50:53', '54354353', 0.01, null, '2018-04-01 10:50:31', '2020-04-01 10:50:37');
INSERT INTO xedu_course.course_market (id, charge, valid, expires, qq, price, price_old, start_time, end_time) VALUES ('4028e58161bd22e60161bd23672a0001', '203001', '204002', '2018-06-02 00:00:22', '4324322', 0.01, null, '2018-04-01 10:50:31', '2020-04-01 10:50:37');
INSERT INTO xedu_course.course_market (id, charge, valid, expires, qq, price, price_old, start_time, end_time) VALUES ('4028e58161bd3b380161bd3bcd2f0000', '203002', '204001', '2018-04-05 10:50:55', '32432432', 0.01, null, '2018-04-01 10:50:31', '2020-04-01 10:50:37');