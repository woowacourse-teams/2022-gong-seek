package com.woowacourse.gongseek.article.exception;

import com.woowacourse.gongseek.common.exception.BadRequestException;

public class ArticleTitleTooLongException extends BadRequestException {

    public ArticleTitleTooLongException(int articleTitleLength) {
        super(String.format("(입력하신 길이 : %d)", articleTitleLength));
    }
}
