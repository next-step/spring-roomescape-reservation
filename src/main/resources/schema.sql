create table if not exists reservation (
    id bigint not null auto_increment primary key,
    date date not null,
    time time not null,
    name varchar(255) not null
);

create table if not exists theme (
    id bigint not null auto_increment primary key,
    name varchar(255) not null,
    desc varchar(255) not null,
    price int not null
);

create table if not exists schedule (
    id bigint not null auto_increment primary key,
    theme_id bigint not null,
    date date not null,
    time time not null
);
