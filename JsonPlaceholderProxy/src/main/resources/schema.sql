CREATE SCHEMA IF NOT EXISTS security;

CREATE TABLE IF NOT EXISTS security.users
(
    id SERIAL PRIMARY KEY,
    username varchar(15)  NOT NULL,
    password varchar(100) NOT NULL,
    enabled  boolean      NOT NULL
);

CREATE TABLE IF NOT EXISTS security.authorities
(
    user_id SERIAL NOT NULL REFERENCES security.users(id),
    authority varchar(15) NOT NULL
);

INSERT INTO security.users (username, password, enabled) VALUES ('users', '{noop}users', true);
INSERT INTO security.users (username, password, enabled) VALUES ('admin', '{noop}admin', true);
INSERT INTO security.users (username, password, enabled) VALUES ('albums', '{noop}albums', true);
INSERT INTO security.users (username, password, enabled) VALUES ('posts', '{noop}posts', true);


INSERT INTO security.authorities (user_id, authority) VALUES (1, 'ROLE_USERS');
INSERT INTO security.authorities (user_id, authority) VALUES (2, 'ROLE_POSTS');
INSERT INTO security.authorities (user_id, authority) VALUES (3, 'ROLE_ADMIN');
INSERT INTO security.authorities (user_id, authority) VALUES (4, 'ROLE_ALBUMS');
