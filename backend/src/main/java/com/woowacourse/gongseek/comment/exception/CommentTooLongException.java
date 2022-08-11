package com.woowacourse.gongseek.comment.exception;

import com.woowacourse.gongseek.common.exception.BadRequestException;

public class CommentTooLongException extends BadRequestException {

    public CommentTooLongException(int commentLength) {
        super(String.format("(입력하신 길이 : %d)", commentLength));
    }
}
