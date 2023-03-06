create table message
(
    id              bigint not null auto_increment,
    filename        varchar(255),
    tag             varchar(255),
    text_of_message varchar(2048) not null,
    user_id         bigint,
    primary key (id)
) engine=InnoDB DEFAULT CHARSET=UTF8;
create table user_role
(
    user_id bigint not null,
    roles   varchar(255)
) engine=InnoDB DEFAULT CHARSET=UTF8;
create table usr
(
    id              bigint not null auto_increment,
    activation_code varchar(255),
    active          bit,
    email           varchar(255),
    password        varchar(255) not null,
    username        varchar(255) not null,
    primary key (id)
) engine=InnoDB DEFAULT CHARSET=UTF8;
alter table message
    add constraint message_user_fk foreign key (user_id) references usr (id);
alter table user_role
    add constraint user_role_fk foreign key (user_id) references usr (id);