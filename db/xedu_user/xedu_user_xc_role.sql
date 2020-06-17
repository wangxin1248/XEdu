create table xc_role
(
    id          varchar(32)  not null
        primary key,
    role_name   varchar(255) null,
    role_code   varchar(255) null,
    description varchar(255) null,
    create_time datetime     null,
    update_time datetime     null,
    status      char         not null,
    constraint unique_role_name
        unique (role_name),
    constraint unique_role_value
        unique (role_code)
);

INSERT INTO xedu_user.xc_role (id, role_name, role_code, description, create_time, update_time, status) VALUES ('17', '学生', 'student', null, '2018-03-19 15:07:13', '2018-03-19 15:07:13', '1');
INSERT INTO xedu_user.xc_role (id, role_name, role_code, description, create_time, update_time, status) VALUES ('18', '老师', 'teacher', null, '2018-03-19 15:07:13', '2018-03-19 15:07:13', '1');
INSERT INTO xedu_user.xc_role (id, role_name, role_code, description, create_time, update_time, status) VALUES ('20', '教学管理员', 'teachmanager', null, '2018-03-19 15:07:13', '2018-03-19 15:07:13', '1');
INSERT INTO xedu_user.xc_role (id, role_name, role_code, description, create_time, update_time, status) VALUES ('6', '管理员', 'admin', null, '2018-03-19 15:07:13', '2018-03-19 15:07:13', '1');
INSERT INTO xedu_user.xc_role (id, role_name, role_code, description, create_time, update_time, status) VALUES ('8', '超级管理员', 'super', null, '2018-03-19 15:07:13', '2018-03-19 15:07:13', '1');