package com.woowacourse.gongseek.comment.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.gongseek.comment.domain.Comment;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommentResponse {

    private Long id;

    private String content;

    private AuthorDto author;

    @JsonProperty("isAuthor")
    @Accessors(fluent = true)
    private boolean isAuthor;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public CommentResponse(Comment comment, boolean isAuthor) {
        this(
                comment.getId(),
                comment.getContent(),
                new AuthorDto(comment.getMember().getName(), comment.getMember().getAvatarUrl()),
                isAuthor,
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    public CommentResponse(Comment comment, AuthorDto authorDto, boolean isAuthor) {
        this(
                comment.getId(),
                comment.getContent(),
                authorDto,
                isAuthor,
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
