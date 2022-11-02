package com.woowacourse.gongseek.article.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.gongseek.article.domain.Article;
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
public class ArticleResponse {

    private String title;

    private List<String> tag;

    private AuthorDto author;

    private String content;

    private Boolean isAuthor;

    private Long views;

    private Boolean hasVote;

    private Boolean isLike;

    private Long likeCount;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public static ArticleResponse of(Article article, Boolean isAuthor, Boolean hasVote, Boolean isLike) {
        return new ArticleResponseBuilder()
                .title(article.getTitle())
                .tag(article.getTagNames())
                .author(new AuthorDto(article.getMember()))
                .content(article.getContent())
                .isAuthor(isAuthor)
                .views(article.getViews())
                .hasVote(hasVote)
                .isLike(isLike)
                .likeCount(article.getLikeCount())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
    }
}
