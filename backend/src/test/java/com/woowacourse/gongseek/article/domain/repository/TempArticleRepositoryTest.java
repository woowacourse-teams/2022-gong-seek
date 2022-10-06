package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.Content;
import com.woowacourse.gongseek.article.domain.TempArticle;
import com.woowacourse.gongseek.article.domain.TempTags;
import com.woowacourse.gongseek.article.domain.Title;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.RepositoryTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class TempArticleRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TempArticleRepository tempArticleRepository;

    private Member member;
    private TempArticle tempArticle;

    @BeforeEach
    public void setUp() {
        member = memberRepository.save(new Member("slow", "hanull", "avatarUrl"));
        tempArticle = tempArticleRepository.save(TempArticle.builder()
                .title(new Title("title"))
                .content(new Content("content"))
                .category(Category.QUESTION)
                .member(member)
                .tempTags(new TempTags(List.of("spring")))
                .isAnonymous(false)
                .build());
    }

    @ValueSource(strings = {"a", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    @ParameterizedTest
    void 임시_게시글을_저장한다_제목_길이는_최소1에서_최대500자까지_가능하다(String title) {
        System.out.println(title.length());
        final TempArticle tempArticle = TempArticle.builder()
                .title(new Title(title))
                .content(new Content("content"))
                .category(Category.QUESTION)
                .member(member)
                .tempTags(new TempTags(List.of("spring")))
                .isAnonymous(false)
                .build();

        final TempArticle savedTempArticle = tempArticleRepository.save(tempArticle);

        assertThat(savedTempArticle).isEqualTo(tempArticle);
    }

    @Test
    void 전체_임시_게시글을_조회한다() {
        final TempArticle tempArticle2 = TempArticle.builder()
                .title(new Title("title2"))
                .content(new Content("content2"))
                .category(Category.QUESTION)
                .member(member)
                .tempTags(new TempTags(List.of("spring2")))
                .isAnonymous(false)
                .build();
        tempArticleRepository.save(tempArticle2);

        final List<TempArticle> tempArticles = tempArticleRepository.findAll();

        assertAll(
                () -> assertThat(tempArticles).hasSize(2),
                () -> assertThat(tempArticles).isEqualTo(List.of(tempArticle, tempArticle2))
        );
    }

    @Test
    void 단건_임시_게시글을_조회한다() {
        final TempArticle foundTempArticle = tempArticleRepository.findById(tempArticle.getId())
                .get();

        assertThat(foundTempArticle).isEqualTo(tempArticle);
    }

    @Test
    void 임시_게시글을_삭제한다() {
        tempArticleRepository.delete(tempArticle);

        assertThat(tempArticleRepository.findById(tempArticle.getId())).isEmpty();
    }
}
