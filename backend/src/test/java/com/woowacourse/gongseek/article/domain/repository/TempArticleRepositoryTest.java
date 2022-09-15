package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.TempArticle;
import com.woowacourse.gongseek.config.JpaAuditingConfig;
import com.woowacourse.gongseek.config.QuerydslConfig;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.RepositoryTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@RepositoryTest
class TempArticleRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TempArticleRepository tempArticleRepository;

    private Member member;

    @BeforeEach
    public void setUp() {
        member = memberRepository.save(new Member("slow", "hanull", "avatarUrl"));
    }

    @Test
    void 임시_게시글을_저장한다() {
        final TempArticle tempArticle = new TempArticle("title", "content", Category.DISCUSSION.getValue(),
                member, List.of("spring"), false);

        final TempArticle savedTempArticle = tempArticleRepository.save(tempArticle);

        assertThat(savedTempArticle).isEqualTo(tempArticle);
    }

    @Test
    void 전체_임시_게시글을_조회한다() {
        final TempArticle request1 = new TempArticle("title", "content", Category.DISCUSSION.getValue(), member,
                List.of("spring"), false);
        final TempArticle request2 = new TempArticle("title2", "content2", Category.QUESTION.getValue(), member,
                List.of("spring2"), false);
        tempArticleRepository.save(request1);
        tempArticleRepository.save(request2);

        final List<TempArticle> tempArticles = tempArticleRepository.findAll();

        assertAll(
                () -> assertThat(tempArticles).hasSize(2),
                () -> assertThat(tempArticles).isEqualTo(List.of(request1, request2))
        );
    }

    @Test
    void 단건_임시_게시글을_조회한다() {
        final TempArticle tempArticle = tempArticleRepository.save(
                new TempArticle("title", "content", Category.DISCUSSION.getValue(), member, List.of("spring"), false));

        final TempArticle foundTempArticle = tempArticleRepository.findById(tempArticle.getId())
                .get();

        assertThat(foundTempArticle).isEqualTo(tempArticle);
    }

    @Test
    void 임시_게시글을_삭제한다() {
        final TempArticle tempArticle = tempArticleRepository.save(
                new TempArticle("title", "content", Category.DISCUSSION.getValue(), member, List.of("spring"), false));

        tempArticleRepository.delete(tempArticle);

        assertThat(tempArticleRepository.findById(tempArticle.getId())).isEmpty();
    }
}
