package com.woowacourse.gongseek.article.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.gongseek.article.domain.TempArticle;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class TempArticleResponse {

    private long id;

    private String title;

    private String category;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createAt;

    public static TempArticleResponse from(TempArticle tempArticle) {
        return new TempArticleResponse(tempArticle.getId(),
                tempArticle.getTitle().getValue(),
                tempArticle.getCategory().getValue(),
                tempArticle.getCreatedAt());
    }
}
