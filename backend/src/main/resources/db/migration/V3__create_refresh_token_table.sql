create table refresh_token
(
       id BINARY(16) not null,
        expiry_date datetime(6),
        issue bit not null,
        member_id bigint,
        primary key (id)
) engine=InnoDB
