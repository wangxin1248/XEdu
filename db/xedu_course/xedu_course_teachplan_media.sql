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

INSERT INTO xedu_course.teachplan_media (teachplan_id, media_id, media_fileoriginalname, media_url, courseid) VALUES ('4028e58161bd3b380161bd3fe9220008', '53ac4cca3ddf386c21f4f1cbb4dc9876', '3.avi', '5/3/53ac4cca3ddf386c21f4f1cbb4dc9876/hls/53ac4cca3ddf386c21f4f1cbb4dc9876.m3u8', '4028e58161bd3b380161bd3bcd2f0000');
INSERT INTO xedu_course.teachplan_media (teachplan_id, media_id, media_fileoriginalname, media_url, courseid) VALUES ('4028e58161bd3b380161bd40cf340009', '809694a6a974c35e3a36f36850837d7c', '1.avi', '8/0/809694a6a974c35e3a36f36850837d7c/hls/809694a6a974c35e3a36f36850837d7c.m3u8', '4028e58161bd3b380161bd3bcd2f0000');