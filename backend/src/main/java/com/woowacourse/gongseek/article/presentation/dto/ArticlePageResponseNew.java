package com.woowacourse.gongseek.article.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticlePageResponseNew {

    private List<ArticlePreviewDto> articles;

    @JsonProperty("hasNext")
    @Accessors(fluent = true)
    private boolean hasNext;
}
