package com.woowacourse.gongseek.article.presentation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticleUpdateRequest {

    @Length(max = 500)
    private String title;

    @Length(max = 1000)
    private String content;
}
