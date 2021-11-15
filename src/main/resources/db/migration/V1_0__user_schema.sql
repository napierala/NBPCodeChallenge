CREATE TABLE users(
    id IDENTITY NOT NULL,
    create_time TIMESTAMP NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_name VARCHAR(255) NOT NULL,
    CONSTRAINT users_primary_key PRIMARY KEY (id),
    CONSTRAINT users_user_name_unique_key UNIQUE KEY (user_name)
);

CREATE TABLE users_and_roles (
    users_id BIGINT NOT NULL,
    roles VARCHAR(255),
    CONSTRAINT users_and_roles_users_id_fkey FOREIGN KEY (users_id) references users (id)
);