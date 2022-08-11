package com.woowacourse.gongseek.vote.exception;

import com.woowacourse.gongseek.common.exception.BadRequestException;

public class UnavailableArticleException extends BadRequestException {

    public UnavailableArticleException(long articleId) {
        super(String.format("(articleId : )", articleId));
    }
}
