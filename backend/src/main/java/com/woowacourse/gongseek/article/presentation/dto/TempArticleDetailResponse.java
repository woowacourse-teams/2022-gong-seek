package com.woowacourse.gongseek.article.presentation.dto;

import com.woowacourse.gongseek.article.domain.TempArticle;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private LocalDateTime createAt;

    public static TempArticleDetailResponse from(TempArticle tempArticle) {
        return new TempArticleDetailResponse(
                tempArticle.getId(),
                tempArticle.getTitle().getValue(),
                tempArticle.getContent().getValue(),
                tempArticle.getCategory().getValue(),
                tempArticle.getTempTags(),
                tempArticle.isAnonymous(),
                tempArticle.getCreatedAt()
        );
    }
}
