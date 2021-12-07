create type SystemLanguageType as enum (
    'English',
    'Catalan',
    'Spanish',
    'French'
    );

CREATE TABLE "user"
(
    id                 varchar PRIMARY KEY,
    first_name          varchar,
    last_name          varchar,
    fiscal_id           varchar,
    address            varchar,
    email              varchar UNIQUE,
    email_token        varchar,
    is_valid_email     boolean                     default false,
    preferred_language SystemLanguageType NOT NULL DEFAULT 'English',
    password           varchar            NOT NULL,
    deleted_at         timestamp,
    created_at         timestamp
);

CREATE TABLE password_reset
(
    id                varchar PRIMARY KEY,
    user_id           varchar   NOT NULL,
    hashed_token      varchar   NOT NULL,
    requester_ip_addr varchar,
    created_at        timestamp NOT NULL,
    used_at           timestamp,

    CONSTRAINT user_reset_password FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE role
(
    id                   varchar PRIMARY KEY,
    role_name            varchar,
    role_definition_json json
);

CREATE TABLE user_role
(
    id     varchar PRIMARY KEY,
    "user" varchar NOT NULL,
    role   varchar NOT NULL,
    CONSTRAINT role_user FOREIGN KEY ("user") REFERENCES "user" (id) ON DELETE CASCADE,
    CONSTRAINT role_user_role FOREIGN KEY (role) REFERENCES role (id),
    CONSTRAINT unique_pairs UNIQUE ("user", role)
);

INSERT INTO role(id, role_name, role_definition_json)
VALUES ('ADMIN', 'ADMINISTRATOR', '{}'),
       ('USER', 'USER', '{}'),
       ('SUPERADMIN', 'SUPERADMIN', '{}');
