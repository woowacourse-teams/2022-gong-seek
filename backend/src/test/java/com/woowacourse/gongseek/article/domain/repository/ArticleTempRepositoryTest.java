package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.article.domain.ArticleTemp;
import com.woowacourse.gongseek.article.domain.Category;
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
class ArticleTempRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleTempRepository articleTempRepository;

    @Test
    void 임시_게시글을_저장한다() {
        final Member member = memberRepository.save(new Member("slow", "hanull", "avatarUrl"));
        final ArticleTemp articleTemp = new ArticleTemp("title", "content", Category.DISCUSSION,
                member, List.of("spring"), false);

        final ArticleTemp savedArticleTemp = articleTempRepository.save(articleTemp);

        assertThat(savedArticleTemp).isEqualTo(articleTemp);
    }
}
