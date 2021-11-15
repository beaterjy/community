create table question
(
    id int auto_increment primary key not null,
    title varchar(50),
    description text,
    tag varchar(256),
    creator int,
    gmt_create bigint,
    gmt_modified bigint,
    comment_count int default 0,
    like_count int default 0,
    view_count int default 0
);