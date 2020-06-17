create table oauth_approvals
(
    userId         varchar(256)                            null,
    clientId       varchar(256)                            null,
    scope          varchar(256)                            null,
    status         varchar(10)                             null,
    expiresAt      timestamp default CURRENT_TIMESTAMP     not null on update CURRENT_TIMESTAMP,
    lastModifiedAt timestamp default '0000-00-00 00:00:00' not null
);

