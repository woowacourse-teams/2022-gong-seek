package com.woowacourse.gongseek.like.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.like.domain.Like;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.RepositoryTest;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
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
    void 추천한_게시글이_삭제된_경우_추천도_삭제된다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));
        Like like = likeRepository.save(new Like(article, member));

        articleRepository.delete(article);
        likeRepository.flush();

        assertThat(likeRepository.existsById(like.getId())).isFalse();
    }

    @Test
    void 추천한_유저가_삭제된_경우_추천도_삭제된다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article = articleRepository.save(new Article("title", "content", Category.QUESTION, member, false));
        Like like = likeRepository.save(new Like(article, member));

        memberRepository.delete(member);
        likeRepository.flush();

        assertThat(likeRepository.existsById(like.getId())).isFalse();
    }

    @Test
    void 유저가_추천한_게시글들을_조회한다() {
        Member member = memberRepository.save(new Member("judy", "jurlring", "avatarUrl"));
        Article article1 = articleRepository.save(new Article("title1", "content1", Category.QUESTION, member, false));
        Article article2 = articleRepository.save(new Article("title2", "content2", Category.QUESTION, member, false));
        Article article3 = articleRepository.save(new Article("title3", "content3", Category.QUESTION, member, false));
        likeRepository.save(new Like(article1, member));
        likeRepository.save(new Like(article2, member));

        Map<Long, List<Like>> likes = likeRepository.findLikesByArticleIdsAndMemberId(
                List.of(article1.getId(), article2.getId(), article3.getId()), member.getId());
        List<Long> articleIds = likes.entrySet().stream()
                .map(Entry::getKey)
                .collect(Collectors.toList());

        assertThat(articleIds).containsExactly(article1.getId(), article2.getId());
    }
}
