package com.woowacourse.gongseek.comment.domain.repository;

import com.woowacourse.gongseek.comment.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByArticleId(Long articleId);

    List<Comment> findAllByMemberIdIn(List<Long> memberIds);

    int countByArticleId(Long articleId);
}
