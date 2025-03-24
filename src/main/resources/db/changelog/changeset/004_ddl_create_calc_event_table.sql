--liquibase formatted sql
--changeset novoselov:create_enum_calc_event_type
CREATE TYPE calc_event_type AS ENUM ('ADDITIONAL', 'SUBTRACTION', 'MULTIPLICATION', 'DIVISION');

--changeset novoselov:create_calc_event_table
CREATE TABLE calc_event (
id           SERIAL PRIMARY KEY,
user_id      INTEGER REFERENCES users(id),
first        DOUBLE PRECISION NOT NULL,
second       DOUBLE PRECISION NOT NULL,
result       DOUBLE PRECISION NOT NULL,
create_date  TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
calc_event_type calc_event_type NOT NULL
);