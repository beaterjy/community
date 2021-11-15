create table ad
(
    id int auto_increment primary key not null,
    title varchar(256) not null,
    url varchar(512),
    image varchar(512),
    gmt_create bigint not null,
    gmt_modified bigint not null,
    gmt_start bigint not null,
    gmt_end bigint not null,
    status int default 0 not null,
    pos varchar(20) not null
);