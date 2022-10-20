package com.woowacourse.gongseek.like.domain.repository;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.like.domain.Like;
import com.woowacourse.gongseek.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    void deleteByArticleAndMember(Article article, Member member);

    boolean existsByArticleIdAndMemberId(Long articleId, Long memberId);

    Long countByArticleId(Long articleId);

    void deleteAllByArticleId(Long articleId);
}
