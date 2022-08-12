package com.woowacourse.gongseek.vote.exception;

import com.woowacourse.gongseek.common.exception.NotFoundException;

public class VoteItemNotFoundException extends NotFoundException {

    public VoteItemNotFoundException(long voteItemId) {
        super(String.format("(voteItemId : %d)", voteItemId));
    }
}
