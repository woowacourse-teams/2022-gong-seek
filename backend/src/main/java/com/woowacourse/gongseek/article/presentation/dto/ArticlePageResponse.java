package com.woowacourse.gongseek.article.presentation.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticlePageResponse {

    private List<ArticlePreviewResponse> articles;

    private Boolean hasNext;
}
