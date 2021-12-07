create table refresh_token
(
    id          varchar primary key,
    user_id     varchar   not null,
    constraint refresh_token_user_id foreign key (user_id) references "user" (id),
    expiry_date timestamp not null
)