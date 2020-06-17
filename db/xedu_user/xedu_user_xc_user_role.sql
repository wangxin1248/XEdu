create table xc_user_role
(
    id          varchar(32)  not null
        primary key,
    user_id     varchar(32)  null,
    role_id     varchar(32)  null,
    create_time datetime     null,
    creator     varchar(255) null,
    constraint fk_xc_user_role_role_id
        foreign key (role_id) references xc_role (id),
    constraint fk_xc_user_role_user_id
        foreign key (user_id) references xc_user (id)
);

INSERT INTO xedu_user.xc_user_role (id, user_id, role_id, create_time, creator) VALUES ('1', '46', '8', '2017-09-11 13:02:45', '超级管理员');
INSERT INTO xedu_user.xc_user_role (id, user_id, role_id, create_time, creator) VALUES ('19', '50', '6', '2017-09-12 14:20:20', '超级管理员');
INSERT INTO xedu_user.xc_user_role (id, user_id, role_id, create_time, creator) VALUES ('2', '48', '6', '2017-09-11 13:02:56', '超级管理员');
INSERT INTO xedu_user.xc_user_role (id, user_id, role_id, create_time, creator) VALUES ('20', '50', '17', '2017-09-12 14:20:20', '超级管理员');
INSERT INTO xedu_user.xc_user_role (id, user_id, role_id, create_time, creator) VALUES ('21', '52', '20', null, null);
INSERT INTO xedu_user.xc_user_role (id, user_id, role_id, create_time, creator) VALUES ('3', '49', '20', '2017-09-11 13:03:12', null);