create sequence revinfo_seq start with 1 increment by 50;
create table course_aud
(
    rev     integer not null,
    revtype smallint,
    id      bigint  not null,
    name    varchar(255),
    primary key (rev, id)
);
create table course_students_aud
(
    rev         integer not null,
    revtype     smallint,
    courses_id  bigint  not null,
    students_id bigint  not null,
    primary key (rev, courses_id, students_id)
);
create table course_teachers_aud
(
    rev         integer not null,
    revtype     smallint,
    courses_id  bigint  not null,
    teachers_id bigint  not null,
    primary key (rev, courses_id, teachers_id)
);
create table revinfo
(
    rev      integer not null,
    revtstmp bigint,
    primary key (rev)
);
create table student_aud
(
    date_of_birth      date,
    rev                integer not null,
    revtype            smallint,
    semester           integer,
    used_free_semester integer,
    central_id         bigint,
    id                 bigint  not null,
    name               varchar(255),
    primary key (rev, id)
);
create table teacher_aud
(
    date_of_birth date,
    rev           integer not null,
    revtype       smallint,
    id            bigint  not null,
    name          varchar(255),
    primary key (rev, id)
);
alter table if exists course_aud
    add constraint FK7wota7b9g9bu9by751v8r8j65 foreign key (rev) references revinfo;
alter table if exists course_students_aud
    add constraint FK53xchn8n2cpmyfr1tvaa7ndvv foreign key (rev) references revinfo;
alter table if exists course_teachers_aud
    add constraint FKhxsr1pho67e2es9pv4v0js9l9 foreign key (rev) references revinfo;
alter table if exists student_aud
    add constraint FKj009xm0wjydklskl2dgnfyyjq foreign key (rev) references revinfo;
alter table if exists teacher_aud
    add constraint FKsg6tnk689ja9qg8qhnyarygx5 foreign key (rev) references revinfo;
