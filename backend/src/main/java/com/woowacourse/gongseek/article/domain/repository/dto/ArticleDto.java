package com.woowacourse.gongseek.article.domain.repository.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.gongseek.article.domain.articletag.ArticleTags;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleDto {

    private static final String ANONYMOUS_NAME = "익명";
    private static final String ANONYMOUS_AVATAR_URL = "https://raw.githubusercontent.com/woowacourse-teams/2022-gong-seek/develop/frontend/src/assets/gongseek.png";

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

    private long likeCount;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public ArticleDto(
            String title,
            ArticleTags articleTags,
            String memberName,
            String memberAvatarUrl,
            String content,
            boolean isAuthor,
            int views,
            boolean hasVote,
            boolean isLike,
            boolean isAnonymous,
            long likeCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this(
                title,
                articleTags.getTagNames(),
                new AuthorDto(memberName, memberAvatarUrl),
                content,
                isAuthor,
                views,
                hasVote,
                isLike,
                likeCount,
                createdAt,
                updatedAt
        );
        checkAnonymous(isAnonymous);
    }

    public void checkAnonymous(boolean isAnonymous) {
        if (isAnonymous) {
            this.author = new AuthorDto(ANONYMOUS_NAME, ANONYMOUS_AVATAR_URL);
        }
    }
}
