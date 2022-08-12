package com.woowacourse.gongseek.vote.exception;

import com.woowacourse.gongseek.common.exception.BadRequestException;

public class InvalidVoteItemCountException extends BadRequestException {

    public InvalidVoteItemCountException(int voteItemSize) {
        super(" 현재 길이 : " + voteItemSize);
    }
}
