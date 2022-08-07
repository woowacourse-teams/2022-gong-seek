package com.woowacourse.gongseek.member.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.woowacourse.gongseek.comment.domain.Comment;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MyPageCommentResponse {

    private Long id;

    private String content;

    private Long articleId;

    private String category;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    public MyPageCommentResponse(Comment comment) {
        this(
                comment.getId(),
                comment.getContent(),
                comment.getArticle().getId(),
                comment.getArticle().getCategory().getValue(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    @Override
    public String toString() {
        return "MyPageCommentResponse{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", articleId=" + articleId +
                ", category='" + category + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
