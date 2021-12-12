create table event
(
    id           varchar primary key,
    name         varchar not null unique,
    description  varchar not null,
    header_image varchar,
    start_date   timestamp,
    end_date     timestamp,

    organizer    varchar,
    constraint fk_event_organizer_event foreign key (organizer) references event_organizer (id),

    category     varchar,
    constraint fk_event_category foreign key (category) references category (id)
);

create table label_event
(
    event_id    varchar not null,
    category_id varchar not null,
    constraint label_event_event_id foreign key (event_id) references event (id),
    constraint label_event_category_id foreign key (category_id) references category (id)
);