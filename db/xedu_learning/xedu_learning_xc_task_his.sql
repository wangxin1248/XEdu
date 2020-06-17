create table xc_task_his
(
    id            varchar(32)       not null comment '任务id'
        primary key,
    create_time   datetime          null,
    update_time   datetime          null,
    delete_time   datetime          null,
    task_type     varchar(32)       null comment '任务类型',
    mq_exchange   varchar(64)       null comment '交换机名称',
    mq_routingkey varchar(64)       null comment 'routingkey',
    request_body  varchar(512)      null comment '任务请求的内容',
    version       int(10) default 0 null comment '乐观锁版本号',
    status        varchar(32)       null comment '任务状态',
    errormsg      varchar(512)      null
);

INSERT INTO xedu_learning.xc_task_his (id, create_time, update_time, delete_time, task_type, mq_exchange, mq_routingkey, request_body, version, status, errormsg) VALUES ('10', '2018-04-04 22:58:20', '2018-04-04 22:58:54', '2018-04-04 22:58:55', null, 'ex_learning_addchoosecourse', 'addchoosecourse', '{"userId":"49","courseId":"4028e581617f945f01617f9dabc40000"}', null, null, null);