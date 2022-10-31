alter table article drop comment_count;
alter table article drop version;
alter table vote_item drop version;
alter table vote_history drop index idx_vote_vote_history;
alter table comment drop index idx_comment_article;
