create table xc_user
(
    id          varchar(32)  not null
        primary key,
    username    varchar(45)  not null,
    password    varchar(96)  not null,
    salt        varchar(45)  null,
    name        varchar(45)  not null,
    userpic     varchar(255) null comment '头像',
    utype       varchar(32)  not null,
    birthday    datetime     null,
    sex         char         null,
    email       varchar(45)  null,
    phone       varchar(45)  null,
    qq          varchar(32)  null,
    status      varchar(32)  not null comment '用户状态',
    create_time datetime     not null,
    update_time datetime     null,
    constraint unique_user_username
        unique (username)
);

INSERT INTO xedu_user.xc_user (id, username, password, salt, name, userpic, utype, birthday, sex, email, phone, qq, status, create_time, update_time) VALUES ('46', 'super', '$2a$10$TJ4TmCdK.X4wv/tCqHW14.w70U3CC33CeVncD3SLmyMXMknstqKRe', null, '超级管理员', null, '101003', null, '1', null, null, null, '1', '2018-03-07 16:27:47', null);
INSERT INTO xedu_user.xc_user (id, username, password, salt, name, userpic, utype, birthday, sex, email, phone, qq, status, create_time, update_time) VALUES ('48', 'admin', '$2a$10$TJ4TmCdK.X4wv/tCqHW14.w70U3CC33CeVncD3SLmyMXMknstqKRe', null, '系统管理员', null, '101003', null, '1', null, null, null, '1', '2018-03-07 16:27:47', null);
INSERT INTO xedu_user.xc_user (id, username, password, salt, name, userpic, utype, birthday, sex, email, phone, qq, status, create_time, update_time) VALUES ('49', 'itcast', '$2a$10$TJ4TmCdK.X4wv/tCqHW14.w70U3CC33CeVncD3SLmyMXMknstqKRe', null, 'test02', null, '101002', null, '1', '', '12345', null, '1', '2018-03-07 16:27:47', null);
INSERT INTO xedu_user.xc_user (id, username, password, salt, name, userpic, utype, birthday, sex, email, phone, qq, status, create_time, update_time) VALUES ('50', 'stu1', '$2a$10$pLtt2KDAFpwTWLjNsmTEi.oU1yOZyIn9XkziK/y/spH5rftCpUMZa', null, '学生1', null, '101001', null, '1', null, null, null, '1', '2018-03-07 16:27:47', null);
INSERT INTO xedu_user.xc_user (id, username, password, salt, name, userpic, utype, birthday, sex, email, phone, qq, status, create_time, update_time) VALUES ('51', 'stu2', '$2a$10$nxPKkYSez7uz2YQYUnwhR.z57km3yqKn3Hr/p1FR6ZKgc18u.Tvqm', null, '学生2', null, '101001', null, '1', null, null, null, '1', '2018-03-07 16:27:47', null);
INSERT INTO xedu_user.xc_user (id, username, password, salt, name, userpic, utype, birthday, sex, email, phone, qq, status, create_time, update_time) VALUES ('52', 't1', '$2a$10$TJ4TmCdK.X4wv/tCqHW14.w70U3CC33CeVncD3SLmyMXMknstqKRe', null, '老师1', null, '101002', null, '1', null, null, null, '', '2018-03-07 16:27:47', null);