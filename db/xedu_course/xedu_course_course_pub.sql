create table course_pub
(
    id          varchar(32)                         not null comment '主键'
        primary key,
    name        varchar(32)                         not null comment '课程名称',
    users       varchar(500)                        not null comment '适用人群',
    mt          varchar(32)                         not null comment '大分类',
    st          varchar(32)                         not null comment '小分类',
    grade       varchar(32)                         not null comment '课程等级',
    studymodel  varchar(32)                         not null comment '学习模式',
    teachmode   varchar(32)                         null comment '教育模式',
    description text                                not null comment '课程介绍',
    timestamp   timestamp default CURRENT_TIMESTAMP not null comment '时间戳logstash使用',
    charge      varchar(32)                         not null comment '收费规则，对应数据字典',
    valid       varchar(32)                         not null comment '有效性，对应数据字典',
    qq          varchar(32)                         null comment '咨询qq',
    price       float(10, 2)                        null comment '价格',
    price_old   float(10, 2)                        null comment '原价格',
    expires     varchar(32)                         null comment '过期时间',
    start_time  varchar(32)                         null comment '课程有效期-开始时间',
    end_time    varchar(32)                         null comment '课程有效期-结束时间',
    pic         varchar(500)                        null comment '课程图片',
    teachplan   text                                not null comment '课程计划',
    pub_time    varchar(32)                         null comment '发布时间'
)
    charset = utf8;

