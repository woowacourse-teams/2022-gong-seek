package com.woowacourse.gongseek.member.application.dto;

import com.woowacourse.gongseek.article.domain.repository.dto.MyPagePreviewDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MyPageArticlesResponse {

    private List<MyPagePreviewDto> articles;
}
