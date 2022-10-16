package com.woowacourse.gongseek.like.domain.repository;

import com.woowacourse.gongseek.like.domain.Like;
import java.util.List;
import java.util.Map;

public interface LikeRepositoryCustom {

    Map<Long, List<Like>> findLikesByArticleIdsAndMemberId(List<Long> articleIds, Long memberId);
}
