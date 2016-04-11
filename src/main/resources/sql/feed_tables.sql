USE simplefeed_db;

DROP TABLE IF EXISTS user_feed_tabs;
DROP TABLE IF EXISTS feed_items;
DROP TABLE IF EXISTS feed_sources;

CREATE TABLE feed_sources (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    url VARCHAR(256) NOT NULL,
    logo_url VARCHAR(256) NOT NULL,
    description VARCHAR(1000),
    user_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_feed_sources_1 FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE feed_items (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(1000) NOT NULL,
    description VARCHAR(10000),
    link VARCHAR(256) NOT NULL,
    published_date DATETIME NOT NULL,
    author VARCHAR(100),
    viewed BOOLEAN NOT NULL DEFAULT 0,
    image_url VARCHAR(256),
    feed_source_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_feed_items_1 FOREIGN KEY (feed_source_id) REFERENCES feed_sources (id)
);

CREATE TABLE user_feed_tabs (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    feed_item_id INT NOT NULL,
    position INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_feed_tabs_1 FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_feed_tabs_2 FOREIGN KEY (feed_item_id) REFERENCES feed_items (id)
);
