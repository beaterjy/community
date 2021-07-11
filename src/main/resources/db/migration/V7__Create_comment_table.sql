CREATE TABLE comment
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    parent_id BIGINT NOT NULL,
    type INT NOT NULL,
    content VARCHAR(1024),
    commentator INT NOT NULL,
    like_count BIGINT DEFAULT 0,
    gmt_create BIGINT,
    gmt_modified BIGINT
);