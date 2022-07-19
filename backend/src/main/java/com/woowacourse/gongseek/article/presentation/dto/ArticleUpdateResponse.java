package com.woowacourse.gongseek.article.presentation.dto;

import com.woowacourse.gongseek.article.domain.Article;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticleUpdateResponse {

    private Long id;
    private String category;

    public ArticleUpdateResponse(Article article) {
        this.id = article.getId();
        this.category = article.getCategory().getValue();
    }
}
