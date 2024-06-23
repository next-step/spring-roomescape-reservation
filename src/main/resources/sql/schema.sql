drop table if exists reservations;

create table reservations
(
    id          bigint       not null auto_increment,
    name        varchar(255) not null,
    datetime    timestamp    not null,
    status      varchar(255) not null default 'CONFIRMED',
    canceled_at timestamp,
    created_at  timestamp    not null,
    primary key (id)
);