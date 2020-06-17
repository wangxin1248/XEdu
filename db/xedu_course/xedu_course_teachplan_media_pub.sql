create table teachplan_media_pub
(
    teachplan_id           varchar(32)                         not null comment '课程计划id'
        primary key,
    media_id               varchar(32)                         not null comment '媒资文件id',
    media_fileoriginalname varchar(128)                        not null comment '媒资文件的原始名称',
    media_url              varchar(256)                        not null comment '媒资文件访问地址',
    courseid               varchar(32)                         not null comment '课程Id',
    timestamp              timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'logstash使用'
)
    charset = utf8;

INSERT INTO xedu_course.teachplan_media_pub (teachplan_id, media_id, media_fileoriginalname, media_url, courseid, timestamp) VALUES ('3', 'c5c75d70f382e6016d2f506d134eee11', 'lucene.avi', 'c/5/c5c75d70f382e6016d2f506d134eee11/hls/c5c75d70f382e6016d2f506d134eee11.m3u8', '4028e581617f945f01617f9dabc40000', '2020-06-14 07:23:27');
INSERT INTO xedu_course.teachplan_media_pub (teachplan_id, media_id, media_fileoriginalname, media_url, courseid, timestamp) VALUES ('4', '5fbb79a2016c0eb609ecd0cd3dc48016', 'solr.avi', '5/f/5fbb79a2016c0eb609ecd0cd3dc48016/hls/5fbb79a2016c0eb609ecd0cd3dc48016.m3u8', '4028e581617f945f01617f9dabc40000', '2020-06-14 07:23:27');
INSERT INTO xedu_course.teachplan_media_pub (teachplan_id, media_id, media_fileoriginalname, media_url, courseid, timestamp) VALUES ('402847eb71fddbd50171fddd6cb50000', 'c5c75d70f382e6016d2f506d134eee11', 'lucene.avi', 'c/5/c5c75d70f382e6016d2f506d134eee11/hls/c5c75d70f382e6016d2f506d134eee11.m3u8', '402847eb71205ec501712061978c0001', '2020-05-16 14:47:36');
INSERT INTO xedu_course.teachplan_media_pub (teachplan_id, media_id, media_fileoriginalname, media_url, courseid, timestamp) VALUES ('402847eb71fddbd50171fdddaf1c0001', '5fbb79a2016c0eb609ecd0cd3dc48016', 'solr.avi', '5/f/5fbb79a2016c0eb609ecd0cd3dc48016/hls/5fbb79a2016c0eb609ecd0cd3dc48016.m3u8', '402847eb71205ec501712061978c0001', '2020-05-16 14:47:36');