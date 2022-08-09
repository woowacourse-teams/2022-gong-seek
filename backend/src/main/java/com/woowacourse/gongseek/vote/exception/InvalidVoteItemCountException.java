package com.woowacourse.gongseek.vote.exception;

import com.woowacourse.gongseek.common.exception.ApplicationException;

public class InvalidVoteItemCountException extends ApplicationException {

    public InvalidVoteItemCountException(int voteItemSize) {
        super(" 현재 길이 : " + voteItemSize);
    }
}
