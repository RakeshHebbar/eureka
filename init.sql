CREATE TABLE IF NOT EXISTS users (
    id serial primary key,
    username varchar not null,
    password varchar not null
);


CREATE TABLE IF NOT EXISTS events (
    id varchar primary key,
    name varchar,
    event_url varchar
);

CREATE TABLE IF NOT EXISTS interested_events (
    event_id varchar not null,
    user_id integer not null,
    PRIMARY KEY(event_id, user_id)
);


CREATE TABLE IF NOT EXISTS venue (
    id varchar primary key,
    name varchar,
    event_id varchar,
    latitude varchar,
    longitude varchar
);


CREATE EXTENSION IF NOT EXISTS cube;
CREATE EXTENSION IF NOT EXISTS earthdistance;