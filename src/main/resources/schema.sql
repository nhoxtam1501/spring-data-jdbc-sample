CREATE TABLE IF NOT EXISTS customer(
    id serial primary key,
    name text
);

CREATE TABLE IF NOT EXISTS customer_orders(
    id serial primary key,
    customer bigint not null references customer(id),
    name varchar(255)
);

CREATE TABLE IF NOT EXISTS customer_profile(
    id serial primary key,
    customer bigint not null references customer(id),
    username text not null,
    password text not null
);

delete from customer_profile;
delete from customer_orders;
delete from customer;

