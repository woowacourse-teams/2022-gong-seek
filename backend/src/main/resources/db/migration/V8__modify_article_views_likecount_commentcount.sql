alter table article change views views bigint not null;
alter table article add comment_count bigint not null;
alter table article add like_count bigint not null;
