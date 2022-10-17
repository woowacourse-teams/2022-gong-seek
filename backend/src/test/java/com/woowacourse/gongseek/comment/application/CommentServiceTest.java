package com.woowacourse.gongseek.comment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.auth.exception.NotAuthorException;
import com.woowacourse.gongseek.auth.presentation.dto.GuestMember;
import com.woowacourse.gongseek.auth.presentation.dto.LoginMember;
import com.woowacourse.gongseek.comment.domain.Comment;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.comment.exception.CommentNotFoundException;
import com.woowacourse.gongseek.comment.presentation.dto.CommentRequest;
import com.woowacourse.gongseek.comment.presentation.dto.CommentResponse;
import com.woowacourse.gongseek.comment.presentation.dto.CommentUpdateRequest;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.member.presentation.dto.MemberDto;
import com.woowacourse.gongseek.support.DatabaseCleaner;
import com.woowacourse.gongseek.support.IntegrationTest;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
class CommentServiceTest extends IntegrationTest {

    private static final String CONTENT = "content";
    private static final String ANONYMOUS_NAME = "익명";
    private static final String ANONYMOUS_AVATAR_URL = "https://raw.githubusercontent.com/woowacourse-teams/2022-gong-seek/develop/frontend/src/assets/gongseek.png";

    private final Member member = new Member("slow", "hanull", "avatarUrl");
    private final Article article = new Article("title", CONTENT, Category.QUESTION, member, false);

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
    void 회원이_기명_댓글을_생성한다() {
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
    void 회원이_익명_댓글을_생성한다() {
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
    void 존재하지_않는_회원은_기명_댓글을_생성할_수_없다() {
        CommentRequest request = new CommentRequest(CONTENT, false);

        assertThatThrownBy(() -> commentService.save(new GuestMember(), article.getId(), request))
                .isExactlyInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원이 존재하지 않습니다.(memberId : 0)");
    }

    @Test
    void 존재하지_않는_회원은_익명_댓글을_생성할_수_없다() {
        CommentRequest request = new CommentRequest(CONTENT, true);

        assertThatThrownBy(() -> commentService.save(new GuestMember(), article.getId(), request))
                .isExactlyInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원이 존재하지 않습니다.(memberId : 0)");
    }

    @Test
    void 회원이_존재하지_않는_경우_댓글을_생성할_수_없다() {
        CommentRequest request = new CommentRequest(CONTENT, false);

        assertThatThrownBy(() -> commentService.save(new LoginMember(-1L), article.getId(), request))
                .isExactlyInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("회원이 존재하지 않습니다.");
    }

    @Test
    void 게시글이_존재하지_않는_경우_댓글을_생성할_수_없다() {
        CommentRequest request = new CommentRequest(CONTENT, true);

        assertThatThrownBy(() -> commentService.save(new LoginMember(member.getId()), -1L, request))
                .isExactlyInstanceOf(ArticleNotFoundException.class)
                .hasMessageContaining("게시글이 존재하지 않습니다.");
    }

    @Test
    void 회원이_작성한_기명_댓글을_조회한다() {
        commentService.save(new LoginMember(member.getId()), article.getId(), new CommentRequest(CONTENT, false));

        List<CommentResponse> savedComments = commentService.getAllByArticleId(new LoginMember(member.getId()),
                article.getId()).getComments();

        assertAll(
                () -> assertThat(savedComments.size()).isEqualTo(1),
                () -> assertThat(savedComments.get(0))
                        .usingRecursiveComparison()
                        .ignoringFields("id", "createdAt", "updatedAt")
                        .isEqualTo(new CommentResponse(new Comment(CONTENT, member, article, false), true))
        );
    }

    @Test
    void 회원이_작성한_익명_댓글을_조회한다() {
        commentService.save(new LoginMember(member.getId()), article.getId(), new CommentRequest(CONTENT, true));

        List<CommentResponse> savedComments = commentService.getAllByArticleId(new LoginMember(member.getId()),
                article.getId()).getComments();

        assertAll(
                () -> assertThat(savedComments).hasSize(1),
                () -> assertThat(savedComments.get(0).getAuthor())
                        .usingRecursiveComparison()
                        .isEqualTo(new MemberDto(ANONYMOUS_NAME, ANONYMOUS_AVATAR_URL)),
                () -> assertThat(savedComments.get(0).getIsAuthor()).isTrue()
        );
    }

    @Test
    void 회원이_작성하지_않은_댓글을_조회한다() {
        Member newMember = new Member("jurl", "jurlring", "avatarUrl");
        memberRepository.save(newMember);
        commentRepository.save(new Comment(CONTENT, member, article, true));
        commentRepository.save(new Comment(CONTENT, member, article, false));

        List<CommentResponse> savedComments = commentService.getAllByArticleId(new LoginMember(newMember.getId()),
                article.getId()).getComments();
        List<CommentResponse> authorizedComments = savedComments.stream()
                .filter(CommentResponse::getIsAuthor)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(savedComments).hasSize(2),
                () -> assertThat(authorizedComments).isEmpty()
        );
    }

    @Test
    void 비회원이_댓글을_조회한다() {
        commentRepository.save(new Comment(CONTENT, member, article, true));
        commentRepository.save(new Comment(CONTENT, member, article, false));

        List<CommentResponse> savedComments = commentService.getAllByArticleId(new GuestMember(),
                article.getId()).getComments();
        List<CommentResponse> authorizedComments = savedComments.stream()
                .filter(CommentResponse::getIsAuthor)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(savedComments).hasSize(2),
                () -> assertThat(authorizedComments).isEmpty()
        );
    }

