CREATE TABLE ad
(
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    title VARCHAR(256) NOT NULL,
    url VARCHAR(512),
    image VARCHAR(512),
    gmt_create BIGINT NOT NULL,
    gmt_modified BIGINT NOT NULL,
    gmt_start BIGINT NOT NULL,
    gmt_end BIGINT NOT NULL,
    status INT DEFAULT 0 NOT NULL,
    pos VARCHAR(20) NOT NULL
);