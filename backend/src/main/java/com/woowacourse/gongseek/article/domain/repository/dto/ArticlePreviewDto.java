package com.woowacourse.gongseek.article.domain.repository.dto;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.articletag.ArticleTags;
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
public class ArticlePreviewDto {

    private Long id;
    private String title;
    private List<String> tags;
    private AuthorDto author;
    private String content;
    private String category;
    private Long commentCount;
    private Integer views;
    private Boolean isLike;
    private Long likeCount;
    private LocalDateTime createdAt;

    public ArticlePreviewDto(Long id,
                             String title,
                             ArticleTags articleTags,
                             String authorName,
                             String authorAvatarUrl,
                             String content,
                             Category category,
                             Long commentCount,
                             Integer views,
                             Boolean isLike,
                             Long likeCount,
                             LocalDateTime createdAt) {
        this(
                id,
                title,
                articleTags.getTagNames(),
                new AuthorDto(authorName, authorAvatarUrl),
                content,
                category.getValue().toLowerCase(),
                commentCount,
                views,
                isLike,
                likeCount,
                createdAt
        );
    }
}
