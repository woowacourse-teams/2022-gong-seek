package com.woowacourse.gongseek.article.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.gongseek.article.domain.TempArticle;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class TempArticleDetailResponse {

    private long id;

    private String title;

    private String content;

    private String category;

    private List<String> tags;

    private Boolean isAnonymous;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createAt;

    public static TempArticleDetailResponse from(TempArticle tempArticle) {
        return new TempArticleDetailResponseBuilder()
                .id(tempArticle.getId())
                .title(tempArticle.getTitle().getValue())
                .content(tempArticle.getContent().getValue())
                .category(tempArticle.getCategory().getValue())
                .tags(tempArticle.getTempTags())
                .isAnonymous(tempArticle.isAnonymous())
                .createAt(tempArticle.getCreatedAt())
                .build();
    }
}
