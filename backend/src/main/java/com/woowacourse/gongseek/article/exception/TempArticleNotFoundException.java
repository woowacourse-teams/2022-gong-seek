package com.woowacourse.gongseek.article.exception;

import com.woowacourse.gongseek.common.exception.NotFoundException;

public class TempArticleNotFoundException extends NotFoundException {

    public TempArticleNotFoundException(long tempArticleId) {
        super(String.format("(tempArticleId : %d)", tempArticleId));
    }
}
