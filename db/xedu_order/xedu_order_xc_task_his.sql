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

INSERT INTO xedu_order.xc_task_his (id, create_time, update_time, delete_time, task_type, mq_exchange, mq_routingkey, request_body, version, status, errormsg) VALUES ('10', '2018-04-04 22:58:20', '2020-06-17 17:39:44', '2018-04-04 22:58:55', null, 'ex_learning_addchoosecourse', 'addchoosecourse', '{"userId":"49","courseId":"4028e581617f945f01617f9dabc40000"}', null, null, null);
INSERT INTO xedu_order.xc_task_his (id, create_time, update_time, delete_time, task_type, mq_exchange, mq_routingkey, request_body, version, status, errormsg) VALUES ('4028858162959ce5016295b604ba0000', '2018-04-05 20:09:17', '2018-04-05 20:09:18', '2018-04-05 20:09:21', 'add_choosecourse', 'XC_TASK_EXCHANGE', 'request_choosecourse_task_queue_routing_key', '{"courseId":"4028e58161bcf7f40161bcf8b77c0000,","userId":"49"}', 1, '10201', null);