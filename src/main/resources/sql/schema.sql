DROP DATABASE IF EXISTS simplefeed_db;
CREATE DATABASE simplefeed_db CHARACTER SET utf8 COLLATE utf8_general_ci;
USE simplefeed_db;

DROP TABLE IF EXISTS feed_sources;
CREATE TABLE feed_sources (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    url VARCHAR(255) NOT NULL,
    image_url VARCHAR(512) NOT NULL,
    UNIQUE uq_source (name, url),
    PRIMARY KEY (id)
);
