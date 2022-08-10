package com.woowacourse.gongseek.vote.exception;

import com.woowacourse.gongseek.common.exception.ApplicationException;

public class AlreadyVoteSameItemException extends ApplicationException {

    public AlreadyVoteSameItemException(Long id) {
        super(" : " + id);
    }
}
