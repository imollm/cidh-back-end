create table user_event_favorites
(
    user_id    varchar   not null,
    event_id   varchar   not null,
    created_at timestamp not null,

    constraint pk_user_event_favorites primary key (user_id, event_id),
    constraint fk_user_event_favorites_event_id foreign key (event_id) references event (id),
    constraint fk_user_event_favorites_user_id foreign key (user_id) references "user" (id)
);

create table user_event_rating
(
    user_id    varchar   not null,
    event_id   varchar   not null,
    created_at timestamp not null,
    rating     int       not null,

    constraint pk_user_event_rating primary key (user_id, event_id),
    constraint fk_user_event_rating_event_id foreign key (event_id) references event (id),
    constraint fk_user_event_rating_user_id foreign key (user_id) references "user" (id)

);

create table user_event_comment
(
    user_id    varchar   not null,
    event_id   varchar   not null,
    created_at timestamp not null,
    comment    varchar   not null,

    constraint pk_user_event_comment primary key (user_id, event_id),
    constraint fk_user_event_comment_event_id foreign key (event_id) references event (id),
    constraint fk_user_event_comment_user_id foreign key (user_id) references "user" (id)

);

create table user_event_subscription
(
    user_id    varchar   not null,
    event_id   varchar   not null,
    created_at timestamp not null,

    constraint pk_user_event_subscription primary key (user_id, event_id),
    constraint fk_user_event_subscription_event_id foreign key (event_id) references event (id),
    constraint fk_user_event_subscription_user_id foreign key (user_id) references "user" (id)
);

create table event_forum_message
(
    id             varchar   not null primary key,
    author_user_id varchar   not null,
    event_id       varchar   not null,
    parent_id      varchar,
    created_at     timestamp not null,
    message        varchar   not null,

    constraint fk_forum_author foreign key (author_user_id) references "user" (id),
    constraint fk_parent_comment foreign key (parent_id) references event_forum_message (id),
    constraint fk_forum_event_id foreign key (event_id) references event (id)

);

alter table event
    add column event_url varchar not null default 'https://www.youtube.com/watch?v=NpEaa2P7qZI';
