package com.woowacourse.gongseek.comment.domain.repository;

import com.woowacourse.gongseek.comment.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.article where c.member.id = :memberId")
    List<Comment> findAllByMemberId(@Param("memberId") Long memberId);

    @Query("select c from Comment c join fetch c.member where c.article.id = :articleId")
    List<Comment> findAllByArticleIdWithMember(@Param("articleId") Long articleId);

    long countByArticleId(Long articleId);
}
