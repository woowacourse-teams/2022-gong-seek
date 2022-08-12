package com.woowacourse.gongseek.comment.exception;

import com.woowacourse.gongseek.common.exception.NotFoundException;

public class CommentNotFoundException extends NotFoundException {

    public CommentNotFoundException(long commentId) {
        super(String.format("(commentId : %d)", commentId));
    }
}
