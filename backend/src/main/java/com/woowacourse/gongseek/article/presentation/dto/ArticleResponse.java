package com.woowacourse.gongseek.article.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.like.presentation.dto.LikeResponse;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticleResponse {

    private String title;

    private List<String> tag;

    private AuthorDto author;

    private String content;

    @JsonProperty("isAuthor")
    @Accessors(fluent = true)
    private boolean isAuthor;

    private int views;

    private boolean hasVote;

    private Boolean isLike;

    private Long likeCount;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public ArticleResponse(Article article, List<String> tag, AuthorDto authorDto, boolean isAuthor,
                           boolean hasVote, LikeResponse likeResponse) {
        this(
                article.getTitle(),
                tag,
                authorDto,
                article.getContent(),
                isAuthor,
                article.getViews(),
                hasVote,
                likeResponse.getIsLike(),
                likeResponse.getLikeCount(),
                article.getCreatedAt(),
                article.getUpdatedAt()
        );
    }

    public ArticleResponse(Article article, List<String> tag, boolean isAuthor, boolean hasVote,
                           LikeResponse likeResponse) {
        this(
                article,
                tag,
                new AuthorDto(article.getMember()),
                isAuthor,
                hasVote,
                likeResponse
        );
    }
}
