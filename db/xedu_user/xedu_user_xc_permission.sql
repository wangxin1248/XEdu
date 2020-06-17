create table xc_permission
(
    id          varchar(32)  not null
        primary key,
    role_id     varchar(32)  not null,
    menu_id     varchar(255) not null,
    create_time datetime     null,
    constraint xu_permission_unique
        unique (role_id, menu_id),
    constraint fk_xc_permission_menu_id
        foreign key (menu_id) references xc_menu (id),
    constraint fk_xc_permission_role_id
        foreign key (role_id) references xc_role (id)
);

INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('11', '20', '222222222222222222', null);
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('11101', '20', '903459378655395850', null);
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('12', '20', '903459378655395841', null);
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('13', '20', '903459378655395842', null);
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('14', '20', '903459378655395843', null);
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('15', '20', '903459378655395845', null);
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('16', '20', '903459378655395846', null);
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('17', '20', '903459378655395847', null);
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('18', '20', '903459378655395848', null);
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('19', '20', '903459378655395849', null);
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('20', '20', '903459378655395851', null);
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('89328714465778073617', '17', '111111111111111111', '2017-09-14 18:40:48');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8932871446577807366', '6', '111111111111111111', '2017-08-08 11:31:39');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8932871446577807367', '6', '903459378655395846', null);
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8932871446577807368', '8', '111111111111111111', '2017-08-08 11:56:44');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8932887158818078726', '6', '893288715881807872', '2017-08-08 11:31:39');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8932887158818078728', '8', '893288715881807872', '2017-08-08 11:56:44');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8933049602827878406', '6', '893304960282787840', '2017-08-08 11:31:39');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8933049602827878408', '8', '893304960282787840', '2017-08-08 11:56:44');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8943965235325173766', '6', '894396523532517376', '2017-08-08 11:31:39');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8943965235325173768', '8', '894396523532517376', '2017-08-08 11:56:44');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944734867124387846', '6', '894473486712438784', '2017-08-08 11:31:39');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944734867124387848', '8', '894473486712438784', '2017-08-08 11:56:44');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944736518379929606', '6', '894473651837992960', '2017-08-08 11:31:39');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944736518379929608', '8', '894473651837992960', '2017-08-08 11:56:44');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944751420616212488', '8', '894475142061621248', '2017-08-08 11:56:44');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944758278806568968', '8', '894475827880656896', '2017-08-08 11:56:44');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944759854522695688', '8', '894475985452269568', '2017-08-08 11:56:44');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944761187304734728', '8', '894476118730473472', '2017-08-08 11:56:45');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944762764027494408', '8', '894476276402749440', '2017-08-08 11:56:45');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944769509516902408', '8', '894476950951690240', '2017-08-08 11:56:45');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944771079193231368', '8', '894477107919323136', '2017-08-08 11:56:45');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944772449262632968', '8', '894477244926263296', '2017-08-08 11:56:45');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944774205124116488', '8', '894477420512411648', '2017-08-08 11:56:45');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('89447785108288307217', '17', '894477851082883072', '2017-09-14 18:40:51');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944778510828830726', '6', '894477851082883072', '2017-08-08 11:31:39');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944778510828830728', '8', '894477851082883072', '2017-08-08 11:56:45');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('89447799590381158417', '17', '894477995903811584', '2017-09-14 18:40:53');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944779959038115846', '6', '894477995903811584', '2017-08-08 11:31:39');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8944779959038115848', '8', '894477995903811584', '2017-08-08 11:56:45');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('89475273445919948817', '17', '894752734459199488', '2017-09-14 18:40:54');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8947527344591994888', '8', '894752734459199488', '2017-08-08 11:56:45');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8947692177635409926', '6', '903459378655395842', '2017-08-08 11:56:45');
INSERT INTO xedu_user.xc_permission (id, role_id, menu_id, create_time) VALUES ('8947692177635409930', '6', '903459378655395841', null);