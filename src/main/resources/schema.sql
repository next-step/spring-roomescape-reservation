create table if not exists reservation (
    id bigint auto_increment,
    date varchar(100) not null,
    time varchar(100) not null,
    name varchar(100) not null
);

create table if not exists theme (
    id bigint auto_increment,
    name varchar(100) not null,
    desc varchar(100) not null,
    price numeric not null
);
