package com.woowacourse.gongseek.article.domain.repository.dto;

import com.woowacourse.gongseek.article.domain.Category;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MyPagePreviewDto {

    private Long id;

    private String title;

    private String category;

    private Long commentCount;

    private Long views;

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public MyPagePreviewDto(Long id, String title, Category category, Long commentCount, Long views,
                            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.category = category.getValue();
        this.commentCount = commentCount;
        this.views = views;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
