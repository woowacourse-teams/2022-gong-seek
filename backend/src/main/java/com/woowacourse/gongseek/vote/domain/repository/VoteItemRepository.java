package com.woowacourse.gongseek.vote.domain.repository;

import com.woowacourse.gongseek.vote.domain.VoteItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteItemRepository extends JpaRepository<VoteItem, Long>, VoteItemRepositoryCustom {

    List<VoteItem> findAllByVoteArticleId(Long articleId);

    List<VoteItem> findAllByVoteId(Long voteId);
}
