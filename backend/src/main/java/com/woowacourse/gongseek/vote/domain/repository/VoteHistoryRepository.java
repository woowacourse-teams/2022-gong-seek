package com.woowacourse.gongseek.vote.domain.repository;

import com.woowacourse.gongseek.vote.domain.VoteHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteHistoryRepository extends JpaRepository<VoteHistory, Long>, VoteHistoryRepositoryCustom {

}
