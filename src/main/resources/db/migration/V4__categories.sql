CREATE TABLE categories (
    id          VARCHAR PRIMARY KEY,
    name        VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    created_at  TIMESTAMP NOT NULL
);