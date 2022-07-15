package com.woowacourse.gongseek.comment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.auth.presentation.dto.LoginMember;
import com.woowacourse.gongseek.comment.presentation.dto.CommentRequest;
import com.woowacourse.gongseek.comment.presentation.dto.CommentResponse;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class CommentServiceTest {

    private final Member member = new Member("slow", "hanull", "avatarUrl");
    private final Article article = new Article("title", "content", Category.QUESTION, member);

    @Autowired
    private CommentService commentService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(member);
        articleRepository.save(article);
    }

    @Test
    void 댓글을_생성한다() {
        CommentRequest request = new CommentRequest("content");

        commentService.save(new LoginMember(member.getId()), article.getId(), request);
        List<CommentResponse> savedComments = commentService.findByArticleId(article.getId());

        assertAll(
                () -> assertThat(savedComments).hasSize(1),
                () -> assertThat(savedComments.get(0).getAuthorName()).isEqualTo(member.getName()),
                () -> assertThat(savedComments.get(0).getContent()).isEqualTo(request.getContent())
        );
    }

    @Test
    void 회원이_존재하지_않는_경우_댓글을_생성할_수_없다() {
        CommentRequest request = new CommentRequest("content");

        assertThatThrownBy(() -> commentService.save(new LoginMember(-1L), article.getId(), request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("회원이 존재하지 않습니다.");
    }

    @Test
    void 게시글이_존재하지_않는_경우_댓글을_생성할_수_없다() {
        CommentRequest request = new CommentRequest("content");

        assertThatThrownBy(() -> commentService.save(new LoginMember(member.getId()), 2L, request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("게시글이 존재하지 않습니다.");
    }
}
