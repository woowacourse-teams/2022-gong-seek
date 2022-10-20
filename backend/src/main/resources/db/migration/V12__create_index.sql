CREATE INDEX idx_vote_vote_history ON vote_history (vote_item_id);
CREATE INDEX idx_comment_article ON comment (article_id);
CREATE INDEX idx_likes_article ON likes (article_id);
