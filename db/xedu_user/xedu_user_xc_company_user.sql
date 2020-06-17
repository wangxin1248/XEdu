create table xc_company_user
(
    id         varchar(32) not null
        primary key,
    company_id varchar(32) not null,
    user_id    varchar(32) not null,
    constraint xc_company_user_unique
        unique (company_id, user_id),
    constraint FK_xc_company_user_company_id
        foreign key (company_id) references xc_company (id),
    constraint FK_xc_company_user_user_id
        foreign key (user_id) references xc_user (id)
);

INSERT INTO xedu_user.xc_company_user (id, company_id, user_id) VALUES ('1', '1', '49');
INSERT INTO xedu_user.xc_company_user (id, company_id, user_id) VALUES ('2', '2', '52');