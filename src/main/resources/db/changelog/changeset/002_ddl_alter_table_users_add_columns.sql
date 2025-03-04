--liquibase formatted sql
--changeset anovoselov:alter_table_users_add_columns
ALTER TABLE users
ADD COLUMN first_arg BIGINT,
ADD COLUMN second_arg BIGINT,
ADD COLUMN result BIGINT;