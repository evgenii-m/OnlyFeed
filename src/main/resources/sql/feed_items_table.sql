USE simplefeed_db;

DROP TABLE IF EXISTS feed_items;
CREATE TABLE feed_items (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(1000) NOT NULL,
    description VARCHAR(10000),
    link VARCHAR(256) NOT NULL,
    published_date DATETIME NOT NULL,
    author VARCHAR(100),
    image_url VARCHAR(256),
    feed_source_id INT NOT NULL,
    PRIMARY KEY (id),
    INDEX (feed_source_id)
);
SELECT * FROM feed_items JOIN feed_sources USING(id);