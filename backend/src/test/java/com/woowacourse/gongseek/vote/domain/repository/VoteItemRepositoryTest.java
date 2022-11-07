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
import com.woowacourse.gongseek.vote.domain.repository.dto.VoteItemDto;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
@RepositoryTest
class VoteItemRepositoryTest {

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
    void 투표_아이템을_투표수와_조회할_수_있다() {
        Member member = memberRepository.save(new Member("rennon", "brorae", "avatar.com"));
        Article article = articleRepository.save(new Article("title", "content", Category.DISCUSSION, member, false));
        Vote vote = voteRepository.save(new Vote(article, LocalDateTime.now().plusDays(1)));
        VoteItem firstVoteItem = voteItemRepository.save(new VoteItem("1번", vote));
        VoteItem secondVoteItem = voteItemRepository.save(new VoteItem("2번", vote));
        voteHistoryRepository.save(new VoteHistory(member, firstVoteItem));

        List<VoteItemDto> voteItems = voteItemRepository.findAllByArticleIdWithCount(article.getId());

        assertAll(
                () -> assertThat(voteItems.get(0).getId()).isEqualTo(firstVoteItem.getId()),
                () -> assertThat(voteItems.get(0).getAmount()).isEqualTo(1),
                () -> assertThat(voteItems.get(1).getId()).isEqualTo(secondVoteItem.getId()),
                () -> assertThat(voteItems.get(1).getAmount()).isEqualTo(0)
        );
    }
}
