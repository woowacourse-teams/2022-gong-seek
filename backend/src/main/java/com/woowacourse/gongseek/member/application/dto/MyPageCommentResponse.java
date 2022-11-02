package com.woowacourse.gongseek.member.application.dto;

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

    private String articleTitle;

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
                comment.getArticle().getTitle(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
