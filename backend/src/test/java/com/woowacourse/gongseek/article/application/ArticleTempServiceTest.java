package com.woowacourse.gongseek.article.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.ArticleTemp;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleTempRepository;
import com.woowacourse.gongseek.article.presentation.dto.ArticleTempIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleTempRequest;
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
class ArticleTempServiceTest {

    @Autowired
    private ArticleTempService articleTempService;

    @Autowired
    private ArticleTempRepository articleTempRepository;

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
        final ArticleTempRequest request = new ArticleTempRequest("title", "content", Category.QUESTION.getValue(),
                List.of("spring"), false);

        final ArticleTempIdResponse articleTempIdResponse = articleTempService.createOrUpdate(
                new LoginMember(member.getId()),
                request);

        assertThat(articleTempIdResponse.getId()).isNotNull();
    }

    @Transactional
    @Test
    void 임시_게시글을_업데이트한다() {
        final LoginMember loginMember = new LoginMember(member.getId());
        final ArticleTempRequest createRequest = new ArticleTempRequest("title", "content",
                Category.QUESTION.getValue(),
                List.of("spring"), false);
        final ArticleTempIdResponse savedId = articleTempService.createOrUpdate(loginMember, createRequest);
        final ArticleTempRequest updateRequest = new ArticleTempRequest(savedId.getId(), "updateTitle", "updateContent",
                Category.QUESTION.getValue(),
                List.of("updateSpring"), false);

        final ArticleTempIdResponse updatedId = articleTempService.createOrUpdate(loginMember, updateRequest);
        final ArticleTemp articleTemp = articleTempRepository.findById(updatedId.getId()).get();

        assertAll(
                () -> assertThat(savedId.getId()).isEqualTo(updatedId.getId()),
                () -> assertThat(articleTemp.getTitle().getValue()).isEqualTo("updateTitle"),
                () -> assertThat(articleTemp.getContent().getValue()).isEqualTo("updateContent"),
                () -> assertThat(articleTemp.getTags().get(0)).isEqualTo("updateSpring")
        );
    }
}
