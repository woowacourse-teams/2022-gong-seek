package com.woowacourse.gongseek.comment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.auth.exception.NoAuthorizationException;
import com.woowacourse.gongseek.auth.presentation.dto.GuestMember;
import com.woowacourse.gongseek.auth.presentation.dto.LoginMember;
import com.woowacourse.gongseek.comment.domain.Comment;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.comment.exception.CommentNotFoundException;
import com.woowacourse.gongseek.comment.presentation.dto.CommentRequest;
import com.woowacourse.gongseek.comment.presentation.dto.CommentResponse;
import com.woowacourse.gongseek.comment.presentation.dto.CommentUpdateRequest;
import com.woowacourse.gongseek.commons.DatabaseCleaner;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
class CommentServiceTest {

    private final Member member = new Member("slow", "hanull", "avatarUrl");
    private final Article article = new Article("title", "content", Category.QUESTION, member, false);

    @Autowired
    private CommentService commentService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp() {
        memberRepository.save(member);
        articleRepository.save(article);
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.tableClear();
    }

    @Test
    void 기명_댓글을_생성한다() {
        CommentRequest request = new CommentRequest("content2", false);
        LoginMember member = new LoginMember(this.member.getId());

        commentService.save(member, article.getId(), request);
        List<CommentResponse> savedComments = commentService.getAllByArticleId(member, article.getId()).getComments();

        assertAll(
                () -> assertThat(savedComments).hasSize(1),
                () -> assertThat(savedComments.get(0).getAuthor().getName()).isEqualTo(this.member.getName()),
                () -> assertThat(savedComments.get(0).getContent()).isEqualTo(request.getContent())
        );
    }

    @Test
    void 익명_댓글을_생성한다() {
        CommentRequest request = new CommentRequest("content2", true);
        LoginMember member = new LoginMember(this.member.getId());

        commentService.save(member, article.getId(), request);
        List<CommentResponse> savedComments = commentService.getAllByArticleId(member, article.getId()).getComments();

        assertAll(
                () -> assertThat(savedComments).hasSize(1),
                () -> assertThat(savedComments.get(0).getAuthor().getName()).isEqualTo("익명"),
                () -> assertThat(savedComments.get(0).getContent()).isEqualTo(request.getContent())
        );
    }

    @Test
    void 비회원인_경우_기명_댓글을_생성할_수_없다() {
        CommentRequest request = new CommentRequest("content", false);

        assertThatThrownBy(() -> commentService.save(new GuestMember(), article.getId(), request))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 비회원인_경우_익명_댓글을_생성할_수_없다() {
        CommentRequest request = new CommentRequest("content", true);

        assertThatThrownBy(() -> commentService.save(new GuestMember(), article.getId(), request))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 회원이_존재하지_않는_경우_댓글을_생성할_수_없다() {
        CommentRequest request = new CommentRequest("content", false);

        assertThatThrownBy(() -> commentService.save(new LoginMember(-1L), article.getId(), request))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원이 존재하지 않습니다.");
    }

    @Test
    void 게시글이_존재하지_않는_경우_댓글을_생성할_수_없다() {
        CommentRequest request = new CommentRequest("content", true);

        assertThatThrownBy(() -> commentService.save(new LoginMember(member.getId()), -1L, request))
                .isInstanceOf(ArticleNotFoundException.class)
                .hasMessage("게시글이 존재하지 않습니다.");
    }

    @Test
    void 회원이_자신이_쓴_기명_댓글을_조회한다() {
        commentRepository.save(new Comment("content", member, article, false));

        List<CommentResponse> savedComments = commentService.getAllByArticleId(new LoginMember(member.getId()),
                article.getId()).getComments();

        List<CommentResponse> authorizedComments = savedComments.stream()
                .filter(CommentResponse::isAuthor)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(authorizedComments.size()).isEqualTo(1),
                () -> assertThat(authorizedComments.get(0).getAuthor())
                        .usingRecursiveComparison()
                        .isEqualTo(new AuthorDto("slow", "avatarUrl")),
                () -> assertThat(authorizedComments.get(0).isAuthor()).isTrue()
        );
    }

    @Test
    void 회원이_자신이_쓴_익명_댓글을_조회한다() {
        commentRepository.save(new Comment("content", member, article, true));

        List<CommentResponse> savedComments = commentService.getAllByArticleId(new LoginMember(member.getId()),
                article.getId()).getComments();
        List<CommentResponse> authorizedComments = savedComments.stream()
                .filter(CommentResponse::isAuthor)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(authorizedComments.size()).isEqualTo(1),
                () -> assertThat(authorizedComments.get(0).getAuthor())
                        .usingRecursiveComparison()
                        .isEqualTo(
                                new AuthorDto(
                                        "익명",
                                        "https://raw.githubusercontent.com/woowacourse-teams/2022-gong-seek/develop/frontend/src/assets/gongseek.png"
                                )
                        ),
                () -> assertThat(authorizedComments.get(0).isAuthor()).isTrue()
        );
    }

