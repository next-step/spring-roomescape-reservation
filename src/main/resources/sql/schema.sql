drop table if exists reservation_times cascade;
create table reservation_times
(
    time_id    bigint       not null auto_increment,
    start_at   varchar(255) not null,
    created_at varchar(255) not null,
    primary key (time_id)
);


drop table if exists reservations cascade;
create table reservations
(
    reservation_id bigint       not null auto_increment,
    name           varchar(255) not null,
    date           varchar(255) not null,
    time_id        bigint       not null,
    status         varchar(255) not null default 'CONFIRMED',
    canceled_at    varchar(255),
    created_at     varchar(255) not null,
    primary key (reservation_id)
);