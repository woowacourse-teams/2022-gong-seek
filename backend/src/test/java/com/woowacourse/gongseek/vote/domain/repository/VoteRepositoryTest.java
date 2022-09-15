package com.woowacourse.gongseek.vote.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.RepositoryTest;
import com.woowacourse.gongseek.vote.domain.Vote;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Article article;

    @BeforeEach
    void setUp() {
        Member savedMember = memberRepository.save(new Member("slow", "githubId", "avatarUrl"));
        article = new Article("title", "content", Category.QUESTION, savedMember, false);
        articleRepository.save(article);
    }

    @Test
    void 투표를_생성한다() {
        Vote savedVote = voteRepository.save(new Vote(article, LocalDateTime.now().plusDays(7)));

        assertThat(savedVote.getId()).isNotNull();
    }

    @Test
    void 투표_식별자를_통해_조회한다() {
        Vote savedVote = voteRepository.save(new Vote(article, LocalDateTime.now().plusDays(7)));

        Vote foundVote = voteRepository.findById(savedVote.getId()).get();

        assertThat(foundVote.getExpiryAt()).isEqualTo(savedVote.getExpiryAt());
    }
}