    @Test
    void 회원이_자신이_쓰지않은_댓글을_조회한다() {
        Member newMember = new Member("jurl", "jurlring", "avatarUrl");
        memberRepository.save(newMember);
        commentRepository.save(new Comment("content", member, article, true));
        commentRepository.save(new Comment("content", member, article, false));

        List<CommentResponse> savedComments = commentService.getAllByArticleId(new LoginMember(newMember.getId()),
                article.getId()).getComments();
        List<CommentResponse> authorizedComments = savedComments.stream()
                .filter(CommentResponse::isAuthor)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(savedComments).hasSize(2),
                () -> assertThat(authorizedComments.size()).isEqualTo(0)
        );
    }

    @Test
    void 비회원이_댓글을_조회한다() {
        commentRepository.save(new Comment("content", member, article, true));
        commentRepository.save(new Comment("content", member, article, false));

        List<CommentResponse> savedComments = commentService.getAllByArticleId(new GuestMember(),
                article.getId()).getComments();
        List<CommentResponse> authorizedComments = savedComments.stream()
                .filter(CommentResponse::isAuthor)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(savedComments).hasSize(2),
                () -> assertThat(authorizedComments.size()).isEqualTo(0)
        );
    }

    @Test
    void 댓글_작성자가_기명_댓글을_수정한다() {
        commentRepository.save(new Comment("content", member, article, false));
        LoginMember member = new LoginMember(this.member.getId());
        List<CommentResponse> comments = commentService.getAllByArticleId(member, article.getId()).getComments();
        String updateContent = "update";
        CommentUpdateRequest updateRequest = new CommentUpdateRequest(updateContent);

        commentService.update(member, comments.get(0).getId(), updateRequest);
        List<CommentResponse> savedComments = commentService.getAllByArticleId(member, article.getId()).getComments();

        assertThat(savedComments.get(0).getContent()).isEqualTo(updateContent);
    }

    @Test
    void 댓글_작성자가_익명_댓글을_수정한다() {
        commentRepository.save(new Comment("content", member, article, true));
        LoginMember member = new LoginMember(this.member.getId());
        List<CommentResponse> comments = commentService.getAllByArticleId(member, article.getId()).getComments();
        String updateContent = "update";
        CommentUpdateRequest updateRequest = new CommentUpdateRequest(updateContent);

        commentService.update(member, comments.get(0).getId(), updateRequest);
        List<CommentResponse> savedComments = commentService.getAllByArticleId(member, article.getId()).getComments();

        assertThat(savedComments.get(0).getContent()).isEqualTo(updateContent);
    }

    @Test
    void 비회원인_경우_기명_댓글을_수정할_수_없다() {
        Comment comment = commentRepository.save(new Comment("content", member, article, false));
        CommentUpdateRequest request = new CommentUpdateRequest("content");

        assertThatThrownBy(() -> commentService.update(new GuestMember(), comment.getId(), request))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 비회원인_경우_익명_댓글을_수정할_수_없다() {
        Comment comment = commentRepository.save(new Comment("content", member, article, true));
        CommentUpdateRequest request = new CommentUpdateRequest("content");

        assertThatThrownBy(() -> commentService.update(new GuestMember(), comment.getId(), request))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 댓글이_존재하지_않는_경우_수정할_수_없다() {
        assertThatThrownBy(
                () -> commentService.update(new LoginMember(member.getId()), -1L,
                        new CommentUpdateRequest("update content")))
                .isInstanceOf(CommentNotFoundException.class)
                .hasMessage("댓글이 존재하지 않습니다.");
    }

    @Test
    void 회원이_아닌_경우_댓글을_수정할_수_없다() {
        Comment comment = commentRepository.save(new Comment("content", member, article, true));

        assertThatThrownBy(() -> commentService.update(new LoginMember(-1L), comment.getId(),
                new CommentUpdateRequest("update content")))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원이 존재하지 않습니다.");
    }

    @Test
    void 기명_댓글을_작성한_회원이_아닌_경우_수정할_수_없다() {
        Comment comment = commentRepository.save(new Comment("content", member, article, false));

        Member newMember = memberRepository.save(new Member("judy", "judyhithub", "avatarUrl"));
        assertThatThrownBy(() -> commentService.update(new LoginMember(newMember.getId()), comment.getId(),
                new CommentUpdateRequest("update content")))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 익명_댓글을_작성한_회원이_아닌_경우_수정할_수_없다() {
        Comment comment = commentRepository.save(new Comment("content", member, article, true));

        Member newMember = memberRepository.save(new Member("judy", "judyhithub", "avatarUrl"));
        assertThatThrownBy(() -> commentService.update(new LoginMember(newMember.getId()), comment.getId(),
                new CommentUpdateRequest("update content")))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 기명_댓글을_삭제한다() {
        Comment comment = commentRepository.save(new Comment("content", member, article, false));
        LoginMember member = new LoginMember(this.member.getId());
        commentService.delete(member, comment.getId());
        List<CommentResponse> comments = commentService.getAllByArticleId(member, article.getId()).getComments();

        boolean isFind = comments.stream()
                .anyMatch(it -> it.getId().equals(comment.getId()));

        assertThat(isFind).isFalse();
    }

    @Test
    void 익명_댓글을_삭제한다() {
        Comment comment = commentRepository.save(new Comment("content", member, article, true));
        LoginMember member = new LoginMember(this.member.getId());
        commentService.delete(member, comment.getId());
        List<CommentResponse> comments = commentService.getAllByArticleId(member, article.getId()).getComments();

        boolean isFind = comments.stream()
                .anyMatch(it -> it.getId().equals(comment.getId()));

        assertThat(isFind).isFalse();
    }

    @Test
    void 비회원인_경우_기명_댓글을_삭제할_수_없다() {
        Comment comment = commentRepository.save(new Comment("content", member, article, false));

        assertThatThrownBy(() -> commentService.delete(new GuestMember(), comment.getId()))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 비회원인_경우_익명_댓글을_삭제할_수_없다() {
        Comment comment = commentRepository.save(new Comment("content", member, article, true));

        assertThatThrownBy(() -> commentService.delete(new GuestMember(), comment.getId()))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 댓글이_존재하지_않는_경우_삭제할_수_없다() {
        assertThatThrownBy(() -> commentService.delete(new LoginMember(member.getId()), -1L))
                .isInstanceOf(CommentNotFoundException.class)
                .hasMessage("댓글이 존재하지 않습니다.");
    }

    @Test
    void 회원이_존재하지_않는_경우_댓글을_삭제할_수_없다() {
        Comment comment = commentRepository.save(new Comment("content", member, article, true));

        assertThatThrownBy(() -> commentService.delete(new LoginMember(-1L), comment.getId()))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원이 존재하지 않습니다.");
    }

    @Test
    void 기명_댓글을_작성한_회원이_아닌_경우_삭제할_수_없다() {
        Comment comment = commentRepository.save(new Comment("content", member, article, false));

        Member newMember = memberRepository.save(new Member("judy", "judyhithub", "avatarUrl"));
        assertThatThrownBy(() -> commentService.delete(new LoginMember(newMember.getId()), comment.getId()))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 익명_댓글을_작성한_회원이_아닌_경우_삭제할_수_없다() {
        Comment comment = commentRepository.save(new Comment("content", member, article, true));

        Member newMember = memberRepository.save(new Member("judy", "judyhithub", "avatarUrl"));
        assertThatThrownBy(() -> commentService.delete(new LoginMember(newMember.getId()), comment.getId()))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }
}
