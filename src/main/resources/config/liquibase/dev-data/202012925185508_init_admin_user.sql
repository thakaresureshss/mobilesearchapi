--liquibase formatted sql

--changeset Suresh_Thakare:1601060116516-3
INSERT INTO role (id, code, description, status) VALUES (1, 'ROLE_ADMIN', 'This role is used for fully administration purpose', 'ACTIVE');


--changeset Suresh_Thakare:1601060116516-5
INSERT INTO users (id, enabled, password, status, username,email) VALUES (1,  1, '$2y$12$3Hqb9rMEElSZQA9HhlOige4FgJbapb8xipYpnpOIbFf4uCcEeoulG', 'ACTIVE', 'admin',"admin@admin.com");

--changeset Suresh_Thakare:1601060116516-6
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);

