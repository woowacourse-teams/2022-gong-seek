package com.woowacourse.gongseek.article.exception;

import com.woowacourse.gongseek.common.exception.BadRequestException;

public class ArticleContentTooLongException extends BadRequestException {

    public ArticleContentTooLongException(int articleContentLength) {
        super(String.format("(입력하신 길이 : %d)", articleContentLength));
    }
}
