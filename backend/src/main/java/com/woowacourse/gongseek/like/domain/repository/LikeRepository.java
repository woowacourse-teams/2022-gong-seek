package com.woowacourse.gongseek.like.domain.repository;

import com.woowacourse.gongseek.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Modifying
    @Query(value = "delete from Likes l where l.article.id = :articleId and l.member.id = :memberId")
    void deleteByArticleIdAndMemberId(@Param("articleId") Long articleId, @Param("memberId") Long memberId);

    boolean existsByArticleIdAndMemberId(Long articleId, Long memberId);

    Long countByArticleId(Long articleId);
}
