create table course_pre
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
    status      varchar(32)                         not null comment '课程状态',
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

INSERT INTO xedu_course.course_pre (id, name, users, mt, st, grade, studymodel, description, status, timestamp, charge, valid, qq, price, price_old, expires, pic, teachplan) VALUES ('4028e581617f945f01617f9dabc40000', 'Bootstrap开发框架', '', '1-1', '1-1-1', '200002', '201001', 'Bootstrap是由Twitter推出的一个前台页面开发框架，在行业之中使用较为广泛。此开发框架包含了大量的CSS、JS程序代码，可以帮助开发者（尤其是不擅长页面开发的程序人员）轻松的实现一个不受浏览器限制的精美界面效果。', '202001', '2018-02-22 21:49:22', '203002', '204001', '4455432', 89.1, null, '2018-02-22 17:45:42', '["group1/M00/00/01/wKhlQFqO0OGAFyhGAAA-8SWa8Qc537.jpg"]', '计算机原理 计算机硬件 计算机软件 计算机编程入门 java语法介绍 Hello World 数据库编程 操作系统原理 操作系统原理 操作系统类型介绍 ');
INSERT INTO xedu_course.course_pre (id, name, users, mt, st, grade, studymodel, description, status, timestamp, charge, valid, qq, price, price_old, expires, pic, teachplan) VALUES ('4028e58161bcf7f40161bcf8b77c0000', 'spring cloud实战', '所有人', '1-3', '1-3-2', '200003', '201001', '本课程主要从四个章节进行讲解： 1.微服务架构入门 2.spring cloud 基础入门 3.实战Spring Boot 4.注册中心eureka。', '202001', '2018-02-22 21:49:26', '203002', '204002', '54354353', 99, null, '2018-02-22 18:17:54', '["group1/M00/00/01/wKhlQFqO2HqAA6sPAAArlhJed-w088.jpg"]', '微服务架构入门 为什么要使用微服务:单体架构的特点 为什么要使用微服务:微服务的优缺点 spring cloud 基础入门 为什么要选择spring cloud? 为什么springcloud要设计一套新的版本升级规则？ 实战-Spring Boot 为什么越来越多的开发者选择使用spring boot？它解决了什么问题？ spring boot的入门例子 注册中心Eureka 微服务架构为什么需要注册中心，它解决了什么问题？  一个Eureka注册中心的入门例子 ');
INSERT INTO xedu_course.course_pre (id, name, users, mt, st, grade, studymodel, description, status, timestamp, charge, valid, qq, price, price_old, expires, pic, teachplan) VALUES ('4028e58161bd22e60161bd23672a0001', 'Javascript之VueJS', '所有人', '1-1', '1-1-9', '200002', '201001', 'Vue系列课程：从Vue1.0讲到Vue2.0，从理论讲到实战，理论与案例巧妙结合，让课程更容易理解！', '202001', '2018-02-22 21:49:30', '203002', '204001', '4324322', 90, null, '2018-02-22 18:53:13', '["group1/M00/00/01/wKhlQFqO4MmAOP53AAAcwDwm6SU490.jpg"]', 'Vuejs 第一讲 第一节 vue基础、常用指令、bootstrap+vue的简易留言 第二节 属性和事件、模板、交互、案例 Vuejs 第二讲 第一节 计算属性的使用、vue实例的简单方法、提高循环的性能，让重复数据显示出来 第二节 自定义过滤器、自定义指令 、自定义键盘事件、数据的监听 Vuejs 第三讲 ');
INSERT INTO xedu_course.course_pre (id, name, users, mt, st, grade, studymodel, description, status, timestamp, charge, valid, qq, price, price_old, expires, pic, teachplan) VALUES ('4028e58161bd3b380161bd3bcd2f0000', 'Redis从入门到项目实战', '', '1-3', '1-3-2', '200002', '201001', 'redis在当前的大型网站和500强企业中，已被广泛应用。 redis是基于内存的key-value数据库，比传统的关系型数据库在性能方面有非常大的优势。 肖老师这套视频，精选了redis在实际项目中的十几个应用场景。通过本课程的学习，可以让学员快速掌握redis在实际项目中如何应用。 作为架构师，redis是必须要掌握的技能！', '202001', '2018-02-22 21:49:35', '203002', '204001', '32432432', 100, null, '2018-02-22 19:20:24', '["group1/M00/00/01/wKhlQFqO5yqAQMozAAAqor3lyz0082.jpg"]', '第一章：redis简介 第一节 NoSQL简介 第二节 认识Redis 第二章：redis安装与配置 第三章：Redis数据操作 第四章：Redis进阶操作 第五章：Redis主从配置 ');