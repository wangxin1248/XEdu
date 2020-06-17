create table xc_task
(
    id            varchar(32)  not null comment '任务id'
        primary key,
    create_time   datetime     null,
    update_time   datetime     null,
    delete_time   datetime     null,
    task_type     varchar(32)  null comment '任务类型',
    mq_exchange   varchar(64)  null comment '交换机名称',
    mq_routingkey varchar(64)  null comment 'routingkey',
    request_body  varchar(512) null comment '任务请求的内容',
    version       int(10)      null comment '乐观锁版本号',
    status        varchar(32)  null comment '任务状态',
    errormsg      varchar(512) null comment '任务错误信息'
);

