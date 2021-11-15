INSERT INTO users (user_name, password, create_time) values ('admin', '$2a$10$yFDwc5f1b.regd3b..0r0OzfLtd9ugXKw0fPePrO3wyGIo2vAP9Yq', now());

INSERT INTO users_and_roles (users_id, roles) values ((select id from users where user_name = 'admin'), 'ROLE_ADMIN');