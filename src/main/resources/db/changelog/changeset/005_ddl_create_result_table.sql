--liquibase formatted sql

--changeset novoselov:create_result_table
CREATE TABLE results (
id           SERIAL PRIMARY KEY,
first        DOUBLE PRECISION NOT NULL,
second       DOUBLE PRECISION NOT NULL,
result       DOUBLE PRECISION NOT NULL,
create_date  TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
calc_event_type VARCHAR(20) NOT NULL
);