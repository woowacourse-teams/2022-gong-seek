package com.woowacourse.gongseek.article.domain.repository;

import com.woowacourse.gongseek.article.domain.repository.dto.MyPagePreviewDto;
import java.util.List;

public interface ArticleRepositoryCustom {

    List<MyPagePreviewDto> findAllByMemberIdWithCommentCount(Long memberId);
}
