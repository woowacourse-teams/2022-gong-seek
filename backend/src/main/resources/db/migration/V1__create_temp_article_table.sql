create table temp_article
(
    id           bigint       not null auto_increment,
    category     varchar(255) not null,
    content      text         not null,
    created_at   datetime(6),
    is_anonymous bit          not null,
    temp_tags    varchar(255),
    title        varchar(255) not null,
    member_id    bigint       not null,
    primary key (id)
) engine=InnoDB

alter table temp_article
    add constraint FKsraq3bq90l7xinsr8pfxqy4xm
        foreign key (member_id)
            references member (id)
            on delete cascade
