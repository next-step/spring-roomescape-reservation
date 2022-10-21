drop table if exists reservation;
drop table if exists themes;
drop table if exists schedules;

create table reservation
(
    id   bigint      not null primary key auto_increment comment '예약 아이디',
    date date        not null default now() comment '예약 날짜',
    time time        not null default now() comment '예약 시간',
    name varchar(32) not null comment '예약자 이름'
);

create table themes
(
    id    bigint       not null primary key auto_increment comment '테마 아이디',
    name  varchar(32)  not null comment '테마 이름',
    desc  varchar(128) not null comment '테마 설명',
    price bigint       not null comment '테마 가격'
);

create table schedules
(
    id       bigint not null primary key auto_increment comment '스케줄 아이디',
    theme_id bigint not null comment '테마 아이디',
    date     date   not null default now() comment '스케줄 날짜',
    time     time   not null default now() comment '스케줄 시간'
);
