create table xc_menu
(
    id          varchar(64)  not null
        primary key,
    code        varchar(255) null comment '菜单编码',
    p_id        varchar(255) null comment '父菜单ID',
    menu_name   varchar(255) null comment '名称',
    url         varchar(255) null comment '请求地址',
    is_menu     char         null comment '是否是菜单',
    level       int          null comment '菜单层级',
    sort        int          null comment '菜单排序',
    status      char         null,
    icon        varchar(255) null,
    create_time datetime     null,
    update_time datetime     null,
    constraint FK_CODE
        unique (code)
);

INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('', 'xc_teachmanager_course_pic', null, null, null, null, null, null, null, null, null, null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('000000000000000000', 'root', '0', '系统根目录', null, '0', 0, 1, '1', null, '2017-08-03 18:31:54', null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('111111111111111111', 'xc_sysmanager', '000000000000000000', '系统管理', null, '1', 1, 10, '1', '', '2017-08-04 09:47:06', null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('222222222222222222', 'xc_teachmanager', '000000000000000000', '教学管理', null, '1', 1, 2, '1', null, null, null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('893288715881807872', 'xc_sysmanager_user', '111111111111111111', '用户管理', null, '1', 2, 1, '1', '', '2017-08-04 09:53:21', '2017-08-07 18:18:39');
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('893304960282787840', 'xc_sysmanager_user_add', '893288715881807872', '添加用户', null, '1', 3, 1, '1', '', '2017-08-04 10:57:54', '2017-08-08 11:02:55');
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('894396523532517376', 'xc_sysmanager_user_edit', '893288715881807872', '用户修改', null, '0', 3, 1, '1', '', '2017-08-07 11:15:23', '2017-08-07 16:57:52');
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('894473486712438784', 'xc_sysmanager_user_view', '893288715881807872', '用户列表', null, '1', 3, 2, '1', '', '2017-08-07 16:21:12', null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('894473651837992960', 'xc_sysmanager_user_delete', '893288715881807872', '用户删除', null, '0', 3, 4, '1', '', '2017-08-07 16:21:52', null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('894475142061621248', 'xc_sysmanager_role', '111111111111111111', '角色管理', null, '1', 2, 2, '1', '', '2017-08-07 16:27:47', '2017-08-08 10:34:56');
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('894475827880656896', 'xc_sysmanager_role_add', '894475142061621248', '角色添加', null, '0', 3, 1, '1', '', '2017-08-07 16:30:31', null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('894475985452269568', 'xc_sysmanager_role_edit', '894475142061621248', '角色编辑', null, '0', 3, 2, '1', '', '2017-08-07 16:31:08', null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('894476118730473472', 'xc_sysmanager_role_delete', '894475142061621248', '角色删除', null, '0', 3, 2, '1', '', '2017-08-07 16:31:40', '2017-08-07 16:37:24');
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('894476276402749440', 'xc_sysmanager_role_permission', '894475142061621248', '角色配权', null, '0', 3, 3, '1', '', '2017-08-07 16:32:18', null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('894476950951690240', 'xc_sysmanager_menu', '111111111111111111', '菜单管理', null, '1', 2, 2, '1', '', '2017-08-07 16:34:58', null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('894477107919323136', 'xc_sysmanager_menu_add', '894476950951690240', '菜单添加', null, '0', 3, 1, '1', '', '2017-08-07 16:35:36', null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('894477244926263296', 'xc_sysmanager_menu_edit', '894476950951690240', '菜单编辑', null, '0', 3, 2, '1', '', '2017-08-07 16:36:08', null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('894477420512411648', 'xc_sysmanager_menu_delete', '894476950951690240', '菜单删除', null, '0', 3, 2, '1', '', '2017-08-07 16:36:50', null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('894477851082883072', 'xc_sysmanager_doc', '111111111111111111', '文档查询', null, '1', 2, 9, '1', '', '2017-08-07 16:38:33', '2017-09-13 11:20:26');
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('894477995903811584', 'xc_sysmanager_log', '111111111111111111', 'add', null, '1', 2, 10, '1', '', '2017-08-07 16:39:07', '2017-08-08 09:56:29');
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('894752734459199488', 'xc_sysmanager_company', '111111111111111111', '机构管理', null, '1', 1, 1, '1', '', '2017-08-08 10:50:50', null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('903459378655395840', 'xc_sysmanager_user_resetpwd', '893288715881807872', '密码重置', null, '1', 3, 2, '1', '', '2017-09-01 11:27:56', null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('903459378655395841', 'xc_teachmanager_course', '222222222222222222', '课程管理', null, '1', 2, 1, '1', null, null, null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('903459378655395842', 'xc_teachmanager_course_add', '903459378655395841', '添加课程', null, '1', 3, 1, '1', null, null, null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('903459378655395843', 'xc_teachmanager_course_del', '903459378655395841', '删除课程', null, null, null, null, null, null, null, null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('903459378655395845', 'xc_teachmanager_course_market', '903459378655395841', '编辑课程营销信息', null, null, null, null, null, null, null, null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('903459378655395846', 'xc_teachmanager_course_base', '903459378655395841', '编辑课程基础信息', null, null, null, null, null, null, null, null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('903459378655395847', 'xc_teachmanager_course_plan', '903459378655395841', '编辑课程计划', null, null, null, null, null, null, null, null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('903459378655395848', 'xc_teachmanager_course_publish', '903459378655395841', '发布课程', null, null, null, null, null, null, null, null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('903459378655395849', 'xc_teachmanager_course_list', '903459378655395841', '我的课程', null, null, null, null, null, null, null, null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('903459378655395850', 'course_find_list', '903459378655395841', '查询课程列表', null, null, null, null, '1', null, null, null);
INSERT INTO xedu_user.xc_menu (id, code, p_id, menu_name, url, is_menu, level, sort, status, icon, create_time, update_time) VALUES ('903459378655395851', 'course_get_baseinfo', '903459378655395841', '获取课程信息', null, null, null, null, '1', null, null, null);