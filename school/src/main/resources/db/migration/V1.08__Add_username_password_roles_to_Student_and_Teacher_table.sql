create sequence login_user_seq start with 1 increment by 50;

create table login_user
(
    date_of_birth date,
    id            bigint not null,
    name          varchar(255),
    password      varchar(255),
    username      varchar(255),
    primary key (id)
);
create table login_user_aud
(
    date_of_birth date,
    rev           integer not null,
    revtype       smallint,
    id            bigint  not null,
    name          varchar(255),
    password      varchar(255),
    username      varchar(255),
    primary key (rev, id)
);

ALTER TABLE student
    DROP COLUMN date_of_birth,
    DROP COLUMN name;

ALTER TABLE student_aud
    DROP COLUMN date_of_birth,
    DROP COLUMN name;

ALTER TABLE teacher
    DROP COLUMN date_of_birth,
    DROP COLUMN name;

ALTER TABLE teacher_aud
    DROP COLUMN date_of_birth,
    DROP COLUMN name;

alter table if exists login_user_aud
    add constraint FKfjlavr6bxwi9t984luyiagjh4 foreign key (rev) references revinfo;
alter table if exists student
    add constraint FKcbk0gdrrasxt482fh8bh25esa foreign key (id) references login_user;
alter table if exists student_aud
    add constraint FK5lbpems03x3klu6oghqt0iosu foreign key (rev, id) references login_user_aud;
alter table if exists teacher
    add constraint FKop4t149qiua424v6lohmb5ecc foreign key (id) references login_user;
alter table if exists teacher_aud
    add constraint FKr55nmrct0dvg0ausw1ie693jd foreign key (rev, id) references login_user_aud;
