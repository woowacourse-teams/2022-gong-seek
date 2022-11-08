package com.woowacourse.gongseek.like.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.like.domain.Like;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.RepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
@RepositoryTest
class LikeRepositoryTest {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 추천한_유저가_삭제된_경우_추천도_삭제된다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));
        Like like = likeRepository.save(new Like(article, member));

        memberRepository.delete(member);
        memberRepository.flush();

        assertThat(likeRepository.existsById(like.getId())).isFalse();
    }

    @Test
    void 게시글의_추천수를_조회한다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Member otherMember = memberRepository.save(new Member("rennon", "brorae", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));
        likeRepository.save(new Like(article, member));
        likeRepository.save(new Like(article, otherMember));

        Long count = likeRepository.countByArticleId(article.getId());

        assertThat(count).isEqualTo(2);
    }
}
