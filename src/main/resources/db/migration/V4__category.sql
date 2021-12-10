CREATE TABLE category (
    id          VARCHAR PRIMARY KEY,
    name        VARCHAR UNIQUE,
    description VARCHAR NOT NULL,
    created_at  TIMESTAMP NOT NULL
);

INSERT INTO category VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4a', 'Category 1', 'Description 1', '2021-03-31 09:30:20-07');
INSERT INTO category VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4b', 'Category 2', 'Description 2', '2021-03-31 09:30:20-07');
INSERT INTO category VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4c', 'Category 3', 'Description 3', '2021-03-31 09:30:20-07');
INSERT INTO category VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4d', 'Category 4', 'Description 4', '2021-03-31 09:30:20-07');
INSERT INTO category VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4e', 'Category 5', 'Description 5', '2021-03-31 09:30:20-07');
INSERT INTO category VALUES ('d5a8fae9-d376-4e37-a5b0-46f9128beb4f', 'Category 6', 'Description 6', '2021-03-31 09:30:20-07');