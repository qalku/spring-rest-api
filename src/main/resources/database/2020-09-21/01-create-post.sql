--liquibase formatted sql

--changeset mwrona:1
CREATE TABLE POST (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(200) NULL,
    created timestamp
);