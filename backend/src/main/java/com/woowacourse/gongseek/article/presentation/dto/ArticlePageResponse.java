package com.woowacourse.gongseek.article.presentation.dto;

import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticlePageResponse {

    private List<ArticlePreviewDto> articles;

    private Boolean hasNext;

    public static ArticlePageResponse of(Slice<ArticlePreviewDto> articles) {
        return new ArticlePageResponse(articles.getContent(), articles.hasNext());
    }
}
