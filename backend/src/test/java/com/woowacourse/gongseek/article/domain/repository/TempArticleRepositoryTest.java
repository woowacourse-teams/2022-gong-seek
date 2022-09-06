package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.TempArticle;
import com.woowacourse.gongseek.config.JpaAuditingConfig;
import com.woowacourse.gongseek.config.QuerydslConfig;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import({JpaAuditingConfig.class, QuerydslConfig.class})
@DataJpaTest
class TempArticleRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TempArticleRepository tempArticleRepository;

    @Test
    void 임시_게시글을_저장한다() {
        final Member member = memberRepository.save(new Member("slow", "hanull", "avatarUrl"));
        final TempArticle tempArticle = new TempArticle("title", "content", Category.DISCUSSION,
                member, List.of("spring"), false);

        final TempArticle savedTempArticle = tempArticleRepository.save(tempArticle);

        assertThat(savedTempArticle).isEqualTo(tempArticle);
    }

    @Test
    void 전체_임시_게시글을_조회한다() {
        final Member member = memberRepository.save(new Member("slow", "hanull", "avatarUrl"));
        final TempArticle request1 = new TempArticle("title", "content", Category.DISCUSSION, member, List.of("spring"),
                false);
        final TempArticle request2 = new TempArticle("title2", "content2", Category.QUESTION, member,
                List.of("spring2"), false);
        tempArticleRepository.save(request1);
        tempArticleRepository.save(request2);

        final List<TempArticle> tempArticles = tempArticleRepository.findAll();

        assertAll(
                () -> assertThat(tempArticles).hasSize(2),
                () -> assertThat(tempArticles).isEqualTo(List.of(request1, request2))
        );
    }
}
