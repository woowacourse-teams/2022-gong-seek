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
    private Long views;
    private Long commentCount;
    private Long likeCount;
    private Boolean isLike;
    private LocalDateTime createdAt;

    public ArticlePreviewResponse(ArticlePreviewDto article, List<String> tagNames) {
        this(
                article.getId(),
                article.getTitle(),
                tagNames,
                article.getAuthor(),
                article.getContent(),
                article.getCategory(),
                article.getViews(),
                article.getCommentCount(),
                article.getLikeCount(),
                article.getIsLike(),
                article.getCreatedAt()
        );
    }

    public static ArticlePreviewResponse of(Article article, List<String> tag, Long commentCount,
                                            LikeResponse likeResponse) {

        return new ArticlePreviewResponseBuilder()
                .id(article.getId())
                .title(article.getTitle())
                .tag(tag)
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
