begin transaction;

drop sequence if exists blog_sequence;

create sequence blog_sequence
    start 1
    increment 1;

create table role (
    id int4 not null,
    role_name varchar(255),
    primary key (id));

    create table role_permissions (
        role_id int4 not null,
        permissions varchar(255));

    create table users (
        id int4 not null,
        password varchar(255),
        username varchar(255),
        role_id int4 not null,
        primary key (id)
        );

    alter table if exists role
        add constraint unique_role_name unique (role_name);

    create sequence hibernate_sequence start 1 increment 1;

    alter table if exists users add constraint
        user_role_FK foreign key (role_id) references role;

    alter table if exists role_permissions add constraint
        role_permissions_role_FK foreign key (role_id) references role;

end transaction;