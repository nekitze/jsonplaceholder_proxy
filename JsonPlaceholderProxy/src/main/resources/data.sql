INSERT INTO jsonplaceholder_proxy.users (name, password, roles)
VALUES ('admin', '$2a$10$UuNBDg5K7G1BojQ4KlT/Pu0I5YyJYA1vqvFED8Tma2avwfSmYmR0S', 'ROLE_ADMIN'),
       ('users', '$2a$10$ArWzEk9x2GuTeyGwtfDsWu4CO43/gQVLSZP7PLl/wqTTDp2ia0KOK', 'ROLE_USERS'),
       ('albums', '$2a$10$Hd10EwcmAggo2IUFDEqZi.3PQn2c1EMgWzc1zzsWIsQ/6LQKX3jQ2', 'ROLE_ALBUMS'),
       ('posts', '$2a$10$UeasAg0Q.lnU.ddrk3F1uub8mY3T/25mWgSP6HkECBimH3NPN7rCS', 'ROLE_POSTS'),
       ('visitor', '$2a$10$yXMne.CnGQT3f2pUTGBMXu10b9QL2krfBvMlKe3evK8hMxoOJMueu',
        'ROLE_POSTS_VIEWER, ROLE_ALBUMS_VIEWER'),
       ('redactor', '$2a$10$ejC00SeFIxMgjV5mCKu/9.DixgH2qRjF0MKzltBt5lSgJih0WLNVC',
        'ROLE_POSTS_VIEWER, ROLE_POSTS_EDITOR, ROLE_ALBUMS_VIEWER, ROLE_ALBUMS_EDITOR');
