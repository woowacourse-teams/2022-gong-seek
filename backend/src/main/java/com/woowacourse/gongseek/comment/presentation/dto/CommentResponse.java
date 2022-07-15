package com.woowacourse.gongseek.comment.presentation.dto;

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
}
