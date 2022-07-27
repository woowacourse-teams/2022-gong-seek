package com.woowacourse.gongseek.article.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticleResponse {

    private String title;

    private AuthorDto author;

    private String content;

    @JsonProperty(value = "isAuthor")
    private boolean isAuthor;

    private int views;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public ArticleResponse(Article article, boolean isAuthor) {
        this.title = article.getTitle();
        this.content = article.getContent();
        this.isAuthor = isAuthor;
        this.author = new AuthorDto(article.getMember());
        this.views = article.getViews();
        this.createdAt = article.getCreatedAt();
    }
}
