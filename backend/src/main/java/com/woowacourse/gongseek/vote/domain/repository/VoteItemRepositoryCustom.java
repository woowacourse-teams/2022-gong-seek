package com.woowacourse.gongseek.vote.domain.repository;

import com.woowacourse.gongseek.vote.domain.repository.dto.VoteItemDto;
import java.util.List;

public interface VoteItemRepositoryCustom {

    List<VoteItemDto> findAllByArticleIdWithCount(Long articleId);
}
