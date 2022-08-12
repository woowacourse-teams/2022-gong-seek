package com.woowacourse.gongseek.vote.exception;

import com.woowacourse.gongseek.common.exception.NotFoundException;

public class VoteNotFoundException extends NotFoundException {

    public VoteNotFoundException(long voteId) {
        super(String.format("(voteId : %d)", voteId));
    }
}
