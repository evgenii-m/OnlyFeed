USE onlyfeed_db;

DROP TABLE IF EXISTS user_feed_tabs;
DROP TABLE IF EXISTS feed_items;
DROP TABLE IF EXISTS feed_sources;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS persistent_logins;

CREATE TABLE persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) NOT NULL,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL,
    PRIMARY KEY (series)
);

CREATE TABLE users (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(64) NOT NULL,
    email VARCHAR(64) NOT NULL UNIQUE,
    picture_url VARCHAR(512) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT 1,
    feed_view_type TINYINT NOT NULL DEFAULT 0,
    feed_sorting_type TINYINT NOT NULL DEFAULT 0,
    feed_filter_type TINYINT NOT NULL DEFAULT 0,
    feed_panel_pos BOOLEAN NOT NULL DEFAULT 0,
    news_storage_time_hours INT NOT NULL DEFAULT 168,
    PRIMARY KEY (id)
);

CREATE TABLE roles (
    id INT NOT NULL AUTO_INCREMENT,
    role VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);
INSERT INTO roles (role) values ("ROLE_ADMIN"); 
INSERT INTO roles (role) values ("ROLE_USER"); 

CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_1 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_2 FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE feed_sources (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    url VARCHAR(512) NOT NULL,
    logo_url VARCHAR(512) NOT NULL,
    description VARCHAR(1000),
    user_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_feed_sources_1 FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE feed_items (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(500) NOT NULL,
    description VARCHAR(10000),
    link VARCHAR(512) NOT NULL,
    published_date DATETIME NOT NULL,
    author VARCHAR(100),
    viewed BOOLEAN NOT NULL DEFAULT 0,
    image_url VARCHAR(512),
    feed_source_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_feed_items_1 FOREIGN KEY (feed_source_id) REFERENCES feed_sources (id)
);

CREATE TABLE user_feed_tabs (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    feed_item_id INT NOT NULL,
    prev_tab_id INT,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_feed_tabs_1 FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_feed_tabs_2 FOREIGN KEY (feed_item_id) REFERENCES feed_items (id)
);
