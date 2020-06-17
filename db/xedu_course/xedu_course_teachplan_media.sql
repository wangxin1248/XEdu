create table teachplan_media
(
    teachplan_id           varchar(32)  not null comment '课程计划id'
        primary key,
    media_id               varchar(32)  not null comment '媒资文件id',
    media_fileoriginalname varchar(128) not null comment '媒资文件的原始名称',
    media_url              varchar(256) not null comment '媒资文件访问地址',
    courseid               varchar(32)  not null comment '课程Id'
)
    charset = utf8;

INSERT INTO xedu_course.teachplan_media (teachplan_id, media_id, media_fileoriginalname, media_url, courseid) VALUES ('3', 'c5c75d70f382e6016d2f506d134eee11', 'lucene.avi', 'c/5/c5c75d70f382e6016d2f506d134eee11/hls/c5c75d70f382e6016d2f506d134eee11.m3u8', '4028e581617f945f01617f9dabc40000');
INSERT INTO xedu_course.teachplan_media (teachplan_id, media_id, media_fileoriginalname, media_url, courseid) VALUES ('4', '5fbb79a2016c0eb609ecd0cd3dc48016', 'solr.avi', '5/f/5fbb79a2016c0eb609ecd0cd3dc48016/hls/5fbb79a2016c0eb609ecd0cd3dc48016.m3u8', '4028e581617f945f01617f9dabc40000');
INSERT INTO xedu_course.teachplan_media (teachplan_id, media_id, media_fileoriginalname, media_url, courseid) VALUES ('402847eb71fc73940171fc7512eb0001', 'c5c75d70f382e6016d2f506d134eee11', 'lucene.avi', 'c/5/c5c75d70f382e6016d2f506d134eee11/c5c75d70f382e6016d2f506d134eee11.m3u8', '297e7c7c62b8aa9d0162b8ab13910000');
INSERT INTO xedu_course.teachplan_media (teachplan_id, media_id, media_fileoriginalname, media_url, courseid) VALUES ('402847eb71fc73940171fc7523c80002', '5fbb79a2016c0eb609ecd0cd3dc48016', 'solr.avi', '5/f/5fbb79a2016c0eb609ecd0cd3dc48016/5fbb79a2016c0eb609ecd0cd3dc48016.m3u8', '297e7c7c62b8aa9d0162b8ab13910000');
INSERT INTO xedu_course.teachplan_media (teachplan_id, media_id, media_fileoriginalname, media_url, courseid) VALUES ('402847eb71fddbd50171fddd6cb50000', 'c5c75d70f382e6016d2f506d134eee11', 'lucene.avi', 'c/5/c5c75d70f382e6016d2f506d134eee11/hls/c5c75d70f382e6016d2f506d134eee11.m3u8', '402847eb71205ec501712061978c0001');
INSERT INTO xedu_course.teachplan_media (teachplan_id, media_id, media_fileoriginalname, media_url, courseid) VALUES ('402847eb71fddbd50171fdddaf1c0001', '5fbb79a2016c0eb609ecd0cd3dc48016', 'solr.avi', '5/f/5fbb79a2016c0eb609ecd0cd3dc48016/hls/5fbb79a2016c0eb609ecd0cd3dc48016.m3u8', '402847eb71205ec501712061978c0001');
INSERT INTO xedu_course.teachplan_media (teachplan_id, media_id, media_fileoriginalname, media_url, courseid) VALUES ('4028e58161bd3b380161bd3fe9220008', '53ac4cca3ddf386c21f4f1cbb4dc9876', '3.avi', '5/3/53ac4cca3ddf386c21f4f1cbb4dc9876/hls/53ac4cca3ddf386c21f4f1cbb4dc9876.m3u8', '4028e58161bd3b380161bd3bcd2f0000');
INSERT INTO xedu_course.teachplan_media (teachplan_id, media_id, media_fileoriginalname, media_url, courseid) VALUES ('4028e58161bd3b380161bd40cf340009', '809694a6a974c35e3a36f36850837d7c', '1.avi', '8/0/809694a6a974c35e3a36f36850837d7c/hls/809694a6a974c35e3a36f36850837d7c.m3u8', '4028e58161bd3b380161bd3bcd2f0000');