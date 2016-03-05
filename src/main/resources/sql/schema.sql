DROP DATABASE IF EXISTS simplefeed_db;
CREATE DATABASE simplefeed_db CHARACTER SET utf8 COLLATE utf8_general_ci;
USE simplefeed_db;

DROP TABLE IF EXISTS channels;
CREATE TABLE channels (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    url VARCHAR(255) NOT NULL,
    UNIQUE uq_channel (name, url),
    PRIMARY KEY (id)
);
