package com.woowacourse.gongseek.article.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.member.presentation.dto.MemberDto;
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

    private MemberDto author;

    private String content;

    @JsonProperty(value = "isAuthor")
    private boolean isAuthor;

    private int views;

    private LocalDateTime createdAt;

    public ArticleResponse(Article article, boolean isAuthor) {
        this.title = article.getTitle();
        this.content = article.getContent();
        this.isAuthor = isAuthor;
        this.author = new MemberDto(article.getMember());
        this.views = article.getViews();
        this.createdAt = article.getCreatedAt();
    }
}
