package com.woowacourse.gongseek.auth.exception;

import com.woowacourse.gongseek.common.exception.ForbiddenException;

public class NotAuthorException extends ForbiddenException {

    public NotAuthorException(long articleId, long memberId) {
        super(String.format("(articleId : %d, memberId : %d)", articleId, memberId));
    }
}
