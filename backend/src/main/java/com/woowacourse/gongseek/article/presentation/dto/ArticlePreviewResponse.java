package com.woowacourse.gongseek.article.presentation.dto;

import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
