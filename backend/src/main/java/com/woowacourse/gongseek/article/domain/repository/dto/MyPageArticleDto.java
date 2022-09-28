package com.woowacourse.gongseek.article.domain.repository.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.gongseek.article.domain.Category;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MyPageArticleDto {

    private Long id;

    private String title;

    private String category;

    private long commentCount;

    private int views;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public MyPageArticleDto(Long id, String title, Category category, long commentCount, int views,
                            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.category = category.getValue();
        this.commentCount = commentCount;
        this.views = views;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ArticleDTO{" +
                "articleId=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", commentCount=" + commentCount +
                ", views=" + views +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
