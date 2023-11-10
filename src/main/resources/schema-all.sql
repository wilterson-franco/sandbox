DROP TABLE person IF EXISTS;
DROP TABLE customer IF EXISTS;

CREATE TABLE person  (
  id BIGINT NOT NULL PRIMARY KEY,
  first_name VARCHAR(20),
  last_name VARCHAR(20)
);

CREATE TABLE customer (
  id BIGINT NOT NULL PRIMARY KEY,
  first_name VARCHAR(20),
  last_name VARCHAR(20)
);