package com.woowacourse.gongseek.article.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.TempArticle;
import com.woowacourse.gongseek.article.domain.repository.TempArticleRepository;
import com.woowacourse.gongseek.article.exception.TempArticleNotFoundException;
import com.woowacourse.gongseek.article.presentation.dto.TempArticleDetailResponse;
import com.woowacourse.gongseek.article.presentation.dto.TempArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.TempArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.TempArticlesResponse;
import com.woowacourse.gongseek.auth.presentation.dto.LoginMember;
import com.woowacourse.gongseek.common.DatabaseCleaner;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class TempArticleServiceTest {

    @Autowired
    private TempArticleService tempArticleService;

    @Autowired
    private TempArticleRepository tempArticleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("slo", "hanull", "avatar.com"));
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.tableClear();
    }

    @Transactional
    @Test
    void 임시_게시글을_저장한다() {
        final TempArticleRequest request = new TempArticleRequest("title", "content", Category.QUESTION.getValue(),
                List.of("spring"), false);

        final TempArticleIdResponse tempArticleIdResponse = tempArticleService.createOrUpdate(
                new LoginMember(member.getId()), request);

        assertThat(tempArticleIdResponse.getId()).isNotNull();
    }

    @Transactional
    @Test
    void 임시_게시글을_업데이트한다() {
        final TempArticle saveTempArticle = tempArticleRepository.save(
                new TempArticle("title", "content", Category.QUESTION.getValue(), member, List.of("spring"), false));
        final TempArticleRequest updateRequest = new TempArticleRequest(saveTempArticle.getId(), "updateTitle",
                "updateContent",
                Category.QUESTION.getValue(), List.of("updateSpring"), false);

        final TempArticleIdResponse updatedId = tempArticleService.createOrUpdate(new LoginMember(member.getId()),
                updateRequest);
        final TempArticle tempArticle = tempArticleRepository.findById(updatedId.getId()).get();

        assertAll(
                () -> assertThat(updatedId.getId()).isEqualTo(saveTempArticle.getId()),
                () -> assertThat(tempArticle.getTitle().getValue()).isEqualTo("updateTitle"),
                () -> assertThat(tempArticle.getContent().getValue()).isEqualTo("updateContent"),
                () -> assertThat(tempArticle.getTempTags().get(0)).isEqualTo("updateSpring")
        );
    }

    @Test
    void 전체_임시_게시글을_조회한다() {
        tempArticleRepository.save(
                new TempArticle("title", "content", Category.QUESTION.getValue(), member, List.of("spring"), false));
        tempArticleRepository.save(
                new TempArticle("title2", "content2", Category.QUESTION.getValue(), member, List.of("spring2"), false));

        final TempArticlesResponse tempArticles = tempArticleService.getAll(new LoginMember(member.getId()));

        assertAll(
                () -> assertThat(tempArticles.getValues()).hasSize(2),
                () -> assertThat(tempArticles.getValues().get(0).getTitle()).isEqualTo("title"),
                () -> assertThat(tempArticles.getValues().get(1).getTitle()).isEqualTo("title2")
        );
    }

    @Transactional
    @Test
    void 단건_임시_게시물을_조회한다() {
        final TempArticle tempArticle = tempArticleRepository.save(
                new TempArticle("title", "content", Category.QUESTION.getValue(), member, List.of("spring"), false));

        final TempArticleDetailResponse tempArticleDetailResponse = tempArticleService.getOne(
                new LoginMember(member.getId()),
                tempArticle.getId());

        assertAll(
                () -> assertThat(tempArticleDetailResponse.getId()).isEqualTo(tempArticle.getId()),
                () -> assertThat(tempArticleDetailResponse.getTitle()).isEqualTo("title"),
                () -> assertThat(tempArticleDetailResponse.getContent()).isEqualTo("content"),
                () -> assertThat(tempArticleDetailResponse.getCategory()).isEqualTo("question"),
                () -> assertThat(tempArticleDetailResponse.getTags()).containsOnly("spring"),
                () -> assertThat(tempArticleDetailResponse.getIsAnonymous()).isFalse()
        );
    }

    @Test
    void 임시_게시물_조회_시_없다면_예외를_발생한다() {
        assertThatThrownBy(() -> tempArticleService.getOne(new LoginMember(member.getId()), 1L))
                .isInstanceOf(TempArticleNotFoundException.class)
                .hasMessageContaining("임시 게시글이 존재하지 않습니다.");
    }

    @Transactional
    @Test
    void 임시_게시물을_삭제한다() {
        final TempArticle tempArticle = tempArticleRepository.save(
                new TempArticle("title", "content", Category.QUESTION.getValue(), member, List.of("spring"), false));

        tempArticleService.delete(tempArticle.getId(), new LoginMember(member.getId()));

        assertThatThrownBy(() -> tempArticleService.getOne(new LoginMember(member.getId()), tempArticle.getId()))
                .isExactlyInstanceOf(TempArticleNotFoundException.class)
                .hasMessageContaining("임시 게시글이 존재하지 않습니다.");
    }
}
