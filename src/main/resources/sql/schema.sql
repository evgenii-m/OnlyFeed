DROP DATABASE IF EXISTS take_rss_db;
CREATE DATABASE take_rss_db CHARACTER SET utf8 COLLATE utf8_general_ci;
USE take_rss_db;

DROP TABLE IF EXISTS rss_channels;
CREATE TABLE rss_channels (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    url VARCHAR(255) NOT NULL,
    UNIQUE uq_rss_channel (name, url),
    PRIMARY KEY (id)
);