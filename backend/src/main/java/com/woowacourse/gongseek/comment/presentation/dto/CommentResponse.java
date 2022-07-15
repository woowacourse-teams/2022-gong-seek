package com.woowacourse.gongseek.comment.presentation.dto;

import com.woowacourse.gongseek.comment.domain.Comment;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommentResponse {

    private Long id;
    private String content;
    private String authorName;
    private String authorAvatarUrl;
    private LocalDateTime createdAt;

    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getMember().getName(),
                comment.getMember().getAvatarUrl(),
                comment.getCreatedAt()
        );
    }
}
