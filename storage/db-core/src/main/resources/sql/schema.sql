DROP TABLE IF EXISTS reservations;
create table reservations
(
    reservation_id bigint       not null auto_increment,
    theme_id       bigint       not null,
    name           varchar(255) not null,
    date           varchar(255) not null,
    time_id        bigint       not null,
    active_status  varchar(255) not null default 'ACTIVE',
    deleted_at     varchar(255),
    created_at     varchar(255) not null,
    primary key (reservation_id)
);

DROP TABLE IF EXISTS reservation_times;
create table reservation_times
(
    time_id    bigint       not null auto_increment,
    start_at   varchar(255) not null,
    created_at varchar(255) not null,
    primary key (time_id)
);

DROP TABLE IF EXISTS themes;
CREATE TABLE themes
(
    theme_id      BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255) NOT NULL,
    description   VARCHAR(255) NOT NULL,
    thumbnail     VARCHAR(255) NOT NULL,
    active_status VARCHAR(255) NOT NULL DEFAULT 'ACTIVE',
    PRIMARY KEY (theme_id)
);
