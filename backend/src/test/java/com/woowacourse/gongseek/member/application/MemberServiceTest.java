package com.woowacourse.gongseek.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.auth.presentation.dto.GuestMember;
import com.woowacourse.gongseek.auth.presentation.dto.LoginMember;
import com.woowacourse.gongseek.comment.domain.Comment;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.commons.DatabaseCleaner;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.member.presentation.dto.MemberDto;
import com.woowacourse.gongseek.member.presentation.dto.MyPageArticlesResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageCommentsResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("rennon", "brorae", "avatar.com"));
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.tableClear();
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
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원이 존재하지 않습니다.");
    }

    @Test
    void 회원이_작성한_게시글들을_조회할_수_있다() {
        articleRepository.save(new Article("title1", "content1", Category.QUESTION, member));
        articleRepository.save(new Article("title2", "content2", Category.QUESTION, member));

        MyPageArticlesResponse response = memberService.getArticles(new LoginMember(member.getId()));

        assertThat(response.getArticles()).size().isEqualTo(2);
    }

    @Test
    void 비회원은_작성한_게시글들을_조회할_수_없다() {
        assertThatThrownBy(() -> memberService.getArticles(new GuestMember()))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원이 존재하지 않습니다.");
    }

    @Test
    void 회원이_작성한_게시글의_댓글수를_확인한다() {
        Article article = articleRepository.save(new Article("title1", "content1", Category.QUESTION, member));
        commentRepository.save(new Comment("hi1", member, article));
        commentRepository.save(new Comment("hi2", member, article));

        MyPageArticlesResponse myPageArticlesResponse = memberService.getArticles(new LoginMember(member.getId()));

        assertThat(myPageArticlesResponse.getArticles().get(0).getCommentCount()).isEqualTo(2);
    }

    @Test
    void 회원이_작성한_댓글들을_조회할_수_있다() {
        Article article = articleRepository.save(new Article("title1", "content1", Category.QUESTION, member));
        commentRepository.save(new Comment("댓글1", member, article));
        commentRepository.save(new Comment("댓글2", member, article));

        MyPageCommentsResponse response = memberService.getComments(new LoginMember(member.getId()));

        assertThat(response.getComments()).size().isEqualTo(2);
    }

    @Test
    void 비회원은_작성한_댓글들을_조회할_수_없다() {
        assertThatThrownBy(() -> memberService.getComments(new GuestMember()))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원이 존재하지 않습니다.");
    }
}
