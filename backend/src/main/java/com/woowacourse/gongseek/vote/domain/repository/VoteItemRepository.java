package com.woowacourse.gongseek.vote.domain.repository;

import com.woowacourse.gongseek.vote.domain.VoteItem;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteItemRepository extends JpaRepository<VoteItem, Long> {

    List<VoteItem> findAllByVoteArticleId(Long articleId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE VoteItem SET amount.value = amount.value - 1 where id = :id and amount.value > 1")
    void decreaseAmount(Long id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<VoteItem> findById(Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE VoteItem SET amount.value = amount.value + 1 where id = :id")
    void increaseAmount(@Param(value = "id") Long id);
}
