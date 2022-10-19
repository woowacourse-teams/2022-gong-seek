create index article_likes on article(like_count desc, id desc);
create index article_views on article(views desc, id desc);
