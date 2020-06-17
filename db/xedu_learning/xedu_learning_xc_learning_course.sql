create table xc_learning_course
(
    id         varchar(32) not null
        primary key,
    course_id  varchar(32) not null comment '课程id',
    user_id    varchar(32) not null comment '用户id',
    charge     varchar(32) null comment '收费规则',
    price      float(8, 2) null comment '课程价格',
    valid      varchar(32) null comment '有效性',
    start_time datetime    null,
    end_time   datetime    null,
    status     varchar(32) null comment '选课状态',
    constraint xc_learning_list_unique
        unique (course_id, user_id)
);

INSERT INTO xedu_learning.xc_learning_course (id, course_id, user_id, charge, price, valid, start_time, end_time, status) VALUES ('40288581629123300162912af5630001', '4028e581617f945f01617f9dabc40000', '49', null, 0.01, null, null, null, '501001');
INSERT INTO xedu_learning.xc_learning_course (id, course_id, user_id, charge, price, valid, start_time, end_time, status) VALUES ('4028858162936228016295b613650000', '4028e58161bcf7f40161bcf8b77c0000', '49', null, 0.01, null, '2018-04-01 10:50:31', '2020-04-01 10:50:37', '501001');
INSERT INTO xedu_learning.xc_learning_course (id, course_id, user_id, charge, price, valid, start_time, end_time, status) VALUES ('402885816298a126016298ac09a10001', '4028e58161bd22e60161bd23672a0001', '49', null, null, null, '2018-04-01 10:50:31', '2020-04-01 10:50:37', '501001');
INSERT INTO xedu_learning.xc_learning_course (id, course_id, user_id, charge, price, valid, start_time, end_time, status) VALUES ('8a9b588b72c23d250172c23e7d140000', '49', '4028e581617f945f01617f9dabc40000', null, null, null, null, null, '501001');