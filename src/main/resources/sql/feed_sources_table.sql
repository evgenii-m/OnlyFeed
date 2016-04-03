USE simplefeed_db;

DROP TABLE IF EXISTS feed_sources;
CREATE TABLE feed_sources (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    url VARCHAR(256) NOT NULL,
    logo_url VARCHAR(256) NOT NULL,
    description VARCHAR(1000),
    user_id INT NOT NULL,
    PRIMARY KEY (id),
    INDEX (user_id)
);
SELECT * FROM feed_sources JOIN users USING(id);