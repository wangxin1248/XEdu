create table course_off
(
    id          varchar(32)                         not null comment '主键'
        primary key,
    name        varchar(32)                         not null comment '课程名称',
    users       varchar(500)                        null comment '适用人群',
    mt          varchar(32)                         not null comment '大分类',
    st          varchar(32)                         not null comment '小分类',
    grade       varchar(32)                         not null comment '课程等级',
    studymodel  varchar(32)                         not null comment '学习模式',
    description text                                null comment '课程介绍',
    timestamp   timestamp default CURRENT_TIMESTAMP not null comment '时间戳',
    charge      varchar(32)                         not null comment '收费规则，对应数据字典',
    valid       varchar(32)                         not null comment '有效性，对应数据字典',
    qq          varchar(32)                         null comment '咨询qq',
    price       float(10, 2)                        not null comment '价格',
    price_old   float(10, 2)                        null comment '原价格',
    expires     timestamp                           null comment '过期时间',
    pic         varchar(500)                        null comment '课程图片',
    teachplan   text                                not null comment '课程计划'
)
    charset = utf8;