    @Test
    void 작성자인_회원이_기명_댓글을_수정한다() {
        commentRepository.save(new Comment(CONTENT, member, article, false));
        LoginMember appMember = new LoginMember(this.member.getId());
        List<CommentResponse> comments = commentService.getAllByArticleId(appMember, article.getId()).getComments();
        String updateContent = "update";
        CommentUpdateRequest updateRequest = new CommentUpdateRequest(updateContent);

        commentService.update(appMember, comments.get(0).getId(), updateRequest);
        List<CommentResponse> savedComments = commentService.getAllByArticleId(appMember, article.getId())
                .getComments();

        assertThat(savedComments.get(0).getContent()).isEqualTo(updateContent);
    }

    @Test
    void 작성자인_회원이_익명_댓글을_수정한다() {
        LoginMember appMember = new LoginMember(member.getId());
        commentService.save(appMember, article.getId(), new CommentRequest(CONTENT, true));
        List<CommentResponse> comments = commentService.getAllByArticleId(appMember, article.getId()).getComments();
        String updateContent = "update";
        CommentUpdateRequest updateRequest = new CommentUpdateRequest(updateContent);

        commentService.update(appMember, comments.get(0).getId(), updateRequest);
        List<CommentResponse> savedComments = commentService.getAllByArticleId(appMember, article.getId())
                .getComments();

        assertThat(savedComments.get(0).getContent()).isEqualTo(updateContent);
    }

    @Test
    void 작성자가_아닌_회원은_기명_댓글을_수정할_수_없다() {
        Comment comment = commentRepository.save(new Comment(CONTENT, member, article, false));

        Member newMember = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        assertThatThrownBy(() -> commentService.update(new LoginMember(newMember.getId()), comment.getId(),
                new CommentUpdateRequest("update content")))
                .isInstanceOf(NotAuthorException.class)
                .hasMessageContaining("작성자가 아니므로 권한이 없습니다.");
    }

    @Test
    void 작성자가_아닌_회원은_익명_댓글을_수정할_수_없다() {
        Comment comment = commentRepository.save(new Comment(CONTENT, member, article, true));

        Member newMember = memberRepository.save(new Member("judy", "judyhithub", "avatarUrl"));
        assertThatThrownBy(() -> commentService.update(new LoginMember(newMember.getId()), comment.getId(),
                new CommentUpdateRequest("update content")))
                .isExactlyInstanceOf(NotAuthorException.class)
                .hasMessageContaining("작성자가 아니므로 권한이 없습니다.");
    }

    @Test
    void 존재하지_않는_회원은_기명_댓글을_수정할_수_없다() {
        Comment comment = commentRepository.save(new Comment(CONTENT, member, article, false));
        CommentUpdateRequest request = new CommentUpdateRequest(CONTENT);

        assertThatThrownBy(() -> commentService.update(new GuestMember(), comment.getId(), request))
                .isExactlyInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원이 존재하지 않습니다.(memberId : 0)");
    }

