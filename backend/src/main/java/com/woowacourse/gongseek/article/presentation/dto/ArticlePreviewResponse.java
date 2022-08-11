package com.woowacourse.gongseek.article.presentation.dto;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.like.presentation.dto.LikeResponse;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
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
    private AuthorDto author;
    private String content;
    private String category;
    private Integer commentCount;
    private Integer views;
    private Boolean isLike;
    private Long likeCount;
    private LocalDateTime createdAt;

    public static ArticlePreviewResponse of(Article article, int commentCount, LikeResponse likeResponse) {

        return new ArticlePreviewResponseBuilder()
                .id(article.getId())
                .title(article.getTitle())
                .author(new AuthorDto(article.getMember()))
                .content(article.getContent())
                .category(article.getCategory().getValue())
                .commentCount(commentCount)
                .views(article.getViews())
                .isLike(likeResponse.getIsLike())
                .likeCount(likeResponse.getLikeCount())
                .createdAt(article.getCreatedAt())
                .build();
    }
}
