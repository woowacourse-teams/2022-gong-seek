package com.woowacourse.gongseek.article.presentation.dto;

import com.woowacourse.gongseek.article.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ArticleIdResponse {

    private final Long id;

    public ArticleIdResponse(Article article) {
        this.id = article.getId();
    }
}
