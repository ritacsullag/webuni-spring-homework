CREATE SEQUENCE teacher_seq START WITH 1 INCREMENT BY 50;
create table teacher
(
    date_of_birth date,
    id            integer not null,
    name          varchar(255),
    primary key (id)
);

