create table oauth_access_token
(
    token_id          varchar(256) null,
    token             blob         null,
    authentication_id varchar(48)  not null
        primary key,
    user_name         varchar(256) null,
    client_id         varchar(256) null,
    authentication    blob         null,
    refresh_token     varchar(256) null
);

