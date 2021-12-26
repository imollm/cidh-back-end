CREATE TABLE category (
    id          VARCHAR PRIMARY KEY,
    name        VARCHAR UNIQUE,
    description VARCHAR NOT NULL,
    created_at  TIMESTAMP NOT NULL
);
