USE simplefeed_db;

DROP TABLE IF EXISTS user_roles;
CREATE TABLE user_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_1 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_2 FOREIGN KEY (role_id) REFERENCES roles (id)
);