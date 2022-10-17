package com.woowacourse.gongseek.vote.domain.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.RepositoryTest;
import com.woowacourse.gongseek.vote.domain.Vote;
import com.woowacourse.gongseek.vote.domain.VoteHistory;
import com.woowacourse.gongseek.vote.domain.VoteItem;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
public class VoteHistoryRepositoryTest {

    private final Member member1 = new Member("slo", "hanull", "avatar.com");
    private final Member member2 = new Member("rennon", "brorae", "avatar.com");

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteItemRepository voteItemRepository;

    @Autowired
    private VoteHistoryRepository voteHistoryRepository;

    @Test
    void 투표_기록을_조회한다() {
        Member savedMember1 = memberRepository.save(member1);
        Member savedMember2 = memberRepository.save(member2);
        Article article = articleRepository.save(
                new Article("title", "content", Category.DISCUSSION, savedMember1, false));
        Vote vote1 = voteRepository.save(new Vote(article, LocalDateTime.now().plusDays(7)));
        Vote vote2 = voteRepository.save(new Vote(article, LocalDateTime.now().plusDays(7)));

        VoteItem voteItem1 = voteItemRepository.save(new VoteItem("1111", vote1));
        voteItemRepository.save(new VoteItem("2222", vote1));

        VoteItem voteItem3 = voteItemRepository.save(new VoteItem("3333", vote2));
        voteItemRepository.save(new VoteItem("4444", vote2));

        voteHistoryRepository.save(new VoteHistory(savedMember1, voteItem1.getId()));
        voteHistoryRepository.save(new VoteHistory(savedMember1, voteItem3.getId()));
        voteHistoryRepository.save(new VoteHistory(savedMember2, voteItem1.getId()));

        VoteHistory foundVoteHistory = voteHistoryRepository.findByVoteIdAndMemberId(vote1.getId(),
                member1.getId()).get();

        assertAll(
                () -> assertThat(foundVoteHistory.getMember()).isEqualTo(savedMember1),
                () -> assertThat(foundVoteHistory.getVoteItemId()).isEqualTo(voteItem1.getId())
        );
    }
}
