CREATE TABLE event_organizer(
    id              VARCHAR PRIMARY KEY,
    name            VARCHAR UNIQUE,
    description     VARCHAR DEFAULT NULL,
    admin           VARCHAR NOT NULL,
    created_at      TIMESTAMP NOT NULL,

    CONSTRAINT fk_admin FOREIGN KEY (admin) REFERENCES "user" (id)
);