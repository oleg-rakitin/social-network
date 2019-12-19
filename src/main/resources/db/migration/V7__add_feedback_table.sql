CREATE TABLE if not exists feedback
(
    id int8 not null,
    email varchar(255),
    name  varchar(255),
    phone varchar(255),
    message text
);