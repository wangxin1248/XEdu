create table course_pic
(
    courseid varchar(32)  not null comment '课程id'
        primary key,
    pic      varchar(256) not null comment '图片id'
)
    charset = utf8;

INSERT INTO xedu_course.course_pic (courseid, pic) VALUES ('402847eb71205ec501712061978c0001', 'group1/M00/00/00/wKh0gV6II-qABCiTAABA3pi7cFk51.jpeg');
INSERT INTO xedu_course.course_pic (courseid, pic) VALUES ('402847eb71205ec50171208a3b660002', 'group1/M00/00/00/wKh0gV6IFAKAZDZgAABA3pi7cFk33.jpeg');
INSERT INTO xedu_course.course_pic (courseid, pic) VALUES ('402847eb71208f46017120901ec60000', 'group1/M00/00/00/wKh0gV6IFBGAcaVnAABA3pi7cFk26.jpeg');
INSERT INTO xedu_course.course_pic (courseid, pic) VALUES ('402847eb71208f46017120964f5e0003', 'group1/M00/00/00/wKh0gV6ICtOATNXKAABA3pi7cFk22.jpeg');