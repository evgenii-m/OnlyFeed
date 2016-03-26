USE simplefeed_db;

DROP TABLE IF EXISTS user_roles;
CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id)
);