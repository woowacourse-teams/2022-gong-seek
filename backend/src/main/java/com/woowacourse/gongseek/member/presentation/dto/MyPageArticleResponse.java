package com.woowacourse.gongseek.member.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.gongseek.article.domain.Article;
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

    private long commentCount;

    private long views;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public MyPageArticleResponse(Article article, Long commentCount) {
        this(
                article.getId(),
                article.getTitle(),
                article.getCategory().getValue(),
                commentCount,
                article.getViews(),
                article.getCreatedAt(),
                article.getUpdatedAt()
        );
    }
}
