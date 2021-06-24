CREATE TABLE question
(
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    title VARCHAR(50),
    description TEXT,
    tag VARCHAR(256),
    creator INT,
    gmt_create BIGINT,
    gmt_modified BIGINT,
    comment_count INT DEFAULT 0,
    like_count INT DEFAULT 0,
    view_count INT DEFAULT 0
);