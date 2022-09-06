package com.woowacourse.gongseek.article.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.TempArticle;
import com.woowacourse.gongseek.article.domain.repository.TempArticleRepository;
import com.woowacourse.gongseek.article.presentation.dto.TempArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.TempArticleRequest;
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
                new LoginMember(member.getId()),
                request);

        assertThat(tempArticleIdResponse.getId()).isNotNull();
    }

    @Transactional
    @Test
    void 임시_게시글을_업데이트한다() {
        final LoginMember loginMember = new LoginMember(member.getId());
        final TempArticleRequest createRequest = new TempArticleRequest("title", "content",
                Category.QUESTION.getValue(),
                List.of("spring"), false);
        final TempArticleIdResponse savedId = tempArticleService.createOrUpdate(loginMember, createRequest);
        final TempArticleRequest updateRequest = new TempArticleRequest(savedId.getId(), "updateTitle", "updateContent",
                Category.QUESTION.getValue(),
                List.of("updateSpring"), false);

        final TempArticleIdResponse updatedId = tempArticleService.createOrUpdate(loginMember, updateRequest);
        final TempArticle tempArticle = tempArticleRepository.findById(updatedId.getId()).get();

        assertAll(
                () -> assertThat(savedId.getId()).isEqualTo(updatedId.getId()),
                () -> assertThat(tempArticle.getTitle().getValue()).isEqualTo("updateTitle"),
                () -> assertThat(tempArticle.getContent().getValue()).isEqualTo("updateContent"),
                () -> assertThat(tempArticle.getTags().get(0)).isEqualTo("updateSpring")
        );
    }
}
