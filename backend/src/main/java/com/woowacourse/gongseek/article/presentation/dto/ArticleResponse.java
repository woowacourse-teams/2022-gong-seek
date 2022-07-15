package com.woowacourse.gongseek.article.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.gongseek.member.presentation.dto.MemberDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ArticleResponse {

    private String title;

    private MemberDto author;

    private String content;

    @JsonProperty(value = "isAuthor")
    private boolean writer;

    private int views;

    private LocalDateTime createdAt;
}
