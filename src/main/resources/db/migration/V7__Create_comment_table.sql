create table comment
(
    id bigint auto_increment primary key not null,
    parent_id bigint not null,
    type int not null,
    content varchar(1024),
    commentator int not null,
    like_count bigint default 0,
    gmt_create bigint,
    gmt_modified bigint
);