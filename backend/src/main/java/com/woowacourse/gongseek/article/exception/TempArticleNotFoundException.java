package com.woowacourse.gongseek.article.exception;

import com.woowacourse.gongseek.common.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TempArticleNotFoundException extends NotFoundException {

    public TempArticleNotFoundException(long tempArticleId) {
        super(String.format("(tempArticleId : %d)", tempArticleId));
    }
}
