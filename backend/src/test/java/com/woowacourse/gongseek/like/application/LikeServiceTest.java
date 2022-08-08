package com.woowacourse.gongseek.like.application;

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
import com.woowacourse.gongseek.like.presentation.dto.LikeResponse;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
class LikeServiceTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void 게시물을_추천한다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));

        LikeResponse likeResponse = likeService.likeArticle(new LoginMember(member.getId()), article.getId());

        assertAll(
                () -> assertThat(likeResponse.isLike()).isTrue(),
                () -> assertThat(likeResponse.getLikeCount()).isEqualTo(1)
        );
    }

    @Test
    void 비회원인_경우_게시물을_추천할_수_없다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));

        assertThatThrownBy(() -> likeService.likeArticle(new GuestMember(), article.getId()))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 게시물이_존재하지_않은_경우_게시물을_추천할_수_없다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));

        assertThatThrownBy(() -> likeService.likeArticle(new LoginMember(member.getId()), -1L))
                .isInstanceOf(ArticleNotFoundException.class)
                .hasMessage("게시글이 존재하지 않습니다.");
    }

    @Test
    void 존재하지_않는_회원은_게시물을_추천할_수_없다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));

        assertThatThrownBy(() -> likeService.likeArticle(new LoginMember(-1L), article.getId()))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원이 존재하지 않습니다.");
    }

    @Test
    void 게시물_추천을_취소한다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));
        LoginMember appMember = new LoginMember(member.getId());
        likeService.likeArticle(appMember, article.getId());

        LikeResponse likeResponse = likeService.unlikeArticle(appMember, article.getId());

        assertAll(
                () -> assertThat(likeResponse.isLike()).isFalse(),
                () -> assertThat(likeResponse.getLikeCount()).isEqualTo(0)
        );
    }

    @Test
    void 비회원인_경우_게시물_추천을_취소할_수_없다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));

        assertThatThrownBy(() -> likeService.unlikeArticle(new GuestMember(), article.getId()))
                .isInstanceOf(NoAuthorizationException.class)
                .hasMessage("권한이 없습니다.");
    }

    @Test
    void 게시물이_존재하지_않은_경우_게시물_추천을_취소할_수_없다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));

        assertThatThrownBy(() -> likeService.unlikeArticle(new LoginMember(member.getId()), -1L))
                .isInstanceOf(ArticleNotFoundException.class)
                .hasMessage("게시글이 존재하지 않습니다.");
    }

    @Test
    void 존재하지_않는_회원은_게시물_추천을_취소할_수_없다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));

        assertThatThrownBy(() -> likeService.unlikeArticle(new LoginMember(-1L), article.getId()))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("회원이 존재하지 않습니다.");
    }
}
