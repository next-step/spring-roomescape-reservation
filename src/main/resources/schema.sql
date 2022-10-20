drop table if exists reservation;

create table reservation
(
    id   bigint      not null primary key auto_increment comment '예약 아이디',
    date date        not null default now() comment '예약 날짜',
    time time        not null default now() comment '예약 시간',
    name varchar(32) not null comment '예약자 이름'
);