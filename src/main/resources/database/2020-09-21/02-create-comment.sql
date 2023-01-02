--liquibase formatted sql

--changeset mwrona:2
CREATE TABLE COMMENT (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    content VARCHAR(255) NULL,
    created timestamp
);

--changeset mwrona:3
// klucz obcy relacji
ALTER TABLE COMMENT
    ADD CONSTRAINT comment_post_id
    FOREIGN KEY (post_id) REFERENCES post(id)