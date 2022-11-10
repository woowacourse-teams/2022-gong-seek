package com.woowacourse.gongseek.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.application.ArticleService;
import com.woowacourse.gongseek.article.application.dto.ArticleRequest;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.auth.application.dto.GuestMember;
import com.woowacourse.gongseek.auth.application.dto.LoginMember;
import com.woowacourse.gongseek.comment.application.CommentService;
import com.woowacourse.gongseek.comment.application.dto.CommentRequest;
import com.woowacourse.gongseek.comment.domain.Comment;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.member.application.dto.MemberDto;
import com.woowacourse.gongseek.member.application.dto.MemberUpdateRequest;
import com.woowacourse.gongseek.member.application.dto.MemberUpdateResponse;
import com.woowacourse.gongseek.member.application.dto.MyPageArticlesResponse;
import com.woowacourse.gongseek.member.application.dto.MyPageCommentsResponse;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.support.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
class MemberServiceTest extends IntegrationTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("rennon", "brorae", "avatar.com"));
    }

    @Test
    void 회원은_회원_정보를_조회한다() {
        MemberDto memberDto = memberService.getOne(new LoginMember(member.getId()));

        assertAll(
                () -> assertThat(memberDto.getName()).isEqualTo(member.getName()),
                () -> assertThat(memberDto.getAvatarUrl()).isEqualTo(member.getAvatarUrl())
        );
    }

    @Test
    void 비회원은_회원_정보를_조회할_수_없다() {
        assertThatThrownBy(() -> memberService.getOne(new GuestMember()))
                .isExactlyInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("회원이 존재하지 않습니다.");
    }

    @Test
    void 회원이_작성한_게시글들을_조회할_수_있다() {
        articleService.create(new LoginMember(member.getId()),
                new ArticleRequest("cipherTitle", "cipherContent", Category.QUESTION.getValue(), List.of("SPRING"),
                        true));
        articleRepository.save(new Article("title1", "content1", Category.QUESTION, member, false));
        articleRepository.save(new Article("title2", "content2", Category.QUESTION, member, false));

        MyPageArticlesResponse response = memberService.getArticles(new LoginMember(member.getId()));

        assertThat(response.getArticles()).hasSize(3);
    }

    @Test
    void 비회원은_작성한_게시글들을_조회할_수_없다() {
        assertThatThrownBy(() -> memberService.getArticles(new GuestMember()))
                .isExactlyInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("회원이 존재하지 않습니다.");
    }

    @Test
    void 회원이_작성한_게시글의_댓글수를_확인한다() {
        Article article = articleRepository.save(new Article("title1", "content1", Category.QUESTION, member, false));
        commentService.create(new LoginMember(member.getId()), article.getId(), new CommentRequest("hi1", false));
        commentService.create(new LoginMember(member.getId()), article.getId(), new CommentRequest("hi2", false));

        MyPageArticlesResponse myPageArticlesResponse = memberService.getArticles(new LoginMember(member.getId()));

        assertThat(myPageArticlesResponse.getArticles().get(0).getCommentCount()).isEqualTo(2);
    }

    @Test
    void 회원이_작성한_댓글들을_조회할_수_있다() {
        Article article = articleRepository.save(new Article("title1", "content1", Category.QUESTION, member, false));
        commentService.create(new LoginMember(member.getId()), article.getId(),
                new CommentRequest("cipherContent", true));
        commentRepository.save(new Comment("댓글1", member, article, false));
        commentRepository.save(new Comment("댓글2", member, article, false));

        MyPageCommentsResponse response = memberService.getComments(new LoginMember(member.getId()));

        assertThat(response.getComments()).hasSize(3);
    }

    @Test
    void 비회원은_작성한_댓글들을_조회할_수_없다() {
        assertThatThrownBy(() -> memberService.getComments(new GuestMember()))
                .isExactlyInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("회원이 존재하지 않습니다.");
    }

    @Test
    void 회원의_이름을_수정한다() {
        MemberUpdateRequest request = new MemberUpdateRequest("홍길동");
        MemberUpdateResponse response = memberService.update(new LoginMember(member.getId()), request);

        assertThat(response.getName()).isEqualTo(request.getName());
    }

    @Test
    void 권한이_없는_회원은_이름을_수정하면_예외가_발생한다() {
        MemberUpdateRequest request = new MemberUpdateRequest("홍길동");

        assertThatThrownBy(() -> memberService.update(new GuestMember(), request))
                .isExactlyInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("회원이 존재하지 않습니다.");
    }
}
