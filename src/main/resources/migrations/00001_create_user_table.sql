--liquibase formatted sql

--changeset nikita.klimenko:1
CREATE TABLE user (
  id CHAR(36) NOT NULL,
  first_name VARCHAR(64) NULL,
  last_name VARCHAR(64) NULL,
  contacts VARCHAR(128) NULL,
  admin BOOL,
  locale VARCHAR(10),
  secret CHAR(36) NOT NULL,
  custom_properties JSON NULL DEFAULT NULL,
  PRIMARY KEY (id));

--rollback DROP TABLE user;