    @Test
    void 존재하지_않는_회원은_익명_댓글을_수정할_수_없다() {
        Comment comment = commentRepository.save(new Comment(CONTENT, member, article, true));
        CommentUpdateRequest request = new CommentUpdateRequest(CONTENT);

        assertThatThrownBy(() -> commentService.update(new GuestMember(), comment.getId(), request))
                .isExactlyInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원이 존재하지 않습니다.(memberId : 0)");
    }

    @Test
    void 댓글이_존재하지_않는_경우_수정할_수_없다() {
        assertThatThrownBy(
                () -> commentService.update(new LoginMember(member.getId()), -1L,
                        new CommentUpdateRequest("update content")))
                .isExactlyInstanceOf(CommentNotFoundException.class)
                .hasMessageContaining("댓글이 존재하지 않습니다.");
    }

    @Test
    void 회원이_작성한_기명_댓글을_삭제한다() {
        LoginMember appMember = new LoginMember(member.getId());
        commentService.save(appMember, article.getId(), new CommentRequest(CONTENT, true));
        CommentResponse comment = commentService.getAllByArticleId(appMember, article.getId()).getComments().get(0);
        commentService.delete(appMember, comment.getId());

        List<CommentResponse> responses = commentService.getAllByArticleId(appMember, article.getId()).getComments();

        assertThat(responses).isEmpty();
    }

    @Test
    void 회원이_작성한_익명_댓글을_삭제한다() {
        LoginMember appMember = new LoginMember(member.getId());
        commentService.save(appMember, article.getId(), new CommentRequest(CONTENT, true));
        CommentResponse comment = commentService.getAllByArticleId(appMember, article.getId()).getComments().get(0);
        commentService.delete(appMember, comment.getId());

        List<CommentResponse> responses = commentService.getAllByArticleId(appMember, article.getId()).getComments();

        assertThat(responses).isEmpty();
    }

    @Test
    void 작성자가_아닌_회원은_기명_댓글을_삭제할_수_없다() {
        Comment comment = commentRepository.save(new Comment(CONTENT, member, article, false));
        Member newMember = memberRepository.save(new Member("judy", "judyhithub", "avatarUrl"));

        assertThatThrownBy(() -> commentService.delete(new LoginMember(newMember.getId()), comment.getId()))
                .isExactlyInstanceOf(NotAuthorException.class)
                .hasMessageContaining("작성자가 아니므로 권한이 없습니다.");
    }

    @Test
    void 작성자가_아닌_회원은_익명_댓글을_삭제할_수_없다() {
        Comment comment = commentRepository.save(new Comment(CONTENT, member, article, true));
        Member newMember = memberRepository.save(new Member("judy", "judyhithub", "avatarUrl"));

        assertThatThrownBy(() -> commentService.delete(new LoginMember(newMember.getId()), comment.getId()))
                .isExactlyInstanceOf(NotAuthorException.class)
                .hasMessageContaining("작성자가 아니므로 권한이 없습니다.");
    }

    @Test
    void 존재하지_않는_회원인_경우_기명_댓글을_삭제할_수_없다() {
        Comment comment = commentRepository.save(new Comment(CONTENT, member, article, false));

        assertThatThrownBy(() -> commentService.delete(new GuestMember(), comment.getId()))
                .isExactlyInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원이 존재하지 않습니다.(memberId : 0)");
    }

    @Test
    void 존재하지_않는_회원인_경우_익명_댓글을_삭제할_수_없다() {
        Comment comment = commentRepository.save(new Comment(CONTENT, member, article, true));

        assertThatThrownBy(() -> commentService.delete(new GuestMember(), comment.getId()))
                .isExactlyInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원이 존재하지 않습니다.(memberId : 0)");
    }

    @Test
    void 댓글이_존재하지_않는_경우_삭제할_수_없다() {
        assertThatThrownBy(() -> commentService.delete(new LoginMember(member.getId()), -1L))
                .isExactlyInstanceOf(CommentNotFoundException.class)
                .hasMessageContaining("댓글이 존재하지 않습니다.");
    }

    @Test
    void 회원이_존재하지_않는_경우_댓글을_삭제할_수_없다() {
        Comment comment = commentRepository.save(new Comment(CONTENT, member, article, true));

        assertThatThrownBy(() -> commentService.delete(new LoginMember(-1L), comment.getId()))
                .isExactlyInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("회원이 존재하지 않습니다.");
    }
}
