package com.woowacourse.gongseek.article.presentation.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ArticleResponses {

    private List<ArticleAllResponse> articles;

}
