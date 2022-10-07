package com.woowacourse.gongseek.article.domain.repository.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private Boolean isAuthor;

    private Integer views;

    private Boolean hasVote;

    private Boolean isLike;

    private Long likeCount;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public ArticleDto(
            String title,
            List<String> tags,
            String memberName,
            String memberAvatarUrl,
            String content,
            Boolean isAuthor,
            Integer views,
            Boolean hasVote,
            Boolean isLike,
            Boolean isAnonymous,
            long likeCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this(
                title,
                tags,
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

    public void checkAnonymous(Boolean isAnonymous) {
        if (isAnonymous) {
            this.author = new AuthorDto(ANONYMOUS_NAME, ANONYMOUS_AVATAR_URL);
        }
    }
}
