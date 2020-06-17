create table xc_company
(
    id          varchar(32)  not null
        primary key,
    linkname    varchar(64)  null comment '联系人名称',
    name        varchar(128) not null comment '名称',
    mobile      varchar(11)  not null,
    email       varchar(128) not null,
    intro       varchar(512) null comment '简介',
    logo        varchar(128) null comment 'logo',
    identitypic varchar(128) null comment '身份证照片',
    worktype    varchar(32)  null comment '工具性质',
    businesspic varchar(128) null comment '营业执照',
    status      varchar(32)  null comment '企业状态'
);

INSERT INTO xedu_user.xc_company (id, linkname, name, mobile, email, intro, logo, identitypic, worktype, businesspic, status) VALUES ('1', '张老师', '传智播客', '13333334444', 'abc@126.com', '2006年创建！', null, null, null, null, null);
INSERT INTO xedu_user.xc_company (id, linkname, name, mobile, email, intro, logo, identitypic, worktype, businesspic, status) VALUES ('2', '李老师', '博学谷', '', '', null, null, null, null, null, null);