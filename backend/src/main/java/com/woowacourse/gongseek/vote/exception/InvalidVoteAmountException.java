package com.woowacourse.gongseek.vote.exception;

import com.woowacourse.gongseek.common.exception.BadRequestException;

public class InvalidVoteAmountException extends BadRequestException {

    public InvalidVoteAmountException(int amount) {
        super(String.format("(입력한 amount : %d)", amount));
    }
}