INSERT INTO xedu_course.course_pub (id, name, users, mt, st, grade, studymodel, teachmode, description, timestamp, charge, valid, qq, price, price_old, expires, start_time, end_time, pic, teachplan, pub_time) VALUES ('297e7c7c62b888f00162b8a7dec20000', 'test_java基础33', 'java爱好者d', '1-3', '1-3-3', '200002', '201002', null, 'test_java基础33test_java基础33test_java基础33', '2018-04-27 22:57:24', '203002', '204002', '32432', 55, null, null, null, null, 'group1/M00/00/02/wKhlQFrQfNqAL0d_AALDG1Ia4xE439.png', '', '2018-04-26 16:57:23');
INSERT INTO xedu_course.course_pub (id, name, users, mt, st, grade, studymodel, teachmode, description, timestamp, charge, valid, qq, price, price_old, expires, start_time, end_time, pic, teachplan, pub_time) VALUES ('297e7c7c62b888f00162b8a965510001', 'test_java基础', 'test_java基础', '1-3', '1-3-2', '200001', '201001', null, 'test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2test_java基础2', '2018-04-27 22:57:24', '203001', '204001', '443242', null, null, null, null, null, 'group1/M00/00/00/wKhlQFrZS2aACA0LAAAxkpcK7CQ874.jpg', 'java基础语法 ', '2018-04-25 19:11:35');
INSERT INTO xedu_course.course_pub (id, name, users, mt, st, grade, studymodel, teachmode, description, timestamp, charge, valid, qq, price, price_old, expires, start_time, end_time, pic, teachplan, pub_time) VALUES ('297e7c7c62b8aa9d0162b8ab13910000', 'java基础3', 'java基础3', '1-3', '1-3-2', '200001', '201001', null, 'java基础3java基础3java基础3java基础3java基础3java基础3', '2018-04-27 22:57:24', '203001', '204001', null, null, null, null, null, null, 'group1/M00/00/00/wKhlQFrZS2aACA0LAAAxkpcK7CQ874.jpg', '', '2018-04-26 17:10:55');
INSERT INTO xedu_course.course_pub (id, name, users, mt, st, grade, studymodel, teachmode, description, timestamp, charge, valid, qq, price, price_old, expires, start_time, end_time, pic, teachplan, pub_time) VALUES ('402885816243d2dd016243f24c030002', '大数据', '具有一定的java基础', '1-6', '1-6-1', '200001', '201001', null, '111111大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据大数据', '2018-05-26 16:49:33', '203002', '204001', null, 99, 199, null, null, null, 'group1/M00/00/02/wKhlQFrQfNqAL0d_AALDG1Ia4xE439.png', '第一节 ', '2018-04-25 19:11:35');
INSERT INTO xedu_course.course_pub (id, name, users, mt, st, grade, studymodel, teachmode, description, timestamp, charge, valid, qq, price, price_old, expires, start_time, end_time, pic, teachplan, pub_time) VALUES ('4028858162e0bc0a0162e0bfdf1a0000', '人工智能+python', '小白', '1-6', '1-6-5', '200002', '201001', null, '人工智能+python非常不错！！！', '2018-04-27 22:57:24', '203002', '204002', '45323453', 198, null, null, null, null, 'group1/M00/00/00/wKhlQFrZS2aACA0LAAAxkpcK7CQ874.jpg', '', '2018-04-25 19:11:35');
INSERT INTO xedu_course.course_pub (id, name, users, mt, st, grade, studymodel, teachmode, description, timestamp, charge, valid, qq, price, price_old, expires, start_time, end_time, pic, teachplan, pub_time) VALUES ('4028e581617f945f01617f9dabc40000', 'Bootstrap开发框架', '', '1-1', '1-1-1', '200002', '201001', null, 'Bootstrap是由Twitter推出的一个前台页面开发框架，在行业之中使用较为广泛。此开发框架包含了大量的CSS、JS程序代码，可以帮助开发者（尤其是不擅长页面开发的程序人员）轻松的实现一个不受浏览器限制的精美界面效果。', '2018-05-05 19:24:41', '203002', '204001', '4455432', 0.01, null, null, null, null, 'group1/M00/00/01/wKhlQFqO0OGAFyhGAAA-8SWa8Qc537.jpg', '计算机原理 计算机硬件 计算机软件 计算机编程入门 java语法介绍 Hello World 数据库编程 操作系统原理 操作系统类型介绍 操作系统原理 ', '2018-04-25 19:11:35');
INSERT INTO xedu_course.course_pub (id, name, users, mt, st, grade, studymodel, teachmode, description, timestamp, charge, valid, qq, price, price_old, expires, start_time, end_time, pic, teachplan, pub_time) VALUES ('4028e58161bcf7f40161bcf8b77c0000', 'spring cloud实战', '所有人', '1-3', '1-3-2', '200003', '201001', '', '本课程主要从四个章节进行讲解： 1.微服务架构入门 2.spring cloud 基础入门 3.实战Spring Boot 4.注册中心eureka。', '2018-04-27 22:57:24', '203002', '204002', '54354353', 0.01, null, null, null, null, 'group1/M00/00/01/wKhlQFqO2HqAA6sPAAArlhJed-w088.jpg', '微服务架构入门 为什么要使用微服务:单体架构的特点 为什么要使用微服务:微服务的优缺点 spring cloud 基础入门 为什么要选择spring cloud? 为什么springcloud要设计一套新的版本升级规则？ 实战-Spring Boot 为什么越来越多的开发者选择使用spring boot？它解决了什么问题？ spring boot的入门例子 注册中心Eureka 微服务架构为什么需要注册中心，它解决了什么问题？  一个Eureka注册中心的入门例子 ', '2018-04-25 19:11:35');
INSERT INTO xedu_course.course_pub (id, name, users, mt, st, grade, studymodel, teachmode, description, timestamp, charge, valid, qq, price, price_old, expires, start_time, end_time, pic, teachplan, pub_time) VALUES ('4028e58161bd22e60161bd23672a0001', 'Javascript之VueJS', '所有人', '1-1', '1-1-9', '200002', '201001', '', 'Vue系列课程：从Vue1.0讲到Vue2.0，从理论讲到实战，理论与案例巧妙结合，让课程更容易理解！', '2018-04-27 16:57:24', '203002', '204001', '4324322', 0.01, null, null, null, null, 'group1/M00/00/01/wKhlQFqO4MmAOP53AAAcwDwm6SU490.jpg', 'Vuejs 第一讲 第一节 vue基础、常用指令、bootstrap+vue的简易留言 第二节 属性和事件、模板、交互、案例 Vuejs 第二讲 第一节 计算属性的使用、vue实例的简单方法、提高循环的性能，让重复数据显示出来 第二节 自定义过滤器、自定义指令 、自定义键盘事件、数据的监听 Vuejs 第三讲 ', '2018-04-25 19:11:35');
INSERT INTO xedu_course.course_pub (id, name, users, mt, st, grade, studymodel, teachmode, description, timestamp, charge, valid, qq, price, price_old, expires, start_time, end_time, pic, teachplan, pub_time) VALUES ('4028e58161bd3b380161bd3bcd2f0000', 'Redis从入门到项目实战', '', '1-3', '1-3-2', '200002', '201001', null, 'redis在当前的大型网站和500强企业中，已被广泛应用。 redis是基于内存的key-value数据库，比传统的关系型数据库在性能方面有非常大的优势。 肖老师这套视频，精选了redis在实际项目中的十几个应用场景。通过本课程的学习，可以让学员快速掌握redis在实际项目中如何应用。 作为架构师，redis是必须要掌握的技能！', '2018-05-16 18:55:36', '203002', '204001', '32432432', 0.01, null, null, null, null, 'group1/M00/00/01/wKhlQFqO5yqAQMozAAAqor3lyz0082.jpg', '第一章：redis简介 第一节 NoSQL简介 第二节 认识Redis 第二章：redis的安装与配置 第三章：Redis数据操作 第四章：Redis进阶操作 第五章：Redis主从配置 ', '2018-04-25 19:11:35');