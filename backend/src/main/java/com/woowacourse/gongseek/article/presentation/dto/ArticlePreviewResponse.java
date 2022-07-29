package com.woowacourse.gongseek.article.presentation.dto;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.member.presentation.dto.MemberDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticlePreviewResponse {

    private Long id;
    private String title;
    private MemberDto author;
    private String content;
    private String category;
    private Integer commentCount;
    private Integer views;
    private LocalDateTime createdAt;

    public static ArticlePreviewResponse of(Article article, int commentCount) {

        return new ArticlePreviewResponseBuilder()
                .id(article.getId())
                .title(article.getTitle())
                .author(new MemberDto(article.getMember()))
                .content(article.getContent())
                .category(article.getCategory().getValue())
                .commentCount(commentCount)
                .views(article.getViews())
                .createdAt(article.getCreatedAt())
                .build();
    }
}
