package com.woowacourse.gongseek.like.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.article.application.ArticleService;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.auth.application.dto.GuestMember;
import com.woowacourse.gongseek.auth.application.dto.LoginMember;
import com.woowacourse.gongseek.auth.exception.NotMemberException;
import com.woowacourse.gongseek.like.domain.repository.LikeRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.support.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
class LikeServiceTest extends IntegrationTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Test
    void 게시글을_추천한다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));
        likeService.likeArticle(new LoginMember(member.getId()), article.getId());
        Article foundArticle = articleRepository.findById(article.getId()).get();

        assertThat(foundArticle.getLikeCount()).isEqualTo(1);
    }

    @Test
    void 비회원인_경우_게시글을_추천할_수_없다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));

        assertThatThrownBy(() -> likeService.likeArticle(new GuestMember(), article.getId()))
                .isExactlyInstanceOf(NotMemberException.class)
                .hasMessage("회원이 아니므로 권한이 없습니다.");
    }

    @Test
    void 게시글이_존재하지_않은_경우_게시글을_추천할_수_없다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));

        assertThatThrownBy(() -> likeService.likeArticle(new LoginMember(member.getId()), -1L))
                .isExactlyInstanceOf(ArticleNotFoundException.class)
                .hasMessageContaining("게시글이 존재하지 않습니다.");
    }

    @Test
    void 존재하지_않는_회원은_게시글을_추천할_수_없다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));

        assertThatThrownBy(() -> likeService.likeArticle(new LoginMember(-1L), article.getId()))
                .isExactlyInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("회원이 존재하지 않습니다.");
    }

    @Test
    void 게시글_추천을_취소한다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));
        LoginMember appMember = new LoginMember(member.getId());
        likeService.likeArticle(appMember, article.getId());
        likeService.unlikeArticle(appMember, article.getId());
        Article foundArticle = articleRepository.findById(article.getId()).get();

        assertThat(foundArticle.getLikeCount()).isEqualTo(0);
    }

    @Test
    void 비회원인_경우_게시글_추천을_취소할_수_없다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));

        assertThatThrownBy(() -> likeService.unlikeArticle(new GuestMember(), article.getId()))
                .isExactlyInstanceOf(NotMemberException.class)
                .hasMessageContaining("권한이 없습니다.");
    }

    @Test
    void 게시글이_존재하지_않은_경우_게시글_추천을_취소할_수_없다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));

        assertThatThrownBy(() -> likeService.unlikeArticle(new LoginMember(member.getId()), -1L))
                .isExactlyInstanceOf(ArticleNotFoundException.class)
                .hasMessageContaining("게시글이 존재하지 않습니다.");
    }

    @Test
    void 존재하지_않는_회원은_게시글_추천을_취소할_수_없다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));

        assertThatThrownBy(() -> likeService.unlikeArticle(new LoginMember(-1L), article.getId()))
                .isExactlyInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("회원이 존재하지 않습니다.");
    }

    @Test
    void 추천한_게시글이_삭제된_경우_추천도_삭제된다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));
        likeService.likeArticle(new LoginMember(member.getId()), article.getId());

        articleService.delete(new LoginMember(member.getId()), article.getId());

        assertThat(likeRepository.existsById(1L)).isFalse();
    }
}
