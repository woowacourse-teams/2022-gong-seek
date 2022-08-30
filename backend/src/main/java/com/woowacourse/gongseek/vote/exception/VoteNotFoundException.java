package com.woowacourse.gongseek.vote.exception;

import com.woowacourse.gongseek.common.exception.NotFoundException;

public class VoteNotFoundException extends NotFoundException {

    public VoteNotFoundException(Long articleId) {
        super(String.format("(articleId : %d)", articleId));
    }
}
