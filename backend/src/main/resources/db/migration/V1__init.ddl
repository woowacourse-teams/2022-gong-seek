create table article
(
    id           bigint  not null auto_increment,
    created_at   datetime(6),
    updated_at   datetime(6),
    category     enum ('QUESTION', 'DISCUSSION'),
    content      text    not null,
    is_anonymous bit     not null,
    title        text    not null,
    views        integer not null,
    member_id    bigint  not null,
    primary key (id)
) engine = InnoDB;

create table article_tag
(
    id         bigint not null auto_increment,
    article_id bigint,
    tag_id     bigint,
    primary key (id)
) engine = InnoDB;

create table comment
(
    id           bigint         not null auto_increment,
    created_at   datetime(6),
    updated_at   datetime(6),
    content      varchar(10000) not null,
    is_anonymous bit            not null,
    article_id   bigint         not null,
    member_id    bigint         not null,
    primary key (id)
) engine = InnoDB;

create table likes
(
    id         bigint not null auto_increment,
    article_id bigint not null,
    member_id  bigint not null,
    primary key (id)
) engine = InnoDB;

create table member
(
    id         bigint       not null auto_increment,
    avatar_url varchar(255) not null,
    github_id  varchar(255) not null,
    name       varchar(255) not null,
    primary key (id)
) engine = InnoDB;

create table tag
(
    id   bigint      not null auto_increment,
    name varchar(20) not null,
    primary key (id)
) engine = InnoDB;

create table vote
(
    id         bigint not null auto_increment,
    created_at datetime(6),
    expiry_at  datetime(6) not null,
    article_id bigint not null,
    primary key (id)
) engine = InnoDB;

create table vote_history
(
    id           bigint not null auto_increment,
    member_id    bigint not null,
    vote_id      bigint not null,
    vote_item_id bigint not null,
    primary key (id)
) engine = InnoDB;

create table vote_item
(
    id      bigint       not null auto_increment,
    amount  integer      not null,
    content varchar(500) not null,
    vote_id bigint       not null,
    primary key (id)
) engine = InnoDB;

alter
    table article
    add constraint FK6l9vkfd5ixw8o8kph5rj1k7gu
        foreign key (member_id)
            references member (id)
            on delete
                cascade;

alter
    table article_tag
    add constraint FKenqeees0y8hkm7x1p1ittuuye
        foreign key (article_id)
            references article (id)
            on delete
                cascade;

alter
    table article_tag
    add constraint FKesqp7s9jj2wumlnhssbme5ule
        foreign key (tag_id)
            references tag (id)
            on delete
                cascade;

alter
    table comment
    add constraint FK5yx0uphgjc6ik6hb82kkw501y
        foreign key (article_id)
            references article (id)
            on delete
                cascade;

alter
    table comment
    add constraint FKmrrrpi513ssu63i2783jyiv9m
        foreign key (member_id)
            references member (id);

alter
    table likes
    add constraint FK1hlv6urq91y6fqfg6bds5gvp9
        foreign key (article_id)
            references article (id)
            on delete
                cascade;

alter
    table likes
    add constraint FKa4vkf1skcfu5r6o5gfb5jf295
        foreign key (member_id)
            references member (id)
            on delete
                cascade;

alter
    table vote
    add constraint FKr0cbtnr3ogukmmkj8041tf3v3
        foreign key (article_id)
            references article (id)
            on delete
                cascade;

alter
    table vote_history
    add constraint FK70y4h3fmm014b2g316vx8a3ss
        foreign key (member_id)
            references member (id)
            on delete
                cascade;

alter
    table vote_history
    add constraint FKn6wt7p7qiemilpv5juak1y45g
        foreign key (vote_id)
            references vote (id)
            on delete
                cascade;

alter
    table vote_history
    add constraint FKa7aj8mtky83ty4x7qkykwdkvl
        foreign key (vote_item_id)
            references vote_item (id)
            on delete
                cascade;

alter
    table vote_item
    add constraint FKky2h5qevbsp1vtwsmjig0mnvt
        foreign key (vote_id)
            references vote (id)
            on delete
                cascade;
