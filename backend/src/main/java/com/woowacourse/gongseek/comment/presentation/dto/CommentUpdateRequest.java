package com.woowacourse.gongseek.comment.presentation.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommentUpdateRequest {

    @Length(max = 10_000)
    private String content;
}
