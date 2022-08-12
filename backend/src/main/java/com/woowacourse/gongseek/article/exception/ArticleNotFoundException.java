package com.woowacourse.gongseek.article.exception;

import com.woowacourse.gongseek.common.exception.NotFoundException;

public class ArticleNotFoundException extends NotFoundException {

    public ArticleNotFoundException(long articleId) {
        super(String.format("(articleId : %d)", articleId));
    }
}
