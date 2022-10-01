package com.woowacourse.gongseek.article.presentation.dto;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
import com.woowacourse.gongseek.like.presentation.dto.LikeResponse;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import java.time.LocalDateTime;
import java.util.List;
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
    private List<String> tag;
    private AuthorDto author;
    private String content;
    private String category;
    private Long commentCount;
    private Integer views;
    private Boolean isLike;
    private Long likeCount;
    private LocalDateTime createdAt;

    public ArticlePreviewResponse(ArticlePreviewDto article, List<String> tags) {
        this(
                article.getId(),
                article.getTitle(),
                tags,
                article.getAuthor(),
                article.getContent(),
                article.getCategory(),
                article.getCommentCount(),
                article.getViews(),
                article.getIsLike(),
                article.getLikeCount(),
                article.getCreatedAt()
        );
    }

    public static ArticlePreviewResponse of(Article article, List<String> tag, int commentCount,
                                            LikeResponse likeResponse) {

        return new ArticlePreviewResponseBuilder()
                .id(article.getId())
                .title(article.getTitle())
                .tag(tag)
                .author(new AuthorDto(article.getMember()))
                .content(article.getContent())
                .category(article.getCategory().getValue())
                .commentCount((long) commentCount)
                .views(article.getViews())
                .isLike(likeResponse.getIsLike())
                .likeCount(likeResponse.getLikeCount())
                .createdAt(article.getCreatedAt())
                .build();
    }
}
