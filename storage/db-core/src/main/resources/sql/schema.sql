create table reservation_times
(
    time_id    bigint       not null auto_increment,
    start_at   varchar(255) not null,
    created_at varchar(255) not null,
    primary key (time_id)
);

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

CREATE TABLE themes
(
    theme_id          BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    thumbnail   VARCHAR(255) NOT NULL,
    PRIMARY KEY (theme_id)
);