--liquibase formatted sql

--changeset Suresh_Thakare:1600981420129-1
CREATE TABLE role (id INT AUTO_INCREMENT NOT NULL, code VARCHAR(50) NULL, description VARCHAR(255) NULL, status VARCHAR(15) NULL, CONSTRAINT PK_ROLE PRIMARY KEY (id));
CREATE TABLE users (id INT AUTO_INCREMENT NOT NULL,enabled BIT(1) NOT NULL,password VARCHAR(255) NULL, status VARCHAR(30) NULL,username VARCHAR(255) NULL,email VARCHAR(100) NOT NULL, CONSTRAINT PK_USERS PRIMARY KEY (id));

--changeset Suresh_Thakare:1600981420129-2
CREATE TABLE users_roles (user_id INT NOT NULL, role_id INT NOT NULL);

--changeset Suresh_Thakare:1600981420129-3
ALTER TABLE role ADD CONSTRAINT UK_unique_code UNIQUE (code);
ALTER TABLE users ADD CONSTRAINT UK_unique_username_email UNIQUE (username,email);

--changeset Suresh_Thakare:1600981420129-4
CREATE INDEX FK_user_id ON users_roles(user_id);
CREATE INDEX FK_role_id ON users_roles(role_id);

--changeset Suresh_Thakare:1600981420129-5
ALTER TABLE users_roles ADD CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE users_roles ADD CONSTRAINT FK_role_id FOREIGN KEY (role_id) REFERENCES role (id) ON UPDATE RESTRICT ON DELETE RESTRICT;
