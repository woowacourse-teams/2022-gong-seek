package com.woowacourse.gongseek.vote.exception;

import com.woowacourse.gongseek.common.exception.BadRequestException;

public class VoteItemTooLongException extends BadRequestException {

    public VoteItemTooLongException(int voteItemLength) {
        super(String.format("(입력 투표 내용 길이 : %d)", voteItemLength));
    }
}
