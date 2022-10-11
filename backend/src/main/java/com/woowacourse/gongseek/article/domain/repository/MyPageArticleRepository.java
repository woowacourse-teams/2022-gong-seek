package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.repository.dto.MyPageArticleDto;
import java.util.List;

public interface MyPageArticleRepository {

    List<MyPageArticleDto> findAllByMemberIdWithCommentCount(Long memberId);
}
