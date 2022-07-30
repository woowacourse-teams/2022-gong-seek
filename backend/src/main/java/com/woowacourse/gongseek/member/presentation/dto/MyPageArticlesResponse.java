package com.woowacourse.gongseek.member.presentation.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MyPageArticlesResponse {

    private List<MyPageArticleResponse> articles;
}
