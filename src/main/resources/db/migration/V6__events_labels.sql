create table event
(
    id           varchar primary key,
    name         varchar not null unique,
    description  varchar not null,
    header_image varchar,
    start_date   timestamp,
    end_date     timestamp,

    organizer_id varchar,
    constraint fk_event_event_organizer foreign key (organizer_id) references event_organizer (id),

    category_id  varchar,
    constraint fk_event_category foreign key (category_id) references category (id)
);

create table label
(
    id          VARCHAR PRIMARY KEY,
    name        VARCHAR UNIQUE,
    description VARCHAR   NOT NULL,
    created_at  TIMESTAMP NOT NULL
);

create table label_event
(
    event_id   varchar not null,
    label_id varchar not null,
    constraint fk_label_event_event_id foreign key (event_id) references event (id),
    constraint fk_label_event_label_id foreign key (label_id) references label (id)
);