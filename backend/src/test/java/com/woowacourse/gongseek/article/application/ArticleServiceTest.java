package com.woowacourse.gongseek.article.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.auth.presentation.dto.SearchMember;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
@Transactional
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 질문을_저장한다() {
        String title = "질문합니다.";
        String content = "내용입나다....";
        String category = "question";
        Member member = new Member("slo", "hanull", "avatar.com");
        memberRepository.save(member);
        ArticleRequest question = new ArticleRequest(title, content, category);
        SearchMember searchMember = new SearchMember(member.getId(), false);

        ArticleIdResponse articleIdResponse = articleService.save(searchMember, question);

        assertThat(articleIdResponse.getId()).isNotNull();
    }

    @Test
    void 존재하지_않는_회원이_질문을_저장하면_예외가_발생한다() {
        String title = "질문합니다.";
        String content = "내용입나다....";
        String category = "question";
        ArticleRequest question = new ArticleRequest(title, content, category);

        assertThatThrownBy(() -> articleService.save(new SearchMember(1L, true), question))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("회원이 존재하지 않습니다.");
    }
}
