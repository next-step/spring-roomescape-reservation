create table if not exists reservation (
    id bigint not null auto_increment primary key,
    date date not null,
    time datetime not null,
    name varchar(255) not null
);
