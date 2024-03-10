CREATE SCHEMA IF NOT EXISTS jsonplaceholder_proxy;

CREATE TABLE IF NOT EXISTS jsonplaceholder_proxy.audit
(
    id         UUID NOT NULL PRIMARY KEY,
    date_time  timestamp(6),
    user_name  varchar(255),
    has_access boolean,
    method     varchar(255),
    url        varchar(255)
);

CREATE TABLE IF NOT EXISTS jsonplaceholder_proxy.users
(
    name     varchar(255) NOT NULL PRIMARY KEY,
    password varchar(255) NOT NULL,
    roles    varchar(255) NOT NULL
);