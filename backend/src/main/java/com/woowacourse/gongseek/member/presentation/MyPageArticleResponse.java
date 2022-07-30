package com.woowacourse.gongseek.member.presentation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MyPageArticleResponse {

    private Long id;

    private String title;

    private String category;

    private int commentCount;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    private int views;

    public MyPageArticleResponse(Article article, int commentCount) {
        this(
                article.getId(),
                article.getTitle(),
                article.getCategory().getValue(),
                commentCount,
                article.getCreatedAt(),
                article.getUpdatedAt(),
                article.getViews()
        );
    }
}
