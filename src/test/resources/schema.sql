create table IF NOT EXISTS user_table
(
    id         int         not null auto_increment,
    email   varchar(50) not null,
    first_name varchar(50) not null,
    last_name  varchar(50) not null,
    password   varchar(50) not null,
    PRIMARY KEY (id)
);