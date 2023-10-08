CREATE TABLE IF NOT EXISTS task (
    id serial primary key,
    product varchar(50) NOT NULL,
    price float8 NOT NULL
);